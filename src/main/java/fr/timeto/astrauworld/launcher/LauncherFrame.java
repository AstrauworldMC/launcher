package fr.timeto.astrauworld.launcher;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
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
     */
    @SuppressWarnings("all")
    public static void initializeDataFiles() {
        if(Objects.equals(firstProfileSaver.get("fileCreated"), "true"))
        {// Si le fichier existe, ne rien faire
        } else {
            // Informations générales
            firstProfileSaver.set("name", "none");
            firstProfileSaver.set("email", "none");
            firstProfileSaver.set("password", "none");
            firstProfileSaver.set("UUID", "none");
            // Configuration de Minecraft
            firstProfileSaver.set("Optifine", "false");

            firstProfileSaver.set("fileCreated", "true");
        }
        if(Objects.equals(secondProfileSaver.get("fileCreated"), "true"))
        {// Si le fichier existe, ne rien faire
        } else {
            // Informations générales
            secondProfileSaver.set("name", "none");
            secondProfileSaver.set("email", "none");
            secondProfileSaver.set("password", "none");
            secondProfileSaver.set("UUID", "none");
            // Configuration de Minecraft
            secondProfileSaver.set("Optifine", "false");

            secondProfileSaver.set("fileCreated", "true");
        }
        if(Objects.equals(thirdProfileSaver.get("fileCreated"), "true"))
        {// Si le fichier existe, ne rien faire
        } else {
            // Informations générales
            thirdProfileSaver.set("name", "none");
            thirdProfileSaver.set("email", "none");
            thirdProfileSaver.set("password", "none");
            thirdProfileSaver.set("UUID", "none");
            // Configuration de Minecraft
            thirdProfileSaver.set("Optifine", "false");

            thirdProfileSaver.set("fileCreated", "true");
        }

    }

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
