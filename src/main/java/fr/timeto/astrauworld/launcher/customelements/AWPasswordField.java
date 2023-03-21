package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.timutilslib.CustomFonts;

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
        setFont(CustomFonts.kollektifBoldFont.deriveFont(25f));
        setCaretColor(Color.RED);
        setSelectionColor(new Color(255, 20, 20, 200));
        setBackground(new Color(33, 33, 33));
        setBorder(new EmptyBorder(5, 10, 0, 10));
    }
}
