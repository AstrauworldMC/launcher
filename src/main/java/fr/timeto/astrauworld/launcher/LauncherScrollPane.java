package fr.timeto.astrauworld.launcher;

import javax.swing.*;
import java.awt.*;

import static fr.theshark34.swinger.Swinger.*;

public class LauncherScrollPane extends JScrollPane {
    private JViewport scrollViewport = new JViewport();

    public Image scrollBackground = getResourceIgnorePath("/scrollTest.png");
    public JButton testButton = new JButton("Test scroll pane");
    public JTextArea testText = new JTextArea(200, 50);

    public LauncherScrollPane() { // la/les scrollbars n'apparaissent pas
        this.setPreferredSize(new Dimension(821,1000));
        this.setBounds(179, 113, 821, 517);
        this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        this.setViewport(scrollViewport);
        this.setBorder(null);
        this.setLayout(null);

        testButton.setBounds(100,100,200,50);
        this.add(testButton);

        testText.setBounds(20, 200, 500, 1000);
        this.add(testText);
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(scrollBackground, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
