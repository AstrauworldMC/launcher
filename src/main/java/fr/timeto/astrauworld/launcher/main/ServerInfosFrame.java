package fr.timeto.astrauworld.launcher.main;

import br.com.azalim.mcserverping.MCPing;
import br.com.azalim.mcserverping.MCPingResponse;
import fr.theshark34.swinger.Swinger;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ServerInfosFrame extends JPanel {
    private static final JFrame frame = new JFrame();
    private static final JLabel connectionImage = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/serverInfosFrame/notConnected.png")));
    private static final JLabel playersLabel = new JLabel("Connexion...", SwingConstants.RIGHT);
    private static final JLabel pingLabel = new JLabel("Connexion...", SwingConstants.RIGHT);
    private static final JLabel versionLabel = new JLabel("Connexion...", SwingConstants.RIGHT);
    private static final JLabel protocolLabel = new JLabel("Connexion...", SwingConstants.RIGHT);

    private static void initFrame() {
        frame.setTitle("Infos serveur");
        frame.setSize(264, 373);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Swinger.getResourceIgnorePath("/assets/launcher/main/logo.png"));
        frame.setContentPane(new ServerInfosFrame());

        frame.setVisible(true);

        updateInfos();

    }

    private ServerInfosFrame() {
        this.setLayout(null);

        connectionImage.setBounds(67, 70, 130, 30);
        this.add(connectionImage);

        playersLabel.setBounds(10, 126, 226, 20);
        playersLabel.setFont(LauncherPanel.Components.titleLabel.getFont().deriveFont(18f));
        playersLabel.setForeground(Color.WHITE);
        this.add(playersLabel);

        pingLabel.setBounds(playersLabel.getBounds());
        pingLabel.setLocation(pingLabel.getX(), pingLabel.getY() + 39);
        pingLabel.setFont(playersLabel.getFont());
        pingLabel.setForeground(Color.ORANGE);
        this.add(pingLabel);

        versionLabel.setBounds(pingLabel.getBounds());
        versionLabel.setLocation(versionLabel.getX(), versionLabel.getY() + 39);
        versionLabel.setFont(playersLabel.getFont());
        versionLabel.setForeground(Color.WHITE);
        this.add(versionLabel);

        protocolLabel.setBounds(versionLabel.getBounds());
        protocolLabel.setLocation(protocolLabel.getX(), protocolLabel.getY() + 39);
        protocolLabel.setFont(playersLabel.getFont());
        protocolLabel.setForeground(Color.WHITE);
        this.add(protocolLabel);

    }

    private static void updateInfos() {

        Thread t = new Thread(() -> {
            playersLabel.setText("Connexion...");
            playersLabel.setForeground(Color.WHITE);
            pingLabel.setText("Connexion...");
            pingLabel.setForeground(Color.WHITE);
            versionLabel.setText("Connexion...");
            versionLabel.setForeground(Color.WHITE);
            protocolLabel.setText("Connexion...");
            protocolLabel.setForeground(Color.WHITE);

            while (frame.isShowing()) {
                MCPingResponse reply;

                try {
                    reply = MCPing.getPing(Launcher.serverOptions);
                } catch (IOException ex) {
                    connectionImage.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/serverInfosFrame/notConnected.png")));
                    playersLabel.setText("N/A");
                    playersLabel.setForeground(Color.RED);
                    pingLabel.setText("N/A");
                    pingLabel.setForeground(Color.RED);
                    versionLabel.setText("N/A");
                    versionLabel.setForeground(Color.RED);
                    protocolLabel.setText("N/A");
                    protocolLabel.setForeground(Color.RED);
                    return;
                }

                connectionImage.setIcon(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/serverInfosFrame/connected.png")));

                MCPingResponse.Players players = reply.getPlayers();
                MCPingResponse.Version version = reply.getVersion();

                playersLabel.setText(players.getOnline() + "/" + players.getMax());

                if (reply.getPing() < 50) {
                    pingLabel.setForeground(Color.GREEN);
                } else if (reply.getPing() < 140) {
                    pingLabel.setForeground(Color.ORANGE);
                } else {
                    pingLabel.setForeground(Color.RED);
                }
                pingLabel.setText(reply.getPing() + "ms");

                versionLabel.setText(version.getName());
                protocolLabel.setText(version.getProtocol() + "");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        t.start();
    }

    public static void openServerInfos() {
        initFrame();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Swinger.getResourceIgnorePath("/assets/launcher/serverInfosFrame/serverInfosFrame.png"), 0, 0, this.getWidth(), this.getHeight(), this);

    }
}
