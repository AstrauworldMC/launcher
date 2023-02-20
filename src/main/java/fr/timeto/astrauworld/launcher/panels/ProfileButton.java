package fr.timeto.astrauworld.launcher.panels;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.timutilslib.CustomFonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.setProfilePage;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static fr.timeto.timutilslib.CustomFonts.kollektifBoldFont;
import static fr.timeto.timutilslib.CustomFonts.kollektifFont;

public class ProfileButton extends JPanel implements SwingerEventListener {

    private final Saver saver;
    private File profileIcon;
    private int profileNumber;

    private final SColoredButton profileButton = new SColoredButton(
            Swinger.getTransparentWhite(0),
            Swinger.getTransparentWhite(20),
            Swinger.getTransparentWhite(0)) {
        @Override
        public void setEnabled(boolean enabled) {
            if (enabled) {
                profileNumberLabel.setFont(kollektifFont.deriveFont(19f));
            } else {
                profileNumberLabel.setFont(kollektifBoldFont.deriveFont(19f));
            }
            super.setEnabled(enabled);
        }
    };
    private final JLabel profileIconLabel = new JLabel();
    private final JLabel profileNameLabel = new JLabel();
    private final JLabel profileNumberLabel = new JLabel();

    public ProfileButton(Saver saver) {
        this.saver = saver;
        setLayout(null);
        CustomFonts.initFonts();

        if (Objects.equals(saver, firstProfileSaver)) {
            profileIcon = Launcher.AW_FIRSTPROFILE_ICON;
            profileNumber = 1;
        } else if (Objects.equals(saver, secondProfileSaver)) {
            profileIcon = Launcher.AW_SECONDPROFILE_ICON;
            profileNumber = 2;
        } else if (Objects.equals(saver, thirdProfileSaver)) {
            profileIcon = Launcher.AW_THIRDPROFILE_ICON;
            profileNumber = 3;
        }

        setSize(178, 59);
        setOpaque(false);

        profileIconLabel.setBounds(15, 13, 35, 35);
        profileIconLabel.setIcon(new ImageIcon(Objects.requireNonNull(getProfileIcon(profileIcon))));
        add(profileIconLabel);

        profileNameLabel.setBounds(61, 15, 80, 12);
        profileNameLabel.setForeground(Color.WHITE);
        profileNameLabel.setFont(kollektifBoldFont.deriveFont(13f));
        profileNameLabel.setText(saver.get(KEY.SETTINGS_PROFILENAME));
        add(profileNameLabel);

        profileNumberLabel.setBounds(61, 30, 80, 20);
        profileNumberLabel.setForeground(Color.WHITE);
        profileNumberLabel.setFont(kollektifBoldFont.deriveFont(19f));
        profileNumberLabel.setText("Profil " + profileNumber);
        add(profileNumberLabel);

        profileButton.setBounds(0,0,178,59);
        profileButton.addEventListener(this);
        add(profileButton);

    }

    public void initButton() {
        profileIconLabel.setIcon(new ImageIcon(Objects.requireNonNull(getProfileIcon(profileIcon))));
        profileNameLabel.setText(saver.get(KEY.SETTINGS_PROFILENAME));
        profileNumberLabel.setText("Profil " + profileNumber);
    }


    public static BufferedImage getProfileIcon(File file) {
        // This time, you can use an InputStream to load
        try {
            // Grab the InputStream for the image.
            InputStream in = new FileInputStream(file);

            // Then read it.
            return ImageIO.read(in);
        } catch (IOException e) {
            Launcher.println("The image was not loaded.");
        }

        return null;
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        setProfilePage(true, profileNumber + "", PageChange.TAB_KEY.profileHome);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(15,15); //Border corners arcs {width,height}, change this to whatever you want
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //Draws the rounded panel with borders.
        graphics.setColor(Swinger.getTransparentWhite(0));
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
        graphics.setColor(Swinger.getTransparentWhite(0));
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
    }

    public SColoredButton getProfileButton() {
        return profileButton;
    }

    public JLabel getProfileIconLabel() {
        return profileIconLabel;
    }

    public JLabel getProfileNameLabel() {
        return profileNameLabel;
    }

    public JLabel getProfileNumberLabel() {
        return profileNumberLabel;
    }
}
