package fr.timeto.astrauworld.launcher.customelements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

/**
 * This class implements a layer that draws a scroll bar.
 * This scrollbar may be vertical or horizontal
 * <br> <br>
 * Copié du repo GitHub <a href="https://github.com/Reagan/Custom-UI-components">Custom-UI-components</a> et renommé de <a href="https://github.com/Reagan/Custom-UI-components/blob/master/src/org/aprilsecond/customuicomponents/ScrollPane/ScrollBar.java">ScrollBar</a> à CustomScrollBar
 * @author Reagan Mbitiru reaganmbitiru@gmail.com
 */
public class CustomScrollBar extends JPanel {

    /**
     * stores the type of scroll bar
     */
    private int scrollBarType = CustomScrollBar.VERTICAL;

    /**
     * stores the vertical type of scroll bar
     */
    public static final int VERTICAL = 1 ;

    /**
     * stores the horizontal type of scroll bar
     */
    public static final int HORIZONTAL = 2 ;

    /**
     * stores the length of the Scroll Bar
     */
    private double barLength = 102 ;

    /**
     * stores the topX or topY location
     * for the scroll bar (which may be a vertical or horizontal scroll
     * bar)
     */
    private double topX = 0 ;

    /**
     * stores the topY location for the scrollbar
     * which may be a vertical or horizontal component
     */
    private double topY = 0 ;

    /**
     * stores the color for the scroll bar
     */
    private final Color SCROLL_BAR_COLOR = new Color(153, 153, 153);

    /**
     * stores the background color for the scroll bar
     */
    private final Color SCROLL_BAR_BG_COLOR = new Color(0, 0, 0) ;

    /**
     * stores the color for the highlighted instance of
     * the scrollbar
     */
    private final Color SCROLL_BAR_HOVERED_COLOR = new Color(216, 216, 216);

    /**
     * stores the default width/length for scrollbar
     */
    private int componentDefaultDimension = 4 ;

    /**
     * stores state on whether or not the scroll bar is hovered
     */
    private boolean isTrackBarHovered = false ;

    /**
     * stores whether the scroll bar has been hovered
     */
    private boolean isScrollBarHovered = false ;

    /**
     * null constructor initializes vertical scroll bar type
     */
    public CustomScrollBar() {}

    /**
     * null constructor initializes the type of scrollbar
     * @param barType bar type
     */
    public CustomScrollBar(int barType) {

        // set the type of scroll bar
        scrollBarType = barType ;
    }

    /**
     * constructor sets the type and length of the scrollbar
     * @param barType bar type
     * @param bLength bar length
     */
    public CustomScrollBar(int barType, int bLength) {

        // set the type of scroll bar
        scrollBarType = barType ;

        // set the length of the bar
        barLength = bLength ;
    }

    /**
     * This method sets the topX location and the
     * length of the component
     * @param topXLocation top x location
     * @param lengthOfComponent length
     */
    public void setXDimensions(double topXLocation, double lengthOfComponent) {
        // set the length of the scrollbar
        barLength = lengthOfComponent;

        // set the top left position of the component
        topX = topXLocation ;

        // repaint immediately
        repaint() ;
    }

    /**
     * gets the length of component
     * @return the bar length
     */
    public double getBarLength() {
        return barLength ;
    }

    /**
     * sets the topY location and length for the
     * component
     * @param topYLocation top y location
     * @param lengthOfComponent length
     */
    public void setYDimensions(double topYLocation, double lengthOfComponent) {

        // set the length of the scrollbar
        barLength = lengthOfComponent ;

        // set the top left Y position of the component
        topY = topYLocation ;

        // repaint immediately
        repaint() ;
    }

    /**
     * get the topY dimension
     * @return top y
     */
    public double getTopY() {
        return topY ;
    }

    /**
     * get the topX dimension
     * @return top x
     */
    public double getTopX() {
        return topX ;
    }

    /**
     * sets the state of the hovering on the scroll bar track
     * @param hoveredState hovered state
     */
    public void setTrackbarHovered(boolean hoveredState) {
        isTrackBarHovered = hoveredState ;
        repaint();
    }

    /**
     * gets the state of the hovering on the scroll bar track
     * @return is track bar hovered
     */
    public boolean isTrackBarHovered() {
        return isTrackBarHovered ;
    }

    /**
     * stores whether the scroll bar is hovered on
     * @param state state
     */
    public void setScrollBarHovered(boolean state) {
        isScrollBarHovered = state ;
    }

    /**
     * returns state of hovering on scroll bar
     * @return is scroll bar hovered
     */
    public boolean isScrollbarHovered() {
        return isScrollBarHovered ;
    }

    /**
     * sets the default width/height for a component
     * @param dim dimension
     */
    public void setDefaultDimension(int dim) {
        componentDefaultDimension = dim ;
    }

    /**
     * gets the default width/height for a component
     * @return dimension
     */
    public int getDefaultDimension() {
        return componentDefaultDimension ;
    }

    /**
     * customizes the display of the scroll pane
     * @param g graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        // clone the graphics object
        Graphics2D graphics = (Graphics2D) g ;

        // set the color for the rectangle
        graphics.setColor(SCROLL_BAR_BG_COLOR) ;

        // fill the background rectangle depending on the scrollbar
        // type
        graphics.fillRect(0, 0, getWidth(),
                getHeight());

        // draw the scrollbar handle with the selected state as preferred
        if (isTrackBarHovered) {
            graphics.setColor(SCROLL_BAR_HOVERED_COLOR);
        } else {
            graphics.setColor(SCROLL_BAR_COLOR) ;
        }

        graphics.translate(topX, topY);

        if (scrollBarType == CustomScrollBar.VERTICAL) {
            graphics.fillRect(0, 0, componentDefaultDimension, (int) barLength);
        } else if (scrollBarType == CustomScrollBar.HORIZONTAL) {
            graphics.fillRect(0, 0, (int) barLength, componentDefaultDimension);
        }
    }
}
