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
 * Unit tests for {@link VerticalLabelUI}.
 */
public class VerticalLabelUITest extends AbstractGraphicsTest {

    private class MyLabel extends JLabel {
        MyLabel(String text) {
            this(text, Color.BLACK, Color.WHITE);
        }

        MyLabel(String text, Color fg, Color bg) {
            super(text);
            setForeground(fg);
            setBackground(bg);
            setOpaque(true);
        }
    }

    @Test
    @Ignore(FRAME)
    public void verticalLabels() {
        title("vertical labels");

        UnitTestFrame frame = new UnitTestFrame(300, 200);
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        frame.getContentPane().add(panel);

        MyLabel ccLabel = new MyLabel("Counter-Clockwise", Color.BLUE, Color.YELLOW);
        ccLabel.setUI(new VerticalLabelUI());

        MyLabel stLabel = new MyLabel("Standard");
        stLabel.setOpaque(true);

        MyLabel cwLabel = new MyLabel("Clockwise", Color.YELLOW, Color.BLUE);
        cwLabel.setUI(new VerticalLabelUI(true));

        panel.add(ccLabel);
        panel.add(stLabel);
        panel.add(cwLabel);

        frame.setVisible(true);
        napForAWhile(4);
    }
}
