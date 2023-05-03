package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AWSpinner extends JSpinner {

    public AWSpinner() {
        super();

        setUI(new CustomSpinnerUI());
        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setFont(CustomFonts.robotoBlackFont.deriveFont(25f));
        setOpaque(false);
        setBorder(null);
        setBounds(313, 193, 93, 58);
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        getEditor().setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setOpaque(false);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        ((JSpinner.NumberEditor)getEditor()).getTextField().setBorder(new EmptyBorder(2, 0, 0, 4));
    }

    public AWSpinner(SpinnerModel spinnerModel) {
        super(spinnerModel);

        setUI(new CustomSpinnerUI());
        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setFont(CustomFonts.robotoBlackFont.deriveFont(25f));
        setOpaque(false);
        setBorder(null);
        setBounds(313, 193, 93, 58);
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        getEditor().setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setOpaque(false);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        ((JSpinner.NumberEditor)getEditor()).getTextField().setBorder(new EmptyBorder(2, 0, 0, 4));
    }

    public void recolor() {
        setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        getEditor().setBackground(Launcher.CUSTOM_COLORS.ELEMENTS_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setCaretColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        setUI(new CustomSpinnerUI());
        setBorder(null);
    }
}
