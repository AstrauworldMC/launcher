package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.customelements.ZoneWindowMover;
import fr.timeto.astrauworld.launcher.secret.DiscordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public static void setTaskbarIcon(Image image) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Class taskbar = Class.forName("java.awt.Taskbar");
        Method getTaskbar = taskbar.getDeclaredMethod("getTaskbar");
        Object instance = getTaskbar.invoke(taskbar);
        Method setIconImage = instance.getClass().getDeclaredMethod("setIconImage", Image.class);
        setIconImage.invoke(instance, image);
    }

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


    /**
     * @return la classe
     */
    public static LauncherFrame getInstance() {
        return instance;
    }

}
