package fr.timeto.astrauworld.launcher.pagesutilities;

public enum PageName {
    NEWS("news"),

    PROFILE_HOME("profile-home"),
    PROFILE_ACCOUNT("profile-account"),
    PROFILE_ADDONS("profile-addons"),
    PROFILE_ADDONS_MODS("profile-addons-mods"),
    PROFILE_ADDONS_SHADERS("profile-addons-shaders"),
    PROFILE_ADDONS_SHADERS_CHOCAPICV6("profile-addons-shaders-chocapicv6"),
    PROFILE_ADDONS_SHADERS_CHOCAPICV7("profile-addons-shaders-chocapicv7"),
    PROFILE_ADDONS_SHADERS_CHOCAPICV9("profile-addons-shaders-chocapicv9"),
    PROFILE_ADDONS_RESOURCEPACKS("profile-addons-resourcepacks"),
    PROFILE_SETTINGS("profile-settings"),
    PROFILE_ALL("profile-all"),

    CHANGELOGS("changelogs"),

    ABOUT_INFOS("about-infos"),
    ABOUT_MODS("about-mods"),
    ABOUT("about");

    private final String name;

    PageName(String name) {this.name = name;}

    public String getName() {
        return name;
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
