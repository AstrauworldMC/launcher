package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.secret.DiscordManager;
import fr.timeto.astrauworld.launcher.pagesutilities.EasterEggs;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.timutilslib.PopUpMessages;
import fr.timeto.timutilslib.TimFilesUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.main.Launcher.parseUnicode;
import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.initLauncherSystemTray;
import static fr.timeto.astrauworld.launcher.main.LauncherSystemTray.verifyLauncherVersion;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static fr.timeto.astrauworld.launcher.main.LauncherFrame.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            initCurrentLauncherProperties();

            String OS = System.getProperty("os.name");

            if (OS.toLowerCase().contains("win")) {
                Launcher.println("Windows OK");
            } else if (OS.toLowerCase().contains("mac")) {
                Launcher.println("MacOS OK");
            } else if (OS.toLowerCase().contains("nix") || OS.toLowerCase().contains("nux") || OS.toLowerCase().contains("aix")) {
                Launcher.println("Unix OK");
            } else {
                Thread ok = new Thread(() -> System.exit(1));
                PopUpMessages.errorMessage("Erreur", "Désolé, votre système d'exploitation (" + OS + ") n'est pas compatible", ok);
                Launcher.println("OS non supporté");
            }

            Launcher.println("Chargement...");

            Launcher.println("[Lancement] Création des dossiers");
            Launcher.AW_DIR.mkdirs();
            Launcher.AW_DATA_FOLDER.mkdir();
            Launcher.AW_DISCORD_DATA_FOLDER.mkdir();
            Launcher.AW_GAMEFILES_FOLDER.mkdir();
            Launcher.AW_CRASH_FOLDER.mkdir();

            Launcher.AW_SETTINGS_DATA.createNewFile();

            Launcher.AW_FIRSTPROFILE_CUSTOMFILES_FOLDER.mkdir();
            Launcher.AW_SECONDPROFILE_CUSTOMFILES_FOLDER.mkdir();
            Launcher.AW_THIRDPROFILE_CUSTOMFILES_FOLDER.mkdir();

            Launcher.println("[Lancement] Création des fichiers d'image de profil");
            Launcher.AW_FIRSTPROFILE_ICON.createNewFile();

            Launcher.AW_SECONDPROFILE_ICON.createNewFile();

            Launcher.AW_THIRDPROFILE_ICON.createNewFile();

            Launcher.println("[Lancement] Initialisation des fichiers de données");
            initializeDataFiles();

            Launcher.CUSTOM_COLORS.MAIN_COLOR.set(Launcher.CUSTOM_COLORS.MAIN_COLOR.getFileColor());
            Launcher.CUSTOM_COLORS.TEXT_COLOR.set(Launcher.CUSTOM_COLORS.TEXT_COLOR.getFileColor());
            Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.set(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.getFileColor());
            Launcher.CUSTOM_COLORS.DARKER_BACKGROUND_COLOR.set(Launcher.CUSTOM_COLORS.DARKER_BACKGROUND_COLOR.getFileColor());
            Launcher.CUSTOM_COLORS.MID_BACKGROUND_COLOR.set(Launcher.CUSTOM_COLORS.MID_BACKGROUND_COLOR.getFileColor());
            Launcher.CUSTOM_COLORS.BASE_BACKGROUND_COLOR.set(Launcher.CUSTOM_COLORS.BASE_BACKGROUND_COLOR.getFileColor());
            Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.set(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.getFileColor());

            if (firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME.get()).toLowerCase().replaceAll(" ", "").equals("no")) {
                firstProfileSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME.get(), "Vide");
                firstProfileSaver.set(ProfileSaver.KEY.INFOS_NAME.get(), "");
            }
            if (secondProfileSaver.get(KEY.INFOS_NAME.get()).toLowerCase().replaceAll(" ", "").equals("no")) {
                secondProfileSaver.set(KEY.SETTINGS_PROFILENAME.get(), "Vide");
                secondProfileSaver.set(KEY.INFOS_NAME.get(), "");
            }
            if (thirdProfileSaver.get(KEY.INFOS_NAME.get()).toLowerCase().replaceAll(" ", "").equals("no")) {
                thirdProfileSaver.set(KEY.SETTINGS_PROFILENAME.get(), "Vide");
                thirdProfileSaver.set(KEY.INFOS_NAME.get(), "");
            }

            CrashReporter crashReporter = new CrashReporter("Astrauworld Launcher", Launcher.awCrashFolder);

            Swinger.setResourcePath("/assets/launcher/");
            Swinger.setSystemLookNFeel();

            Launcher.println("[Lancement] Vérification des arguments");
            try {
                if (Objects.equals(args[0], Launcher.afterMcExitArg)) {
                    Saver saver = null;
                    if (Objects.equals(args[1], "1")) {
                        saver = firstProfileSaver;
                    } else if (Objects.equals(args[1], "2")) {
                        saver = secondProfileSaver;
                    } else if (Objects.equals(args[1], "3")) {
                        saver = thirdProfileSaver;
                    }
                    profileAfterMcExit = args[1];

                    Launcher.println("[Lancement] Relancement après la sortie du jeu avec le profil " + args[1] + " détecté, enregistrement des fichiers");

                    ProfileSaver.saveCustomFiles(saver);
                } else if (Objects.equals(args[0], Launcher.devEnvArg)) {
                    devEnv = true;
                    Launcher.println("[Lancement] Environnement de développement détecté");
                }
            } catch (Exception ignored) {
            }

            if (profileAfterMcExit == null) {

                Thread t = new Thread(() -> {
                    try {

                        Launcher.println("[Lancement] Lancement du system tray icon");
                        initLauncherSystemTray();

                        Launcher.println("[Lancement] Lancement du DiscordRPC");
                        DiscordManager.start();
                        DiscordManager.setLauncherPresence();

                        Launcher.println("[Lancement] Initialisation des easters eggs");
                        EasterEggs.initEastereggs();
                        new File(Launcher.AW_DATA_FOLDER.getAbsolutePath() + "eastereggs.properties").delete();

                        PageChange.lastSettingsSaver = null;

                        Launcher.println("[Lancement] Initialisation des images de profil");
                        initProfileIcon();

                        Launcher.println("[Lancement] Vérifications de problèmes de fichiers d'anciennes versions");
                        shaderpacksFolder.mkdir();
                        optionsShadersTextfile.createNewFile();

                        new File(Launcher.AW_GAMEFILES_FOLDER + "1.18.2.json").delete();
                        File mcVersionJsonFile = new File(Launcher.AW_GAMEFILES_FOLDER + Launcher.separatorChar + "1.18.2.json");
                        if (!mcVersionJsonFile.exists()) {
                            optionsTextfile.delete();
                            optionsOFTextfile.delete();
                            optionsShadersTextfile.delete();
                            int i = 1;
                            while (i != 4) {
                                initCustomFilesFolder(ProfileSaver.getSaver(i + ""));
                                optionsProfileTextfile.delete();
                                optionsOFProfileTextfile.delete();
                                optionsShadersProfileTextfile.delete();
                                i++;
                            }
                            mcVersionJsonFile.createNewFile();
                        }

                        File serverDat = new File(Launcher.AW_GAMEFILES_FOLDER, "servers.dat");
                        if (!serverDat.exists()) {
                            Launcher.println("[Lancement] Téléchargement du servers.dat");
                            TimFilesUtils.downloadFromInternet("https://github.com/AstrauworldMC/resources/raw/main/servers.dat", serverDat);
                            Launcher.println("servers.dat file downloaded");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Thread tt = new Thread(() -> System.exit(1));
                        try {
                            String[] causeSplit1 = e.getCause().toString().split(":");
                            String[] causeSplit2 = causeSplit1[0].split("\\.");
                            PopUpMessages.errorMessage(causeSplit2[causeSplit2.length - 1], parseUnicode(e.getLocalizedMessage()), tt);
                        } catch (Exception ex) {
                            PopUpMessages.errorMessage("Erreur (non reconnue)", parseUnicode(e.getLocalizedMessage()), tt);
                        }
                        try {
                            Taskbar.getTaskbar().requestUserAttention(true, true);
                        } catch (UnsupportedOperationException ignored) {
                        }
                    }
                });
                t.start();

                if (!devEnv) {
                    Launcher.println("[Lancement] Vérification de la version");
                    verifyLauncherVersion(false, false);
                }

                instance = new LauncherFrame();
                TimFilesUtils.setSelectedWindow(instance);
            } else {
                Launcher.println("[Lancement] Relancement du launcher");

                getInstance().setName("Astrauworld Launcher");
                getInstance().setVisible(true);
                initLauncherSystemTray();
                DiscordManager.setLauncherPresence();
                if (Integer.parseInt(profileAfterMcExit) == 0) {
                    PageChange.setPage(true, PageName.PROFILE_HOME, getActualMainProfile());
                } else {
                    PageChange.setPage(true, PageName.PROFILE_HOME, profileAfterMcExit);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Thread t = new Thread(() -> System.exit(1));
            try {
                String[] causeSplit1 = e.getCause().toString().split(":");
                String[] causeSplit2 = causeSplit1[0].split("\\.");
                PopUpMessages.errorMessage(causeSplit2[causeSplit2.length - 1], parseUnicode(e.getLocalizedMessage()), t);
            } catch (Exception ex) {
                PopUpMessages.errorMessage("Erreur (non reconnue)", parseUnicode(e.getLocalizedMessage()), t);
            }
            try {
                Taskbar.getTaskbar().requestUserAttention(true, true);
            } catch (UnsupportedOperationException ignored) {
            }
        }

    }
}
