package fr.timeto.astrauworld.launcher.pagesutilities;


import fr.timeto.astrauworld.launcher.panels.Background;

public enum PageName {
    NEWS("news", new Background(), "1.1"),
    NEWS_OPEN("news-open", new Background(), "1.2"),

    PROFILE_HOME("profile-home", new Background().addBelowReliefRectangle(), "2.1"),
    PROFILE_ACCOUNT("profile-account", new Background().addLoginFields().addMiddleVerticalLine(), "2.2"),
    PROFILE_ADDONS("profile-addons", new Background().addAboveMiniSection().addMiddleVerticalLine(), "2.3"),
    PROFILE_ADDONS_MODS("profile-addons-mods", new Background().addAboveMiniSection().addMiddleVerticalLine(), "2.3.0"),
    PROFILE_ADDONS_SHADERS("profile-addons-shaders", new Background().addAboveMiniSection().addMiddleVerticalLine(), "2.3.1"),
    PROFILE_ADDONS_SHADERS_CHOCAPICV6("profile-addons-shaders-chocapicv6", new Background().addAboveMiniSection().addMiddleVerticalLine(), "2.3.1.1"),
    PROFILE_ADDONS_SHADERS_CHOCAPICV7("profile-addons-shaders-chocapicv7", new Background().addAboveMiniSection().addMiddleVerticalLine(), "2.3.1.2"),
    PROFILE_ADDONS_SHADERS_CHOCAPICV9("profile-addons-shaders-chocapicv9", new Background().addAboveMiniSection().addMiddleVerticalLine(), "2.3.1.3"),
    PROFILE_ADDONS_RESOURCEPACKS("profile-addons-resourcepacks", new Background().addAboveMiniSection().addMiddleVerticalLine(), "2.3.2"),
    PROFILE_SETTINGS("profile-settings", new Background(), "2.4"),
    PROFILE_WHITELIST_SERVERS("profile-whitelistservers", new Background().addMiddleHorizontalLine().addMiddleVerticalLine(), "2.5"),
    PROFILE_ALL("profile-all", new Background(), "2"),

    CHANGELOGS("changelogs", new Background(), "3"),

    ABOUT_INFOS("about-infos", new Background(), "4.1"),
    ABOUT_MODS("about-mods", new Background(), "4.2"),
    ABOUT("about", new Background(), "4"),

    SETTINGS_COLORS("settings-colors", new Background().addMiddleVerticalLine(), "5.1"),
    SETTINGS_DISCORD("settings-discord", new Background(), "5.2"),
    SETTINGS("settings", new Background(), "5");

    private final String name;
    private final Background background;
    private final String priority;

    PageName(String name, Background background, String priority) {
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

    public String getPriority() {
        return priority;
    }

    public int getPriority1() {
        try {
            String[] split = priority.split("\\.");
            return Integer.parseInt(split[0]);
        }catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int getPriority2() {
        try {
            String[] split = priority.split("\\.");
            return Integer.parseInt(split[1]);
        }catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int getPriority3() {
        try {
            String[] split = priority.split("\\.");
            return Integer.parseInt(split[2]);
        }catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int getPriority4() {
        try {
            String[] split = priority.split("\\.");
            return Integer.parseInt(split[3]);
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
