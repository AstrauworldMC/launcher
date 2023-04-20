package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredButton;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;

public class Tab extends JPanel implements SwingerEventListener {

    private String name;
    private final PageName page;

    public final JLabel label = new JLabel();
    public final SColoredButton button = new SColoredButton(new Color(0, 0, 0, 0)) {
        @Override
        public void setEnabled(boolean e) {
            super.setEnabled(e);
            if (e) {
                label.setFont(CustomFonts.robotoBoldFont.deriveFont(16f));
            } else {
                label.setFont(CustomFonts.robotoBlackFont.deriveFont(17f));
            }
        }
    };

    public Tab(String name, PageName linkedPage) {
        this.name = Launcher.parseUnicode(name);
        this.page = linkedPage;

        setLayout(null);
        setOpaque(false);

        label.setText(this.name);
        label.setFont(CustomFonts.robotoBoldFont.deriveFont(16f));
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBounds(calculateBounds());
        add(label);

        button.setBounds(label.getBounds());
        button.setColorHover(Swinger.getTransparentWhite(20));
        button.setColorDisabled(button.getColor());
        button.addEventListener(this);
        add(button);

        setSize(label.getSize());

    }

    public void recolor() {
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
    }

    private Rectangle calculateBounds() {
        Rectangle rct = new Rectangle();

        int numberCharacters = name.length();

        if (numberCharacters <= 15) {
            rct.setSize((int) getStringSize(label, name).getWidth() + 38, (int) rct.getHeight());
        } else {
            String newName = name.substring(0, 6) + "..." + name.substring(name.length()-6);
            this.name = newName;
            rct.setSize((int) getStringSize(label, newName).getWidth() + 38, (int) rct.getHeight());
        }

        rct.setSize((int) rct.getWidth(), 24);
        return rct;
    }

    private static Dimension getStringSize(JComponent c, String text) {
        FontMetrics metrics = c.getFontMetrics(c.getFont());
        int hgt = metrics.getHeight();
        int adv = metrics.stringWidth(text);
        return new Dimension(adv + 2, hgt + 2);
    }

    @Override
    public String getName() {
        return name;
    }

    public PageName getLinkedPage() {
        return page;
    }

    @Override
    public void setEnabled(boolean e) {
        button.setEnabled(e);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (swingerEvent.getSource() == button) {
            PageChange.setPage(true, page);
        }
    }
}
