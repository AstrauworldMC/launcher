package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.*;
import fr.timeto.astrauworld.launcher.pagesutilities.*;
import fr.timeto.astrauworld.launcher.panels.LeftMenuButton;
import fr.timeto.astrauworld.launcher.panels.LeftMenuSelector;
import fr.timeto.astrauworld.launcher.panels.ProfileButton;
import fr.timeto.astrauworld.launcher.panels.ShaderPanel;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;
import static fr.timeto.timutilslib.CustomFonts.*;

/**
 * La classe du panel du launcher
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 * @see LauncherFrame
 */
@SuppressWarnings("InstantiatingAThreadWithDefaultRunMethod")
public class LauncherPanel extends JPanel implements SwingerEventListener, ActionListener { // TODO faire une belle doc en utilisant la run assets [javadoc] pour voir où y'a rien

     /**
      * La variable du background, change à changement de page
      * @see PageChange
      */
     public static Image background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

     static String lineSeparator = System.getProperty("line.separator");

     public static boolean inDownload = false;

     public static void inDownloadError() {
          PopUpMessages.errorMessage("Erreur", Launcher.parseUnicode("Un téléchargement est déjà en route"));
     }

     /**
      * Initialise les boutons de profils, à appeler dès que le profil change.
      * Change le {@code ProfileButton}, la {@code ProfileIcon} et le {@code ProfileNameLabel}
      * @author <a href="https://github.com/TimEtOff">TimEtO</a>
      */
     public static void initProfileButtons() {
          try {
               initProfileIcon();
          } catch (IOException e) {
               throw new RuntimeException(e);
          }
          firstProfileButton.initButton();

          secondProfileButton.initButton();

          thirdProfileButton.initButton();

          try {
               if (!Objects.equals(ProfileSaver.selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "")) {
                    profileAccountLabel.setText(ProfileSaver.selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                    profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                    enablePlayButtons(true);
               } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    enablePlayButtons(false);
               }
          } catch (NullPointerException ignored) {}

     }

     public static void verifyNoAccountBefore(String oldAccount, Saver saver) {
          if (oldAccount.replaceAll(" ", "").equals("")) {
               saver.set(KEY.SETTINGS_PROFILENAME, saver.get(KEY.INFOS_NAME));
          }
     }

     /**
      * La classe regroupant tous les composants
      * @since Beta 2.2.0
      * @author <a href="https://github.com/TimEtOff">TimEtO</a>
      */
     public static class Components {
          // Common components
          /**
           * Le bouton pour fermer la fenêtre et terminer le processus
           */
          public static final STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/commonButtons/quitButton.png"), getResourceIgnorePath("/assets/launcher/commonButtons/quitButtonHover.png"));
          /**
           * Le bouton pour réduire la fenêtre
           */
          public static final STexturedButton hideButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/commonButtons/hideButton.png"), getResourceIgnorePath("/assets/launcher/commonButtons/hideButtonHover.png"));

          public static final STexturedButton updateButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/commonButtons/updateButton.png"), getResourceIgnorePath("/assets/launcher/commonButtons/updateButtonHover.png"));

          public static final LeftMenuSelector leftMenuSelector = new LeftMenuSelector();
          public static final JTextArea launcherVersionLabel = new JTextArea("Version du launcher:" + lineSeparator + Launcher.version);
          public static final LeftMenuButton newsButton = new LeftMenuButton("Actualit\u00e9s", getResourceIgnorePath("/assets/launcher/icons/newsIcon.png"));
          public static final ProfileButton firstProfileButton = new ProfileButton(firstProfileSaver);
          public static final ProfileButton secondProfileButton = new ProfileButton(secondProfileSaver);
          public static final ProfileButton thirdProfileButton = new ProfileButton(thirdProfileSaver);

          /**
           * Bouton du menu général de gauche pour ouvrir la page des changelogs
           * @see Changelogs
           */
          public static final LeftMenuButton changesButton = new LeftMenuButton("Changelogs", getResourceIgnorePath("/assets/launcher/icons/changesIcon.png"));
          /**
           * Bouton du menu général de gauche pour ouvrir la page principale à propos
           */
          public static final LeftMenuButton aboutButton = new LeftMenuButton("\u00c0 propos", getResourceIgnorePath("/assets/launcher/icons/aboutIcon.png"));
          /**
           * Label contenant le titre de la page, affiché au-dessus du contenu de la page
           * @see Components#subTitleLabel
           */
          public static final JLabel titleLabel = new JLabel("", SwingConstants.LEFT);
          /**
           * Label contenant le sous-titre de la page, affiché au-dessus du {@link Components#titleLabel}
           * @see Components#titleLabel
           */
          public static final JLabel subTitleLabel = new JLabel("none", SwingConstants.LEFT);
          /**
           * La barre de chargement, apparait lors d'un téléchargement
           * @see Components#barLabel
           * @see Components#percentLabel
           * @see Components#infosLabel
           */
          public static SColoredBar loadingBar = new SColoredBar(getTransparentWhite(25), Color.RED){
               @Override
               public void setVisible(boolean aFlag) {
                    super.setVisible(aFlag);
                    if (aFlag) {
                         profileNewsButton.setLocation(profileNewsButton.getX(), 575);
                    } else {
                         profileNewsButton.setLocation(profileNewsButton.getX(), 578);
                    }
               }
          };
          /**
           * Label à gauche dans la {@link Components#loadingBar}, apparait et affiche les noms des fichiers téléchargés lors d'un d'un téléchargement
           * @see Components#loadingBar
           * @see Components#percentLabel
           * @see Components#infosLabel
           */
          public static JLabel barLabel = new JLabel("", SwingConstants.LEFT);
          /**
           * Label à droite dans la {@link Components#loadingBar}, apparait et affiche les noms des fichiers téléchargés lors d'un d'un téléchargement
           * @see Components#loadingBar
           * @see Components#barLabel
           * @see Components#infosLabel
           */
          public static JLabel percentLabel = new JLabel("", SwingConstants.RIGHT);
          /**
           * Label au centre dans la {@link Components#loadingBar}, apparait et affiche les noms des fichiers téléchargés lors d'un d'un téléchargement
           * @see Components#loadingBar
           * @see Components#percentLabel
           * @see Components#barLabel
           */
          public static JLabel infosLabel = new JLabel("", SwingConstants.CENTER);

