/*
 * RealmSpeak is the Java application for playing the board game Magic Realm.
 * Copyright (c) 2005-2016 Robin Warren
 * E-mail: robin@dewkid.com
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see
 *
 * http://www.gnu.org/licenses/
 */

package com.robin.general.swing;

import com.robin.general.graphics.AbstractGraphicsTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Unit tests for {@link PointBar}.
 */
public class PointBarTest extends AbstractGraphicsTest {

    private static final Dimension THIN_BAR = new Dimension(200, 10);
    private static final Dimension THICK_BAR = new Dimension(200, 20);

    @Test
    @Ignore(FRAME)
    public void originalMain() {
        title("Original Main");
        PointBar bar = new PointBar(3, 12);
        bar.setValue(4);
        bar.setPreferredSize(THIN_BAR);
        JOptionPane.showMessageDialog(null, bar);
    }

    private PointBar makeBar(int value) {
        PointBar bar = new PointBar(3, 12);
        bar.setPreferredSize(THICK_BAR);
        bar.setGoal(6);
        bar.setValue(value);
        return bar;
    }


    private void addBarToBox(Box box, int value) {
        box.add(makeBar(value));
        box.add(Box.createVerticalStrut(8));
    }

    @Test
    @Ignore(FRAME)
    public void someBars() {
        title("someBars");
        UnitTestFrame frame = new UnitTestFrame(600, 300);
        Box box = Box.createVerticalBox();
        frame.basePanel().add(box);

        addBarToBox(box, 0);
        addBarToBox(box, 4);
        addBarToBox(box, 8);
        addBarToBox(box, 12);
        addBarToBox(box, 16);

        frame.setVisible(true);
        napForAWhile(10);
    }
}
