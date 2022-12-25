package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.pagesutilities.ChangePage;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;

/**
 * La classe pour le système tray (icône dans la zone de notification)
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @since Beta2.2.0
 */
public class LauncherSystemTray {
    /* Récupère l'image */
    static BufferedImage trayIconImage = getResourceIgnorePath("/assets.launcher/main/logo.png");
    static int trayIconWidth = new TrayIcon(trayIconImage).getSize().width;

    static TrayIcon trayIcon;

    /* récupère la zone de notification */
    static SystemTray tray = SystemTray.getSystemTray();

    public static void initLauncherSystemTray() {
        tray.remove(trayIcon);

        /* Vérifie si l'OS supporte la zone de notification */
        if (!SystemTray.isSupported()) {
            Launcher.println("SystemTray is not supported");
            return;
        }

        /* Création du menu popup */
        PopupMenu popup = new PopupMenu();

        MenuItem newsPageItem = new MenuItem("Actualit\u00e9s");

        String firstProfileName;
        if ((Objects.equals(ProfileSaver.firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME), "no")) | (Objects.equals(ProfileSaver.firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME), "none"))) {
            firstProfileName = "Vide";
        } else {
            firstProfileName = ProfileSaver.firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME);
        }
        Menu firstProfileMenu = new Menu("Profil 1 - " + firstProfileName);
        MenuItem firstProfilePlayPageItem = new MenuItem("Jouer");
        MenuItem firstProfileAccountPageItem = new MenuItem("Compte");
        MenuItem firstProfileModsPageItem = new MenuItem("Mods");
        MenuItem firstProfileSettingsPageItem = new MenuItem("Param\u00e8tres");

        String secondProfileName;
        if ((Objects.equals(ProfileSaver.secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME), "no")) | (Objects.equals(ProfileSaver.secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME), "none"))) {
            secondProfileName = "Vide";
        } else {
            secondProfileName = ProfileSaver.secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME);
        }
        Menu secondProfileMenu = new Menu("Profil 2 - " + secondProfileName);
        MenuItem secondProfilePlayPageItem = new MenuItem("Jouer");
        MenuItem secondProfileAccountPageItem = new MenuItem("Compte");
        MenuItem secondProfileModsPageItem = new MenuItem("Mods");
        MenuItem secondProfileSettingsPageItem = new MenuItem("Param\u00e8tres");

        String thirdProfileName;
        if ((Objects.equals(ProfileSaver.thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME), "no")) | (Objects.equals(ProfileSaver.thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME), "none"))) {
            thirdProfileName = "Vide";
        } else {
            thirdProfileName = ProfileSaver.thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME);
        }
        Menu thirdProfileMenu = new Menu("Profil 3 - " + thirdProfileName);
        MenuItem thirdProfilePlayPageItem = new MenuItem("Jouer");
        MenuItem thirdProfileAccountPageItem = new MenuItem("Compte");
        MenuItem thirdProfileModsPageItem = new MenuItem("Mods");
        MenuItem thirdProfileSettingsPageItem = new MenuItem("Param\u00e8tres");

        MenuItem changelogsPageItem = new MenuItem("Changelogs");

        Menu aboutMenu = new Menu("\u00c0 propos");
        MenuItem aboutInfosPageItem = new MenuItem("Infos");
        MenuItem aboutModsPageItem = new MenuItem("Mods");
        MenuItem aboutEasterEggItem = new MenuItem("");
        final int[] numberOfTrolled = {0};

        /* Création du menu Quitter */
        MenuItem exitItem = new MenuItem("Quitter");

        ActionListener listener = e -> {
            Object src = e.getSource();

            if (src == exitItem) {
                System.exit(0);
            } else if (src == newsPageItem) {
                ChangePage.setNewsPage(true);
            } else if (src == firstProfilePlayPageItem) {
                ChangePage.setProfilePage(true, "1", "home");
            } else if (src == firstProfileAccountPageItem) {
                ChangePage.setProfilePage(true, "1", "account");
            } else if (src == firstProfileModsPageItem) {
                ChangePage.setProfilePage(true, "1", "mods");
            } else if (src == firstProfileSettingsPageItem) {
                ChangePage.setProfilePage(true, "1", "settings");
            } else if (src == secondProfilePlayPageItem) {
                ChangePage.setProfilePage(true, "2", "home");
            } else if (src == secondProfileAccountPageItem) {
                ChangePage.setProfilePage(true, "2", "account");
            } else if (src == secondProfileModsPageItem) {
                ChangePage.setProfilePage(true, "2", "mods");
            } else if (src == secondProfileSettingsPageItem) {
                ChangePage.setProfilePage(true, "2", "settings");
            } else if (src == thirdProfilePlayPageItem) {
                ChangePage.setProfilePage(true, "3", "home");
            } else if (src == thirdProfileAccountPageItem) {
                ChangePage.setProfilePage(true, "3", "account");
            } else if (src == thirdProfileModsPageItem) {
                ChangePage.setProfilePage(true, "3", "mods");
            } else if (src == thirdProfileSettingsPageItem) {
                ChangePage.setProfilePage(true, "3", "settings");
            } else if (src == changelogsPageItem) {
                ChangePage.setChangesPage(true);
            } else if (src == aboutInfosPageItem) {
                ChangePage.setAboutPage("infos", true);
            } else if (src == aboutModsPageItem) {
                ChangePage.setAboutPage("mods", true);
            } else if (src == aboutEasterEggItem) {
                if (numberOfTrolled[0] < 8) {
                    try {
                        Desktop.getDesktop().browse(new URL("https://youtu.be/BsIa_LKojJI").toURI());
                    } catch (IOException | URISyntaxException ignored) {
                    }
                    EasterEggs.setEatereggAsFound(EasterEggs.youveBeenTrolled);

                } else {
                    try {
                        Desktop.getDesktop().browse(new URL("https://youtu.be/ZZ5LpwO-An4?t=29").toURI());
                    } catch (IOException | URISyntaxException ignored) {}
                    EasterEggs.setEatereggAsFound(EasterEggs.heyyaaeyaaaeyaeyaa);

                }
                numberOfTrolled[0] += 1;
            }

            LauncherFrame.getInstance().toFront();
        };

        newsPageItem.addActionListener(listener);

        firstProfilePlayPageItem.addActionListener(listener);
        firstProfileAccountPageItem.addActionListener(listener);
        firstProfileModsPageItem.addActionListener(listener);
        firstProfileSettingsPageItem.addActionListener(listener);

        secondProfilePlayPageItem.addActionListener(listener);
        secondProfileAccountPageItem.addActionListener(listener);
        secondProfileModsPageItem.addActionListener(listener);
        secondProfileSettingsPageItem.addActionListener(listener);

        thirdProfilePlayPageItem.addActionListener(listener);
        thirdProfileAccountPageItem.addActionListener(listener);
        thirdProfileModsPageItem.addActionListener(listener);
        thirdProfileSettingsPageItem.addActionListener(listener);

        changelogsPageItem.addActionListener(listener);

        aboutInfosPageItem.addActionListener(listener);
        aboutModsPageItem.addActionListener(listener);
        aboutEasterEggItem.addActionListener(listener);

        exitItem.addActionListener(listener);

        /* Ajoute tous les menus principaux au menu popup */
        popup.add(newsPageItem);

        popup.add(firstProfileMenu);
        firstProfileMenu.add(firstProfilePlayPageItem);
        firstProfileMenu.add(firstProfileAccountPageItem);
        firstProfileMenu.add(firstProfileModsPageItem);
        firstProfileMenu.add(firstProfileSettingsPageItem);

        popup.add(secondProfileMenu);
        secondProfileMenu.add(secondProfilePlayPageItem);
        secondProfileMenu.add(secondProfileAccountPageItem);
        secondProfileMenu.add(secondProfileModsPageItem);
        secondProfileMenu.add(secondProfileSettingsPageItem);

        popup.add(thirdProfileMenu);
        thirdProfileMenu.add(thirdProfilePlayPageItem);
        thirdProfileMenu.add(thirdProfileAccountPageItem);
        thirdProfileMenu.add(thirdProfileModsPageItem);
        thirdProfileMenu.add(thirdProfileSettingsPageItem);

        popup.add(changelogsPageItem);

        popup.add(aboutMenu);
        aboutMenu.add(aboutInfosPageItem);
        aboutMenu.add(aboutModsPageItem);
        aboutMenu.add(aboutEasterEggItem);

        popup.addSeparator(); // Séparateur de menu __________
        popup.add(exitItem);

        /* Création de l'icône */
        trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH), "Astrauworld Launcher");
        trayIcon.setPopupMenu(popup);

        try {
            /* Affiche l'icone dans la zone de notification */
            tray.add(trayIcon);
        } catch (AWTException e) {
            Launcher.println("TrayIcon could not be added.");
        }
    }

    public static void initGameSystemTray(String profile) {
        tray.remove(trayIcon);

        /* Vérifie si l'OS supporte la zone de notification */
        if (!SystemTray.isSupported()) {
            Launcher.println("SystemTray is not supported");
            return;
        }

        /* Création du menu popup */
        PopupMenu popup = new PopupMenu();

        MenuItem closeMc = new MenuItem("Fermer Minecraft");

        /* Création du menu Quitter */
        MenuItem exitItem = new MenuItem("Quitter");

        ActionListener listener = e -> {
            Object src = e.getSource();

            if (src == exitItem) {
                Launcher.process.destroy();
                System.exit(0);
            } else if (src == closeMc) {
                Launcher.process.destroy();
            }
        };

        closeMc.addActionListener(listener);
        exitItem.addActionListener(listener);

        /* Ajoute tous les menus principaux au menu popup */
        popup.add(closeMc);
        popup.addSeparator(); // Séparateur de menu __________
        popup.add(exitItem);

        ProfileSaver.initSelectedSaver(profile);
        Saver saver = ProfileSaver.getSelectedSaver();

        /* Création de l'icône */
        trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH), "Astrauworld - Profil " + saver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME) + " (" + saver.get(ProfileSaver.KEY.INFOS_NAME) + ")");
        trayIcon.setPopupMenu(popup);

        /* récupère la zone de notification */
        SystemTray tray = SystemTray.getSystemTray();
        try {
            /* Affiche l'icone dans la zone de notification */
            tray.add(trayIcon);
        } catch (AWTException e) {
            Launcher.println("TrayIcon could not be added.");
        }
    }

    public static void changeTrayTooltip() {
        if (Objects.equals(LauncherPanel.Components.subTitleLabel.getText(), "") || Objects.equals(LauncherPanel.Components.subTitleLabel.getText(), " ")) {
            trayIcon.setToolTip("Astrauworld Launcher - " + LauncherPanel.Components.titleLabel.getText());
        } else if (LauncherPanel.Components.titleLabel.getText().contains("Profil")) {
            ProfileSaver.initSelectedSaver();
            trayIcon.setToolTip("Astrauworld Launcher - " + LauncherPanel.Components.titleLabel.getText() + " [" + ProfileSaver.selectedSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME) + "] (" + LauncherPanel.Components.subTitleLabel.getText() + ")");
        } else {
            trayIcon.setToolTip("Astrauworld Launcher - " + LauncherPanel.Components.titleLabel.getText() + " (" + LauncherPanel.Components.subTitleLabel.getText() + ")");
        }
    }
}
