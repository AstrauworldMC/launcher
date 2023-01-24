package fr.timeto.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
//from w  w w .  jav a  2s  .c  o  m
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

public class TestScrollPane {
    public static void main(String args[]) {
        JPanel container = new ScrollablePanel();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        for (int i = 0; i < 20; ++i) {
            JPanel p = new JPanel();
            p.setPreferredSize(new Dimension(50, 50));
            p.add(new JLabel("" + i));
            container.add(p);
        }

        JScrollPane scroll = new JScrollPane(container);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(scroll);
        f.pack();
        f.setSize(250, 300);
        f.setVisible(true);
    }
}
class ScrollablePanel extends JPanel implements Scrollable {
    public Dimension getPreferredSize() {
        return getPreferredScrollableViewportSize();
    }

    public Dimension getPreferredScrollableViewportSize() {
        if (getParent() == null)
            return getSize();
        Dimension d = getParent().getSize();
        int c = (int) Math
                .floor((d.width - getInsets().left - getInsets().right) / 50.0);
        if (c == 0)
            return d;
        int r = 20 / c;
        if (r * c < 20)
            ++r;
        return new Dimension(c * 50, r * 50);
    }
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation, int direction) {
        return 50;
    }
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation,
                                          int direction) {
        return 10;
    }
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
    public boolean getScrollableTracksViewportWidth() {
        return getParent() != null ? getParent().getSize().width > getPreferredSize().width
                : true;
    }
}
