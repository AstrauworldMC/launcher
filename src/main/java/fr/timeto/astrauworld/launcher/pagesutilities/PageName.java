package fr.timeto.astrauworld.launcher.pagesutilities;


import fr.timeto.astrauworld.launcher.panels.Background;

public enum PageName {
    NEWS("news", new Background(), 1.1),
    NEWS_OPEN("news-open", new Background(), 1.2),

    PROFILE_HOME("profile-home", new Background().addBelowReliefRectangle(), 2.1),
    PROFILE_ACCOUNT("profile-account", new Background().addLoginFields().addMiddleVerticalLine(), 2.2),
    PROFILE_ADDONS("profile-addons", new Background().addAboveMiniSection().addMiddleVerticalLine(), 2.3),
    PROFILE_ADDONS_MODS("profile-addons-mods", new Background().addAboveMiniSection().addMiddleVerticalLine(), 2.30),
    PROFILE_ADDONS_SHADERS("profile-addons-shaders", new Background().addAboveMiniSection().addMiddleVerticalLine(), 2.31),
    PROFILE_ADDONS_SHADERS_CHOCAPICV6("profile-addons-shaders-chocapicv6", new Background().addAboveMiniSection().addMiddleVerticalLine(), 2.311),
    PROFILE_ADDONS_SHADERS_CHOCAPICV7("profile-addons-shaders-chocapicv7", new Background().addAboveMiniSection().addMiddleVerticalLine(), 2.312),
    PROFILE_ADDONS_SHADERS_CHOCAPICV9("profile-addons-shaders-chocapicv9", new Background().addAboveMiniSection().addMiddleVerticalLine(), 2.313),
    PROFILE_ADDONS_RESOURCEPACKS("profile-addons-resourcepacks", new Background().addAboveMiniSection().addMiddleVerticalLine(), 2.32),
    PROFILE_SETTINGS("profile-settings", new Background().addProfileSettingsFields(), 2.4),
    PROFILE_WHITELIST_SERVERS("profile-whitelistservers", new Background().addMiddleHorizontalLine().addMiddleVerticalLine(), 2.5),
    PROFILE_ALL("profile-all", new Background(), 2),

    CHANGELOGS("changelogs", new Background().addBigTextArea().addComboboxUpField(), 3),

    ABOUT_INFOS("about-infos", new Background(), 4.1),
    ABOUT_MODS("about-mods", new Background(), 4.2),
    ABOUT("about", new Background(), 4);

    private final String name;
    private final Background background;
    private final double priority;

    PageName(String name, Background background, double priority) {
        this.name = name;
        this.background = background;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public Background getBackground() {
        return background;
    }

    public double getPriority() {
        return priority;
    }

    public double getPriority1() {
        try {
            String[] split = String.valueOf(priority).split(".");
            return Double.parseDouble(split[0]);
        }catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public double getSubPriority2() {
        try {
            String[] split = String.valueOf(priority).split(".");
            return Double.parseDouble(split[1]);
        }catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
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
