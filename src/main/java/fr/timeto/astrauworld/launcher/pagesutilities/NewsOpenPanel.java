package fr.timeto.astrauworld.launcher.pagesutilities;

import fr.theshark34.swinger.Swinger;
import fr.timeto.astrauworld.launcher.customelements.CustomScrollBarUI;
import fr.timeto.astrauworld.launcher.main.LauncherPanel;
import fr.timeto.astrauworld.launcher.main.LauncherSystemTray;
import fr.timeto.timutilslib.CustomFonts;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static fr.theshark34.swinger.Swinger.getResourceIgnorePath;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.Components.*;
import static fr.timeto.astrauworld.launcher.main.LauncherPanel.background;
import static fr.timeto.astrauworld.launcher.pagesutilities.PageChange.*;

public class NewsOpenPanel extends JScrollPane {
    JPanel panel = new JPanel();

    public static News[] newsList = {
            new News("test", "Ceci est un article de test", "TimEtOff", "23/01/2023"),
            new News("test", "Ceci est un article de test", "Astrauwolf", "24/02/2023"),
            new News("test", "Ceci est un article de test", "Thomasdu332", "25/03/2023"),
            new News("test", "Ceci est un article de test", "Capitenzo674", "26/04/2023"),
            new News("test", "Ceci est un article de test", "ThunderFurax", "27/05/2023"),
            new News("test", "Ceci est un article de test", "Cyril.04", "28/06/2023")};

    static Box[] boxes;
    private static JPanel container = new JPanel();
    private static JPanel inside = new JPanel();

    static JLabel imageLabel = new JLabel();
    static JLabel titleLabel = new JLabel();
 //   private static WebView browser = new WebView();
 //   static WebEngine textArea = browser.getEngine();
    static JTextArea textArea = new JTextArea();

    public NewsOpenPanel() {
        CustomFonts.initFonts();

        setOpaque(false);

        container.setLayout(new GridLayout(1,1));
        container.setPreferredSize(new Dimension(822, 1500));
        container.setBackground(Swinger.getTransparentWhite(10));
        container.setOpaque(false);

        inside.setLayout(null);
        inside.setPreferredSize(new Dimension(822, 1500));
        inside.setBackground(Swinger.getTransparentWhite(10));
        inside.setOpaque(false);

        setViewportView(container);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getVerticalScrollBar().setUI(new CustomScrollBarUI());
        getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        getVerticalScrollBar().setUnitIncrement(14);

        getViewport().setOpaque(false);
        setBorder(null);

        container.add(inside);

        imageLabel.setBounds(0, 0, 822, 213);
        inside.add(imageLabel);

        titleLabel.setBounds(20, 220, 764, 30);
        titleLabel.setFont(CustomFonts.kollektifBoldFont.deriveFont(22f));
        titleLabel.setForeground(Color.WHITE);
        inside.add(titleLabel);

        textArea.setBounds(20, 260, 764, 1200);
    //    textArea.setContentType("text/html");
        textArea.setFont(CustomFonts.kollektifFont.deriveFont(16f));
        textArea.setForeground(Color.WHITE);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        inside.add(textArea);

    }

    public static void setNewsPage(News news) {
        PageChange.setNewsPage(false);
        setProfilePage(false, null, "all");
        setChangesPage(false);
        setAboutPage(false, null);

        LauncherPanel.Components.titleLabel.setText("Actualit\u00e9s");
        subTitleLabel.setText(news.getTitle());
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

        background = getResourceIgnorePath("/assets/launcher/main/baseGUI -Vierge.png");

        corner.setVisible(true);
    }
}
