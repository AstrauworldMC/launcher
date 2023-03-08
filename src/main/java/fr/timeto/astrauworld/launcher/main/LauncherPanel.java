package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.*;
import fr.timeto.astrauworld.launcher.pagesutilities.*;
import fr.timeto.astrauworld.launcher.panels.profile.ProfileAccountPage;
import fr.timeto.astrauworld.launcher.panels.profile.ProfileAddonsPage;
import fr.timeto.astrauworld.launcher.panels.profile.ProfileHomePage;
import fr.timeto.astrauworld.launcher.panels.profile.ProfileSettingsPage;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
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
public class LauncherPanel extends JPanel implements SwingerEventListener, ActionListener { // TODO faire une belle doc en utilisant la run launcher [javadoc] pour voir où y'a rien

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
               if (!Objects.equals(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()), "")) {
                    profileAccountLabel.setText(ProfileSaver.getSelectedSaver().get(ProfileSaver.KEY.INFOS_NAME.get()));
                    profileAccountConnectedLabel.setText("Connect\u00e9 en tant que: ");
                    profileHomePage.enablePlayButtons(true);
               } else {
                    profileAccountLabel.setText("");
                    profileAccountConnectedLabel.setText("Non connect\u00e9");
                    profileHomePage.enablePlayButtons(false);
               }
          } catch (NullPointerException ignored) {}

     }

     public static void verifyNoAccountBefore(String oldAccount, Saver saver) {
          if (oldAccount.replaceAll(" ", "").equals("")) {
               saver.set(KEY.SETTINGS_PROFILENAME.get(), saver.get(KEY.INFOS_NAME.get()));
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
                         profileHomePage.newsButton.setLocation(profileHomePage.newsButton.getX(), 462);
                    } else {
                         profileHomePage.newsButton.setLocation(profileHomePage.newsButton.getX(), 465);
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


          /**
           * Label du nom du compte connecté du profil
           */
          public static final JLabel profileAccountLabel = new JLabel("", SwingConstants.LEFT);
          public static final JLabel profileAccountConnectedLabel = new JLabel("Connecté en tant que: ");

          public static final ProfileHomePage profileHomePage = new ProfileHomePage();
          public static final ProfileAccountPage profileAccountPage = new ProfileAccountPage();

          public static final ProfileAddonsPage profileAddonsModsPage = new ProfileAddonsPage(PageName.PROFILE_ADDONS_MODS);
          public static final ProfileAddonsPage profileAddonsShadersPage = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS);
          public static final ProfileAddonsPage profileAddonsShadersChocapicv6Page = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV6);
          public static final ProfileAddonsPage profileAddonsShadersChocapicv7Page = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV7);
          public static final ProfileAddonsPage profileAddonsShadersChocapicv9Page = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV9);
          public static final ProfileSettingsPage profileSettingsPage = new ProfileSettingsPage();

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

     //     public static PageCreator testPageCreator = new PageCreator();
     }

     /**
      * Initialise le panel de la frame (boutons, textes, images...)
      * @author <a href="https://github.com/TimEtOff">TimEtO</a>
      */
     public LauncherPanel() {
          this.setLayout(null);

          initFonts();

     /*     testPageCreator.setBounds(178, 113);
          this.add(testPageCreator); */

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
          launcherVersionLabel.setSelectionColor(new Color(255, 20, 20, 200));
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

          // Profiles components
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

          profileAccountLabel.setBounds(386, 468, 276, 31);
          profileAccountLabel.setForeground(Color.WHITE);
          profileAccountLabel.setFont(titleLabel.getFont().deriveFont(17f));
          this.add(profileAccountLabel);

          profileAccountConnectedLabel.setBounds(192, 472, 191, 19);
          profileAccountConnectedLabel.setForeground(new Color(179, 179, 179));
          profileAccountConnectedLabel.setFont(titleLabel.getFont().deriveFont(17f));
          add(profileAccountConnectedLabel);

          profileHomePage.setBounds(178, 113);
          this.add(profileHomePage);
          profileHomePage.setVisible(false);

          profileAccountPage.setBounds(178, 113);
          this.add(profileAccountPage);
          profileAccountPage.setVisible(false);

          profileAddonsModsPage.setBounds(178, 113);
          this.add(profileAddonsModsPage);
          profileAddonsModsPage.setVisible(false);

          profileAddonsShadersPage.setBounds(178, 113);
          this.add(profileAddonsShadersPage);
          profileAddonsShadersPage.setVisible(false);

          profileAddonsShadersChocapicv6Page.setBounds(178, 113);
          this.add(profileAddonsShadersChocapicv6Page);
          profileAddonsShadersChocapicv6Page.setVisible(false);

          profileAddonsShadersChocapicv7Page.setBounds(178, 113);
          this.add(profileAddonsShadersChocapicv7Page);
          profileAddonsShadersChocapicv7Page.setVisible(false);

          profileAddonsShadersChocapicv9Page.setBounds(178, 113);
          this.add(profileAddonsShadersChocapicv9Page);
          profileAddonsShadersChocapicv9Page.setVisible(false);

          profileSettingsPage.setBounds(178, 113);
          this.add(profileSettingsPage);
          profileSettingsPage.setVisible(false);

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
          changelogsTextArea.setSelectionColor(new Color(255, 20, 20, 200));
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

          Launcher.println("Affichage...");
          setPage(true, PageName.PROFILE_HOME, ProfileSaver.getActualMainProfile());

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
