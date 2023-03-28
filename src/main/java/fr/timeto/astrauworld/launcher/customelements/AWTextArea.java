package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

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

        setForeground(Color.WHITE);
        setFont(CustomFonts.kollektifBoldFont.deriveFont(deriveFont));
        setCaretColor(Color.RED);
        setSelectionColor(new Color(255, 20, 20, 200));
        setBackground(new Color(33, 33, 33));
        setBorder(new EmptyBorder(8, 10, 5, 10));
        setEditable(editable);
    }
}
