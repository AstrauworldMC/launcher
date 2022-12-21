package fr.timeto.astrauworld.launcher.customelements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ZoneWindowMover extends MouseAdapter {

    private Point click;
    private final JFrame window;
    private final Rectangle zone;

    public ZoneWindowMover(JFrame window, Rectangle zone) {
        this.window = window;
        this.zone = zone;
    }

    public void mouseDragged(MouseEvent e) {
        if (this.click != null) {
            Point draggedPoint = MouseInfo.getPointerInfo().getLocation();
            if (zone.contains(click)) {
                this.window.setLocation(new Point((int) draggedPoint.getX() - (int) this.click.getX(), (int) draggedPoint.getY() - (int) this.click.getY()));
            }
        }

    }

    public void mousePressed(MouseEvent e) {
        this.click = e.getPoint();
    }
}
