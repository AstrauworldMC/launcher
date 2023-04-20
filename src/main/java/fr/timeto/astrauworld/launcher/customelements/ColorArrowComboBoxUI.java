package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;

import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorArrowComboBoxUI extends BasicComboBoxUI {

    public static ComboBoxUI createUI(JComponent c) {
        return new ColorArrowComboBoxUI();
    }

    @Override
    protected JButton createArrowButton() {

        return new BasicArrowButton(
                BasicArrowButton.SOUTH,
                Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get(),
                Launcher.CUSTOM_COLORS.MAIN_COLOR.get().darker(),
                Launcher.CUSTOM_COLORS.MAIN_COLOR.get(),
                Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get().brighter()
        );
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
