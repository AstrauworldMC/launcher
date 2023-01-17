package fr.timeto.astrauworld.launcher.main;

import br.com.azalim.mcserverping.MCPing;
import br.com.azalim.mcserverping.MCPingOptions;
import br.com.azalim.mcserverping.MCPingResponse;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.customelements.ZoneWindowMover;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.List;

import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.initLauncherSystemTray;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

/**
 * La classe de la frame du assets
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @see LauncherPanel
 */
public class LauncherFrame extends JFrame {

    private static LauncherFrame instance;

    private static final Rectangle movableZone = new Rectangle(0, 0, 1000, 33);

    /**
     * Défini comment s'affichera la frame du assets, son contenu, puis l'affiche
     */
    public LauncherFrame() {
        this.setTitle("Astrauworld Launcher");
        this.setSize(1000, 630);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResourceIgnorePath("/assets/launcher/main/logo.png"));
        this.setContentPane(new LauncherPanel());

        ZoneWindowMover mover = new ZoneWindowMover(this, movableZone);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);

    }

    public static String profileAfterMcExit = null;

    /**
     * La méthode appelée par le JRE au lancement, initialise tout le launcher
     * @param args Si des arguments sont ajoutés au lancement. Si l'argument {@link Launcher#afterMcExitArg} est spécifié, il doit être suivi du numéro du profil lancé
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void main(String[] args) throws IOException {

        String OS = System.getProperty("os.name");

        if (OS.toLowerCase().contains("win")) {
            Launcher.println("Windows OK");
        }else {
            JOptionPane.showMessageDialog(null, "Désolé, votre système d'exploitation (" + OS + ") n´est pas encore compatible", "Erreur de compatibilité", JOptionPane.ERROR_MESSAGE);
            Launcher.println("Sorry nope");
            System.exit(0);
        }

        Launcher.AW_DIR.mkdir();
        Launcher.AW_DATA_FOLDER.mkdir();
        Launcher.AW_GAMEFILES_FOLDER.mkdir();
        Launcher.AW_CRASH_FOLDER.mkdir();

        Launcher.AW_FIRSTPROFILE_CUSTOMFILES_FOLDER.mkdir();
        Launcher.AW_SECONDPROFILE_CUSTOMFILES_FOLDER.mkdir();
        Launcher.AW_THIRDPROFILE_CUSTOMFILES_FOLDER.mkdir();

        shaderpacksFolder.mkdir();
        optionsShadersTextfile.createNewFile();

        Launcher.AW_FIRSTPROFILE_ICON.createNewFile();

        Launcher.AW_SECONDPROFILE_ICON.createNewFile();

        Launcher.AW_THIRDPROFILE_ICON.createNewFile();


        initializeDataFiles();
        initProfileIcon();

        CrashReporter crashReporter = new CrashReporter("Astrauworld Launcher", Launcher.awCrashFolder);

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
            }
        } catch (Exception ignored) {}

        EasterEggs.initEastereggs();
        new File(Launcher.dataFolder + "eastereggs.properties").delete();

        if (firstProfileSaver.get(KEY.SETTINGS_MAINPROFILE) == null && secondProfileSaver.get(KEY.SETTINGS_MAINPROFILE) == null && thirdProfileSaver.get(KEY.SETTINGS_MAINPROFILE) == null) {
            firstProfileSaver.set(KEY.SETTINGS_MAINPROFILE, "true");
            secondProfileSaver.set(KEY.SETTINGS_MAINPROFILE, "false");
            thirdProfileSaver.set(KEY.SETTINGS_MAINPROFILE, "false");
        }

        initLauncherSystemTray();
        instance = new LauncherFrame();
    //    serverInfosTest();

    }

    private static void serverInfosTest() {
        MCPingOptions options = MCPingOptions.builder()
                .hostname("90.120.153.171")
                .port(25565)
                .build();

        MCPingResponse reply;

        try {
            reply = MCPing.getPing(options);
        } catch (IOException ex) {
            System.out.println(options.getHostname() + " is down or unreachable.");
            return;
        }

        System.out.printf("Full response from %s:%n", options.getHostname());
        System.out.println();

        MCPingResponse.Description description = reply.getDescription();

        System.out.println("Description:");
        System.out.println("    Raw: " + description.getText());
        System.out.println("    No color codes: " + description.getStrippedText());
        System.out.println();

        MCPingResponse.Players players = reply.getPlayers();

        System.out.println("Players: ");
        System.out.println("    Online count: " + players.getOnline());
        System.out.println("    Max players: " + players.getMax());
        System.out.println();

        // Can be null depending on the server
        List<MCPingResponse.Player> sample = players.getSample();

        if (sample != null) {
            System.out.println("    Players: " + players.getSample().stream()
                    .map(player -> String.format("%s@%s", player.getName(), player.getId()))
                    .collect(Collectors.joining(", "))
            );
            System.out.println();
        }

        MCPingResponse.Version version = reply.getVersion();

        System.out.println("Version: ");

        // The protocol is the version number: http://wiki.vg/Protocol_version_numbers
        System.out.println("    Protocol: " + version.getProtocol());
        System.out.println("    Name: " + version.getName());
        System.out.println();

        System.out.println("Ping: " + reply.getPing() + "ms");

    }

    /**
     * @return la classe
     */
    public static LauncherFrame getInstance() {
        return instance;
    }

}
