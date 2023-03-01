package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.image.BufferedImage;
import java.io.File;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class ShadersDownloadButton extends STexturedButton {

    private final ShadersSwitchButton shadersSwitchButton;

    private final BufferedImage downloadNormal = getResourceIgnorePath("/assets/launcher/profilesPage/addons/download-normal.png");
    private final BufferedImage downloadHover = getResourceIgnorePath("/assets/launcher/profilesPage/addons/download-hover.png");
    private final BufferedImage downloadedNormal = getResourceIgnorePath("/assets/launcher/profilesPage/addons/downloaded-normal.png");
    private final BufferedImage downloadedHover = getResourceIgnorePath("/assets/launcher/profilesPage/addons/downloaded-hover.png");

    private boolean downloaded = false;

    public ShadersDownloadButton(ShadersSwitchButton shadersSwitchButton) {
        super(getResourceIgnorePath("/assets/launcher/profilesPage/addons/download-normal.png"));
        this.shadersSwitchButton = shadersSwitchButton;
        defineTextures();
    }

    public void defineTextures() {
        initCustomFilesFolder(selectedSaver);
        if (new File(shaderpacksProfileFolder + File.separator + shadersSwitchButton.getShaderFileName()).exists()) {
            this.setTexture(downloadedNormal);
            this.setTextureHover(downloadedHover);
            if (!downloaded) {
                this.setBounds(getX() - 17, getY(), 74, 16);
            }
            downloaded = true;
        } else {
            this.setTexture(downloadNormal);
            this.setTextureHover(downloadHover);
            if (downloaded) {
                this.setBounds(getX() + 17, getY(), 57, 16);
            }
            downloaded = false;
        }
    }

    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        this.defineTextures();
    }

    public void installShader() {
        Thread t = new Thread(() -> {
            Thread tt = shadersSwitchButton.installShader();
            try {
                tt.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            boolean actualState = this.isVisible();
            this.setVisible(!actualState);
            this.setVisible(actualState);
        });
        t.start();
    }
}
