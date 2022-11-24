package fr.timeto.astrauworld.launcher;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

@SuppressWarnings("unused")
public class LauncherFrame extends JFrame {

    private static LauncherFrame instance;
    private final LauncherPanel launcherPanel;
    private JScrollPane scrollPane;
    private static CrashReporter crashReporter;

    public static Saver firstProfileSaver = new Saver(Launcher.awFirstProfileData);
    public static Saver secondProfileSaver = new Saver(Launcher.awSecondProfileData);
    public static Saver thirdProfileSaver = new Saver(Launcher.awThirdProfileData);

    /**
     * Défini comment s'affichera la frame du launcher, son contenu, puis l'affiche
     */
    public LauncherFrame() {
        this.setTitle("Astrauworld Launcher");
        this.setSize(1000, 630);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResourceIgnorePath("/logo.png"));
        this.setContentPane(launcherPanel = new LauncherPanel());

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);

    }

    /**
     * Initialise les fichiers de données situés dans [APPDATA]\Astrauworld Launcher\data
     * @param saver Le saver qui se fera initialiser
     */
    public static void initializeDataFiles(Saver saver) {
        if(!Objects.equals(saver.get(ProfileSaver.KEY.FILECREATED), "true")) {
            // Informations générales
            saver.set(ProfileSaver.KEY.INFOS_NAME, "none");
            saver.set(ProfileSaver.KEY.INFOS_EMAIL, "none");
            saver.set(ProfileSaver.KEY.INFOS_UUID, "none");
            saver.set(ProfileSaver.KEY.INFOS_ACCESSTOKEN, "none");
            saver.set(ProfileSaver.KEY.INFOS_REFRESHTOKEN, "none");
            // Configuration de Minecraft, si la key commence par 'mod' -> mod client
            saver.set(ProfileSaver.KEY.MOD_OPTIFINE, "false");
            saver.set(ProfileSaver.KEY.MOD_FPSMODEL, "false");
            saver.set(ProfileSaver.KEY.MOD_BETTERTPS, "false");
            saver.set(ProfileSaver.KEY.MOD_FALLINGLEAVES, "false");
            saver.set(ProfileSaver.KEY.MOD_APPLESKIN, "false");
            saver.set(ProfileSaver.KEY.MOD_SOUNDPHYSICS, "false");

            saver.set(ProfileSaver.KEY.FILECREATED, "true");
        }
    }

    public static void initializeDataFiles() {
        initializeDataFiles(firstProfileSaver);
        initializeDataFiles(secondProfileSaver);
        initializeDataFiles(thirdProfileSaver);
    }

    public static void main(String[] args) {
    //    System.out.println("Var: " + System.getenv("JAVAFX_HOME"));

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

        crashReporter = new CrashReporter("Astrauworld Launcher", Launcher.awCrashFolder);

        instance = new LauncherFrame();

    }

    public static LauncherFrame getInstance() {
        return instance;
    }

    public static CrashReporter getCrashReporter() {
        return crashReporter;
    }

    public LauncherPanel getLauncherPanel() {
        return this.launcherPanel;
    }
}
