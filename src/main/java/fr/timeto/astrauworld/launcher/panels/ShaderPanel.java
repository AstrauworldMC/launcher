package fr.timeto.astrauworld.launcher.panels;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.ShadersDownloadButton;
import fr.timeto.astrauworld.launcher.customelements.ShadersSwitchButton;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.util.NoSuchElementException;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

public class ShaderPanel extends JPanel implements SwingerEventListener {

    private final boolean isLink;
    private final String name;
    private final String fileName;
    private final PageName toPage;

    private final ShadersSwitchButton switchButton;
    private final ShadersDownloadButton downloadButton;
    private final STexturedButton plusButton;
    private final JLabel shaderNameLabel;

    public ShaderPanel(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
        this.toPage = null;

        CustomFonts.initFonts();

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(400, 60));
        setBackground(Swinger.getTransparentWhite(10));

        isLink = false;

        switchButton = new ShadersSwitchButton(fileName);
        downloadButton = new ShadersDownloadButton(switchButton);
        plusButton = null;
        shaderNameLabel = new JLabel(name, SwingConstants.RIGHT);

        ProfileSaver.shadersButtonsList.add(switchButton);

        switchButton.setBounds(297, 5);
        switchButton.addEventListener(this);
        add(switchButton);

        downloadButton.setBounds(213, 44);
        downloadButton.addEventListener(this);
        add(downloadButton);

        shaderNameLabel.setBounds(0, 21, 270, 24);
        shaderNameLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(22f));
        shaderNameLabel.setForeground(Color.WHITE);
        add(shaderNameLabel);
    }

    public ShaderPanel(String displayed, PageName toPage) {
        this.name = displayed;
        this.fileName = null;
        this.toPage = toPage;

        CustomFonts.initFonts();

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(400, 60));
        setBackground(Swinger.getTransparentWhite(10));

        isLink = true;

        switchButton = null;
        downloadButton = null;
        plusButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-hover.png"));
        shaderNameLabel = new JLabel(name, SwingConstants.RIGHT);

        plusButton.setBounds(314, 14);
        plusButton.addEventListener(this);
        add(plusButton);

        shaderNameLabel.setBounds(0, 21, 270, 24);
        shaderNameLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(22f));
        shaderNameLabel.setForeground(Color.WHITE);
        add(shaderNameLabel);

    }

    public void setBounds(int x, int y) {
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        if (isLink) {
            throw new NoSuchElementException("Le panel est un lien");
        }
        return fileName;
    }

    public ShadersSwitchButton getSwitchButton() {
        if (isLink) {
            throw new NoSuchElementException("Le panel est un lien");
        }
        return switchButton;
    }

    public ShadersDownloadButton getDownloadButton() {
        if (isLink) {
            throw new NoSuchElementException("Le panel est un lien");
        }
        return downloadButton;
    }

    public STexturedButton getPlusButton() {
        if (!isLink) {
            throw new NoSuchElementException("Le panel n'est pas un lien");
        }
        return plusButton;
    }

    public boolean isLink() {
        return isLink;
    }

    public PageName getToPage() {
        if (!isLink) {
            throw new NoSuchElementException("Le panel n'est pas un lien");
        }
        return toPage;
    }

    public void setVisible(boolean aFlag) {
        if (aFlag) {
            if (!isLink) {
                switchButton.defineTextures();
                downloadButton.defineTextures();
            }
        }
        super.setVisible(aFlag);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == switchButton) {
            switchButton.toggle();
        } else if (e.getSource() == downloadButton) {
            downloadButton.installShader();
        } else if (e.getSource() == plusButton) {
            PageChange.setPage(true, toPage, ProfileSaver.getSelectedProfile());
        }
    }
}
