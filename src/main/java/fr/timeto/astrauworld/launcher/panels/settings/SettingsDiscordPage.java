package fr.timeto.astrauworld.launcher.panels.settings;

import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.astrauworld.launcher.panels.PageCreator;

public class SettingsDiscordPage extends PageCreator {

    public SettingsDiscordPage() {
        super(PageName.SETTINGS_DISCORD, "Param\u00e8tres", "Int\u00e9gration Discord");

        add(getBg().getPanel());
    }
}
