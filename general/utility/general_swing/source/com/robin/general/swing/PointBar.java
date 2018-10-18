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

/**
 * A component that displays a bar, with coloring indicating a value between
 * zero and a maximum. An optional "goal" value can be displayed in the
 * background of the bar.
 */
public class PointBar extends JComponent {

    private static final Color GOAL_COLOR = Color.red;
    private static final Color VALUE_COLOR = Color.green;
    private static final Color MARKINGS_COLOR = Color.black;

    private static final int CORNER = 10;

    private final int divisionSize;
    private final int maxValue;

    private int currentValue;
    private int goalValue;

    /**
     * Creates a point bar component with the given division width and maximum
     * value, with the current value being set to 0.
     *
     * @param divisionSize the division size
     * @param maxValue     the maximum value
     */
    public PointBar(int divisionSize, int maxValue) {
        this.maxValue = maxValue;
        this.divisionSize = divisionSize;
        this.currentValue = 0;
    }

    /**
     * Sets the current value for this point bar. The appropriate portion of
     * the bar will be colored as a result.
     *
     * @param val the current value to set
     */
    public void setValue(int val) {
        this.currentValue = val;
        repaint();
    }

    /**
     * Sets the goal value for this point bar. The goal value will be colored
     * in the "background" of the bar.
     *
     * @param goal the goal value for this bar
     */
    public void setGoal(int goal) {
        this.goalValue = goal;
        repaint();
    }

    private void paintBar(Graphics2D g2, Color color, int value,
                          double divAmt, int height) {
        g2.setColor(color);
        int width = (int) (value * divAmt);
        g2.fillRoundRect(0, 0, width, height, CORNER, CORNER);
        g2.fillRect(CORNER, 0, width - CORNER, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension size = getSize();
        int divisionCount = maxValue / divisionSize;
        double divisionWidth = (double) size.width / (double) divisionCount;
        double divisionAmount = (double) size.width / (double) maxValue;

        if (currentValue < goalValue) {
            paintBar(g2, GOAL_COLOR, goalValue, divisionAmount, size.height - 1);
        }
        paintBar(g2, VALUE_COLOR, currentValue, divisionAmount, size.height - 1);

        g2.setColor(MARKINGS_COLOR);
        g2.drawRoundRect(0, 0, size.width - 1, size.height - 1, CORNER, CORNER);
        double x = 0.0;
        for (int i = 0; i < divisionCount - 1; i++) {
            x += divisionWidth;
            int dx = (int) x;
            g2.drawLine(dx, 0, dx, size.height);
        }
    }
}
