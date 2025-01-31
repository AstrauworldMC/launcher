package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
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
    public static PageCreator actualPagePanel;

    public static void setPage(boolean e, PageName page) {
        setPage(e, page, getSelectedProfile());
    }

    public static void setPage(boolean e, PageName page, String profileNum) {
        if (e) actualPage = page;

        if (actualPagePanel == null) {
            actualPagePanel = profileHomePage;
        }

        tabManager.setActualPage(page);

        if (Objects.equals(page.getPage1(), PageName.NEWS.getPage1())) {
            setNewsPage(e);
        } else if (Objects.equals(page.getPage1(), PageName.PROFILE_HOME.getPage1())) {
            setProfilePage(e, profileNum, page);
        } else if (Objects.equals(page.getPage1(), PageName.CHANGELOGS.getPage1())) {
            setChangesPage(e);
        } else if (Objects.equals(page.getPage1(), PageName.ABOUT_INFOS.getPage1())) {
            setAboutPage(e, page);
        } else if (Objects.equals(page.getPage1(), PageName.SETTINGS_COLORS.getPage1())) {
            setSettingsPage(e, page);
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
            setSettingsPage(false, PageName.SETTINGS);

            leftMenuSelector.moveTo(newsButton);
            newsButton.getButton().setEnabled(false);

            newsScrollPanel.setVisible(true);
            newsOpenScrollPanel.setVisible(false);
            PageAnimation.animTo(newsScrollPanel);
            actualPagePanel = newsScrollPanel;

            corner.setVisible(false);

            LauncherSystemTray.changeTrayTooltip();

            background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

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
                setSettingsPage(false, PageName.SETTINGS);

                if(page.getTab2().equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }

                PageAnimation.animTo(profileHomePage);
                actualPagePanel = profileHomePage;

                corner.setVisible(false);

                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");
                 
                corner.setVisible(true);

            } else {
                profileNotSelected1.setEnabled(true);
                profileNotSelected2.setEnabled(true);

                profileHomePage.setVisible(false);

                if (profileNumber == null) {
                    thirdProfileButton.setEnabled(true);
                }
            }
        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_WHITELIST_SERVERS.getTab2())) {
            if (enabled) {
                setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);
                setSettingsPage(false, PageName.SETTINGS);

                if (page.getTab2().equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }

                PageAnimation.animTo(profileWhitelistServersPage);
                actualPagePanel = profileWhitelistServersPage;

                corner.setVisible(false);

                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

                corner.setVisible(true);

            } else {

                profileWhitelistServersPage.setVisible(false);
            }

        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_ACCOUNT.getTab2())) {
            if (enabled) {
                setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);
                setSettingsPage(false, PageName.SETTINGS);

                if (page.getTab2().equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }

                PageAnimation.animTo(profileAccountPage);
                actualPagePanel = profileAccountPage;

                if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_EMAIL.get()), "none")) {
                    profileAccountPage.textField.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_EMAIL.get()));
                }
                profileAccountPage.passwordField.setText("");

                corner.setVisible(false);

                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

                corner.setVisible(true);

            } else {

                profileAccountPage.setVisible(false);
            }

        } else if (page.getTab2().contains(PageName.PROFILE_ADDONS.getTab2())) {
            if (page.getSubTab3().contains(PageName.PROFILE_ADDONS_MODS.getSubTab3())) {
                if (enabled) {
                    setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, PageName.ABOUT);
                    setSettingsPage(false, PageName.SETTINGS);

                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);

                    PageAnimation.animTo(profileAddonsModsPage);
                    actualPagePanel = profileAddonsModsPage;

                    corner.setVisible(false);

                    LauncherSystemTray.changeTrayTooltip();

                    background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

                    corner.setVisible(true);
                } else {

                    profileAddonsModsPage.setVisible(false);
                }
            } else if (page.getSubTab3().contains(PageName.PROFILE_ADDONS_SHADERS.getSubTab3())) {
                if (enabled) {
                    setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(false, PageName.ABOUT);
                    setSettingsPage(false, PageName.SETTINGS);

                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);

                    if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV6.getSpecialTab4())) {

                        PageAnimation.animTo(profileAddonsShadersChocapicv6Page);
                        actualPagePanel = profileAddonsShadersChocapicv6Page;

                    } else if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV7.getSpecialTab4())) {

                        PageAnimation.animTo(profileAddonsShadersChocapicv7Page);
                        actualPagePanel = profileAddonsShadersChocapicv7Page;

                    } else if (page.getSpecialTab4().contains(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV9.getSpecialTab4())) {

                        PageAnimation.animTo(profileAddonsShadersChocapicv9Page);
                        actualPagePanel = profileAddonsShadersChocapicv9Page;

                    } else {

                        PageAnimation.animTo(profileAddonsShadersPage);
                        actualPagePanel = profileAddonsShadersPage;

                    }

                    background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

                    corner.setVisible(false);

                    LauncherSystemTray.changeTrayTooltip();

                    corner.setVisible(true);

                } else {

                    profileAddonsModsPage.setVisible(false);
                    profileAddonsShadersPage.setVisible(false);
                    profileAddonsShadersChocapicv6Page.setVisible(false);
                    profileAddonsShadersChocapicv7Page.setVisible(false);
                    profileAddonsShadersChocapicv9Page.setVisible(false);
                }
            }

        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_SETTINGS.getTab2())) {
            if (enabled) {
                setProfilePage(false, getSelectedProfile(), PageName.PROFILE_ALL);
                setNewsPage(false);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);
                setSettingsPage(false, PageName.SETTINGS);

                if(page.getTab2().equals("all")) {
                } else {
                    profileSelected.setEnabled(false);
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);
                }
                lastSettingsSaver = ProfileSaver.getSelectedSaver();

                PageAnimation.animTo(profileSettingsPage);
                actualPagePanel = profileSettingsPage;

                corner.setVisible(false);

                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

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

                profileSettingsPage.setVisible(false);
            }

        } else if (Objects.equals(page.getTab2(), PageName.PROFILE_ALL.getTab2())) {
            firstProfileButton.getProfileButton().setEnabled(true);
            secondProfileButton.getProfileButton().setEnabled(true);
            thirdProfileButton.getProfileButton().setEnabled(true);

            lastSettingsProfileName = profileSettingsPage.profileNameTextField.getText();
            lastSettingsRam = profileSettingsPage.allowedRamSpinner.getValue().toString();
            setProfilePage(enabled, null, PageName.PROFILE_HOME);
            setProfilePage(enabled, null, PageName.PROFILE_WHITELIST_SERVERS);
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
            setSettingsPage(false, PageName.SETTINGS);

            leftMenuSelector.moveTo(changesButton);
            changesButton.getButton().setEnabled(false);

            PageAnimation.animTo(changelogsPage);
            actualPagePanel = changelogsPage;

            corner.setVisible(false);

            LauncherSystemTray.changeTrayTooltip();

            int i = changelogsPage.verifyVersionChangelog();
            changelogsPage.versionComboBox.setSelectedIndex(i);
            changelogsPage.textArea.setText(Changelogs.getChangelogsTextsList()[i]);

            background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");
             
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
                setSettingsPage(false, PageName.SETTINGS);

                leftMenuSelector.moveTo(aboutButton);
                aboutButton.getButton().setEnabled(false);

                PageAnimation.animTo(aboutInfosPage);
                aboutInfosPage.eastereggsLabel.setText(EasterEggs.getNumberOfFoundEasterEggs() + "/" + EasterEggs.getNumberTotalEasterEggs());
                actualPagePanel = aboutInfosPage;

                corner.setVisible(false);

                subTitleLabel.setText("Infos");
                titleLabel.setText("\u00c0 propos");
                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");
                 
                corner.setVisible(true);
            } else {

                aboutInfosPage.setVisible(false);

                aboutButton.getButton().setEnabled(true);
            }
        } else if (Objects.equals(page.getTab2(), PageName.ABOUT_MODS.getTab2())) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, PageName.PROFILE_ALL);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT_INFOS);
                setSettingsPage(false, PageName.SETTINGS);

                leftMenuSelector.moveTo(aboutButton);
                aboutButton.setEnabled(false);

                PageAnimation.animTo(aboutModsPage);
                actualPagePanel = aboutModsPage;

                corner.setVisible(false);

                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

            } else {
                aboutButton.getButton().setEnabled(true);
            }

        } else {
            setAboutPage(false, PageName.ABOUT_INFOS);
            setAboutPage(false, PageName.ABOUT_MODS);
        }

    }

    private static void setSettingsPage(boolean enabled, PageName page) {
        if (Objects.equals(page.getTab2(), PageName.SETTINGS_COLORS.getTab2())) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, PageName.PROFILE_ALL);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);
                setSettingsPage(false, PageName.SETTINGS_DISCORD);

                leftMenuSelector.moveTo(settingsButton);
                settingsButton.getButton().setEnabled(false);

                PageAnimation.animTo(settingsColorsPage);
                actualPagePanel = settingsColorsPage;

                corner.setVisible(false);

                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

                corner.setVisible(true);
            } else {
                settingsButton.getButton().setEnabled(true);
            }
        } else if (Objects.equals(page.getTab2(), PageName.SETTINGS_DISCORD.getTab2())) {
            if (enabled) {
                setNewsPage(false);
                setProfilePage(false, null, PageName.PROFILE_ALL);
                setChangesPage(false);
                setAboutPage(false, PageName.ABOUT);
                setSettingsPage(false, PageName.SETTINGS_COLORS);

                leftMenuSelector.moveTo(settingsButton);
                settingsButton.setEnabled(false);

                PageAnimation.animTo(settingsDiscordPage);
                actualPagePanel = settingsDiscordPage;

                corner.setVisible(false);

                LauncherSystemTray.changeTrayTooltip();

                background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

            } else {
                settingsButton.getButton().setEnabled(true);
            }

        } else {
            setSettingsPage(false, PageName.SETTINGS_COLORS);
            setSettingsPage(false, PageName.SETTINGS_DISCORD);
        }

    }
}
