package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class ModPanel extends JPanel implements SwingerEventListener {

    private final String name;
    private final ProfileSaver.KEY key;
    private final URL url;

    private final JLabel nameLabel;
    private final TexturedSwitchButton switchButton;
    private final STexturedButton moreInfosButton;

    public ModPanel(String name, ProfileSaver.KEY key, String moreInfosUrl) {
        this.name = name;
        this.key = key;
        try {
            this.url = new URL(moreInfosUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(400, 60));
        setBackground(Swinger.getTransparentWhite(10));

        switchButton = new TexturedSwitchButton(key, false);
        moreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-hover.png"));
        nameLabel = new JLabel(name, SwingConstants.RIGHT);

        switchButton.setBounds(297, 5);
        switchButton.addEventListener(this);
        add(switchButton);

        moreInfosButton.setBounds(199, 36);
        moreInfosButton.addEventListener(this);
        add(moreInfosButton);

        nameLabel.setBounds(0, 8, 280, 24);
        nameLabel.setFont(CustomFonts.robotoBlackFont.deriveFont(22f));
        nameLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        add(nameLabel);
    }

    public void setBounds(int x, int y) {
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
    }

    public void setVisible(boolean aFlag) {
        if (aFlag) {
            switchButton.defineTextures();
        }
        super.setVisible(aFlag);
    }

    public void defineTextures() {
        switchButton.defineTextures();
    }

    public void recolor() {
        nameLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
    }

    public String getName() {
        return name;
    }

    public ProfileSaver.KEY getKey() {
        return key;
    }

    public URL getUrl() {
        return url;
    }

    public TexturedSwitchButton getSwitchButton() {
        return switchButton;
    }

    public STexturedButton getMoreInfosButton() {
        return moreInfosButton;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == switchButton) {
            switchButton.toggleButton();
        } else if (e.getSource() == moreInfosButton) {
            try {
                Desktop.getDesktop().browse(url.toURI());
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
