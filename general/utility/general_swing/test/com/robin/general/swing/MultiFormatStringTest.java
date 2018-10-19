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
import java.awt.image.BufferedImage;

/**
 * Unit tests for {@link MultiFormatString}.
 */
public class MultiFormatStringTest extends AbstractGraphicsTest {

    private static final Color PAPER = new Color(0xee, 0xee, 0xee);
    private static final Color INK = new Color(0x22, 0x55, 0x88);
    private static final Color INK2 = new Color(0x88, 0x55, 0x22);

    private static final String SAMPLE =
            "To <b>boldly</b> <i>italicize</i> wherever " +
            "the <b>mood</b> may take you.";

    private static final String QUOTE =
            "Oh my god! It's <b>full</b> of <i>stars...</i>";

    private MultiFormatString str;

    @Test
    public void basic() {
        title("Basic");
        BufferedImage bi = createBufferedImage();
        Graphics2D g2 = bi.createGraphics();
        fillImage(g2, PAPER);
        g2.setColor(INK);

        int xoff = 20;
        int yoff = 20;
        int spacer = 8;
        int width = 100;

        str = new MultiFormatString(SAMPLE);
        str.setFontAttributes("Arial", 12);
        int h = str.draw(g2, xoff, yoff, width);
        print(str);

        str = new MultiFormatString(QUOTE);
        str.setFontAttributes("Arial", 12);
        g2.setColor(INK2);
        str.draw(g2, xoff, yoff + spacer + h, width);

        UnitTestFrame frame = new UnitTestFrame();
        ImageIcon ii = new ImageIcon(bi);
        frame.basePanel().add(new JLabel(ii));
        frame.setVisible(true);
        napForAWhile(8);
    }
}
