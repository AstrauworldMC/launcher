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
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.theshark34.swinger.Swinger.getTransparentWhite;

@SuppressWarnings("unused")
public class LauncherPanel extends JPanel implements SwingerEventListener { // TODO faire une belle doc en utilisant la run launcher [javadoc] pour voir où y'a rien

     private Image background = getResourceIgnorePath("/baseGUI.png");

     private static LauncherPanel launcherPanel;

     public static Saver firstProfileSaver = LauncherFrame.firstProfileSaver;
     public static Saver secondProfileSaver = LauncherFrame.secondProfileSaver;
     public static Saver thirdProfileSaver = LauncherFrame.thirdProfileSaver;

     String lineSeparator = System.getProperty("line.separator");

     public static void dlProfileIcon(String imageURL, int profile) throws IOException {
          String destinationFile = "";
          if(profile == 1){
               destinationFile = Launcher.firstProfileIcon;
          } else if(profile == 2){
               destinationFile = Launcher.secondProfileIcon;
          }else if(profile == 3){
               destinationFile = Launcher.thirdProfileIcon;
          }
          URL url = new URL(imageURL);
          InputStream is = url.openStream();
          OutputStream os = new FileOutputStream(destinationFile);

          byte[] b = new byte[2048];
          int length;

          while ((length = is.read(b)) != -1) {
               os.write(b, 0, length);
          }

          is.close();
          os.close();
     }

     public static void initProfileIcon(Saver saver) throws IOException {
          String url;
          if (saver.get(ProfileSaver.KEY.SETTINGS_HELMICON).contains("true")) {
               url = "https://minotar.net/helm/" + saver.get(ProfileSaver.KEY.INFOS_UUID) + "/34.png";
          } else {
               url = "https://minotar.net/avatar/" + saver.get(ProfileSaver.KEY.INFOS_UUID) + "/34.png";
          }

          if (saver == firstProfileSaver) {
               dlProfileIcon(url, 1);
          } else if (saver == secondProfileSaver) {
               dlProfileIcon(url, 2);
          } else if (saver == thirdProfileSaver) {
               dlProfileIcon(url, 3);
          }
     }
     
     public static void initProfileIcon() throws IOException {
          initProfileIcon(firstProfileSaver);
          initProfileIcon(secondProfileSaver);
          initProfileIcon(thirdProfileSaver);
     }

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

     private static InputStream getFileFromResourceAsStream(String fileName) {

          // The class loader that loaded the class
          ClassLoader classLoader = LauncherPanel.class.getClassLoader();
          InputStream inputStream = classLoader.getResourceAsStream(fileName);

          // the stream holding the file content
          if (inputStream == null) {
               throw new IllegalArgumentException("file not found! " + fileName);
          } else {
               return inputStream;
          }

     }

     public static Font CustomFont(String path) {
          Font customFont = loadFont(path, 24f);
          GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
          ge.registerFont(customFont);
          return customFont;

     }
     public static Font loadFont(String path, float size){
          try {
               Font myFont = Font.createFont(Font.TRUETYPE_FONT, getFileFromResourceAsStream(path));
               return myFont.deriveFont(Font.PLAIN, size);
          } catch (FontFormatException | IOException e) {
               e.printStackTrace();
               System.exit(1);
          }
          return null;
     }

     private static Font kollektifFont;
     private static Font kollektifBoldFont;
     private static Font kollektifBoldItalicFont;
     private static Font kollektifItalicFont;
     private static Font minecraftiaFont;
     private static final String FONT_PATH_KOLLEKTIF = "fonts/Kollektif.ttf";
     private static final String FONT_PATH_KOLLEKTIFBOLD = "fonts/Kollektif-Bold.ttf";
     private static final String FONT_PATH_KOLLEKTIFBOLDITALIC = "fonts/Kollektif-BoldItalic.ttf";
     private static final String FONT_PATH_KOLLEKTIFITALIC = "fonts/Kollektif-Italic.ttf";
     private static final String FONT_PATH_MINECRAFTIA = "fonts/Minecraftia-Regular.ttf";

