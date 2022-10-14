package fr.timeto.astrauworld.launcher;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.*;

@SuppressWarnings("unused")
public class LauncherPanel extends JPanel implements SwingerEventListener {

     private final Image background = getResourceIgnorePath("/baseGUI.png");

     private static LauncherPanel launcherPanel;

     private LauncherScrollPanel launcherScrollPanel;

     public static Saver firstProfileSaver = LauncherFrame.firstProfileSaver;
     public static Saver secondProfileSaver = LauncherFrame.secondProfileSaver;
     public static Saver thirdProfileSaver = LauncherFrame.thirdProfileSaver;

     String lineSeparator = System.getProperty("line.separator");

     Font verdanaRegular;

     private static Font verdanaFont = null;
     private static final String FONT_PATH_VERDANA = "../resources/fonts/verdana.ttf";

     @SuppressWarnings("all")
     public Font createFont() {
          try {
               InputStream myStream = getClass().getClassLoader().getResourceAsStream("../resources/fonts/verdana.ttf");
               Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
               verdanaFont = ttfBase.deriveFont(Font.PLAIN, 24);
          } catch (Exception ex) {
               ex.printStackTrace();
               System.err.println("Font not loaded.");
          }
          return verdanaFont;
     }

     /**
      * Récupère la version du bouton de profil voulu d'après le fichier de données
      * @param base réfère à 'profile1/2/3' contenu dans le nom de l'image
      * @param state la version de l'image demandée
      * @return Si le 'name' est égal à 'none' dans le fichier de données, l'image retournée est la version avec un bloc d'herbe Minecraft et la mention 'Vide' au dessus de 'Profil [1/2/3]' de l'état demandé.
      * <p>
      * Sinon, retourne la version avec seulement 'Profil [1/2/3]' de l'état demandé
      */
     public BufferedImage getProfileButton(String base, String state) {
          boolean profile = false;
          BufferedImage button = null;

          if(Objects.equals(base, "profile1")){
               if(Objects.equals(firstProfileSaver.get("name"), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "profile2")) {
               if(Objects.equals(secondProfileSaver.get("name"), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          } else if (Objects.equals(base, "profile3")) {
               if(Objects.equals(thirdProfileSaver.get("name"), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + state + ".png");
               }

          }
          return button;
     }

     // Common components
     private final STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/commonButtons/quitButton.png"), getResourceIgnorePath("/commonButtons/quitButtonHover.png"));
     private final STexturedButton hideButton = new STexturedButton(getResourceIgnorePath("/commonButtons/hideButton.png"), getResourceIgnorePath("/commonButtons/hideButtonHover.png"));

     private final STexturedButton newsButton = new STexturedButton(getResourceIgnorePath("/commonButtons/newsButton-normal.png"), getResourceIgnorePath("/commonButtons/newsButton-hover.png"), getResourceIgnorePath("/commonButtons/newsButton-selected.png"));
     private final STexturedButton profile1Button = new STexturedButton(getProfileButton("profile1", "normal"), getProfileButton("profile1", "hover"), getProfileButton("profile1", "selected"));
     private final STexturedButton profile2Button = new STexturedButton(getProfileButton("profile2", "normal"), getProfileButton("profile2", "hover"), getProfileButton("profile2", "selected"));
     private final STexturedButton profile3Button = new STexturedButton(getProfileButton("profile3", "normal"), getProfileButton("profile3", "hover"), getProfileButton("profile3", "selected"));
     private final STexturedButton changesButton = new STexturedButton(getResourceIgnorePath("/commonButtons/changesButton-normal.png"), getResourceIgnorePath("/commonButtons/changesButton-hover.png"), getResourceIgnorePath("/commonButtons/changesButton-selected.png"));
     private final STexturedButton aboutButton = new STexturedButton(getResourceIgnorePath("/commonButtons/aboutButton-normal.png"), getResourceIgnorePath("/commonButtons/aboutButton-hover.png"), getResourceIgnorePath("/commonButtons/aboutButton-selected.png"));

     private final JLabel tabLabel = new JLabel("", SwingConstants.LEFT);

     /**
      * Initialise le panel de la frame (boutons, textes, images...)
      */
     public LauncherPanel() {
          this.setLayout(null);

         // this.add(launcherScrollPanel = new LauncherScrollPanel(), BorderLayout.CENTER);
         // this.add(LauncherScrollPanel.scrollPane);

          // TODO Arriver à ajouter des fonts
          File file = new File("/fonts/verdana.ttf");
         /* try{

               verdanaRegular = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream("..../resources/fonts/verdana.ttf"));;

               GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
               ge.registerFont(verdanaRegular);

          } catch(IOException | FontFormatException e) {
               System.out.println("Font creation failed");
               e.printStackTrace();

          }*/
         // createFont();

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

          profile1Button.setBounds(0, 174);
          profile1Button.addEventListener(this);
          this.add(profile1Button);

          profile2Button.setBounds(0, 235);
          profile2Button.addEventListener(this);
          this.add(profile2Button);

          profile3Button.setBounds(0, 296);
          profile3Button.addEventListener(this);
          this.add(profile3Button);

          changesButton.setBounds(0, 510);
          changesButton.addEventListener(this);
          this.add(changesButton);

          aboutButton.setBounds(0, 571);
          aboutButton.addEventListener(this);
          this.add(aboutButton);

          tabLabel.setBounds(190, 58, 809, 23);
          tabLabel.setForeground(Color.WHITE);
     //     tabLabel.setFont(verdanaRegular.deriveFont(20f));
          this.add(tabLabel);

          setNewsPage(true);

     }

     /**
      * Change la page pour la page principale des actualités
      * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      */
     public void setNewsPage(boolean enabled) {
          if (enabled) {
               setProfilePage(false, null);
               setChangesPage(false);
               setAboutPage(false);

               tabLabel.setText("Actualit\u00e9s");
               newsButton.setEnabled(false);
          }else {
               newsButton.setEnabled(true);
          }

     }

     /**
      * Change la page pour la page principale d'un profil
      * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      * @param profileNumber Le numéro du profil sélectionné
      */
     @SuppressWarnings("all")
     public void setProfilePage(boolean enabled, String profileNumber) {
          STexturedButton profileSelected = null;
          STexturedButton profileNotSelected1 = profile1Button;
          STexturedButton profileNotSelected2 = profile2Button;
          if (Objects.equals(profileNumber, "1")) {
               profileSelected = profile1Button;
               profileNotSelected1 = profile2Button;
               profileNotSelected2 = profile3Button;
               tabLabel.setText("Profil 1");
          } else if (Objects.equals(profileNumber, "2")) {
               profileSelected = profile2Button;
               profileNotSelected2 = profile3Button;
               tabLabel.setText("Profil 2");
          } else if (Objects.equals(profileNumber, "3")) {
               profileSelected = profile3Button;
               profileNotSelected1 = profile2Button;
               profileNotSelected2 = profile1Button;
               tabLabel.setText("Profil 3");
          }

          if (enabled) {
               setNewsPage(false);
               setChangesPage(false);
               setAboutPage(false);

               profileSelected.setEnabled(false);
               profileNotSelected1.setEnabled(true);
               profileNotSelected2.setEnabled(true);
          }else {
               profileNotSelected1.setEnabled(true);
               profileNotSelected2.setEnabled(true);
               if (profileNumber == null) {
                    profile3Button.setEnabled(true);
               }
          }

     }

     /**
      * Change la page pour la page principale des changelogs
      * @param enabled Si true, affiche la page et tous ces composants. Si false, fait disparaitre tous ces composants
      */
     public void setChangesPage(boolean enabled) {
          if (enabled) {
               setAboutPage(false);
               setProfilePage(false, null);
               setAboutPage(false);

               tabLabel.setText("Changelogs");
               changesButton.setEnabled(false);
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
               setProfilePage(false, null);
               setChangesPage(false);

               tabLabel.setText("\u00c0 propos");
               aboutButton.setEnabled(false);
          }else {
               aboutButton.setEnabled(true);
          }

     }


     public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
     }


     @Override
     public void onEvent(SwingerEvent e) {
          if(e.getSource() == quitButton){
               System.exit(0);
          } else if (e.getSource() == hideButton) {
               LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
          } else if (e.getSource() == newsButton) {
               setNewsPage(true);
          } else if (e.getSource() == profile1Button) {
               setProfilePage(true, "1");
          } else if (e.getSource() == profile2Button) {
               setProfilePage(true, "2");
          } else if (e.getSource() == profile3Button) {
               setProfilePage(true, "3");
          } else if (e.getSource() == changesButton) {
               setChangesPage(true);
          } else if (e.getSource() == aboutButton) {
               setAboutPage(true);
          }

     }
}
