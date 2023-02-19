package fr.timeto.astrauworld.launcher.panels;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;
import static fr.timeto.timutilslib.CustomFonts.kollektifBoldFont;

public class LeftMenuButton extends JPanel implements SwingerEventListener {

    private final String name;

    private final BufferedImage icon;

    private final SColoredButton button = new SColoredButton(
            Swinger.getTransparentWhite(0),
            Swinger.getTransparentWhite(20),
            Swinger.getTransparentWhite(0)) {
        @Override
        public void setEnabled(boolean enabled) {
            if (enabled) {
                nameLabel.setFont(kollektifBoldFont.deriveFont(17f));
            } else {
                nameLabel.setFont(kollektifBoldFont.deriveFont(18f));
            }
            super.setEnabled(enabled);
        }
    };

    private final JLabel nameLabel = new JLabel();

    private final JLabel iconLabel = new JLabel();

    public LeftMenuButton(String name, BufferedImage icon) {
        this.name = name;
        this.icon = icon;

        setLayout(null);
        CustomFonts.initFonts();
        setSize(178, 59);
        setOpaque(false);

        nameLabel.setBounds(61, 21, 100, 20);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(kollektifBoldFont.deriveFont(17f));
        nameLabel.setText(name);
        add(nameLabel);

        iconLabel.setBounds(15, 13, 35, 35);
        iconLabel.setIcon(new ImageIcon(Objects.requireNonNull(icon)));
        add(iconLabel);

        button.setBounds(0,0,178,59);
        button.addEventListener(this);
        add(button);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (Objects.equals(name, "Actualit\u00e9s")) {
            setNewsPage(true);
        } else if (Objects.equals(name, "Changelogs")) {
            setChangesPage(true);
        } else if (Objects.equals(name, "\u00c0 propos")) {
            setAboutPage(true, PageChange.TAB_KEY.aboutInfos);
        }

    }

    public SColoredButton getButton() {
        return button;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }
}
