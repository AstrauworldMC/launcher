package fr.timeto.astrauworld.launcher.customelements;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;

public class CustomSpinnerUI extends BasicSpinnerUI {

    @Override
    protected Component createNextButton() {
        Component c = createArrowButton(SwingConstants.NORTH);
        c.setName("Spinner.nextButton");
        installNextButtonListeners(c);
        return c;
    }

    @Override
    protected Component createPreviousButton() {
        Component c = createArrowButton(SwingConstants.SOUTH);
        c.setName("Spinner.previousButton");
        installPreviousButtonListeners(c);
        return c;
    }

    // copied from BasicSpinnerUI
    private Component createArrowButton (int direction) {
        return new BasicArrowButton(direction,
                new Color(40, 40, 40),
                new Color(40, 40, 40),
                Color.RED,
                new Color(40, 40, 40));
    }
}
