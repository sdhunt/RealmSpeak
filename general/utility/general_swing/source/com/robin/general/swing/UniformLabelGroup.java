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
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see
 *
 * http://www.gnu.org/licenses/
 */
package com.robin.general.swing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A utility class for organizing labels uniformly.
 */
public class UniformLabelGroup {

    private static final int LABEL_LINE_HEIGHT = 20;
    private static final int DEFAULT_BORDER_WIDTH = 5;
    private static final String SPACE = " ";
    private static final String COLON = ":";

    private final java.util.List<JLabel> labels = new ArrayList<>();

    private Font labelFont = null;
    private int maxPixelWidth = 0;
    private int borderWidth;
    // only really useful for debugging purposes
    private String longest;

    /**
     * Creates a uniform label group with default border width of 5 pixels.
     */
    public UniformLabelGroup() {
        this(DEFAULT_BORDER_WIDTH);
    }

    /**
     * Creates a uniform label group with border width as specified.
     *
     * @param borderWidth the border width
     */
    public UniformLabelGroup(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Sets the font associated with this label group.
     *
     * @param font the font
     */
    public void setLabelFont(Font font) {
        labelFont = font;
    }

    /**
     * Returns the number of labels in this group.
     *
     * @return the label count
     */
    public int labelCount() {
        return labels.size();
    }

    /**
     * Returns the text of the label that has the longest (pixel) rendering.
     *
     * @return the text of the longest label
     */
    public String longestLabel() {
        return longest;
    }

    /**
     * Adds the given JLabel to the group (adjusting the collection of
     * labels as necessary).
     *
     * @param label the label to add
     * @throws NullPointerException if label is null
     */
    public void add(JLabel label) {
        if (label == null) {
            throw new NullPointerException("Label cannot be null");
        }
        labels.add(label);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int stringWidth = metrics.stringWidth(label.getText());
        if (stringWidth > maxPixelWidth) {
            maxPixelWidth = stringWidth;
            longest = label.getText();
        }
        updateLabels();
    }

    private void updateLabels() {
        Dimension d = new Dimension(maxPixelWidth + borderWidth,
                                    LABEL_LINE_HEIGHT);
        for (JLabel label : labels) {
            label.setMinimumSize(d);
            label.setMaximumSize(d);
            label.setPreferredSize(d);
        }
    }

    /**
     * Returns the maximum width, measured in pixels.
     *
     * @return maximum pixel width
     */
    public int getMaxPixelWidth() {
        return maxPixelWidth;
    }

    /**
     * Creates and returns a JLabel from the given string, also adding it
     * to the group.
     *
     * @param labelText the label text
     * @return the added label
     */
    public JLabel createLabel(String labelText) {
        JLabel jLabel = new JLabel(labelText, JLabel.RIGHT);
        if (labelFont != null) {
            jLabel.setFont(labelFont);
        }
        add(jLabel);
        return jLabel;
    }

    /**
     * Creates a label line consisting of an initial label (from the specified
     * text) with a colon (:) suffix, followed by a short space, ready for
     * more stuff to be added to the line.
     * Note that the "line" itself is a horizontal {@link Box} component.
     *
     * @param labelText the initial label text
     * @return the newly created box component
     */
    public Box createLabelLine(String labelText) {
        return createLabelLine(labelText, null);
    }

    /**
     * Creates a label line consisting of an initial label (from the specified
     * text and font) with a colon (:) suffix, followed by a short space,
     * ready for more stuff to be added to the line.
     * Note that the "line" itself is a horizontal {@link Box} component.
     *
     * @param labelText the initial label text
     * @param font      the font to apply to the label
     * @return the newly created box component
     */
    public Box createLabelLine(String labelText, Font font) {
        Box line = Box.createHorizontalBox();
        JLabel jLabel = createLabel(labelText + COLON);
        if (font != null) {
            jLabel.setFont(font);
        }
        line.add(jLabel);
        line.add(Box.createHorizontalStrut(borderWidth));
        return line;
    }

    /**
     * Creates an initially empty label line.
     * Note that the "line" itself is a horizontal {@link Box} component.
     *
     * @return the newly created box component
     */
    public Box createLine() {
        Box line = Box.createHorizontalBox();
        line.add(createLabel(SPACE));
        line.add(Box.createHorizontalStrut(borderWidth));
        return line;
    }
}
