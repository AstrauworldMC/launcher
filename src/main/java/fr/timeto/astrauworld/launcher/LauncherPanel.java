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

     private Image background = getResourceIgnorePath("/Homepage.png");

     private static LauncherPanel launcherPanel;

     private LauncherScrollPanel launcherScrollPanel;

     private Saver firstProfileSaver = new Saver(Launcher.awFirstProfileData);
     private Saver secondProfileSaver = new Saver(Launcher.awSecondProfileData);
     private Saver thirdProfileSaver = new Saver(Launcher.awThirdProfileData);

     String lineSeparator = System.getProperty("line.separator");

     public SColoredButton testButton = new SColoredButton(Color.CYAN, Color.BLUE);



     public LauncherPanel() {
          this.setLayout(null);

          this.add(launcherScrollPanel = new LauncherScrollPanel(), BorderLayout.CENTER);
         // this.add(LauncherScrollPanel.scrollPane);

          testButton.setBounds(10, 10, 100, 50);
          testButton.addEventListener(this);
          this.add(testButton);

     }

     public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
     }


     @Override
     public void onEvent(SwingerEvent swingerEvent) {

     }
}
