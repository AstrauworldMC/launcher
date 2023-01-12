package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.ShadersSwitchButton;
import fr.timeto.astrauworld.launcher.pagesutilities.*;
import fr.timeto.astrauworld.launcher.customelements.TexturedSwitchButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver.*;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;
import static fr.timeto.timutilslib.CustomFonts.*;

/**
 * La classe du panel du assets
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

     /**
      * Récupère une image de profil
      * @param file le chemin du fichier où est stockée l'image (../AppData/Roaming/Astrauworld Launcher/data)
      * @return L'image en {@code BufferedImage}
      */
     public static BufferedImage getProfileIcon(File file) {
          // This time, you can use an InputStream to load
          try {
               // Grab the InputStream for the image.
               InputStream in = new FileInputStream(file);

               // Then read it.
               return ImageIO.read(in);
          } catch (IOException e) {
               Launcher.println("The image was not loaded.");
          }

          return null;
     }

     /**
      * Récupère la version du bouton de profil voulu d'après le fichier de données
      * @param base réfère à 'profile1/2/3' contenu dans le nom de l'image
      * @param state la version de l'image demandée
      * @return Si le 'name' est égal à 'none' dans le fichier de données, l'image retournée est la version avec un bloc d'herbe Minecraft et la mention 'Vide' au-dessus de 'Profil [1/2/3]' de l'état demandé.
      * <p>
      * Sinon, retourne la version avec seulement 'Profil [1/2/3]' de l'état demandé
      */
     public static BufferedImage getProfileButton(String base, String state) {
          BufferedImage button = null;

          if(Objects.equals(base, "firstProfile")){
               if(Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                    button = getResourceIgnorePath("/assets/launcher/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/assets/launcher/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "secondProfile")) {
               if(Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                    button = getResourceIgnorePath("/assets/launcher/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/assets/launcher/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "thirdProfile")) {
               if(Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                    button = getResourceIgnorePath("/assets/launcher/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/assets/launcher/commonButtons/" + base + "Button-" + state + ".png");
               }

          }
          return button;
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
          firstProfileButton.setTexture(getProfileButton("firstProfile", "normal"));
          firstProfileButton.setTextureHover(getProfileButton("firstProfile", "hover"));
          firstProfileButton.setTextureDisabled(getProfileButton("firstProfile", "selected"));
          firstProfileIcon.setIcon(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_FIRSTPROFILE_ICON))));
          firstProfileIcon.setVisible(!(Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no") || Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));
          firstProfileNameLabel.setText(firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
          firstProfileNameLabel.setVisible(!(Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no") || Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));

          secondProfileButton.setTexture(getProfileButton("secondProfile", "normal"));
          secondProfileButton.setTextureHover(getProfileButton("secondProfile", "hover"));
          secondProfileButton.setTextureDisabled(getProfileButton("secondProfile", "selected"));
          secondProfileIcon.setIcon(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_SECONDPROFILE_ICON))));
          secondProfileIcon.setVisible(!(Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no") || Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));
          secondProfileNameLabel.setText(secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
          secondProfileNameLabel.setVisible(!(Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no") || Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));

          thirdProfileButton.setTexture(getProfileButton("thirdProfile", "normal"));
          thirdProfileButton.setTextureHover(getProfileButton("thirdProfile", "hover"));
          thirdProfileButton.setTextureDisabled(getProfileButton("thirdProfile", "selected"));
          thirdProfileIcon.setIcon(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_THIRDPROFILE_ICON))));
          thirdProfileIcon.setVisible(!(Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no") || Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));
          thirdProfileNameLabel.setText(thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
          thirdProfileNameLabel.setVisible(!(Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no") || Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));

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

          /**
           * Le bouton du menu général de gauche pour ouvrir la page principale des actualités
           */
          public static final STexturedButton newsButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/commonButtons/newsButton-normal.png"), getResourceIgnorePath("/assets/launcher/commonButtons/newsButton-hover.png"), getResourceIgnorePath("/assets/launcher/commonButtons/newsButton-selected.png"));
          /**
           * Le bouton du menu général de gauche pour ouvrir la page principale du premier profil
           * @see Components#firstProfileIcon
           * @see Components#firstProfileNameLabel
           */
          public static final STexturedButton firstProfileButton = new STexturedButton(getProfileButton("firstProfile", "normal"), getProfileButton("firstProfile", "hover"), getProfileButton("firstProfile", "selected"));
          /**
           * Le bouton du menu général de gauche pour ouvrir la page principale du second profil
           * @see Components#secondProfileIcon
           * @see Components#secondProfileNameLabel
           */
          public static final STexturedButton secondProfileButton = new STexturedButton(getProfileButton("secondProfile", "normal"), getProfileButton("secondProfile", "hover"), getProfileButton("secondProfile", "selected"));
          /**
           * Le bouton du menu général de gauche pour ouvrir la page principale du troisième profil
           * @see Components#thirdProfileIcon
           * @see Components#thirdProfileNameLabel
           */
          public static final STexturedButton thirdProfileButton = new STexturedButton(getProfileButton("thirdProfile", "normal"), getProfileButton("thirdProfile", "hover"), getProfileButton("thirdProfile", "selected"));
          /**
           * Icône du bouton du premier profil
           * @see Components#firstProfileButton
           * @see Components#firstProfileNameLabel
           */
          public static final JLabel firstProfileIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_FIRSTPROFILE_ICON))));
          /**
           * Icône du bouton du second profil
           * @see Components#secondProfileButton
           * @see Components#secondProfileNameLabel
           */
          public static final JLabel secondProfileIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_SECONDPROFILE_ICON))));
          /**
           * Icône du bouton du troisième profil
           * @see Components#thirdProfileButton
           * @see Components#thirdProfileNameLabel
           */
          public static final JLabel thirdProfileIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_THIRDPROFILE_ICON))));
          /**
           * Label contenant le nom du premier profil
           * @see Components#firstProfileButton
           * @see Components#firstProfileIcon
           */
          public static final JLabel firstProfileNameLabel = new JLabel(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME));
          /**
           * Label contenant le nom du second profil
           * @see Components#secondProfileButton
           * @see Components#secondProfileIcon
           */
          public static final JLabel secondProfileNameLabel = new JLabel(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME));
          /**
           * Label contenant le nom du troisième profil
           * @see Components#thirdProfileButton
           * @see Components#thirdProfileIcon
           */
          public static final JLabel thirdProfileNameLabel = new JLabel(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME));

          /**
           * Le texte contenant la version du assets dans le menu général de gauche
           */
          public static final JTextArea launcherVersionLabel = new JTextArea("Version du assets:" + lineSeparator + Launcher.version);

          /**
           * Bouton du menu général de gauche pour ouvrir la page des changelogs
           * @see Changelogs
           */
          public static final STexturedButton changesButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/commonButtons/changesButton-normal.png"), getResourceIgnorePath("/assets/launcher/commonButtons/changesButton-hover.png"), getResourceIgnorePath("/assets/launcher/commonButtons/changesButton-selected.png"));
          /**
           * Bouton du menu général de gauche pour ouvrir la page principale à propos
           */
          public static final STexturedButton aboutButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/commonButtons/aboutButton-normal.png"), getResourceIgnorePath("/assets/launcher/commonButtons/aboutButton-hover.png"), getResourceIgnorePath("/assets/launcher/commonButtons/aboutButton-selected.png"));
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
          public static SColoredBar loadingBar = new SColoredBar(getTransparentWhite(25), Color.RED);
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
           * Bouton d'onglet de la page des mods des pages de profil
           */
          public static final STexturedButton profileModsTabButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/up/Mods-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Mods-hover.png"), getResourceIgnorePath("/assets/launcher/profilesPage/up/Mods-selected.png"));
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
          public static STexturedButton profileAddonsShadersButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/shadersButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/shadersButton-hover.png"));
          /**
           * Bouton pour les resource packs
           */
          public static STexturedButton profileAddonsResourcePacksButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/resourcePacksButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/resourcePacksButton-hover.png"));
          public static STexturedButton profileAddonsModsButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/modsButton-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/modsButton-hover.png"));
          public static STexturedButton profileAddonsGoToFolderButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/goToFolder-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/goToFolder-hover.png"));
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
          public static final STexturedButton profileModsFpsmodelMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-hover.png"));
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
          public static final STexturedButton profileModsBettertpsMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-hover.png"));
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
          public static final STexturedButton profileModsFallingleavesMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-hover.png"));
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
          public static final STexturedButton profileModsAppleskinMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-hover.png"));
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
          public static final STexturedButton profileModsSoundphysicsMoreInfosButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/moreInfos-hover.png"));

          public static final STexturedButton profileShadersChocapicV6PlusButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/plus-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/plus-hover.png"));
          public static final STexturedButton profileShadersChocapicV7_1PlusButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/plus-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/plus-hover.png"));
          public static final STexturedButton profileShadersChocapicV9PlusButton =new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/plus-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/plus-hover.png"));
          public static final STexturedButton profileShadersSeusRenewedDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersSeusRenewedSwitchButton = new ShadersSwitchButton(shaderSeusRenewed, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));

          public static final ShadersSwitchButton profileShadersChocapicV6LiteSwitchButton = new ShadersSwitchButton(shaderChocapicV6Lite, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV6LiteDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV6LowSwitchButton = new ShadersSwitchButton(shaderChocapicV6Low, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV6LowDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV6MediumSwitchButton = new ShadersSwitchButton(shaderChocapicV6Medium, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV6MediumDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV6UltraSwitchButton = new ShadersSwitchButton(shaderChocapicV6Ultra, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV6UltraDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV6ExtremeSwitchButton = new ShadersSwitchButton(shaderChocapicV6Extreme, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV6ExtremeDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));

          public static final ShadersSwitchButton profileShadersChocapicV7_1ToasterSwitchButton = new ShadersSwitchButton(shaderChocapicV7_1Toaster, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV7_1ToasterDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV7_1LiteSwitchButton = new ShadersSwitchButton(shaderChocapicV7_1Lite, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV7_1LiteDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV7_1LowSwitchButton = new ShadersSwitchButton(shaderChocapicV7_1Low, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV7_1LowDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV7_1MediumSwitchButton = new ShadersSwitchButton(shaderChocapicV7_1Medium, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV7_1MediumDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV7_1UltraSwitchButton = new ShadersSwitchButton(shaderChocapicV7_1Ultra, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV7_1UltraDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV7_1ExtremeSwitchButton = new ShadersSwitchButton(shaderChocapicV7_1Extreme, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV7_1ExtremeDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));

          public static final ShadersSwitchButton profileShadersChocapicV9LowSwitchButton = new ShadersSwitchButton(shaderChocapicV9Low, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV9LowDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV9MediumSwitchButton = new ShadersSwitchButton(shaderChocapicV9Medium, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV9MediumDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV9HighSwitchButton = new ShadersSwitchButton(shaderChocapicV9High, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV9HighDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV9ExtremeSwitchButton = new ShadersSwitchButton(shaderChocapicV9Extreme, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV9ExtremeDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));
          public static final ShadersSwitchButton profileShadersChocapicV9_1ExtremeSwitchButton = new ShadersSwitchButton(shaderChocapicV9_1Extreme, getResourceIgnorePath("/assets/launcher/commonButtons/toggleButton-normal_off.png"));
          public static final STexturedButton profileShadersChocapicV9_1ExtremeDownloadButton = new STexturedButton(getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-normal.png"), getResourceIgnorePath("/assets/launcher/profilesPage/mods/download-hover.png"));


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
          public static JSpinner profileSettingsAllowedRamSpinner = new JSpinner(new SpinnerNumberModel(2, 0.10, 256.00, 0.10));
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
          public static final ArrayList<String> changelogsVersionsArrayList = Changelogs.getChangelogsVersionsList();
          /**
           * La combo-box pour sélectionner la version du changelog voulu
           * @since Beta2.1.2
           * @see Changelogs
           */
          public static JComboBox<Object> changelogsVersionComboBox = new JComboBox<>(changelogsVersionsArrayList.toArray());
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
           * Le bouton de l'onglet de la page à propos - mods
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
          quitButton.setBounds(962, 1);
          quitButton.addEventListener(this);
          this.add(quitButton);

          hideButton.setBounds(921, 1);
          hideButton.addEventListener(this);
          this.add(hideButton);

          newsButton.setBounds(0, 113);
          newsButton.addEventListener(this);
          this.add(newsButton);

          try {
               initProfileIcon();
          } catch (IOException e) {
               throw new RuntimeException(e);
          }

          firstProfileIcon.setBounds(15, 187, 35, 35);
          this.add(firstProfileIcon);
          firstProfileIcon.setVisible(false);
          firstProfileNameLabel.setBounds(61, 188, 80, 12);
          firstProfileNameLabel.setForeground(Color.WHITE);
          firstProfileNameLabel.setFont(kollektifBoldFont.deriveFont(13f));
          this.add(firstProfileNameLabel);
          firstProfileNameLabel.setVisible(false);
          firstProfileButton.setBounds(0, 174);
          firstProfileButton.addEventListener(this);
          this.add(firstProfileButton);

          secondProfileIcon.setBounds(15, 248, 35, 35);
          this.add(secondProfileIcon);
          secondProfileIcon.setVisible(false);
          secondProfileNameLabel.setBounds(61, 249, 80, 12);
          secondProfileNameLabel.setForeground(Color.WHITE);
          secondProfileNameLabel.setFont(firstProfileNameLabel.getFont());
          this.add(secondProfileNameLabel);
          secondProfileNameLabel.setVisible(false);
          secondProfileButton.setBounds(0, 235);
          secondProfileButton.addEventListener(this);
          this.add(secondProfileButton);

          thirdProfileIcon.setBounds(15, 309, 35, 35);
          this.add(thirdProfileIcon);
          thirdProfileIcon.setVisible(false);
          thirdProfileNameLabel.setBounds(61, 310, 80, 12);
          thirdProfileNameLabel.setForeground(Color.WHITE);
          thirdProfileNameLabel.setFont(firstProfileNameLabel.getFont());
          this.add(thirdProfileNameLabel);
          thirdProfileNameLabel.setVisible(false);
          thirdProfileButton.setBounds(0, 296);
          thirdProfileButton.addEventListener(this);
          this.add(thirdProfileButton);

          initProfileButtons();

          changesButton.setBounds(0, 510);
          changesButton.addEventListener(this);
          this.add(changesButton);

          aboutButton.setBounds(0, 571);
          aboutButton.addEventListener(this);
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

          launcherVersionLabel.setBounds(4, 39, 160, 50);
          launcherVersionLabel.setForeground(new Color(128, 128, 128));
          launcherVersionLabel.setFont(kollektifBoldFont.deriveFont(14f));
          launcherVersionLabel.setEditable(false);
          launcherVersionLabel.setOpaque(false);
          this.add(launcherVersionLabel);

          corner.setBounds(this.getWidth(), this.getHeight());
          corner.addEventListener(this);
          this.add(corner);
          corner.setEnabled(false);

          // Profiles components - up
          profilePlayTabButton.setBounds(178, 89);
          profilePlayTabButton.addEventListener(this);
          this.add(profilePlayTabButton);
          profilePlayTabButton.setVisible(false);

          profileAccountTabButton.setBounds(298, 89);
          profileAccountTabButton.addEventListener(this);
          this.add(profileAccountTabButton);
          profileAccountTabButton.setVisible(false);

          profileModsTabButton.setBounds(418, 89);
          profileModsTabButton.addEventListener(this);
          this.add(profileModsTabButton);
          profileModsTabButton.setVisible(false);

          profileSettingsTabButton.setBounds(538, 89);
          profileSettingsTabButton.addEventListener(this);
          this.add(profileSettingsTabButton);
          profileSettingsTabButton.setVisible(false);

          //Profiles components - home
          profilePlayButton.setBounds(728, 436);
          profilePlayButton.addEventListener(this);
          this.add(profilePlayButton);
          profilePlayButton.setVisible(false);

          profileNewsButton.setBounds(212, 552);
          profileNewsButton.addEventListener(this);
          this.add(profileNewsButton);
          profileNewsButton.setVisible(false);

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

          profileAddonsGoToFolderButton.setBounds(804, 554);
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

          profileShadersSeusRenewedDownloadButton.setBounds(808, 299);
          profileShadersSeusRenewedDownloadButton.addEventListener(this);
          this.add(profileShadersSeusRenewedDownloadButton);
          profileShadersSeusRenewedDownloadButton.setVisible(false);

          profileShadersSeusRenewedSwitchButton.setBounds(892, 260);
          profileShadersSeusRenewedSwitchButton.addEventListener(this);
          this.add(profileShadersSeusRenewedSwitchButton);
          profileShadersSeusRenewedSwitchButton.setVisible(false);


          profileShadersChocapicV6LiteSwitchButton.setBounds(496, 200);
          profileShadersChocapicV6LiteSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV6LiteSwitchButton);
          profileShadersChocapicV6LiteSwitchButton.setVisible(false);

          profileShadersChocapicV6LiteDownloadButton.setBounds(419, 239);
          profileShadersChocapicV6LiteDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV6LiteDownloadButton);
          profileShadersChocapicV6LiteDownloadButton.setVisible(false);

          profileShadersChocapicV6LowSwitchButton.setBounds(892, 200);
          profileShadersChocapicV6LowSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV6LowSwitchButton);
          profileShadersChocapicV6LowSwitchButton.setVisible(false);

          profileShadersChocapicV6LowDownloadButton.setBounds(808, 239);
          profileShadersChocapicV6LowDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV6LowDownloadButton);
          profileShadersChocapicV6LowDownloadButton.setVisible(false);

          profileShadersChocapicV6MediumSwitchButton.setBounds(496, 260);
          profileShadersChocapicV6MediumSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV6MediumSwitchButton);
          profileShadersChocapicV6MediumSwitchButton.setVisible(false);

          profileShadersChocapicV6MediumDownloadButton.setBounds(419, 299);
          profileShadersChocapicV6MediumDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV6MediumDownloadButton);
          profileShadersChocapicV6MediumDownloadButton.setVisible(false);

          profileShadersChocapicV6UltraSwitchButton.setBounds(892, 260);
          profileShadersChocapicV6UltraSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV6UltraSwitchButton);
          profileShadersChocapicV6UltraSwitchButton.setVisible(false);

          profileShadersChocapicV6UltraDownloadButton.setBounds(808, 299);
          profileShadersChocapicV6UltraDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV6UltraDownloadButton);
          profileShadersChocapicV6UltraDownloadButton.setVisible(false);

          profileShadersChocapicV6ExtremeSwitchButton.setBounds(496, 320);
          profileShadersChocapicV6ExtremeSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV6ExtremeSwitchButton);
          profileShadersChocapicV6ExtremeSwitchButton.setVisible(false);

          profileShadersChocapicV6ExtremeDownloadButton.setBounds(419, 359);
          profileShadersChocapicV6ExtremeDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV6ExtremeDownloadButton);
          profileShadersChocapicV6ExtremeDownloadButton.setVisible(false);


          profileShadersChocapicV7_1ToasterSwitchButton.setBounds(496, 200);
          profileShadersChocapicV7_1ToasterSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1ToasterSwitchButton);
          profileShadersChocapicV7_1ToasterSwitchButton.setVisible(false);

          profileShadersChocapicV7_1ToasterDownloadButton.setBounds(419, 239);
          profileShadersChocapicV7_1ToasterDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1ToasterDownloadButton);
          profileShadersChocapicV7_1ToasterDownloadButton.setVisible(false);

          profileShadersChocapicV7_1LiteSwitchButton.setBounds(892, 200);
          profileShadersChocapicV7_1LiteSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1LiteSwitchButton);
          profileShadersChocapicV7_1LiteSwitchButton.setVisible(false);

          profileShadersChocapicV7_1LiteDownloadButton.setBounds(808, 239);
          profileShadersChocapicV7_1LiteDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1LiteDownloadButton);
          profileShadersChocapicV7_1LiteDownloadButton.setVisible(false);

          profileShadersChocapicV7_1LowSwitchButton.setBounds(496, 260);
          profileShadersChocapicV7_1LowSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1LowSwitchButton);
          profileShadersChocapicV7_1LowSwitchButton.setVisible(false);

          profileShadersChocapicV7_1LowDownloadButton.setBounds(419, 299);
          profileShadersChocapicV7_1LowDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1LowDownloadButton);
          profileShadersChocapicV7_1LowDownloadButton.setVisible(false);

          profileShadersChocapicV7_1MediumSwitchButton.setBounds(892, 260);
          profileShadersChocapicV7_1MediumSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1MediumSwitchButton);
          profileShadersChocapicV7_1MediumSwitchButton.setVisible(false);

          profileShadersChocapicV7_1MediumDownloadButton.setBounds(808, 299);
          profileShadersChocapicV7_1MediumDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1MediumDownloadButton);
          profileShadersChocapicV7_1MediumDownloadButton.setVisible(false);

          profileShadersChocapicV7_1UltraSwitchButton.setBounds(496, 320);
          profileShadersChocapicV7_1UltraSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1UltraSwitchButton);
          profileShadersChocapicV7_1UltraSwitchButton.setVisible(false);

          profileShadersChocapicV7_1UltraDownloadButton.setBounds(419, 359);
          profileShadersChocapicV7_1UltraDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1UltraDownloadButton);
          profileShadersChocapicV7_1UltraDownloadButton.setVisible(false);

          profileShadersChocapicV7_1ExtremeSwitchButton.setBounds(892, 320);
          profileShadersChocapicV7_1ExtremeSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1ExtremeSwitchButton);
          profileShadersChocapicV7_1ExtremeSwitchButton.setVisible(false);

          profileShadersChocapicV7_1ExtremeDownloadButton.setBounds(808, 359);
          profileShadersChocapicV7_1ExtremeDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV7_1ExtremeDownloadButton);
          profileShadersChocapicV7_1ExtremeDownloadButton.setVisible(false);


          profileShadersChocapicV9LowSwitchButton.setBounds(496, 200);
          profileShadersChocapicV9LowSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV9LowSwitchButton);
          profileShadersChocapicV9LowSwitchButton.setVisible(false);

          profileShadersChocapicV9LowDownloadButton.setBounds(419, 239);
          profileShadersChocapicV9LowDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV9LowDownloadButton);
          profileShadersChocapicV9LowDownloadButton.setVisible(false);

          profileShadersChocapicV9MediumSwitchButton.setBounds(892, 200);
          profileShadersChocapicV9MediumSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV9MediumSwitchButton);
          profileShadersChocapicV9MediumSwitchButton.setVisible(false);

          profileShadersChocapicV9MediumDownloadButton.setBounds(808, 239);
          profileShadersChocapicV9MediumDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV9MediumDownloadButton);
          profileShadersChocapicV9MediumDownloadButton.setVisible(false);

          profileShadersChocapicV9HighSwitchButton.setBounds(496, 260);
          profileShadersChocapicV9HighSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV9HighSwitchButton);
          profileShadersChocapicV9HighSwitchButton.setVisible(false);

          profileShadersChocapicV9HighDownloadButton.setBounds(419, 299);
          profileShadersChocapicV9HighDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV9HighDownloadButton);
          profileShadersChocapicV9HighDownloadButton.setVisible(false);

          profileShadersChocapicV9ExtremeSwitchButton.setBounds(892, 260);
          profileShadersChocapicV9ExtremeSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV9ExtremeSwitchButton);
          profileShadersChocapicV9ExtremeSwitchButton.setVisible(false);

          profileShadersChocapicV9ExtremeDownloadButton.setBounds(808, 299);
          profileShadersChocapicV9ExtremeDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV9ExtremeDownloadButton);
          profileShadersChocapicV9ExtremeDownloadButton.setVisible(false);

          profileShadersChocapicV9_1ExtremeSwitchButton.setBounds(496, 320);
          profileShadersChocapicV9_1ExtremeSwitchButton.addEventListener(this);
          this.add(profileShadersChocapicV9_1ExtremeSwitchButton);
          profileShadersChocapicV9_1ExtremeSwitchButton.setVisible(false);

          profileShadersChocapicV9_1ExtremeDownloadButton.setBounds(419, 359);
          profileShadersChocapicV9_1ExtremeDownloadButton.addEventListener(this);
          this.add(profileShadersChocapicV9_1ExtremeDownloadButton);
          profileShadersChocapicV9_1ExtremeDownloadButton.setVisible(false);

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

          profileSettingsAllowedRamSpinner.setForeground(Color.WHITE);
          profileSettingsAllowedRamSpinner.setFont(titleLabel.getFont().deriveFont(25f));
          profileSettingsAllowedRamSpinner.setOpaque(false);
          profileSettingsAllowedRamSpinner.setBorder(null);
          profileSettingsAllowedRamSpinner.setBounds(491, 306, 93, 58);
          profileSettingsAllowedRamSpinner.setOpaque(false);
          profileSettingsAllowedRamSpinner.getEditor().setOpaque(false);
          ((JSpinner.NumberEditor)profileSettingsAllowedRamSpinner.getEditor()).getTextField().setOpaque(false);
          ((JSpinner.NumberEditor)profileSettingsAllowedRamSpinner.getEditor()).getTextField().setForeground(Color.WHITE);
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
          changelogsVersionComboBox.setBorder(null);
          changelogsVersionComboBox.setRenderer(new DefaultListCellRenderer(){
               @Override
               public Component getListCellRendererComponent(JList list, Object value,
                                                             int index, boolean isSelected, boolean cellHasFocus) {
                    JComponent result = (JComponent)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    list.setForeground(Color.BLACK);
                    result.setOpaque(false);
                    return result;
               }});
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

          setProfilePage(true, Objects.requireNonNullElseGet(LauncherFrame.profileAfterMcExit, ProfileSaver::getActualMainProfile), TAB_KEY.profileHome);

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
          int i = 0;
          while (Objects.requireNonNull(changelogsVersionComboBox.getSelectedItem()).toString() != changelogsVersionsArrayList.toArray()[i]) {
               i += 1;
          }
          return i;
     }

     @Override
     public void actionPerformed(ActionEvent e) {
          if (e.getSource() == changelogsVersionComboBox) {
               int i = verifyVersionChangelog();
               changelogsTextArea.setText(Changelogs.getChangelogsTextsList().toArray()[i].toString());

          }
     }
}