          /**
           * Bouton invisible en bas à droite de la fenêtre pour régler le bug de l'arrière-plan qui ne se met pas à jour.
           * <p> Doit être mis visible puis invisible à chaque changement de page
           */
          public static final STexturedButton corner = new STexturedButton(getResourceIgnorePath("/assets/launcher/main/corner.png"), getResourceIgnorePath("/assets/launcher/main/corner.png"), getResourceIgnorePath("/assets/launcher/main/corner.png"));

          // News components
          public static final NewsPanel newsScrollPanel = new NewsPanel();
          public static final NewsOpenPanel newsOpenScrollPanel = new NewsOpenPanel();

          // Profiles components - up
          /**
           * Bouton d'onglet de la page principale des pages de profil
           */
          public static final STexturedButton profilePlayTabButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/up/Jouer-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Jouer-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Jouer-selected.png"));
          /**
           * Bouton d'onglet de la page du compte des pages de profil
           */
          public static final STexturedButton profileAccountTabButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/up/Compte-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Compte-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Compte-selected.png"));
          /**
           * Bouton d'onglet de la page des addons des pages de profil
           */
          public static final STexturedButton profileAddonsTabButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/up/Addons-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Addons-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Addons-selected.png"));
          /**
           * Bouton d'onglet de la page des paramètres des pages de profil
           */
          public static final STexturedButton profileSettingsTabButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/up/Reglages-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Reglages-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Reglages-selected.png"));

          // Profiles components - home
          /**
           * Booléen de si le bouton de lancement est dans le statut de lancement
           */
          public static boolean profilePlayButtonIsPlayStatus = true;
          /**
           * Booléen de si les fichiers du jeu sont en update
           */
          public static boolean isUpdating = false;
          /**
           * Bouton pour lancer une update puis le jeu directement vers le serveur
           */
          public static final STexturedButton profilePlayButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/playButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/playButton-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/playButton-disabled.png"));
          public static final STexturedButton profileServerInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/serverInfosButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/serverInfosButton-hover.png"));
          /**
           * Bouton pour voir les actualités
           */
          public static final STexturedButton profileNewsButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/newsButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/newsButton-hover.png"));
          /**
           * Bouton pour lancer une update puis le jeu
           */
          public static final STexturedButton profileLaunchToMenuButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/launchToMenuButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/launchToMenuButton-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/launchToMenuButton-disabled.png"));
          /**
           * Bouton pour lancer une update des fichiers du jeu
           */
          public static final STexturedButton profileDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/downloadButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/downloadButton-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/downloadButton-disabled.png"));
          /**
           * Label du nom du compte connecté du profil
           */
          public static final JLabel profileAccountLabel = new JLabel("", SwingConstants.LEFT);
          public static final JLabel profileAccountConnectedLabel = new JLabel("Connecté en tant que: ");
          public static final JPanel profileDiapoPanel = new JPanel();
          public static final JLabel profileTextLogo = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/logo-texte.png")));
          public static JLabel profileDiapoImage1 = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/lake-day.png")));
          public static JLabel profileDiapoImage2 = new JLabel(new ImageIcon(Swinger.getResourceIgnorePath("/assets/launcher/profilesPage/townHall-day.png")));

          // Profiles components - compte
          /**
           * Bouton de connexion classique dans la page des profils - compte
           * @see Components#profileAccountConnectionMicrosoftButton
           */
          public static final STexturedButton profileAccountConnectionButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionButton-hover.png"));
          /**
           * Bouton de connexion avec une webview de Microsoft dans la page des profils - compte
           * @see Components#profileAccountConnectionButton
           */
          public static final STexturedButton profileAccountConnectionMicrosoftButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionWithMicrosoftButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/compte/connectionWithMicrosoftButton-hover.png"));
          /**
           * Bouton pour réinitialiser les informations du compte connecté
           */
          public static final STexturedButton profileAccountResetButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/compte/resetButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/compte/resetButton-hover.png"));
          /**
           * TextField pour entrer l'adresse email pour la connexion
           */
          public static final JTextField profileAccountTextField = new JTextField("");

          /**
           * PasswordField pour entrer le mot de passe pour la connexion
           */
          public static final JPasswordField profileAccountPasswordField = new JPasswordField();

