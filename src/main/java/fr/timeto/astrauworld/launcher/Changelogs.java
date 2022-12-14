package fr.timeto.astrauworld.launcher;

import java.util.ArrayList;

public class Changelogs {

    private static final String lineSep = System.getProperty("line.separator");

    private static class TEXTS {
        public static final String BETA2_1_2 =  "- Options d'Optifine enregistr\u00e9es par profils" + lineSep +
                                                "- Console de Minecraft affich\u00e9e dans la console du launcher" + lineSep +
                                                "- Le launcher se relance quand Minecraft se ferme" + lineSep +
                                                "- Fen\u00eatre bougeable seulement depuis la barre d'infos de la fen\u00eatre" + lineSep +
                                                "- Visuel du s\u00e9lecteur de RAM dans Profil - R\u00e9glages am\u00e9lior\u00e9" + lineSep +
                                                "- Ajout de la page des changelogs" + lineSep +
                                                "- Ajout de la page \u00c0 propos - Infos";

    }

    public String version;
    public String text;
    private static final ArrayList<Changelogs> changelogsList = new ArrayList<>();

    private Changelogs(String version, String text) {
        this.version = version;
        this.text = text;

    }

    private static void initChangelogs() {
        changelogsList.add(new Changelogs("Beta2.1.2", TEXTS.BETA2_1_2));

    }

    public static ArrayList<String> returnChangelogsVersionsList() {
        initChangelogs();

        ArrayList<String> changelogsVersionsList = new ArrayList<>();
        int i = 0;
        while (i != changelogsList.toArray().length/2) {
            changelogsVersionsList.add(changelogsList.get(i).version);
            i+=1;
        }
        return changelogsVersionsList;
    }

    public static ArrayList<String> returnChangelogsTextsList() {
        initChangelogs();

        ArrayList<String> changelogsTextsList = new ArrayList<>();
        int i = 0;
        while (i != changelogsList.toArray().length/2) {
            changelogsTextsList.add(changelogsList.get(i).text);
            i+=1;
        }
        return changelogsTextsList;
    }

    public static ArrayList<Changelogs> returnChangelogsList() {
        initChangelogs();

        return changelogsList;
    }
}
