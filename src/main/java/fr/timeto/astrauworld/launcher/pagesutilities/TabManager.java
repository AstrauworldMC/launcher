package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.timeto.astrauworld.launcher.customelements.Tab;
import fr.timeto.astrauworld.launcher.main.Launcher;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class TabManager extends JPanel {

    private final ArrayList<TabList> tabs;
    private PageName actualPage;

    private final Selector selector = new Selector();

    public TabManager() {
        this(new ArrayList<>(), 0, 0);
    }

    public TabManager(ArrayList<TabList> tabs) {
        this(tabs, 0, 0);
    }

    public TabManager(int x, int y) {
        this(new ArrayList<>(), x, y);
    }

    public TabManager(ArrayList<TabList> tabs, int x, int y) {
        this.tabs = tabs;

        setLayout(null);
        setOpaque(false);

        setBounds(x, y, 822, 24);

        add(selector);

        if (!this.tabs.isEmpty()) {
            TabList[] tabsArray = this.tabs.toArray(new TabList[0]);

            int i = 0;
            while (i != tabsArray.length) {
                Tab[] tabs1 = tabsArray[i].toArray(new Tab[0]);
                int ii = 0;
                while (ii != tabs1.length) {
                    add(tabs1[ii]);
                    ii++;
                }

                this.tabs.get(i).setVisible(false);
                this.tabs.get(i).arrangeTabPanels();

                i++;
            }
        }

    }

    public void displayGoodTabs() {
        TabList[] array = tabs.toArray(new TabList[0]);
        TabList wanted = null;

        int i = 0;
        while (i != array.length) {
            if (Objects.equals(actualPage.getPage1(), array[i].getExemplePage().getPage1())) {
                wanted = array[i];
            } else {
                array[i].setVisible(false);
            }
            i++;
        }

        if (wanted == null) {
            return;
        }

        wanted.setVisible(true);

    }

    public void setActualPage(PageName page) {

        if (actualPage == null) {
            actualPage = page;
        }

        if (getTabFromPage(page) == null) {
            selector.setVisible(false);
        } else {
            selector.setVisible(true);
            selector.moveTo(getTabFromPage(page));
            getTabListFromPage(page).setSelected(getTabFromPage(page));
        }

        actualPage = page;

        displayGoodTabs();
    }

    public Tab getTabFromPage(PageName page) {
        Tab wanted = null;

        if (!tabs.isEmpty()) {
            TabList[] tabsArray = tabs.toArray(new TabList[0]);

            int i = 0;
            while (i != tabsArray.length) {
                Tab[] tabs1 = tabsArray[i].toArray(new Tab[0]);
                int ii = 0;

                while (ii != tabs1.length) {
                    if (tabs1[ii].getLinkedPage() == page) {
                        wanted = tabs1[ii];
                    }
                    ii++;
                }

                i++;
            }
        }

        return wanted;
    }

    public TabList getTabListFromPage(PageName page) {
        return getTabListFromTab(getTabFromPage(page));
    }

    public TabList getTabListFromTab(Tab tab) {
        TabList[] array = tabs.toArray(new TabList[0]);
        TabList wanted = null;

        int i = 0;
        while (i != array.length) {
            if (array[i].contains(tab)) {
                wanted = array[i];
            }
            i++;
        }

        return wanted;
    }

    public void addTabList(TabList tabList) {
        tabs.add(tabList);
        if (!tabList.isEmpty()) {
            Tab[] tabs1 = tabList.toArray(new Tab[0]);
            int i = 0;
            while (i != tabs1.length) {
                add(tabs1[i]);
                i++;
            }
            tabList.arrangeTabPanels();
        }
    }

    public void addTabToTabList(int index, Tab tab) {
        tabs.get(index).add(tab);
    }

    public void addTabToTabList(String name, Tab tab) {
        TabList[] array = tabs.toArray(new TabList[0]);
        TabList wanted = null;

        int i = 0;
        while (i != array.length) {
            if (Objects.equals(array[i].getName(), name)) {
                wanted = array[i];
                i = array.length;
            } else {
                i++;
            }
        }

        if (wanted == null) {
            return;
        }

        wanted.add(tab);
    }

    public TabList getTabList(String name) {
        TabList[] array = tabs.toArray(new TabList[0]);
        TabList wanted = null;

        int i = 0;
        while (i != array.length) {
            if (Objects.equals(array[i].getName(), name)) {
                wanted = array[i];
                i = array.length;
            } else {
                i++;
            }
        }

        return wanted;
    }

    public TabList getTabList(Tab tab) {
        TabList[] array = tabs.toArray(new TabList[0]);
        TabList wanted = null;

        int i = 0;
        while (i != array.length) {
            if (array[i].contains(tab)) {
                wanted = array[i];
                i = array.length;
            } else {
                i++;
            }
        }

        return wanted;
    }

    static class Selector extends JPanel {

        public Selector() {
            setBackground(Launcher.MAIN_COLOR);
            setBounds(0, 23, 100, 2);
        }

        private Thread moveThread = new Thread();

        public void moveTo(Tab tab) {
            if (tab == null) {
                return;
            }

            if (moveThread.isAlive()) {
                moveThread.interrupt();
            }

            moveThread = new Thread(() -> {
                int actualX = getX();
                int finalX = tab.getX();

                int pixelToMove = Math.abs(actualX - finalX);

                int pixelToMoveEachTime = 1;
                if (pixelToMove < 200) {
                    pixelToMoveEachTime = 2;
                } else if (pixelToMove < 300) {
                    pixelToMoveEachTime = 3;
                } else if (pixelToMove < 400) {
                    pixelToMoveEachTime = 4;
                } else if (pixelToMove < 500) {
                    pixelToMoveEachTime = 5;
                } else if (pixelToMove < 600) {
                    pixelToMoveEachTime = 6;
                }

                Thread width = new Thread();

                if (width.isAlive()) {
                    width.interrupt();
                }

                width = new Thread(() -> {
                    int actualWidth = getWidth();
                    int finalWidth = tab.getWidth();

                    while (getWidth() != finalWidth) {
                        if (actualWidth > finalWidth) {
                            setBounds(getX(), getY(), getWidth() - 1, getHeight());
                        } else if (actualWidth < finalWidth) {
                            setBounds(getX(), getY(), getWidth() + 1, getHeight());
                        }

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                width.start();

                while (getX() != finalX)
                    if (actualX > finalX) {
                        if (pixelToMoveEachTime < getX() - finalX) {
                            setLocation(getX() - pixelToMoveEachTime, getY());
                        } else {
                            setLocation(getX() - 1, getY());
                        }

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (actualX < finalX) {
                        if (pixelToMoveEachTime < finalX - getX()) {
                            setLocation(getX() + pixelToMoveEachTime, getY());
                        } else {
                            setLocation(getX() + 1, getY());
                        }

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

            });
            moveThread.start();
        }

    /*    @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(4,4); //Border corners arcs {width,height}, change this to whatever you want
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


            //Draws the rounded panel with borders.
            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
        } */
    }

}
