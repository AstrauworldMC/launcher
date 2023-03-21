package fr.timeto.astrauworld.launcher.panels.profile;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.ModPanel;
import fr.timeto.astrauworld.launcher.customelements.ShaderPanel;
import fr.timeto.astrauworld.launcher.customelements.TexturedSwitchButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.setPage;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

public class ProfileAddonsPage extends PageCreator implements SwingerEventListener {

    public STexturedButton shadersButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/shadersButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/shadersButton-hover.png"));
    public STexturedButton resourcePacksButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/resourcePacksButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/resourcePacksButton-hover.png"));
    public STexturedButton modsButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/modsButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/modsButton-hover.png"));
    public STexturedButton goToFolderButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/goToFolder-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/goToFolder-hover.png"));
    public TexturedSwitchButton optifineSwitchButton = new TexturedSwitchButton(ProfileSaver.KEY.MOD_OPTIFINE, false);
    public final JLabel optifineLabel = new JLabel("Optifine");
    public final JTextArea optifineNeededLabel = new JTextArea("Obligatoire pour les shaders");


    public final ModPanel modsFpsmodelPanel = new ModPanel("First Person Model", ProfileSaver.KEY.MOD_FPSMODEL, "https://www.curseforge.com/minecraft/mc-mods/first-person-model");
    public final ModPanel modsBettertpsPanel = new ModPanel("Better First Person", ProfileSaver.KEY.MOD_BETTERTPS, "https://www.curseforge.com/minecraft/mc-mods/better-third-person");
    public final ModPanel modsFallingleavesPanel = new ModPanel("Falling Leaves", ProfileSaver.KEY.MOD_FALLINGLEAVES, "https://www.curseforge.com/minecraft/mc-mods/falling-leaves-forge");
    public final ModPanel modsAppleskinPanel = new ModPanel("Apple Skin", ProfileSaver.KEY.MOD_APPLESKIN, "https://www.curseforge.com/minecraft/mc-mods/appleskin");
    public final ModPanel modsSoundphysicsPanel = new ModPanel("Sound Physics Remastered", ProfileSaver.KEY.MOD_SOUNDPHYSICS, "https://www.curseforge.com/minecraft/mc-mods/sound-physics-remastered");
    public final ModPanel modsWaveyCapesPanel = new ModPanel("Wavey Capes", ProfileSaver.KEY.MOD_WAVEYCAPES, "https://www.curseforge.com/minecraft/mc-mods/waveycapes");
    public final ModPanel mods3dSkinLayersPanel = new ModPanel("3D Skin Layers", ProfileSaver.KEY.MOD_3DSKINLAYERS, "https://www.curseforge.com/minecraft/mc-mods/skin-layers-3d");

    public final STexturedButton shadersSeeComparisonButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/seeComparison-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/seeComparison-hover.png"));
    public final ShaderPanel shadersChocapicV6PlusButton = new ShaderPanel("Chocapic V6", PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV6);
    public final ShaderPanel shadersChocapicV7_1PlusButton = new ShaderPanel("Chocapic V7.1", PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV7);
    public final ShaderPanel shadersChocapicV9PlusButton = new ShaderPanel("Chocapic V9", PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV9);
    public final ShaderPanel shadersSeusRenewedPanel = new ShaderPanel("SEUS Renewed", ProfileSaver.Shader.SEUS_RENEWED);

    public final ShaderPanel shadersChocapicV6LitePanel = new ShaderPanel("Chocapic13 V6 Lite", ProfileSaver.Shader.CHOCAPICV6_LITE);
    public final ShaderPanel shadersChocapicV6LowPanel = new ShaderPanel("Chocapic13 V6 Low", ProfileSaver.Shader.CHOCAPICV6_LOW);
    public final ShaderPanel shadersChocapicV6MediumPanel = new ShaderPanel("Chocapic13 V6 Medium", ProfileSaver.Shader.CHOCAPICV6_MEDIUM);
    public final ShaderPanel shadersChocapicV6UltraPanel = new ShaderPanel("Chocapic13 V6 Ultra", ProfileSaver.Shader.CHOCAPICV6_ULTRA);
    public final ShaderPanel shadersChocapicV6ExtremePanel = new ShaderPanel("Chocapic13 V6 Extreme", ProfileSaver.Shader.CHOCAPICV6_EXTREME);

