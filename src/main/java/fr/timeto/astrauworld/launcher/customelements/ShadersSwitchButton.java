package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.abstractcomponents.AbstractButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.timutilslib.TimFilesUtils;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.infosLabel;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class ShadersSwitchButton extends AbstractButton {

    private Saver shaderOptionsSaver;
    private final String shaderResourceFolderUrl = "https://github.com/AstrauworldMC/resources/blob/main/shaderpacks/";
    private final String selectedShaderKey = "shaderPack";

    private final String shaderFileName;

    private void initOptionSaver() {
        initCustomFilesFolder(getSelectedSaver());
        shaderOptionsSaver = new Saver(Paths.get(optionsShadersProfileTextfile.toString()));
    }

    public ShadersSwitchButton(Shader fileName) {
        super();
        shaderFileName = fileName.get();
    }

    public String getShaderFileName() {return shaderFileName;}

    public void defineTextures() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        initOptionSaver();

        if (Objects.equals(getSelectedSaver().get(KEY.MOD_OPTIFINE.get()), "true") && new File(shaderpacksProfileFolder + File.separator + shaderFileName).exists()) {
            this.setEnabled(true);
        } else {
            shaderOptionsSaver.set(selectedShaderKey, "");
            this.setEnabled(false);
        }

        if (Boolean.parseBoolean(shaderOptionsSaver.get(selectedShaderKey))) {
            if (!this.isEnabled()) {
                // true - disabled
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(77, 77, 77));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(59, 59, 59));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(30, 30, 30));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(46, 0, 28, 50);

                g2d.setColor(new Color(132, 132, 132));
                g2d.fillRect(49, 3, 22, 44);

                g2d.setColor(new Color(74, 74, 74));
                g2d.fillRect(52, 6, 19, 41);

                g2d.setColor(new Color(117, 117, 117));
                g2d.fillRect(52, 6, 16, 38);
            } else if (this.isHover()) {
                // true - hover
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 154));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 65));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.WHITE);
                g2d.fillRect(46, 0, 28, 50);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 77));
                g2d.fillRect(49, 3, 22, 44);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(52, 6, 19, 41);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 122));
                g2d.fillRect(52, 6, 16, 38);
            } else {
                // true
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(57, 57, 57));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(46, 46, 46));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(184, 184, 184));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(46, 0, 28, 50);

                g2d.setColor(Color.WHITE);
                g2d.fillRect(49, 3, 22, 44);

                g2d.setColor(new Color(109, 109, 109));
                g2d.fillRect(52, 6, 19, 41);

                g2d.setColor(new Color(180, 180, 180));
                g2d.fillRect(52, 6, 16, 38);
            }
        } else {
            if (!this.isEnabled()) {
                // false - disabled
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(77, 77, 77));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(59, 59, 59));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(30, 30, 30));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, 28, 50);

                g2d.setColor(new Color(132, 132, 132));
                g2d.fillRect(3, 3, 22, 44);

                g2d.setColor(new Color(74, 74, 74));
                g2d.fillRect(6, 6, 19, 41);

                g2d.setColor(new Color(117, 117, 117));
                g2d.fillRect(6, 6, 16, 38);
            } else if (this.isHover()) {
                // false - hover
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 154));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 65));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, 28, 50);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 77));
                g2d.fillRect(3, 3, 22, 44);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 141));
                g2d.fillRect(6, 6, 19, 41);

                g2d.setColor(HSLColor.getColorDarker(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), 122));
                g2d.fillRect(6, 6, 16, 38);
            } else {
                // false
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 7, 74, 36);

                g2d.setColor(new Color(57, 57, 57));
                g2d.fillRect(3, 10, 68, 30);

                g2d.setColor(new Color(46, 46, 46));
                g2d.fillRect(5, 12, 64, 26);

                g2d.setColor(new Color(184, 184, 184));
                g2d.fillRect(29, 22, 16, 6);


                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, 28, 50);

                g2d.setColor(Color.WHITE);
                g2d.fillRect(3, 3, 22, 44);

                g2d.setColor(new Color(109, 109, 109));
                g2d.fillRect(6, 6, 19, 41);

                g2d.setColor(new Color(180, 180, 180));
                g2d.fillRect(6, 6, 16, 38);
            }
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        if(aFlag) {
            defineTextures();
            super.setVisible(aFlag);
        } else {
            super.setVisible(aFlag);
        }
    }

    public void setBounds(int x, int y) {
        this.setBounds(x, y, 74, 50);
    }

    // FIXME TOGGLE MARCHE PLUS
    public void toggle() {
        initOptionSaver();

        if (!Objects.equals(shaderOptionsSaver.get(selectedShaderKey), shaderFileName)) {
            shaderOptionsSaver.set(selectedShaderKey, shaderFileName);
        } else {
            shaderOptionsSaver.set(selectedShaderKey, "");
        }
        this.repaint();

        int i = 0;

        ArrayList<ShadersSwitchButton> enabledShadersButtonsList = new ArrayList<>();
        while (i != shadersButtonsList.toArray().length) {
            if (shadersButtonsList.toArray(new ShadersSwitchButton[0])[i].isEnabled()) {
                enabledShadersButtonsList.add(shadersButtonsList.toArray(new ShadersSwitchButton[0])[i]);
            }
            i++;
        }

        i = 0;
        while (i != enabledShadersButtonsList.toArray().length) {
            enabledShadersButtonsList.toArray(new ShadersSwitchButton[0])[i].repaint();
            i++;
        }
        this.repaint();
    }

    public Thread installShader() {
        initCustomFilesFolder(getSelectedSaver());
        if (LauncherPanel.inDownload) {
            LauncherPanel.inDownloadError();
            return null;
        }
        Thread t = new Thread(() -> {
            LauncherPanel.inDownload = true;
            loadingBar.setValue(0);
            loadingBar.setVisible(true);
            barLabel.setText(shaderFileName);
            infosLabel.setText("T\u00e9l\u00e9chargement du shader");
            infosLabel.setVisible(true);
            barLabel.setVisible(true);
            percentLabel.setVisible(true);
            infosLabel.setVisible(true);
            try {
                if (!shaderpacksProfileFolder.exists()) shaderpacksProfileFolder.mkdir();
                TimFilesUtils.downloadFromInternet(shaderResourceFolderUrl + shaderFileName + "?raw=true", new File(shaderpacksProfileFolder + File.separator + shaderFileName), loadingBar, percentLabel);
                Thread.sleep(1500);
                loadingBar.setVisible(false);
                infosLabel.setVisible(false);
                barLabel.setVisible(false);
                percentLabel.setVisible(false);
                infosLabel.setVisible(false);
                defineTextures();
                LauncherPanel.inDownload = false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        return t;
    }
}
