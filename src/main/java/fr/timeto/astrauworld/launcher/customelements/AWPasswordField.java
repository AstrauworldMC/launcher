package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AWPasswordField extends JPasswordField {

    public AWPasswordField() {
        this("", 15f);
    }

    public AWPasswordField(String text) {
        this(text, 15f);
    }

    public AWPasswordField(float deriveFont) {
        this("", deriveFont);
    }

    public AWPasswordField(String text, float deriveFont) {
        super(text);

        setForeground(Color.WHITE);
        setFont(this.getFont().deriveFont(25f));
        setCaretColor(Launcher.MAIN_COLOR);
        setSelectionColor(Launcher.MAIN_COLOR);
        setBackground(Launcher.LIGHTER_GREY);
        setBorder(new EmptyBorder(2, 10, 0, 10));
    }
}
