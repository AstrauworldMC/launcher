package fr.timeto.astrauworld.launcher.customelements;

import fr.timeto.astrauworld.launcher.main.Launcher;

import javax.swing.*;
import java.awt.*;

public class LeftMenuSelector extends JPanel {

    public LeftMenuSelector() {
        setBackground(Launcher.TEXT_COLOR);
        setBounds(0, 0, 6, 33);
        setOpaque(false);
    }

    private Thread moveThread = new Thread();

    public void moveTo(JPanel button) {
        if (moveThread.isAlive()) {
            moveThread.interrupt();
        }

        moveThread = new Thread(() -> {
            int actualY = getY();
            int finalY = button.getY() + 13;
            int pixelToMove = Math.abs(actualY - finalY);

            int pixelToMoveEachTime = 1;
            if (pixelToMove < 200) {
                pixelToMoveEachTime = 2;
            } else if (pixelToMove < 300) {
                pixelToMoveEachTime = 3;
            } else if (pixelToMove < 400) {
                pixelToMoveEachTime = 4;
            } else if (pixelToMove < 500) {
                pixelToMoveEachTime = 5;
            } else if (pixelToMove < 600) {
                pixelToMoveEachTime = 6;
            }

            while (getY() != finalY)
                if (actualY > finalY) {
                    if (pixelToMoveEachTime < getY() - finalY) {
                        setLocation(getX(), getY() - pixelToMoveEachTime);
                    } else {
                        setLocation(getX(), getY() - 1);
                    }

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else if (actualY < finalY) {
                    if (pixelToMoveEachTime < finalY - getY()) {
                        setLocation(getX(), getY() + pixelToMoveEachTime);
                    } else {
                        setLocation(getX(), getY() + 1);
                    }

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

        });
        moveThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(4,4); //Border corners arcs {width,height}, change this to whatever you want
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        //Draws the rounded panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint background
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);//paint border
    }
}
