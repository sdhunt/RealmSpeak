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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Unit tests for {@link SingleButtonManager}.
 */
public class SingleButtonManagerTest extends AbstractGraphicsTest {

    private static final Dimension BUTTON_DIM = new Dimension(80, 24);
    private static final String LINE = ">-<";

    private int step = 0;

    @Test
    @Ignore(FRAME)
    public void basic() {
        title("Basic");
        /*
         * An example of how the SingleButton / SingleButtonManager works
         * to provide "step-through" actions via the buttons.
         *
         * This example based on CharacterActionPanel code.
         */
        step = 0;


        Box box = Box.createHorizontalBox();
        SingleButtonManager manager = new SingleButtonManager();

        // first button...
        SingleButton b1 = new SingleButton("One", true) {
            @Override
            public boolean needsShow() {
                return step < 1;
            }
        };
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print("ONE Pressed");
                step++;
                manager.updateButtonVisibility();
            }
        });
        b1.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
        b1.setVisible(false);
        ComponentTools.lockComponentSize(b1, BUTTON_DIM);
        manager.addButton(b1);
        box.add(b1);

        // second button...
        SingleButton b2 = new SingleButton("Two", true) {
            @Override
            public boolean needsShow() {
                return step < 2;
            }
        };
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print("TWO pressed");
                step++;
                manager.updateButtonVisibility();
            }
        });
        b2.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
        b2.setVisible(false);
        ComponentTools.lockComponentSize(b2, BUTTON_DIM);
        manager.addButton(b2);
        box.add(b2);

        manager.updateButtonVisibility();

        Box outerBox = Box.createVerticalBox();
        outerBox.add(new JLabel(LINE));
        outerBox.add(box);
        outerBox.add(new JLabel(LINE));


        UnitTestFrame frame = new UnitTestFrame();
        frame.basePanel().add(outerBox);
        frame.setVisible(true);
        napForAWhile(20);
    }
}
