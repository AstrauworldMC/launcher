package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.*;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static fr.timeto.timutilslib.PopUpMessages.*;

/**
 * La classe qui regroupe tous les événements des {@link STexturedButton} du {@link LauncherPanel}
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @since Beta2.2.0
 */
public class OnButtonEvent {

    private static ArrayList<STexturedButton> generalButtons;
    private static ArrayList<STexturedButton> profilePageButtons;
    private static ArrayList<STexturedButton> aboutPageButtons;

    /**
     * Initialise les listes de boutons
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @since Beta2.2.0
     */
    private static void initLists() {
        generalButtons = new ArrayList<>();
        profilePageButtons = new ArrayList<>();
        aboutPageButtons = new ArrayList<>();

        generalButtons.add(quitButton);
        generalButtons.add(hideButton);
        generalButtons.add(updateButton);
        generalButtons.add(corner);


        profilePageButtons.add(profilePlayTabButton);
        profilePageButtons.add(profileAccountTabButton);
        profilePageButtons.add(profileAddonsTabButton);
        profilePageButtons.add(profileSettingsTabButton);

        profilePageButtons.add(profilePlayButton);
        profilePageButtons.add(profileServerInfosButton);
        profilePageButtons.add(profileNewsButton);
        profilePageButtons.add(profileLaunchToMenuButton);
        profilePageButtons.add(profileDownloadButton);

        profilePageButtons.add(profileAccountConnectionButton);
        profilePageButtons.add(profileAccountConnectionMicrosoftButton);
        profilePageButtons.add(profileAccountResetButton);

        profilePageButtons.add(profileAddonsShadersButton);
        profilePageButtons.add(profileAddonsResourcePacksButton);
        profilePageButtons.add(profileAddonsModsButton);
        profilePageButtons.add(profileAddonsOptifineSwitchButton);
        profilePageButtons.add(profileAddonsGoToFolderButton);

        profilePageButtons.add(profileModsFpsmodelSwitchButton);
        profilePageButtons.add(profileModsFpsmodelMoreInfosButton);
        profilePageButtons.add(profileModsBettertpsSwitchButton);
        profilePageButtons.add(profileModsBettertpsMoreInfosButton);
        profilePageButtons.add(profileModsFallingleavesSwitchButton);
        profilePageButtons.add(profileModsFallingleavesMoreInfosButton);
        profilePageButtons.add(profileModsAppleskinSwitchButton);
        profilePageButtons.add(profileModsAppleskinMoreInfosButton);
        profilePageButtons.add(profileModsSoundphysicsSwitchButton);
        profilePageButtons.add(profileModsSoundphysicsMoreInfosButton);

        profilePageButtons.add(profileShadersSeeComparisonButton);

        profilePageButtons.add(profileShadersChocapicV6PlusButton);
        profilePageButtons.add(profileShadersChocapicV7_1PlusButton);
        profilePageButtons.add(profileShadersChocapicV9PlusButton);
        profilePageButtons.add(profileShadersSeusRenewedSwitchButton);
        profilePageButtons.add(profileShadersSeusRenewedDownloadButton);

        profilePageButtons.add(profileShadersChocapicV6LiteSwitchButton);
        profilePageButtons.add(profileShadersChocapicV6LiteDownloadButton);
        profilePageButtons.add(profileShadersChocapicV6LowSwitchButton);
        profilePageButtons.add(profileShadersChocapicV6LowDownloadButton);
        profilePageButtons.add(profileShadersChocapicV6MediumSwitchButton);
        profilePageButtons.add(profileShadersChocapicV6MediumDownloadButton);
        profilePageButtons.add(profileShadersChocapicV6UltraSwitchButton);
        profilePageButtons.add(profileShadersChocapicV6UltraDownloadButton);
        profilePageButtons.add(profileShadersChocapicV6ExtremeSwitchButton);
        profilePageButtons.add(profileShadersChocapicV6ExtremeDownloadButton);

        profilePageButtons.add(profileShadersChocapicV7_1ToasterSwitchButton);
        profilePageButtons.add(profileShadersChocapicV7_1ToasterDownloadButton);
        profilePageButtons.add(profileShadersChocapicV7_1LiteSwitchButton);
        profilePageButtons.add(profileShadersChocapicV7_1LiteDownloadButton);
        profilePageButtons.add(profileShadersChocapicV7_1LowSwitchButton);
        profilePageButtons.add(profileShadersChocapicV7_1LowDownloadButton);
        profilePageButtons.add(profileShadersChocapicV7_1MediumSwitchButton);
        profilePageButtons.add(profileShadersChocapicV7_1MediumDownloadButton);
        profilePageButtons.add(profileShadersChocapicV7_1UltraSwitchButton);
        profilePageButtons.add(profileShadersChocapicV7_1UltraDownloadButton);
        profilePageButtons.add(profileShadersChocapicV7_1ExtremeSwitchButton);
        profilePageButtons.add(profileShadersChocapicV7_1ExtremeDownloadButton);

        profilePageButtons.add(profileShadersChocapicV9LowSwitchButton);
        profilePageButtons.add(profileShadersChocapicV9LowDownloadButton);
        profilePageButtons.add(profileShadersChocapicV9MediumSwitchButton);
        profilePageButtons.add(profileShadersChocapicV9MediumDownloadButton);
        profilePageButtons.add(profileShadersChocapicV9HighSwitchButton);
        profilePageButtons.add(profileShadersChocapicV9HighDownloadButton);
        profilePageButtons.add(profileShadersChocapicV9ExtremeSwitchButton);
        profilePageButtons.add(profileShadersChocapicV9ExtremeDownloadButton);
        profilePageButtons.add(profileShadersChocapicV9_1ExtremeSwitchButton);
        profilePageButtons.add(profileShadersChocapicV9_1ExtremeDownloadButton);

        profilePageButtons.add(profileSettingsHelmIconSwitchButton);
        profilePageButtons.add(profileSettingsMainProfileSwitchButton);
        profilePageButtons.add(profileSettingsSaveSettings);

        aboutPageButtons.add(aboutInfosTabButton);
        aboutPageButtons.add(aboutModsTabButton);

        aboutPageButtons.add(aboutTextLogo);
        aboutPageButtons.add(aboutAstrauwolfLogo);
        aboutPageButtons.add(aboutCapitenzoLogo);
        aboutPageButtons.add(aboutTimEtOLogo);
        aboutPageButtons.add(aboutGithubButton);
        aboutPageButtons.add(aboutMailButton);
        aboutPageButtons.add(aboutDiscordButton);
        aboutPageButtons.add(aboutTwitterButton);

    }

