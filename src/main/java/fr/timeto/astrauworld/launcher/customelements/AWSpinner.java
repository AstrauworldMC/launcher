package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.timutilslib.CustomFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AWSpinner extends JSpinner {

    public AWSpinner() {
        super();

        setUI(new CustomSpinnerUI());
        setForeground(Color.WHITE);
        setFont(CustomFonts.kollektifBoldFont.deriveFont(25f));
        setOpaque(false);
        setBorder(null);
        setBounds(313, 193, 93, 58);
        setBackground(new Color(33, 33, 33));
        getEditor().setBackground(new Color(33, 33, 33));
        ((JSpinner.NumberEditor)getEditor()).getTextField().setOpaque(false);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Color.WHITE);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(new Color(255, 20, 20, 200));
        ((JSpinner.NumberEditor)getEditor()).getTextField().setBorder(new EmptyBorder(5, 0, 0, 4));
    }

    public AWSpinner(SpinnerModel spinnerModel) {
        super(spinnerModel);

        setUI(new CustomSpinnerUI());
        setForeground(Color.WHITE);
        setFont(CustomFonts.kollektifBoldFont.deriveFont(25f));
        setOpaque(false);
        setBorder(null);
        setBounds(313, 193, 93, 58);
        setBackground(new Color(33, 33, 33));
        getEditor().setBackground(new Color(33, 33, 33));
        ((JSpinner.NumberEditor)getEditor()).getTextField().setOpaque(false);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setForeground(Color.WHITE);
        ((JSpinner.NumberEditor)getEditor()).getTextField().setSelectionColor(new Color(255, 20, 20, 200));
        ((JSpinner.NumberEditor)getEditor()).getTextField().setBorder(new EmptyBorder(5, 0, 0, 4));
    }
}
