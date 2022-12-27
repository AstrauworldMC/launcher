package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.timutilslib.TimFilesUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class ShadersSwitchButton extends STexturedButton {

    private final BufferedImage textureOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png");
    private final BufferedImage textureOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_on.png");
    private final BufferedImage textureHoverOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_off.png");
    private final BufferedImage textureHoverOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_on.png");
    private final BufferedImage textureDisabledOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_off.png");
    private final BufferedImage textureDisabledOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_on.png");

    private Saver shaderOptionsSaver;
    private final String shaderResourceFolderUrl = "https://github.com/AstrauworldMC/resources/shaderpacks/";
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

        if (Objects.equals(selectedSaver.get(KEY.MOD_OPTIFINE), "true")) {
            this.setEnabled(true);
        } else {
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

    public void setOn() {
        initOptionSaver();

        shaderOptionsSaver.set(selectedShaderKey, shaderFileName);
        this.defineTextures();

        int i = 0;

        while (i != shadersButtonsList.length) {
            shadersButtonsList[i].setOn();
            shadersButtonsList[i].defineTextures();
            i += 1;
        }

    }

    public void installShader() throws IOException {
        initCustomFilesFolder(selectedSaver);

        TimFilesUtils.downloadFromInternet(shaderResourceFolderUrl + shaderFileName, new File(shaderpacksProfileFolder + System.getProperty("file.separator") + shaderFileName));
    }
}
