package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.secret.Infos;
import net.harawata.appdirs.AppDirsFactory;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.Launcher.parseUnicode;
import static fr.timeto.astrauworld.launcher.main.Launcher.println;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.timutilslib.TimFilesUtils.copyFile;
import static fr.timeto.timutilslib.TimFilesUtils.downloadFromInternet;

/**
 * La classe pour le système tray (icône dans la zone de notification)
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @since Beta2.2.0
 */
public class LauncherSystemTray {
    /* Récupère l'image */
    static BufferedImage trayIconImage = getResourceIgnorePath("/assets/launcher/main/logo.png");
    static int trayIconWidth = new TrayIcon(trayIconImage).getSize().width;

    static TrayIcon trayIcon;

    /* récupère la zone de notification */
    static SystemTray tray = SystemTray.getSystemTray();

    public static void stop() {
        tray.remove(trayIcon);
    }

    public static void initLauncherSystemTray() {
        tray.remove(trayIcon);

        /* Vérifie si l'OS supporte la zone de notification */
        if (!SystemTray.isSupported()) {
            println("SystemTray is not supported");
            return;
        }

        /* Création du menu popup */
        PopupMenu popup = new PopupMenu();

        MenuItem newsPageItem = new MenuItem("Actualit\u00e9s");

        String firstProfileName;
        if ((Objects.equals(ProfileSaver.firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()), "no")) | (Objects.equals(ProfileSaver.firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()), "none"))) {
            firstProfileName = "Vide";
        } else {
            firstProfileName = ProfileSaver.firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get());
        }
        Menu firstProfileMenu = new Menu("Profil 1 - " + firstProfileName);
        MenuItem firstProfilePlayPageItem = new MenuItem("Jouer");
        MenuItem firstProfileAccountPageItem = new MenuItem("Compte");
        MenuItem firstProfileModsPageItem = new MenuItem("Addons");
        MenuItem firstProfileSettingsPageItem = new MenuItem("Param\u00e8tres");

        String secondProfileName;
        if ((Objects.equals(ProfileSaver.secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()), "no")) | (Objects.equals(ProfileSaver.secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()), "none"))) {
            secondProfileName = "Vide";
        } else {
            secondProfileName = ProfileSaver.secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get());
        }
        Menu secondProfileMenu = new Menu("Profil 2 - " + secondProfileName);
        MenuItem secondProfilePlayPageItem = new MenuItem("Jouer");
        MenuItem secondProfileAccountPageItem = new MenuItem("Compte");
        MenuItem secondProfileModsPageItem = new MenuItem("Addons");
        MenuItem secondProfileSettingsPageItem = new MenuItem("Param\u00e8tres");

        String thirdProfileName;
        if ((Objects.equals(ProfileSaver.thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()), "no")) | (Objects.equals(ProfileSaver.thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()), "none"))) {
            thirdProfileName = "Vide";
        } else {
            thirdProfileName = ProfileSaver.thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get());
        }
        Menu thirdProfileMenu = new Menu("Profil 3 - " + thirdProfileName);
        MenuItem thirdProfilePlayPageItem = new MenuItem("Jouer");
        MenuItem thirdProfileAccountPageItem = new MenuItem("Compte");
        MenuItem thirdProfileModsPageItem = new MenuItem("Addons");
        MenuItem thirdProfileSettingsPageItem = new MenuItem("Param\u00e8tres");

        MenuItem changelogsPageItem = new MenuItem("Changelogs");

        Menu aboutMenu = new Menu("\u00c0 propos");
        MenuItem aboutInfosPageItem = new MenuItem("Infos");
        MenuItem aboutModsPageItem = new MenuItem("Mods");
        MenuItem aboutEasterEggItem = new MenuItem("D\u00e9tails");
        final int[] numberOfTrolled = {0};

        MenuItem discordItem = new MenuItem("Discord");
        MenuItem chatItem = new MenuItem("Tchat du serveur");
        MenuItem mapItem = new MenuItem("Map du serveur");
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
                PageChange.setPage(true, PageName.NEWS);
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfilePlayPageItem) {
                PageChange.setPage(true, PageName.PROFILE_HOME, "1");
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfileAccountPageItem) {
                PageChange.setPage(true, PageName.PROFILE_ACCOUNT, "1");
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfileModsPageItem) {
                PageChange.setPage(true, PageName.PROFILE_ADDONS_MODS, "1");
                LauncherFrame.getInstance().toFront();
            } else if (src == firstProfileSettingsPageItem) {
                PageChange.setPage(true, PageName.PROFILE_SETTINGS, "1");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfilePlayPageItem) {
                PageChange.setPage(true, PageName.PROFILE_HOME, "2");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfileAccountPageItem) {
                PageChange.setPage(true, PageName.PROFILE_ACCOUNT, "2");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfileModsPageItem) {
                PageChange.setPage(true, PageName.PROFILE_ADDONS_MODS, "2");
                LauncherFrame.getInstance().toFront();
            } else if (src == secondProfileSettingsPageItem) {
                PageChange.setPage(true, PageName.PROFILE_SETTINGS, "2");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfilePlayPageItem) {
                PageChange.setPage(true, PageName.PROFILE_HOME, "3");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfileAccountPageItem) {
                PageChange.setPage(true, PageName.PROFILE_ACCOUNT, "3");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfileModsPageItem) {
                PageChange.setPage(true, PageName.PROFILE_ADDONS_MODS, "3");
                LauncherFrame.getInstance().toFront();
            } else if (src == thirdProfileSettingsPageItem) {
                PageChange.setPage(true, PageName.PROFILE_SETTINGS, "3");
                LauncherFrame.getInstance().toFront();
            } else if (src == changelogsPageItem) {
                PageChange.setPage(true, PageName.CHANGELOGS);
                LauncherFrame.getInstance().toFront();
            } else if (src == aboutInfosPageItem) {
                PageChange.setPage(true, PageName.ABOUT_INFOS);
                LauncherFrame.getInstance().toFront();
            } else if (src == aboutModsPageItem) {
                PageChange.setPage(true, PageName.ABOUT_MODS);
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
            } else if (src == mapItem) {
                try {
                    Desktop.getDesktop().browse(new URL(Infos.getServerHostname() + ":" + LauncherFrame.launcherProperties.getProperty("bluemapPort")).toURI());
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
        mapItem.addActionListener(listener);
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
        popup.add(mapItem);

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
            println("TrayIcon could not be added.");
        }
    }

    public static void initGameSystemTray(String profile) {
        tray.remove(trayIcon);

        /* Vérifie si l'OS supporte la zone de notification */
        if (!SystemTray.isSupported()) {
            println("SystemTray is not supported");
            return;
        }

        /* Création du menu popup */
        PopupMenu popup = new PopupMenu();

        MenuItem closeMc = new MenuItem("Fermer Minecraft");

        /* Création du menu Quitter */
        MenuItem exitItem = new MenuItem("Quitter");

        MenuItem discordItem = new MenuItem("Discord");
        MenuItem chatItem = new MenuItem("Tchat du serveur");
        MenuItem serverInfos = new MenuItem("Infos du serveur");
        MenuItem mapItem = new MenuItem("Map du serveur");
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
            } else if (src == mapItem) {
                try {
                    Desktop.getDesktop().browse(new URL(LauncherFrame.launcherProperties.getProperty("blueMapLink")).toURI());
                } catch (IOException | URISyntaxException ignored) {}
            } else if (src == serverInfos) {
                ServerInfosFrame.openServerInfos();
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
        mapItem.addActionListener(listener);
        serverInfos.addActionListener(listener);
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
        popup.add(serverInfos);
        popup.add(mapItem);

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

        ProfileSaver.setSelectedProfile(profile);
        Saver saver = ProfileSaver.getSelectedSaver();

        /* Création de l'icône */
        trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH), "Astrauworld - Profil " + saver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()) + " (" + saver.get(ProfileSaver.KEY.INFOS_NAME.get()) + ")");
        trayIcon.setPopupMenu(popup);

        /* récupère la zone de notification */
        SystemTray tray = SystemTray.getSystemTray();
        try {
            /* Affiche l'icône dans la zone de notification */
            tray.add(trayIcon);
        } catch (AWTException e) {
            println("TrayIcon could not be added.");
        }
    }

    public static void initBlackSpaceSystemTray() {
        tray.remove(trayIcon);

        if (!SystemTray.isSupported()) {
            println("SystemTray is not supported");
            return;
        }

        PopupMenu popup = new PopupMenu();

        MenuItem stab = new MenuItem("Poignarder");

        ActionListener listener = e -> {
            aboutInfosPage.blackSpace.stab();
        };

        stab.addActionListener(listener);

        popup.add(stab);

        /* Création de l'icône */
        trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH), "Astrauworld?");
        trayIcon.setPopupMenu(popup);

        /* récupère la zone de notification */
        SystemTray tray = SystemTray.getSystemTray();
        try {
            /* Affiche l'icône dans la zone de notification */
            tray.add(trayIcon);
        } catch (AWTException e) {
            println("TrayIcon could not be added.");
        }
    }

    public static void displayMessage(String caption, String message, TrayIcon.MessageType messageType) {
        trayIcon.displayMessage(caption, message, messageType);
    }

    public static void changeTrayTooltip() {
        String titleText = Launcher.parseUnicode(LauncherPanel.Components.titleLabel.getText());
        String subtitleText = Launcher.parseUnicode(LauncherPanel.Components.subTitleLabel.getText());

        if (titleText.contains("Profil")) {
            trayIcon.setToolTip("Astrauworld Launcher | " + titleText + " - " + subtitleText + " (" + ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()) + ")");
        } else if (Objects.equals(subtitleText, "") || Objects.equals(subtitleText, " ")) {
            trayIcon.setToolTip("Astrauworld Launcher | " + titleText);
        } else {
            trayIcon.setToolTip("Astrauworld Launcher | " + titleText + " - " + subtitleText);
        }
    }

    public static String getToolTip(boolean withAppName) {
        if (withAppName) {
            return trayIcon.getToolTip();
        } else {
            return trayIcon.getToolTip().replaceFirst("Astrauworld Launcher \\| ", "");
        }
    }

    static String astrauworldDir = AppDirsFactory.getInstance().getUserDataDir("Astrauworld Launcher", null, null, true);
    static String customJavaDir = astrauworldDir + File.separator + "java";

    static String currentPropertiesDir = astrauworldDir + File.separator + "launcher.properties";
    static String newPropertiesDir = astrauworldDir + File.separator + "newLauncher.properties";
    static String launcherJar = astrauworldDir + File.separator + "launcher.jar";

    static File customJavaFolder = new File(customJavaDir);
    static File currentPropertiesFile = new File(currentPropertiesDir);
    static File newPropertiesFile = new File(newPropertiesDir);
    static File launcherJarFile = new File(launcherJar);

    static Path currentPropertiesPath = Paths.get(currentPropertiesDir);
    static Path newPropertiesPath = Paths.get(newPropertiesDir);

    static Saver currentSaver = new Saver(currentPropertiesPath);
    static Saver newSaver = new Saver(newPropertiesPath);

    public static void verifyLauncherVersion(boolean download, boolean confirmNoNew) throws Exception {
        setPropertiesFile();

        Launcher.println("");
        Launcher.println("---- JAR UPDATE ----");

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            if (launcherJarFile.createNewFile()) {
                currentSaver.set("launcherVersion", "");
                Launcher.println("jar created");
            }
        } catch (IOException ignored) {}

        Launcher.println("Current: " + currentSaver.get("launcherVersion"));
        Launcher.println("New: " + newSaver.get("launcherVersion"));
        if (!Objects.equals(currentSaver.get("launcherVersion"), newSaver.get("launcherVersion"))) {
            Launcher.println("Dernière version non détectée");

            if (download) {
                updateJar();
                newPropertiesFile.delete();
                LauncherPanel.Components.updateButton.setTexture(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButton.png"));
                LauncherPanel.Components.updateButton.setTextureHover(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButtonHover.png"));
                LauncherPanel.Components.updateButton.setTextureDisabled(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButton.png"));
            } else {
                trayIcon.displayMessage("V\u00e9rification de la version", "Mise \u00e0 jour disponible" + System.getProperty("line.separator") + "T\u00e9l\u00e9chargez-la depuis le launcher", TrayIcon.MessageType.INFO);
                newPropertiesFile.delete();
                LauncherPanel.Components.updateButton.setTexture(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButton-red.png"));
                LauncherPanel.Components.updateButton.setTextureHover(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButtonHover-red.png"));
                LauncherPanel.Components.updateButton.setTextureDisabled(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButton-red.png"));
            }
        } else {
            Launcher.println("Dernière version détectée");
            if (confirmNoNew) {
                trayIcon.displayMessage("V\u00e9rification de la version", "Derni\u00e8re version d\u00e9tect\u00e9e", TrayIcon.MessageType.INFO);
            }
            newPropertiesFile.delete();
            LauncherPanel.Components.updateButton.setTexture(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButton.png"));
            LauncherPanel.Components.updateButton.setTextureHover(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButtonHover.png"));
            LauncherPanel.Components.updateButton.setTextureDisabled(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/updateButton.png"));
        }
    }

    static String getJarLink() {
        return "https://github.com/AstrauworldMC/launcher/releases/download/" + newSaver.get("launcherVersion") + "/launcher.jar";
    }

    static void setPropertiesFile() throws Exception {
        infosLabel.setText("V\u00e9rification de la derni\u00e8re version");

        try {
            newPropertiesFile.createNewFile();
        } catch (IOException ignored) {}

        try {
            downloadFromInternet("https://raw.githubusercontent.com/AstrauworldMC/launcher/main/src/main/resources/launcher.properties", newPropertiesFile);
            //     downloadFromInternet("https://raw.githubusercontent.com/AstrauworldMC/launcher/Beta2.3.0/src/main/resources/launcher.properties", newPropertiesFile);
        } catch (Exception e) {
            currentPropertiesFile.delete();
            newPropertiesFile.delete();
            launcherJarFile.delete();
            infosLabel.setVisible(false);
            throw e;
        }

        currentSaver.load();
        newSaver.load();

        if (currentSaver.get("launcherVersion") == null) {
            try {
                currentPropertiesFile.createNewFile();
                println("created");
            } catch (IOException ignored) {}
        }

        infosLabel.setVisible(false);
    }

    static void updateJar() throws Exception {
        infosLabel.setText("Mise \u00e0 jour du launcher");

        try {
            if (launcherJarFile.createNewFile()) {
                currentSaver.set("launcherVersion", "");
                println("jar created");
            }
        } catch (IOException ignored) {
        }

        if (!Objects.equals(currentSaver.get("launcherVersion"), newSaver.get("launcherVersion"))) {
            infosLabel.setText(parseUnicode("Téléchargement de la mise à jour"));
            infosLabel.setVisible(true);
            loadingBar.setVisible(true);
            percentLabel.setVisible(true);
            barLabel.setVisible(true);
            launcherJarFile.createNewFile();
            try {
                downloadFromInternet(getJarLink(), launcherJarFile, loadingBar, percentLabel, barLabel);
            } catch (Exception e) {
                currentPropertiesFile.delete();
                newPropertiesFile.delete();
                launcherJarFile.delete();
                throw e;
            }
            println("jar downloaded");
            copyFile(newPropertiesFile, currentPropertiesFile, true);
            infosLabel.setText("");
            infosLabel.setVisible(false);
            loadingBar.setVisible(false);
            percentLabel.setText("");
            percentLabel.setVisible(false);
            barLabel.setText("");
            barLabel.setVisible(false);
            verifyLauncherVersion(false, false);

        } else {
            println("Dernière version détectée");
            infosLabel.setText("Derni\u00e8re version d\u00e9tect\u00e9e");
        }
        newPropertiesFile.delete();
    }

    public static File getJava() throws Exception {
        println("");
        println("---- JAVA 17 VERIF ----");

        infosLabel.setText("V\u00e9rification de Java 17");
        try {
            println("-- Vérification du %JAVA_HOME% --");
            String javaHome = System.getenv("JAVA_HOME");
            String[] javaHomeSplit1 = javaHome.split(";");
            String pattern = Pattern.quote(File.separator);
            String[] javaHomeSplit2 = javaHomeSplit1[0].split(pattern);
            String firstReferencedJavaVersion = javaHomeSplit2[javaHomeSplit2.length - 1];
            String[] javaHomeSplit3 = firstReferencedJavaVersion.split("\\.");
            String firstReferencedJavaGlobalVersion = javaHomeSplit3[0];
            println("%JAVA_HOME%: " + javaHome);
            println("First referenced java: " + javaHomeSplit1[0]);
            println("Last part of first referenced java: " + firstReferencedJavaVersion);
            println("First referenced java global version: " + firstReferencedJavaGlobalVersion);

            if (firstReferencedJavaGlobalVersion.contains("17")) {
                return new File(javaHomeSplit1[0]);
            } else {
                throw new NoSuchElementException("Java 17 non trouvé dans %JAVA_HOME%");
            }


        } catch (Exception e) {
            println("Aucun Java 17 dans %JAVA_HOME% détecté");

            File jre17 = new File(customJavaDir + File.separator + "jre-17");
            jre17.mkdirs();

            return jre17;

        }
    }
}
