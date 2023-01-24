package fr.timeto.astrauworld.launcher.customelements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        this.trackColor = new Color(50, 50, 50);
        this.trackHighlightColor = new Color(70, 70, 70);
        this.thumbColor = new Color(70, 70, 70);
        this.thumbDarkShadowColor = new Color(70, 70, 70);
        this.thumbLightShadowColor = new Color(70, 70, 70);
        this.thumbHighlightColor = new Color(70, 70, 70);
    }

    protected JButton createZeroButton() {
        JButton button = new JButton("");
        Dimension zeroDim = new Dimension(0,0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new BasicArrowButton(orientation,
                new Color(60, 60, 60),
                new Color(60, 60, 60),
                Color.RED,
                new Color(60, 60, 60));
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new BasicArrowButton(orientation,
                new Color(60, 60, 60),
                new Color(60, 60, 60),
                Color.RED,
                new Color(60, 60, 60));
    }
}
