package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.timeto.astrauworld.launcher.customelements.Tab;

import java.util.ArrayList;
import java.util.Objects;

public class TabList extends ArrayList<Tab> {

    private String name;
    private PageName exemplePage;
    private boolean isInit = false;

    public TabList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageName getExemplePage() {
        return exemplePage;
    }

    public void recolor() {
        if (!isEmpty()) {
            int i = 0;
            Tab[] tabs = this.toArray(new Tab[0]);
            while (i != tabs.length) {
                tabs[i].recolor();
                i++;
            }
        }
    }

    public void arrangeTabPanels() {
        if (!this.isEmpty()) {
            Tab[] tabs = this.toArray(new Tab[0]);
            int i = 0;
            int lastX = 0;

            while (i != tabs.length) {
                tabs[i].setLocation(lastX, 0);
                lastX = tabs[i].getX() + tabs[i].getWidth();
                i++;
            }
        }
    }

    public void setVisible(boolean e) {
        if (!this.isEmpty()) {
            Tab[] tabs = this.toArray(new Tab[0]);
            int i = 0;
            while (i != tabs.length) {
                tabs[i].setVisible(e);
                i++;
            }
        }
    }

    public void setSelected(Tab tab) {
        if (!this.isEmpty()) {
            Tab[] tabs = this.toArray(new Tab[0]);
            int i = 0;
            while (i != tabs.length) {
                tabs[i].setEnabled(tabs[i] != tab);
                i++;
            }
        }
    }

    @Override
    public boolean add(Tab tab) {
        if (!isInit) {
            exemplePage = tab.getLinkedPage();
            super.add(tab);
            isInit = true;
            return true;
        } else {
            if (Objects.equals(tab.getLinkedPage().getPage1(), exemplePage.getPage1())) {
                super.add(tab);
                return true;
            } else {
                return false;
            }
        }
    }
}