    public final ShaderPanel shadersChocapicV7_1ToasterPanel = new ShaderPanel("V7.1.1 Toaster Edition", ProfileSaver.Shader.CHOCAPICV7_1_TOASTER);
    public final ShaderPanel shadersChocapicV7_1LitePanel = new ShaderPanel("Chocapic13 V7.1 Lite", ProfileSaver.Shader.CHOCAPICV7_1_LITE);
    public final ShaderPanel shadersChocapicV7_1LowPanel = new ShaderPanel("Chocapic13 V7.1 Low", ProfileSaver.Shader.CHOCAPICV7_1_LOW);
    public final ShaderPanel shadersChocapicV7_1MediumPanel = new ShaderPanel("Chocapic13 V7.1 Medium", ProfileSaver.Shader.CHOCAPICV7_1_MEDIUM);
    public final ShaderPanel shadersChocapicV7_1UltraPanel = new ShaderPanel("Chocapic13 V7.1 Ultra", ProfileSaver.Shader.CHOCAPICV7_1_ULTRA);
    public final ShaderPanel shadersChocapicV7_1ExtremePanel = new ShaderPanel("Chocapic13 V7.1 Extreme", ProfileSaver.Shader.CHOCAPICV7_1_EXTREME);

    public final ShaderPanel shadersChocapicV9LowPanel = new ShaderPanel("Chocapic13 V9 Low", ProfileSaver.Shader.CHOCAPICV9_LOW);
    public final ShaderPanel shadersChocapicV9MediumPanel = new ShaderPanel("Chocapic13 V9 Medium", ProfileSaver.Shader.CHOCAPICV9_MEDIUM);
    public final ShaderPanel shadersChocapicV9HighPanel = new ShaderPanel("Chocapic13 V9 High", ProfileSaver.Shader.CHOCAPICV9_HIGH);
    public final ShaderPanel shadersChocapicV9ExtremePanel = new ShaderPanel("Chocapic13 V9 Extreme", ProfileSaver.Shader.CHOCAPICV9_EXTREME);
    public final ShaderPanel shadersChocapicV9_1ExtremePanel = new ShaderPanel("V9.1 Extreme Beta 5", ProfileSaver.Shader.CHOCAPICV9_1_EXTREMEBETA5);

    private final ArrayList<ModPanel> modPanelsList = new ArrayList<>();
    private final ArrayList<ShaderPanel> shaderPanelsList = new ArrayList<>();

    public final JLabel accountLabel = new JLabel("", SwingConstants.LEFT);
    public final JLabel accountConnectedLabel = new JLabel("Connecté en tant que: ", SwingConstants.LEFT);

