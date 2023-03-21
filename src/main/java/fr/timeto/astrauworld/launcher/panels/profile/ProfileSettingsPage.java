package fr.timeto.astrauworld.launcher.panels.profile;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.CustomSpinnerUI;
import fr.timeto.astrauworld.launcher.customelements.TexturedSwitchButton;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.astrauworld.launcher.panels.PageCreator;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.Launcher.parseUnicode;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.initProfileButtons;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.getSelectedSaver;
import static fr.timeto.timutilslib.PopUpMessages.doneMessage;

public class ProfileSettingsPage extends PageCreator implements SwingerEventListener {

    public JTextField profileNameTextField = new JTextField();
    public JLabel profileNameTextFieldLabel = new JLabel("Nom du profil");
    public final TexturedSwitchButton helmIconSwitchButton = new TexturedSwitchButton(ProfileSaver.KEY.SETTINGS_HELMICON, false);
    public JLabel helmIconSwitchButtonLabel1 = new JLabel("Ic\u00f4ne du joueur avec", SwingConstants.RIGHT);
    public JLabel helmIconSwitchButtonLabel2 = new JLabel("la 2nde couche", SwingConstants.RIGHT);
    public JSpinner allowedRamSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 256.00, 1));
    public JLabel allowedRamSpinnerLabel1 = new JLabel(parseUnicode("RAM allouée au jeu"), SwingConstants.RIGHT);
    public JLabel allowedRamSpinnerLabel2 = new JLabel(parseUnicode("En Go (3Go conseillé)"), SwingConstants.RIGHT);
    public STexturedButton saveSettings = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/reglages/saveProfileNameButton.png"), getResourceIgnorePath("/assets/launcher/profilesPage/reglages/saveProfileNameButton-hover.png"));
    public final JLabel accountLabel = new JLabel("", SwingConstants.LEFT);
    public final JLabel accountConnectedLabel = new JLabel("Connecté en tant que: ", SwingConstants.LEFT);

    public ProfileSettingsPage() {
        super(PageName.PROFILE_SETTINGS, "Profil " + ProfileSaver.getSelectedProfile(), "Paramètres");

        setLayout(null);
        setOpaque(false);

        profileNameTextField.setForeground(Color.WHITE);
        profileNameTextField.setFont(CustomFonts.kollektifBoldFont.deriveFont(25f));
        profileNameTextField.setCaretColor(Color.RED);
        profileNameTextField.setSelectionColor(new Color(255, 20, 20, 200));
        profileNameTextField.setOpaque(false);
        profileNameTextField.setBorder(null);
        profileNameTextField.setBounds(317, 25, 310, 63);
        add(profileNameTextField);

        profileNameTextFieldLabel.setForeground(Color.WHITE);
        profileNameTextFieldLabel.setFont(profileNameTextField.getFont());
        profileNameTextFieldLabel.setBounds(118, 42, 168, 24);
        add(profileNameTextFieldLabel);

        helmIconSwitchButton.setBounds(313, 117);
        helmIconSwitchButton.addEventListener(this);
        add(helmIconSwitchButton);

        helmIconSwitchButtonLabel1.setForeground(Color.WHITE);
        helmIconSwitchButtonLabel1.setFont(profileNameTextFieldLabel.getFont());
        helmIconSwitchButtonLabel1.setBounds(32, 110, 254, 44);
        add(helmIconSwitchButtonLabel1);

        helmIconSwitchButtonLabel2.setForeground(Color.WHITE);
        helmIconSwitchButtonLabel2.setFont(profileNameTextFieldLabel.getFont());
        helmIconSwitchButtonLabel2.setBounds(32, 132, 254, 44);
        add(helmIconSwitchButtonLabel2);

        allowedRamSpinner.setUI(new CustomSpinnerUI());
        allowedRamSpinner.setForeground(Color.WHITE);
        allowedRamSpinner.setFont(profileNameTextField.getFont());
        allowedRamSpinner.setOpaque(false);
        allowedRamSpinner.setBorder(null);
        allowedRamSpinner.setBounds(313, 193, 93, 58);
        allowedRamSpinner.setOpaque(false);
        allowedRamSpinner.getEditor().setOpaque(false);
        ((JSpinner.NumberEditor)allowedRamSpinner.getEditor()).getTextField().setOpaque(false);
        ((JSpinner.NumberEditor)allowedRamSpinner.getEditor()).getTextField().setForeground(Color.WHITE);
        ((JSpinner.NumberEditor)allowedRamSpinner.getEditor()).getTextField().setSelectionColor(new Color(255, 20, 20, 200));
        ((JSpinner.NumberEditor)allowedRamSpinner.getEditor()).getTextField().setBorder(new EmptyBorder(5, 0, 0, 4));
        add(allowedRamSpinner);

        allowedRamSpinnerLabel1.setForeground(Color.WHITE);
        allowedRamSpinnerLabel1.setFont(profileNameTextFieldLabel.getFont());
        allowedRamSpinnerLabel1.setBounds(48, 191, 238, 43);
        add(allowedRamSpinnerLabel1);

        allowedRamSpinnerLabel2.setForeground(new Color(164, 164, 164));
        allowedRamSpinnerLabel2.setFont(CustomFonts.kollektifFont.deriveFont(20f));
        allowedRamSpinnerLabel2.setBounds(48, 211, 238, 43);
        add(allowedRamSpinnerLabel2);

        saveSettings.setBounds(646, 430);
        saveSettings.addEventListener(this);
        add(saveSettings);

        accountLabel.setBounds(380 - 178, 577 - 113, 276, 31);
        accountLabel.setForeground(Color.WHITE);
        accountLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(17f));
        this.add(accountLabel);

        accountConnectedLabel.setBounds(198 - 178, 577 - 113, 191, 31);
        accountConnectedLabel.setForeground(new Color(179, 179, 179));
        accountConnectedLabel.setFont(accountLabel.getFont());
        add(accountConnectedLabel);

        add(bg.getPanel());
    }

    public void setVisible(boolean aFlag) {
        if (aFlag) {
            setTitle("Profil " + ProfileSaver.getSelectedProfile());
            profileNameTextField.setText(getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_PROFILENAME.get()));
            helmIconSwitchButton.defineTextures();
            allowedRamSpinner.setValue(Float.parseFloat(getSelectedSaver().get(ProfileSaver.KEY.SETTINGS_RAM.get())));

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
    public void onEvent(SwingerEvent swingerEvent) {
        Object src = swingerEvent.getSource();

        if (src == helmIconSwitchButton) {
            helmIconSwitchButton.toggleButton();
            initProfileButtons();
        } else if (src == saveSettings) {
            getSelectedSaver().set(ProfileSaver.KEY.SETTINGS_RAM.get(), allowedRamSpinner.getValue().toString());
            getSelectedSaver().set(ProfileSaver.KEY.SETTINGS_PROFILENAME.get(), profileNameTextField.getText());
            initProfileButtons();
            doneMessage("Enregistr\u00e9 !", "Param\u00e8tres           enregistr\u00e9s");
            LauncherSystemTray.changeTrayTooltip();
        }

    }
}
