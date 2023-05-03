package fr.timeto.astrauworld.launcher.panels.profile;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.AWPasswordField;
import fr.timeto.astrauworld.launcher.customelements.AWTextField;
import fr.timeto.astrauworld.launcher.customelements.HSLColor;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.timutilslib.CustomFonts;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;

import java.awt.*;
import java.util.Objects;

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
    public final AWTextField textField = new AWTextField("", 25f);
    public final JLabel textFieldLabel = new JLabel("Email");

    /**
     * PasswordField pour entrer le mot de passe pour la connexion
     */
    public final AWPasswordField passwordField = new AWPasswordField(25f);
    public final JLabel passwordFieldLabel = new JLabel("Mot de passe");

    public final JTextArea infosLabel = new JTextArea(parseUnicode("Vos informations de connexion sont stockées dans votre ordinateur seulement, elles ne sont en aucun cas \n" +
            "partagées avec la team Astrauworld ou n'importe qui d'autre. Ne partagez vos identifiants à personne."));

    public final JLabel accountLabel = new JLabel("", SwingConstants.LEFT);
    public final JLabel accountConnectedLabel = new JLabel("Connecté en tant que: ", SwingConstants.LEFT);

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

        textField.setBounds(39, 55, 395, 55);
        add(textField);

        textFieldLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        textFieldLabel.setFont(textField.getFont().deriveFont(20f));
        textFieldLabel.setBounds(42, 10, 386, 60);
        add(textFieldLabel);

        passwordField.setBounds(39, 149, 395, 55);
        add(passwordField);

        passwordFieldLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        passwordFieldLabel.setFont(textFieldLabel.getFont());
        passwordFieldLabel.setBounds(42, 104, 386, 60);
        add(passwordFieldLabel);

        infosLabel.setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get().darker());
        infosLabel.setFont(CustomFonts.robotoMediumFont.deriveFont(14f));
        infosLabel.setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        infosLabel.setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        infosLabel.setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        infosLabel.setOpaque(false);
        infosLabel.setBorder(null);
        infosLabel.setEditable(false);
        infosLabel.setBounds(10, 450, 800, 40);
        add(infosLabel);

        accountLabel.setBounds(380 - 178, 523 - 113, 276, 31);
        accountLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        accountLabel.setFont(CustomFonts.robotoBlackFont.deriveFont(17f));
        this.add(accountLabel);

        accountConnectedLabel.setBounds(198 - 178, 523 - 113, 191, 31);
        accountConnectedLabel.setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get());
        accountConnectedLabel.setFont(accountLabel.getFont());
        add(accountConnectedLabel);

        add(getBg().getPanel());
    }

    @Override
    public void recolor() {
        textField.recolor();
        textFieldLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());

        passwordField.recolor();
        passwordFieldLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());

        infosLabel.setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get().darker());
        infosLabel.setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        infosLabel.setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        infosLabel.setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));

        accountLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        accountConnectedLabel.setForeground(Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR.get());
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag) {
            setTitle("Profil " + ProfileSaver.getSelectedProfile());

            if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                accountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                accountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                LauncherPanel.enablePlayButtons(true);
            } else {
                accountLabel.setText("");
                accountConnectedLabel.setText("Non connect\u00e9");
                LauncherPanel.enablePlayButtons(false);
            }
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
