package fr.timeto.astrauworld.launcher.pagesutilities;


import fr.timeto.astrauworld.launcher.panels.Background;

public enum PageName {
    NEWS("news", new Background()),
    NEWS_OPEN("news-open", new Background()),

    PROFILE_HOME("profile-home", new Background().addBelowReliefRectangle()),
    PROFILE_ACCOUNT("profile-account", new Background().addLoginFields().addMiddleVerticalLine()),
    PROFILE_ADDONS("profile-addons", new Background().addAboveMiniSection().addMiddleVerticalLine()),
    PROFILE_ADDONS_MODS("profile-addons-mods", new Background().addAboveMiniSection().addMiddleVerticalLine()),
    PROFILE_ADDONS_SHADERS("profile-addons-shaders", new Background().addAboveMiniSection().addMiddleVerticalLine()),
    PROFILE_ADDONS_SHADERS_CHOCAPICV6("profile-addons-shaders-chocapicv6", new Background().addAboveMiniSection().addMiddleVerticalLine()),
    PROFILE_ADDONS_SHADERS_CHOCAPICV7("profile-addons-shaders-chocapicv7", new Background().addAboveMiniSection().addMiddleVerticalLine()),
    PROFILE_ADDONS_SHADERS_CHOCAPICV9("profile-addons-shaders-chocapicv9", new Background().addAboveMiniSection().addMiddleVerticalLine()),
    PROFILE_ADDONS_RESOURCEPACKS("profile-addons-resourcepacks", new Background().addAboveMiniSection().addMiddleVerticalLine()),
    PROFILE_SETTINGS("profile-settings", new Background().addProfileSettingsFields()),
    PROFILE_ALL("profile-all", new Background()),

    CHANGELOGS("changelogs", new Background().addBigTextArea().addComboboxUpField()),

    ABOUT_INFOS("about-infos", new Background()),
    ABOUT_MODS("about-mods", new Background()),
    ABOUT("about", new Background());

    private final String name;
    private final Background background;

    PageName(String name, Background background) {
        this.name = name;
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public Background getBackground() {
        return background;
    }

    public String getPage1() {
        try {
            String[] split = name.split("-");
            return split[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            return name;
        }
    }

    public String getTab2() {
        try {
            String[] split = name.split("-");
            return split[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "null";
        }
    }

    public String getSubTab3() {
        try {
            String[] split = name.split("-");
            return split[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "null";
        }
    }

    public String getSpecialTab4() {
        try {
            String[] split = name.split("-");
            return split[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "null";
        }
    }
}
