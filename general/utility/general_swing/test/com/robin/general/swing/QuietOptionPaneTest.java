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

/**
 * Unit tests for {@link QuietOptionPane}.
 */
public class QuietOptionPaneTest extends AbstractGraphicsTest {

    @Test
    public void originalMain() {
        title("Original Main");
        do {
            QuietOptionPane.showMessageDialog(null, "This is\na test", "Don't ask me again!", false);
        } while (!QuietOptionPane.isLastWasSilenced());
    }

    @Test
    public void alternateMain() {
        title("Alternate Main");
        do {
            QuietOptionPane.showConfirmDialog(null, "This is\na test", "Don't ask me again!", false);
        } while (!QuietOptionPane.isLastWasSilenced());
    }

    @Test
    public void yetAnotherMain() {
        title("Yet another main");
        JOptionPane.showConfirmDialog(null, "This is\na test");

        do {
            QuietOptionPane.showMessageDialog(null, "This is\na test", "Don't ask me again!", false);
        } while (!QuietOptionPane.isLastWasSilenced());
    }
}
