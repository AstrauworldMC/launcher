package fr.timeto.astrauworld.launcher.panels;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.customelements.ShadersDownloadButton;
import fr.timeto.astrauworld.launcher.customelements.ShadersSwitchButton;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;

public class ShaderPanel extends JPanel implements SwingerEventListener {

    private final String name;
    private final String fileName;

    private final ShadersSwitchButton switchButton;
    private final ShadersDownloadButton downloadButton;
    private final JLabel shaderNameLabel;

    public ShaderPanel(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;

        CustomFonts.initFonts();

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(400, 60));
        setBackground(Swinger.getTransparentWhite(10));

        switchButton = new ShadersSwitchButton(fileName);
        downloadButton = new ShadersDownloadButton(switchButton);
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

    public void setBounds(int x, int y) {
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public ShadersSwitchButton getSwitchButton() {
        return switchButton;
    }

    public ShadersDownloadButton getDownloadButton() {
        return downloadButton;
    }

    public void setVisible(boolean aFlag) {
        if (aFlag) {
            switchButton.defineTextures();
            downloadButton.defineTextures();
        }
        super.setVisible(aFlag);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == switchButton) {
            switchButton.toggle();
        } else if (e.getSource() == downloadButton) {
            downloadButton.installShader();
        }
    }
}
