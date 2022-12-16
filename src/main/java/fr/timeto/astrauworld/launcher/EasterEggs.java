package fr.timeto.astrauworld.launcher;

import fr.theshark34.openlauncherlib.util.Saver;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class EasterEggs {
    public static final String rickroll = "Rickroll";
    public static final String polishCow = "PolishCow";
    public static final String frogWalking = "FrogWalking";

    private static final String easterEggsData = Launcher.dataFolder + "eastereggs.properties";
    private static final Path easterEggsDataPath = Paths.get(easterEggsData);
    public static final Saver easterEggsSaver = new Saver(easterEggsDataPath);

    private static ArrayList<String> initEasterEggsList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(rickroll);
        list.add(polishCow);
        list.add(frogWalking);

        return list;

    }

    public static void initEastereggs() {
        int i = 0;
        int l = initEasterEggsList().toArray().length;
        Object[] list = initEasterEggsList().toArray();

        while (i != l) {
            if (!Objects.equals(easterEggsSaver.get(list[i].toString()), "true")) {
                easterEggsSaver.set(list[i].toString(), "false");
                i += 1;
            } else {
                i += 1;
            }
        }

    }

    public static int getNumberOfFoundEasterEggs() {
        int i = 0;
        int l = initEasterEggsList().toArray().length;
        Object[] list = initEasterEggsList().toArray();
        int numberFound = 0;

        while (i != l) {
            if (Objects.equals(easterEggsSaver.get(list[i].toString()), "true")) {
                numberFound += 1;
                i += 1;
            } else {
                i += 1;
            }
        }

        return numberFound;
    }

    public static int getNumberTotalEasterEggs() {
        return initEasterEggsList().toArray().length;
    }

    public static void fondedEateregg(String easterEggs) {
        easterEggsSaver.set(easterEggs, "true");
        LauncherPanel.aboutEastereggsLabel.setText(EasterEggs.getNumberOfFoundEasterEggs() + "/" + EasterEggs.getNumberTotalEasterEggs());
    }
}
