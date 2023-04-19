package fr.timeto.astrauworld.launcher.panels.settings;

import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.panels.PageCreator;

public class SettingsColorsPage extends PageCreator {

    public SettingsColorsPage() {
        super(PageName.SETTINGS_COLORS, "Param\u00e8tres", "Couleurs");

        add(getBg().getPanel());
    }
}
