package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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

        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setFont(this.getFont().deriveFont(25f));
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
