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

    /**
     * Change la page pour la page principale des actualités
     * @param enabled Si {@code true}, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setNewsPage(boolean enabled) {
        if (enabled) {
            setProfilePage(false, null, "all");
            setChangesPage(false);
            setAboutPage(null, false);

            newsButton.setEnabled(false);

            upLeftCorner.setVisible(false);
            upRightCorner.setVisible(false);
            downLeftCorner.setVisible(false);
            downRightCorner.setVisible(false);

            titleLabel.setText("Actualit\u00e9s");
            subTitleLabel.setText("");
            LauncherSystemTray.changeTrayTooltip();

            background = getResourceIgnorePath("/assets.launcher/main/baseGUI.png");

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
     * @param tab Quel onglet est selectionnée. Si {@code null} -> changement de page, si "null" -> aucun changement de profil, si "all" -> fait toute les pages
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setProfilePage(boolean enabled, String profileNumber, String tab) {
        STexturedButton profileSelected = null;
        STexturedButton profileNotSelected1 = firstProfileButton;
        STexturedButton profileNotSelected2 = secondProfileButton;
        Saver selectedSaver = null;

        if(Objects.equals(tab, "home")) {
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
                setAboutPage(null, false);

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

                background = getResourceIgnorePath("/assets.launcher/profilesPage/profilePage.png");

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
        } else if (Objects.equals(tab, "account")) {
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
                setAboutPage(null, false);

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

                background = getResourceIgnorePath("/assets.launcher/profilesPage/compte/profilePage-compte.png");

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

        } else if (Objects.equals(tab, "mods")) {
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
                setAboutPage(null, false);

                if(tab.equals("all")) {
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

                profileModsShadersButton.setVisible(true);
                profileModsResourcePacksButton.setVisible(true);
                profileModsOptifineSwitchButton.setVisible(true);
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
                if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                    profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                } else {
                    profileAccountLabel.setText("");
                }

                upLeftCorner.setVisible(false);
                upRightCorner.setVisible(false);
                downLeftCorner.setVisible(false);
                downRightCorner.setVisible(false);

                subTitleLabel.setText("Mods");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets.launcher/profilesPage/mods/profilePage-mods.png");

                upLeftCorner.setVisible(true);
                upRightCorner.setVisible(true);
                downLeftCorner.setVisible(true);
                downRightCorner.setVisible(true);
            } else {
                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileModsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);

                profileModsShadersButton.setVisible(false);
                profileModsResourcePacksButton.setVisible(false);
                profileModsOptifineSwitchButton.setVisible(false);
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

        } else if (Objects.equals(tab, "settings")) {
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
                setAboutPage(null, false);

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

                initSelectedSaver();
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

                background = getResourceIgnorePath("/assets.launcher/profilesPage/reglages/profilePage-reglages.png");

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
            setProfilePage(enabled, null, "home");
            setProfilePage(enabled, null, "account");
            setProfilePage(enabled, null, "mods");
            setProfilePage(enabled, null, "settings");
        }

    }

    /**
     * Change la page pour la page principale des changelogs
     * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setChangesPage(boolean enabled) {
        if (enabled) {
            setAboutPage(null, false);
            setProfilePage(false, null, "all");
            setAboutPage(null, false);
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

            background = getResourceIgnorePath("/assets.launcher/changelogsPage/changelogsPage.png");

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
     * @param page Le nom de la sous-page voulue
     * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setAboutPage(String page, boolean enabled) {
        if (Objects.equals(page, "infos")) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, "all");
                setChangesPage(false);
                setAboutPage("mods", false);

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

                background = getResourceIgnorePath("/assets.launcher/aboutPage/aboutPage-infos.png");

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
        } else if (Objects.equals(page, "mods")) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, "all");
                setChangesPage(false);
                setAboutPage("infos", false);

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

                background = getResourceIgnorePath("/assets.launcher/main/baseGUI.png");

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
            setAboutPage("infos", false);
            setAboutPage("mods", false);
        }

    }
}
