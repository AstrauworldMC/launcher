package fr.timeto.astrauworld.launcher.panels.profile;

import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.ServerInfosFrame;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.pagesutilities.Server;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.astrauworld.launcher.secret.whitelistservers.WhitelistServers;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;

import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class ProfileWhitelistServers extends PageCreator {

    public WhitelistServer server1 = new WhitelistServer();
    public WhitelistServer server2 = new WhitelistServer();
    public WhitelistServer server3 = new WhitelistServer();
    public WhitelistServer server4 = new WhitelistServer();
    public final WhitelistServer[] serversList = new WhitelistServer[] {
      server1, server2, server3, server4
    };

    public ProfileWhitelistServers() {
        super(PageName.PROFILE_WHITELIST_SERVERS, "Profil " + ProfileSaver.getSelectedProfile(), "Serveurs whitelist");

        server1.setBounds(0, 0);
        add(server1);
        server1.setVisible(false);

        server2.setBounds(411, 0);
        add(server2);
        server2.setVisible(false);

        server3.setBounds(0, 259);
        add(server3);
        server3.setVisible(false);

        server4.setBounds(411, 259);
        add(server4);
        server4.setVisible(false);

        add(getBg().getPanel());
    }

    @Override
    public void recolor() {
        server1.recolor();
        server2.recolor();
        server3.recolor();
        server4.recolor();
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag) {
            setTitle("Profil " + ProfileSaver.getSelectedProfile());
            int i = 0;
            MinecraftProfile mcProfile = new MinecraftProfile(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_UUID.get()), ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), null);
            while (i != WhitelistServers.getPlayerWhitelistedServers(mcProfile).length) {
                serversList[i].setServer(WhitelistServers.getPlayerWhitelistedServers(mcProfile)[i]);
                serversList[i].setVisible(true);
                i++;
            }

            LauncherPanel.enablePlayButtons(!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), ""));
        } else {
            server1.setVisible(false);
            server2.setVisible(false);
            server3.setVisible(false);
            server4.setVisible(false);
        }
        super.setVisible(aFlag);
    }

    public void enablePlayButtons(boolean e) {
        server1.playButton.setEnabled(e);
        server2.playButton.setEnabled(e);
        server3.playButton.setEnabled(e);
        server4.playButton.setEnabled(e);
    }

    static class WhitelistServer extends JPanel implements SwingerEventListener {
        public final JLabel serverNameLabel = new JLabel("", SwingConstants.CENTER);
        public final STexturedButton serverInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/serverInfosButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/serverInfosButton-hover.png"));
        public final STexturedButton modsListButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/modsList-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/modsList-hover.png"));
        public final STexturedButton playButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/playButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/playButton-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/playButton-disabled.png"));

        private Server actualServer = null;
        WhitelistServer() {
            setLayout(null);
            setOpaque(false);

            serverNameLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
            serverNameLabel.setFont(CustomFonts.robotoBlackFont.deriveFont(25f));
            serverNameLabel.setBounds(15, 30, 380, 25);
            add(serverNameLabel);

            serverInfosButton.setBounds(64, 100);
            serverInfosButton.addEventListener(this);
            add(serverInfosButton);

            modsListButton.setBounds(264, 100);
            modsListButton.addEventListener(this);
            add(modsListButton);

            playButton.setBounds(88, 173);
            playButton.addEventListener(this);
            add(playButton);
        }

        public void setServer(Server server) {
            actualServer = server;
            serverNameLabel.setText(server.getServerName());
        }

        public void recolor() {
            serverNameLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        }

        @Override
        public void onEvent(SwingerEvent swingerEvent) {
            Object src = swingerEvent.getSource();
            Saver eventSaver = ProfileSaver.getSelectedSaver();

            if (src == serverInfosButton) {
                if (actualServer != null) {
                    ServerInfosFrame.openServerInfos(actualServer);
                }
            } else if (src == playButton) {
                if (actualServer != null) {
                    Thread t = new Thread(() -> {
                        try {
                            actualServer.update(eventSaver);

                            actualServer.launch(eventSaver);

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    });
                    t.start();
                }
            } else if (src == modsListButton) {
                LauncherPanel.Components.aboutModsPage.setServer(actualServer);
                PageChange.setPage(true, PageName.ABOUT_MODS);
            }

        }

        public void setBounds(int x, int y) {
            setBounds(x, y, 411, 259);
        }
    }
}
