package fr.timeto.astrauworld.launcher;

import java.util.ArrayList;
import java.util.List;

public class Changelogs {
	String lineSeparator = System.getProperty("line.separator");
	
	static String sDefault = "Impossible de trouver le changelog...";
	static String s100 = "Aucun changements pour cette première version";
	
	public static ChangelogInfos vDefault = new ChangelogInfos("Erreur", sDefault);
	public static ChangelogInfos v100 = new ChangelogInfos("1.0.0", s100);
	
	public static List<ChangelogInfos> changelogsList = new ArrayList<ChangelogInfos>();
	public static int nombreChangelogs = 1;
	
	public static void setArticlesList() {
		changelogsList.add(0, v100);
	}
	
	public static String getChangesVersion(ChangelogInfos selected) {
		return selected.version;
	}
	
	public static String getChangesText(ChangelogInfos selected) {
		return selected.text;
	}
}

class ChangelogInfos {
	String version;
	String text;
	
	ChangelogInfos(String version, String text) {
		this.version = version;
		this.text = text;
	}
}