package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.timutilslib.TimFilesUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.infosLabel;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class ShadersSwitchButton extends STexturedButton {

    private final BufferedImage textureOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png");
    private final BufferedImage textureOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_on.png");
    private final BufferedImage textureHoverOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_off.png");
    private final BufferedImage textureHoverOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_on.png");
    private final BufferedImage textureDisabledOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_off.png");
    private final BufferedImage textureDisabledOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_on.png");

    private Saver shaderOptionsSaver;
    private final String shaderResourceFolderUrl = "https://github.com/AstrauworldMC/resources/blob/main/shaderpacks/";
    private final String selectedShaderKey = "shaderPack";

    private final String shaderFileName;

    private void initOptionSaver() {
        initCustomFilesFolder(selectedSaver);
        shaderOptionsSaver = new Saver(Paths.get(optionsShadersProfileTextfile.toString()));
    }

    public ShadersSwitchButton(String fileName, BufferedImage texture) {
        super(texture);
        shaderFileName = fileName;
    }

    public BufferedImage getTexture() {
        initOptionSaver();
        if (Objects.equals(shaderOptionsSaver.get(selectedShaderKey), shaderFileName)) {
            return(textureOn);
        }
        return textureOff;
    }

    public BufferedImage getTextureHover() {
        initOptionSaver();
        if (Objects.equals(shaderOptionsSaver.get(selectedShaderKey), shaderFileName)) {
            return(textureHoverOn);
        }
        return textureHoverOff;
    }

    public BufferedImage getTextureDisabled() {
        initOptionSaver();
        if (Objects.equals(shaderOptionsSaver.get(selectedShaderKey), shaderFileName)) {
            return(textureDisabledOn);
        }
        return textureDisabledOff;
    }

    public String getShaderFileName() {return shaderFileName;}

    private void defineTextures() {
        initOptionSaver();
        if (Objects.equals(shaderOptionsSaver.get(selectedShaderKey), shaderFileName)) {
            super.setTexture(textureOn);
            super.setTextureHover(textureHoverOn);
            super.setTextureDisabled(textureDisabledOn);
        } else {
            super.setTexture(textureOff);
            super.setTextureHover(textureHoverOff);
            super.setTextureDisabled(textureDisabledOff);
        }

        if (Objects.equals(selectedSaver.get(KEY.MOD_OPTIFINE), "true") && new File(shaderpacksProfileFolder + System.getProperty("file.separator") + shaderFileName).exists()) {
            this.setEnabled(true);
        } else {
            shaderOptionsSaver.set(selectedShaderKey, "");
            super.setTextureDisabled(textureDisabledOff);
            this.setEnabled(false);
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

    public void toggle() {
        initOptionSaver();

        if (!Objects.equals(shaderOptionsSaver.get(selectedShaderKey), shaderFileName)) {
            shaderOptionsSaver.set(selectedShaderKey, shaderFileName);
        } else {
            shaderOptionsSaver.set(selectedShaderKey, "");
        }
        this.defineTextures();

        int i = 0;

        while (i != shadersButtonsList.length) {
            shadersButtonsList[i].defineTextures();
            i += 1;
        }

    }

    public void installShader() {
        initCustomFilesFolder(selectedSaver);

        Thread t = new Thread(() -> {
            loadingBar.setValue(0);
            loadingBar.setVisible(true);
            barLabel.setText(shaderFileName);
            percentLabel.setText("0%");
            infosLabel.setText("T\u00e9l\u00e9chargement du shader");
            infosLabel.setVisible(true);
            barLabel.setVisible(true);
            percentLabel.setVisible(true);
            infosLabel.setVisible(true);
            try {
                TimFilesUtils.downloadFromInternet(shaderResourceFolderUrl + shaderFileName + "?raw=true", new File(shaderpacksProfileFolder + System.getProperty("file.separator") + shaderFileName));
                loadingBar.setValue(100);
                percentLabel.setText("100%");
                Thread.sleep(1500);
                loadingBar.setVisible(false);
                infosLabel.setVisible(false);
                barLabel.setVisible(false);
                percentLabel.setVisible(false);
                infosLabel.setVisible(false);
                defineTextures();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
    }
}
