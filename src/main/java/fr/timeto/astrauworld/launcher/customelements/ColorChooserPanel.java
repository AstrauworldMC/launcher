package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.textured.STexturedButton;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherFrame;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static fr.timeto.astrauworld.launcher.customelements.HSLColor.getContrastVersionForColor;

public class ColorChooserPanel extends JPanel {

    private final String text;
    private final Launcher.CUSTOM_COLORS customColor;

    private final JLabel label = new JLabel();
    private final STexturedButton resetButton = new STexturedButton(Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/resetButton-normal.png"), Swinger.getResourceIgnorePath("/assets/launcher/commonButtons/resetButton-hover.png"));
    private final JPanel colorPanel = new JPanel();

    public ColorChooserPanel(String text, Launcher.CUSTOM_COLORS customColor) {
        this.text = text;
        this.customColor = customColor;

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(412, 75));

        label.setBounds(25, 14, 280, 24);
        label.setText(text);
        label.setFont(CustomFonts.robotoBlackFont.deriveFont(22f));
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        add(label);

        resetButton.setBounds(25, 42);
        resetButton.addEventListener(swingerEvent -> customColor.reset());
        add(resetButton);

        colorPanel.setBounds(335, 12, 50, 50);
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
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
    }
}
