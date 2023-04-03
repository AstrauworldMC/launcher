package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.*;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
import java.util.ArrayList;

import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.*;

/**
 * La classe qui regroupe tous les événements des {@link STexturedButton} du {@link LauncherPanel}
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @since Beta2.2.0
 */
public class OnButtonEvent {

    private static ArrayList<STexturedButton> generalButtons;

    /**
     * Initialise les listes de boutons
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @since Beta2.2.0
     */
    private static void initLists() {
        generalButtons = new ArrayList<>();

        generalButtons.add(quitButton);
        generalButtons.add(hideButton);
        generalButtons.add(updateButton);
        generalButtons.add(corner);

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
                try {
                    LauncherSystemTray.verifyLauncherVersion(true, true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            t.start();
        } else if (src == corner) {
            Launcher.println("Corner?");
        }
    }
}