          // Profiles components - addons
          /**
           * Bouton pour les shaders
           */
          public static STexturedButton profileAddonsShadersButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/shadersButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/shadersButton-hover.png"));
          /**
           * Bouton pour les resource packs
           */
          public static STexturedButton profileAddonsResourcePacksButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/resourcePacksButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/resourcePacksButton-hover.png"));
          public static STexturedButton profileAddonsModsButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/modsButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/modsButton-hover.png"));
          public static STexturedButton profileAddonsGoToFolderButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/goToFolder-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/goToFolder-hover.png"));
          /**
           * Bouton I/O pour Optfine
           */
          public static TexturedSwitchButton profileAddonsOptifineSwitchButton = new TexturedSwitchButton(KEY.MOD_OPTIFINE, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), false);
          /**
           * Bouton I/O pour le mod client 'First Person Model'
           * @see KEY#MOD_FPSMODEL
           * @see Components#profileModsFpsmodelMoreInfosButton
           */
          public static final TexturedSwitchButton profileModsFpsmodelSwitchButton = new TexturedSwitchButton(KEY.MOD_FPSMODEL, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), false);
          /**
           * Bouton pour avoir plus d'informations sur le mod client 'First Person Model', revoie à la page CurseForge
           * @see KEY#MOD_FPSMODEL
           * @see Components#profileModsFpsmodelSwitchButton
           */
          public static final STexturedButton profileModsFpsmodelMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-hover.png"));
          /**
           * Bouton I/O pour le mod client 'Better Third Person'
           * @see KEY#MOD_BETTERTPS
           * @see Components#profileModsBettertpsMoreInfosButton
           */
          public static final TexturedSwitchButton profileModsBettertpsSwitchButton = new TexturedSwitchButton(KEY.MOD_BETTERTPS, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), false);
          /**
           * Bouton pour avoir plus d'informations sur le mod client 'Better Third Person', revoie à la page CurseForge
           * @see KEY#MOD_BETTERTPS
           * @see Components#profileModsBettertpsSwitchButton
           */
          public static final STexturedButton profileModsBettertpsMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-hover.png"));
          /**
           * Bouton I/O pour le mod client 'Falling Leaves'
           * @see KEY#MOD_FALLINGLEAVES
           * @see Components#profileModsFallingleavesMoreInfosButton
           */
          public static final TexturedSwitchButton profileModsFallingleavesSwitchButton = new TexturedSwitchButton(KEY.MOD_FALLINGLEAVES, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), false);
          /**
           * Bouton pour avoir plus d'informations sur le mod client 'Falling Leaves', revoie à la page CurseForge
           * @see KEY#MOD_FALLINGLEAVES
           * @see Components#profileModsFallingleavesSwitchButton
           */
          public static final STexturedButton profileModsFallingleavesMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-hover.png"));
          /**
           * Bouton I/O pour le mod client 'AppleSkin'
           * @see KEY#MOD_APPLESKIN
           * @see Components#profileModsAppleskinMoreInfosButton
           */
          public static final TexturedSwitchButton profileModsAppleskinSwitchButton = new TexturedSwitchButton(KEY.MOD_APPLESKIN, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), false);
          /**
           * Bouton pour avoir plus d'informations sur le mod client 'AppleSkin', revoie à la page CurseForge
           * @see KEY#MOD_APPLESKIN
           * @see Components#profileModsAppleskinSwitchButton
           */
          public static final STexturedButton profileModsAppleskinMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-hover.png"));
          /**
           * Bouton I/O pour le mod client 'Sound Physics Remastered'
           * @see KEY#MOD_SOUNDPHYSICS
           * @see Components#profileModsSoundphysicsMoreInfosButton
           */
          public static final TexturedSwitchButton profileModsSoundphysicsSwitchButton = new TexturedSwitchButton(KEY.MOD_SOUNDPHYSICS, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), false);
          /**
           * Bouton pour avoir plus d'informations sur le mod client 'Sound Physics Remastered', revoie à la page CurseForge
           * @see KEY#MOD_SOUNDPHYSICS
           * @see Components#profileModsSoundphysicsSwitchButton
           */
          public static final STexturedButton profileModsSoundphysicsMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/moreInfos-hover.png"));

          public static final STexturedButton profileShadersSeeComparisonButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/seeComparison-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/seeComparison-hover.png"));
          public static final STexturedButton profileShadersChocapicV6PlusButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-hover.png"));
          public static final STexturedButton profileShadersChocapicV7_1PlusButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-hover.png"));
          public static final STexturedButton profileShadersChocapicV9PlusButton =new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/addons/plus-hover.png"));
          public static final ShaderPanel profileShadersSeusRenewedPanel = new ShaderPanel("SEUS Renewed", shaderSeusRenewed);

          public static final ShaderPanel profileShadersChocapicV6LitePanel = new ShaderPanel("Chocapic13 V6 Lite", shaderChocapicV6Lite);
          public static final ShaderPanel profileShadersChocapicV6LowPanel = new ShaderPanel("Chocapic13 V6 Low", shaderChocapicV6Low);
          public static final ShaderPanel profileShadersChocapicV6MediumPanel = new ShaderPanel("Chocapic13 V6 Medium", shaderChocapicV6Medium);
          public static final ShaderPanel profileShadersChocapicV6UltraPanel = new ShaderPanel("Chocapic13 V6 Ultra", shaderChocapicV6Ultra);
          public static final ShaderPanel profileShadersChocapicV6ExtremePanel = new ShaderPanel("Chocapic13 V6 Extreme", shaderChocapicV6Ultra);

          public static final ShaderPanel profileShadersChocapicV7_1ToasterPanel = new ShaderPanel("V7.1.1 Toaster Edition", shaderChocapicV7_1Toaster);
          public static final ShaderPanel profileShadersChocapicV7_1LitePanel = new ShaderPanel("Chocapic13 V7.1 Lite", shaderChocapicV7_1Lite);
          public static final ShaderPanel profileShadersChocapicV7_1LowPanel = new ShaderPanel("Chocapic13 V7.1 Low", shaderChocapicV7_1Low);
          public static final ShaderPanel profileShadersChocapicV7_1MediumPanel = new ShaderPanel("Chocapic13 V7.1 Medium", shaderChocapicV7_1Medium);
          public static final ShaderPanel profileShadersChocapicV7_1UltraPanel = new ShaderPanel("Chocapic13 V7.1 Ultra", shaderChocapicV7_1Ultra);
          public static final ShaderPanel profileShadersChocapicV7_1ExtremePanel = new ShaderPanel("Chocapic13 V7.1 Extreme", shaderChocapicV7_1Extreme);

          public static final ShaderPanel profileShadersChocapicV9LowPanel = new ShaderPanel("Chocapic13 V9 Low", shaderChocapicV9Low);
          public static final ShaderPanel profileShadersChocapicV9MediumPanel = new ShaderPanel("Chocapic13 V9 Medium", shaderChocapicV9Medium);
          public static final ShaderPanel profileShadersChocapicV9HighPanel = new ShaderPanel("Chocapic13 V9 High", shaderChocapicV9High);
          public static final ShaderPanel profileShadersChocapicV9ExtremePanel = new ShaderPanel("Chocapic13 V9 Extreme", shaderChocapicV9Extreme);
          public static final ShaderPanel profileShadersChocapicV9_1ExtremePanel = new ShaderPanel("V9.1 Extreme Beta 5", shaderChocapicV9_1Extreme);


          // Profiles components - reglages
          /**
           * Text field pour changer le nom du profil dans la page profil - réglages
           */
          public static JTextField profileSettingsProfileNameTextField = new JTextField();
          /**
           * Bouton I/O pour avoir la 2e couche sur l'avatar du profil dans la page profil - réglages
           */
          public static final TexturedSwitchButton profileSettingsHelmIconSwitchButton = new TexturedSwitchButton(KEY.SETTINGS_HELMICON, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), false);
          /**
           * Spinner pour sélectionner la ram allouée au jeu au lancement dans la page profil - réglages
           */
          public static JSpinner profileSettingsAllowedRamSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 256.00, 1));
          public static TexturedSwitchButton profileSettingsMainProfileSwitchButton = new TexturedSwitchButton(KEY.SETTINGS_MAINPROFILE, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"), true);
          /**
           * Bouton pour sauvegarder les paramètres du profil
           */
          public static STexturedButton profileSettingsSaveSettings = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/reglages/saveProfileNameButton.png"), getResourceIgnorePath("/assets/launcher/profilesPage/reglages/saveProfileNameButton-hover.png"));

          // Changelogs components
          /**
           * La liste des versions des changelogs
           * @since Beta2.1.2
           * @see Changelogs#getChangelogsVersionsList()
           */
          public static final String[] changelogsVersionsArrayList = Changelogs.getChangelogsVersionsList();
          /**
           * La combo-box pour sélectionner la version du changelog voulu
           * @since Beta2.1.2
           * @see Changelogs
           */
          public static JComboBox<Object> changelogsVersionComboBox = new JComboBox<>(changelogsVersionsArrayList);
          /**
           * La text area non éditable où apparait le texte du changelog
           * @since Beta2.1.2
           * @see Changelogs
           */
          public static JTextArea changelogsTextArea = new JTextArea();

          // About components - up
          /**
           * Le bouton de l'onglet de la page à propos - infos
           * @since Beta2.1.2
           */
          public static final STexturedButton aboutInfosTabButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/up/infosTab-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/up/infosTab-hover.png"), getResourceIgnorePath("/assets/launcher/aboutPage/up/infosTab-selected.png"));
          /**
           * Le bouton de l'onglet de la page à propos - addons
           * @since Beta2.1.2
           */
          public static final STexturedButton aboutModsTabButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/up/modsTab-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/up/modsTab-hover.png"), getResourceIgnorePath("/assets/launcher/aboutPage/up/modsTab-selected.png"));

          // About components - infos
          /**
           * Bouton du logo Astrauworld avec le texte qui renvoie au site dans la page à propos - infos
           * @since Beta2.1.2
           */
          public static final STexturedButton aboutTextLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/logo-texte.png"), getResourceIgnorePath("/assets/launcher/aboutPage/logo-texte.png"));
          /**
           * Bouton du logo d'Astrauwolf dans la page à propos - infos (easter egg) dans la page à propos - infos
           *
           * @since Beta2.1.2
           * @see EasterEggs#polishCow
           */
          public static final STexturedButton aboutAstrauwolfLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoAstrau.png"), getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoAstrau.png"));
          /**
           * Bouton du logo de Capitenzo974 dans la page à propos - infos (easter egg) dans la page à propos - infos
           * @since Beta2.1.2
           * @see EasterEggs#frogWalking
           */
          public static final STexturedButton aboutCapitenzoLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/capitenzoPfp.png"), getResourceIgnorePath("/assets/launcher/aboutPage/capitenzoPfp.png"));
          /**
           * Bouton du logo de TimEtO dans la page à propos - infos (easter egg) dans la page à propos - infos
           * @since Beta2.1.2
           * @see EasterEggs#rickroll
           */
          public static final STexturedButton aboutTimEtOLogo = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoTim.png"), getResourceIgnorePath("/assets/launcher/aboutPage/aboutLogoTim.png"));
          /**
           * Bouton qui envoie à la page GitHub de AstrauworldMC dans la page à propos - infos
           * @since Beta2.1.2
           */
          public static final STexturedButton aboutGithubButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/github-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/github-hover.png"));
          /**
           * Bouton qui renvoie à un mailto:[l'adresse mail d'Astrauworld] dans la page à propos - infos
           * @since Beta2.1.2
           */
          public static final STexturedButton aboutMailButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/mail-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/mail-hover.png"));
          /**
           * Bouton qui envoie à la page d'invitation au serveur Discord d'Astrauworld dans la page à propos - infos
           * @since Beta2.1.2
           */
          public static final STexturedButton aboutDiscordButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/discord-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/discord-hover.png"));
          /**
           * Bouton qui envoie à la page du compte Twitter d'@AstrauworldMC dans la page à propos - infos
           * @since Beta2.1.2
           */
          public static final STexturedButton aboutTwitterButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/aboutPage/twitter-normal.png"), getResourceIgnorePath("/assets/launcher/aboutPage/twitter-hover.png"));
          /**
           * Label où est écrit le nombre d'easter eggs trouvés dans la page à propos - infos
           * @since Beta2.1.2
           */
          public static JLabel aboutEastereggsLabel = new JLabel("", SwingConstants.LEFT);
     }

     /**
      * Initialise le panel de la frame (boutons, textes, images...)
      * @author <a href="https://github.com/TimEtOff">TimEtO</a>
      */
     public LauncherPanel() {
          this.setLayout(null);

          initFonts();

          // Common components
          quitButton.setBounds(970, 4);
          quitButton.addEventListener(this);
          this.add(quitButton);

          hideButton.setBounds(935, 4);
          hideButton.addEventListener(this);
          this.add(hideButton);

          updateButton.setBounds(899, 5);
          updateButton.addEventListener(this);
          this.add(updateButton);

          leftMenuSelector.setLocation(-2, 187);
          add(leftMenuSelector);

          newsButton.setLocation(0, 113);
          this.add(newsButton);

          try {
               initProfileIcon();
          } catch (IOException e) {
               throw new RuntimeException(e);
          }

          firstProfileButton.setLocation(0, 174);
          this.add(firstProfileButton);

          secondProfileButton.setLocation(0, 235);
          this.add(secondProfileButton);

          thirdProfileButton.setLocation(0, 296);
          this.add(thirdProfileButton);

          initProfileButtons();

          changesButton.setLocation(0, 510);
          this.add(changesButton);

          aboutButton.setLocation(0, 571);
          this.add(aboutButton);

          titleLabel.setBounds(190, 60, 809, 23);
          titleLabel.setForeground(Color.WHITE);
          titleLabel.setFont(kollektifBoldFont.deriveFont(20f));
          this.add(titleLabel);

          subTitleLabel.setBounds(190, 37, 809, 23);
          subTitleLabel.setForeground(Color.WHITE);
          subTitleLabel.setFont(titleLabel.getFont().deriveFont(16f));
          this.add(subTitleLabel);

          barLabel.setBounds(181, 612, 269, 16);
          barLabel.setForeground(Color.WHITE);
          barLabel.setFont(kollektifFont.deriveFont(10f));
          this.add(barLabel);

          percentLabel.setBounds(920, 612, 70, 16);
          percentLabel.setForeground(Color.WHITE);
          percentLabel.setFont(barLabel.getFont());
          this.add(percentLabel);

          infosLabel.setBounds(460, 612, 255, 16);
          infosLabel.setForeground(Color.WHITE);
          infosLabel.setFont(barLabel.getFont());
          this.add(infosLabel);

          loadingBar.setBounds(178, 610, 821, 20);
          this.add(loadingBar);
          loadingBar.setVisible(false);

          launcherVersionLabel.setBounds(9, 39, 150, 50);
          launcherVersionLabel.setForeground(new Color(100, 100, 100));
          launcherVersionLabel.setFont(kollektifBoldFont.deriveFont(14f));
          launcherVersionLabel.setEditable(false);
          launcherVersionLabel.setOpaque(false);
          this.add(launcherVersionLabel);

          corner.setBounds(this.getWidth(), this.getHeight());
          corner.addEventListener(this);
          this.add(corner);
          corner.setEnabled(false);

          // News components
          newsScrollPanel.setBounds(178, 113, 822, 517);
          this.add(newsScrollPanel);
          newsScrollPanel.setVisible(false);

          newsOpenScrollPanel.setBounds(178, 113, 822, 517);
          this.add(newsOpenScrollPanel);
          newsOpenScrollPanel.setVisible(false);

          // Profiles components - up
          profilePlayTabButton.setBounds(178, 89);
          profilePlayTabButton.addEventListener(this);
          this.add(profilePlayTabButton);
          profilePlayTabButton.setVisible(false);

          profileAccountTabButton.setBounds(298, 89);
          profileAccountTabButton.addEventListener(this);
          this.add(profileAccountTabButton);
          profileAccountTabButton.setVisible(false);

          profileAddonsTabButton.setBounds(418, 89);
          profileAddonsTabButton.addEventListener(this);
          this.add(profileAddonsTabButton);
          profileAddonsTabButton.setVisible(false);

          profileSettingsTabButton.setBounds(538, 89);
          profileSettingsTabButton.addEventListener(this);
          this.add(profileSettingsTabButton);
          profileSettingsTabButton.setVisible(false);

          //Profiles components - home
          profilePlayButton.setBounds(728, 436);
          profilePlayButton.addEventListener(this);
          this.add(profilePlayButton);
          profilePlayButton.setVisible(false);

          profileNewsButton.setBounds(229, 578);
          profileNewsButton.addEventListener(this);
          this.add(profileNewsButton);
          profileNewsButton.setVisible(false);

          profileServerInfosButton.setBounds(246, 528);
          profileServerInfosButton.addEventListener(this);
          this.add(profileServerInfosButton);
          profileServerInfosButton.setVisible(false);

          profileLaunchToMenuButton.setBounds(495, 541);
          profileLaunchToMenuButton.addEventListener(this);
          this.add(profileLaunchToMenuButton);
          profileLaunchToMenuButton.setVisible(false);

          profileDownloadButton.setBounds(764, 553);
          profileDownloadButton.addEventListener(this);
          this.add(profileDownloadButton);
          profileDownloadButton.setVisible(false);

          profileAccountLabel.setBounds(386, 468, 276, 31);
          profileAccountLabel.setForeground(Color.WHITE);
          profileAccountLabel.setFont(titleLabel.getFont().deriveFont(17f));
          this.add(profileAccountLabel);
          profileAccountLabel.setVisible(false);

          profileAccountConnectedLabel.setBounds(192, 472, 191, 19);
          profileAccountConnectedLabel.setForeground(new Color(179, 179, 179));
          profileAccountConnectedLabel.setFont(titleLabel.getFont().deriveFont(17f));
          this.add(profileAccountConnectedLabel);
          profileAccountConnectedLabel.setVisible(false);

          profileTextLogo.setBounds(420, 138, 338, 83);
          this.add(profileTextLogo);
          profileTextLogo.setVisible(false);

          profileDiapoPanel.setLayout(null);
          this.add(profileDiapoPanel);
          profileDiapoPanel.setBounds(178, 113, 822, 343);
          profileDiapoPanel.setVisible(false);
          profileDiapoImage1.setBounds(0, 0, 822, 343);
          profileDiapoPanel.add(profileDiapoImage1);
          profileDiapoImage2.setBounds(0, 0, 822, 343);
          profileDiapoPanel.add(profileDiapoImage2);

          // Profiles components - compte
          profileAccountConnectionButton.setBounds(301, 359);
          profileAccountConnectionButton.addEventListener(this);
          this.add(profileAccountConnectionButton);
          profileAccountConnectionButton.setVisible(false);

          profileAccountConnectionMicrosoftButton.setBounds(690, 233);
          profileAccountConnectionMicrosoftButton.addEventListener(this);
          this.add(profileAccountConnectionMicrosoftButton);
          profileAccountConnectionMicrosoftButton.setVisible(false);

          profileAccountResetButton.setBounds(853, 500);
          profileAccountResetButton.addEventListener(this);
          this.add(profileAccountResetButton);
          profileAccountResetButton.setVisible(false);

          profileAccountTextField.setForeground(Color.WHITE);
          profileAccountTextField.setFont(titleLabel.getFont().deriveFont(25f));
          profileAccountTextField.setCaretColor(Color.RED);
          profileAccountTextField.setOpaque(false);
          profileAccountTextField.setBorder(null);
          profileAccountTextField.setBounds(222, 168, 386, 60);
          this.add(profileAccountTextField);
          profileAccountTextField.setVisible(false);

          profileAccountPasswordField.setForeground(Color.WHITE);
          profileAccountPasswordField.setFont(profileAccountTextField.getFont());
          profileAccountPasswordField.setCaretColor(Color.RED);
          profileAccountPasswordField.setOpaque(false);
          profileAccountPasswordField.setBorder(null);
          profileAccountPasswordField.setBounds(222, 262, 386, 60);
          this.add(profileAccountPasswordField);
          profileAccountPasswordField.setVisible(false);

          // Profile components - addons
          profileAddonsShadersButton.setBounds(566, 128);
          profileAddonsShadersButton.addEventListener(this);
          this.add(profileAddonsShadersButton);
          profileAddonsShadersButton.setVisible(false);

          profileAddonsResourcePacksButton.setBounds(735, 128);
          profileAddonsResourcePacksButton.addEventListener(this);
          this.add(profileAddonsResourcePacksButton);
          profileAddonsResourcePacksButton.setVisible(false);

          profileAddonsModsButton.setBounds(566, 128);
          profileAddonsModsButton.addEventListener(this);
          this.add(profileAddonsModsButton);
          profileAddonsModsButton.setVisible(false);

          profileAddonsGoToFolderButton.setBounds(804, 561);
          profileAddonsGoToFolderButton.addEventListener(this);
          this.add(profileAddonsGoToFolderButton);
          profileAddonsGoToFolderButton.setVisible(false);

          profileAddonsOptifineSwitchButton.setBounds(402, 120);
          profileAddonsOptifineSwitchButton.addEventListener(this);
          this.add(profileAddonsOptifineSwitchButton);
          profileAddonsOptifineSwitchButton.setVisible(false);

          profileModsFpsmodelSwitchButton.setBounds(496, 200);
          profileModsFpsmodelSwitchButton.addEventListener(this);
          this.add(profileModsFpsmodelSwitchButton);
          profileModsFpsmodelSwitchButton.setVisible(false);

          profileModsFpsmodelMoreInfosButton.setBounds(397, 239);
          profileModsFpsmodelMoreInfosButton.addEventListener(this);
          this.add(profileModsFpsmodelMoreInfosButton);
          profileModsFpsmodelMoreInfosButton.setVisible(false);

          profileModsBettertpsSwitchButton.setBounds(892, 200);
          profileModsBettertpsSwitchButton.addEventListener(this);
          this.add(profileModsBettertpsSwitchButton);
          profileModsBettertpsSwitchButton.setVisible(false);

          profileModsBettertpsMoreInfosButton.setBounds(796, 239);
          profileModsBettertpsMoreInfosButton.addEventListener(this);
          this.add(profileModsBettertpsMoreInfosButton);
          profileModsBettertpsMoreInfosButton.setVisible(false);

          profileModsFallingleavesSwitchButton.setBounds(496, 260);
          profileModsFallingleavesSwitchButton.addEventListener(this);
          this.add(profileModsFallingleavesSwitchButton);
          profileModsFallingleavesSwitchButton.setVisible(false);

          profileModsFallingleavesMoreInfosButton.setBounds(397, 299);
          profileModsFallingleavesMoreInfosButton.addEventListener(this);
          this.add(profileModsFallingleavesMoreInfosButton);
          profileModsFallingleavesMoreInfosButton.setVisible(false);

          profileModsAppleskinSwitchButton.setBounds(892, 260);
          profileModsAppleskinSwitchButton.addEventListener(this);
          this.add(profileModsAppleskinSwitchButton);
          profileModsAppleskinSwitchButton.setVisible(false);

          profileModsAppleskinMoreInfosButton.setBounds(796, 299);
          profileModsAppleskinMoreInfosButton.addEventListener(this);
          this.add(profileModsAppleskinMoreInfosButton);
          profileModsAppleskinMoreInfosButton.setVisible(false);

          profileModsSoundphysicsSwitchButton.setBounds(496, 320);
          profileModsSoundphysicsSwitchButton.addEventListener(this);
          this.add(profileModsSoundphysicsSwitchButton);
          profileModsSoundphysicsSwitchButton.setVisible(false);

          profileModsSoundphysicsMoreInfosButton.setBounds(397, 359);
          profileModsSoundphysicsMoreInfosButton.addEventListener(this);
          this.add(profileModsSoundphysicsMoreInfosButton);
          profileModsSoundphysicsMoreInfosButton.setVisible(false);


          profileShadersSeeComparisonButton.setBounds(630, 582);
          profileShadersSeeComparisonButton.addEventListener(this);
          this.add(profileShadersSeeComparisonButton);
          profileShadersSeeComparisonButton.setVisible(false);

          profileShadersChocapicV6PlusButton.setBounds(490, 210);
          profileShadersChocapicV6PlusButton.addEventListener(this);
          this.add(profileShadersChocapicV6PlusButton);
          profileShadersChocapicV6PlusButton.setVisible(false);

          profileShadersChocapicV7_1PlusButton.setBounds(892, 210);
          profileShadersChocapicV7_1PlusButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1PlusButton);
          profileShadersChocapicV7_1PlusButton.setVisible(false);

          profileShadersChocapicV9PlusButton.setBounds(490, 269);
          profileShadersChocapicV9PlusButton.addEventListener(this);
          this.add(profileShadersChocapicV9PlusButton);
          profileShadersChocapicV9PlusButton.setVisible(false);

          profileShadersSeusRenewedPanel.setBounds(595, 255);
          this.add(profileShadersSeusRenewedPanel);
          profileShadersSeusRenewedPanel.setVisible(false);


          profileShadersChocapicV6LitePanel.setBounds(185, 195);
          this.add(profileShadersChocapicV6LitePanel);
          profileShadersChocapicV6LitePanel.setVisible(false);

          profileShadersChocapicV6LowPanel.setBounds(595, 195);
          this.add(profileShadersChocapicV6LowPanel);
          profileShadersChocapicV6LowPanel.setVisible(false);

          profileShadersChocapicV6MediumPanel.setBounds(185, 255);
          this.add(profileShadersChocapicV6MediumPanel);
          profileShadersChocapicV6MediumPanel.setVisible(false);

          profileShadersChocapicV6UltraPanel.setBounds(595, 255);
          this.add(profileShadersChocapicV6UltraPanel);
          profileShadersChocapicV6UltraPanel.setVisible(false);

          profileShadersChocapicV6ExtremePanel.setBounds(185, 315);
          this.add(profileShadersChocapicV6ExtremePanel);
          profileShadersChocapicV6ExtremePanel.setVisible(false);


          profileShadersChocapicV7_1ToasterPanel.setBounds(185, 195);
          this.add(profileShadersChocapicV7_1ToasterPanel);
          profileShadersChocapicV7_1ToasterPanel.setVisible(false);

          profileShadersChocapicV7_1LitePanel.setBounds(595, 195);
          this.add(profileShadersChocapicV7_1LitePanel);
          profileShadersChocapicV7_1LitePanel.setVisible(false);

          profileShadersChocapicV7_1LowPanel.setBounds(185, 255);
          this.add(profileShadersChocapicV7_1LowPanel);
          profileShadersChocapicV7_1LowPanel.setVisible(false);

          profileShadersChocapicV7_1MediumPanel.setBounds(595, 255);
          this.add(profileShadersChocapicV7_1MediumPanel);
          profileShadersChocapicV7_1MediumPanel.setVisible(false);

          profileShadersChocapicV7_1UltraPanel.setBounds(185, 315);
          this.add(profileShadersChocapicV7_1UltraPanel);
          profileShadersChocapicV7_1UltraPanel.setVisible(false);

          profileShadersChocapicV7_1ExtremePanel.setBounds(595, 315);
          this.add(profileShadersChocapicV7_1ExtremePanel);
          profileShadersChocapicV7_1ExtremePanel.setVisible(false);


          profileShadersChocapicV9LowPanel.setBounds(185, 195);
          this.add(profileShadersChocapicV9LowPanel);
          profileShadersChocapicV9LowPanel.setVisible(false);

          profileShadersChocapicV9MediumPanel.setBounds(595, 195);
          this.add(profileShadersChocapicV9MediumPanel);
          profileShadersChocapicV9MediumPanel.setVisible(false);

          profileShadersChocapicV9HighPanel.setBounds(185, 255);
          this.add(profileShadersChocapicV9HighPanel);
          profileShadersChocapicV9HighPanel.setVisible(false);

          profileShadersChocapicV9ExtremePanel.setBounds(595, 255);
          this.add(profileShadersChocapicV9ExtremePanel);
          profileShadersChocapicV9ExtremePanel.setVisible(false);

          profileShadersChocapicV9_1ExtremePanel.setBounds(185, 315);
          this.add(profileShadersChocapicV9_1ExtremePanel);
          profileShadersChocapicV9_1ExtremePanel.setVisible(false);

          // Profile components - reglages
          profileSettingsProfileNameTextField.setForeground(Color.WHITE);
          profileSettingsProfileNameTextField.setFont(titleLabel.getFont().deriveFont(25f));
          profileSettingsProfileNameTextField.setCaretColor(Color.RED);
          profileSettingsProfileNameTextField.setOpaque(false);
          profileSettingsProfileNameTextField.setBorder(null);
          profileSettingsProfileNameTextField.setBounds(496, 138, 310, 63);
          this.add(profileSettingsProfileNameTextField);
          profileSettingsProfileNameTextField.setVisible(false);

          profileSettingsHelmIconSwitchButton.setBounds(491, 230);
          profileSettingsHelmIconSwitchButton.addEventListener(this);
          this.add(profileSettingsHelmIconSwitchButton);
          profileSettingsHelmIconSwitchButton.setVisible(false);

          profileSettingsAllowedRamSpinner.setUI(new CustomSpinnerUI());
          profileSettingsAllowedRamSpinner.setForeground(Color.WHITE);
          profileSettingsAllowedRamSpinner.setFont(titleLabel.getFont().deriveFont(25f));
          profileSettingsAllowedRamSpinner.setOpaque(false);
          profileSettingsAllowedRamSpinner.setBorder(null);
          profileSettingsAllowedRamSpinner.setBounds(491, 306, 93, 58);
          profileSettingsAllowedRamSpinner.setOpaque(false);
          profileSettingsAllowedRamSpinner.getEditor().setOpaque(false);
          ((JSpinner.NumberEditor)profileSettingsAllowedRamSpinner.getEditor()).getTextField().setOpaque(false);
          ((JSpinner.NumberEditor)profileSettingsAllowedRamSpinner.getEditor()).getTextField().setForeground(Color.WHITE);
          ((JSpinner.NumberEditor)profileSettingsAllowedRamSpinner.getEditor()).getTextField().setBorder(new EmptyBorder(5, 0, 0, 4));
          this.add(profileSettingsAllowedRamSpinner);
          profileSettingsAllowedRamSpinner.setVisible(false);

          profileSettingsMainProfileSwitchButton.setBounds(491, 381);
          profileSettingsMainProfileSwitchButton.addEventListener(this);
          this.add(profileSettingsMainProfileSwitchButton);
          profileSettingsMainProfileSwitchButton.setVisible(false);

          profileSettingsSaveSettings.setBounds(824, 543);
          profileSettingsSaveSettings.addEventListener(this);
          this.add(profileSettingsSaveSettings);
          profileSettingsSaveSettings.setVisible(false);

          // Changelogs components
          changelogsVersionComboBox.setBounds(189, 84, 150, 24);
          changelogsVersionComboBox.setFont(kollektifFont.deriveFont(14f));
          changelogsVersionComboBox.addActionListener(this);
          changelogsVersionComboBox.setForeground(Color.WHITE);

          changelogsVersionComboBox.setOpaque(false);
          changelogsVersionComboBox.setEditable(true);
          changelogsVersionComboBox.setRenderer(new CustomComboBoxRenderer());
          changelogsVersionComboBox.setEditor(new CustomComboBoxEditor());
          changelogsVersionComboBox.setUI(ColorArrowComboBoxUI.createUI(changelogsVersionComboBox));
          changelogsVersionComboBox.setBorder(null);
          this.add(changelogsVersionComboBox);
          changelogsVersionComboBox.setVisible(false);

          changelogsTextArea.setBounds(199, 133, 787, 484);
          changelogsTextArea.setForeground(Color.WHITE);
          changelogsTextArea.setFont(kollektifBoldFont.deriveFont(14f));
          changelogsTextArea.setEditable(false);
          changelogsTextArea.setOpaque(false);
          this.add(changelogsTextArea);
          changelogsTextArea.setVisible(false);

          // About components - up
          aboutInfosTabButton.setBounds(178, 89);
          aboutInfosTabButton.addEventListener(this);
          this.add(aboutInfosTabButton);
          aboutInfosTabButton.setVisible(false);

          aboutModsTabButton.setBounds(298, 89);
          aboutModsTabButton.addEventListener(this);
          this.add(aboutModsTabButton);
          aboutModsTabButton.setVisible(false);

          aboutTextLogo.setBounds(190, 123);
          aboutTextLogo.addEventListener(this);
          this.add(aboutTextLogo);
          aboutTextLogo.setVisible(false);

          aboutAstrauwolfLogo.setBounds(488, 234);
          aboutAstrauwolfLogo.addEventListener(this);
          this.add(aboutAstrauwolfLogo);
          aboutAstrauwolfLogo.setVisible(false);

          aboutCapitenzoLogo.setBounds(639, 235);
          aboutCapitenzoLogo.addEventListener(this);
          this.add(aboutCapitenzoLogo);
          aboutCapitenzoLogo.setVisible(false);

          aboutTimEtOLogo.setBounds(742, 235);
          aboutTimEtOLogo.addEventListener(this);
          this.add(aboutTimEtOLogo);
          aboutTimEtOLogo.setVisible(false);

          aboutGithubButton.setBounds(338, 462);
          aboutGithubButton.addEventListener(this);
          this.add(aboutGithubButton);
          aboutGithubButton.setVisible(false);

          aboutMailButton.setBounds(398, 465);
          aboutMailButton.addEventListener(this);
          this.add(aboutMailButton);
          aboutMailButton.setVisible(false);

          aboutDiscordButton.setBounds(457, 465);
          aboutDiscordButton.addEventListener(this);
          this.add(aboutDiscordButton);
          aboutDiscordButton.setVisible(false);

          aboutTwitterButton.setBounds(518, 466);
          aboutTwitterButton.addEventListener(this);
          this.add(aboutTwitterButton);
          aboutTwitterButton.setVisible(false);

          aboutEastereggsLabel.setBounds(286, 605, 91, 16);
          aboutEastereggsLabel.setForeground(new Color(151, 151, 151));
          aboutEastereggsLabel.setFont(kollektifBoldFont.deriveFont(16f));
          aboutEastereggsLabel.setOpaque(false);
          this.add(aboutEastereggsLabel);
          aboutEastereggsLabel.setVisible(false);

          setProfilePage(true, ProfileSaver.getActualMainProfile(), TAB_KEY.profileHome);

     }

     /**
      * Pour activer/désactiver les boutons de la page principale des profils
      * @param e {@code true} si activation, {@code false} si désactivation
      * @author <a href="https://github.com/TimEtOff">TimEtO</a>
      */
     public static void enablePlayButtons(boolean e) {

          profilePlayButton.setEnabled(e);

          profileLaunchToMenuButton.setEnabled(e);
          profileDownloadButton.setEnabled(e);
     }

     /**
      * Pour changer le statut du bouton de lancement
      * @param toPlayStatus en statut de lancement
      * @see Components#profilePlayButtonIsPlayStatus
      */
     public static void togglePlayButtonStatus(boolean toPlayStatus) {
          if (toPlayStatus) {
               profilePlayButton.setTexture(getResourceIgnorePath("/assets/launcher/profilesPage/playButton-normal.png"));
               profilePlayButton.setTextureHover(getResourceIgnorePath("/assets/launcher/profilesPage/playButton-hover.png"));
               profilePlayButton.setTextureDisabled(getResourceIgnorePath("/assets/launcher/profilesPage/playButton-disabled.png"));

               enablePlayButtons(true);

               profilePlayButtonIsPlayStatus = true;
               isUpdating = false;
          } else {
               profilePlayButton.setTexture(getResourceIgnorePath("/assets/launcher/profilesPage/stopButton-normal.png"));
               profilePlayButton.setTextureHover(getResourceIgnorePath("/assets/launcher/profilesPage/stopButton-hover.png"));
               profilePlayButton.setTextureDisabled(getResourceIgnorePath("/assets/launcher/profilesPage/stopButton-disabled.png"));

               enablePlayButtons(false);

               profilePlayButtonIsPlayStatus = false;
               isUpdating = true;
          }

     }

     /**
      * Background
      * @param g the <code>Graphics</code> object to protect
      */
     @Override
     public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

     }

     /**
      * Remet à zéro la barre de chargement et ses composants liés après une update
      */
     public static void updatePostExecutions() {
          loadingBar.setValue(0);
          loadingBar.setVisible(false);
          barLabel.setText("");
          percentLabel.setText("");
          infosLabel.setText("");
          togglePlayButtonStatus(true);
     }

     /**
      * Le thread de lancement, initialisé plus tard
      */
     public static Thread launchThread = new Thread();
     /**
      * Le thread d'update, initialisé plus tard
      */
     public static Thread updateThread = new Thread();

     /**
      * Fais une action quand un bouton est appuyé (doit avoir intégré un {@link fr.theshark34.swinger.event.SwingerEventListener})
      * @param e pour savoir quel bouton a été appuyé
      */
     @Override
     public void onEvent(SwingerEvent e) {
          OnButtonEvent.onButtonEvent(e);
     }

     /**
      * Vérifie la version sélectionnée des changelogs
      * @return le numéro dans la liste
      */
     public static int verifyVersionChangelog() {
          String val = Objects.requireNonNull(changelogsVersionComboBox.getSelectedItem()).toString();
          String[] T = changelogsVersionsArrayList;

          int i;
          for(i = 0; i<T.length;i++){
               val = val.replaceAll("\\[", "").replaceAll("]", "");
               changelogsVersionComboBox.setSelectedItem(val);
               if(val.contains(T[i]))
                    //retourner la position courante
                    return i;
          }
          return i-1;
     }

     @Override
     public void actionPerformed(ActionEvent e) {
          if (e.getSource() == changelogsVersionComboBox) {
               int i = verifyVersionChangelog();
               changelogsTextArea.setText(Changelogs.getChangelogsTextsList()[i]);

          }
     }
}
