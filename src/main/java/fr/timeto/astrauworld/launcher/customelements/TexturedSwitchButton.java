package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.openlauncherlib.util.Saver;
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
    private final String saverKeyStr;
    private final KEY saverKey;
    private final boolean global;

    public BufferedImage getTexture() {
        if (getSelectedSaver().get(saverKeyStr).contains("true")) {
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
        try {
            if (getSelectedSaver().get(saverKeyStr).contains("true")) {
                super.setTexture(textureOn);
                super.setTextureHover(textureHoverOn);
                super.setTextureDisabled(textureDisabledOn);
            } else {
                super.setTexture(textureOff);
                super.setTextureHover(textureHoverOff);
                super.setTextureDisabled(textureDisabledOff);
            }
        } catch (NullPointerException e) {
            getSelectedSaver().set(saverKeyStr, saverKey.getDefaultValue());
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

    @Override
    public void setEnabled(boolean aFlag) {
        String value = getSelectedSaver().get(saverKeyStr);

        if(aFlag) {
            if (Objects.equals(value, "trueDisabled")) {
                getSelectedSaver().set(saverKeyStr, "false");
            } else if (Objects.equals(value, "falseDisabled")) {
                getSelectedSaver().set(saverKeyStr, "true");
            }
            super.setEnabled(aFlag);
        } else {
            if (Objects.equals(value, "true")) {
                getSelectedSaver().set(saverKeyStr, "falseDisabled");
            } else if (Objects.equals(value, "false")) {
                getSelectedSaver().set(saverKeyStr, "trueDisabled");
            }
            super.setEnabled(aFlag);
        }
    }

    public void toggleButton() {
        String value = getSelectedSaver().get(saverKeyStr);

        Saver saverNotSelect1 = null;
        Saver saverNotSelect2 = null;
        if (getSelectedSaver() == firstProfileSaver) {
            saverNotSelect1 = secondProfileSaver;
            saverNotSelect2 = thirdProfileSaver;
        } else if (getSelectedSaver() == secondProfileSaver) {
            saverNotSelect1 = firstProfileSaver;
            saverNotSelect2 = thirdProfileSaver;
        } else if (getSelectedSaver() == thirdProfileSaver) {
            saverNotSelect1 = firstProfileSaver;
            saverNotSelect2 = secondProfileSaver;
        }

        if (Objects.equals(value, "true")) {
            getSelectedSaver().set(this.getSaverKey(), "false");
            if (global) {firstProfileSaver.set(this.getSaverKey(), "true");}
        } else if (Objects.equals(value, "false")) {
            getSelectedSaver().set(this.getSaverKey(), "true");
            if (global) {
                saverNotSelect1.set(this.getSaverKey(), "false");
                saverNotSelect2.set(this.getSaverKey(), "false");
            }
        }
        defineTextures();
    }

}
