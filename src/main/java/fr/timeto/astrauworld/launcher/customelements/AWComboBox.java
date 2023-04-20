package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;

public class AWComboBox<E> extends JComboBox<E> {

    public AWComboBox() {
        this(null);
    }

    public AWComboBox(E[] array) {
        super(array);

        setFont(CustomFonts.robotoMediumFont.deriveFont(14f));
        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        setEditable(true);
        setRenderer(new CustomComboBoxRenderer());
        setEditor(new CustomComboBoxEditor());
        setUI(ColorArrowComboBoxUI.createUI(this));
        setBorder(null);
    }

    public void recolor() {
        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setRenderer(new CustomComboBoxRenderer());
        setEditor(new CustomComboBoxEditor());
        setUI(ColorArrowComboBoxUI.createUI(this));
        setBorder(null);
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
    }
}
