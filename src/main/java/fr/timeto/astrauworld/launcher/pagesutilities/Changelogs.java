package fr.timeto.astrauworld.launcher.pagesutilities;

import java.util.ArrayList;

/**
 * La classe qui regroupe tout ce qui est en rapport avec les changelogs
 * @since Beta2.1.2
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 */
public class Changelogs {

    private static final String lineSep = System.getProperty("line.separator");

    /**
     * Regroupe tous les textes des changelogs
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see Changelogs#text
     */
    private static class TEXTS {
        public static final String BETA2_2_0 = "- Rangement du code et documentation dans les sources";
        public static final String BETA2_1_2 =  "- Options d'Optifine enregistr\u00e9es par profils" + lineSep +
                                                "- Console de Minecraft affich\u00e9e dans la console du launcher" + lineSep +
                                                "- Le launcher se relance quand Minecraft se ferme" + lineSep +
                                                "- Fen\u00eatre bougeable seulement depuis la barre d'infos de la fen\u00eatre" + lineSep +
                                                "- Visuel du s\u00e9lecteur de RAM dans Profil - R\u00e9glages am\u00e9lior\u00e9" + lineSep +
                                                "- Ajout de la page des changelogs" + lineSep +
                                                "- Ajout de la page \u00c0 propos - Infos";

    }

    /**
     * Le string de la version du changelog
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public String version;
    /**
     * Le string du texte du changelog (toutes les modifications depuis la dernière version
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see TEXTS
     */
    public String text;
    /**
     * La liste des changelogs
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see Changelogs#initChangelogs()
     * @see Changelogs#getChangelogsList()
     */
    private static ArrayList<Changelogs> changelogsList = new ArrayList<>();

    /**
     * Initialise un nouveau changelog
     * @param version La version de ce nouveau changelog
     * @param text Le texte ({@link TEXTS}) de ce nouveau changelog
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    private Changelogs(String version, String text) {
        this.version = version;
        this.text = text;

    }

    /**
     * Initialise la liste des changelogs ({@link Changelogs#changelogsList})
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    private static void initChangelogs() {
        changelogsList = new ArrayList<>();
        changelogsList.add(new Changelogs("Beta2.2.0", TEXTS.BETA2_2_0));
        changelogsList.add(new Changelogs("Beta2.1.2", TEXTS.BETA2_1_2));

    }

    /**
     * Pour récupérer une liste des Strings des versions de la {@link Changelogs#changelogsList}
     * @return une array list de Strings des versions
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see Changelogs#getChangelogsTextsList()
     */
    public static ArrayList<String> getChangelogsVersionsList() {
        initChangelogs();

        ArrayList<String> changelogsVersionsList = new ArrayList<>();
        int i = 0;
        while (i != changelogsList.toArray().length) {
            changelogsVersionsList.add(changelogsList.get(i).version);
            i+=1;
        }
        return changelogsVersionsList;
    }

    /**
     * Pour récupérer une liste des Strings des textes de la {@link Changelogs#changelogsList}
     * @return une array list de Strings des textes
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see Changelogs#getChangelogsVersionsList()
     */
    public static ArrayList<String> getChangelogsTextsList() {
        initChangelogs();

        ArrayList<String> changelogsTextsList = new ArrayList<>();
        int i = 0;
        while (i != changelogsList.toArray().length) {
            changelogsTextsList.add(changelogsList.get(i).text);
            i+=1;
        }
        return changelogsTextsList;
    }

    /**
     * Pour récupérer la {@link Changelogs#changelogsList} initialisée
     * @return une array list de Strings des versions
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see Changelogs#getChangelogsVersionsList()
     * @see Changelogs#getChangelogsTextsList() 
     */
    public static ArrayList<Changelogs> getChangelogsList() {
        initChangelogs();

        return changelogsList;
    }
}
