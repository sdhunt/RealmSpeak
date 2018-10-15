/*
 * RealmSpeak is the Java application for playing the board game Magic Realm.
 * Copyright (c) 2005-2015 Robin Warren
 * E-mail: robin@dewkid.com
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see
 *
 * http://www.gnu.org/licenses/
 */
package com.robin.general.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A line of text that can appear in a {@link ScrollingText} widget.
 */
public class ScrollLine {
    private static final Color LINK_URL_COLOR = Color.blue;
    private static final Font DEFAULT_FONT = new Font("Dialog", Font.PLAIN, 12);
    private static final Color DEFAULT_COLOR = Color.black;

    private Rectangle lastRect;
    private String text;
    private Font font;
    private Color color;
    private Color shadowColor;
    private int shadowOffset;
    private int alignment;
    private String linkUrl;

    /**
     * Constructs an empty scroll line.
     */
    public ScrollLine() {
        this("");
    }

    /**
     * Constructs a default scroll line, with the specified text.
     *
     * @param text the scroll text
     */
    public ScrollLine(String text) {
        this(text, DEFAULT_FONT, DEFAULT_COLOR,
             null, 0, SwingConstants.CENTER, null);
    }

    /**
     * Constructs a scroll line with the given text, font and color.
     *
     * @param text  scroll text
     * @param font  the font
     * @param color text color
     */
    public ScrollLine(String text, Font font, Color color) {
        this(text, font, color, null, 0, SwingConstants.CENTER, null);
    }

    /**
     * Constructs a scroll line with the given text, font, and color, along
     * with a shadow effect.
     *
     * @param text         the text
     * @param font         the font
     * @param color        text color
     * @param shadowColor  shadow color
     * @param shadowOffset shadow offset
     */
    public ScrollLine(String text, Font font, Color color, Color shadowColor,
                      int shadowOffset) {
        this(text, font, color, shadowColor, shadowOffset,
             SwingConstants.CENTER, null);
    }

    /**
     * Constructs a scroll line with the given text, font, and color, along
     * with a shadow effect, text alignment, and possible hyperlink URL.
     *
     * @param text         the text
     * @param font         the font
     * @param color        text color
     * @param shadowColor  shadow color
     * @param shadowOffset shadow offset
     * @param alignment    text alignment
     * @param linkUrl      link URL
     */

    public ScrollLine(String text, Font font, Color color, Color shadowColor,
                      int shadowOffset, int alignment, String linkUrl) {
        this.text = text;
        this.font = font;
        this.color = color;
        this.alignment = alignment;
        this.shadowColor = shadowColor;
        this.shadowOffset = shadowOffset;
        this.linkUrl = linkUrl;
        if (linkUrl != null) {
            this.color = LINK_URL_COLOR;
        }
    }

    /**
     * Returns the left inset for this scroll line.
     *
     * @param g the graphics context
     * @param drawWidth the width we are drawing in
     * @return the left inset
     */
    public int getLeftInset(Graphics2D g, int drawWidth) {
        switch (alignment) {
            case SwingConstants.CENTER:
                return (drawWidth - getWidth(g)) >> 1;
        }
        return 0;
    }

    /**
     * Returns the URL of the link (if defined) if the specified point lies
     * within this scroll line.
     *
     * @param p the point to consider
     * @return link URL string (or null)
     */
    public String linkAtPoint(Point p) {
        return linkUrl != null && lastRect != null && lastRect.contains(p) ? linkUrl : null;
    }

    /**
     * Returns the width of this scroll line in pixels (based off the text).
     *
     * @param g graphics context
     * @return the width in pixels
     */
    public int getWidth(Graphics2D g) {
        g.setFont(font);
        return (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
    }

    /**
     * Returns the height of this scroll line in pixels.
     *
     * @param g graphics context
     * @return the height in pixels
     */
    public int getHeight(Graphics2D g) {
        g.setFont(font);
        return (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
    }

    /**
     * Draws this scroll line into the given graphics context, at the location
     * specified by x and y coordinates.
     *
     * @param g graphics context
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void draw(Graphics2D g, int x, int y) {
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(text, g);
        int h = (int) bounds.getHeight();
        int w = (int) bounds.getWidth();
        lastRect = new Rectangle(x, y - h, w, h);
        g.setFont(font);
        if (shadowColor != null) {
            g.setColor(shadowColor);
            g.drawString(text, x + shadowOffset, y + shadowOffset);
        }
        g.setColor(color);
        g.drawString(text, x, y);
        if (linkUrl != null) {
            g.drawLine(x, y + 1, x + lastRect.width, y + 1);
        }
    }
}