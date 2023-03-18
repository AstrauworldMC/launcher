package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.flowarg.flowupdater.download.json.CurseFileInfo;

public class Mod {

    private final String name;
    private final String version;
    private final CurseFileInfo curseFileInfo;

    public Mod(String name, String version, CurseFileInfo curseFileInfo) {
        this.name = name;
        this.version = version;
        this.curseFileInfo = curseFileInfo;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getNameAndVersion() {
        return name + " " + version;
    }

    public CurseFileInfo getCurseFileInfo() {
        return curseFileInfo;
    }
}
