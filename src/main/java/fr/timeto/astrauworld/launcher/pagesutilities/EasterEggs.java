package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.timeto.astrauworld.launcher.main.Launcher;

import java.awt.*;
import java.io.File;
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
     * String général pour le nom de l'easter egg 'FriskProfileName'
     * @since Beta2.2.0
     */
    public static final String friskName = "FriskProfileName";

    /**
     * String général pour le nom de l'easter egg 'CharaProfileName'
     * @since Beta2.2.0
     */
    public static final String charaName = "CharaProfileName";

    /**
     * String général pour le nom de l'easter egg 'AsrielProfileName'
     * @since Beta2.2.0
     */
    public static final String asrielName = "AsrielProfileName";
    public static final String trueAsrielName = "TrueAsrielProfileName";

    /**
     * String général pour le nom de l'easter egg 'FloweyProfileName'
     * @since Beta2.2.0
     */
    public static final String floweyName = "FloweyProfileName";

    /**
     * String général pour le nom de l'easter egg 'CursedFloweyProfileName'
     * @since Beta2.2.0
     */
    public static final String cursedFloweyName = "CursedFloweyProfileName";

    public static final String blackSpace = "BlackSpace";
    public static final String blackSpaceStab = "BlackSpaceStab";

    /**
     * @see Launcher#dataFolder
     * @since Beta2.1.2
     */
    private static final String easterEggsData = Launcher.dataFolder + File.separator + "eastereggs.properties";
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
    public static String[] easterEggsList = {
            rickroll,
            polishCow,
            frogWalking,
            youveBeenTrolled,
            heyyaaeyaaaeyaeyaa,
            friskName,
            charaName,
            asrielName,
            trueAsrielName,
            floweyName,
            cursedFloweyName,
            blackSpace,
            blackSpaceStab
    };

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

    public static boolean getIfEasterEggIsFound(String name) {
        return Boolean.parseBoolean(easterEggsSaver.get(name));
    }

    /**
     * Pour marquer un easter egg comme trouvé dans le saver ({@link EasterEggs#easterEggsSaver})
     * @param easterEggs Le nom de l'easter egg trouvé
     * @since Beta2.1.2
     * @author <a href="https://github.com/TimEtOff">TimEtO</a>
     */
    public static void setEatereggAsFound(String easterEggs) {
        easterEggsSaver.set(easterEggs, "true");
        aboutInfosPage.eastereggsLabel.setText(EasterEggs.getNumberOfFoundEasterEggs() + "/" + EasterEggs.getNumberTotalEasterEggs());
    }

    public static boolean isOmoriCharacter(String characterName) {
        String chara = characterName.toLowerCase();

        return chara.equals("omori") ||
                chara.equals("hero") ||
                chara.equals("kel") ||
                chara.equals("aubrey") ||
                chara.equals("sunny") ||
                chara.equals("basil") ||
                chara.equals("mari");
    }

    public static String getOmoriIcon(String characterName) {
        String base = "http://www.astrauworld.be:3001/eastereggs/omori/";

        if (!isOmoriCharacter(characterName)) return null;

        if (characterName.equalsIgnoreCase("omori")) {
            return base + characterName.toLowerCase() + "-" + getOmoriEmotion3Stage(getOmoriEmotion()) + ".png";
        } else {
            return base + characterName.toLowerCase() + "-" + getOmoriEmotion2Stage(getOmoriEmotion()) + ".png";
        }
    }

    public static String getOmoriDreamIcon(String characterName) {
        String base = "http://www.astrauworld.be:3001/eastereggs/omori/";

        if (!isOmoriCharacter(characterName)) return null;

        if (characterName.equalsIgnoreCase("omori") || characterName.toLowerCase().equals("sunny")) {
            return getOmoriIcon(characterName);
        } else if (characterName.equalsIgnoreCase("basil")) {
            return base + characterName.toLowerCase() + "Dream-" + getOmoriEmotion3Stage(getOmoriEmotion()) + ".png";
        } else {
            return base + characterName.toLowerCase() + "Dream-" + getOmoriEmotion2Stage(getOmoriEmotion()) + ".png";
        }
    }

    private static EMOTION getOmoriEmotion() {
        Color color = Launcher.getMainColor();

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        float[] hsb = new float[3];
        Color.RGBtoHSB(r,g,b,hsb);

        if (r == g) {
            if (g == b) {
                if (hsb[2] >= 0.4) {
                    return EMOTION.NEUTRAL;
                } else return EMOTION.AFRAID;
            }
        }

        if (hsb[0] >= 0.116) {
            if (hsb[0] <= 0.461) return EMOTION.HAPPY;
            else if (hsb[0] <= 0.804) return EMOTION.SAD;
            else return EMOTION.ANGRY;
        } else return EMOTION.ANGRY;
    }

    private static String getOmoriEmotion2Stage(EMOTION emo) {
        Color color = Launcher.getMainColor();

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        float[] hsb = new float[3];
        hsb = Color.RGBtoHSB(r,g,b,hsb);

        if (emo == EMOTION.NEUTRAL) {
            return emo.getEmotion();
        }

        if (hsb[2] >= 0.3) {
            return emo.getEmotion();
        } else {
            return emo.getStage2();
        }

    }

    private static String getOmoriEmotion3Stage(EMOTION emo) {
        Color color = Launcher.getMainColor();

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        float[] hsb = new float[3];
        hsb = Color.RGBtoHSB(r,g,b,hsb);

        if (emo == EMOTION.NEUTRAL) {
            return emo.getEmotion();
        }

        if (hsb[2] >= 0.6) {
            return emo.getEmotion();
        } else if (hsb[2] >= 0.3) {
            return emo.getStage2();
        } else {
            return emo.getStage3();
        }

    }

    private enum EMOTION {
        NEUTRAL("neutral", "neutral", "neutral"),
        AFRAID("afraid", "afraid", "afraid"),
        HAPPY("happy", "ecstatic", "manic"),
        SAD("sad", "depressed", "miserable"),
        ANGRY("angry", "enraged", "furious");

        private final String emotion;
        private final String stage2;
        private final String stage3;

        EMOTION(String emotion, String stage2, String stage3) {
            this.emotion = emotion;
            this.stage2 = stage2;
            this.stage3 = stage3;
        }

        String getEmotion() {
            return emotion;
        }

        String getStage2() {
            return stage2;
        }

        String getStage3() {
            return stage3;
        }

    }
}
