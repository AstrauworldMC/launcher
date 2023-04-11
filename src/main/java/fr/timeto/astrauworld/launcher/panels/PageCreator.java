package fr.timeto.astrauworld.launcher.panels;

import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;

import javax.swing.*;

import static fr.timeto.astrauworld.launcher.main.Launcher.parseUnicode;

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

        setOpaque(false);
        setLayout(null);
    }

    public void setBounds(int x, int y) {
        setBounds(x, y, 822, 517);
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag) {
            LauncherPanel.Components.titleLabel.setText(parseUnicode(title));
            LauncherPanel.Components.subTitleLabel.setText(parseUnicode(subtitle));
            LauncherSystemTray.changeTrayTooltip();
        }
        super.setVisible(aFlag);
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
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

    public void recolor() {}

}
