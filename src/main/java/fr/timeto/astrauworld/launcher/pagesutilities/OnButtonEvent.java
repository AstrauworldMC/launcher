package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherFrame;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.ChangePage.*;
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

        generalButtons.add(newsButton);
        generalButtons.add(firstProfileButton);
        generalButtons.add(secondProfileButton);
        generalButtons.add(thirdProfileButton);
        generalButtons.add(changesButton);
        generalButtons.add(aboutButton);


        profilePageButtons.add(profilePlayTabButton);
        profilePageButtons.add(profileAccountTabButton);
        profilePageButtons.add(profileModsTabButton);
        profilePageButtons.add(profileSettingsTabButton);

        profilePageButtons.add(profilePlayButton);
        profilePageButtons.add(profileNewsButton);
        profilePageButtons.add(profileLaunchToMenuButton);
        profilePageButtons.add(profileDownloadButton);

        profilePageButtons.add(profileAccountConnectionButton);
        profilePageButtons.add(profileAccountConnectionMicrosoftButton);
        profilePageButtons.add(profileAccountResetButton);

        profilePageButtons.add(profileModsShadersButton);
        profilePageButtons.add(profileModsResourcePacksButton);
        profilePageButtons.add(profileModsOptifineSwitchButton);
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

        profilePageButtons.add(profileSettingsHelmIconSwitchButton);
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
        Object src = e.getSource();
        initLists();

        if(generalButtons.contains(src)) {
            onGeneralEvent(src);
        } else if (profilePageButtons.contains(src)) {
            onProfilePageEvent(src);
        } else if (aboutPageButtons.contains(src)) {
            onAboutPageEvent(src);
        } else {
            PopUpMessages.errorMessage("Erreur du bouton", "Ev\u00e9nement du bouton non trouv\u00e9");
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
        }

        // Actions des boutons du menu de gauche
        else if (src == newsButton) {
            setNewsPage(true);
        } else if (src == firstProfileButton) {
            setProfilePage(true, "1", "home");
        } else if (src == secondProfileButton) {
            setProfilePage(true, "2", "home");
        } else if (src == thirdProfileButton) {
            setProfilePage(true, "3", "home");
        } else if (src == changesButton) {
            setChangesPage(true);
        } else if (src == aboutButton) {
            setAboutPage("infos", true);
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
        // Actions des boutons du haut des profilePage
        if (src == profilePlayTabButton) {
            setProfilePage(true, "null", "home");
        } else if (src == profileAccountTabButton) {
            setProfilePage(true, "null", "account");
        } else if (src == profileModsTabButton) {
            setProfilePage(true, "null", "mods");
        } else if (src == profileSettingsTabButton) {
            setProfilePage(true, "null", "settings");
        }

        // Actions des boutons de la profilePage - Home
        else if (src == profilePlayButton) {
            //     if (profilePlayButtonIsPlayStatus) {
            launchThread = new Thread(() -> {
                enablePlayButtons(false);
                //               togglePlayButtonStatus(false);
                loadingBar.setVisible(true);
                infosLabel.setVisible(true);
                infosLabel.setText("Connexion...");
                try {
                    Launcher.connect();
                } catch (MicrosoftAuthenticationException m) {
                    enablePlayButtons(true);
                    errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                    infosLabel.setText("Connexion \u00e9chou\u00e9e");
                    loadingBar.setVisible(false);
                    infosLabel.setVisible(false);
                    return;
                }
                initSelectedSaver();
                infosLabel.setText("Connect\u00e9 avec " + selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));

                try {
                    Launcher.update();
                } catch (Exception ex) {
                    enablePlayButtons(true);
                    throw new RuntimeException(ex);
                }
                updatePostExecutions();

                try {
                    Launcher.launch(true);
                } catch (Exception ex) {
                    enablePlayButtons(true);
                    throw new RuntimeException(ex);
                }
                enablePlayButtons(true);

            });
            launchThread.start();

            //          togglePlayButtonStatus(false);
            //     } else {
            //     stopUpdate();

            //          togglePlayButtonStatus(true);
            //     }

        } else if (src == profileNewsButton) {
            setNewsPage(true);
        } else if (src == profileLaunchToMenuButton) {

            launchThread = new Thread(() -> {
                enablePlayButtons(false);
                //          togglePlayButtonStatus(false);

                try {
                    Launcher.connect();
                } catch (MicrosoftAuthenticationException m) {
                    enablePlayButtons(true);
                    errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                    infosLabel.setText("Connexion \u00e9chou\u00e9e");
                    loadingBar.setVisible(false);
                    infosLabel.setVisible(false);
                    return;
                }

                try {
                    Launcher.update();
                } catch (Exception ex) {
                    enablePlayButtons(true);
                    throw new RuntimeException(ex);
                }
                updatePostExecutions();

                try {
                    Launcher.launch(false);
                } catch (Exception ex) {
                    enablePlayButtons(true);
                    throw new RuntimeException(ex);
                }
                enablePlayButtons(true);

            });
            launchThread.start();

        } else if (src == profileDownloadButton) {

            updateThread = new Thread(() -> {
                enablePlayButtons(false);
                //          togglePlayButtonStatus(false);
                try {
                    Launcher.update();
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

            });

            updateThread.start();

        }

        // Actions des boutons de la profilePage - Account
        else if (src == profileAccountConnectionButton) {
            if (profileAccountTextField.getText().replaceAll(" ", "").length() == 0 || profileAccountPasswordField.getPassword().length == 0) {
                errorMessage("Erreur de connexion", "Erreur, veuillez entrer un    email et un mot de passe      valides");
                return;
            }

            Thread connect = new Thread(() -> {
                try {
                    Launcher.println("Connexion...");
                    Launcher.microsoftAuth(profileAccountTextField.getText(), new String(profileAccountPasswordField.getPassword()));
                } catch (MicrosoftAuthenticationException m) {
                    errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                    return;
                }
                doneMessage("Connexion r\u00e9ussie", "Connexion r\u00e9ussie");
                initProfileButtons();
            });

            connect.start();


        } else if (src == profileAccountConnectionMicrosoftButton) {

            Thread connect = new Thread(() -> {
                try {
                    Launcher.println("Connexion...");
                    Launcher.microsoftAuthWebview();
                } catch (MicrosoftAuthenticationException m) {
                    errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                    return;
                }
                doneMessage("Connexion r\u00e9ussie", "Connexion r\u00e9ussie");
                initProfileButtons();
            });
            connect.start();
        } else if (src == profileAccountResetButton) {
            Thread ifYes = new Thread(() -> {
                initSelectedSaver();
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
        else if (src == profileModsShadersButton) {
            shaderpacksFolder.mkdir();
            try {
                Desktop.getDesktop().open((shaderpacksFolder)); // TODO Temporaire jusqu'à pouvoir le faire depuis le launcher
            } catch (IOException ignored) {}
        } else if (src == profileModsResourcePacksButton) {
            try {
                Desktop.getDesktop().open((resourcepacksFolder)); // TODO Temporaire jusqu'à pouvoir le faire depuis le launcher
            } catch (IOException ignored) {}
        } else if (src == profileModsOptifineSwitchButton) {
            profileModsOptifineSwitchButton.toggleButton();
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

        // Actions des boutons de la profilePage - Reglages
        else if (src == profileSettingsHelmIconSwitchButton) {
            profileSettingsHelmIconSwitchButton.toggleButton();
            initProfileButtons();
        } else if (src == profileSettingsSaveSettings) {
            initSelectedSaver();
            selectedSaver.set(ProfileSaver.KEY.SETTINGS_RAM, profileSettingsAllowedRamSpinner.getValue().toString());
            selectedSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME, profileSettingsProfileNameTextField.getText());
            initProfileButtons();
            doneMessage("Enregistr\u00e9 !", "Param\u00e8tres enregistr\u00e9s");
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
            setAboutPage("infos", true);
        } else if (src == aboutModsTabButton) {
            setAboutPage("mods", true);
        }

        // Actions des boutons de l'aboutPage - Infos
        else if (src == aboutTextLogo) {
            try {
                Desktop.getDesktop().browse(new URL("http://astrauworld.ovh").toURI());
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
                Desktop.getDesktop().browse(new URL("mailto:astrauworld.minecraft@gmail.com").toURI());
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