    /**
     * Méthode appelée à {@link LauncherPanel#onEvent(SwingerEvent)}, définie la méthode à appeler selon la page du {@link SwingerEvent}
     * @param e {@link SwingerEvent} spécifié
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @since Beta2.2.0
     */
    public static void onButtonEvent(SwingerEvent e) {
        try {
            Object src = e.getSource();
            initLists();

            if (generalButtons.contains(src)) {
                onGeneralEvent(src);
            } else if (profilePageButtons.contains(src)) {
                onProfilePageEvent(src);
            } else if (aboutPageButtons.contains(src)) {
                onAboutPageEvent(src);
            } else {
                PopUpMessages.errorMessage("Erreur du bouton", "Ev\u00e9nement du bouton non trouv\u00e9");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Thread t = new Thread(() -> {
                System.exit(1);
            });
            PopUpMessages.errorMessage("Erreur", ex.getLocalizedMessage(), t);
        }
    }

    /**
     * Tous les événements généraux
     * @param src {@link SwingerEvent} transformé en {@link Object}
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see OnButtonEvent#onButtonEvent(SwingerEvent)
     * @since Beta2.2.0
     */
    private static void onGeneralEvent(Object src) {
        // Actions des boutons de la barre d'infos de la fenêtre
        if(src == quitButton){
            System.exit(0);
        } else if (src == hideButton) {
            LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
        } else if (src == updateButton) {
            Thread t = new Thread(() -> {
                LauncherSystemTray.verifyLauncherVersion(true, true);
            });
            t.start();
        } else if (src == corner) {
            Launcher.println("Corner?");
        }
    }

    /**
     * Tous les événements de la profile page
     * @param src {@link SwingerEvent} transformé en {@link Object}
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see OnButtonEvent#onButtonEvent(SwingerEvent)
     * @since Beta2.2.0
     */
    private static void onProfilePageEvent(Object src) {
        String eventSelectedProfile = getSelectedProfile(selectedSaver);

        // Actions des boutons du haut des profilePage
        if (src == profilePlayTabButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileHome);
        } else if (src == profileAccountTabButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileAccount);
        } else if (src == profileAddonsTabButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileAddonsMods);
        } else if (src == profileSettingsTabButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileSettings);
        }

        // Actions des boutons de la profilePage - Home
        else if (src == profilePlayButton) {
            Saver saver = selectedSaver;
            enablePlayButtons(false);

            //     if (profilePlayButtonIsPlayStatus) {
            launchThread = new Thread(() -> {
                try {
                    //               togglePlayButtonStatus(false);
                    loadingBar.setVisible(true);
                    infosLabel.setVisible(true);
                    infosLabel.setText("Connexion...");
                    try {
                        Launcher.connect(saver);
                    } catch (MicrosoftAuthenticationException m) {
                        enablePlayButtons(true);
                        errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                        infosLabel.setText("Connexion \u00e9chou\u00e9e");
                        loadingBar.setVisible(false);
                        infosLabel.setVisible(false);
                        return;
                    }
                    infosLabel.setText("Connect\u00e9 avec " + selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));

                    try {
                        Launcher.update(saver);
                    } catch (Exception ex) {
                        enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    updatePostExecutions();

                    try {
                        Launcher.launch(true, saver);
                    } catch (Exception ex) {
                        enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    enablePlayButtons(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", e.getLocalizedMessage(), t);
                }

            });
            launchThread.start();

            //          togglePlayButtonStatus(false);
            //     } else {
            //     stopUpdate();

            //          togglePlayButtonStatus(true);
            //     }

        } else if (src == profileServerInfosButton) {
            ServerInfosFrame.openServerInfos();
        } else if (src == profileNewsButton) {
            setNewsPage(true);
        } else if (src == profileLaunchToMenuButton) {
            Saver saver = selectedSaver;
            enablePlayButtons(false);

            launchThread = new Thread(() -> {
                try {
                    //          togglePlayButtonStatus(false);

                    try {
                        Launcher.connect(saver);
                    } catch (MicrosoftAuthenticationException m) {
                        enablePlayButtons(true);
                        errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                        infosLabel.setText("Connexion \u00e9chou\u00e9e");
                        loadingBar.setVisible(false);
                        infosLabel.setVisible(false);
                        return;
                    }

                    try {
                        Launcher.update(saver);
                    } catch (Exception ex) {
                        enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    updatePostExecutions();

                    try {
                        Launcher.launch(false, saver);
                    } catch (Exception ex) {
                        enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    enablePlayButtons(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", e.getLocalizedMessage(), t);
                }

            });
            launchThread.start();

        } else if (src == profileDownloadButton) {
            Saver saver = selectedSaver;
            enablePlayButtons(false);

            updateThread = new Thread(() -> {
                try {
                    //          togglePlayButtonStatus(false);
                    try {
                        Launcher.update(saver);
                    } catch (InterruptedException e1) {
                        enablePlayButtons(true);
                        updatePostExecutions();
                    } catch (Exception ex) {
                        enablePlayButtons(true);
                        throw new RuntimeException(ex);
                    }
                    enablePlayButtons(true);
                    updatePostExecutions();
                    doneMessage("T\u00e9l\u00e9chargement", "T\u00e9l\u00e9chargement termin\u00e9");
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", e.getLocalizedMessage(), t);
                }

            });

            updateThread.start();

        }

        // Actions des boutons de la profilePage - Account
        else if (src == profileAccountConnectionButton) {
            Saver saver = selectedSaver;

            if (profileAccountTextField.getText().replaceAll(" ", "").length() == 0 || profileAccountPasswordField.getPassword().length == 0) {
                errorMessage("Erreur de connexion", "Erreur, veuillez     entrer un email et un   mot de passe valides");
                return;
            }

            Thread connect = new Thread(() -> {
                try {
                    try {
                        Launcher.println("Connexion...");
                        Launcher.microsoftAuth(profileAccountTextField.getText(), new String(profileAccountPasswordField.getPassword()), saver);
                    } catch (MicrosoftAuthenticationException m) {
                        errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                        return;
                    }
                    doneMessage("Connexion r\u00e9ussie", "Connexion r\u00e9ussie");
                    initProfileButtons();
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", e.getLocalizedMessage(), t);
                }
            });

            connect.start();


        } else if (src == profileAccountConnectionMicrosoftButton) {
            Saver saver = selectedSaver;

            Thread connect = new Thread(() -> {
                try {
                    try {
                        Launcher.println("Connexion...");
                        Launcher.microsoftAuthWebview(saver);
                    } catch (MicrosoftAuthenticationException m) {
                        errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                        return;
                    }
                    doneMessage("Connexion r\u00e9ussie", "Connexion r\u00e9ussie");
                    initProfileButtons();
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", e.getLocalizedMessage(), t);
                }
            });
            connect.start();
        } else if (src == profileAccountResetButton) {
            Thread ifYes = new Thread(() -> {
                selectedSaver.set(ProfileSaver.KEY.FILECREATED, "");
                initializeDataFiles(selectedSaver);
                profileAccountTextField.setText("");
                doneMessage("Compte supprim\u00e9", "Donn\u00e9es du compte r\u00e9initialis\u00e9es");
                initProfileButtons();
            });

            Thread ifNo = new Thread();

            yesNoMessage("R\u00e9initialisation du compte", "Voulez vous vraiment r\u00e9initialiser le compte ?", ifYes, ifNo);

        }

        // Actions des boutons de la profilePage - Mods
        else if (src == profileAddonsShadersButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileAddonsShaders);
        } else if (src == profileAddonsResourcePacksButton) {
            initCustomFilesFolder(selectedSaver);
            try {
                Desktop.getDesktop().open(resourcepacksProfileFolder); // TODO Temporaire jusqu'à pouvoir le faire depuis le assets
            } catch (IOException ignored) {}
        } else if (src == profileAddonsModsButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileAddonsMods);
        } else if (src == profileAddonsGoToFolderButton) {
            initCustomFilesFolder(selectedSaver);
            if (subTitleLabel.getText().toLowerCase().contains("shader")) {
                try {
                    Desktop.getDesktop().open(shaderpacksProfileFolder);
                } catch (Exception e) {
                    shaderpacksProfileFolder.mkdir();
                    Launcher.println("Folder created: " + shaderpacksProfileFolder);
                    try {
                        Desktop.getDesktop().open(shaderpacksProfileFolder);
                    } catch (IOException ignored) {}
                }
            } else if (subTitleLabel.getText().toLowerCase().contains("resource")) {
                try {
                    Desktop.getDesktop().open(resourcepacksProfileFolder);
                } catch (IOException ignored) {}
            }
        } else if (src == profileAddonsOptifineSwitchButton) {
            profileAddonsOptifineSwitchButton.toggleButton();
        } else if (src == profileModsFpsmodelSwitchButton) {
            profileModsFpsmodelSwitchButton.toggleButton();
        } else if (src == profileModsFpsmodelMoreInfosButton) {
            openMoreInfosUrl(KEY.MOD_FPSMODEL);
        } else if (src == profileModsBettertpsSwitchButton) {
            profileModsBettertpsSwitchButton.toggleButton();
        } else if (src == profileModsBettertpsMoreInfosButton) {
            openMoreInfosUrl(KEY.MOD_BETTERTPS);
        } else if (src == profileModsFallingleavesSwitchButton) {
            profileModsFallingleavesSwitchButton.toggleButton();
        } else if (src == profileModsFallingleavesMoreInfosButton) {
            openMoreInfosUrl(KEY.MOD_FALLINGLEAVES);
        } else if (src == profileModsAppleskinSwitchButton) {
            profileModsAppleskinSwitchButton.toggleButton();
        } else if (src == profileModsAppleskinMoreInfosButton) {
            openMoreInfosUrl(KEY.MOD_APPLESKIN);
        } else if (src == profileModsSoundphysicsSwitchButton) {
            profileModsSoundphysicsSwitchButton.toggleButton();
        } else if (src == profileModsSoundphysicsMoreInfosButton) {
            openMoreInfosUrl(KEY.MOD_SOUNDPHYSICS);
        }
        
        // Actions des boutons de la profilePage - Shaders
        else if (src == profileShadersSeeComparisonButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/AstrauworldMC/launcher/wiki/Comparaison-des-shaders").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else if (src == profileShadersSeusRenewedSwitchButton) {
            profileShadersSeusRenewedSwitchButton.toggle();
        } else if (src == profileShadersSeusRenewedDownloadButton) {
            profileShadersSeusRenewedSwitchButton.installShader();
        } else if (src == profileShadersChocapicV6PlusButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileAddonsShaders + " ChocapicV6");
        } else if (src == profileShadersChocapicV7_1PlusButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileAddonsShaders + " ChocapicV7");
        } else if (src == profileShadersChocapicV9PlusButton) {
            setProfilePage(true, eventSelectedProfile, PageChange.TAB_KEY.profileAddonsShaders + " ChocapicV9");
        }

        // Actions des boutons de la profilePage - Shaders (ChocapicV6)
        else if (src == profileShadersChocapicV6LiteSwitchButton) {
            profileShadersChocapicV6LiteSwitchButton.toggle();
        } else if (src == profileShadersChocapicV6LiteDownloadButton) {
            profileShadersChocapicV6LiteSwitchButton.installShader();
        } else if (src == profileShadersChocapicV6LowSwitchButton) {
            profileShadersChocapicV6LowSwitchButton.toggle();
        } else if (src == profileShadersChocapicV6LowDownloadButton) {
            profileShadersChocapicV6LowSwitchButton.installShader();
        } else if (src == profileShadersChocapicV6MediumSwitchButton) {
            profileShadersChocapicV6MediumSwitchButton.toggle();
        } else if (src == profileShadersChocapicV6MediumDownloadButton) {
            profileShadersChocapicV6MediumSwitchButton.installShader();
        } else if (src == profileShadersChocapicV6UltraSwitchButton) {
            profileShadersChocapicV6UltraSwitchButton.toggle();
        } else if (src == profileShadersChocapicV6UltraDownloadButton) {
            profileShadersChocapicV6UltraSwitchButton.installShader();
        } else if (src == profileShadersChocapicV6ExtremeSwitchButton) {
            profileShadersChocapicV6ExtremeSwitchButton.toggle();
        } else if (src == profileShadersChocapicV6ExtremeDownloadButton) {
            profileShadersChocapicV6ExtremeSwitchButton.installShader();
        }

        // Actions des boutons de la profilePage - Shaders (ChocapicV7)
        else if (src == profileShadersChocapicV7_1ToasterSwitchButton) {
            profileShadersChocapicV7_1ToasterSwitchButton.toggle();
        } else if (src == profileShadersChocapicV7_1ToasterDownloadButton) {
            profileShadersChocapicV7_1ToasterSwitchButton.installShader();
        } else if (src == profileShadersChocapicV7_1LiteSwitchButton) {
            profileShadersChocapicV7_1LiteSwitchButton.toggle();
        } else if (src == profileShadersChocapicV7_1LiteDownloadButton) {
            profileShadersChocapicV7_1LiteSwitchButton.installShader();
        } else if (src == profileShadersChocapicV7_1LowSwitchButton) {
            profileShadersChocapicV7_1LowSwitchButton.toggle();
        } else if (src == profileShadersChocapicV7_1LowDownloadButton) {
            profileShadersChocapicV7_1LowSwitchButton.installShader();
        } else if (src == profileShadersChocapicV7_1MediumSwitchButton) {
            profileShadersChocapicV7_1MediumSwitchButton.toggle();
        } else if (src == profileShadersChocapicV7_1MediumDownloadButton) {
            profileShadersChocapicV7_1MediumSwitchButton.installShader();
        } else if (src == profileShadersChocapicV7_1UltraSwitchButton) {
            profileShadersChocapicV7_1UltraSwitchButton.toggle();
        } else if (src == profileShadersChocapicV7_1UltraDownloadButton) {
            profileShadersChocapicV7_1UltraSwitchButton.installShader();
        } else if (src == profileShadersChocapicV7_1ExtremeSwitchButton) {
            profileShadersChocapicV7_1ExtremeSwitchButton.toggle();
        } else if (src == profileShadersChocapicV7_1ExtremeDownloadButton) {
            profileShadersChocapicV7_1ExtremeSwitchButton.installShader();
        }

        // Actions des boutons de la profilePage - Shaders (ChocapicV9)
        else if (src == profileShadersChocapicV9LowSwitchButton) {
            profileShadersChocapicV9LowSwitchButton.toggle();
        } else if (src == profileShadersChocapicV9LowDownloadButton) {
            profileShadersChocapicV9LowSwitchButton.installShader();
        } else if (src == profileShadersChocapicV9MediumSwitchButton) {
            profileShadersChocapicV9MediumSwitchButton.toggle();
        } else if (src == profileShadersChocapicV9MediumDownloadButton) {
            profileShadersChocapicV9MediumSwitchButton.installShader();
        } else if (src == profileShadersChocapicV9HighSwitchButton) {
            profileShadersChocapicV9HighSwitchButton.toggle();
        } else if (src == profileShadersChocapicV9HighDownloadButton) {
            profileShadersChocapicV9HighSwitchButton.installShader();
        } else if (src == profileShadersChocapicV9ExtremeSwitchButton) {
            profileShadersChocapicV9ExtremeSwitchButton.toggle();
        } else if (src == profileShadersChocapicV9ExtremeDownloadButton) {
            profileShadersChocapicV9ExtremeSwitchButton.installShader();
        } else if (src == profileShadersChocapicV9_1ExtremeSwitchButton) {
            profileShadersChocapicV9_1ExtremeSwitchButton.toggle();
        } else if (src == profileShadersChocapicV9_1ExtremeDownloadButton) {
            profileShadersChocapicV9_1ExtremeSwitchButton.installShader();
        }

        // Actions des boutons de la profilePage - Reglages
        else if (src == profileSettingsHelmIconSwitchButton) {
            profileSettingsHelmIconSwitchButton.toggleButton();
            initProfileButtons();
        } else if (src == profileSettingsMainProfileSwitchButton) {
            profileSettingsMainProfileSwitchButton.toggleButton();
        } else if (src == profileSettingsSaveSettings) {
            selectedSaver.set(ProfileSaver.KEY.SETTINGS_RAM, profileSettingsAllowedRamSpinner.getValue().toString());
            selectedSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME, profileSettingsProfileNameTextField.getText());
            initProfileButtons();
            doneMessage("Enregistr\u00e9 !", "Param\u00e8tres           enregistr\u00e9s");
        }

    }

    /**
     * Tous les événements de l'about page
     * @param src {@link SwingerEvent} transformé en {@link Object}
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see OnButtonEvent#onButtonEvent(SwingerEvent)
     * @since Beta2.2.0
     */
    private static void onAboutPageEvent(Object src) {
        // Actions des boutons de l'aboutPage - Up
        if (src == aboutInfosTabButton) {
            setAboutPage(true, PageChange.TAB_KEY.aboutInfos);
        } else if (src == aboutModsTabButton) {
            setAboutPage(true, PageChange.TAB_KEY.aboutMods);
        }

        // Actions des boutons de l'aboutPage - Infos
        else if (src == aboutTextLogo) {
            try {
                Desktop.getDesktop().browse(new URL("http://astrauworld.ovh:8100").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == aboutAstrauwolfLogo) {
            try {
                Desktop.getDesktop().browse(new URL("https://youtu.be/rRPQs_kM_nw").toURI());
            } catch (IOException | URISyntaxException ignored) {}
            EasterEggs.setEatereggAsFound(EasterEggs.polishCow);
        } else if (src == aboutCapitenzoLogo) {
            try {
                Desktop.getDesktop().browse(new URL("https://youtu.be/vyPjz2QbFT4").toURI());
            } catch (IOException | URISyntaxException ignored) {}
            EasterEggs.setEatereggAsFound(EasterEggs.frogWalking);
        } else if (src == aboutTimEtOLogo) {
            try {
                Desktop.getDesktop().browse(new URL("https://youtu.be/dQw4w9WgXcQ").toURI());
            } catch (IOException | URISyntaxException ignored) {}
            EasterEggs.setEatereggAsFound(EasterEggs.rickroll);
        } else if (src == aboutGithubButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/AstrauworldMC").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == aboutMailButton) {
            try {
                Desktop.getDesktop().mail(new URL("mailto:astrauworld.minecraft@gmail.com").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == aboutDiscordButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://discord.gg/GpqB5eES5r").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        } else if (src == aboutTwitterButton) {
            try {
                Desktop.getDesktop().browse(new URL("https://twitter.com/AstrauworldMC").toURI());
            } catch (IOException | URISyntaxException ignored) {}
        }

    }
}
