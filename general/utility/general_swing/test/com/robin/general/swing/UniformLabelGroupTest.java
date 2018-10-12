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
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

/**
 * Unit tests for {@link UniformLabelGroup}.
 */
public class UniformLabelGroupTest extends AbstractGraphicsTest {

    private static final Font TEST_FONT = new Font("Courier", Font.BOLD, 18);

    @Test
    public void basic() {
        title("Basic");

        UnitTestFrame frame = new UnitTestFrame(300, 200);
        JPanel panel = frame.basePanel();

        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEtchedBorder());

        UniformLabelGroup group = new UniformLabelGroup();
        Box fooBox = group.createLabelLine("Foo");
        fooBox.add(Box.createHorizontalGlue());
        fooBox.add(new JButton("Manchu"));

        Box barBox = group.createLabelLine("Barbie Doll");
        barBox.add(Box.createHorizontalGlue());
        barBox.add(new JButton("and Ken!"));

        Box zooBox = group.createLabelLine("Zoo");
        zooBox.add(Box.createHorizontalGlue());
        zooBox.add(new JButton("Zebras"));

        Box yoBox = group.createLabelLine("Yo!", TEST_FONT);
        yoBox.add(Box.createHorizontalGlue());
        yoBox.add(new JButton("Howzat!?"));


        box.add(fooBox);
        box.add(barBox);
        box.add(zooBox);
        box.add(yoBox);

        panel.add(box);
        frame.setVisible(true);
        napForAWhile();
    }
}
