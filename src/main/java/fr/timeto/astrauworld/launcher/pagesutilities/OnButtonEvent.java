package fr.timeto.astrauworld.launcher.pagesutilities;

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
            LauncherFrame.exitListener.windowClosing(null);
        } else if (src == hideButton) {
            LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
        } else if (src == updateButton) {
            if (inDownload) {
                inDownloadError();
                return;
            }
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
        String eventSelectedProfile = getSelectedProfile(getSelectedSaver());

        // Actions des boutons du haut des profilePage
        if (src == profilePlayTabButton) {
            setPage(true, PageName.PROFILE_HOME, eventSelectedProfile);
        } else if (src == profileAccountTabButton) {
            setPage(true, PageName.PROFILE_ACCOUNT, eventSelectedProfile);
        } else if (src == profileAddonsTabButton) {
            setPage(true, PageName.PROFILE_ADDONS_MODS, eventSelectedProfile);
        } else if (src == profileSettingsTabButton) {
            setPage(true, PageName.PROFILE_SETTINGS, eventSelectedProfile);
        }

        // Actions des boutons de la profilePage - Reglages
        else if (src == profileSettingsHelmIconSwitchButton) {
            profileSettingsHelmIconSwitchButton.toggleButton();
            initProfileButtons();
        } else if (src == profileSettingsMainProfileSwitchButton) {
            profileSettingsMainProfileSwitchButton.toggleButton();
        } else if (src == profileSettingsSaveSettings) {
            getSelectedSaver().set(ProfileSaver.KEY.SETTINGS_RAM.get(), profileSettingsAllowedRamSpinner.getValue().toString());
            getSelectedSaver().set(ProfileSaver.KEY.SETTINGS_PROFILENAME.get(), profileSettingsProfileNameTextField.getText());
            initProfileButtons();
            doneMessage("Enregistr\u00e9 !", "Param\u00e8tres           enregistr\u00e9s");
            LauncherSystemTray.changeTrayTooltip();
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
            setPage(true, PageName.ABOUT_INFOS);
        } else if (src == aboutModsTabButton) {
            setPage(true, PageName.ABOUT_MODS);
        }

        // Actions des boutons de l'aboutPage - Infos
        else if (src == aboutTextLogo) {
            try {
                Desktop.getDesktop().browse(new URL(LauncherFrame.launcherProperties.getProperty("blueMapLink")).toURI());
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
