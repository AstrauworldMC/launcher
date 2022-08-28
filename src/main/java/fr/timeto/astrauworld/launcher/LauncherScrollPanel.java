package fr.timeto.astrauworld.launcher;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.*;
import static javax.swing.ScrollPaneConstants.*;

public class LauncherScrollPanel extends JPanel {

    private static LauncherScrollPanel launcherScrollPanel;

    private static JViewport viewport = new JViewport();
    public static JScrollPane scrollPane = new JScrollPane(launcherScrollPanel, VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);

    private JTextArea testScroll = new JTextArea(50,30);

    public LauncherScrollPanel() {
        this.setBounds(178, 113, 822, 517);
        scrollPane.setPreferredSize(new Dimension(822, 2000));
       // scrollPane.setViewport(viewport);
    //    scrollPane.setViewportView(launcherScrollPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(Color.red);
        scrollPane.setOpaque(false);
        scrollPane.setBackground(Color.red);
        this.setOpaque(false);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        testScroll.setBounds(20, 20, 600, 1500);
        this.add(testScroll);

        this.add(scrollPane);

    }
}