    public ProfileAddonsPage(PageName pageName) {
        super(pageName, "Profil " + ProfileSaver.getSelectedProfile(), "Addons");

        setLayout(null);
        setOpaque(false);

        resourcePacksButton.setBounds(557, 15);
        resourcePacksButton.addEventListener(this);
        add(resourcePacksButton);

        optifineSwitchButton.setBounds(224, 9);
        optifineSwitchButton.addEventListener(this);
        add(optifineSwitchButton);

        optifineLabel.setForeground(Color.WHITE);
        optifineLabel.setFont(modsFpsmodelPanel.getNameLabel().getFont().deriveFont(24f));
        optifineLabel.setBounds(89, 26, 91, 24);
        add(optifineLabel);

        if (Objects.equals(pageName.getSubTab3(), PageName.PROFILE_ADDONS_MODS.getSubTab3())) {
            setSubtitle("Addons - Mods");

            shadersButton.setBounds(388, 15);
            shadersButton.addEventListener(this);
            add(shadersButton);

            modsFpsmodelPanel.setBounds(7, 82);
            add(modsFpsmodelPanel);
            modPanelsList.add(modsFpsmodelPanel);

            modsBettertpsPanel.setBounds(417, 82);
            add(modsBettertpsPanel);
            modPanelsList.add(modsBettertpsPanel);

            modsFallingleavesPanel.setBounds(7, 142);
            add(modsFallingleavesPanel);
            modPanelsList.add(modsFallingleavesPanel);

            modsAppleskinPanel.setBounds(417, 142);
            add(modsAppleskinPanel);
            modPanelsList.add(modsAppleskinPanel);

            modsSoundphysicsPanel.setBounds(7, 202);
            add(modsSoundphysicsPanel);
            modPanelsList.add(modsSoundphysicsPanel);

            modsWaveyCapesPanel.setBounds(417, 202);
            add(modsWaveyCapesPanel);
            modPanelsList.add(modsWaveyCapesPanel);

            mods3dSkinLayersPanel.setBounds(7, 262);
            add(mods3dSkinLayersPanel);
            modPanelsList.add(mods3dSkinLayersPanel);

        } else if (Objects.equals(pageName.getSubTab3(), PageName.PROFILE_ADDONS_SHADERS.getSubTab3())) {
            if (Objects.equals(pageName.getSpecialTab4(), PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV6.getSpecialTab4())) {
                setSubtitle("Addons - Shaders (Chocapic V6)");

                shadersChocapicV6LitePanel.setBounds(7, 82);
                add(shadersChocapicV6LitePanel);
                shaderPanelsList.add(shadersChocapicV6LitePanel);

                shadersChocapicV6LowPanel.setBounds(417, 82);
                add(shadersChocapicV6LowPanel);
                shaderPanelsList.add(shadersChocapicV6LowPanel);

                shadersChocapicV6MediumPanel.setBounds(7, 142);
                add(shadersChocapicV6MediumPanel);
                shaderPanelsList.add(shadersChocapicV6MediumPanel);

                shadersChocapicV6UltraPanel.setBounds(417, 142);
                add(shadersChocapicV6UltraPanel);
                shaderPanelsList.add(shadersChocapicV6UltraPanel);

                shadersChocapicV6ExtremePanel.setBounds(7, 202);
                add(shadersChocapicV6ExtremePanel);
                shaderPanelsList.add(shadersChocapicV6ExtremePanel);

                shadersButton.setBounds(388, 15);
                shadersButton.addEventListener(this);
                add(shadersButton);

            } else if (Objects.equals(pageName.getSpecialTab4(), PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV7.getSpecialTab4())) {
                setSubtitle("Addons - Shaders (Chocapic V7)");

                shadersChocapicV7_1ToasterPanel.setBounds(7, 82);
                add(shadersChocapicV7_1ToasterPanel);
                shaderPanelsList.add(shadersChocapicV7_1ToasterPanel);

                shadersChocapicV7_1LitePanel.setBounds(417, 82);
                add(shadersChocapicV7_1LitePanel);
                shaderPanelsList.add(shadersChocapicV7_1LitePanel);

                shadersChocapicV7_1LowPanel.setBounds(7, 142);
                add(shadersChocapicV7_1LowPanel);
                shaderPanelsList.add(shadersChocapicV7_1LowPanel);

                shadersChocapicV7_1MediumPanel.setBounds(417, 142);
                add(shadersChocapicV7_1MediumPanel);
                shaderPanelsList.add(shadersChocapicV7_1MediumPanel);

                shadersChocapicV7_1UltraPanel.setBounds(7, 202);
                add(shadersChocapicV7_1UltraPanel);
                shaderPanelsList.add(shadersChocapicV7_1UltraPanel);

                shadersChocapicV7_1ExtremePanel.setBounds(417, 202);
                add(shadersChocapicV7_1ExtremePanel);
                shaderPanelsList.add(shadersChocapicV7_1ExtremePanel);

                shadersButton.setBounds(388, 15);
                shadersButton.addEventListener(this);
                add(shadersButton);

            } else if (Objects.equals(pageName.getSpecialTab4(), PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV9.getSpecialTab4())) {
                setSubtitle("Addons - Shaders (Chocapic V9)");

                shadersChocapicV9LowPanel.setBounds(7, 82);
                add(shadersChocapicV9LowPanel);
                shaderPanelsList.add(shadersChocapicV9LowPanel);

                shadersChocapicV9MediumPanel.setBounds(417, 82);
                add(shadersChocapicV9MediumPanel);
                shaderPanelsList.add(shadersChocapicV9MediumPanel);

                shadersChocapicV9HighPanel.setBounds(7, 142);
                add(shadersChocapicV9HighPanel);
                shaderPanelsList.add(shadersChocapicV9HighPanel);

                shadersChocapicV9ExtremePanel.setBounds(417, 142);
                add(shadersChocapicV9ExtremePanel);
                shaderPanelsList.add(shadersChocapicV9ExtremePanel);

                shadersChocapicV9_1ExtremePanel.setBounds(7, 202);
                add(shadersChocapicV9_1ExtremePanel);
                shaderPanelsList.add(shadersChocapicV9_1ExtremePanel);

                shadersButton.setBounds(388, 15);
                shadersButton.addEventListener(this);
                add(shadersButton);

            } else {
                setSubtitle("Addons - Shaders");

                shadersChocapicV6PlusButton.setBounds(7, 82);
                add(shadersChocapicV6PlusButton);

                shadersChocapicV7_1PlusButton.setBounds(417, 82);
                add(shadersChocapicV7_1PlusButton);

                shadersChocapicV9PlusButton.setBounds(7, 142);
                add(shadersChocapicV9PlusButton);

                shadersSeusRenewedPanel.setBounds(417, 142);
                add(shadersSeusRenewedPanel);
                shaderPanelsList.add(shadersSeusRenewedPanel);

                modsButton.setBounds(388, 15);
                modsButton.addEventListener(this);
                add(modsButton);

            }

            goToFolderButton.setBounds(626, 448);
            goToFolderButton.addEventListener(this);
            add(goToFolderButton);

            optifineNeededLabel.setForeground(new Color(109, 109, 109));
            optifineNeededLabel.setFont(CustomFonts.kollektifFont.deriveFont(16f));
            optifineNeededLabel.setBounds(6, 50, 210, 30);
            optifineNeededLabel.setCaretColor(Color.RED);
            optifineNeededLabel.setBorder(null);
            optifineNeededLabel.setOpaque(false);
            optifineNeededLabel.setAlignmentY(SwingConstants.RIGHT);
            optifineNeededLabel.setSelectionColor(new Color(255, 20, 20, 200));
            optifineNeededLabel.setEditable(false);
            add(optifineNeededLabel);

            shadersSeeComparisonButton.setBounds(452, 469);
            shadersSeeComparisonButton.addEventListener(this);
            add(shadersSeeComparisonButton);
        }

        accountLabel.setBounds(380 - 178, 577 - 113, 276, 31);
        accountLabel.setForeground(Color.WHITE);
        accountLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(17f));
        this.add(accountLabel);

