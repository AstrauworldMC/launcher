package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.customelements.ZoneWindowMover;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * La classe de la frame du launcher
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @see LauncherPanel
 */
public class LauncherFrame extends JFrame {

    public static LauncherFrame instance;

    private static final Rectangle movableZone = new Rectangle(0, 0, 1000, 33);

    public static boolean devEnv = false;

    /**
     * Défini comment s'affichera la frame du launcher, son contenu, puis l'affiche
     */
    public LauncherFrame() throws Exception {
        this.setTitle("Astrauworld Launcher");
        this.setSize(1000, 630);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(exitListener);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResourceIgnorePath("/assets/launcher/main/logo.png"));
        this.setContentPane(new LauncherPanel());
        try {
            Taskbar.getTaskbar().setIconImage(Swinger.getResourceIgnorePath("/assets/launcher/main/logo.png"));
        } catch (UnsupportedOperationException ignored) {}

        ZoneWindowMover mover = new ZoneWindowMover(this, movableZone);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);
    }

    @Override
    public void setSize(int width, int height) {
        setLocation(this.getX() + ((this.getWidth() - width)/2), this.getY() + ((this.getHeight() - height)/2));
        super.setSize(width, height);
    }

    public static WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
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
     * @return la classe
     */
    public static LauncherFrame getInstance() {
        return instance;
    }

}
