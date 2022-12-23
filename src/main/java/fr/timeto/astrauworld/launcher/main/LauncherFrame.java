package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.customelements.ZoneWindowMover;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

/**
 * La classe de la frame du launcher
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @see LauncherPanel
 */
public class LauncherFrame extends JFrame {

    private static LauncherFrame instance;
    private final LauncherPanel launcherPanel;
    private JScrollPane scrollPane;
    private static CrashReporter crashReporter;

    private static final Rectangle movableZone = new Rectangle(0, 0, 1000, 33);

    /**
     * Défini comment s'affichera la frame du launcher, son contenu, puis l'affiche
     */
    public LauncherFrame() {
        this.setTitle("Astrauworld Launcher");
        this.setSize(1000, 630);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResourceIgnorePath("/assets.launcher/main/logo.png"));
        this.setContentPane(launcherPanel = new LauncherPanel());

        ZoneWindowMover mover = new ZoneWindowMover(this, movableZone);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);

    }

    /**
     * La méthode appelée par le JRE au lancement, initialise tout le launcher
     * @param args Si des arguments sont ajoutés au lancement. Si l'argument {@link Launcher#afterMcExitArg} est spécifié, il doit être suivi du numéro du profil lancé
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void main(String[] args) {

        String OS = System.getProperty("os.name");

        if (OS.contains("Win") || OS.contains("win")) {
            System.out.println("Windows OK");
        }else {
            JOptionPane.showMessageDialog(null, "Désolé, votre système d'exploitation (" + OS + ") n´est pas encore compatible", "Erreur de compatibilité", JOptionPane.ERROR_MESSAGE);
            System.out.println("Sorry nope");
            System.exit(0);
        }

        Launcher.AW_DIR.mkdir();
        Launcher.AW_DATA_FOLDER.mkdir();
        Launcher.AW_GAMEFILES_FOLDER.mkdir();
        Launcher.AW_CRASH_FOLDER.mkdir();

        Launcher.AW_FIRSTPROFILE_CUSTOMFILES_FOLDER.mkdir();
        Launcher.AW_SECONDPROFILE_CUSTOMFILES_FOLDER.mkdir();
        Launcher.AW_THIRDPROFILE_CUSTOMFILES_FOLDER.mkdir();

        try {
            Launcher.AW_FIRSTPROFILE_ICON.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Launcher.AW_SECONDPROFILE_ICON.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Launcher.AW_THIRDPROFILE_ICON.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initializeDataFiles();
        try {
            initProfileIcon();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        crashReporter = new CrashReporter("Astrauworld Launcher", Launcher.awCrashFolder);

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

                ProfileSaver.saveCustomFiles(saver);
            }
        } catch (Exception ignored) {}

        EasterEggs.initEastereggs();

        instance = new LauncherFrame();

    }

    /**
     * @return la classe
     */
    public static LauncherFrame getInstance() {
        return instance;
    }

}
