package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AWTextField extends JTextField {

    public AWTextField() {
        this("", 15f);
    }

    public AWTextField(String text) {
        this(text, 15f);
    }

    public AWTextField(float deriveFont) {
        this("", deriveFont);
    }

    public AWTextField(String text, float deriveFont) {
        super(text);

        setForeground(Color.WHITE);
        setFont(CustomFonts.kollektifBoldFont.deriveFont(deriveFont));
        setCaretColor(Color.RED);
        setSelectionColor(new Color(255, 20, 20, 200));
        setBackground(new Color(33, 33, 33));
        setBorder(new EmptyBorder(5, 10, 0, 10));
    }
}
