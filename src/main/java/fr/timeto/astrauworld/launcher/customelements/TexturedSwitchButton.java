package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.image.BufferedImage;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class TexturedSwitchButton extends STexturedButton {
    private final BufferedImage textureOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png");
    private final BufferedImage textureOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_on.png");
    private final BufferedImage textureHoverOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_off.png");
    private final BufferedImage textureHoverOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_on.png");
    private final BufferedImage textureDisabledOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_off.png");
    private final BufferedImage textureDisabledOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_on.png");
    private final String saverKeyStr;
    private final KEY saverKey;
    private final boolean global;

    public BufferedImage getTexture() {
        Saver saver;
        if (global) {
            saver = globalSettingsSaver;
        } else saver = getSelectedSaver();

        if (Boolean.parseBoolean(saver.get(saverKeyStr))) {
            return(textureOn);
        }
        return textureOff;
    }

    public BufferedImage getTextureHover() {
        if (getSelectedSaver().get(saverKeyStr).contains("true")) {
            return(textureHoverOn);
        }
        return textureHoverOff;
    }

    public BufferedImage getTextureDisabled() {
        if (getSelectedSaver().get(saverKeyStr).contains("true")) {
            return(textureDisabledOn);
        }
        return textureDisabledOff;
    }

    public void defineTextures() {
        Saver saver;
        if (global) {
            saver = globalSettingsSaver;
        } else saver = getSelectedSaver();

        try {
            if (Boolean.parseBoolean(saver.get(saverKeyStr))) {
                super.setTexture(textureOn);
                super.setTextureHover(textureHoverOn);
                super.setTextureDisabled(textureDisabledOn);
            } else {
                super.setTexture(textureOff);
                super.setTextureHover(textureHoverOff);
                super.setTextureDisabled(textureDisabledOff);
            }
        } catch (NullPointerException e) {
            saver.set(saverKeyStr, saverKey.getDefaultValue());
            defineTextures();
        }
    }

    public String getSaverKey() {
        return saverKeyStr;
    }

    public KEY getKey() {
        return saverKey;
    }

    public TexturedSwitchButton(KEY saverKey, boolean global) {
        super(getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
        this.saverKeyStr = saverKey.get();
        this.saverKey = saverKey;
        this.global = global;

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

    public void toggleButton() {
        String value;

        if (global) {
            value = globalSettingsSaver.get(saverKeyStr);
            globalSettingsSaver.set(getSaverKey(), String.valueOf(!Boolean.parseBoolean(value)));
        } else {
            value = getSelectedSaver().get(saverKeyStr);
            getSelectedSaver().set(getSaverKey(), String.valueOf(!Boolean.parseBoolean(value)));
        }
        defineTextures();
    }

}