     public void initFonts() {
          kollektifFont = CustomFont(FONT_PATH_KOLLEKTIF);
          kollektifBoldFont = CustomFont(FONT_PATH_KOLLEKTIFBOLD);
          kollektifBoldItalicFont = CustomFont(FONT_PATH_KOLLEKTIFBOLDITALIC);
          kollektifItalicFont = CustomFont(FONT_PATH_KOLLEKTIFITALIC);
          minecraftiaFont = CustomFont(FONT_PATH_MINECRAFTIA);

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
               if(Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "secondProfile")) {
               if(Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "thirdProfile")) {
               if(Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none")) {
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
          firstProfileIcon.setVisible(!(Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none") || Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));
          firstProfileNameLabel.setText(firstProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
          firstProfileNameLabel.setVisible(!(Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none") || Objects.equals(firstProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));

          secondProfileButton.setTexture(getProfileButton("secondProfile", "normal"));
          secondProfileButton.setTextureHover(getProfileButton("secondProfile", "hover"));
          secondProfileButton.setTextureDisabled(getProfileButton("secondProfile", "selected"));
          secondProfileIcon.setIcon(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_SECONDPROFILE_ICON))));
          secondProfileIcon.setVisible(!(Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none") || Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));
          secondProfileNameLabel.setText(secondProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
          secondProfileNameLabel.setVisible(!(Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none") || Objects.equals(secondProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));

          thirdProfileButton.setTexture(getProfileButton("thirdProfile", "normal"));
          thirdProfileButton.setTextureHover(getProfileButton("thirdProfile", "hover"));
          thirdProfileButton.setTextureDisabled(getProfileButton("thirdProfile", "selected"));
          thirdProfileIcon.setIcon(new ImageIcon(Objects.requireNonNull(getProfileIcon(Launcher.AW_THIRDPROFILE_ICON))));
          thirdProfileIcon.setVisible(!(Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none") || Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));
          thirdProfileNameLabel.setText(thirdProfileSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
          thirdProfileNameLabel.setVisible(!(Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "none") || Objects.equals(thirdProfileSaver.get(ProfileSaver.KEY.INFOS_NAME), "")));

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
     public static String selectedProfile = "";
     public static Saver selectedSaver;

     public static void initSelectedProfile() {
          if (tabLabel.toString().contains("1")) {
               selectedProfile = "1";
          } else if (tabLabel.toString().contains("2")) {
               selectedProfile = "2";
          } else if (tabLabel.toString().contains("3")) {
               selectedProfile = "3";
          }
     }
     public static void initSelectedSaver() {
          if (Objects.equals(selectedProfile, "1")) {
               selectedSaver = firstProfileSaver;
          } else if (Objects.equals(selectedProfile, "2")) {
               selectedSaver = secondProfileSaver;
          } else if (Objects.equals(selectedProfile, "3")) {
               selectedSaver = thirdProfileSaver;
          } else {
               initSelectedProfile();
               initSelectedSaver();
          }
     }

     // Profiles components - home
     private final STexturedButton profilePlayButton = new STexturedButton(getResourceIgnorePath("/profilesPage/playButton-normal.png"), getResourceIgnorePath("/profilesPage/playButton-hover.png"), getResourceIgnorePath("/profilesPage/playButton-disabled.png"));
     private final STexturedButton profileNewsButton = new STexturedButton(getResourceIgnorePath("/profilesPage/newsButton-normal.png"), getResourceIgnorePath("/profilesPage/newsButton-hover.png"));
     private final STexturedButton profileLaunchToMenuButton = new STexturedButton(getResourceIgnorePath("/profilesPage/launchToMenuButton-normal.png"), getResourceIgnorePath("/profilesPage/launchToMenuButton-hover.png"), getResourceIgnorePath("/profilesPage/launchToMenuButton-disabled.png"));
     private final STexturedButton profileDownloadButton = new STexturedButton(getResourceIgnorePath("/profilesPage/downloadButton-normal.png"), getResourceIgnorePath("/profilesPage/downloadButton-hover.png"), getResourceIgnorePath("/profilesPage/downloadButton-disabled.png"));
     private final JLabel profileAccountLabel = new JLabel("", SwingConstants.LEFT);

     // Profiles components - compte
     private final STexturedButton profileAccountConnectionButton = new STexturedButton(getResourceIgnorePath("/profilesPage/compte/connectionButton-normal.png"), getResourceIgnorePath("/profilesPage/compte/connectionButton-hover.png"));
     private final STexturedButton profileAccountConnectionMicrosoftButton = new STexturedButton(getResourceIgnorePath("/profilesPage/compte/connectionWithMicrosoftButton-normal.png"), getResourceIgnorePath("/profilesPage/compte/connectionWithMicrosoftButton-hover.png"));
     private final STexturedButton profileAccountResetButton = new STexturedButton(getResourceIgnorePath("/profilesPage/compte/resetButton-normal.png"), getResourceIgnorePath("/profilesPage/compte/resetButton-hover.png"));
     public static final JTextField profileAccountTextField = new JTextField("");
     public static final JPasswordField profileAccountPasswordField = new JPasswordField();

     // Profiles components - reglages
     public static JTextField profileSettingsProfileNameTextField = new JTextField();
     public static STexturedButton profileSettingsSaveProfileNameButton = new STexturedButton(getResourceIgnorePath("/profilesPage/reglages/saveProfileNameButton.png"), getResourceIgnorePath("/profilesPage/reglages/saveProfileNameButton-hover.png"));
     private final STexturedToggleButton profileSettingsHelmIconToggleButton = new STexturedToggleButton(ProfileSaver.KEY.SETTINGS_HELMICON, getResourceIgnorePath("/commonButtons/toggleButton-normal_off.png"), getResourceIgnorePath("/commonButtons/toggleButton-hover_off.png"), getResourceIgnorePath("/commonButtons/toggleButton-disabled_off.png"));

     public void errorMessage(String title, String msg){
          JOptionPane.showMessageDialog(LauncherPanel.this, msg, title, JOptionPane.ERROR_MESSAGE);
          System.out.println(msg);
     }

     public void normalMessage(String title, String msg) {
          JOptionPane.showMessageDialog(LauncherPanel.this, msg, title, JOptionPane.INFORMATION_MESSAGE);
     }

     /**
      * Initialise le panel de la frame (boutons, textes, images...)
      */
     public LauncherPanel() {
          this.setLayout(null);

          // TODO Arriver à ajouter des fonts
         /* File file = new File("/fonts/verdana.ttf");
          try{

               verdanaRegular = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("..../resources/fonts/verdana.ttf"));;

               GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
               ge.registerFont(verdanaRegular);

          } catch(IOException | FontFormatException e) {
               System.out.println("Font creation failed");
               e.printStackTrace();

          }*/
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
          firstProfileNameLabel.setBounds(61, 188, 78, 12);
          firstProfileNameLabel.setForeground(Color.WHITE);
          firstProfileNameLabel.setFont(kollektifFont.deriveFont(14f));
          this.add(firstProfileNameLabel);
          firstProfileNameLabel.setVisible(false);
          firstProfileButton.setBounds(0, 174);
          firstProfileButton.addEventListener(this);
          this.add(firstProfileButton);

          secondProfileIcon.setBounds(15, 248, 35, 35);
          this.add(secondProfileIcon);
          secondProfileIcon.setVisible(false);
          secondProfileNameLabel.setBounds(61, 249, 78, 12);
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
          thirdProfileNameLabel.setBounds(61, 310, 78, 12);
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

          // Profile components - reglages
          profileSettingsProfileNameTextField.setForeground(Color.WHITE);
          profileSettingsProfileNameTextField.setFont(tabLabel.getFont().deriveFont(25f));
          profileSettingsProfileNameTextField.setCaretColor(Color.RED);
          profileSettingsProfileNameTextField.setOpaque(false);
          profileSettingsProfileNameTextField.setBorder(null);
          profileSettingsProfileNameTextField.setBounds(496, 138, 310, 63);
          this.add(profileSettingsProfileNameTextField);
          profileSettingsProfileNameTextField.setVisible(false);

          profileSettingsSaveProfileNameButton.setBounds(824, 138);
          profileSettingsSaveProfileNameButton.addEventListener(this);
          this.add(profileSettingsSaveProfileNameButton);
          profileSettingsSaveProfileNameButton.setVisible(false);

          profileSettingsHelmIconToggleButton.setBounds(491, 230);
          profileSettingsHelmIconToggleButton.addEventListener(this);
          this.add(profileSettingsHelmIconToggleButton);
          profileSettingsHelmIconToggleButton.setVisible(false);

          setProfilePage(true, "1", "home");

     }

     /**
      * Change la page pour la page principale des actualités
      * @param enabled Si {@code true}, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      */
     public void setNewsPage(boolean enabled) {
          if (enabled) {
               setProfilePage(false, null, "all");
               setChangesPage(false);
               setAboutPage(false);

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
                    setAboutPage(false);

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
                    profileAccountLabel.setVisible(true);
                    if (!Objects.equals(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME), "none")){
                         profileAccountLabel.setText(selectedSaver.get(ProfileSaver.KEY.INFOS_NAME));
                    } else {
                         profileAccountLabel.setText("");
                    }

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
               if (enabled) {
                    setProfilePage(false, "null", "all");

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
                    profileAccountConnectionMicrosoftButton.setEnabled(false);
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
                    background = getResourceIgnorePath("/baseGUI.png");
               }
               
          } else if (tab == "mods") {
               if (enabled) {
                    setProfilePage(false, "null", "all");

                    profilePlayTabButton.setEnabled(true);
                    profileAccountTabButton.setEnabled(true);
                    profileModsTabButton.setEnabled(false);
                    profileSettingsTabButton.setEnabled(true);

                    profilePlayTabButton.setVisible(true);
                    profileAccountTabButton.setVisible(true);
                    profileModsTabButton.setVisible(true);
                    profileSettingsTabButton.setVisible(true);

                    upLeftCorner.setVisible(false);
                    upRightCorner.setVisible(false);
                    downLeftCorner.setVisible(false);
                    downRightCorner.setVisible(false);

                    tabSecondLabel.setText("Mods");

                    background = getResourceIgnorePath("/baseGUI.png");

                    upLeftCorner.setVisible(true);
                    upRightCorner.setVisible(true);
                    downLeftCorner.setVisible(true);
                    downRightCorner.setVisible(true);
               } else {
                    profilePlayTabButton.setVisible(false);
                    profileAccountTabButton.setVisible(false);
                    profileModsTabButton.setVisible(false);
                    profileSettingsTabButton.setVisible(false);
               }
               
          } else if (tab == "settings") {
               if (enabled) {
                    setProfilePage(false, "null", "all");

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
                    profileSettingsProfileNameTextField.setText(this.selectedSaver.get(ProfileSaver.KEY.SETTINGS_PROFILENAME));
                    profileSettingsSaveProfileNameButton.setVisible(true);
                    profileSettingsHelmIconToggleButton.setVisible(true);

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
                    profileSettingsSaveProfileNameButton.setVisible(false);
                    profileSettingsHelmIconToggleButton.setVisible(false);
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
               setAboutPage(false);
               setProfilePage(false, null, "all");
               setAboutPage(false);

               tabLabel.setText("Changelogs");
               changesButton.setEnabled(false);

               upLeftCorner.setVisible(false);
               upRightCorner.setVisible(false);
               downLeftCorner.setVisible(false);
               downRightCorner.setVisible(false);

               tabSecondLabel.setText(" ");
               tabLabel.setText("Changelogs");

               background = getResourceIgnorePath("/baseGUI.png");

               upLeftCorner.setVisible(true);
               upRightCorner.setVisible(true);
               downLeftCorner.setVisible(true);
               downRightCorner.setVisible(true);
          }else {
               changesButton.setEnabled(true);
          }

     }

     /**
      * Change la page pour la page principale des contacts
      * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      */
     public void setAboutPage(boolean enabled) {
          if (enabled) {
               setNewsPage(false);
               setProfilePage(false, null, "all");
               setChangesPage(false);

               aboutButton.setEnabled(false);

               upLeftCorner.setVisible(false);
               upRightCorner.setVisible(false);
               downLeftCorner.setVisible(false);
               downRightCorner.setVisible(false);

               tabSecondLabel.setText(" ");
               tabLabel.setText("\u00c0 propos");

               background = getResourceIgnorePath("/baseGUI.png");

               upLeftCorner.setVisible(true);
               upRightCorner.setVisible(true);
               downLeftCorner.setVisible(true);
               downRightCorner.setVisible(true);
          }else {
               aboutButton.setEnabled(true);
          }

     }

     @Override
     public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);

     }

     private void updatePostExecutions() {
          loadingBar.setValue(0);
          loadingBar.setVisible(false);
          barLabel.setText("");
          percentLabel.setText("");
          infosLabel.setText("");
     }

     private void setButtonsEnabled(boolean enabled) {
          profilePlayButton.setEnabled(enabled);
          profileDownloadButton.setEnabled(enabled);
          profileLaunchToMenuButton.setEnabled(enabled);
     }

     /**
      * Fais une action quand un bouton est appuyé (doit avoir intégré un {@link fr.theshark34.swinger.event.SwingerEventListener})
      * @param e pour savoir quel bouton a été appuyé
      */
     @Override
     public void onEvent(SwingerEvent e) {

          // Actions des boutons de la barre d'infos de la fenêtre
          if(e.getSource() == quitButton){
               System.exit(0);
          } else if (e.getSource() == hideButton) {
               LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
          }

          // Actions des boutons du menu de gauche
          else if (e.getSource() == newsButton) {
               setNewsPage(true);
          } else if (e.getSource() == firstProfileButton) {
               setProfilePage(true, "1", "home");
          } else if (e.getSource() == secondProfileButton) {
               setProfilePage(true, "2", "home");
          } else if (e.getSource() == thirdProfileButton) {
               setProfilePage(true, "3", "home");
          } else if (e.getSource() == changesButton) {
               setChangesPage(true);
          } else if (e.getSource() == aboutButton) {
               setAboutPage(true);
          }

          // Actions des boutons du haut des profilePage
          else if (e.getSource() == profilePlayTabButton) {
               setProfilePage(true, "null", "home");
          } else if (e.getSource() == profileAccountTabButton) {
               setProfilePage(true, "null", "account");
          } else if (e.getSource() == profileModsTabButton) {
               setProfilePage(true, "null", "mods");
          } else if (e.getSource() == profileSettingsTabButton) {
               setProfilePage(true, "null", "settings");
          }

          // Actions des boutons de la profilePage - Home
          else if (e.getSource() == profilePlayButton) {

               Thread launch = new Thread(() -> {
                    setButtonsEnabled(false);
                    loadingBar.setVisible(true);
                    infosLabel.setVisible(true);
                    infosLabel.setText("Connexion...");
                    try {
                         Launcher.connect();
                    } catch (MicrosoftAuthenticationException m) {
                         setButtonsEnabled(true);
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
                         setButtonsEnabled(true);
                         throw new RuntimeException(ex);
                    }
                    updatePostExecutions();

                    try {
                         Launcher.launch();
                    } catch (Exception ex) {
                         setButtonsEnabled(true);
                         throw new RuntimeException(ex);
                    }
                    setButtonsEnabled(true);

               });
               launch.start();

          } else if (e.getSource() == profileNewsButton) {
               setNewsPage(true);
          } else if (e.getSource() == profileLaunchToMenuButton) {

               Thread launch = new Thread(() -> {
                    setButtonsEnabled(false);

                    try {
                         Launcher.connect();
                    } catch (MicrosoftAuthenticationException m) {
                         setButtonsEnabled(true);
                         errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                         infosLabel.setText("Connexion \u00e9chou\u00e9e");
                         loadingBar.setVisible(false);
                         infosLabel.setVisible(false);
                         return;
                    }

                    try {
                         Launcher.update();
                    } catch (Exception ex) {
                         setButtonsEnabled(true);
                         throw new RuntimeException(ex);
                    }
                    updatePostExecutions();

                    try {
                         Launcher.launch();
                    } catch (Exception ex) {
                         setButtonsEnabled(true);
                         throw new RuntimeException(ex);
                    }
                    setButtonsEnabled(true);

               });
               launch.start();

          } else if (e.getSource() == profileDownloadButton) {

               Thread update = new Thread(() -> {
                    setButtonsEnabled(false);
                    try {
                         Launcher.update();
                    } catch (Exception ex) {
                         setButtonsEnabled(true);
                         throw new RuntimeException(ex);
                    }
                    setButtonsEnabled(true);
                    updatePostExecutions();
                    normalMessage("T\u00e9l\u00e9chargement", "T\u00e9l\u00e9chargement termin\u00e9");
               });
               update.start();

          }

          // Actions des boutons de la profilePage - Account
          else if (e.getSource() == profileAccountConnectionButton) {
               if (profileAccountTextField.getText().replaceAll(" ", "").length() == 0 || profileAccountPasswordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un mail et un mot de passe valides", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
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
                         normalMessage("Connexion r\u00e9ussie", "Connexion r\u00e9ussie");
                         initProfileButtons();
               });
               connect.start();


          } else if (e.getSource() == profileAccountConnectionMicrosoftButton) {

               Thread connect = new Thread(() -> {
                    try {
                         System.out.println("Connexion...");
                         Launcher.microsoftAuthWebview();
                    } catch (MicrosoftAuthenticationException m) {
                         errorMessage("Erreur de connexion", "Erreur, impossible de se connecter");
                         return;
                    }
                         normalMessage("Connexion r\u00e9ussie", "Connexion r\u00e9ussie");
                         initProfileButtons();
               });
               connect.start();
          } else if (e.getSource() == profileAccountResetButton) {
               int reply = JOptionPane.showConfirmDialog(LauncherPanel.this, "Voulez vous vraiment r\u00e9initialiser le compte ?", "R\u00e9initialisation du compte", JOptionPane.YES_NO_OPTION);
               if (reply == JOptionPane.YES_OPTION) {
                    initSelectedSaver();
                    selectedSaver.set(ProfileSaver.KEY.FILECREATED, "");
                    LauncherFrame.initializeDataFiles(selectedSaver);
                    normalMessage("Compte supprim\u00e9", "Donn\u00e9es du compte r\u00e9initialis\u00e9es");
                    initProfileButtons();
               }
          }

          // Actions des boutons de la profilePage - Reglages
          else if (e.getSource() == profileSettingsSaveProfileNameButton) {
               initSelectedSaver();
               selectedSaver.set(ProfileSaver.KEY.SETTINGS_PROFILENAME, profileSettingsProfileNameTextField.getText());
               normalMessage("Enregistr\u00e9 !", "Nom du profil enregistr\u00e9");
               initProfileButtons();

          } else if (e.getSource() == profileSettingsHelmIconToggleButton) {
               profileSettingsHelmIconToggleButton.toggleButton();
               initProfileButtons();
          }
     }
}
