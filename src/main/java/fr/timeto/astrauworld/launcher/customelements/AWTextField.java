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

        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setFont(CustomFonts.robotoBlackFont.deriveFont(deriveFont));
        setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        setBorder(new EmptyBorder(2, 10, 0, 10));
    }

    public void recolor() {
        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
    }
}
