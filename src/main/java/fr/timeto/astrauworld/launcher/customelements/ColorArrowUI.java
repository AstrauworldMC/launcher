package fr.timeto.astrauworld.launcher.customelements;

import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorArrowUI extends BasicComboBoxUI {

    public static ComboBoxUI createUI(JComponent c) {
        return new ColorArrowUI();
    }

    @Override
    protected JButton createArrowButton() {
        BasicArrowButton basicArrowButton = new BasicArrowButton(
                BasicArrowButton.SOUTH,
                new Color(30, 30, 30),
                new Color(190, 0, 0),
                new Color(180, 0, 0),
                new Color(40, 40, 40)
        );

        return basicArrowButton;
    }

    private static BufferedImage createColorImage(BufferedImage originalImage, int mask) {
        BufferedImage colorImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int pixel = originalImage.getRGB(x, y) & mask;
                colorImage.setRGB(x, y, pixel);
            }
        }

        return colorImage;
    }
}
