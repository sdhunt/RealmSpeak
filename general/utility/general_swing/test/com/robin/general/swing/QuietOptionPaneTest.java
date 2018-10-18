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
import java.awt.image.BufferedImage;

/**
 * Unit tests for {@link QuietOptionPane}.
 */
public class QuietOptionPaneTest extends AbstractGraphicsTest {

    private static final String MESSAGE = "This is\na test";
    private static final String SILENCE = "Don't ask me again!";
    private static final String WITH_ICON = "With an Icon";
    private static final String WITHOUT_ICON = "Without an Icon";

    private static final int ICON_SIZE = 32;

    private Icon someIcon() {
        BufferedImage bi = createBufferedImage(ICON_SIZE);
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(Color.yellow);
        g2.fillRect(0, 0, ICON_SIZE, ICON_SIZE);
        g2.setColor(Color.red);
        g2.fillOval(4, 4, 24, 24);
        return new ImageIcon(bi);
    }

    @Test
    @Ignore(FRAME)
    public void originalMain() {
        title("Original Main");
        do {
            QuietOptionPane.showMessageDialog(null, MESSAGE, SILENCE, false);
        } while (!QuietOptionPane.lastWasSilenced());
    }

    @Test
    @Ignore(FRAME)
    public void alternateMain() {
        title("Alternate Main");
        do {
            QuietOptionPane.showConfirmDialog(null, MESSAGE, SILENCE, false);
        } while (!QuietOptionPane.lastWasSilenced());
    }

    @Test
    @Ignore(FRAME)
    public void yetAnotherMain() {
        title("Yet another main");
        JOptionPane.showConfirmDialog(null, MESSAGE);

        do {
            QuietOptionPane.showMessageDialog(null, MESSAGE, SILENCE, false);
        } while (!QuietOptionPane.lastWasSilenced());
    }

    @Test
    @Ignore(FRAME)
    public void defaultOnTrue() {
        title("Default ON");
        // default ON true will set the "Don't tell me again" checkbox CHECKED
        do {
            QuietOptionPane.showMessageDialog(null, MESSAGE, SILENCE, true);
        } while (!QuietOptionPane.lastWasSilenced());
    }

    @Test
    @Ignore(FRAME)
    public void confirmWithAnIcon() {
        title("Confirm With An Icon");
        do {
            QuietOptionPane.showConfirmDialog(null, MESSAGE, WITH_ICON,
                                              JOptionPane.WARNING_MESSAGE,
                                              JOptionPane.OK_CANCEL_OPTION,
                                              someIcon(), SILENCE, false);
        } while (!QuietOptionPane.lastWasSilenced());
    }

    @Test
    @Ignore(FRAME)
    public void confirmWithoutAnIcon() {
        title("Confirm Without An Icon");
        do {
            QuietOptionPane.showConfirmDialog(null, MESSAGE, WITHOUT_ICON,
                                              JOptionPane.WARNING_MESSAGE,
                                              JOptionPane.OK_CANCEL_OPTION,
                                              null, SILENCE, false);
        } while (!QuietOptionPane.lastWasSilenced());
    }

    @Test
    @Ignore(FRAME)
    public void messageWithAnIcon() {
        title("Message With An Icon");
        do {
            QuietOptionPane.showMessageDialog(null, MESSAGE, WITH_ICON,
                                              JOptionPane.WARNING_MESSAGE,
                                              someIcon(), SILENCE, false);
        } while (!QuietOptionPane.lastWasSilenced());
    }

    @Test
    @Ignore(FRAME)
    public void messageWithoutAnIcon() {
        title("Message Without An Icon");
        do {
            QuietOptionPane.showMessageDialog(null, MESSAGE, WITHOUT_ICON,
                                              JOptionPane.WARNING_MESSAGE,
                                              null, SILENCE, false);
        } while (!QuietOptionPane.lastWasSilenced());
    }
}
