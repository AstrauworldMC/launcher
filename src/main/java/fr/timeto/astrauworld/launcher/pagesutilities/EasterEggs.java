package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.main.Launcher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;

/**
 * La classe regroupant tout ce qui en lien avec les easters eggs
 * @since Beta2.1.2
 * @author <a href="https://github.com/TimEtOff">TimEtO</a>
 */
public class EasterEggs {
    /**
     * String général pour le nom de l'easter egg 'Rickroll'
     * @since Beta2.1.2
     */
    public static final String rickroll = "Rickroll";
    /**
     * String général pour le nom de l'easter egg 'PolishCow'
     * @since Beta2.1.2
     */
    public static final String polishCow = "PolishCow";
    /**
     * String général pour le nom de l'easter egg 'FrogWalking'
     * @since Beta2.1.2
     */
    public static final String frogWalking = "FrogWalking";

    /**
     * String général pour le nom de l'easter egg 'You've been trolled'
     * @since Beta2.2.0
     */
    public static final String youveBeenTrolled = "YouveBeenTrolled";

    /**
     * String général pour le nom de l'easter egg 'HEYYEYAAEYAAAEYAEYAA'
     * @since Beta2.2.0
     */
    public static final String heyyaaeyaaaeyaeyaa = "HEYYEYAAEYAAAEYAEYAA";

    /**
     * @see Launcher#dataFolder
     * @since Beta2.1.2
     */
    private static final String easterEggsData = Launcher.dataFolder + System.getProperty("file.separator") + "eastereggs.properties";
    /**
     * Path de {@link EasterEggs#easterEggsData}
     * @since Beta2.1.2
     */
    private static final Path easterEggsDataPath = Paths.get(easterEggsData);
    /**
     * Le {@link Saver} lié au fichier des easter eggs ({@link EasterEggs#easterEggsDataPath})
     * @since Beta2.1.2
     */
    public static final Saver easterEggsSaver = new Saver(easterEggsDataPath);

    /**
     * La liste des easters eggs
     * @since Beta2.2.0
     */
    public static String[] easterEggsList = {rickroll, polishCow, frogWalking, youveBeenTrolled, heyyaaeyaaaeyaeyaa};

    /**
     * Initialise le saver ({@link EasterEggs#easterEggsSaver})
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void initEastereggs() {
        int i = 0;
        int l = easterEggsList.length;

        while (i != l) {
            if (!Objects.equals(easterEggsSaver.get(easterEggsList[i]), "true")) {
                easterEggsSaver.set(easterEggsList[i], "false");
                i += 1;
            } else {
                i += 1;
            }
        }

    }

    /**
     * Pour récupérer le nombre d'easter eggs trouvé
     * @return Le nombre d'easter eggs trouvé sous forme de {@code int}
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static int getNumberOfFoundEasterEggs() {
        int i = 0;
        int l = easterEggsList.length;
        int numberFound = 0;

        while (i != l) {
            if (Objects.equals(easterEggsSaver.get(easterEggsList[i]), "true")) {
                numberFound += 1;
                i += 1;
            } else {
                i += 1;
            }
        }

        return numberFound;
    }

    /**
     * Pour récupérer le nombre d'easter eggs total
     * @return Le nombre d'easter eggs total sous forme de {@code int}
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static int getNumberTotalEasterEggs() {
        return easterEggsList.length;
    }

    /**
     * Pour marquer un easter egg comme trouvé dans le saver ({@link EasterEggs#easterEggsSaver})
     * @param easterEggs Le nom de l'easter egg trouvé
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setEatereggAsFound(String easterEggs) {
        easterEggsSaver.set(easterEggs, "true");
        aboutEastereggsLabel.setText(EasterEggs.getNumberOfFoundEasterEggs() + "/" + EasterEggs.getNumberTotalEasterEggs());
    }
}
