package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.panels.PageCreator;

import java.lang.module.FindException;
import java.util.Objects;

public class PageAnimation {

    private static final int DEFAULT_X = 0;
    private static final int RIGHT_X = 822;
    private static final int LEFT_X = -822;

    public static void animFromTo(PageCreator actualPage, PageCreator nextPage) {
        Thread t = new Thread(() -> {
            if (actualPage != nextPage) {
                if (actualPage.getParent() == nextPage.getParent()) {
                    int actualPageZOrder = actualPage.getParent().getComponentZOrder(actualPage);
                    int nextPageZOrder = actualPage.getParent().getComponentZOrder(nextPage);

                    if (actualPage.getPageName().getPriority1() < nextPage.getPageName().getPriority1()) { // actual en dessous / next au dessus
                        if (actualPageZOrder < nextPageZOrder) { // actual en dessous / next au dessus
                            actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                            actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                        }
                        actualPage.setLocation(DEFAULT_X, actualPage.getY());
                        actualPage.setVisible(true);
                        nextPage.setLocation(RIGHT_X, actualPage.getY());
                        nextPage.setVisible(true);
                        moveSmoothNextToLeft(actualPage, nextPage);
                        try {
                            animThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        actualPage.setVisible(false);
                    } else if (actualPage.getPageName().getPriority1() > nextPage.getPageName().getPriority1()) {
                        if (actualPageZOrder > nextPageZOrder) {
                            actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                            actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                        }
                        actualPage.setLocation(DEFAULT_X, actualPage.getY());
                        actualPage.setVisible(true);
                        nextPage.setLocation(DEFAULT_X, actualPage.getY());
                        nextPage.setVisible(true);
                        moveSmoothNextToRight(nextPage, actualPage);
                        try {
                            animThread.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        actualPage.setVisible(false);
                    } else {

                        if (actualPage.getPageName().getPriority2() < nextPage.getPageName().getPriority2()) { // actual en dessous / next au dessus
                            if (actualPageZOrder < nextPageZOrder) { // actual en dessous / next au dessus
                                actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                                actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                            }
                            actualPage.setLocation(DEFAULT_X, actualPage.getY());
                            actualPage.setVisible(true);
                            nextPage.setLocation(RIGHT_X, actualPage.getY());
                            nextPage.setVisible(true);
                            moveSmoothNextToLeft(actualPage, nextPage);
                            try {
                                animThread.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            actualPage.setVisible(false);
                        } else if (actualPage.getPageName().getPriority2() > nextPage.getPageName().getPriority2()) {
                            if (actualPageZOrder > nextPageZOrder) {
                                actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                                actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                            }
                            actualPage.setLocation(DEFAULT_X, actualPage.getY());
                            actualPage.setVisible(true);
                            nextPage.setLocation(DEFAULT_X, actualPage.getY());
                            nextPage.setVisible(true);
                            moveSmoothNextToRight(nextPage, actualPage);
                            try {
                                animThread.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            actualPage.setVisible(false);
                        } else {

                            if (actualPage.getPageName().getPriority3() < nextPage.getPageName().getPriority3()) { // actual en dessous / next au dessus
                                if (actualPageZOrder < nextPageZOrder) { // actual en dessous / next au dessus
                                    actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                                    actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                                }
                                actualPage.setLocation(DEFAULT_X, actualPage.getY());
                                actualPage.setVisible(true);
                                nextPage.setLocation(RIGHT_X, actualPage.getY());
                                nextPage.setVisible(true);
                                moveSmoothNextToLeft(actualPage, nextPage);
                                try {
                                    animThread.join();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                actualPage.setVisible(false);
                            } else if (actualPage.getPageName().getPriority3() > nextPage.getPageName().getPriority3()) {
                                if (actualPageZOrder > nextPageZOrder) {
                                    actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                                    actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                                }
                                actualPage.setLocation(DEFAULT_X, actualPage.getY());
                                actualPage.setVisible(true);
                                nextPage.setLocation(DEFAULT_X, actualPage.getY());
                                nextPage.setVisible(true);
                                moveSmoothNextToRight(nextPage, actualPage);
                                try {
                                    animThread.join();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                actualPage.setVisible(false);
                            } else {

                                if (actualPage.getPageName().getPriority4() < nextPage.getPageName().getPriority4()) { // actual en dessous / next au dessus
                                    if (actualPageZOrder < nextPageZOrder) { // actual en dessous / next au dessus
                                        actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                                        actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                                    }
                                    actualPage.setLocation(DEFAULT_X, actualPage.getY());
                                    actualPage.setVisible(true);
                                    nextPage.setLocation(RIGHT_X, actualPage.getY());
                                    nextPage.setVisible(true);
                                    moveSmoothNextToLeft(actualPage, nextPage);
                                    try {
                                        animThread.join();
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    actualPage.setVisible(false);
                                } else if (actualPage.getPageName().getPriority4() > nextPage.getPageName().getPriority4()) {
                                    if (actualPageZOrder > nextPageZOrder) {
                                        actualPage.getParent().setComponentZOrder(actualPage, nextPageZOrder);
                                        actualPage.getParent().setComponentZOrder(nextPage, actualPageZOrder);
                                    }
                                    actualPage.setLocation(DEFAULT_X, actualPage.getY());
                                    actualPage.setVisible(true);
                                    nextPage.setLocation(DEFAULT_X, actualPage.getY());
                                    nextPage.setVisible(true);
                                    moveSmoothNextToRight(nextPage, actualPage);
                                    try {
                                        animThread.join();
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    actualPage.setVisible(false);
                                } else {
                                    Launcher.println("fail?");
                                }

                            }
                        }
                    }

                } else {
                    throw new FindException("Les 2 pages ne font pas partie du même container parent.");
                }
            } else {
                nextPage.setVisible(true);
            }
        });
        t.start();
    }

    public static void animTo(PageCreator nextPage) {
        animFromTo(PageChange.actualPagePanel, nextPage);
    }

    static boolean inAnim = false;
    static Thread animThread;
    private static void moveSmoothNextToLeft(PageCreator actualPage, PageCreator nextPage) {
        animThread = new Thread(() -> {
            inAnim = false;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (actualPage.getX() < nextPage.getX()) {
                inAnim = true;

                while (nextPage.getX() != actualPage.getX() && inAnim) {
                    nextPage.setLocation(nextPage.getX() - 6, nextPage.getY());

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        animThread.start();
    }

    private static void moveSmoothNextToRight(PageCreator actualPage, PageCreator nextPage) {
        animThread = new Thread(() -> {
            inAnim = false;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (actualPage.getX() == nextPage.getX()) {
                inAnim = true;
                while (nextPage.getX() != RIGHT_X && inAnim) {
                    nextPage.setLocation(nextPage.getX() + 6, nextPage.getY());

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        animThread.start();
    }
}
