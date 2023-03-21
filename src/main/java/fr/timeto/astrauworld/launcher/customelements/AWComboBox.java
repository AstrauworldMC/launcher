package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;

public class AWComboBox<E> extends JComboBox<E> {

    public AWComboBox() {
        this(null);
    }

    public AWComboBox(E[] array) {
        super(array);

        setFont(CustomFonts.kollektifFont.deriveFont(14f));
        setForeground(Color.WHITE);
        setBackground(new Color(25, 25, 25));
        setEditable(true);
        setRenderer(new CustomComboBoxRenderer());
        setEditor(new CustomComboBoxEditor());
        setUI(ColorArrowComboBoxUI.createUI(this));
        setBorder(null);
    }
}
