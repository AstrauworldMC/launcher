package fr.timeto.astrauworld.launcher;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.*;

public class LauncherPanel extends JPanel {

     private Image background = getResourceIgnorePath("/Homepage.png");

     public void paintComponent(Graphics g) {
          super.paintComponent(g);
          g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
     }


}
