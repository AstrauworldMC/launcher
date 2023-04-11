package fr.timeto.astrauworld.launcher.main;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.customelements.*;
import fr.timeto.astrauworld.launcher.pagesutilities.*;
import fr.timeto.astrauworld.launcher.panels.ChangelogsPage;
import fr.timeto.astrauworld.launcher.panels.NewsOpenPanel;
import fr.timeto.astrauworld.launcher.panels.NewsPanel;
import fr.timeto.astrauworld.launcher.panels.about.AboutInfosPage;
import fr.timeto.astrauworld.launcher.panels.about.AboutModsPage;
import fr.timeto.astrauworld.launcher.panels.profile.*;
import fr.timeto.astrauworld.launcher.secret.whitelistservers.WhitelistServers;
import fr.timeto.timutilslib.PopUpMessages;

import javax.swing.*;
import java.awt.*;
import java.io.*;

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
public class LauncherPanel extends JPanel implements SwingerEventListener { // TODO faire une belle doc en utilisant la run launcher [javadoc] pour voir où y'a rien

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
          public static SColoredBar loadingBar = new SColoredBar(getTransparentWhite(25), Launcher.getMainColor()){
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

          public static TabManager tabManager = new TabManager(178, 89);

          /**
           * Bouton invisible en bas à droite de la fenêtre pour régler le bug de l'arrière-plan qui ne se met pas à jour.
           * <p> Doit être mis visible puis invisible à chaque changement de page
           */
          public static final STexturedButton corner = new STexturedButton(getResourceIgnorePath("/assets/launcher/main/corner.png"), getResourceIgnorePath("/assets/launcher/main/corner.png"), getResourceIgnorePath("/assets/launcher/main/corner.png"));

          // News components
          public static final NewsPanel newsScrollPanel = new NewsPanel();
          public static final NewsOpenPanel newsOpenScrollPanel = new NewsOpenPanel();

          // Profiles components - up

          public static final ProfileHomePage profileHomePage = new ProfileHomePage();
          public static final ProfileWhitelistServers profileWhitelistServersPage = new ProfileWhitelistServers();
          public static final ProfileAccountPage profileAccountPage = new ProfileAccountPage();

          public static final ProfileAddonsPage profileAddonsModsPage = new ProfileAddonsPage(PageName.PROFILE_ADDONS_MODS);
          public static final ProfileAddonsPage profileAddonsShadersPage = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS);
          public static final ProfileAddonsPage profileAddonsShadersChocapicv6Page = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV6);
          public static final ProfileAddonsPage profileAddonsShadersChocapicv7Page = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV7);
          public static final ProfileAddonsPage profileAddonsShadersChocapicv9Page = new ProfileAddonsPage(PageName.PROFILE_ADDONS_SHADERS_CHOCAPICV9);
          public static final ProfileSettingsPage profileSettingsPage = new ProfileSettingsPage();

          public static final ChangelogsPage changelogsPage = new ChangelogsPage();

          // About components - up
          public static final AboutInfosPage aboutInfosPage = new AboutInfosPage();
          public static final AboutModsPage aboutModsPage = new AboutModsPage();
     }

     /**
      * Initialise le panel de la frame (boutons, textes, images...)
      * @author <a href="https://github.com/TimEtOff">TimEtO</a>
      */
     public LauncherPanel() throws Exception {
          this.setLayout(null);

          WhitelistServers.registerServers();

          // Common components
          tabManager.setLocation(178, 89);
          add(tabManager);

          TabList profileTabList = new TabList("profile");
          profileTabList.add(new Tab("Jouer", PageName.PROFILE_HOME));
          profileTabList.add(new Tab("Compte", PageName.PROFILE_ACCOUNT));
          profileTabList.add(new Tab("Addons", PageName.PROFILE_ADDONS_MODS));
          profileTabList.add(new Tab("Paramètres", PageName.PROFILE_SETTINGS));

          TabList aboutTabList = new TabList("about");
          aboutTabList.add(new Tab("Infos", PageName.ABOUT_INFOS));
          aboutTabList.add(new Tab("Mods", PageName.ABOUT_MODS) {
               @Override
               public void onEvent(SwingerEvent swingerEvent) {
                    aboutModsPage.setServer(Launcher.ASTRAUWORLD_MC);
                    super.onEvent(swingerEvent);
               }
          });

          tabManager.addTabList(profileTabList);
          tabManager.addTabList(aboutTabList);

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

          titleLabel.setBounds(190, 56, 809, 23);
          titleLabel.setForeground(Launcher.getTextColor());
          titleLabel.setFont(robotoBlackFont.deriveFont(20f));
          this.add(titleLabel);

          subTitleLabel.setBounds(190, 33, 809, 23);
          subTitleLabel.setForeground(Launcher.getTextColor());
          subTitleLabel.setFont(titleLabel.getFont().deriveFont(16f));
          this.add(subTitleLabel);

          barLabel.setBounds(181, 610, 269, 16);
          barLabel.setForeground(Launcher.getTextColor());
          barLabel.setFont(robotoMediumFont.deriveFont(10f));
          this.add(barLabel);

          percentLabel.setBounds(920, 611, 70, 16);
          percentLabel.setForeground(Launcher.getTextColor());
          percentLabel.setFont(barLabel.getFont());
          this.add(percentLabel);

          infosLabel.setBounds(460, 611, 255, 16);
          infosLabel.setForeground(Launcher.getTextColor());
          infosLabel.setFont(barLabel.getFont());
          this.add(infosLabel);

          loadingBar.setBounds(178, 611, 822, 20);
          this.add(loadingBar);
          loadingBar.setVisible(false);

          launcherVersionLabel.setBounds(9, 39, 150, 50);
          launcherVersionLabel.setForeground(new Color(100, 100, 100));
          launcherVersionLabel.setFont(robotoBlackFont.deriveFont(14f));
          launcherVersionLabel.setSelectionColor(Launcher.getMainColor());
          launcherVersionLabel.setOpaque(false);
          launcherVersionLabel.setEditable(false);
          this.add(launcherVersionLabel);

          corner.setBounds(this.getWidth(), this.getHeight());
          corner.addEventListener(this);
          this.add(corner);
          corner.setEnabled(false);

          JPanel panel = new JPanel();
          panel.setOpaque(false);
          panel.setLayout(null);
          panel.setBounds(178, 113, 822, 517);

          // News components
          newsScrollPanel.setBounds(0, 0);
          panel.add(newsScrollPanel);
          newsScrollPanel.setVisible(false);

          newsOpenScrollPanel.setBounds(0, 0);
          panel.add(newsOpenScrollPanel);
          newsOpenScrollPanel.setVisible(false);

          // Profiles components
          profileHomePage.setBounds(0, 0);
          panel.add(profileHomePage);
          profileHomePage.setVisible(false);

          profileWhitelistServersPage.setBounds(0, 0);
          panel.add(profileWhitelistServersPage);
          profileWhitelistServersPage.setVisible(false);

          profileAccountPage.setBounds(0, 0);
          panel.add(profileAccountPage);
          profileAccountPage.setVisible(false);

          profileAddonsModsPage.setBounds(0, 0);
          panel.add(profileAddonsModsPage);
          profileAddonsModsPage.setVisible(false);

          profileAddonsShadersPage.setBounds(0, 0);
          panel.add(profileAddonsShadersPage);
          profileAddonsShadersPage.setVisible(false);

          profileAddonsShadersChocapicv6Page.setBounds(0, 0);
          panel.add(profileAddonsShadersChocapicv6Page);
          profileAddonsShadersChocapicv6Page.setVisible(false);

          profileAddonsShadersChocapicv7Page.setBounds(0, 0);
          panel.add(profileAddonsShadersChocapicv7Page);
          profileAddonsShadersChocapicv7Page.setVisible(false);

          profileAddonsShadersChocapicv9Page.setBounds(0, 0);
          panel.add(profileAddonsShadersChocapicv9Page);
          profileAddonsShadersChocapicv9Page.setVisible(false);

          profileSettingsPage.setBounds(0, 0);
          panel.add(profileSettingsPage);
          profileSettingsPage.setVisible(false);

          changelogsPage.setBounds(0, 0);
          panel.add(changelogsPage);
          changelogsPage.setVisible(false);

          // About components
          aboutInfosPage.setBounds(0, 0);
          panel.add(aboutInfosPage);
          aboutInfosPage.setVisible(false);

          aboutModsPage.setBounds(0, 0);
          panel.add(aboutModsPage);
          aboutModsPage.setVisible(false);

          this.add(panel);

          aboutModsPage.setServer(Launcher.ASTRAUWORLD_MC);

          Launcher.println("Affichage...");
          setPage(true, PageName.PROFILE_HOME, ProfileSaver.getActualMainProfile());
          try {
               Taskbar.getTaskbar().requestUserAttention(true, false);
          } catch (UnsupportedOperationException ignored) {}

     }

     public static void enablePlayButtons(boolean e) {
          if (Server.playButtonsCanBeEnabled) {
               profileHomePage.enablePlayButtons(e);
               profileWhitelistServersPage.enablePlayButtons(e);
          }
     }

     public void recolor() {
          loadingBar = new SColoredBar(getTransparentWhite(25), Launcher.getMainColor()){
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

          tabManager.recolor();
          leftMenuSelector.recolor();
          newsButton.recolor();
          changesButton.recolor();
          aboutButton.recolor();
          titleLabel.setForeground(Launcher.getTextColor());
          subTitleLabel.setForeground(Launcher.getTextColor());
          barLabel.setForeground(Launcher.getTextColor());
          percentLabel.setForeground(Launcher.getTextColor());
          infosLabel.setForeground(Launcher.getTextColor());
          launcherVersionLabel.setSelectionColor(Launcher.getMainColor());

          newsScrollPanel.recolor();
          newsOpenScrollPanel.recolor();

          profileHomePage.recolor();
          profileWhitelistServersPage.recolor();
          profileAccountPage.recolor();
          profileAddonsModsPage.recolor();
          profileAddonsShadersPage.recolor();
          profileAddonsShadersChocapicv6Page.recolor();
          profileAddonsShadersChocapicv7Page.recolor();
          profileAddonsShadersChocapicv9Page.recolor();
          profileSettingsPage.recolor();

          changelogsPage.recolor();

          aboutInfosPage.recolor();
          aboutModsPage.recolor();
     }

     /**
      * Background
      * @param g the <code>Graphics</code> object to protect
      */
     @Override
     public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

          Graphics2D g2d = (Graphics2D) g;

          g2d.setColor(Launcher.DARKER_BACKGROUND);
          g2d.fillRect(0, 33, 178, 597);
     //     g2d.fillRect(0, 0, 1000, 33);

          g2d .setColor(Launcher.MID_BACKGROUND);
          g2d.fillRect(178, 33, 822, 80);

          g2d.setColor(Launcher.BASE_BACKGROUND);
          g2d.fillRect(178, 113, 822, 517);

     }

     @Override
     public boolean isOptimizedDrawingEnabled() {
          return false;
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
}
