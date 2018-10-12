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

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * A JFrame that can be used in the unit tests of swing components.
 */
public class UnitTestFrame extends JFrame {

    private JPanel panel;

    /**
     * Creates a unit test frame of the specified dimensions.
     * Positions the frame in the center of the screen, and adds
     * a base panel.
     *
     * @param width frame width
     * @param height frame height
     */
    public UnitTestFrame(int width, int height) {
        super("Unit Test Frame");
        positionMe(width, height);
        addWindowListener(new WindowHandler());

        panel = new JPanel();
        panel.setBackground(Color.lightGray);
        getContentPane().add(panel);
    }

    /**
     * Returns a reference to the base panel.
     *
     * @return the base panel
     */
    public JPanel basePanel() {
        return panel;
    }

    private void positionMe(int width, int height) {
        setSize(width, height);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int ox = (screen.width - width) / 2;
        int oy = (screen.height - height) / 2;
        setLocation(ox, oy);
    }

    private class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.out.println("Closing Unit Test Frame");
        }
    }
}
