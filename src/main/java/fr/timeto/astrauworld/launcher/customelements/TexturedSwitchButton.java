package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.image.BufferedImage;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class TexturedSwitchButton extends STexturedButton {
    private final BufferedImage textureOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png");
    private final BufferedImage textureOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_on.png");
    private final BufferedImage textureHoverOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_off.png");
    private final BufferedImage textureHoverOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-hover_on.png");
    private final BufferedImage textureDisabledOff = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_off.png");
    private final BufferedImage textureDisabledOn = getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-disabled_on.png");
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

    private void defineTextures() {
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

    public TexturedSwitchButton(String saverKey, BufferedImage texture) {
        super(texture);
        this.saverKey = saverKey;

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

    @Override
    public void setEnabled(boolean aFlag) {
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
        String value = selectedSaver.get(saverKey);

        if (Objects.equals(value, "true")) {
            selectedSaver.set(saverKey, "false");
        } else if (Objects.equals(value, "false")) {
            selectedSaver.set(saverKey, "true");
        }
        defineTextures();
    }

}
