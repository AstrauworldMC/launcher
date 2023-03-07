package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
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

    public static void setPage(boolean e, PageName page) {
        if (Objects.equals(page.getPage1(), PageName.NEWS.getPage1())) {
            setNewsPage(e);
        } else if (Objects.equals(page.getPage1(), PageName.PROFILE_HOME.getPage1())) {
            setProfilePage(e, getSelectedProfile(), page);
        }else if (Objects.equals(page.getPage1(), PageName.CHANGELOGS.getPage1())) {
            setChangesPage(e);
        } else if (Objects.equals(page.getPage1(), PageName.ABOUT_INFOS.getPage1())) {
            setAboutPage(e, page);
        }
    }

    public static void setPage(boolean e, PageName page, String profileNum) {
        if (Objects.equals(page.getPage1(), PageName.NEWS.getPage1())) {
            setNewsPage(e);
        } else if (Objects.equals(page.getPage1(), PageName.PROFILE_HOME.getPage1())) {
            setProfilePage(e, profileNum, page);
        } else if (Objects.equals(page.getPage1(), PageName.CHANGELOGS.getPage1())) {
            setChangesPage(e);
        } else if (Objects.equals(page.getPage1(), PageName.ABOUT_INFOS.getPage1())) {
            setAboutPage(e, page);
        }
    }

    /**
     * Change la page pour la page principale des actualités
     *
     * @param enabled Si {@code true}, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    private static void setNewsPage(boolean enabled) {
        if (enabled) {
            setProfilePage(false, null, PageName.PROFILE_ALL);
            setChangesPage(false);
            setAboutPage(false, PageName.ABOUT);

            leftMenuSelector.moveTo(newsButton);
            newsButton.getButton().setEnabled(false);

            newsScrollPanel.setVisible(true);
            newsOpenScrollPanel.setVisible(false);

            corner.setVisible(false);

            titleLabel.setText("Actualit\u00e9s");
            subTitleLabel.setText("");
            LauncherSystemTray.changeTrayTooltip();

            background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");

            corner.setVisible(true);
        }else {
            newsButton.getButton().setEnabled(true);

            newsScrollPanel.setVisible(false);
            newsOpenScrollPanel.setVisible(false);
        }

    }

    public static Saver lastSettingsSaver = null;
    public static String lastSettingsProfileName = "";
    public static String lastSettingsRam = "";
    /**
     * Change la page pour la page d'un profil
     * @param enabled Si {@code true}, affiche la page et tous ces composants. {@code Si false}, fait disparaitre tous ces composants
     * @param profileNumber Le numéro du profil sélectionné
     * @param page Quel onglet est selectionné. Si {@code null} -> changement de page, si "null" → aucun changement de profil, si "all" → fait toutes les pages
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    private static void setProfilePage(boolean enabled, String profileNumber, PageName page) {

        JPanel profilePanelSelected = null;
        SColoredButton profileSelected = null;
        SColoredButton profileNotSelected1 = firstProfileButton.getProfileButton();
        SColoredButton profileNotSelected2 = secondProfileButton.getProfileButton();
        Saver selectedSaver = firstProfileSaver;

        if (profileNumber != null) {
            ProfileSaver.setSelectedProfile(profileNumber);

            if (enabled && !Objects.equals(page.getTab2(), "all")) {
                if (Objects.equals(profileNumber, "1")) {
                    profileSelected = firstProfileButton.getProfileButton();
                    profilePanelSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton.getProfileButton();
                    profileNotSelected2 = thirdProfileButton.getProfileButton();
                    titleLabel.setText("Profil 1");
                    selectedSaver = firstProfileSaver;
                    setSelectedProfile("1");
                } else if (Objects.equals(profileNumber, "2")) {
                    profileSelected = secondProfileButton.getProfileButton();
                    profilePanelSelected = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton.getProfileButton();
                    titleLabel.setText("Profil 2");
                    selectedSaver = secondProfileSaver;
                    setSelectedProfile("2");
                } else if (Objects.equals(profileNumber, "3")) {
                    profileSelected = thirdProfileButton.getProfileButton();
                    profilePanelSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton.getProfileButton();
                    profileNotSelected2 = firstProfileButton.getProfileButton();
                    titleLabel.setText("Profil 3");
                    selectedSaver = thirdProfileSaver;
                    setSelectedProfile("3");
                } else if (Objects.equals(profileNumber, "null")) {
                    if (Objects.equals(titleLabel.getText(), "Profil 1")) {
                        profileSelected = firstProfileButton.getProfileButton();
                        profilePanelSelected = firstProfileButton;
                        profileNotSelected1 = secondProfileButton.getProfileButton();
                        profileNotSelected2 = thirdProfileButton.getProfileButton();
                        selectedSaver = firstProfileSaver;
                    } else if (Objects.equals(titleLabel.getText(), "Profil 2")) {
                        profileSelected = secondProfileButton.getProfileButton();
                        profilePanelSelected = secondProfileButton;
                        profileNotSelected1 = firstProfileButton.getProfileButton();
                        profileNotSelected2 = thirdProfileButton.getProfileButton();
                        selectedSaver = secondProfileSaver;
                    } else if (Objects.equals(titleLabel.getText(), "Profil 3")) {
                        profileSelected = thirdProfileButton.getProfileButton();
                        profilePanelSelected = thirdProfileButton;
                        profileNotSelected1 = secondProfileButton.getProfileButton();
                        profileNotSelected2 = firstProfileButton.getProfileButton();
                        selectedSaver = thirdProfileSaver;
                    }
                }
                leftMenuSelector.moveTo(profilePanelSelected);
                ProfileSaver.setSelectedSaver(selectedSaver);
            }
        }

        if(Objects.equals(page.getTab2(), PageName.PROFILE_HOME.getTab2())) {
            if (enabled) {
                setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);

                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);

                if(page.getTab2().equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }

                profilePlayTabButton.setEnabled(false);
                profileAccountTabButton.setEnabled(true);
                profileAddonsTabButton.setEnabled(true);
                profileSettingsTabButton.setEnabled(true);

                profilePlayTabButton.setVisible(true);
                profileAccountTabButton.setVisible(true);
                profileAddonsTabButton.setVisible(true);
                profileSettingsTabButton.setVisible(true);

                profileHomePage.setVisible(true);

                profileAccountLabel.setBounds(374, 470, 276, 31);
                profileAccountLabel.setVisible(true);
                profileAccountConnectedLabel.setBounds(192, 470, 191, 31);
                profileAccountConnectedLabel.setVisible(true);
                if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                    profileAccountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                    profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                    profileHomePage.enablePlayButtons(true);
                } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    profileHomePage.enablePlayButtons(false);
                }

                corner.setVisible(false);

                subTitleLabel.setText("Jouer");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");
                 
                corner.setVisible(true);

            } else {
                profileNotSelected1.setEnabled(true);
                profileNotSelected2.setEnabled(true);

                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileAddonsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);

                profileHomePage.setVisible(false);

                profileAccountLabel.setVisible(false);
                profileAccountConnectedLabel.setVisible(false);
                if (profileNumber == null) {
                    thirdProfileButton.setEnabled(true);
                }
            }
        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_ACCOUNT.getTab2())) {
            if (enabled) {
                setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);

                if (page.getTab2().equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }

                profilePlayTabButton.setEnabled(true);
                profileAccountTabButton.setEnabled(false);
                profileAddonsTabButton.setEnabled(true);
                profileSettingsTabButton.setEnabled(true);

                profilePlayTabButton.setVisible(true);
                profileAccountTabButton.setVisible(true);
                profileAddonsTabButton.setVisible(true);
                profileSettingsTabButton.setVisible(true);

                profileAccountConnectionButton.setVisible(true);
                profileAccountConnectionMicrosoftButton.setVisible(true);
                profileAccountResetButton.setVisible(true);
                profileAccountTextField.setVisible(true);
                profileAccountPasswordField.setVisible(true);
                profileAccountTextField.setText("");
                profileAccountPasswordField.setText("");

                if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_EMAIL.get()), "none")) {
                    profileAccountTextField.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_EMAIL.get()));
                }

                profileAccountLabel.setBounds(380, 526, 276, 31);
                profileAccountLabel.setVisible(true);
                profileAccountConnectedLabel.setBounds(198, 526, 191, 31);
                profileAccountConnectedLabel.setVisible(true);
                if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                    profileAccountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                    profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                    profileHomePage.enablePlayButtons(true);
                } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    profileHomePage.enablePlayButtons(false);
                }

                corner.setVisible(false);

                subTitleLabel.setText("Compte");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/compte/profilePage-compte.png");

                corner.setVisible(true);

            } else {
                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileAddonsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);
                profileAccountConnectionButton.setVisible(false);
                profileAccountConnectionMicrosoftButton.setVisible(false);
                profileAccountResetButton.setVisible(false);
                profileAccountTextField.setVisible(false);
                profileAccountPasswordField.setVisible(false);

                profileAccountLabel.setVisible(false);
                profileAccountConnectedLabel.setVisible(false);
            }

        } else if (page.getTab2().contains(PageName.PROFILE_ADDONS.getTab2())) {
            if (page.getSubTab3().contains(PageName.PROFILE_ADDONS_MODS.getSubTab3())) {
                if (enabled) {
                    setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, PageName.ABOUT);

                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileAddonsTabButton.setEnabled(false);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileAddonsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profileAddonsShadersButton.setVisible(true);
                    profileAddonsResourcePacksButton.setVisible(true);
                    profileAddonsOptifineSwitchButton.setVisible(true);
                    profileModsFpsmodelPanel.setVisible(true);
                    profileModsBettertpsPanel.setVisible(true);
                    profileModsFallingleavesPanel.setVisible(true);
                    profileModsAppleskinPanel.setVisible(true);
                    profileModsSoundphysicsPanel.setVisible(true);
                    profileModsWaveyCapesPanel.setVisible(true);
                    profileMods3dSkinLayersPanel.setVisible(true);

                    profileAccountLabel.setBounds(380, 577, 276, 31);
                    profileAccountLabel.setVisible(true);
                    profileAccountConnectedLabel.setBounds(198, 577, 191, 31);
                    profileAccountConnectedLabel.setVisible(true);
                    if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                        profileAccountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                        profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                        profileHomePage.enablePlayButtons(true);
                    } else {
                        profileAccountLabel.setText("");
                        profileAccountConnectedLabel.setText("Non connect\u00e9");
                        profileHomePage.enablePlayButtons(false);
                    }

                    corner.setVisible(false);

                    subTitleLabel.setText("Addons - Mods");
                    LauncherSystemTray.changeTrayTooltip();

                    background = getResourceIgnorePath("/assets/launcher/profilesPage/addons/profilePage-addons.png");

                    corner.setVisible(true);
                } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileAddonsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileAddonsShadersButton.setVisible(false);
                    profileAddonsResourcePacksButton.setVisible(false);
                    profileAddonsOptifineSwitchButton.setVisible(false);
                    profileModsFpsmodelPanel.setVisible(false);
                    profileModsBettertpsPanel.setVisible(false);
                    profileModsFallingleavesPanel.setVisible(false);
                    profileModsAppleskinPanel.setVisible(false);
                    profileModsSoundphysicsPanel.setVisible(false);
                    profileModsWaveyCapesPanel.setVisible(false);
                    profileMods3dSkinLayersPanel.setVisible(false);

                    profileAccountLabel.setVisible(false);
                    profileAccountConnectedLabel.setVisible(false);
                }
            } else if (page.getSubTab3().contains(PageName.PROFILE_ADDONS_SHADERS.getSubTab3())) {
                if (enabled) {
                    setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, PageName.ABOUT);

                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileAddonsTabButton.setEnabled(false);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileAddonsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profileAddonsResourcePacksButton.setVisible(true);
                    profileAddonsOptifineSwitchButton.setVisible(true);
                    profileAddonsGoToFolderButton.setVisible(true);
                    profileShadersSeeComparisonButton.setVisible(true);

                    if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV6.getSpecialTab4())) {
                        profileAddonsShadersButton.setVisible(true);
                        profileAddonsShadersButton.setBounds(566, 128);

                        profileShadersChocapicV6LitePanel.setVisible(true);
                        profileShadersChocapicV6LowPanel.setVisible(true);
                        profileShadersChocapicV6MediumPanel.setVisible(true);
                        profileShadersChocapicV6UltraPanel.setVisible(true);
                        profileShadersChocapicV6ExtremePanel.setVisible(true);

                        subTitleLabel.setText("Addons - Shaders (ChocapicV6)");
                    } else if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV7.getSpecialTab4())) {
                        profileAddonsShadersButton.setVisible(true);
                        profileAddonsShadersButton.setBounds(566, 128);

                        profileShadersChocapicV7_1ToasterPanel.setVisible(true);
                        profileShadersChocapicV7_1LitePanel.setVisible(true);
                        profileShadersChocapicV7_1LowPanel.setVisible(true);
                        profileShadersChocapicV7_1MediumPanel.setVisible(true);
                        profileShadersChocapicV7_1UltraPanel.setVisible(true);
                        profileShadersChocapicV7_1ExtremePanel.setVisible(true);

                        subTitleLabel.setText("Addons - Shaders (ChocapicV7)");
                    } else if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV9.getSpecialTab4())) {
                        profileAddonsShadersButton.setVisible(true);
                        profileAddonsShadersButton.setBounds(566, 128);

                        profileShadersChocapicV9LowPanel.setVisible(true);
                        profileShadersChocapicV9MediumPanel.setVisible(true);
                        profileShadersChocapicV9HighPanel.setVisible(true);
                        profileShadersChocapicV9ExtremePanel.setVisible(true);
                        profileShadersChocapicV9_1ExtremePanel.setVisible(true);

                        subTitleLabel.setText("Addons - Shaders (ChocapicV9)");
                    } else {
                        profileAddonsModsButton.setVisible(true);
                        profileAddonsModsButton.setBounds(566, 128);

                        profileShadersChocapicV6PlusButton.setVisible(true);
                        profileShadersChocapicV7_1PlusButton.setVisible(true);
                        profileShadersChocapicV9PlusButton.setVisible(true);
                        profileShadersSeusRenewedPanel.setVisible(true);

                        subTitleLabel.setText("Addons - Shaders");
                    }

                    background = getResourceIgnorePath("/assets/launcher/profilesPage/addons/profilePage-addons.png");

                    profileAccountLabel.setBounds(380, 577, 276, 31);
                    profileAccountLabel.setVisible(true);
                    profileAccountConnectedLabel.setBounds(198, 577, 191, 31);
                    profileAccountConnectedLabel.setVisible(true);
                    if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                        profileAccountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                        profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                        profileHomePage.enablePlayButtons(true);
                    } else {
                        profileAccountLabel.setText("");
                        profileAccountConnectedLabel.setText("Non connect\u00e9");
                        profileHomePage.enablePlayButtons(false);
                    }

                    corner.setVisible(false);

                    LauncherSystemTray.changeTrayTooltip();

                    corner.setVisible(true);

                } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileAddonsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileAddonsModsButton.setVisible(false);
                    profileAddonsResourcePacksButton.setVisible(false);
                    profileAddonsOptifineSwitchButton.setVisible(false);
                    profileAddonsShadersButton.setVisible(false);
                    profileAddonsGoToFolderButton.setVisible(false);
                    profileShadersSeeComparisonButton.setVisible(false);

                    profileShadersChocapicV6PlusButton.setVisible(false);
                    profileShadersChocapicV7_1PlusButton.setVisible(false);
                    profileShadersChocapicV9PlusButton.setVisible(false);
                    profileShadersSeusRenewedPanel.setVisible(false);

                    profileShadersChocapicV6LitePanel.setVisible(false);
                    profileShadersChocapicV6LowPanel.setVisible(false);
                    profileShadersChocapicV6MediumPanel.setVisible(false);
                    profileShadersChocapicV6UltraPanel.setVisible(false);
                    profileShadersChocapicV6ExtremePanel.setVisible(false);

                    profileShadersChocapicV7_1ToasterPanel.setVisible(false);
                    profileShadersChocapicV7_1LitePanel.setVisible(false);
                    profileShadersChocapicV7_1LowPanel.setVisible(false);
                    profileShadersChocapicV7_1MediumPanel.setVisible(false);
                    profileShadersChocapicV7_1UltraPanel.setVisible(false);
                    profileShadersChocapicV7_1ExtremePanel.setVisible(false);

                    profileShadersChocapicV9LowPanel.setVisible(false);
                    profileShadersChocapicV9MediumPanel.setVisible(false);
                    profileShadersChocapicV9HighPanel.setVisible(false);
                    profileShadersChocapicV9ExtremePanel.setVisible(false);
                    profileShadersChocapicV9_1ExtremePanel.setVisible(false);

                    profileAccountLabel.setVisible(false);
                    profileAccountConnectedLabel.setVisible(false);
                }
            }

        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_SETTINGS.getTab2())) {
            if (enabled) {
                setProfilePage(false, "null", PageName.PROFILE_ALL);
                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);

                if(page.getTab2().equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }
                lastSettingsSaver = ProfileSaver.getSelectedSaver();

                profilePlayTabButton.setEnabled(true);
                profileAccountTabButton.setEnabled(true);
                profileAddonsTabButton.setEnabled(true);
                profileSettingsTabButton.setEnabled(false);

                profilePlayTabButton.setVisible(true);
                profileAccountTabButton.setVisible(true);
                profileAddonsTabButton.setVisible(true);
                profileSettingsTabButton.setVisible(true);

                profileSettingsProfileNameTextField.setVisible(true);
                profileSettingsProfileNameTextField.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()));
                profileSettingsHelmIconSwitchButton.setVisible(true);
                profileSettingsAllowedRamSpinner.setVisible(true);
                profileSettingsAllowedRamSpinner.setValue(parseFloat(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_RAM.get())));
                profileSettingsMainProfileSwitchButton.setVisible(true);
                profileSettingsSaveSettings.setVisible(true);

                profileAccountLabel.setBounds(380, 577, 276, 31);
                profileAccountLabel.setVisible(true);
                profileAccountConnectedLabel.setBounds(198, 577, 191, 31);
                profileAccountConnectedLabel.setVisible(true);
                if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                    profileAccountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                    profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                    profileHomePage.enablePlayButtons(true);
                } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    profileHomePage.enablePlayButtons(false);
                }

                corner.setVisible(false);

                subTitleLabel.setText("R\u00e9glages");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/profilesPage/reglages/profilePage-reglages.png");

                corner.setVisible(true);

                lastSettingsProfileName = profileSettingsProfileNameTextField.getText();
                lastSettingsRam = profileSettingsAllowedRamSpinner.getValue().toString();
            } else {
                if (lastSettingsSaver != null) {
                    Saver finalLastSettingsSaver = lastSettingsSaver;
                    String finalLastSettingsProfileName = lastSettingsProfileName;
                    String finalLastSettingsRam = lastSettingsRam;

                    if (!Objects.equals(profileSettingsProfileNameTextField.getText(), finalLastSettingsSaver.get(KEY.SETTINGS_PROFILENAME.get())) || !Objects.equals(profileSettingsAllowedRamSpinner.getValue().toString(), finalLastSettingsSaver.get(KEY.SETTINGS_RAM.get()))) {
                        Thread yes = new Thread(() -> {
                            finalLastSettingsSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME.get(), finalLastSettingsProfileName);
                            finalLastSettingsSaver.set(ProfileSaver.KEY.SETTINGS_RAM.get(), finalLastSettingsRam);
                            profileSettingsProfileNameTextField.setText(finalLastSettingsProfileName);
                            profileSettingsAllowedRamSpinner.setValue(parseFloat(finalLastSettingsRam));
                            lastSettingsProfileName = finalLastSettingsProfileName;
                            lastSettingsRam = finalLastSettingsRam;
                            initProfileButtons();

                            PopUpMessages.doneMessage("Sauvegard\u00e9", "Param\u00e8tres           sauvegard\u00e9s");
                        });
                        Thread no = new Thread(() -> {
                            profileSettingsProfileNameTextField.setText(finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()));
                            profileSettingsAllowedRamSpinner.setValue(parseFloat(finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_RAM.get())));
                            lastSettingsProfileName = finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get());
                            lastSettingsRam = finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_RAM.get());

                            PopUpMessages.errorMessage("Non sauvegard\u00e9", "Param\u00e8tres non       sauvegard\u00e9s");
                        });

                        PopUpMessages.yesNoMessage("Sauvegarder ?", "Voulez-vous          sauvegarder les         param\u00e8tres ?", yes, no);
                    }
                }

                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileAddonsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);

                profileSettingsProfileNameTextField.setVisible(false);
                profileSettingsHelmIconSwitchButton.setVisible(false);
                profileSettingsAllowedRamSpinner.setVisible(false);
                profileSettingsMainProfileSwitchButton.setVisible(false);
                profileSettingsSaveSettings.setVisible(false);

                profileAccountLabel.setVisible(false);
                profileAccountConnectedLabel.setVisible(false);
            }

        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_ALL.getTab2())) {
            firstProfileButton.getProfileButton().setEnabled(true);
            secondProfileButton.getProfileButton().setEnabled(true);
            thirdProfileButton.getProfileButton().setEnabled(true);

            lastSettingsProfileName = profileSettingsProfileNameTextField.getText();
            lastSettingsRam = profileSettingsAllowedRamSpinner.getValue().toString();
            setProfilePage(enabled, null, PageName.PROFILE_HOME);
            setProfilePage(enabled, null, PageName.PROFILE_ACCOUNT);
            setProfilePage(enabled, null, PageName.PROFILE_ADDONS_MODS);
            setProfilePage(enabled, null, PageName.PROFILE_ADDONS_SHADERS);
            setProfilePage(enabled, null, PageName.PROFILE_SETTINGS);
        }

    }

    /**
     * Change la page pour la page principale des changelogs
     * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    private static void setChangesPage(boolean enabled) {
        if (enabled) {
            setNewsPage(false);
            setProfilePage(false, null, PageName.PROFILE_ALL);
            setAboutPage(false, PageName.ABOUT);

            leftMenuSelector.moveTo(changesButton);
            changesButton.getButton().setEnabled(false);

            changelogsVersionComboBox.setVisible(true);
            changelogsTextArea.setVisible(true);

            corner.setVisible(false);

            subTitleLabel.setText(" ");
            titleLabel.setText("Changelogs");
            LauncherSystemTray.changeTrayTooltip();

            int i = LauncherPanel.verifyVersionChangelog();
            changelogsVersionComboBox.setSelectedIndex(i);
            changelogsTextArea.setText(Changelogs.getChangelogsTextsList()[i]);

            background = getResourceIgnorePath("/assets/launcher/changelogsPage/changelogsPage.png");
             
            corner.setVisible(true);
        }else {
            changelogsVersionComboBox.setVisible(false);
            changelogsTextArea.setVisible(false);

            changesButton.getButton().setEnabled(true);
        }
    }

    /**
     * Change la page pour la page principale des contacts
     *
     * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
     * @param page     La PageName
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    private static void setAboutPage(boolean enabled, PageName page) {
        if (Objects.equals(page.getTab2(), PageName.ABOUT_INFOS.getTab2())) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, PageName.PROFILE_ALL);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT_MODS);

                leftMenuSelector.moveTo(aboutButton);
                aboutButton.getButton().setEnabled(false);
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

                aboutButton.getButton().setEnabled(true);
                aboutInfosTabButton.setEnabled(true);
            }
        } else if (Objects.equals(page.getTab2(), PageName.ABOUT_MODS.getTab2())) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, PageName.PROFILE_ALL);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT_INFOS);

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

                aboutButton.getButton().setEnabled(true);
                aboutModsTabButton.setEnabled(true);
            }

        } else {
            setAboutPage(false, PageName.ABOUT_INFOS);
            setAboutPage(false, PageName.ABOUT_MODS);
        }

    }
}
