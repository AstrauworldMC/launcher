package fr.timeto.astrauworld.launcher;

import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static fr.theshark34.swinger.Swinger.*;

public class LauncherPanel extends JPanel implements SwingerEventListener {

     private Image background = getResourceIgnorePath("/baseGUI.png");

     private static LauncherPanel launcherPanel;

     private LauncherScrollPanel launcherScrollPanel;

     public static Saver firstProfileSaver = new Saver(Launcher.awFirstProfileData);
     public static Saver secondProfileSaver = new Saver(Launcher.awSecondProfileData);
     private static Saver thirdProfileSaver = new Saver(Launcher.awThirdProfileData);

     String lineSeparator = System.getProperty("line.separator");

     public BufferedImage getProfileButton(String base, String version) {
          boolean profile = false;
          BufferedImage button = null;
          firstProfileSaver.load();
          secondProfileSaver.load();
          thirdProfileSaver.load();

          if(Objects.equals(base, "profile1")){
               if(Objects.equals(firstProfileSaver.get("name"), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + version + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + version + ".png");
               }

          } else if (Objects.equals(base, "profile2")) {
               if(Objects.equals(secondProfileSaver.get("name"), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + version + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + version + ".png");
               }

          } else if (Objects.equals(base, "profile3")) {
               if(Objects.equals(thirdProfileSaver.get("name"), "none")) {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + version + "None.png");
               }else {
                    button = getResourceIgnorePath("/commonButtons/" + base + "Button-" + version + ".png");
               }

          }
          return button;
     }

     // Common components
     private STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/commonButtons/quitButton.png"), getResourceIgnorePath("/commonButtons/quitButtonHover.png"));
     private STexturedButton hideButton = new STexturedButton(getResourceIgnorePath("/commonButtons/hideButton.png"), getResourceIgnorePath("/commonButtons/hideButtonHover.png"));

     private STexturedButton newsButton = new STexturedButton(getResourceIgnorePath("/commonButtons/newsButton-normal.png"), getResourceIgnorePath("/commonButtons/newsButton-hover.png"), getResourceIgnorePath("/commonButtons/newsButton-selected.png"));
     private STexturedButton profile1Button = new STexturedButton(getProfileButton("profile1", "normal"), getProfileButton("profile1", "hover"), getProfileButton("profile1", "selected"));
     private STexturedButton profile2Button = new STexturedButton(getProfileButton("profile2", "normal"), getProfileButton("profile2", "hover"), getProfileButton("profile2", "selected"));
     private STexturedButton profile3Button = new STexturedButton(getProfileButton("profile3", "normal"), getProfileButton("profile3", "hover"), getProfileButton("profile3", "selected"));
     private STexturedButton changesButton = new STexturedButton(getResourceIgnorePath("/commonButtons/changesButton-normal.png"), getResourceIgnorePath("/commonButtons/changesButton-hover.png"), getResourceIgnorePath("/commonButtons/changesButton-selected.png"));
     private STexturedButton aboutButton = new STexturedButton(getResourceIgnorePath("/commonButtons/aboutButton-normal.png"), getResourceIgnorePath("/commonButtons/aboutButton-hover.png"), getResourceIgnorePath("/commonButtons/aboutButton-selected.png"));

     private JLabel tabLabel = new JLabel("", SwingConstants.LEFT);

     public LauncherPanel() {
          this.setLayout(null);

         // this.add(launcherScrollPanel = new LauncherScrollPanel(), BorderLayout.CENTER);
         // this.add(LauncherScrollPanel.scrollPane);

      /*    firstProfileSaver.set("name", "none");
          firstProfileSaver.set("email", "none");

          secondProfileSaver.set("name", "none");
          secondProfileSaver.set("email", "none");

          thirdProfileSaver.set("name", "none");
          thirdProfileSaver.set("email", "none"); */

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
          tabLabel.setFont(tabLabel.getFont().deriveFont(20f));
          this.add(tabLabel);

          setNewsPage(true);

     }

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

     public void setProfilePage(boolean enabled, String profileNumber) {
          STexturedButton profileSelected = null;
          STexturedButton profileNotSelected1 = profile1Button;
          STexturedButton profileNotSelected2 = profile2Button;
          if (profileNumber == "1") {
               profileSelected = profile1Button;
               profileNotSelected1 = profile2Button;
               profileNotSelected2 = profile3Button;
               tabLabel.setText("Profil 1");
          } else if (profileNumber == "2") {
               profileSelected = profile2Button;
               profileNotSelected1 = profile1Button;
               profileNotSelected2 = profile3Button;
               tabLabel.setText("Profil 2");
          } else if (profileNumber == "3") {
               profileSelected = profile3Button;
               profileNotSelected1 = profile2Button;
               profileNotSelected2 = profile1Button;
               tabLabel.setText("Profil 3");
          } else {

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
