package fr.timeto.astrauworld.launcher;

public class BuildProperties {
    public static String getLauncherVersion() { return "${properties.getProperty('launcherVersion')}"; }
}
