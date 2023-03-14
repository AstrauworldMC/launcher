package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredButton;
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
    public static PageName actualPage;

    public static void setPage(boolean e, PageName page) {
        actualPage = page;
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
        actualPage = page;
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
                switch (profileNumber) {
                    case "1":
                        profileSelected = firstProfileButton.getProfileButton();
                        profilePanelSelected = firstProfileButton;
                        profileNotSelected1 = secondProfileButton.getProfileButton();
                        profileNotSelected2 = thirdProfileButton.getProfileButton();
                        titleLabel.setText("Profil 1");
                        selectedSaver = firstProfileSaver;
                        setSelectedProfile("1");
                        break;
                    case "2":
                        profileSelected = secondProfileButton.getProfileButton();
                        profilePanelSelected = secondProfileButton;
                        profileNotSelected2 = thirdProfileButton.getProfileButton();
                        titleLabel.setText("Profil 2");
                        selectedSaver = secondProfileSaver;
                        setSelectedProfile("2");
                        break;
                    case "3":
                        profileSelected = thirdProfileButton.getProfileButton();
                        profilePanelSelected = thirdProfileButton;
                        profileNotSelected1 = secondProfileButton.getProfileButton();
                        profileNotSelected2 = firstProfileButton.getProfileButton();
                        titleLabel.setText("Profil 3");
                        selectedSaver = thirdProfileSaver;
                        setSelectedProfile("3");
                        break;
                    case "null":
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
                        break;
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

                profileAccountPage.setVisible(true);

                if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_EMAIL.get()), "none")) {
                    profileAccountPage.textField.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_EMAIL.get()));
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

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");

                corner.setVisible(true);

            } else {
                profilePlayTabButton.setVisible(false);
                profileAccountTabButton.setVisible(false);
                profileAddonsTabButton.setVisible(false);
                profileSettingsTabButton.setVisible(false);

                profileAccountPage.setVisible(false);

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

                    profileAddonsModsPage.setVisible(true);

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

                    background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");

                    corner.setVisible(true);
                } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileAddonsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileAddonsModsPage.setVisible(false);

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

                    if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV6.getSpecialTab4())) {

                        profileAddonsShadersChocapicv6Page.setVisible(true);

                    } else if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV7.getSpecialTab4())) {

                        profileAddonsShadersChocapicv7Page.setVisible(true);

                    } else if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV9.getSpecialTab4())) {

                        profileAddonsShadersChocapicv9Page.setVisible(true);

                    } else {

                        profileAddonsShadersPage.setVisible(true);

                    }

                    background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");

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

                    profileAddonsModsPage.setVisible(false);
                    profileAddonsShadersPage.setVisible(false);
                    profileAddonsShadersChocapicv6Page.setVisible(false);
                    profileAddonsShadersChocapicv7Page.setVisible(false);
                    profileAddonsShadersChocapicv9Page.setVisible(false);

                    profileAccountLabel.setVisible(false);
                    profileAccountConnectedLabel.setVisible(false);
                }
            }

        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_SETTINGS.getTab2())) {
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
                lastSettingsSaver = ProfileSaver.getSelectedSaver();

                profilePlayTabButton.setEnabled(true);
                profileAccountTabButton.setEnabled(true);
                profileAddonsTabButton.setEnabled(true);
                profileSettingsTabButton.setEnabled(false);

                profilePlayTabButton.setVisible(true);
                profileAccountTabButton.setVisible(true);
                profileAddonsTabButton.setVisible(true);
                profileSettingsTabButton.setVisible(true);

                profileSettingsPage.setVisible(true);

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

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");

                corner.setVisible(true);

                lastSettingsProfileName = profileSettingsPage.profileNameTextField.getText();
                lastSettingsRam = profileSettingsPage.allowedRamSpinner.getValue().toString();
            } else {
                if (lastSettingsSaver != null) {
                    Saver finalLastSettingsSaver = lastSettingsSaver;
                    String finalLastSettingsProfileName = lastSettingsProfileName;
                    String finalLastSettingsRam = lastSettingsRam;

                    if (!Objects.equals(profileSettingsPage.profileNameTextField.getText(), finalLastSettingsSaver.get(KEY.SETTINGS_PROFILENAME.get())) || !Objects.equals(profileSettingsPage.allowedRamSpinner.getValue().toString(), finalLastSettingsSaver.get(KEY.SETTINGS_RAM.get()))) {
                        Thread yes = new Thread(() -> {
                            finalLastSettingsSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME.get(), finalLastSettingsProfileName);
                            finalLastSettingsSaver.set(ProfileSaver.KEY.SETTINGS_RAM.get(), finalLastSettingsRam);
                            profileSettingsPage.profileNameTextField.setText(finalLastSettingsProfileName);
                            profileSettingsPage.allowedRamSpinner.setValue(parseFloat(finalLastSettingsRam));
                            lastSettingsProfileName = finalLastSettingsProfileName;
                            lastSettingsRam = finalLastSettingsRam;
                            initProfileButtons();

                            PopUpMessages.doneMessage("Sauvegard\u00e9", "Param\u00e8tres           sauvegard\u00e9s");
                        });
                        Thread no = new Thread(() -> {
                            profileSettingsPage.profileNameTextField.setText(finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()));
                            profileSettingsPage.allowedRamSpinner.setValue(parseFloat(finalLastSettingsSaver.get(ProfileSaver.KEY.SETTINGS_RAM.get())));
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

                profileSettingsPage.setVisible(false);

                profileAccountLabel.setVisible(false);
                profileAccountConnectedLabel.setVisible(false);
            }

        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_ALL.getTab2())) {
            firstProfileButton.getProfileButton().setEnabled(true);
            secondProfileButton.getProfileButton().setEnabled(true);
            thirdProfileButton.getProfileButton().setEnabled(true);

            lastSettingsProfileName = profileSettingsPage.profileNameTextField.getText();
            lastSettingsRam = profileSettingsPage.allowedRamSpinner.getValue().toString();
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

            changelogsPage.setVisible(true);

            corner.setVisible(false);

            LauncherSystemTray.changeTrayTooltip();

            int i = changelogsPage.verifyVersionChangelog();
            changelogsPage.versionComboBox.setSelectedIndex(i);
            changelogsPage.textArea.setText(Changelogs.getChangelogsTextsList()[i]);

            background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");
             
            corner.setVisible(true);
        }else {
            changelogsPage.setVisible(false);

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

                aboutInfosPage.setVisible(true);
                aboutInfosPage.eastereggsLabel.setText(EasterEggs.getNumberOfFoundEasterEggs() + "/" + EasterEggs.getNumberTotalEasterEggs());

                corner.setVisible(false);

                subTitleLabel.setText("Infos");
                titleLabel.setText("\u00c0 propos");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");
                 
                corner.setVisible(true);
            } else {
                aboutInfosTabButton.setVisible(false);
                aboutModsTabButton.setVisible(false);

                aboutInfosPage.setVisible(false);

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
