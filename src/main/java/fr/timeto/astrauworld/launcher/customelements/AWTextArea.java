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

        setForeground(Launcher.TEXT_COLOR);
        setFont(CustomFonts.robotoBoldFont.deriveFont(deriveFont));
        setCaretColor(Launcher.MAIN_COLOR);
        setSelectionColor(Launcher.MAIN_COLOR);
        setBackground(Launcher.LIGHTER_GREY);
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setEditable(editable);
    }
}
