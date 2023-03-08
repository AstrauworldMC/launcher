package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.customelements.ZoneWindowMover;
import fr.timeto.astrauworld.launcher.discordrpc.DiscordManager;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.timutilslib.CustomFonts;
import fr.timeto.timutilslib.PopUpMessages;
import fr.timeto.timutilslib.TimFilesUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.initLauncherSystemTray;
import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.verifyLauncherVersion;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

/**
 * La classe de la frame du launcher
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @see LauncherPanel
 */
public class LauncherFrame extends JFrame {

    private static LauncherFrame instance;

    private static final Rectangle movableZone = new Rectangle(0, 0, 1000, 33);

    public static boolean devEnv = false;

    /**
     * Défini comment s'affichera la frame du launcher, son contenu, puis l'affiche
     */
    public LauncherFrame() {
        this.setTitle("Astrauworld Launcher");
        this.setSize(1000, 630);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(exitListener);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResourceIgnorePath("/assets/launcher/main/logo.png"));
        this.setContentPane(new LauncherPanel());

        ZoneWindowMover mover = new ZoneWindowMover(this, movableZone);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);
    }

    public static WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            DiscordManager.stop();
            LauncherSystemTray.stop();
            System.exit(0);
        }
    };

    public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = Launcher.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public static Properties launcherProperties = new Properties();
    public static void initCurrentLauncherProperties() {
        InputStream is = getFileFromResourceAsStream("launcher.properties");
        try {
            launcherProperties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String profileAfterMcExit = null;

    /**
     * La méthode appelée par le JRE au lancement, initialise tout le launcher
     * @param args Si des arguments sont ajoutés au lancement. Si l'argument {@link Launcher#afterMcExitArg} est spécifié, il doit être suivi du numéro du profil lancé
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void main(String[] args) throws IOException {
        CustomFonts.initFonts();
        try {
            initCurrentLauncherProperties();

            String OS = System.getProperty("os.name");

            if (OS.toLowerCase().contains("win")) {
                Launcher.println("Windows OK");
            } else {
                Thread ok = new Thread(() -> {
                    System.exit(1);
                });
                PopUpMessages.errorMessage("Erreur", "Désolé, votre système d´exploitation (" + OS + ") n'est pas compatible", ok);
                Launcher.println("Sorry nope");
            }

            Launcher.println("Chargement...");

            Launcher.AW_DIR.mkdir();
            Launcher.AW_DATA_FOLDER.mkdir();
            Launcher.AW_GAMEFILES_FOLDER.mkdir();
            Launcher.AW_CRASH_FOLDER.mkdir();

            Launcher.AW_FIRSTPROFILE_CUSTOMFILES_FOLDER.mkdir();
            Launcher.AW_SECONDPROFILE_CUSTOMFILES_FOLDER.mkdir();
            Launcher.AW_THIRDPROFILE_CUSTOMFILES_FOLDER.mkdir();

            Launcher.AW_FIRSTPROFILE_ICON.createNewFile();

            Launcher.AW_SECONDPROFILE_ICON.createNewFile();

            Launcher.AW_THIRDPROFILE_ICON.createNewFile();

            initializeDataFiles();

            if (firstProfileSaver.get(KEY.INFOS_NAME.get()).toLowerCase().replaceAll(" ", "").equals("no")) {
                firstProfileSaver.set(KEY.SETTINGS_PROFILENAME.get(), "Vide");
                firstProfileSaver.set(KEY.INFOS_NAME.get(), "");
            }
            if (secondProfileSaver.get(KEY.INFOS_NAME.get()).toLowerCase().replaceAll(" ", "").equals("no")) {
                secondProfileSaver.set(KEY.SETTINGS_PROFILENAME.get(), "Vide");
                secondProfileSaver.set(KEY.INFOS_NAME.get(), "");
            }
            if (thirdProfileSaver.get(KEY.INFOS_NAME.get()).toLowerCase().replaceAll(" ", "").equals("no")) {
                thirdProfileSaver.set(KEY.SETTINGS_PROFILENAME.get(), "Vide");
                thirdProfileSaver.set(KEY.INFOS_NAME.get(), "");
            }

            initProfileIcon();

            CrashReporter crashReporter = new CrashReporter("Astrauworld Launcher", Launcher.awCrashFolder);

            Swinger.setResourcePath("/assets/launcher/");
            Swinger.setSystemLookNFeel();

            try {
                if (Objects.equals(args[0], Launcher.afterMcExitArg)) {
                    Saver saver = null;
                    if (Objects.equals(args[1], "1")) {
                        saver = firstProfileSaver;
                    } else if (Objects.equals(args[1], "2")) {
                        saver = secondProfileSaver;
                    } else if (Objects.equals(args[1], "3")) {
                        saver = thirdProfileSaver;
                    }
                    profileAfterMcExit = args[1];

                    ProfileSaver.saveCustomFiles(saver);
                } else if (Objects.equals(args[0], Launcher.devEnvArg)) {
                    devEnv = true;
                }
            } catch (Exception ignored) {
            }

            if (profileAfterMcExit == null) {
                DiscordManager.start();
                DiscordManager.setLauncherPresence();

                EasterEggs.initEastereggs();
                new File(Launcher.dataFolder + "eastereggs.properties").delete();

                if (firstProfileSaver.get(KEY.SETTINGS_MAINPROFILE.get()) == null && secondProfileSaver.get(KEY.SETTINGS_MAINPROFILE.get()) == null && thirdProfileSaver.get(KEY.SETTINGS_MAINPROFILE.get()) == null) {
                    firstProfileSaver.set(KEY.SETTINGS_MAINPROFILE.get(), "true");
                    secondProfileSaver.set(KEY.SETTINGS_MAINPROFILE.get(), "false");
                    thirdProfileSaver.set(KEY.SETTINGS_MAINPROFILE.get(), "false");
                }

                PageChange.lastSettingsSaver = null;
                initLauncherSystemTray();
                instance = new LauncherFrame();

                shaderpacksFolder.mkdir();
                optionsShadersTextfile.createNewFile();

                new File(Launcher.AW_GAMEFILES_FOLDER + "1.18.2.json").delete();
                File mcVersionJsonFile = new File(Launcher.AW_GAMEFILES_FOLDER + Launcher.separatorChar + "1.18.2.json");
                if (!mcVersionJsonFile.exists()) {
                    optionsTextfile.delete();
                    optionsOFTextfile.delete();
                    optionsShadersTextfile.delete();
                    int i = 1;
                    while (i != 4) {
                        initCustomFilesFolder(ProfileSaver.getSaver(i + ""));
                        optionsProfileTextfile.delete();
                        optionsOFProfileTextfile.delete();
                        optionsShadersProfileTextfile.delete();
                        i++;
                    }
                    mcVersionJsonFile.createNewFile();
                }

                File serverDat = new File(Launcher.AW_GAMEFILES_FOLDER + File.separator + "servers.dat");
                if (!serverDat.exists()) {
                    TimFilesUtils.downloadFromInternet("https://github.com/AstrauworldMC/resources/raw/main/servers.dat", serverDat);
                    Launcher.println("servers.dat file downloaded");
                }

                if (!devEnv) {
                    verifyLauncherVersion(false, false);
                }
            } else {
                getInstance().setName("Astrauworld Launcher");
                getInstance().setVisible(true);
                initLauncherSystemTray();
                PageChange.setPage(true, PageName.PROFILE_HOME, profileAfterMcExit);
                DiscordManager.setLauncherPresence();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Thread t = new Thread(() -> {
                System.exit(1);
            });
            PopUpMessages.errorMessage("Erreur", e.getLocalizedMessage(), t);
        }

    }

    /**
     * @return la classe
     */
    public static LauncherFrame getInstance() {
        return instance;
    }

}
