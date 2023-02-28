package fr.timeto.astrauworld.launcher.panels;

import fr.theshark34.swinger.abstractcomponents.AbstractButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PageCreator extends JPanel implements SwingerEventListener {
    String title;
    String subtitle;

    BufferedImage bg;

    ArrayList<?> componentsList;

    public static class Builder {
        private PageCreator p;

        public Builder(String title, String subtitle) {
            p.title = title;
            p.subtitle = subtitle;
        }

        public Builder setBackground(BufferedImage bg) {
            p.bg = bg;
            return this;
        }

        public PageCreator build() {
          return p;
        };

    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this);

    }
}