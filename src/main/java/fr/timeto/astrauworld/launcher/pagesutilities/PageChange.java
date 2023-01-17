package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static java.lang.Float.parseFloat;

/**
 * La classe regroupant toutes les méthodes pour changer de page
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @since Beta2.2.0
 */
public class PageChange {

    public static class TAB_KEY {

        public static final String profileHome = "home";
        public static final String profileAccount = "account";
        private static final String profileAddons = "addons";
        public static final String profileAddonsMods = profileAddons + "-mods";
        public static final String profileAddonsShaders = profileAddons + "-shaders";
        public static final String profileAddonsResourcesPacks = profileAddons + "-resourcespacks";
        public static final String profileSettings = "settings";

        public static final String aboutInfos = "infos";
        public static final String aboutMods = "addons";
    }

    /**
     * Change la page pour la page principale des actualités
     * @param enabled Si {@code true}, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setNewsPage(boolean enabled) {
        if (enabled) {
            setProfilePage(false, null, "all");
            setChangesPage(false);
            setAboutPage(false, null);

            newsButton.setEnabled(false);

            corner.setVisible(false);

            titleLabel.setText("Actualit\u00e9s");
            subTitleLabel.setText("");
            LauncherSystemTray.changeTrayTooltip();

            background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

            
            
             
            corner.setVisible(true);
        }else {
            newsButton.setEnabled(true);
        }

    }

    private static Saver lastSettingsSaver = null;
    /**
     * Change la page pour la page d'un profil
     * @param enabled Si {@code true}, affiche la page et tous ces composants. {@code Si false}, fait disparaitre tous ces composants
     * @param profileNumber Le numéro du profil sélectionné
     * @param tab Quel onglet est selectionné. Si {@code null} -> changement de page, si "null" → aucun changement de profil, si "all" → fait toutes les pages
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setProfilePage(boolean enabled, String profileNumber, String tab) {
        STexturedButton profileSelected = null;
        STexturedButton profileNotSelected1 = firstProfileButton;
        STexturedButton profileNotSelected2 = secondProfileButton;
        Saver selectedSaver = firstProfileSaver;

        ProfileSaver.initSelectedProfile(profileNumber);

        if (enabled && !Objects.equals(tab, "all")) {
            if (Objects.equals(profileNumber, "1")) {
                profileSelected = firstProfileButton;
                profileNotSelected1 = secondProfileButton;
                profileNotSelected2 = thirdProfileButton;
                titleLabel.setText("Profil 1");
                selectedSaver = firstProfileSaver;
                selectedProfile = "1";
            } else if (Objects.equals(profileNumber, "2")) {
                profileSelected = secondProfileButton;
                profileNotSelected2 = thirdProfileButton;
                titleLabel.setText("Profil 2");
                selectedSaver = secondProfileSaver;
                selectedProfile = "2";
            } else if (Objects.equals(profileNumber, "3")) {
                profileSelected = thirdProfileButton;
                profileNotSelected1 = secondProfileButton;
                profileNotSelected2 = firstProfileButton;
                titleLabel.setText("Profil 3");
                selectedSaver = thirdProfileSaver;
                selectedProfile = "3";
            } else if (Objects.equals(profileNumber, "null")) {
                if (Objects.equals(titleLabel.getText(), "Profil 1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver = firstProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected1 = firstProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver = secondProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    selectedSaver = thirdProfileSaver;
                }
            }
        }

        if(Objects.equals(tab, TAB_KEY.profileHome)) {
            if (enabled) {
                setProfilePage(false, "null", "all");

                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, null);

                if(tab.equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }

                profilePlayTabButton.setEnabled(false);
                profileAccountTabButton.setEnabled(true);
                profileModsTabButton.setEnabled(true);
                profileSettingsTabButton.setEnabled(true);

                profilePlayTabButton.setVisible(true);
                profileAccountTabButton.setVisible(true);
                profileModsTabButton.setVisible(true);
                profileSettingsTabButton.setVisible(true);

                profilePlayButton.setVisible(true);
                profileNewsButton.setVisible(true);
                profileLaunchToMenuButton.setVisible(true);
                profileDownloadButton.setVisible(true);

                profileDiapoPanel.setVisible(true);
                profileTextLogo.setVisible(true);
                profileDiaporama(true);

                profileAccountLabel.setBounds(374, 470, 276, 31);
                profileAccountLabel.setVisible(true);
                profileAccountConnectedLabel.setBounds(192, 470, 191, 31);
                profileAccountConnectedLabel.setVisible(true);
                if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                    if (Objects.equals(infosLabel.getText(), "")) {
                        profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                        profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                        enablePlayButtons(true);
                    } else {
                        enablePlayButtons(false);
                    }
                } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    enablePlayButtons(false);
                }

                //          togglePlayButtonStatus(profilePlayButtonIsPlayStatus);

                corner.setVisible(false);

                subTitleLabel.setText("Jouer");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/profilePage.png");
                 
                corner.setVisible(true);

            } else {
                profileNotSelected1.setEnabled(true);
                profileNotSelected2.setEnabled(true);

                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileModsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);

                profilePlayButton.setVisible(false);
                profileNewsButton.setVisible(false);
                profileLaunchToMenuButton.setVisible(false);
                profileDownloadButton.setVisible(false);

                profileDiapoPanel.setVisible(false);
                profileTextLogo.setVisible(false);
                profileDiaporama(false);

                profileAccountLabel.setVisible(false);
                profileAccountConnectedLabel.setVisible(false);
                if (profileNumber == null) {
                    thirdProfileButton.setEnabled(true);
                }
            }
        } else if (Objects.equals(tab, TAB_KEY.profileAccount)) {
            if (enabled) {
                setProfilePage(false, "null", "all");
                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, null);

                if(tab.equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }

                profilePlayTabButton.setEnabled(true);
                profileAccountTabButton.setEnabled(false);
                profileModsTabButton.setEnabled(true);
                profileSettingsTabButton.setEnabled(true);

                profilePlayTabButton.setVisible(true);
                profileAccountTabButton.setVisible(true);
                profileModsTabButton.setVisible(true);
                profileSettingsTabButton.setVisible(true);

                profileAccountConnectionButton.setVisible(true);
                profileAccountConnectionMicrosoftButton.setVisible(true);
                profileAccountResetButton.setVisible(true);
                profileAccountTextField.setVisible(true);
                profileAccountPasswordField.setVisible(true);
                profileAccountTextField.setText("");
                profileAccountPasswordField.setText("");
                if (Objects.equals(selectedProfile, "1")) {
                    if(!Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL), "none")) {
                        profileAccountTextField.setText(firstProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL));
                    }
                } else if (Objects.equals(selectedProfile, "2")) {
                    if(!Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL), "none")) {
                        profileAccountTextField.setText(secondProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL));
                    }
                } else if (Objects.equals(selectedProfile, "3")) {
                    if(!Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL), "none")) {
                        profileAccountTextField.setText(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL));
                    }
                }

                profileAccountLabel.setBounds(380, 526, 276, 31);
                profileAccountLabel.setVisible(true);
                profileAccountConnectedLabel.setBounds(198, 526, 191, 31);
                profileAccountConnectedLabel.setVisible(true);
                if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                    if (Objects.equals(infosLabel.getText(), "")) {
                        profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                        profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                        enablePlayButtons(true);
                    } else {
                        enablePlayButtons(false);
                    }
                } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    enablePlayButtons(false);
                }

                corner.setVisible(false);

                subTitleLabel.setText("Compte");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/compte/profilePage-compte.png");
                 
                corner.setVisible(true);

            } else {
                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileModsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);
                profileAccountConnectionButton.setVisible(false);
                profileAccountConnectionMicrosoftButton.setVisible(false);
                profileAccountResetButton.setVisible(false);
                profileAccountTextField.setVisible(false);
                profileAccountPasswordField.setVisible(false);

                profileAccountLabel.setVisible(false);
                profileAccountConnectedLabel.setVisible(false);
            }

        } else if (tab.contains(TAB_KEY.profileAddons)) {
            if (tab.contains("mods")) {
                if (enabled) {
                    setProfilePage(false, "null", "all");
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, null);

                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileModsTabButton.setEnabled(false);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profileAddonsShadersButton.setVisible(true);
                    profileAddonsResourcePacksButton.setVisible(true);
                    profileAddonsOptifineSwitchButton.setVisible(true);
                    profileModsFpsmodelSwitchButton.setVisible(true);
                    profileModsFpsmodelMoreInfosButton.setVisible(true);
                    profileModsBettertpsSwitchButton.setVisible(true);
                    profileModsBettertpsMoreInfosButton.setVisible(true);
                    profileModsFallingleavesSwitchButton.setVisible(true);
                    profileModsFallingleavesMoreInfosButton.setVisible(true);
                    profileModsAppleskinSwitchButton.setVisible(true);
                    profileModsAppleskinMoreInfosButton.setVisible(true);
                    profileModsSoundphysicsSwitchButton.setVisible(true);
                    profileModsSoundphysicsMoreInfosButton.setVisible(true);

                    profileAccountLabel.setBounds(380, 577, 276, 31);
                    profileAccountLabel.setVisible(true);
                    profileAccountConnectedLabel.setBounds(198, 577, 191, 31);
                    profileAccountConnectedLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                        if (Objects.equals(infosLabel.getText(), "")) {
                            profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                            profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                            enablePlayButtons(true);
                        } else {
                            enablePlayButtons(false);
                        }
                    } else {
                        profileAccountLabel.setText("");
                        profileAccountConnectedLabel.setText("Non connect\u00e9");
                        enablePlayButtons(false);
                    }

                    corner.setVisible(false);

                    subTitleLabel.setText("Addons - Mods");
                    LauncherSystemTray.changeTrayTooltip();

                    background = getResourceIgnorePath("/assets/launcher/profilesPage/addons/profilePage-addons-mods.png");

                    corner.setVisible(true);
                } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileAddonsShadersButton.setVisible(false);
                    profileAddonsResourcePacksButton.setVisible(false);
                    profileAddonsOptifineSwitchButton.setVisible(false);
                    profileModsFpsmodelSwitchButton.setVisible(false);
                    profileModsFpsmodelMoreInfosButton.setVisible(false);
                    profileModsBettertpsSwitchButton.setVisible(false);
                    profileModsBettertpsMoreInfosButton.setVisible(false);
                    profileModsFallingleavesSwitchButton.setVisible(false);
                    profileModsFallingleavesMoreInfosButton.setVisible(false);
                    profileModsAppleskinSwitchButton.setVisible(false);
                    profileModsAppleskinMoreInfosButton.setVisible(false);
                    profileModsSoundphysicsSwitchButton.setVisible(false);
                    profileModsSoundphysicsMoreInfosButton.setVisible(false);

                    profileAccountLabel.setVisible(false);
                    profileAccountConnectedLabel.setVisible(false);
                }
            } else if (tab.contains("shaders")) {
                if (enabled) {
                    setProfilePage(false, "null", "all");
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, null);

                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileModsTabButton.setEnabled(false);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profileAddonsResourcePacksButton.setVisible(true);
                    profileAddonsOptifineSwitchButton.setVisible(true);
                    profileAddonsGoToFolderButton.setVisible(true);

                    if (tab.toLowerCase().contains("chocapicv6")) {
                        profileAddonsShadersButton.setVisible(true);
                        profileAddonsShadersButton.setBounds(566, 128);

                        profileShadersChocapicV6LiteSwitchButton.setVisible(true);
                        profileShadersChocapicV6LiteDownloadButton.setVisible(true);
                        profileShadersChocapicV6LowSwitchButton.setVisible(true);
                        profileShadersChocapicV6LowDownloadButton.setVisible(true);
                        profileShadersChocapicV6MediumSwitchButton.setVisible(true);
                        profileShadersChocapicV6MediumDownloadButton.setVisible(true);
                        profileShadersChocapicV6UltraSwitchButton.setVisible(true);
                        profileShadersChocapicV6UltraDownloadButton.setVisible(true);
                        profileShadersChocapicV6ExtremeSwitchButton.setVisible(true);
                        profileShadersChocapicV6ExtremeDownloadButton.setVisible(true);

                        background = getResourceIgnorePath("/assets/launcher/profilesPage/addons/profilePage-addons-shaders-chocapicV6.png");
                        subTitleLabel.setText("Addons - Shaders (ChocapicV6)");
                    } else if (tab.toLowerCase().contains("chocapicv7")) {
                        profileAddonsShadersButton.setVisible(true);
                        profileAddonsShadersButton.setBounds(566, 128);

                        profileShadersChocapicV7_1ToasterSwitchButton.setVisible(true);
                        profileShadersChocapicV7_1ToasterDownloadButton.setVisible(true);
                        profileShadersChocapicV7_1LiteSwitchButton.setVisible(true);
                        profileShadersChocapicV7_1LiteDownloadButton.setVisible(true);
                        profileShadersChocapicV7_1LowSwitchButton.setVisible(true);
                        profileShadersChocapicV7_1LowDownloadButton.setVisible(true);
                        profileShadersChocapicV7_1MediumSwitchButton.setVisible(true);
                        profileShadersChocapicV7_1MediumDownloadButton.setVisible(true);
                        profileShadersChocapicV7_1UltraSwitchButton.setVisible(true);
                        profileShadersChocapicV7_1UltraDownloadButton.setVisible(true);
                        profileShadersChocapicV7_1ExtremeSwitchButton.setVisible(true);
                        profileShadersChocapicV7_1ExtremeDownloadButton.setVisible(true);

                        background = getResourceIgnorePath("/assets/launcher/profilesPage/addons/profilePage-addons-shaders-chocapicV7.png");
                        subTitleLabel.setText("Addons - Shaders (ChocapicV7)");
                    } else if (tab.toLowerCase().contains("chocapicv9")) {
                        profileAddonsShadersButton.setVisible(true);
                        profileAddonsShadersButton.setBounds(566, 128);

                        profileShadersChocapicV9LowSwitchButton.setVisible(true);
                        profileShadersChocapicV9LowDownloadButton.setVisible(true);
                        profileShadersChocapicV9MediumSwitchButton.setVisible(true);
                        profileShadersChocapicV9MediumDownloadButton.setVisible(true);
                        profileShadersChocapicV9HighSwitchButton.setVisible(true);
                        profileShadersChocapicV9HighDownloadButton.setVisible(true);
                        profileShadersChocapicV9ExtremeSwitchButton.setVisible(true);
                        profileShadersChocapicV9ExtremeDownloadButton.setVisible(true);
                        profileShadersChocapicV9_1ExtremeSwitchButton.setVisible(true);
                        profileShadersChocapicV9_1ExtremeDownloadButton.setVisible(true);

                        background = getResourceIgnorePath("/assets/launcher/profilesPage/addons/profilePage-addons-shaders-chocapicV9.png");
                        subTitleLabel.setText("Addons - Shaders (ChocapicV9)");
                    } else {
                        profileAddonsModsButton.setVisible(true);
                        profileAddonsModsButton.setBounds(566, 128);

                        profileShadersChocapicV6PlusButton.setVisible(true);
                        profileShadersChocapicV7_1PlusButton.setVisible(true);
                        profileShadersChocapicV9PlusButton.setVisible(true);
                        profileShadersSeusRenewedDownloadButton.setVisible(true);
                        profileShadersSeusRenewedSwitchButton.setVisible(true);

                        background = getResourceIgnorePath("/assets/launcher/profilesPage/addons/profilePage-addons-shaders.png");
                        subTitleLabel.setText("Addons - Shaders");
                    }

                    profileAccountLabel.setBounds(380, 577, 276, 31);
                    profileAccountLabel.setVisible(true);
                    profileAccountConnectedLabel.setBounds(198, 577, 191, 31);
                    profileAccountConnectedLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                        if (Objects.equals(infosLabel.getText(), "")) {
                            profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                            profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                            enablePlayButtons(true);
                        } else {
                            enablePlayButtons(false);
                        }
                    } else {
                        profileAccountLabel.setText("");
                        profileAccountConnectedLabel.setText("Non connect\u00e9");
                        enablePlayButtons(false);
                    }

                    corner.setVisible(false);

                    LauncherSystemTray.changeTrayTooltip();

                    corner.setVisible(true);

                } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileAddonsModsButton.setVisible(false);
                    profileAddonsResourcePacksButton.setVisible(false);
                    profileAddonsOptifineSwitchButton.setVisible(false);
                    profileAddonsShadersButton.setVisible(false);
                    profileAddonsGoToFolderButton.setVisible(false);

                    profileShadersChocapicV6PlusButton.setVisible(false);
                    profileShadersChocapicV7_1PlusButton.setVisible(false);
                    profileShadersChocapicV9PlusButton.setVisible(false);
                    profileShadersSeusRenewedDownloadButton.setVisible(false);
                    profileShadersSeusRenewedSwitchButton.setVisible(false);

                    profileShadersChocapicV6LiteSwitchButton.setVisible(false);
                    profileShadersChocapicV6LiteDownloadButton.setVisible(false);
                    profileShadersChocapicV6LowSwitchButton.setVisible(false);
                    profileShadersChocapicV6LowDownloadButton.setVisible(false);
                    profileShadersChocapicV6MediumSwitchButton.setVisible(false);
                    profileShadersChocapicV6MediumDownloadButton.setVisible(false);
                    profileShadersChocapicV6UltraSwitchButton.setVisible(false);
                    profileShadersChocapicV6UltraDownloadButton.setVisible(false);
                    profileShadersChocapicV6ExtremeSwitchButton.setVisible(false);
                    profileShadersChocapicV6ExtremeDownloadButton.setVisible(false);

                    profileShadersChocapicV7_1ToasterSwitchButton.setVisible(false);
                    profileShadersChocapicV7_1ToasterDownloadButton.setVisible(false);
                    profileShadersChocapicV7_1LiteSwitchButton.setVisible(false);
                    profileShadersChocapicV7_1LiteDownloadButton.setVisible(false);
                    profileShadersChocapicV7_1LowSwitchButton.setVisible(false);
                    profileShadersChocapicV7_1LowDownloadButton.setVisible(false);
                    profileShadersChocapicV7_1MediumSwitchButton.setVisible(false);
                    profileShadersChocapicV7_1MediumDownloadButton.setVisible(false);
                    profileShadersChocapicV7_1UltraSwitchButton.setVisible(false);
                    profileShadersChocapicV7_1UltraDownloadButton.setVisible(false);
                    profileShadersChocapicV7_1ExtremeSwitchButton.setVisible(false);
                    profileShadersChocapicV7_1ExtremeDownloadButton.setVisible(false);

                    profileShadersChocapicV9LowSwitchButton.setVisible(false);
                    profileShadersChocapicV9LowDownloadButton.setVisible(false);
                    profileShadersChocapicV9MediumSwitchButton.setVisible(false);
                    profileShadersChocapicV9MediumDownloadButton.setVisible(false);
                    profileShadersChocapicV9HighSwitchButton.setVisible(false);
                    profileShadersChocapicV9HighDownloadButton.setVisible(false);
                    profileShadersChocapicV9ExtremeSwitchButton.setVisible(false);
                    profileShadersChocapicV9ExtremeDownloadButton.setVisible(false);
                    profileShadersChocapicV9_1ExtremeSwitchButton.setVisible(false);
                    profileShadersChocapicV9_1ExtremeDownloadButton.setVisible(false);

                    profileAccountLabel.setVisible(false);
                    profileAccountConnectedLabel.setVisible(false);
                }
            }

        } else if (Objects.equals(tab, TAB_KEY.profileSettings)) {
            if (enabled) {
                setProfilePage(false, "null", "all");
                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, null);

                if(tab.equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }
                lastSettingsSaver = ProfileSaver.selectedSaver;

                profilePlayTabButton.setEnabled(true);
                profileAccountTabButton.setEnabled(true);
                profileModsTabButton.setEnabled(true);
                profileSettingsTabButton.setEnabled(false);

                profilePlayTabButton.setVisible(true);
                profileAccountTabButton.setVisible(true);
                profileModsTabButton.setVisible(true);
                profileSettingsTabButton.setVisible(true);

                profileSettingsProfileNameTextField.setVisible(true);
                profileSettingsProfileNameTextField.setText(ProfileSaver.selectedSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
                profileSettingsHelmIconSwitchButton.setVisible(true);
                profileSettingsAllowedRamSpinner.setVisible(true);
                profileSettingsAllowedRamSpinner.setValue(parseFloat(ProfileSaver.selectedSaver.get(ProfileSaver.KEY.SETTINGS_RAM)));
                profileSettingsMainProfileSwitchButton.setVisible(true);
                profileSettingsSaveSettings.setVisible(true);

                profileAccountLabel.setBounds(380, 577, 276, 31);
                profileAccountLabel.setVisible(true);
                profileAccountConnectedLabel.setBounds(198, 577, 191, 31);
                profileAccountConnectedLabel.setVisible(true);
                if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                    if (Objects.equals(infosLabel.getText(), "")) {
                        profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                        profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                        enablePlayButtons(true);
                    } else {
                        enablePlayButtons(false);
                    }
                } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    enablePlayButtons(false);
                }

                corner.setVisible(false);

                subTitleLabel.setText("R\u00e9glages");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/reglages/profilePage-reglages.png");

                corner.setVisible(true);
            } else {
                if (lastSettingsSaver != null) {
                    Saver finalLastSettingsSaver = lastSettingsSaver;

                    if (Objects.equals(profileSettingsProfileNameTextField.getText(), "")) {
                        profileSettingsProfileNameTextField.setText(finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
                    }
                    if (Objects.equals(profileSettingsAllowedRamSpinner.getValue().toString(), "2.0")) {
                        profileSettingsProfileNameTextField.setText(finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_RAM));
                    }

                    String profileName = profileSettingsProfileNameTextField.getText();
                    String ram = profileSettingsAllowedRamSpinner.getValue().toString();

                    if (!Objects.equals(profileName, finalLastSettingsSaver.get(KEY.SETTINGS_PROFILENAME)) || !Objects.equals(ram, finalLastSettingsSaver.get(KEY.SETTINGS_RAM))) {
                        Thread yes = new Thread(() -> {
                            finalLastSettingsSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME, profileName);
                            finalLastSettingsSaver.set(ProfileSaver.KEY.SETTINGS_RAM, ram);
                            initProfileButtons();

                            PopUpMessages.doneMessage("Sauvegard\u00e9", "Param\u00e8tres           sauvegard\u00e9s");
                        });
                        Thread no = new Thread(() -> {
                            profileSettingsProfileNameTextField.setText(finalLastSettingsSaver.get(KEY.SETTINGS_PROFILENAME));
                            profileSettingsAllowedRamSpinner.setValue(parseFloat(finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_RAM)));

                            PopUpMessages.errorMessage("Non sauvegard\u00e9", "Param\u00e8tres non       sauvegard\u00e9s");
                        });

                        PopUpMessages.yesNoMessage("Sauvegarder ?", "Voulez-vous          sauvegarder les         param\u00e8tres ?", yes, no);
                    }
                }

                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileModsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);

                profileSettingsProfileNameTextField.setVisible(false);
                profileSettingsHelmIconSwitchButton.setVisible(false);
                profileSettingsAllowedRamSpinner.setVisible(false);
                profileSettingsMainProfileSwitchButton.setVisible(false);
                profileSettingsSaveSettings.setVisible(false);

                profileAccountLabel.setVisible(false);
                profileAccountConnectedLabel.setVisible(false);
            }

        } else if (Objects.equals(tab, "all")) {
            setProfilePage(enabled, null, TAB_KEY.profileHome);
            setProfilePage(enabled, null, TAB_KEY.profileAccount);
            setProfilePage(enabled, null, TAB_KEY.profileAddonsMods);
            setProfilePage(enabled, null, TAB_KEY.profileAddonsShaders);
            setProfilePage(enabled, null, TAB_KEY.profileSettings);
        }

    }

    /**
     * Change la page pour la page principale des changelogs
     * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setChangesPage(boolean enabled) {
        if (enabled) {
            setAboutPage(false, null);
            setProfilePage(false, null, "all");
            setAboutPage(false, null);
            changesButton.setEnabled(false);

            changelogsVersionComboBox.setVisible(true);
            changelogsTextArea.setVisible(true);

            corner.setVisible(false);

            subTitleLabel.setText(" ");
            titleLabel.setText("Changelogs");
            LauncherSystemTray.changeTrayTooltip();

            int i = LauncherPanel.verifyVersionChangelog();
            changelogsTextArea.setText(Changelogs.getChangelogsTextsList().toArray()[i].toString());

            background = getResourceIgnorePath("/assets/launcher/changelogsPage/changelogsPage.png");
             
            corner.setVisible(true);
        }else {
            changelogsVersionComboBox.setVisible(false);
            changelogsTextArea.setVisible(false);

            changesButton.setEnabled(true);
        }
    }

    /**
     * Change la page pour la page principale des contacts
     *
     * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @param tab     Le nom de la sous-page voulue
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setAboutPage(boolean enabled, String tab) {
        if (Objects.equals(tab, TAB_KEY.aboutInfos)) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, "all");
                setChangesPage(false);
                setAboutPage(false, PageChange.TAB_KEY.aboutMods);

                aboutButton.setEnabled(false);
                aboutInfosTabButton.setEnabled(false);

                aboutInfosTabButton.setVisible(true);
                aboutModsTabButton.setVisible(true);

                aboutTextLogo.setVisible(true);
                aboutAstrauwolfLogo.setVisible(true);
                aboutCapitenzoLogo.setVisible(true);
                aboutTimEtOLogo.setVisible(true);

                aboutGithubButton.setVisible(true);
                aboutMailButton.setVisible(true);
                aboutDiscordButton.setVisible(true);
                aboutTwitterButton.setVisible(true);

                aboutEastereggsLabel.setText(EasterEggs.getNumberOfFoundEasterEggs() + "/" + EasterEggs.getNumberTotalEasterEggs());
                aboutEastereggsLabel.setVisible(true);

                corner.setVisible(false);

                subTitleLabel.setText("Infos");
                titleLabel.setText("\u00c0 propos");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/aboutPage/aboutPage-infos.png");

                
                
                 
                corner.setVisible(true);
            } else {
                aboutInfosTabButton.setVisible(false);
                aboutModsTabButton.setVisible(false);

                aboutTextLogo.setVisible(false);
                aboutAstrauwolfLogo.setVisible(false);
                aboutCapitenzoLogo.setVisible(false);
                aboutTimEtOLogo.setVisible(false);

                aboutGithubButton.setVisible(false);
                aboutMailButton.setVisible(false);
                aboutDiscordButton.setVisible(false);
                aboutTwitterButton.setVisible(false);

                aboutEastereggsLabel.setVisible(false);

                aboutButton.setEnabled(true);
                aboutInfosTabButton.setEnabled(true);
            }
        } else if (Objects.equals(tab, TAB_KEY.aboutMods)) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, "all");
                setChangesPage(false);
                setAboutPage(false, PageChange.TAB_KEY.aboutInfos);

                aboutButton.setEnabled(false);
                aboutModsTabButton.setEnabled(false);

                aboutInfosTabButton.setVisible(true);
                aboutModsTabButton.setVisible(true);

                corner.setVisible(false);

                subTitleLabel.setText("Mods");
                titleLabel.setText("\u00c0 propos");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

                
                
                 

            } else {
                aboutInfosTabButton.setVisible(false);
                aboutModsTabButton.setVisible(false);

                aboutButton.setEnabled(true);
                aboutModsTabButton.setEnabled(true);
            }

        } else {
            setAboutPage(false, TAB_KEY.aboutInfos);
            setAboutPage(false, TAB_KEY.aboutMods);
        }

    }

    private static boolean inDiaporama;
    private static Thread tDiapo = null;
    public static void profileDiaporama(boolean active) {
        if (active) {
            inDiaporama = true;

            corner.setVisible(true);
            corner.setVisible(false);

            if (tDiapo == null) {
                tDiapo = new Thread(() -> {
                    BufferedImage lakeImage;
                    BufferedImage townHallImage;
                    BufferedImage churchImage;
                    BufferedImage villageImage;

                    String check = "dayCycle";
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                        Date date_from = formatter.parse("20:00");
                        Date date_to = formatter.parse("08:00");
                        Date dateNow = formatter.parse(formatter.format(new Date()));

                        if (date_from.before(date_to)) { // they are on the same day
                            if (dateNow.after(date_from) && dateNow.before(date_to)) {
                                check = "nightCycle";
                            } else {
                                check = "dayCycle";
                            }
                        } else { // interval crossing midnight
                            if (dateNow.before(date_to) || dateNow.after(date_from)) {
                                check = "nightCycle";
                            } else {
                                check = "dayCycle";
                            }
                        }
                    } catch (ParseException ignored) {
                    }
                    if (check.equals("nightCycle")) {
                        lakeImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/lake-night.png");
                        townHallImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/townHall-night.png");
                        churchImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/church-night.png");
                        villageImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/village-night.png");
                    } else {
                        lakeImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/lake-day.png");
                        townHallImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/townHall-day.png");
                        churchImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/church-day.png");
                        villageImage = Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/village-day.png");
                    }

                    final int xAllView = 0;
                    final int xAllLeft = -822;
                    final int xALlRight = 822;

                    int x1;
                    int x2;

                    while (inDiaporama) {
                        profileDiapoImage1.setIcon(new ImageIcon(lakeImage));
                        profileDiapoImage2.setIcon(new ImageIcon(townHallImage));

                        if (!inDiaporama) {
                            return;
                        }

                        x2 = xALlRight;
                        x1 = xAllView;
                        profileDiapoImage2.setLocation(x2, 0);
                        profileDiapoImage1.setLocation(x1, 0);
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);
                        if (!inDiaporama) {
                            return;
                        }

                        while (x1 != xAllLeft && x2 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }

                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            profileDiapoImage1.setLocation(x1, 0);
                            profileDiapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        x1 = xALlRight;
                        profileDiapoImage1.setLocation(x1, 0);
                        if (!inDiaporama) {
                            return;
                        }
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);
                        if (!inDiaporama) {
                            return;
                        }

                        profileDiapoImage1.setIcon(new ImageIcon(churchImage));

                        while (x2 != xAllLeft && x1 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }

                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            profileDiapoImage1.setLocation(x1, 0);
                            profileDiapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        profileDiapoImage2.setIcon(new ImageIcon(villageImage));

                        x2 = xALlRight;
                        profileDiapoImage2.setLocation(x2, 0);
                        if (!inDiaporama) {
                            return;
                        }
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        if (!inDiaporama) {
                            return;
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);

                        while (x1 != xAllLeft && x2 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }
                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            profileDiapoImage1.setLocation(x1, 0);
                            profileDiapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        x1 = xALlRight;
                        profileDiapoImage1.setLocation(x1, 0);
                        if (!inDiaporama) {
                            return;
                        }
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        corner.setVisible(true);
                        corner.setVisible(false);
                        if (!inDiaporama) {
                            return;
                        }

                        profileDiapoImage1.setIcon(new ImageIcon(lakeImage));

                        while (x2 != xAllLeft && x1 != xAllView) {
                            if (!inDiaporama) {
                                return;
                            }
                            x1 -= 3;
                            x2 -= 3;
                            corner.setVisible(true);
                            profileDiapoImage1.setLocation(x1, 0);
                            profileDiapoImage2.setLocation(x2, 0);
                            corner.setVisible(false);
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        corner.setVisible(true);
                        corner.setVisible(false);
                    }

                });
                tDiapo.start();

                Thread t = new Thread(() -> {
                    try {
                        tDiapo.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    tDiapo = null;
                });
                t.start();
            }

        } else {
            inDiaporama = false;
        }
    }
}
