package fr.timeto.astrauworld.launcher.panels;

import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.pagesutilities.PageChange;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;

import javax.swing.*;

public class PageCreator extends JPanel {
    protected String title;
    protected String subtitle;

    protected final PageName pageName;
    protected final Background bg;

    protected PageCreator(PageName pageName, String title, String subtitle) {
        this.pageName = pageName;
        this.bg = pageName.getBackground();
        this.title = title;
        this.subtitle = subtitle;
    }

    public void setBounds(int x, int y) {
        setBounds(x, y, 822, 517);
    }

    public void setVisible(boolean aFlag) {
        if (aFlag && pageName == PageChange.actualPage) {
            LauncherPanel.Components.titleLabel.setText(title);
            LauncherPanel.Components.subTitleLabel.setText(subtitle);
        }
        super.setVisible(aFlag);
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public PageName getPageName() {
        return pageName;
    }

    public Background getBg() {
        return bg;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

}
