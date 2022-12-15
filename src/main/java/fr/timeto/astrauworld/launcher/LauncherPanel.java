package fr.timeto.astrauworld.launcher;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.theshark34.swinger.Swinger.getTransparentWhite;
import static java.lang.Float.parseFloat;
import static fr.timeto.astrauworld.launcher.ProfileSaver.*;
import static fr.timeto.timutilslib.PopUpMessages.*;
import static fr.timeto.timutilslib.CustomFonts.*;

@SuppressWarnings("unused")
public class LauncherPanel extends JPanel implements SwingerEventListener, ActionListener { // TODO faire une belle doc en utilisant la run launcher [javadoc] pour voir où y'a rien

     private Image background = getResourceIgnorePath("/baseGUI.png");

     private static LauncherPanel launcherPanel;

     String lineSeparator = System.getProperty("line.separator");

     private BufferedImage getProfileIcon(File file) {
          // This time, you can use an InputStream to load
          try {
               // Grab the InputStream for the image.
               InputStream in = new FileInputStream(file);

               // Then read it.
               return ImageIO.read(in);
          } catch (IOException e) {
               System.out.println("The image was not loaded.");
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
     public BufferedImage getProfileButton(String base, String state) {
          boolean profile = false;
          BufferedImage button = null;

          if(Objects.equals(base, "firstProfile")){
               if(Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "secondProfile")) {
               if(Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "thirdProfile")) {
               if(Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          }
          return button;
     }

     public void initProfileButtons() {
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

     // Common components
     private final STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/commonButtons/quitButton.png"), getResourceIgnorePath("/commonButtons/quitButtonHover.png"));
     private final STexturedButton hideButton = new STexturedButton(getResourceIgnorePath("/commonButtons/hideButton.png"), getResourceIgnorePath("/commonButtons/hideButtonHover.png"));

     private final STexturedButton newsButton = new STexturedButton(getResourceIgnorePath("/commonButtons/newsButton-normal.png"), getResourceIgnorePath("/commonButtons/newsButton-hover.png"), getResourceIgnorePath("/commonButtons/newsButton-selected.png"));
     private final STexturedButton firstProfileButton = new STexturedButton(getProfileButton("firstProfile", "normal"), getProfileButton("firstProfile", "hover"), getProfileButton("firstProfile", "selected"));
     private final STexturedButton secondProfileButton = new STexturedButton(getProfileButton("secondProfile", "normal"), getProfileButton("secondProfile", "hover"), getProfileButton("secondProfile", "selected"));
     private final STexturedButton thirdProfileButton = new STexturedButton(getProfileButton("thirdProfile", "normal"), getProfileButton("thirdProfile", "hover"), getProfileButton("thirdProfile", "selected"));
     private final JLabel firstProfileIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_FIRSTPROFILE_ICON))));
     private final JLabel secondProfileIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_SECONDPROFILE_ICON))));
     private final JLabel thirdProfileIcon = new JLabel(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_THIRDPROFILE_ICON))));
     private final JLabel firstProfileNameLabel = new JLabel(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME));
     private final JLabel secondProfileNameLabel = new JLabel(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME));
     private final JLabel thirdProfileNameLabel = new JLabel(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME));

     private final JTextArea launcherVersionLabel = new JTextArea("Version du launcher:" + lineSeparator + Launcher.version);

     private final STexturedButton changesButton = new STexturedButton(getResourceIgnorePath("/commonButtons/changesButton-normal.png"), getResourceIgnorePath("/commonButtons/changesButton-hover.png"), getResourceIgnorePath("/commonButtons/changesButton-selected.png"));
     private final STexturedButton aboutButton = new STexturedButton(getResourceIgnorePath("/commonButtons/aboutButton-normal.png"), getResourceIgnorePath("/commonButtons/aboutButton-hover.png"), getResourceIgnorePath("/commonButtons/aboutButton-selected.png"));
     public static final JLabel tabLabel = new JLabel("", SwingConstants.LEFT);
     private final JLabel tabSecondLabel = new JLabel("none", SwingConstants.LEFT);
     public static SColoredBar loadingBar = new SColoredBar(getTransparentWhite(25), Color.RED);
     public static JLabel barLabel = new JLabel("", SwingConstants.LEFT);
     public static JLabel percentLabel = new JLabel("", SwingConstants.RIGHT);
     public static JLabel infosLabel = new JLabel("", SwingConstants.CENTER);

     private final STexturedButton upLeftCorner = new STexturedButton(getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"));
     private final STexturedButton upRightCorner = new STexturedButton(getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"));
     private final STexturedButton downLeftCorner = new STexturedButton(getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"));
     private final STexturedButton downRightCorner = new STexturedButton(getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"), getResourceIgnorePath("/corner.png"));

     // Profiles components - up
     private final STexturedButton profilePlayTabButton = new STexturedButton(getResourceIgnorePath("/profilesPage/up/Jouer-normal.png"), getResourceIgnorePath("/profilesPage/up/Jouer-hover.png"), getResourceIgnorePath("/profilesPage/up/Jouer-selected.png"));
     private final STexturedButton profileAccountTabButton = new STexturedButton(getResourceIgnorePath("/profilesPage/up/Compte-normal.png"), getResourceIgnorePath("/profilesPage/up/Compte-hover.png"), getResourceIgnorePath("/profilesPage/up/Compte-selected.png"));
     private final STexturedButton profileModsTabButton = new STexturedButton(getResourceIgnorePath("/profilesPage/up/Mods-normal.png"), getResourceIgnorePath("/profilesPage/up/Mods-hover.png"), getResourceIgnorePath("/profilesPage/up/Mods-selected.png"));
     private final STexturedButton profileSettingsTabButton = new STexturedButton(getResourceIgnorePath("/profilesPage/up/Reglages-normal.png"), getResourceIgnorePath("/profilesPage/up/Reglages-hover.png"), getResourceIgnorePath("/profilesPage/up/Reglages-selected.png"));

     // Profiles components - home
     private static boolean profilePlayButtonIsPlayStatus = true;
     private static boolean isUpdating = false;
     private static final STexturedButton profilePlayButton = new STexturedButton(getResourceIgnorePath("/profilesPage/playButton-normal.png"), getResourceIgnorePath("/profilesPage/playButton-hover.png"), getResourceIgnorePath("/profilesPage/playButton-disabled.png"));
     private final STexturedButton profileNewsButton = new STexturedButton(getResourceIgnorePath("/profilesPage/newsButton-normal.png"), getResourceIgnorePath("/profilesPage/newsButton-hover.png"));
     private static final STexturedButton profileLaunchToMenuButton = new STexturedButton(getResourceIgnorePath("/profilesPage/launchToMenuButton-normal.png"), getResourceIgnorePath("/profilesPage/launchToMenuButton-hover.png"), getResourceIgnorePath("/profilesPage/launchToMenuButton-disabled.png"));
     private static final STexturedButton profileDownloadButton = new STexturedButton(getResourceIgnorePath("/profilesPage/downloadButton-normal.png"), getResourceIgnorePath("/profilesPage/downloadButton-hover.png"), getResourceIgnorePath("/profilesPage/downloadButton-disabled.png"));
     private final JLabel profileAccountLabel = new JLabel("", SwingConstants.LEFT);

     // Profiles components - compte
     private final STexturedButton profileAccountConnectionButton = new STexturedButton(getResourceIgnorePath("/profilesPage/compte/connectionButton-normal.png"), getResourceIgnorePath("/profilesPage/compte/connectionButton-hover.png"));
     private final STexturedButton profileAccountConnectionMicrosoftButton = new STexturedButton(getResourceIgnorePath("/profilesPage/compte/connectionWithMicrosoftButton-normal.png"), getResourceIgnorePath("/profilesPage/compte/connectionWithMicrosoftButton-hover.png"));
     private final STexturedButton profileAccountResetButton = new STexturedButton(getResourceIgnorePath("/profilesPage/compte/resetButton-normal.png"), getResourceIgnorePath("/profilesPage/compte/resetButton-hover.png"));
     public static final JTextField profileAccountTextField = new JTextField("");
     public static final JPasswordField profileAccountPasswordField = new JPasswordField();

     // Profiles components - mods
     public static STexturedButton profileModsShadersButton = new STexturedButton(getResourceIgnorePath("/profilesPage/mods/shadersButton-normal.png"), getResourceIgnorePath("/profilesPage/mods/shadersButton-hover.png"));
     public static STexturedButton profileModsResourcePacksButton = new STexturedButton(getResourceIgnorePath("/profilesPage/mods/resourcePacksButton-normal.png"), getResourceIgnorePath("/profilesPage/mods/resourcePacksButton-hover.png"));
     public static STexturedToggleButton profileModsOptifineToggleButton = new STexturedToggleButton(KEY.MOD_OPTIFINE, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"));
     public static final STexturedToggleButton profileModsFpsmodelToggleButton = new STexturedToggleButton(KEY.MOD_FPSMODEL, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"));
     public static final STexturedButton profileModsFpsmodelMoreInfosButton = new STexturedButton(getResourceIgnorePath("/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/profilesPage/mods/moreInfos-hover.png"));
     public static final STexturedToggleButton profileModsBettertpsToggleButton = new STexturedToggleButton(KEY.MOD_BETTERTPS, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"));
     public static final STexturedButton profileModsBettertpsMoreInfosButton = new STexturedButton(getResourceIgnorePath("/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/profilesPage/mods/moreInfos-hover.png"));
     public static final STexturedToggleButton profileModsFallingleavesToggleButton = new STexturedToggleButton(KEY.MOD_FALLINGLEAVES, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"));
     public static final STexturedButton profileModsFallingleavesMoreInfosButton = new STexturedButton(getResourceIgnorePath("/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/profilesPage/mods/moreInfos-hover.png"));
     public static final STexturedToggleButton profileModsAppleskinToggleButton = new STexturedToggleButton(KEY.MOD_APPLESKIN, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"));
     public static final STexturedButton profileModsAppleskinMoreInfosButton = new STexturedButton(getResourceIgnorePath("/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/profilesPage/mods/moreInfos-hover.png"));
     public static final STexturedToggleButton profileModsSoundphysicsToggleButton = new STexturedToggleButton(KEY.MOD_SOUNDPHYSICS, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"));
     public static final STexturedButton profileModsSoundphysicsMoreInfosButton = new STexturedButton(getResourceIgnorePath("/profilesPage/mods/moreInfos-normal.png"), getResourceIgnorePath("/profilesPage/mods/moreInfos-hover.png"));

     // Profiles components - reglages
     public static JTextField profileSettingsProfileNameTextField = new JTextField();
     private final STexturedToggleButton profileSettingsHelmIconToggleButton = new STexturedToggleButton(ProfileSaver.KEY.SETTINGS_HELMICON, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"));

     public static JSpinner profileSettingsAllowedRamSpinner = new JSpinner(new SpinnerNumberModel(2, 0.10, 256.00, 0.10));
     public static STexturedButton profileSettingsSaveSettings = new STexturedButton(getResourceIgnorePath("/profilesPage/reglages/saveProfileNameButton.png"), getResourceIgnorePath("/profilesPage/reglages/saveProfileNameButton-hover.png"));

     // Changelogs components
     private static final ArrayList<Changelogs> changelogsArrayList = Changelogs.returnChangelogsList();
     private static final ArrayList<String> changelogsVersionsArrayList = Changelogs.returnChangelogsVersionsList();
     public static JComboBox<Object> changelogsVersionComboBox = new JComboBox<>(changelogsVersionsArrayList.toArray());
     public static JTextArea changelogsTextArea = new JTextArea();

     // About components - up
     private final STexturedButton aboutInfosTabButton = new STexturedButton(getResourceIgnorePath("/aboutPage/up/infosTab-normal.png"), getResourceIgnorePath("/aboutPage/up/infosTab-hover.png"), getResourceIgnorePath("/aboutPage/up/infosTab-selected.png"));
     private final STexturedButton aboutModsTabButton = new STexturedButton(getResourceIgnorePath("/aboutPage/up/modsTab-normal.png"), getResourceIgnorePath("/aboutPage/up/modsTab-hover.png"), getResourceIgnorePath("/aboutPage/up/modsTab-selected.png"));

     // About components - infos
     private final STexturedButton aboutTextLogo = new STexturedButton(getResourceIgnorePath("/aboutPage/logo-texte.png"), getResourceIgnorePath("/aboutPage/logo-texte.png"));
     private final STexturedButton aboutAstrauwolfLogo = new STexturedButton(getResourceIgnorePath("/aboutPage/aboutLogoAstrau.png"), getResourceIgnorePath("/aboutPage/aboutLogoAstrau.png"));
     private final STexturedButton aboutCapitenzoLogo = new STexturedButton(getResourceIgnorePath("/aboutPage/capitenzoPfp.png"), getResourceIgnorePath("/aboutPage/capitenzoPfp.png"));
     private final STexturedButton aboutTimEtOLogo = new STexturedButton(getResourceIgnorePath("/aboutPage/aboutLogoTim.png"), getResourceIgnorePath("/aboutPage/aboutLogoTim.png"));
     private final STexturedButton aboutGithubButton = new STexturedButton(getResourceIgnorePath("/aboutPage/github-normal.png"), getResourceIgnorePath("/aboutPage/github-hover.png"));
     private final STexturedButton aboutMailButton = new STexturedButton(getResourceIgnorePath("/aboutPage/mail-normal.png"), getResourceIgnorePath("/aboutPage/mail-hover.png"));
     private final STexturedButton aboutDiscordButton = new STexturedButton(getResourceIgnorePath("/aboutPage/discord-normal.png"), getResourceIgnorePath("/aboutPage/discord-hover.png"));
     private final STexturedButton aboutTwitterButton = new STexturedButton(getResourceIgnorePath("/aboutPage/twitter-normal.png"), getResourceIgnorePath("/aboutPage/twitter-hover.png"));
     public static JLabel aboutEastereggsLabel = new JLabel("", SwingConstants.LEFT);

     /**
      * Initialise le panel de la frame (boutons, textes, images...)
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

          tabLabel.setBounds(190, 60, 809, 23);
          tabLabel.setForeground(Color.WHITE);
          tabLabel.setFont(kollektifBoldFont.deriveFont(20f));
          this.add(tabLabel);

          tabSecondLabel.setBounds(190, 37, 809, 23);
          tabSecondLabel.setForeground(Color.WHITE);
          tabSecondLabel.setFont(tabLabel.getFont().deriveFont(16f));
          this.add(tabSecondLabel);

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


          upLeftCorner.setBounds(0, 0);
          this.add(upLeftCorner);
          upLeftCorner.setEnabled(false);

          upRightCorner.setBounds(1000, 0);
          this.add(upRightCorner);
          upRightCorner.setEnabled(false);

          downLeftCorner.setBounds(0, 630);
          this.add(downLeftCorner);
          downLeftCorner.setEnabled(false);

          downRightCorner.setBounds(1000, 630);
          this.add(downRightCorner);
          downRightCorner.setEnabled(false);

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
          profileAccountLabel.setFont(tabLabel.getFont().deriveFont(17f));
          this.add(profileAccountLabel);
          profileAccountLabel.setVisible(false);

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
          profileAccountTextField.setFont(tabLabel.getFont().deriveFont(25f));
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

          // Profile components - mods
          profileModsShadersButton.setBounds(566, 128);
          profileModsShadersButton.addEventListener(this);
          this.add(profileModsShadersButton);
          profileModsShadersButton.setVisible(false);

          profileModsResourcePacksButton.setBounds(735, 128);
          profileModsResourcePacksButton.addEventListener(this);
          this.add(profileModsResourcePacksButton);
          profileModsResourcePacksButton.setVisible(false);

          profileModsOptifineToggleButton.setBounds(402, 120);
          profileModsOptifineToggleButton.addEventListener(this);
          this.add(profileModsOptifineToggleButton);
          profileModsOptifineToggleButton.setVisible(false);

          profileModsFpsmodelToggleButton.setBounds(496, 200);
          profileModsFpsmodelToggleButton.addEventListener(this);
          this.add(profileModsFpsmodelToggleButton);
          profileModsFpsmodelToggleButton.setVisible(false);

          profileModsFpsmodelMoreInfosButton.setBounds(397, 239);
          profileModsFpsmodelMoreInfosButton.addEventListener(this);
          this.add(profileModsFpsmodelMoreInfosButton);
          profileModsFpsmodelMoreInfosButton.setVisible(false);

          profileModsBettertpsToggleButton.setBounds(892, 200);
          profileModsBettertpsToggleButton.addEventListener(this);
          this.add(profileModsBettertpsToggleButton);
          profileModsBettertpsToggleButton.setVisible(false);

          profileModsBettertpsMoreInfosButton.setBounds(796, 239);
          profileModsBettertpsMoreInfosButton.addEventListener(this);
          this.add(profileModsBettertpsMoreInfosButton);
          profileModsBettertpsMoreInfosButton.setVisible(false);

          profileModsFallingleavesToggleButton.setBounds(496, 260);
          profileModsFallingleavesToggleButton.addEventListener(this);
          this.add(profileModsFallingleavesToggleButton);
          profileModsFallingleavesToggleButton.setVisible(false);

          profileModsFallingleavesMoreInfosButton.setBounds(397, 299);
          profileModsFallingleavesMoreInfosButton.addEventListener(this);
          this.add(profileModsFallingleavesMoreInfosButton);
          profileModsFallingleavesMoreInfosButton.setVisible(false);

          profileModsAppleskinToggleButton.setBounds(892, 260);
          profileModsAppleskinToggleButton.addEventListener(this);
          this.add(profileModsAppleskinToggleButton);
          profileModsAppleskinToggleButton.setVisible(false);

          profileModsAppleskinMoreInfosButton.setBounds(796, 299);
          profileModsAppleskinMoreInfosButton.addEventListener(this);
          this.add(profileModsAppleskinMoreInfosButton);
          profileModsAppleskinMoreInfosButton.setVisible(false);

          profileModsSoundphysicsToggleButton.setBounds(496, 320);
          profileModsSoundphysicsToggleButton.addEventListener(this);
          this.add(profileModsSoundphysicsToggleButton);
          profileModsSoundphysicsToggleButton.setVisible(false);

          profileModsSoundphysicsMoreInfosButton.setBounds(397, 359);
          profileModsSoundphysicsMoreInfosButton.addEventListener(this);
          this.add(profileModsSoundphysicsMoreInfosButton);
          profileModsSoundphysicsMoreInfosButton.setVisible(false);

          // Profile components - reglages
          profileSettingsProfileNameTextField.setForeground(Color.WHITE);
          profileSettingsProfileNameTextField.setFont(tabLabel.getFont().deriveFont(25f));
          profileSettingsProfileNameTextField.setCaretColor(Color.RED);
          profileSettingsProfileNameTextField.setOpaque(false);
          profileSettingsProfileNameTextField.setBorder(null);
          profileSettingsProfileNameTextField.setBounds(496, 138, 310, 63);
          this.add(profileSettingsProfileNameTextField);
          profileSettingsProfileNameTextField.setVisible(false);

          profileSettingsHelmIconToggleButton.setBounds(491, 230);
          profileSettingsHelmIconToggleButton.addEventListener(this);
          this.add(profileSettingsHelmIconToggleButton);
          profileSettingsHelmIconToggleButton.setVisible(false);

          profileSettingsAllowedRamSpinner.setForeground(Color.WHITE);
          profileSettingsAllowedRamSpinner.setFont(tabLabel.getFont().deriveFont(25f));
          profileSettingsAllowedRamSpinner.setOpaque(false);
          profileSettingsAllowedRamSpinner.setBorder(null);
          profileSettingsAllowedRamSpinner.setBounds(491, 306, 93, 58);
          profileSettingsAllowedRamSpinner.setOpaque(false);
          profileSettingsAllowedRamSpinner.getEditor().setOpaque(false);
          ((JSpinner.NumberEditor)profileSettingsAllowedRamSpinner.getEditor()).getTextField().setOpaque(false);
          ((JSpinner.NumberEditor)profileSettingsAllowedRamSpinner.getEditor()).getTextField().setForeground(Color.WHITE);
          this.add(profileSettingsAllowedRamSpinner);
          profileSettingsAllowedRamSpinner.setVisible(false);

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

          setProfilePage(true, "1", "home");

     }

     public static void enablePlayButtons(boolean e, boolean allButtons) {

          if (allButtons) {
               profilePlayButton.setEnabled(e);
          } else {
               enablePlayButtons(!e, true);
          }
          profileLaunchToMenuButton.setEnabled(e);
          profileDownloadButton.setEnabled(e);
     }

     public static void togglePlayButtonStatus(boolean toPlayStatus) {
          if (toPlayStatus) {
               profilePlayButton.setTexture(getResourceIgnorePath("/profilesPage/playButton-normal.png"));
               profilePlayButton.setTextureHover(getResourceIgnorePath("/profilesPage/playButton-hover.png"));
               profilePlayButton.setTextureDisabled(getResourceIgnorePath("/profilesPage/playButton-disabled.png"));

               enablePlayButtons(true, true);

               profilePlayButtonIsPlayStatus = true;
               isUpdating = false;
          } else {
               profilePlayButton.setTexture(getResourceIgnorePath("/profilesPage/stopButton-normal.png"));
               profilePlayButton.setTextureHover(getResourceIgnorePath("/profilesPage/stopButton-hover.png"));
               profilePlayButton.setTextureDisabled(getResourceIgnorePath("/profilesPage/stopButton-disabled.png"));

               enablePlayButtons(false, false);

               profilePlayButtonIsPlayStatus = false;
               isUpdating = true;
          }

     }

     /**
      * Change la page pour la page principale des actualités
      * @param enabled Si {@code true}, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      */
     public void setNewsPage(boolean enabled) {
          if (enabled) {
               setProfilePage(false, null, "all");
               setChangesPage(false);
               setAboutPage(null, false);

               newsButton.setEnabled(false);

               upLeftCorner.setVisible(false);
               upRightCorner.setVisible(false);
               downLeftCorner.setVisible(false);
               downRightCorner.setVisible(false);

               tabLabel.setText("Actualit\u00e9s");
               tabSecondLabel.setText(" ");

               background = getResourceIgnorePath("/baseGUI.png");

               upLeftCorner.setVisible(true);
               upRightCorner.setVisible(true);
               downLeftCorner.setVisible(true);
               downRightCorner.setVisible(true);
          }else {
               newsButton.setEnabled(true);
          }

     }

     /**
      * Change la page pour la page d'un profil
      * @param enabled Si {@code true}, affiche la page et tous ces composants. {@code Si false}, fait disparaitre tous ces composants
      * @param profileNumber Le numéro du profil sélectionné
      * @param tab Quelle onglet est selectionnée. Si {@code null} -> changement de page, si "null" -> aucun changement de profil, si "all" -> fait toute les pages
      */
     @SuppressWarnings("all")
     public void setProfilePage(boolean enabled, String profileNumber, String tab) {
          STexturedButton profileSelected = null;
          STexturedButton profileNotSelected1 = firstProfileButton;
          STexturedButton profileNotSelected2 = secondProfileButton;
          Saver selectedSaver = null;

          if(tab == "home") {
               if (Objects.equals(profileNumber, "1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 1");
                    selectedSaver = firstProfileSaver;
                    selectedProfile = "1";
               } else if (Objects.equals(profileNumber, "2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 2");
                    selectedSaver = secondProfileSaver;
                    selectedProfile = "2";
               } else if (Objects.equals(profileNumber, "3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    tabLabel.setText("Profil 3");
                    selectedSaver = thirdProfileSaver;
                    selectedProfile = "3";
               } else if (Objects.equals(profileNumber, "null")) {
                    if(tabLabel.getText() == "Profil 1") {
                         profileSelected = firstProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=firstProfileSaver;
                    } else if (tabLabel.getText() == "Profil 2") {
                         profileSelected = secondProfileButton;
                         profileNotSelected1 = firstProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=secondProfileSaver;
                    } else if (tabLabel.getText() == "Profil 3") {
                         profileSelected = thirdProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = firstProfileButton;
                         selectedSaver=thirdProfileSaver;
                    }
               }

               if (enabled) {
                    setProfilePage(false, "null", "all");

                    setNewsPage(false);
                    setChangesPage(false);
                    setAboutPage(null, false);

                    if(tab == "all") {
                    } else {
                         profileSelected.setEnabled(false);
                         profileNotSelected1.setEnabled(true);
                         profileNotSelected2.setEnabled(true);
                    }

                    profilePlayTabButton.setEnabled(false);
                    profileAccountTabButton.setEnabled(true);
                    profileModsTabButton.setEnabled(true);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profilePlayButton.setVisible(true);
                    profileNewsButton.setVisible(true);
                    profileLaunchToMenuButton.setVisible(true);
                    profileDownloadButton.setVisible(true);

                    profileAccountLabel.setBounds(386, 468, 276, 31);
                    profileAccountLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                         if (Objects.equals(infosLabel.getText(), "")) {
                              profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                              enablePlayButtons(true, true);
                         } else {
                              enablePlayButtons(false, true);
                         }
                    } else {
                         profileAccountLabel.setText("");
                         enablePlayButtons(false, true);
                    }

          //          togglePlayButtonStatus(profilePlayButtonIsPlayStatus);

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    tabSecondLabel.setText("Jouer");
                    background = getResourceIgnorePath("/profilesPage/profilePage.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);

               } else {
                    profileNotSelected1.setEnabled(true);
                    profileNotSelected2.setEnabled(true);

                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profilePlayButton.setVisible(false);
                    profileNewsButton.setVisible(false);
                    profileLaunchToMenuButton.setVisible(false);
                    profileDownloadButton.setVisible(false);
                    profileAccountLabel.setVisible(false);
                    if (profileNumber == null) {
                         thirdProfileButton.setEnabled(true);
                    }
               }
          } else if (tab == "account") {
               if (Objects.equals(profileNumber, "1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 1");
                    selectedSaver = firstProfileSaver;
                    selectedProfile = "1";
               } else if (Objects.equals(profileNumber, "2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 2");
                    selectedSaver = secondProfileSaver;
                    selectedProfile = "2";
               } else if (Objects.equals(profileNumber, "3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    tabLabel.setText("Profil 3");
                    selectedSaver = thirdProfileSaver;
                    selectedProfile = "3";
               } else if (Objects.equals(profileNumber, "null")) {
                    if(tabLabel.getText() == "Profil 1") {
                         profileSelected = firstProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=firstProfileSaver;
                    } else if (tabLabel.getText() == "Profil 2") {
                         profileSelected = secondProfileButton;
                         profileNotSelected1 = firstProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=secondProfileSaver;
                    } else if (tabLabel.getText() == "Profil 3") {
                         profileSelected = thirdProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = firstProfileButton;
                         selectedSaver=thirdProfileSaver;
                    }
               }
               if (enabled) {
                    setProfilePage(false, "null", "all");

                    if(tab == "all") {
                    } else {
                         profileSelected.setEnabled(false);
                         profileNotSelected1.setEnabled(true);
                         profileNotSelected2.setEnabled(true);
                    }

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(false);
                    profileModsTabButton.setEnabled(true);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profileAccountConnectionButton.setVisible(true);
                    profileAccountConnectionMicrosoftButton.setVisible(true);
                    profileAccountResetButton.setVisible(true);
                    profileAccountTextField.setVisible(true);
                    profileAccountPasswordField.setVisible(true);
                    profileAccountTextField.setText("");
                    profileAccountPasswordField.setText("");
                    if (Objects.equals(selectedProfile, "1")) {
                         if(!Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL), "none")) {
                              profileAccountTextField.setText(firstProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL));
                         }
                    } else if (Objects.equals(selectedProfile, "2")) {
                         if(!Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL), "none")) {
                              profileAccountTextField.setText(secondProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL));
                         }
                    } else if (Objects.equals(selectedProfile, "3")) {
                         if(!Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL), "none")) {
                              profileAccountTextField.setText(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_EMAIL));
                         }
                    }

                    profileAccountLabel.setBounds(388, 524, 276, 31);
                    profileAccountLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                         profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                    } else {
                         profileAccountLabel.setText("");
                    }

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    tabSecondLabel.setText("Compte");
                    background = getResourceIgnorePath("/profilesPage/compte/profilePage-compte.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);

               } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);
                    profileAccountConnectionButton.setVisible(false);
                    profileAccountConnectionMicrosoftButton.setVisible(false);
                    profileAccountResetButton.setVisible(false);
                    profileAccountTextField.setVisible(false);
                    profileAccountPasswordField.setVisible(false);
                    profileAccountLabel.setVisible(false);
               }
               
          } else if (tab == "mods") {
               if (Objects.equals(profileNumber, "1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 1");
                    selectedSaver = firstProfileSaver;
                    selectedProfile = "1";
               } else if (Objects.equals(profileNumber, "2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 2");
                    selectedSaver = secondProfileSaver;
                    selectedProfile = "2";
               } else if (Objects.equals(profileNumber, "3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    tabLabel.setText("Profil 3");
                    selectedSaver = thirdProfileSaver;
                    selectedProfile = "3";
               } else if (Objects.equals(profileNumber, "null")) {
                    if(tabLabel.getText() == "Profil 1") {
                         profileSelected = firstProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=firstProfileSaver;
                    } else if (tabLabel.getText() == "Profil 2") {
                         profileSelected = secondProfileButton;
                         profileNotSelected1 = firstProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=secondProfileSaver;
                    } else if (tabLabel.getText() == "Profil 3") {
                         profileSelected = thirdProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = firstProfileButton;
                         selectedSaver=thirdProfileSaver;
                    }
               }
               if (enabled) {
                    setProfilePage(false, "null", "all");

                    if(tab == "all") {
                    } else {
                         profileSelected.setEnabled(false);
                         profileNotSelected1.setEnabled(true);
                         profileNotSelected2.setEnabled(true);
                    }

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileModsTabButton.setEnabled(false);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    profileModsShadersButton.setVisible(true);
                    profileModsResourcePacksButton.setVisible(true);
                    profileModsOptifineToggleButton.setVisible(true);
                    profileModsFpsmodelToggleButton.setVisible(true);
                    profileModsFpsmodelMoreInfosButton.setVisible(true);
                    profileModsBettertpsToggleButton.setVisible(true);
                    profileModsBettertpsMoreInfosButton.setVisible(true);
                    profileModsFallingleavesToggleButton.setVisible(true);
                    profileModsFallingleavesMoreInfosButton.setVisible(true);
                    profileModsAppleskinToggleButton.setVisible(true);
                    profileModsAppleskinMoreInfosButton.setVisible(true);
                    profileModsSoundphysicsToggleButton.setVisible(true);
                    profileModsSoundphysicsMoreInfosButton.setVisible(true);

                    profileAccountLabel.setBounds(388, 575, 276, 31);
                    profileAccountLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                         profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                    } else {
                         profileAccountLabel.setText("");
                    }

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    tabSecondLabel.setText("Mods");

                    background = getResourceIgnorePath("/profilesPage/mods/profilePage-mods.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);
               } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileModsShadersButton.setVisible(false);
                    profileModsResourcePacksButton.setVisible(false);
                    profileModsOptifineToggleButton.setVisible(false);
                    profileModsFpsmodelToggleButton.setVisible(false);
                    profileModsFpsmodelMoreInfosButton.setVisible(false);
                    profileModsBettertpsToggleButton.setVisible(false);
                    profileModsBettertpsMoreInfosButton.setVisible(false);
                    profileModsFallingleavesToggleButton.setVisible(false);
                    profileModsFallingleavesMoreInfosButton.setVisible(false);
                    profileModsAppleskinToggleButton.setVisible(false);
                    profileModsAppleskinMoreInfosButton.setVisible(false);
                    profileModsSoundphysicsToggleButton.setVisible(false);
                    profileModsSoundphysicsMoreInfosButton.setVisible(false);

                    profileAccountLabel.setVisible(false);
               }
               
          } else if (tab == "settings") {
               if (Objects.equals(profileNumber, "1")) {
                    profileSelected = firstProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 1");
                    selectedSaver = firstProfileSaver;
                    selectedProfile = "1";
               } else if (Objects.equals(profileNumber, "2")) {
                    profileSelected = secondProfileButton;
                    profileNotSelected2 = thirdProfileButton;
                    tabLabel.setText("Profil 2");
                    selectedSaver = secondProfileSaver;
                    selectedProfile = "2";
               } else if (Objects.equals(profileNumber, "3")) {
                    profileSelected = thirdProfileButton;
                    profileNotSelected1 = secondProfileButton;
                    profileNotSelected2 = firstProfileButton;
                    tabLabel.setText("Profil 3");
                    selectedSaver = thirdProfileSaver;
                    selectedProfile = "3";
               } else if (Objects.equals(profileNumber, "null")) {
                    if(tabLabel.getText() == "Profil 1") {
                         profileSelected = firstProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=firstProfileSaver;
                    } else if (tabLabel.getText() == "Profil 2") {
                         profileSelected = secondProfileButton;
                         profileNotSelected1 = firstProfileButton;
                         profileNotSelected2 = thirdProfileButton;
                         selectedSaver=secondProfileSaver;
                    } else if (tabLabel.getText() == "Profil 3") {
                         profileSelected = thirdProfileButton;
                         profileNotSelected1 = secondProfileButton;
                         profileNotSelected2 = firstProfileButton;
                         selectedSaver=thirdProfileSaver;
                    }
               }
               if (enabled) {
                    setProfilePage(false, "null", "all");

                    if(tab == "all") {
                    } else {
                         profileSelected.setEnabled(false);
                         profileNotSelected1.setEnabled(true);
                         profileNotSelected2.setEnabled(true);
                    }

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileModsTabButton.setEnabled(true);
                    profileSettingsTabButton.setEnabled(false);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    initSelectedSaver();
                    profileSettingsProfileNameTextField.setVisible(true);
                    profileSettingsProfileNameTextField.setText(ProfileSaver.selectedSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
                    profileSettingsHelmIconToggleButton.setVisible(true);
                    profileSettingsAllowedRamSpinner.setVisible(true);
                    profileSettingsAllowedRamSpinner.setValue(parseFloat(ProfileSaver.selectedSaver.get(ProfileSaver.KEY.SETTINGS_RAM)));
                    profileSettingsSaveSettings.setVisible(true);

                    profileAccountLabel.setBounds(388, 575, 276, 31);
                    profileAccountLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "no")){
                         profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                    } else {
                         profileAccountLabel.setText("");
                    }

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    tabSecondLabel.setText("R\u00e9glages");

                    background = getResourceIgnorePath("/profilesPage/reglages/profilePage-reglages.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);
               } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);

                    profileSettingsProfileNameTextField.setVisible(false);
                    profileSettingsHelmIconToggleButton.setVisible(false);
                    profileSettingsAllowedRamSpinner.setVisible(false);
                    profileSettingsSaveSettings.setVisible(false);

                    profileAccountLabel.setVisible(false);
               }
               
          } else if (tab == "all") {
               setProfilePage(enabled, null, "home");
               setProfilePage(enabled, null, "account");
               setProfilePage(enabled, null, "mods");
               setProfilePage(enabled, null, "settings");
          }

     }

     /**
      * Change la page pour la page principale des changelogs
      * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      */
     public void setChangesPage(boolean enabled) {
          if (enabled) {
               setAboutPage(null, false);
               setProfilePage(false, null, "all");
               setAboutPage(null, false);
               changesButton.setEnabled(false);

               changelogsVersionComboBox.setVisible(true);
               changelogsTextArea.setVisible(true);

               upLeftCorner.setVisible(false);
               upRightCorner.setVisible(false);
               downLeftCorner.setVisible(false);
               downRightCorner.setVisible(false);

               tabSecondLabel.setText(" ");
               tabLabel.setText("Changelogs");

               int i = verifyVersionChangelog();
               changelogsTextArea.setText(Changelogs.returnChangelogsTextsList().toArray()[i].toString());

               background = getResourceIgnorePath("/changelogsPage/changelogsPage.png");

               upLeftCorner.setVisible(true);
               upRightCorner.setVisible(true);
               downLeftCorner.setVisible(true);
               downRightCorner.setVisible(true);
          }else {
               changelogsVersionComboBox.setVisible(false);
               changelogsTextArea.setVisible(false);

               changesButton.setEnabled(true);
          }
     }

     /**
      * Change la page pour la page principale des contacts
      * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      */
     public void setAboutPage(String page, boolean enabled) {
          if (Objects.equals(page, "infos")) {
               if (enabled) {
                    setNewsPage(false);
                    setProfilePage(false, null, "all");
                    setChangesPage(false);
                    setAboutPage("mods", false);

                    aboutButton.setEnabled(false);
                    aboutInfosTabButton.setEnabled(false);

                    aboutInfosTabButton.setVisible(true);
                    aboutModsTabButton.setVisible(true);

                    aboutTextLogo.setVisible(true);
                    aboutAstrauwolfLogo.setVisible(true);
                    aboutCapitenzoLogo.setVisible(true);
                    aboutTimEtOLogo.setVisible(true);

                    aboutGithubButton.setVisible(true);
                    aboutMailButton.setVisible(true);
                    aboutDiscordButton.setVisible(true);
                    aboutTwitterButton.setVisible(true);

                    aboutEastereggsLabel.setText(EasterEggs.getNumberOfFoundEasterEggs() + "/" + EasterEggs.getNumberTotalEasterEggs());
                    aboutEastereggsLabel.setVisible(true);

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    tabSecondLabel.setText("Infos");
                    tabLabel.setText("\u00c0 propos");

                    background = getResourceIgnorePath("/aboutPage/aboutPage-infos.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);
               } else {
                    aboutInfosTabButton.setVisible(false);
                    aboutModsTabButton.setVisible(false);

                    aboutTextLogo.setVisible(false);
                    aboutAstrauwolfLogo.setVisible(false);
                    aboutCapitenzoLogo.setVisible(false);
                    aboutTimEtOLogo.setVisible(false);

                    aboutGithubButton.setVisible(false);
                    aboutMailButton.setVisible(false);
                    aboutDiscordButton.setVisible(false);
                    aboutTwitterButton.setVisible(false);

                    aboutEastereggsLabel.setVisible(false);

                    aboutButton.setEnabled(true);
                    aboutInfosTabButton.setEnabled(true);
               }
          } else if (Objects.equals(page, "mods")) {
               if (enabled) {
                    setNewsPage(false);
                    setProfilePage(false, null, "all");
                    setChangesPage(false);
                    setAboutPage("infos", false);

                    aboutButton.setEnabled(false);
                    aboutModsTabButton.setEnabled(false);

                    aboutInfosTabButton.setVisible(true);
                    aboutModsTabButton.setVisible(true);

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    tabSecondLabel.setText("Mods");
                    tabLabel.setText("\u00c0 propos");

                    background = getResourceIgnorePath("/baseGUI.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);

               } else {
                    aboutInfosTabButton.setVisible(false);
                    aboutModsTabButton.setVisible(false);

                    aboutButton.setEnabled(true);
                    aboutModsTabButton.setEnabled(true);
               }

          } else {
               setAboutPage("infos", false);
               setAboutPage("mods", false);
          }

     }

     @Override
     public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

     }

     private static void updatePostExecutions() {
          loadingBar.setValue(0);
          loadingBar.setVisible(false);
          barLabel.setText("");
          percentLabel.setText("");
          infosLabel.setText("");
          togglePlayButtonStatus(true);
     }

     private static Thread launchThread = new Thread();
     private static Thread updateThread = new Thread();

     private static void stopUpdate() {
          launchThread.interrupt();
          updateThread.interrupt();

          togglePlayButtonStatus(true);
          updatePostExecutions();
     }

     /**
      * Fais une action quand un bouton est appuyé (doit avoir intégré un {@link fr.theshark34.swinger.event.SwingerEventListener})
      * @param e pour savoir quel bouton a été appuyé
      */
     @Override
     public void onEvent(SwingerEvent e) {
          Object src = e.getSource();

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

          // Actions des boutons du haut des profilePage
          else if (src == profilePlayTabButton) {
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
                         enablePlayButtons(false, true);
          //               togglePlayButtonStatus(false);
                         loadingBar.setVisible(true);
                         infosLabel.setVisible(true);
                         infosLabel.setText("Connexion...");
                         try {
                              Launcher.connect();
                         } catch (MicrosoftAuthenticationException m) {
                              enablePlayButtons(true, true);
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
                              enablePlayButtons(true, true);
                              throw new RuntimeException(ex);
                         }
                         updatePostExecutions();

                         try {
                              Launcher.launch(true);
                         } catch (Exception ex) {
                              enablePlayButtons(true, true);
                              throw new RuntimeException(ex);
                         }
                         enablePlayButtons(true, true);

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
                    enablePlayButtons(false, true);
          //          togglePlayButtonStatus(false);

                    try {
                         Launcher.connect();
                    } catch (MicrosoftAuthenticationException m) {
                         enablePlayButtons(true, true);
                         errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                         infosLabel.setText("Connexion \u00e9chou\u00e9e");
                         loadingBar.setVisible(false);
                         infosLabel.setVisible(false);
                         return;
                    }

                    try {
                         Launcher.update();
                    } catch (Exception ex) {
                         enablePlayButtons(true, true);
                         throw new RuntimeException(ex);
                    }
                    updatePostExecutions();

                    try {
                         Launcher.launch(false);
                    } catch (Exception ex) {
                         enablePlayButtons(true, true);
                         throw new RuntimeException(ex);
                    }
                    enablePlayButtons(true, true);

               });
               launchThread.start();

          } else if (src == profileDownloadButton) {

               updateThread = new Thread(() -> {
                    enablePlayButtons(false, true);
          //          togglePlayButtonStatus(false);
                    try {
                         Launcher.update();
                    } catch (InterruptedException e1) {
                         enablePlayButtons(true, false);
                         updatePostExecutions();
                    } catch (Exception ex) {
                         enablePlayButtons(true, false);
                         throw new RuntimeException(ex);
                    }
                    enablePlayButtons(true, false);
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
                         System.out.println("Connexion...");
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
                         System.out.println("Connexion...");
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
               shaderspacksFolder.mkdir();
               try {
                    Desktop.getDesktop().open((shaderspacksFolder)); // TODO Temporaire jusqu'à pouvoir le faire depuis le launcher
               } catch (IOException ignored) {}
          } else if (src == profileModsResourcePacksButton) {
               try {
                    Desktop.getDesktop().open((resourcespacksFolder)); // TODO Temporaire jusqu'à pouvoir le faire depuis le launcher
               } catch (IOException ignored) {}
          } else if (src == profileModsOptifineToggleButton) {
               profileModsOptifineToggleButton.toggleButton();
          } else if (src == profileModsFpsmodelToggleButton) {
               profileModsFpsmodelToggleButton.toggleButton();
          } else if (src == profileModsFpsmodelMoreInfosButton) {
               openMoreInfosUrl(KEY.MOD_FPSMODEL);
          } else if (src == profileModsBettertpsToggleButton) {
               profileModsBettertpsToggleButton.toggleButton();
          } else if (src == profileModsBettertpsMoreInfosButton) {
               openMoreInfosUrl(KEY.MOD_BETTERTPS);
          } else if (src == profileModsFallingleavesToggleButton) {
               profileModsFallingleavesToggleButton.toggleButton();
          } else if (src == profileModsFallingleavesMoreInfosButton) {
               openMoreInfosUrl(KEY.MOD_FALLINGLEAVES);
          } else if (src == profileModsAppleskinToggleButton) {
               profileModsAppleskinToggleButton.toggleButton();
          } else if (src == profileModsAppleskinMoreInfosButton) {
               openMoreInfosUrl(KEY.MOD_APPLESKIN);
          } else if (src == profileModsSoundphysicsToggleButton) {
               profileModsSoundphysicsToggleButton.toggleButton();
          } else if (src == profileModsSoundphysicsMoreInfosButton) {
               openMoreInfosUrl(KEY.MOD_SOUNDPHYSICS);
          }

          // Actions des boutons de la profilePage - Reglages
          else if (src == profileSettingsHelmIconToggleButton) {
               profileSettingsHelmIconToggleButton.toggleButton();
               initProfileButtons();
          } else if (src == profileSettingsSaveSettings) {
               initSelectedSaver();
               selectedSaver.set(ProfileSaver.KEY.SETTINGS_RAM, profileSettingsAllowedRamSpinner.getValue().toString());
               selectedSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME, profileSettingsProfileNameTextField.getText());
               initProfileButtons();
               doneMessage("Enregistr\u00e9 !", "Param\u00e8tres enregistr\u00e9s");
          }

          // Actions des boutons de l'aboutPage - Up
          else if (src == aboutInfosTabButton) {
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
               EasterEggs.easterEggsSaver.set(EasterEggs.polishCow, "true");
          } else if (src == aboutCapitenzoLogo) {
               try {
                    Desktop.getDesktop().browse(new URL("https://youtu.be/vyPjz2QbFT4").toURI());
               } catch (IOException | URISyntaxException ignored) {}
               EasterEggs.easterEggsSaver.set(EasterEggs.frogWalking, "true");
          } else if (src == aboutTimEtOLogo) {
               try {
                    Desktop.getDesktop().browse(new URL("https://youtu.be/dQw4w9WgXcQ").toURI());
               } catch (IOException | URISyntaxException ignored) {}
               EasterEggs.easterEggsSaver.set(EasterEggs.rickroll, "true");
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

     private int verifyVersionChangelog() {
          int i = 0;
          while (Objects.requireNonNull(changelogsVersionComboBox.getSelectedItem()).toString() != changelogsVersionsArrayList.toArray()[i]) {
               i += 1;
          }
          return i;
     }

     @Override
     public void actionPerformed(ActionEvent e) {
          JComboBox cb = (JComboBox)e.getSource();
          String petName = (String)cb.getSelectedItem();

          if (e.getSource() == changelogsVersionComboBox) {
               int i = verifyVersionChangelog();
               changelogsTextArea.setText(Changelogs.returnChangelogsTextsList().toArray()[i].toString());

          }
     }
}
