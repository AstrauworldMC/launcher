package fr.timeto.astrauworld.launcher.customelements;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.ProfileSaver;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;

public class SwitchButtonPanel extends JPanel implements SwingerEventListener {

    private final JLabel label = new JLabel();
    private final TexturedSwitchButton switchButton;

    private SwingerEventListener eventListener;

    public SwitchButtonPanel(String text, ProfileSaver.KEY key, boolean global) {
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(412, 75));

        label.setText(Launcher.parseUnicode(text));
        label.setBounds(25, 18, 280, 24);
        label.setFont(CustomFonts.robotoBlackFont.deriveFont(22f));
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        add(label);

        switchButton = new TexturedSwitchButton(key, global);
        switchButton.setBounds(297, 5);
        switchButton.addEventListener(this);
        switchButton.defineTextures();
        add(switchButton);

    }

    public void recolor() {
        label.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
    }

    public void addEventListener(SwingerEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void setBounds(int x, int y) {
        setBounds(x, y, getPreferredSize().width, getPreferredSize().height);
    }

    @Override
    public void setEnabled(boolean aFlag) {
        switchButton.setEnabled(aFlag);
    }

    @Override
    public void onEvent(SwingerEvent e) {
        if (e.getSource() == switchButton) {
            switchButton.toggleButton();
            if (eventListener != null) {
                eventListener.onEvent(new SwingerEvent(switchButton, SwingerEvent.BUTTON_CLICKED_EVENT));
            }
        }
    }
}
