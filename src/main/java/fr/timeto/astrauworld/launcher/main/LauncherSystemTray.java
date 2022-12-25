package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
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
        MenuItem aboutEasterEggItem = new MenuItem("D\u00e9tails");
        final int[] numberOfTrolled = {0};

        MenuItem discordItem = new MenuItem("Discord");
        MenuItem chatItem = new MenuItem("Tchat du serveur");
        Menu problemMenu = new Menu("Report un bug");
        MenuItem problemLauncherItem = new MenuItem("Launcher");
        MenuItem problemServerItem = new MenuItem("Serveur");
        Menu suggestMenu = new Menu("Suggestion");
        MenuItem suggestLauncherItem = new MenuItem("Launcher");
        MenuItem suggestServerItem = new MenuItem("Serveur");


        MenuItem githubItem = new MenuItem("GitHub");
        MenuItem twitterItem = new MenuItem("Twitter");
        MenuItem mailItem = new MenuItem("Mail");

        /* Création du menu Quitter */
        MenuItem exitItem = new MenuItem("Quitter");

        ActionListener listener = e -> {
            Object src = e.getSource();

            if (src == exitItem) {
                System.exit(0);
            } else if (src == newsPageItem) {
                PageChange.setNewsPage(true);
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfilePlayPageItem) {
                PageChange.setProfilePage(true, "1", "home");
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfileAccountPageItem) {
                PageChange.setProfilePage(true, "1", "account");
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfileModsPageItem) {
                PageChange.setProfilePage(true, "1", "mods");
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfileSettingsPageItem) {
                PageChange.setProfilePage(true, "1", "settings");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfilePlayPageItem) {
                PageChange.setProfilePage(true, "2", "home");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfileAccountPageItem) {
                PageChange.setProfilePage(true, "2", "account");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfileModsPageItem) {
                PageChange.setProfilePage(true, "2", "mods");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfileSettingsPageItem) {
                PageChange.setProfilePage(true, "2", "settings");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfilePlayPageItem) {
                PageChange.setProfilePage(true, "3", "home");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfileAccountPageItem) {
                PageChange.setProfilePage(true, "3", "account");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfileModsPageItem) {
                PageChange.setProfilePage(true, "3", "mods");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfileSettingsPageItem) {
                PageChange.setProfilePage(true, "3", "settings");
                LauncherFrame.getInstance().toFront();
            } else if (src == changelogsPageItem) {
                PageChange.setChangesPage(true);
                LauncherFrame.getInstance().toFront();
            } else if (src == aboutInfosPageItem) {
                PageChange.setAboutPage("infos", true);
                LauncherFrame.getInstance().toFront();
            } else if (src == aboutModsPageItem) {
                PageChange.setAboutPage("mods", true);
                LauncherFrame.getInstance().toFront();
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
            } else if (src == discordItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/GpqB5eES5r").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == chatItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/863JZ4yK8Q").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == problemLauncherItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/r3kRQeBQAW").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == problemServerItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/6cbBXU7YDk").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == suggestLauncherItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/AxCF3VaBgT").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == suggestServerItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/rkA9vNRNNJ").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == githubItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/AstrauworldMC").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == twitterItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://twitter.com/AstrauworldMC").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == mailItem) {
                try {
                    Desktop.getDesktop().mail(new URL("mailto:astrauworld.minecraft@gmail.com").toURI());
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
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

        discordItem.addActionListener(listener);
        chatItem.addActionListener(listener);
        problemLauncherItem.addActionListener(listener);
        problemServerItem.addActionListener(listener);
        suggestLauncherItem.addActionListener(listener);
        suggestServerItem.addActionListener(listener);

        githubItem.addActionListener(listener);
        twitterItem.addActionListener(listener);
        mailItem.addActionListener(listener);

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

        popup.addSeparator();
        popup.add(discordItem);
        popup.add(chatItem);

        popup.add(problemMenu);
        problemMenu.add(problemLauncherItem);
        problemMenu.add(problemServerItem);

        popup.add(suggestMenu);
        suggestMenu.add(suggestLauncherItem);
        suggestMenu.add(suggestServerItem);

        popup.addSeparator();
        popup.add(githubItem);
        popup.add(twitterItem);
        popup.add(mailItem);

        popup.addSeparator(); // Séparateur de menu __________
        popup.add(exitItem);

        /* Création de l'icône */
        trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH), "Astrauworld Launcher");
        trayIcon.setPopupMenu(popup);

        try {
            /* Affiche l'icône dans la zone de notification */
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

        MenuItem discordItem = new MenuItem("Discord");
        MenuItem chatItem = new MenuItem("Tchat du serveur");
        Menu problemMenu = new Menu("Report un bug");
        MenuItem problemLauncherItem = new MenuItem("Launcher");
        MenuItem problemServerItem = new MenuItem("Serveur");
        Menu suggestMenu = new Menu("Suggestion");
        MenuItem suggestLauncherItem = new MenuItem("Launcher");
        MenuItem suggestServerItem = new MenuItem("Serveur");

        MenuItem githubItem = new MenuItem("GitHub");
        MenuItem twitterItem = new MenuItem("Twitter");
        MenuItem mailItem = new MenuItem("Mail");

        ActionListener listener = e -> {
            Object src = e.getSource();

            if (src == exitItem) {
                Launcher.process.destroy();
                System.exit(0);
            } else if (src == closeMc) {
                Launcher.process.destroy();
            } else if (src == discordItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/GpqB5eES5r").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == chatItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/863JZ4yK8Q").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == problemLauncherItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/r3kRQeBQAW").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == problemServerItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/6cbBXU7YDk").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == suggestLauncherItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/AxCF3VaBgT").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == suggestServerItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://discord.gg/rkA9vNRNNJ").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == githubItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://github.com/AstrauworldMC").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == twitterItem) {
                try {
                    Desktop.getDesktop().browse(new URL("https://twitter.com/AstrauworldMC").toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == mailItem) {
                try {
                    Desktop.getDesktop().mail(new URL("mailto:astrauworld.minecraft@gmail.com").toURI());
                } catch (IOException | URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };

        closeMc.addActionListener(listener);
        exitItem.addActionListener(listener);

        discordItem.addActionListener(listener);
        chatItem.addActionListener(listener);
        problemLauncherItem.addActionListener(listener);
        problemServerItem.addActionListener(listener);
        suggestLauncherItem.addActionListener(listener);
        suggestServerItem.addActionListener(listener);

        githubItem.addActionListener(listener);
        twitterItem.addActionListener(listener);
        mailItem.addActionListener(listener);


        /* Ajoute tous les menus principaux au menu popup */
        popup.add(discordItem);
        popup.add(chatItem);

        popup.add(problemMenu);
        problemMenu.add(problemLauncherItem);
        problemMenu.add(problemServerItem);

        popup.add(suggestMenu);
        suggestMenu.add(suggestLauncherItem);
        suggestMenu.add(suggestServerItem);

        popup.addSeparator();
        popup.add(githubItem);
        popup.add(twitterItem);
        popup.add(mailItem);
        popup.addSeparator(); // Séparateur de menu __________
        popup.add(closeMc);
        popup.add(exitItem);

        ProfileSaver.initSelectedSaver(profile);
        Saver saver = ProfileSaver.getSelectedSaver();

        /* Création de l'icône */
        trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH), "Astrauworld - Profil " + saver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME) + " (" + saver.get(ProfileSaver.KEY.INFOS_NAME) + ")");
        trayIcon.setPopupMenu(popup);

        /* récupère la zone de notification */
        SystemTray tray = SystemTray.getSystemTray();
        try {
            /* Affiche l'icône dans la zone de notification */
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
