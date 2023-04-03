package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;
import static fr.timeto.timutilslib.CustomFonts.robotoBlackFont;
import static fr.timeto.timutilslib.CustomFonts.robotoMediumFont;

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
                nameLabel.setFont(robotoMediumFont.deriveFont(17f));
            } else {
                nameLabel.setFont(robotoBlackFont.deriveFont(17f));
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
        setSize(178, 59);
        setOpaque(false);

        nameLabel.setBounds(61, 21, 100, 20);
        nameLabel.setForeground(Launcher.TEXT_COLOR);
        nameLabel.setFont(robotoBlackFont.deriveFont(17f));
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
            setPage(true, PageName.NEWS);
        } else if (Objects.equals(name, "Changelogs")) {
            setPage(true, PageName.CHANGELOGS);
        } else if (Objects.equals(name, "\u00c0 propos")) {
            setPage(true, PageName.ABOUT_INFOS);
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