        accountConnectedLabel.setBounds(198 - 178, 577 - 113, 191, 31);
        accountConnectedLabel.setForeground(new Color(179, 179, 179));
        accountConnectedLabel.setFont(accountLabel.getFont());
        add(accountConnectedLabel);

        add(bg.getPanel());
    }

    public void setVisible(boolean aFlag) {
        if (aFlag) {
            setTitle("Profil " + getSelectedProfile());

            Thread t = new Thread(() -> {
                optifineSwitchButton.defineTextures();

                if (Objects.equals(pageName.getSubTab3(), PageName.PROFILE_ADDONS_MODS.getSubTab3())) {
                    int i = 0;
                    while (i != modPanelsList.toArray().length) {
                        modPanelsList.toArray(new ModPanel[0])[i].defineTextures();
                        i++;
                    }
                } else if (Objects.equals(pageName.getSubTab3(), PageName.PROFILE_ADDONS_SHADERS.getSubTab3())) {
                    int i = 0;
                    while (i != shaderPanelsList.toArray().length) {
                        shaderPanelsList.toArray(new ShaderPanel[0])[i].defineTextures();
                        i++;
                    }
                }

            });
            t.start();

            if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                accountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                accountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                LauncherPanel.enablePlayButtons(true);
            } else {
                accountLabel.setText("");
                accountConnectedLabel.setText("Non connect\u00e9");
                LauncherPanel.enablePlayButtons(false);
            }
        }
        super.setVisible(aFlag);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        Object src = swingerEvent.getSource();
        String eventSelectedProfile = getSelectedProfile();

        if (src == shadersButton) {
            setPage(true, PageName.PROFILE_ADDONS_SHADERS, eventSelectedProfile);
        } else if (src == resourcePacksButton) {
            initCustomFilesFolder(getSelectedSaver());
            try {
                Desktop.getDesktop().open(resourcepacksProfileFolder); // TODO Temporaire jusqu'à pouvoir le faire depuis le launcher
            } catch (IOException ignored) {}
        } else if (src == modsButton) {
            setPage(true, PageName.PROFILE_ADDONS_MODS, eventSelectedProfile);
        } else if (src == goToFolderButton) {
            initCustomFilesFolder(getSelectedSaver());
            if (subTitleLabel.getText().toLowerCase().contains("shader")) {
                try {
                    Desktop.getDesktop().open(shaderpacksProfileFolder);
                } catch (Exception e) {
                    shaderpacksProfileFolder.mkdir();
                    Launcher.println("Folder created: " + shaderpacksProfileFolder);
                    try {
                        Desktop.getDesktop().open(shaderpacksProfileFolder);
                    } catch (IOException ignored) {}
                }
            } else if (subTitleLabel.getText().toLowerCase().contains("resource")) {
                try {
                    Desktop.getDesktop().open(resourcepacksProfileFolder);
                } catch (IOException ignored) {}
            }
        } else if (src == optifineSwitchButton) {
            optifineSwitchButton.toggleButton();
        }

        else if (src == shadersSeeComparisonButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/AstrauworldMC/launcher/wiki/Comparaison-des-shaders").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
