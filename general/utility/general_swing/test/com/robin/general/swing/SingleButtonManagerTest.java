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
import java.awt.event.ActionListener;

/**
 * Unit tests for {@link SingleButtonManager}.
 */
public class SingleButtonManagerTest extends AbstractGraphicsTest {

    private static final Dimension BUTTON_DIM = new Dimension(80, 24);
    private static final String LINE = ">-<";

    private int step = 0;

    private void finishUpButton(Box box, SingleButtonManager manager,
            SingleButton button, ActionListener al) {
        button.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
        button.setVisible(false);
        ComponentTools.lockComponentSize(button, BUTTON_DIM);
        button.addActionListener(al);
        manager.addButton(button);
        box.add(button);
    }

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
        // we can inline the action listener as a lambda function...
        finishUpButton(box, manager, b1, ev -> {
            print("ONE Pressed");
            step++;
            manager.updateButtonVisibility();
        });

        // second button...
        SingleButton b2 = new SingleButton("Two", true) {
            @Override
            public boolean needsShow() {
                return step < 2;
            }
        };
        finishUpButton(box, manager, b2, ev -> {
            print("TWO pressed");
            step++;
            manager.updateButtonVisibility();
        });

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
