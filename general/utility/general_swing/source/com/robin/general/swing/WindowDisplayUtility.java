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
 * This class will guarantee that a window is displayed on the GUI thread,
 * where it belongs.
 */
// Not used anywhere in the codebase
@Deprecated
public class WindowDisplayUtility implements Runnable {
    private Window window;

    private WindowDisplayUtility(Window window) {
        this.window = window;
    }

    public void run() {
        window.setVisible(true);
    }

    public static void displayWindow(Window window) {
        WindowDisplayUtility util = new WindowDisplayUtility(window);
        SwingUtilities.invokeLater(util);
        window.setVisible(true);
    }
}