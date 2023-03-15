package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.timeto.astrauworld.launcher.panels.PageCreator;

public class PageAnimation {

    private static final int DEFAULT_X = 0;
    private static final int RIGHT_X = 178;
    private static final int LEFT_X = -178;

    public static void animFromTo(PageCreator actualPage, PageCreator nextPage) {
        Thread t = new Thread(() -> {

        });
        t.start();
    }

    public static void animTo(PageCreator nextPage) {
        PageCreator actualPage = PageChange.actualPagePanel;
        Thread t = new Thread(() -> {

        });
        t.start();

    }
}
