package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AWSpinner extends JSpinner {

    public AWSpinner() {
        super();

        setUI(new CustomSpinnerUI());
        setForeground(Launcher.getTextColor());
        setFont(CustomFonts.robotoBlackFont.deriveFont(25f));
        setOpaque(false);
        setBorder(null);
        setBounds(313, 193, 93, 58);
        setBackground(Launcher.LIGHTER_GREY);
        getEditor().setBackground(Launcher.LIGHTER_GREY);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setOpaque(false);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Launcher.getTextColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setCaretColor(Launcher.getMainColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(Launcher.getMainColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setBorder(new EmptyBorder(2, 0, 0, 4));
    }

    public AWSpinner(SpinnerModel spinnerModel) {
        super(spinnerModel);

        setUI(new CustomSpinnerUI());
        setForeground(Launcher.getTextColor());
        setFont(CustomFonts.robotoBlackFont.deriveFont(25f));
        setOpaque(false);
        setBorder(null);
        setBounds(313, 193, 93, 58);
        setBackground(Launcher.LIGHTER_GREY);
        getEditor().setBackground(Launcher.LIGHTER_GREY);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setOpaque(false);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Launcher.getTextColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setCaretColor(Launcher.getMainColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(Launcher.getMainColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setBorder(new EmptyBorder(2, 0, 0, 4));
    }

    public void recolor() {
        setForeground(Launcher.getTextColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Launcher.getTextColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setCaretColor(Launcher.getMainColor());
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(Launcher.getMainColor());
        setUI(new CustomSpinnerUI());
        setBorder(null);
    }
}
