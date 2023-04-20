package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherFrame;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.timeto.astrauworld.launcher.customelements.HSLColor.getContrastVersionForColor;

public class ColorChooserPanel extends JPanel {

    private final String text;
    private final Launcher.CUSTOM_COLORS customColor;

    private JLabel label = new JLabel();
    private JPanel colorPanel = new JPanel();

    public ColorChooserPanel(String text, Launcher.CUSTOM_COLORS customColor) {
        this.text = text;
        this.customColor = customColor;

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(400, 60));

        label.setBounds(0, 13, 280, 24);
        label.setText(text);
        label.setFont(CustomFonts.robotoBlackFont.deriveFont(22f));
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        add(label);

        colorPanel.setBounds(320, 10, 40, 40);
        colorPanel.setBackground(customColor.get());

        colorPanel.setBorder(new LineBorder(getContrastVersionForColor(customColor.get(), true), 2));

        colorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Color newColor = JColorChooser.showDialog(LauncherFrame.getInstance(), text, customColor.get(), false);
                if (newColor != null) {
                    customColor.set(newColor);
                }
            }
        });
        add(colorPanel);
    }

    public void recolor() {
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        colorPanel.setBackground(customColor.get());
        colorPanel.setBorder(new LineBorder(getContrastVersionForColor(customColor.get(), true), 2));
    }

    public void setBounds(int x, int y) {
        setBounds(x, y, 400, 60);
    }
}
