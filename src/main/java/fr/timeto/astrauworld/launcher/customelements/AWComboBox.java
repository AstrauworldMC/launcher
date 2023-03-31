package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import java.awt.*;

public class AWComboBox<E> extends JComboBox<E> {

    public AWComboBox() {
        this(null);
    }

    public AWComboBox(E[] array) {
        super(array);

        setFont(CustomFonts.robotoMediumFont.deriveFont(14f));
        setForeground(Color.WHITE);
        setBackground(Launcher.LIGHTER_GREY);
        setEditable(true);
        setRenderer(new CustomComboBoxRenderer());
        setEditor(new CustomComboBoxEditor());
        setUI(ColorArrowComboBoxUI.createUI(this));
        setBorder(null);
    }
}
