package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.*;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
import java.util.ArrayList;

import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;

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

        aboutPageButtons.add(aboutInfosTabButton);
        aboutPageButtons.add(aboutModsTabButton);

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

    }
}
