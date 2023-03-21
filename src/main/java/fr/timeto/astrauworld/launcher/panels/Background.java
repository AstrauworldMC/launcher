package fr.timeto.astrauworld.launcher.panels;

import javax.swing.*;
import java.awt.*;

public class Background {
    private boolean aboveMiniSection = false;
    private boolean middleVerticalLine = false;
    private boolean middleHorizontalLine = false;
    private boolean loginFields = false;
    private boolean belowReliefRectangle = false;

    public Background() {

    }

    public Background setAboveMiniSection(boolean e) {
        aboveMiniSection = e;
        return this;
    }

    public Background setMiddleVerticalLine(boolean e) {
        middleVerticalLine = e;
        return this;
    }

    public Background setMiddleHorizontalLine(boolean e) {
        middleHorizontalLine = e;
        return this;
    }

    public Background setLoginFields(boolean e) {
        loginFields = e;
        return this;
    }

    public Background setBelowReliefRectangle(boolean e) {
        belowReliefRectangle = e;
        return this;
    }

    public Background addAboveMiniSection() {
        aboveMiniSection = true;
        return this;
    }

    public Background addMiddleVerticalLine() {
        middleVerticalLine = true;
        return this;
    }

    public Background addMiddleHorizontalLine() {
        middleHorizontalLine = true;
        return this;
    }

    public Background addLoginFields() {
        loginFields = true;
        return this;
    }

    public Background addBelowReliefRectangle() {
        belowReliefRectangle = true;
        return this;
    }

    public boolean isAboveMiniSection() {
        return aboveMiniSection;
    }

    public boolean isMiddleVerticalLine() {
        return middleVerticalLine;
    }

    public boolean isLoginFields() {
        return loginFields;
    }

    public boolean isBelowReliefRectangle() {
        return belowReliefRectangle;
    }

    public JPanel getPanel() {
        JPanel p = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(new Color(40, 40, 40));
                g2d.fillRect(0, 0, 822, 517);

                if (belowReliefRectangle) {
                    g2d.setColor(new Color(33, 33, 33));
                    g2d.fillRect(0, 343, 822, 54);
                }

                if (aboveMiniSection) {
                    g2d.setColor(new Color(112, 112, 122));
                    g2d.fillRect(15, 71, 792, 2);
                }

                if (middleVerticalLine) {
                    g2d.setColor(new Color(112, 112, 122));
                    if (aboveMiniSection) {
                        g2d.fillRect(412, 81, 1, 420);
                    } else if (loginFields) {
                        g2d.fillRect(473, 28, 1, 284);
                    } else {
                        g2d.fillRect(412, 13, 1, 488);
                    }
                }

                if (middleHorizontalLine) {
                    g2d.setColor(new Color(112, 112, 122));
                    g2d.fillRect(20, 259, 782, 1);
                }

            }
        };
        p.setLayout(null);
        p.setOpaque(false);

        p.setBounds(0, 0, 822, 517);

        return p;
    }
}

