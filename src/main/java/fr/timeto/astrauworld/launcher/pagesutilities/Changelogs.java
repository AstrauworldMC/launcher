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
        public static final String BETA2_2_2 =  "- R\u00e9glages de bugs sur les shaders, les changelogs et l'affichage du profil connect\u00e9" + lineSep +
                                                "- Les boutons de profil du menu de gauche ne sont plus d\u00e9pendants d'une image lorsqu'ils n'ont pas de compte";
        public static final String BETA2_2_0 =  "- Rangement du code et documentation dans les sources" + lineSep +
                                                "- Ajout de la license GPLv3" + lineSep +
                                                "- Ajout du system tray icon" + lineSep +
                                                "- R\u00e9glages de bugs avec les fichiers customs" + lineSep +
                                                "- Ajout des pages profils - addons [shaders]" + lineSep +
                                                "- Ajout du diaporama sur la page d'accueil des profils (en lien avec l'heure syst\u00e8me)" + lineSep +
                                                "- Ajout de la fen\u00eatre des infos sur le serveur" + lineSep +
                                                "- Remodelage de l'apparence de certains \u00e9l\u00e9ments";
        public static final String BETA2_1_2 =  "- Options d'Optifine enregistr\u00e9es par profils" + lineSep +
                                                "- Console de Minecraft affich\u00e9e dans la console du assets" + lineSep +
                                                "- Le assets se relance quand Minecraft se ferme" + lineSep +
                                                "- Fen\u00eatre mobile seulement depuis la barre d'infos de la fen\u00eatre" + lineSep +
                                                "- Visuel du s\u00e9lecteur de RAM dans Profil - R\u00e9glages am\u00e9lior\u00e9" + lineSep +
                                                "- Ajout de la page des changelogs" + lineSep +
                                                "- Ajout de la page \u00c0 propos - Infos";

    }

    /**
     * Le string de la version du changelog
     * @since Beta2.1.2
     */
    public String version;
    /**
     * Le string du texte du changelog (toutes les modifications depuis la dernière version
     * @since Beta2.1.2
     * @see TEXTS
     */
    public String text;
    /**
     * La liste des changelogs
     * @since Beta2.1.2
     */
    private static final Changelogs[] changelogsList = {
            new Changelogs("Beta2.2.2", TEXTS.BETA2_2_2),
            new Changelogs("Beta2.2.0", TEXTS.BETA2_2_0),
            new Changelogs("Beta2.1.2", TEXTS.BETA2_1_2)
    };

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
     * Pour récupérer une liste des Strings des versions de la {@link Changelogs#changelogsList}
     * @return une array list de Strings des versions
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see Changelogs#getChangelogsTextsList()
     */
    public static String[] getChangelogsVersionsList() {
        ArrayList<String> changelogsVersionsList = new ArrayList<>();
        int i = 0;
        while (i != changelogsList.length) {
            changelogsVersionsList.add(changelogsList[i].version);
            i+=1;
        }

        return changelogsVersionsList.toArray(new String[0]);
    }

    /**
     * Pour récupérer une liste des Strings des textes de la {@link Changelogs#changelogsList}
     * @return une array list de Strings des textes
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     * @see Changelogs#getChangelogsVersionsList()
     */
    public static String[] getChangelogsTextsList() {
        ArrayList<String> changelogsTextsList = new ArrayList<>();
        int i = 0;
        while (i != changelogsList.length) {
            changelogsTextsList.add(changelogsList[i].text);
            i+=1;
        }
        return changelogsTextsList.toArray(new String[0]);
    }
}
