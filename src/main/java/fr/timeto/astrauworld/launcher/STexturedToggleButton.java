package fr.timeto.astrauworld.launcher;

import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.image.BufferedImage;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.ProfileSaver.*;

public class STexturedToggleButton extends STexturedButton {
    private final BufferedImage textureOff = getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png");
    private final BufferedImage textureOn = getResourceIgnorePath("/commonButtons/toggleButton-normal_on.png");
    private final BufferedImage textureHoverOff = getResourceIgnorePath("/commonButtons/toggleButton-hover_off.png");
    private final BufferedImage textureHoverOn = getResourceIgnorePath("/commonButtons/toggleButton-hover_on.png");
    private final BufferedImage textureDisabledOff = getResourceIgnorePath("/commonButtons/toggleButton-disabled_off.png");
    private final BufferedImage textureDisabledOn = getResourceIgnorePath("/commonButtons/toggleButton-disabled_on.png");
    private final String saverKey;

    public BufferedImage getTexture() {
        if (selectedSaver.get(saverKey).contains("true")) {
            return(textureOn);
        }
        return textureOff;
    }

    public BufferedImage getTextureHover() {
        if (selectedSaver.get(saverKey).contains("true")) {
            return(textureHoverOn);
        }
        return textureHoverOff;
    }

    public BufferedImage getTextureDisabled() {
        if (selectedSaver.get(saverKey).contains("true")) {
            return(textureDisabledOn);
        }
        return textureDisabledOff;
    }

    public void defineTextures() {
        if (selectedSaver.get(saverKey).contains("true")) {
            super.setTexture(textureOn);
            super.setTextureHover(textureHoverOn);
            super.setTextureDisabled(textureDisabledOn);
        } else {
            super.setTexture(textureOff);
            super.setTextureHover(textureHoverOff);
            super.setTextureDisabled(textureDisabledOff);
        }
    }

    public String getSaverKey() {
        return saverKey;
    }

    public STexturedToggleButton(String saverKey, BufferedImage texture, BufferedImage textureHover, BufferedImage textureDisabled) {
        super(texture, textureHover, textureDisabled);
        this.saverKey = saverKey;
        initSelectedSaver();

    }

    public void setVisible(boolean aFlag) {
        if(aFlag) {
            initSelectedSaver();
            defineTextures();
            super.setVisible(aFlag);
        } else {
            super.setVisible(aFlag);
        }
    }

    public void setEnabled(boolean aFlag) {
        initSelectedSaver();
        String value = selectedSaver.get(saverKey);

        if(aFlag) {
            if (Objects.equals(value, "trueDisabled")) {
                selectedSaver.set(saverKey, "false");
            } else if (Objects.equals(value, "falseDisabled")) {
                selectedSaver.set(saverKey, "true");
            }
            super.setEnabled(aFlag);
        } else {
            if (Objects.equals(value, "true")) {
                selectedSaver.set(saverKey, "falseDisabled");
            } else if (Objects.equals(value, "false")) {
                selectedSaver.set(saverKey, "trueDisabled");
            }
            super.setEnabled(aFlag);
        }
    }

    public void toggleButton() {
        initSelectedSaver();
        String value = selectedSaver.get(saverKey);

        if (Objects.equals(value, "true")) {
            selectedSaver.set(saverKey, "false");
        } else if (Objects.equals(value, "false")) {
            selectedSaver.set(saverKey, "true");
        }
        defineTextures();
    }

}
