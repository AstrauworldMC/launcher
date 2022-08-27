package fr.timeto.astrauworld.launcher;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.*;

public class LauncherScrollPanel extends JPanel {

    public Image scrollBackground = getResourceIgnorePath("/scrollTest.png");
    private static LauncherScrollPanel launcherScrollPanel;
    public static JScrollPane scrollPane = new JScrollPane(launcherScrollPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    public LauncherScrollPanel() {
        this.setLayout(new BorderLayout());
        this.setBounds(179, 113, 821, 517);
        this.setVisible(true);

        scrollPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        this.add(scrollPane);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(scrollBackground, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
