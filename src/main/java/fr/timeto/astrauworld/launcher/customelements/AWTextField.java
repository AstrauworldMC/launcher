package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

        setForeground(Launcher.TEXT_COLOR);
        setFont(CustomFonts.robotoBlackFont.deriveFont(deriveFont));
        setCaretColor(Launcher.MAIN_COLOR);
        setSelectionColor(Launcher.MAIN_COLOR);
        setBackground(Launcher.LIGHTER_GREY);
        setBorder(new EmptyBorder(2, 10, 0, 10));
    }
}
