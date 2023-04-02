package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.timeto.astrauworld.launcher.customelements.Tab;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class TabManager extends JPanel {

    private ArrayList<TabList> tabs;
    private PageName actualPage;

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

        if (!this.tabs.isEmpty()) {
            TabList[] tabsArray = this.tabs.toArray(new TabList[0]);

            int i = 0;
            while (i != tabsArray.length) {
                Tab[] tabs1 = this.tabs.toArray(new Tab[0]);
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
        this.actualPage = page;

        displayGoodTabs();
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
}
