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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for {@link ScrollLine}.
 */
public class ScrollLineTest extends AbstractGraphicsTest {

    private static final Font FONT = new Font("Arial", Font.BOLD, 20);
    private static final String URL = "http://foo.bar";

    private ScrollLine line;

    @Test
    @Ignore(FRAME)
    public void basic() {
        title("Basic");
        BufferedImage bi = createBufferedImage();
        Graphics2D g2 = bi.createGraphics();
        fillImage(g2, Color.lightGray);

        line = new ScrollLine("Basic");
        line.draw(g2, 10, 25);

        line = new ScrollLine("Scrolling", FONT, Color.orange);
        line.draw(g2, 10, 50);

        line = new ScrollLine("Hello, Sailor", FONT, Color.GREEN.darker(),
                              Color.BLACK.brighter(), 1);
        line.draw(g2, 10, 75);

        line = new ScrollLine("Wassup??", FONT, Color.green, null, 0,
                              SwingConstants.CENTER, URL);
        line.draw(g2, 10, 100);

        // while we are working on the URL line...
        assertThat(line.linkAtPoint(p(20, 99)), is(URL));
        assertThat(line.linkAtPoint(p(20, 74)), is((String) null));

        UnitTestFrame frame = new UnitTestFrame(300, 240);
        ImageIcon ii = new ImageIcon(bi);
        frame.basePanel().add(new JLabel(ii));
        frame.setVisible(true);
        napForAWhile();
    }
}
