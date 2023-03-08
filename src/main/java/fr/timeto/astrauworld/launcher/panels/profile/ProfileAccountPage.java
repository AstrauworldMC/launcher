package fr.timeto.astrauworld.launcher.panels.profile;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.timutilslib.CustomFonts;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;

import java.awt.*;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.Launcher.parseUnicode;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.initProfileButtons;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.verifyNoAccountBefore;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static fr.timeto.timutilslib.PopUpMessages.*;

public class ProfileAccountPage extends PageCreator implements SwingerEventListener {

    /**
     * Bouton de connexion classique dans la page des profils - compte
     */
    public final STexturedButton connectionButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionButton-hover.png"));
    /**
     * Bouton de connexion avec une webview de Microsoft dans la page des profils - compte
     */
    public final STexturedButton connectionMicrosoftButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionWithMicrosoftButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionWithMicrosoftButton-hover.png"));
    /**
     * Bouton pour réinitialiser les informations du compte connecté
     */
    public final STexturedButton resetButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/compte/resetButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/compte/resetButton-hover.png"));
    /**
     * TextField pour entrer l'adresse email pour la connexion
     */
    public final JTextField textField = new JTextField("");
    public final JLabel textFieldLabel = new JLabel("Email");

    /**
     * PasswordField pour entrer le mot de passe pour la connexion
     */
    public final JPasswordField passwordField = new JPasswordField();
    public final JLabel passwordFieldLabel = new JLabel("Mot de passe");

    public final JTextArea infosLabel = new JTextArea(parseUnicode("Vos informations de connexion sont stockées dans votre ordinateur seulement, elles ne sont en aucun cas \n" +
            "partagées avec la team Astrauworld ou n'importe qui d'autre. Ne partagez vos identifiants à personne."));

    public ProfileAccountPage() {
        super(PageName.PROFILE_ACCOUNT, "Profil " + ProfileSaver.getSelectedProfile(), "Compte");

        setLayout(null);
        setOpaque(false);

        connectionButton.setBounds(123, 246);
        connectionButton.addEventListener(this);
        add(connectionButton);

        connectionMicrosoftButton.setBounds(512, 120);
        connectionMicrosoftButton.addEventListener(this);
        add(connectionMicrosoftButton);

        resetButton.setBounds(675, 387);
        resetButton.addEventListener(this);
        add(resetButton);

        textField.setForeground(Color.WHITE);
        textField.setFont(CustomFonts.kollektifBoldFont.deriveFont(25f));
        textField.setCaretColor(Color.RED);
        textField.setSelectionColor(new Color(255, 20, 20, 200));
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setBounds(44, 55, 386, 60);
        add(textField);

        textFieldLabel.setForeground(Color.WHITE);
        textFieldLabel.setFont(textField.getFont().deriveFont(20f));
        textFieldLabel.setBounds(42, 13, 386, 60);
        add(textFieldLabel);

        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(textField.getFont());
        passwordField.setCaretColor(Color.RED);
        passwordField.setSelectionColor(new Color(255, 20, 20, 200));
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setBounds(44, 149, 386, 60);
        add(passwordField);

        passwordFieldLabel.setForeground(Color.WHITE);
        passwordFieldLabel.setFont(textFieldLabel.getFont());
        passwordFieldLabel.setBounds(42, 107, 386, 60);
        add(passwordFieldLabel);

        infosLabel.setForeground(new Color(109, 109, 109));
        infosLabel.setFont(CustomFonts.kollektifFont.deriveFont(16f));
        infosLabel.setCaretColor(Color.RED);
        infosLabel.setSelectionColor(new Color(255, 20, 20, 200));
        infosLabel.setOpaque(false);
        infosLabel.setBorder(null);
        infosLabel.setEditable(false);
        infosLabel.setBounds(10, 453, 800, 33);
        add(infosLabel);

        add(getBg().getPanel());
    }

    public void setVisible(boolean aFlag) {
        if (aFlag) {
            setTitle("Profil " + ProfileSaver.getSelectedProfile());
        }
        super.setVisible(aFlag);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        Object src = e.getSource();

        if (src == connectionButton) {
            Saver saver = getSelectedSaver();
            String oldAccount = saver.get(ProfileSaver.KEY.INFOS_NAME.get());

            if (textField.getText().replaceAll(" ", "").length() == 0 || passwordField.getPassword().length == 0) {
                errorMessage("Erreur de connexion", "Erreur, veuillez     entrer un email et un   mot de passe valides");
                return;
            }

            Thread connect = new Thread(() -> {
                try {
                    try {
                        Launcher.println("Connexion...");
                        Launcher.microsoftAuth(textField.getText(), new String(passwordField.getPassword()), saver);
                    } catch (MicrosoftAuthenticationException m) {
                        errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                        return;
                    }
                    doneMessage("Connexion r\u00e9ussie", "Connexion r\u00e9ussie");
                    verifyNoAccountBefore(oldAccount, saver);
                    initProfileButtons();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", ex.getLocalizedMessage(), t);
                }
            });

            connect.start();


        } else if (src == connectionMicrosoftButton) {
            Saver saver = getSelectedSaver();
            String oldAccount = saver.get(ProfileSaver.KEY.INFOS_NAME.get());

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
                    verifyNoAccountBefore(oldAccount, saver);
                    initProfileButtons();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Thread t = new Thread(() -> {
                        System.exit(1);
                    });
                    PopUpMessages.errorMessage("Erreur", ex.getLocalizedMessage(), t);
                }
            });
            connect.start();
        } else if (src == resetButton) {
            Thread ifYes = new Thread(() -> {
                resetDataFiles(getSelectedSaver());
                textField.setText("");
                doneMessage("Compte supprim\u00e9", "Donn\u00e9es du compte r\u00e9initialis\u00e9es");
                initProfileButtons();
            });

            Thread ifNo = new Thread();

            yesNoMessage("R\u00e9initialisation du compte", "Voulez vous vraiment r\u00e9initialiser le compte ?", ifYes, ifNo);

        }
    }
}
