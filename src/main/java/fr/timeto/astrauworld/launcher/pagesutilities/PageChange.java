package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;

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

    public class TAB_KEY {

        public static final String profileHome = "home";
        public static final String profileAccount = "account";
        private static final String profileAddons = "addons";
        public static final String profileAddonsMods = profileAddons + "-mods";
        public static final String profileAddonsShaders = profileAddons + "-shaders";
        public static final String profileAddonsResourcesPacks = profileAddons + "-resourcespacks";
        public static final String profileSettings = "settings";

        public static final String aboutInfos = "infos";
        public static final String aboutMods = "mods";
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

            upLeftCorner.setVisible(false);
            upRightCorner.setVisible(false);
            downLeftCorner.setVisible(false);
            downRightCorner.setVisible(false);

            titleLabel.setText("Actualit\u00e9s");
            subTitleLabel.setText("");
            LauncherSystemTray.changeTrayTooltip();

            background = getResourceIgnorePath("/assets/launcher//main/baseGUI.png");

            upLeftCorner.setVisible(true);
            upRightCorner.setVisible(true);
            downLeftCorner.setVisible(true);
            downRightCorner.setVisible(true);
        }else {
            newsButton.setEnabled(true);
        }

    }

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
        Saver selectedSaver = null;

        ProfileSaver.initSelectedProfile(profileNumber);

        if(Objects.equals(tab, TAB_KEY.profileHome)) {
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
                if(Objects.equals(titleLabel.getText(), "Profil 1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=firstProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected1 = firstProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=secondProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    selectedSaver=thirdProfileSaver;
                }
            }

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

                profileAccountLabel.setBounds(386, 468, 276, 31);
                profileAccountLabel.setVisible(true);
                if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                    if (Objects.equals(infosLabel.getText(), "")) {
                        profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                        enablePlayButtons(true);
                    } else {
                        enablePlayButtons(false);
                    }
                } else {
                    profileAccountLabel.setText("");
                    enablePlayButtons(false);
                }

                //          togglePlayButtonStatus(profilePlayButtonIsPlayStatus);

                upLeftCorner.setVisible(false);
                upRightCorner.setVisible(false);
                downLeftCorner.setVisible(false);
                downRightCorner.setVisible(false);

                subTitleLabel.setText("Jouer");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/profilePage.png");

                upLeftCorner.setVisible(true);
                upRightCorner.setVisible(true);
                downLeftCorner.setVisible(true);
                downRightCorner.setVisible(true);

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
                profileAccountLabel.setVisible(false);
                if (profileNumber == null) {
                    thirdProfileButton.setEnabled(true);
                }
            }
        } else if (Objects.equals(tab, TAB_KEY.profileAccount)) {
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
                if(Objects.equals(titleLabel.getText(), "Profil 1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=firstProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected1 = firstProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=secondProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    selectedSaver=thirdProfileSaver;
                }
            }
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

                profileAccountLabel.setBounds(388, 524, 276, 31);
                profileAccountLabel.setVisible(true);
                if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                    profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                } else {
                    profileAccountLabel.setText("");
                }

                upLeftCorner.setVisible(false);
                upRightCorner.setVisible(false);
                downLeftCorner.setVisible(false);
                downRightCorner.setVisible(false);

                subTitleLabel.setText("Compte");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/compte/profilePage-compte.png");

                upLeftCorner.setVisible(true);
                upRightCorner.setVisible(true);
                downLeftCorner.setVisible(true);
                downRightCorner.setVisible(true);

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
            }

        } else if (tab.contains(TAB_KEY.profileAddons)) {
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
                if(Objects.equals(titleLabel.getText(), "Profil 1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=firstProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected1 = firstProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=secondProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    selectedSaver=thirdProfileSaver;
                }
            }
            if (tab.contains("mods")) {
                if (enabled) {
                    setProfilePage(false, "null", "all");
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, null);

                    if (tab.equals("all")) {
                    } else {
                        profileSelected.setEnabled(false);
                        profileNotSelected1.setEnabled(true);
                        profileNotSelected2.setEnabled(true);
                    }

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

                    profileAccountLabel.setBounds(388, 575, 276, 31);
                    profileAccountLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                        profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                    } else {
                        profileAccountLabel.setText("");
                    }

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    subTitleLabel.setText("Addons - Mods");
                    LauncherSystemTray.changeTrayTooltip();

                    background = getResourceIgnorePath("/assets/launcher/profilesPage/mods/profilePage-addons-mods.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);
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
                }
            } else if (tab.contains("shaders")) {
                if (enabled) {
                    setProfilePage(false, "null", "all");
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, null);

                    if (tab.equals("all")) {
                    } else {
                        profileSelected.setEnabled(false);
                        profileNotSelected1.setEnabled(true);
                        profileNotSelected2.setEnabled(true);
                    }

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileModsTabButton.setEnabled(false);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profileAddonsModsButton.setVisible(true);
                    profileAddonsModsButton.setBounds(566, 128);
                    profileAddonsResourcePacksButton.setVisible(true);
                    profileAddonsOptifineSwitchButton.setVisible(true);
                    profileShadersChocapicV6PlusButton.setVisible(true);
                    profileShadersChocapicV7_1PlusButton.setVisible(true);
                    profileShadersChocapicV9PlusButton.setVisible(true);
                    profileShadersSeusRenewedDownloadButton.setVisible(true);
                    profileShadersSeusRenewedSwitchButton.setVisible(true);

                    profileAccountLabel.setBounds(388, 575, 276, 31);
                    profileAccountLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                        profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                    } else {
                        profileAccountLabel.setText("");
                    }

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    subTitleLabel.setText("Addons - Shaders");
                    LauncherSystemTray.changeTrayTooltip();

                    background = getResourceIgnorePath("/assets/launcher/profilesPage/mods/profilePage-addons-shaders.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);
                } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileAddonsModsButton.setVisible(false);
                    profileAddonsResourcePacksButton.setVisible(false);
                    profileAddonsOptifineSwitchButton.setVisible(false);
                    profileShadersChocapicV6PlusButton.setVisible(false);
                    profileShadersChocapicV7_1PlusButton.setVisible(false);
                    profileShadersChocapicV9PlusButton.setVisible(false);
                    profileShadersSeusRenewedDownloadButton.setVisible(false);
                    profileShadersSeusRenewedSwitchButton.setVisible(false);

                    profileAccountLabel.setVisible(false);
                }
            }

        } else if (Objects.equals(tab, TAB_KEY.profileSettings)) {
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
                if(Objects.equals(titleLabel.getText(), "Profil 1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=firstProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected1 = firstProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    selectedSaver=secondProfileSaver;
                } else if (Objects.equals(titleLabel.getText(), "Profil 3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    selectedSaver=thirdProfileSaver;
                }
            }
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
                profileSettingsSaveSettings.setVisible(true);

                profileAccountLabel.setBounds(388, 575, 276, 31);
                profileAccountLabel.setVisible(true);
                if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                    profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                } else {
                    profileAccountLabel.setText("");
                }

                upLeftCorner.setVisible(false);
                upRightCorner.setVisible(false);
                downLeftCorner.setVisible(false);
                downRightCorner.setVisible(false);

                subTitleLabel.setText("R\u00e9glages");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/reglages/profilePage-reglages.png");

                upLeftCorner.setVisible(true);
                upRightCorner.setVisible(true);
                downLeftCorner.setVisible(true);
                downRightCorner.setVisible(true);
            } else {
                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileModsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);

                profileSettingsProfileNameTextField.setVisible(false);
                profileSettingsHelmIconSwitchButton.setVisible(false);
                profileSettingsAllowedRamSpinner.setVisible(false);
                profileSettingsSaveSettings.setVisible(false);

                profileAccountLabel.setVisible(false);
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

            upLeftCorner.setVisible(false);
            upRightCorner.setVisible(false);
            downLeftCorner.setVisible(false);
            downRightCorner.setVisible(false);

            subTitleLabel.setText(" ");
            titleLabel.setText("Changelogs");
            LauncherSystemTray.changeTrayTooltip();

            int i = LauncherPanel.verifyVersionChangelog();
            changelogsTextArea.setText(Changelogs.getChangelogsTextsList().toArray()[i].toString());

            background = getResourceIgnorePath("/assets/launcher/changelogsPage/changelogsPage.png");

            upLeftCorner.setVisible(true);
            upRightCorner.setVisible(true);
            downLeftCorner.setVisible(true);
            downRightCorner.setVisible(true);
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

                upLeftCorner.setVisible(false);
                upRightCorner.setVisible(false);
                downLeftCorner.setVisible(false);
                downRightCorner.setVisible(false);

                subTitleLabel.setText("Infos");
                titleLabel.setText("\u00c0 propos");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/aboutPage/aboutPage-infos.png");

                upLeftCorner.setVisible(true);
                upRightCorner.setVisible(true);
                downLeftCorner.setVisible(true);
                downRightCorner.setVisible(true);
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

                upLeftCorner.setVisible(false);
                upRightCorner.setVisible(false);
                downLeftCorner.setVisible(false);
                downRightCorner.setVisible(false);

                subTitleLabel.setText("Mods");
                titleLabel.setText("\u00c0 propos");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

                upLeftCorner.setVisible(true);
                upRightCorner.setVisible(true);
                downLeftCorner.setVisible(true);

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
}
