package fr.timeto.astrauworld.launcher;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.io.IOException;

import static fr.timeto.astrauworld.launcher.ProfileSaver.*;

@SuppressWarnings("unused")
public class LauncherFrame extends JFrame {

    private static LauncherFrame instance;
    private final LauncherPanel launcherPanel;
    private JScrollPane scrollPane;
    private static CrashReporter crashReporter;

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
