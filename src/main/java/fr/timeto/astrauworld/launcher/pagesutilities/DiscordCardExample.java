package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.timeto.astrauworld.launcher.customelements.HSLColor;
import fr.timeto.astrauworld.launcher.customelements.Tab;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.secret.DiscordManager;
import fr.timeto.timutilslib.CustomFonts;
import fr.timeto.timutilslib.TimFilesUtils;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.imgscalr.Scalr;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DiscordCardExample extends JPanel {

    JPanel card = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(20,20);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            //Draws the rounded opaque panel with borders.
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width, 70, 0, 0);//paint background
            graphics.fillRoundRect(0, 50, width, height-50, arcs.width, arcs.height);//paint background
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
        }
    };
    JPanel round = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(100,100);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            //Draws the rounded opaque panel with borders.
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
        }
    };
    JPanel inside = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(20,20);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            //Draws the rounded opaque panel with borders.
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
            graphics.setColor(new Color(46, 47, 52));
            graphics.fillRect(10, 42, getWidth() - 20, 1);
        }
    };

    JLabel userAvatar = new JLabel();
    JLabel usernameLabel1 = new JLabel();
    JLabel usernameLabel2 = new JLabel();

    JLabel inGameLabel = new JLabel("EN TRAIN DE JOUER (dans le launcher)");
    JPanel smallImagePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(inside.getBackground());
            g2d.fillRoundRect(0, 0, 34, 34, 100, 100);

            super.paintComponent(g);

        }
    };
    JLabel smallImage = new JLabel();
    JLabel bigImage = new JLabel();

    JLabel appNameLabel = new JLabel("AstrauworldMC");
    JLabel detailsLabel = new JLabel("Details label");
    JLabel stateLabel = new JLabel("State label");
    JLabel timeLabel = new JLabel("Time label");


    JLabel inGameLabelGame = new JLabel("EN TRAIN DE JOUER (en jeu)");
    JPanel smallImagePanelGame = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(inside.getBackground());
            g2d.fillRoundRect(0, 0, 34, 34, 100, 100);

            super.paintComponent(g);

        }
    };
    JLabel smallImageGame = new JLabel();
    JLabel bigImageGame = new JLabel();

    JLabel appNameLabelGame = new JLabel("AstrauworldMC");
    JLabel detailsLabelGame = new JLabel("Details label");
    JLabel stateLabelGame = new JLabel("State label");
    JLabel timeLabelGame = new JLabel("Time label");

    int gap = 18;
    int gapBetweenTheTwo = 120;

    public DiscordCardExample() {

        setLayout(null);
        setBackground(new Color(86, 98, 246));
        setForeground(getBackground());
        setOpaque(false);
        setPreferredSize(new Dimension(395, 450));

        card.setLayout(null);
        card.setBackground(new Color(35, 36, 40));
        card.setForeground(card.getBackground());
        card.setOpaque(false);
        card.setBounds(0, 70, getPreferredSize().width, getPreferredSize().height-70);

        round.setLayout(null);
        round.setBackground(card.getBackground());
        round.setForeground(round.getBackground());
        round.setOpaque(false);
        round.setBounds(9, 16, 88, 88);

        inside.setLayout(null);
        inside.setBackground(new Color(17, 18, 20));
        inside.setForeground(inside.getBackground());
        inside.setOpaque(false);
        inside.setBounds(10, 110, getPreferredSize().width-20, getPreferredSize().height-120);

        userAvatar.setBounds(13, 20, 80, 80);
        add(userAvatar);

        usernameLabel1.setBounds(23, 118, 380, 30);
        usernameLabel1.setForeground(Color.WHITE);
        usernameLabel1.setFont(CustomFonts.robotoBoldFont.deriveFont(22f));
        usernameLabel1.setText("Test");
        add(usernameLabel1);

        usernameLabel2.setBounds(Tab.getStringSize(usernameLabel1, "Test").width + 2 + usernameLabel1.getX(), usernameLabel1.getY(), 380 - Tab.getStringSize(usernameLabel1, "").width -5, 30);
        usernameLabel2.setForeground(new Color(181, 186, 193));
        usernameLabel2.setFont(CustomFonts.robotoBoldFont.deriveFont(22f));
        usernameLabel2.setText("#0000");
        add(usernameLabel2);

        inGameLabel.setBounds(12, 48, inside.getWidth() - 24, 20);
        inGameLabel.setForeground(Color.WHITE);
        inGameLabel.setFont(CustomFonts.robotoBlackFont.deriveFont(12f));
        inside.add(inGameLabel);

        smallImagePanel.setLayout(null);
        smallImagePanel.setOpaque(false);
        smallImagePanel.setBounds(62, 126, 60, 60);
        smallImage.setBounds(2, 2, 60, 60);
        smallImage.setVerticalAlignment(SwingConstants.TOP);
        smallImagePanel.add(smallImage);
    //    inside.add(smallImagePanel);

        bigImage.setBounds(10, 75, 80, 80);
        bigImage.setToolTipText("Astrauworld Launcher " + Launcher.version);
        inside.add(bigImage);

        appNameLabel.setBounds(96, 79, inside.getWidth() - 100, 20);
        appNameLabel.setForeground(Color.WHITE);
        appNameLabel.setFont(CustomFonts.robotoBlackFont.deriveFont(15f));
        inside.add(appNameLabel);

        detailsLabel.setBounds(96, appNameLabel.getY() + gap, inside.getWidth() - 100, 20);
        detailsLabel.setForeground(Color.WHITE);
        detailsLabel.setFont(CustomFonts.robotoRegularFont.deriveFont(14f));
        inside.add(detailsLabel);

        stateLabel.setBounds(96, detailsLabel.getY() + gap, inside.getWidth() - 100, 20);
        stateLabel.setForeground(Color.WHITE);
        stateLabel.setFont(detailsLabel.getFont());
        inside.add(stateLabel);

        timeLabel.setBounds(96, stateLabel.getY() + gap, inside.getWidth() - 100, 20);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(detailsLabel.getFont());
        inside.add(timeLabel);


        inGameLabelGame.setBounds(12, 48 + gapBetweenTheTwo, inside.getWidth() - 24, 20);
        inGameLabelGame.setForeground(Color.WHITE);
        inGameLabelGame.setFont(CustomFonts.robotoBlackFont.deriveFont(12f));
        inside.add(inGameLabelGame);

        smallImagePanelGame.setLayout(null);
        smallImagePanelGame.setOpaque(false);
        smallImagePanelGame.setBounds(62, 126 + gapBetweenTheTwo, 60, 60);
        smallImageGame.setBounds(2, 2, 60, 60);
        smallImageGame.setVerticalAlignment(SwingConstants.TOP);
        smallImageGame.setToolTipText("Minecraft " + Launcher.mcVersion);
        smallImagePanelGame.add(smallImageGame);
        inside.add(smallImagePanelGame);

        bigImageGame.setBounds(10, 75 + gapBetweenTheTwo, 80, 80);
        bigImageGame.setToolTipText("AstrauworldMC");
        inside.add(bigImageGame);

        appNameLabelGame.setBounds(96, 79 + gapBetweenTheTwo, inside.getWidth() - 100, 20);
        appNameLabelGame.setForeground(Color.WHITE);
        appNameLabelGame.setFont(CustomFonts.robotoBlackFont.deriveFont(15f));
        inside.add(appNameLabelGame);

        detailsLabelGame.setBounds(96, appNameLabelGame.getY() + gap, inside.getWidth() - 100, 20);
        detailsLabelGame.setForeground(Color.WHITE);
        detailsLabelGame.setFont(CustomFonts.robotoRegularFont.deriveFont(14f));
        inside.add(detailsLabelGame);

        stateLabelGame.setBounds(96, detailsLabelGame.getY() + gap, inside.getWidth() - 100, 20);
        stateLabelGame.setForeground(Color.WHITE);
        stateLabelGame.setFont(detailsLabelGame.getFont());
        inside.add(stateLabelGame);

        timeLabelGame.setBounds(96, stateLabelGame.getY() + gap, inside.getWidth() - 100, 20);
        timeLabelGame.setForeground(Color.WHITE);
        timeLabelGame.setFont(detailsLabel.getFont());
        inside.add(timeLabelGame);

        add(inside);
        add(round);
        add(card);

        update(false);
    }

    public void update(boolean silent) {
        updateGame();

        try {
            DiscordRichPresence dRPC = DiscordManager.getRPC();
            ArrayList<JLabel> actualLabels = new ArrayList<>();

            actualLabels.add(appNameLabel);
            if (Boolean.parseBoolean(ProfileSaver.globalSettingsSaver.get(ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWTHINGS.get()))) {
                if (dRPC.details != null) {
                    actualLabels.add(detailsLabel);
                    detailsLabel.setText(Launcher.parseUnicode(dRPC.details));
                    detailsLabel.setVisible(true);
                } else {
                    detailsLabel.setVisible(false);
                }
                if (dRPC.state != null) {
                    actualLabels.add(stateLabel);
                    stateLabel.setText(Launcher.parseUnicode(dRPC.state));
                    stateLabel.setVisible(true);
                } else {
                    stateLabel.setVisible(false);
                }
            } else {
                detailsLabel.setVisible(false);
                stateLabel.setVisible(false);
            }
            if (Boolean.parseBoolean(ProfileSaver.globalSettingsSaver.get(ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWTIME.get()))) {
                if (dRPC.startTimestamp != dRPC.endTimestamp) {
                    actualLabels.add(timeLabel);
                    long elapsed;
                    String elapsedStr;
                    if (dRPC.endTimestamp == 0) {
                        elapsed = System.currentTimeMillis() - dRPC.startTimestamp;

                        elapsedStr = getTime(elapsed, true) + "écoulées";
                    } else {
                        elapsed = dRPC.endTimestamp - System.currentTimeMillis();

                        elapsedStr = getTime(elapsed, true) + "restantes";
                    }

                    timeLabel.setText(Launcher.parseUnicode(elapsedStr));
                    timeLabel.setVisible(true);
                } else {
                    timeLabel.setVisible(false);
                }
            } else {
                timeLabel.setVisible(false);
            }
            bigImage.setToolTipText(dRPC.largeImageText);

            int x = 98;
            int w = inside.getWidth() - 100;
            int h = 20;
            switch (actualLabels.size()) {
                case (4) -> {
                    actualLabels.get(0).setBounds(x, 78, w, h);
                    actualLabels.get(1).setBounds(x, actualLabels.get(0).getY() + gap, w, h);
                    actualLabels.get(2).setBounds(x, actualLabels.get(1).getY() + gap, w, h);
                    actualLabels.get(3).setBounds(x, actualLabels.get(2).getY() + gap, w, h);
                }
                case (3) -> {
                    actualLabels.get(0).setBounds(x, 87, w, h);
                    actualLabels.get(1).setBounds(x, actualLabels.get(0).getY() + gap, w, h);
                    actualLabels.get(2).setBounds(x, actualLabels.get(1).getY() + gap, w, h);
                }
                case (2) -> {
                    actualLabels.get(0).setBounds(x, 96, w, h);
                    actualLabels.get(1).setBounds(x, actualLabels.get(0).getY() + gap, w, h);
                }
                case (1) -> actualLabels.get(0).setBounds(96, 105, w, h);
            }
        } catch (NullPointerException ignored) {}

        if (!silent) {
            try {
                TimFilesUtils.downloadFromInternet("https://cdn.discordapp.com/avatars/" + DiscordManager.user.userId + "/" + DiscordManager.user.avatar + ".png", Launcher.AW_DISCORD_AVATAR_ICON);
                if (!Launcher.AW_DISCORD_SMALLIMAGE_ICON.exists()) {
                    TimFilesUtils.downloadFromInternet("https://cdn.discordapp.com/app-assets/" + DiscordManager.appId + "/1079919927621861456.png", Launcher.AW_DISCORD_SMALLIMAGE_ICON);
                }
                if (!Launcher.AW_DISCORD_BIGIMAGE_ICON.exists()) {
                    TimFilesUtils.downloadFromInternet("https://cdn.discordapp.com/app-assets/" + DiscordManager.appId + "/1079919874232561664.png", Launcher.AW_DISCORD_BIGIMAGE_ICON);
                }
            } catch (NullPointerException ignored) {
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            userAvatar.setIcon(new ImageIcon(Launcher.makeRoundedCorner(Scalr.resize(Launcher.getImageFromFile(Launcher.AW_DISCORD_AVATAR_ICON), 80), 100, round.getBackground())));
            bigImage.setIcon(new ImageIcon(Launcher.makeRoundedCorner(Scalr.resize(Launcher.getImageFromFile(Launcher.AW_DISCORD_BIGIMAGE_ICON), 80), 20, new Color(35, 39, 42))));
            smallImage.setIcon(new ImageIcon(Launcher.makeRoundedCorner(Scalr.resize(Launcher.getImageFromFile(Launcher.AW_DISCORD_SMALLIMAGE_ICON), 30), 100, new Color(35, 39, 42))));
            usernameLabel1.setText(DiscordManager.user.username);
            usernameLabel2.setText("#" + DiscordManager.user.discriminator);

            setBackground(HSLColor.getDominantColor(Launcher.getImageFromFile(Launcher.AW_DISCORD_AVATAR_ICON)));
            setForeground(getBackground());
        } catch (NullPointerException | IllegalArgumentException ignored) {}

        usernameLabel2.setBounds(Tab.getStringSize(usernameLabel1, usernameLabel1.getText()).width + 2 + usernameLabel1.getX(), usernameLabel1.getY(), 380 - Tab.getStringSize(usernameLabel1, usernameLabel1.getText()).width -5, 30);
    }

    private void updateGame() {
        try {
            DiscordRichPresence dRPC = DiscordManager.getRPC();
            ArrayList<JLabel> actualLabels = new ArrayList<>();

            actualLabels.add(appNameLabelGame);

            stateLabelGame.setVisible(false);
            detailsLabelGame.setVisible(false);
            if (Boolean.parseBoolean(ProfileSaver.globalSettingsSaver.get(ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWTHINGS.get()))) {
                smallImagePanelGame.setVisible(true);
                if (Boolean.parseBoolean(ProfileSaver.globalSettingsSaver.get(ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWACCOUNT.get()))) {
                    actualLabels.add(stateLabelGame);
                    stateLabelGame.setText(Launcher.parseUnicode("Connecté en tant que " + DiscordManager.user.username));
                    stateLabelGame.setVisible(true);

                    if (Boolean.parseBoolean(ProfileSaver.globalSettingsSaver.get(ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWDETECTEDSERVER.get()))) {
                        actualLabels.add(detailsLabelGame);
                        detailsLabelGame.setText("En jeu sur AstrauworldMC");
                        detailsLabelGame.setVisible(true);
                    } else {
                        actualLabels.add(detailsLabelGame);
                        detailsLabelGame.setText("En jeu...");
                        detailsLabelGame.setVisible(true);
                    }
                } else if (Boolean.parseBoolean(ProfileSaver.globalSettingsSaver.get(ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWDETECTEDSERVER.get()))) {
                    actualLabels.add(stateLabelGame);
                    stateLabelGame.setText("En jeu sur AstrauworldMC");
                    stateLabelGame.setVisible(true);
                } else {
                    actualLabels.add(stateLabelGame);
                    stateLabelGame.setText("En jeu...");
                    stateLabelGame.setVisible(true);
                }
            } else {
                smallImagePanelGame.setVisible(false);
            }

            if (Boolean.parseBoolean(ProfileSaver.globalSettingsSaver.get(ProfileSaver.KEY.GLOBALSETTINGS_DISCORD_SHOWTIME.get()))) {
                if (dRPC.startTimestamp != dRPC.endTimestamp) {
                    actualLabels.add(timeLabelGame);
                    long elapsed;
                    String elapsedStr;
                    if (dRPC.endTimestamp == 0) {
                        elapsed = System.currentTimeMillis() - dRPC.startTimestamp;

                        elapsedStr = getTime(elapsed, true) + "écoulées";
                    } else {
                        elapsed = dRPC.endTimestamp - System.currentTimeMillis();

                        elapsedStr = getTime(elapsed, true) + "restantes";
                    }

                    timeLabelGame.setText(Launcher.parseUnicode(elapsedStr));
                    timeLabelGame.setVisible(true);
                } else {
                    timeLabelGame.setVisible(false);
                }
            } else {
                timeLabelGame.setVisible(false);
            }

            int x = 98;
            int w = inside.getWidth() - 100;
            int h = 20;
            switch (actualLabels.size()) {
                case (4) -> {
                    actualLabels.get(0).setBounds(x, 78 + gapBetweenTheTwo, w, h);
                    actualLabels.get(1).setBounds(x, actualLabels.get(0).getY() + gap, w, h);
                    actualLabels.get(2).setBounds(x, actualLabels.get(1).getY() + gap, w, h);
                    actualLabels.get(3).setBounds(x, actualLabels.get(2).getY() + gap, w, h);
                }
                case (3) -> {
                    actualLabels.get(0).setBounds(x, 87 + gapBetweenTheTwo, w, h);
                    actualLabels.get(1).setBounds(x, actualLabels.get(0).getY() + gap, w, h);
                    actualLabels.get(2).setBounds(x, actualLabels.get(1).getY() + gap, w, h);
                }
                case (2) -> {
                    actualLabels.get(0).setBounds(x, 96 + gapBetweenTheTwo, w, h);
                    actualLabels.get(1).setBounds(x, actualLabels.get(0).getY() + gap, w, h);
                }
                case (1) -> actualLabels.get(0).setBounds(96, 105 + gapBetweenTheTwo, w, h);
            }
        } catch (NullPointerException ignored) {}

        try {
            bigImageGame.setIcon(new ImageIcon(Launcher.makeRoundedCorner(Scalr.resize(Launcher.getImageFromFile(Launcher.AW_DISCORD_BIGIMAGE_ICON), 80), 20, new Color(35, 39, 42))));
            smallImageGame.setIcon(new ImageIcon(Launcher.makeRoundedCorner(Scalr.resize(Launcher.getImageFromFile(Launcher.AW_DISCORD_SMALLIMAGE_ICON), 30), 100, new Color(35, 39, 42))));
        } catch (NullPointerException | IllegalArgumentException ignored) {}
    }

    public void setBounds(int x, int y) {
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(20,20);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //Draws the rounded opaque panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
    }

    public static String getTime(long millis, boolean raccourci) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1);

        if (seconds >= 60) {
            seconds = seconds - 60;
            minutes++;
        }

        if (minutes >= 60) {
            minutes = minutes - 60;
            hours++;
        }

        if (hours >= 24) {
            hours = hours - 24;
        }

        String finalStr = "";

        if (hours != 0) {
            if (raccourci) {
                finalStr = hours + " h ";
            } else {
                finalStr = hours + " heure(s) ";
            }
        }

        if (minutes != 0) {
            if (raccourci) {
                finalStr = finalStr + minutes + " min ";
            } else {
                finalStr = finalStr + minutes + " minute(s) ";
            }
        }

        if (seconds != 0) {
            if (raccourci) {
                finalStr = finalStr + seconds + " s ";
            } else {
                finalStr = finalStr + seconds + " seconde(s) ";
            }
        }

        return finalStr;
    }
}
