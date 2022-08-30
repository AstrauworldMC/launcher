package fr.timeto.astrauworld.launcher;

import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.*;

public class LauncherPanel extends JPanel implements SwingerEventListener {

     private Image background = getResourceIgnorePath("/baseGUI.png");

     private static LauncherPanel launcherPanel;

     private LauncherScrollPanel launcherScrollPanel;

     private Saver firstProfileSaver = new Saver(Launcher.awFirstProfileData);
     private Saver secondProfileSaver = new Saver(Launcher.awSecondProfileData);
     private Saver thirdProfileSaver = new Saver(Launcher.awThirdProfileData);

     String lineSeparator = System.getProperty("line.separator");

     // Common components
     public STexturedButton quitButton = new STexturedButton(getResourceIgnorePath("/commonButtons/quitButton.png"), getResourceIgnorePath("/commonButtons/quitButtonHover.png"));
     private STexturedButton hideButton = new STexturedButton(getResourceIgnorePath("/commonButtons/hideButton.png"), getResourceIgnorePath("/commonButtons/hideButtonHover.png"));

     public LauncherPanel() {
          this.setLayout(null);

          this.add(launcherScrollPanel = new LauncherScrollPanel(), BorderLayout.CENTER);
         // this.add(LauncherScrollPanel.scrollPane);

          // Common components
          quitButton.setBounds(962, 1);
          quitButton.addEventListener(this);
          this.add(quitButton);

          hideButton.setBounds(921, 1);
          hideButton.addEventListener(this);
          this.add(hideButton);

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
          }

     }
}
