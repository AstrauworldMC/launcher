package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AWTextArea extends JTextArea {

    public AWTextArea() {
        this("", 15f, false);
    }

    public AWTextArea(String text) {
        this(text, 15f, false);
    }

    public AWTextArea(float deriveFont) {
        this("", deriveFont, false);
    }

    public AWTextArea(boolean editable) {
        this("", 15f, editable);
    }

    public AWTextArea(String text, boolean editable) {
        this(text, 15f, editable);
    }

    public AWTextArea(float deriveFont, boolean editable) {
        this("", deriveFont, editable);
    }

    public AWTextArea(String text, float deriveFont) {
        this("", deriveFont, false);
    }

    public AWTextArea(String text, float deriveFont, boolean editable) {
        super(text);

        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setFont(CustomFonts.robotoBoldFont.deriveFont(deriveFont));
        setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setEditable(editable);
    }

    public void recolor() {
        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
    }
}
