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
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Unit tests for {@link ScrollingText}.
 */
public class ScrollingTextTest extends AbstractGraphicsTest {

    private class TimerThing implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("..tick..");
        }
    }

    private class HyperListen implements HyperlinkListener {
        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            System.out.println("URL: " + e.getURL());
        }
    }

    @Test
    @Ignore(SLOW)
    public void timerHuh() {
        title("Timer Huh");
        Timer timer = new Timer(100, new TimerThing());
        timer.start();
        napForAWhile();
    }

    @Test
    @Ignore(FRAME)
    public void basic() {
        title("Basic");
        String fontName = "Dialog";
        Font header = new Font(fontName, Font.BOLD, 22);
        Font listing = new Font(fontName, Font.BOLD, 12);
        Font url = new Font(fontName, Font.PLAIN, 10);
        Insets insets = new Insets(32, 0, 30, 0);
        ImageIcon parchment = IconFactory.findIcon("images/parchment.png");

        ScrollingText scrolly = new ScrollingText(parchment, insets);

        scrolly.setFont(url);
        scrolly.setForeground(Color.blue);

        scrolly.addLine(new ScrollLine("Coding", header, Color.green, Color.black, 2));
        scrolly.addLine(new ScrollLine("Dave One", listing, Color.black));
        scrolly.addLine(new ScrollLine("Mark Twosome", listing, Color.black, null, 0, SwingConstants.CENTER, "http://www.google.com"));
        scrolly.addLine(new ScrollLine("Fred Threebay", listing, Color.black));
        scrolly.addLine(new ScrollLine("Steve Fourplay", listing, Color.black));
        scrolly.addLine(new ScrollLine("Gary Fivish", listing, Color.black));
        scrolly.addLine(new ScrollLine());
        scrolly.addLine(new ScrollLine("Testing", header, Color.green, Color.black, 2));
        scrolly.addLine(new ScrollLine("Dave One", listing, Color.black));
        scrolly.addLine(new ScrollLine("Mark Twosome", listing, Color.black));
        scrolly.addLine(new ScrollLine("Fred Threebay", listing, Color.black));
        scrolly.addLine(new ScrollLine("Steve Fourplay", listing, Color.black, null, 0, SwingConstants.CENTER, "http://www.google.com"));
        scrolly.addLine(new ScrollLine("Gary Fivish", listing, Color.black));

        scrolly.addHyperlinkListener(new HyperListen());

        UnitTestFrame frame = new UnitTestFrame(380, 450);
        frame.basePanel().add(scrolly);
        frame.setVisible(true);

        // Note: need to make frame visible, because we check isDisplayable()
        scrolly.start();

        napForAWhile(30);
    }
}
