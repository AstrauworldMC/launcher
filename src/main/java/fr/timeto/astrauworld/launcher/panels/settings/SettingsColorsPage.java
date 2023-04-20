package fr.timeto.astrauworld.launcher.panels.settings;

import fr.timeto.astrauworld.launcher.customelements.ColorChooserPanel;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.panels.PageCreator;

import java.awt.*;

public class SettingsColorsPage extends PageCreator {

    ColorChooserPanel mainColorChooser = new ColorChooserPanel("Couleur principale", Launcher.CUSTOM_COLORS.MAIN_COLOR);
    ColorChooserPanel textColorChooser = new ColorChooserPanel("Couleur du texte", Launcher.CUSTOM_COLORS.TEXT_COLOR);
    ColorChooserPanel secondTextColorChooser = new ColorChooserPanel("Couleur du texte secondaire", Launcher.CUSTOM_COLORS.SECONDTEXT_COLOR);
    ColorChooserPanel baseBackgroundColorChooser = new ColorChooserPanel("Arri\u00e8re-plan principal", Launcher.CUSTOM_COLORS.BASE_BACKGROUND_COLOR);
    ColorChooserPanel midBackgroundColorChooser = new ColorChooserPanel("Arri\u00e8re-plan interm\u00e9diaire", Launcher.CUSTOM_COLORS.MID_BACKGROUND_COLOR);
    ColorChooserPanel darkerBackgroundColorChooser = new ColorChooserPanel("Arri\u00e8re-plan fonc\u00e9", Launcher.CUSTOM_COLORS.DARKER_BACKGROUND_COLOR);
    ColorChooserPanel elementsColorChooser = new ColorChooserPanel("Couleur des \u00e9l\u00e9ments", Launcher.CUSTOM_COLORS.ELEMENTS_COLOR);

    public SettingsColorsPage() {
        super(PageName.SETTINGS_COLORS, "Param\u00e8tres", "Couleurs personalis\u00e9es");

        setLayout(null);

        mainColorChooser.setBounds(25, 20);
        add(mainColorChooser);

        textColorChooser.setBounds(435, 20);
        add(textColorChooser);

        secondTextColorChooser.setBounds(25, 100);
        add(secondTextColorChooser);

        elementsColorChooser.setBounds(435, 100);
        add(elementsColorChooser);

        baseBackgroundColorChooser.setBounds(25, 180);
        add(baseBackgroundColorChooser);

        midBackgroundColorChooser.setBounds(435, 180);
        add(midBackgroundColorChooser);

        darkerBackgroundColorChooser.setBounds(25, 260);
        add(darkerBackgroundColorChooser);

        add(getBg().getPanel());
    }

    @Override
    public void recolor() {
        mainColorChooser.recolor();
        textColorChooser.recolor();
        secondTextColorChooser.recolor();
        baseBackgroundColorChooser.recolor();
        midBackgroundColorChooser.recolor();
        darkerBackgroundColorChooser.recolor();
        elementsColorChooser.recolor();
    }
}
