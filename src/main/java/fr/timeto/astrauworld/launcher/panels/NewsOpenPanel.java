package fr.timeto.astrauworld.launcher.panels;

import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.customelements.HSLColor;
import fr.timeto.astrauworld.launcher.main.Launcher;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.astrauworld.launcher.pagesutilities.PageName;
import fr.timeto.timutilslib.CustomFonts;
import fr.timeto.timutilslib.CustomScrollBarUI;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.background;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;

public class NewsOpenPanel extends PageCreator {

    private final JScrollPane scrollPane;
    private  final JPanel container = new JPanel();
    private  final JPanel inside = new JPanel();

     JLabel imageLabel = new JLabel();
     JLabel titleLabel = new JLabel();
 //   private static WebView browser = new WebView();
 //   static WebEngine textArea = browser.getEngine();
     JTextArea textArea = new JTextArea();

    public NewsOpenPanel() {
        super(PageName.NEWS_OPEN, "Actualit\u00e9s", "");

        setLayout(null);
        setOpaque(false);

        scrollPane = new JScrollPane();

        scrollPane.setBounds(0, 0, 822, 517);
        add(scrollPane);

        scrollPane.setOpaque(false);

        container.setLayout(new GridLayout(1,1));
        container.setPreferredSize(new Dimension(822, 1500));
        container.setBackground(Swinger.getTransparentWhite(10));
        container.setOpaque(false);

        inside.setLayout(null);
        inside.setPreferredSize(new Dimension(822, 1500));
        inside.setBackground(Swinger.getTransparentWhite(10));
        inside.setOpaque(false);

        scrollPane.setViewportView(container);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        container.add(inside);

        imageLabel.setBounds(0, 0, 822, 213);
        inside.add(imageLabel);

        titleLabel.setBounds(20, 220, 764, 30);
        titleLabel.setFont(CustomFonts.robotoBlackFont.deriveFont(22f));
        titleLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        inside.add(titleLabel);

        textArea.setBounds(20, 260, 764, 1200);
    //    textArea.setContentType("text/html");
        textArea.setFont(CustomFonts.robotoMediumFont.deriveFont(16f));
        textArea.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        textArea.setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        textArea.setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
        textArea.setOpaque(false);
        textArea.setEditable(false);
        inside.add(textArea);

        add(getBg().getPanel());
    }

    @Override
    public void recolor() {
        titleLabel.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        textArea.setForeground(Launcher.CUSTOM_COLORS.TEXT_COLOR.get());
        textArea.setSelectionColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get());
        textArea.setSelectedTextColor(HSLColor.getContrastVersionForColor(Launcher.CUSTOM_COLORS.MAIN_COLOR.get(), true));
    }

    public void setNewsPage(News news) {
        setPage(false, PageName.NEWS);
        setPage(false, PageName.PROFILE_ALL, null);
        setPage(false, PageName.CHANGELOGS);
        setPage(false, PageName.ABOUT);

        setSubtitle(news.getTitle());

        LauncherSystemTray.changeTrayTooltip();

        imageLabel.setIcon(new ImageIcon(news.getImage()));
        titleLabel.setText(news.getTitle());
        textArea.setText(news.getText());
    /*    try {
            textArea.setPage("https://github.com/AstrauworldMC/launcher/blob/main/README.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } */
        //    textArea.loadContent(news.getText(), "text/html");

        background = getResourceIgnorePath("/assets/launcher/main/baseGUI.png");

        corner.setVisible(true);
    }
}
