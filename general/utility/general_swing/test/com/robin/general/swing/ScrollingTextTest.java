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

    private static final String FONT_NAME = "Dialog";

    private static final Font HEADER_FONT = new Font(FONT_NAME, Font.BOLD, 22);
    private static final Font LISTING_FONT = new Font(FONT_NAME, Font.BOLD, 12);
    private static final Font URL_FONT = new Font(FONT_NAME, Font.PLAIN, 10);
    private static final Color URL_COLOR = Color.blue;

    private static final ImageIcon PARCHMENT =
            IconFactory.findIcon("images/parchment.png");
    private static final Insets PARCHMENT_INSETS = new Insets(32, 0, 30, 0);


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

    private ScrollLine slHeader(String title) {
        return new ScrollLine(title, HEADER_FONT, Color.green, Color.black, 2);
    }

    private ScrollLine slListing(String text) {
        return new ScrollLine(text, LISTING_FONT, Color.black);
    }

    private ScrollLine slLink(String text, String url) {
        return new ScrollLine(text, LISTING_FONT, Color.black, null, 0,
                              SwingConstants.CENTER, url);
    }

    @Test
    @Ignore(FRAME)
    public void parchmentScroller() {
        title("Parchment Scroller");

        ScrollingText scroller = new ScrollingText(PARCHMENT, PARCHMENT_INSETS);

        scroller.setFont(URL_FONT);
        scroller.setForeground(URL_COLOR);

        scroller.addLine(slHeader("Coding"));
        scroller.addLine(slListing("Dave One"));
        scroller.addLine(slLink("Mark Twosome", "http://google.com/TWO"));
        scroller.addLine(slListing("Fred Threebay"));
        scroller.addLine(slListing("Steve Fourplay"));
        scroller.addLine(slListing("Gary Fivish"));

        scroller.addLine(new ScrollLine());

        scroller.addLine(slHeader("Testing"));
        scroller.addLine(slListing("Dave One"));
        scroller.addLine(slListing("Mark Twosome"));
        scroller.addLine(slListing("Fred Threebay"));
        scroller.addLine(slLink("Steve Fourplay", "http://www.google.com/FOUR"));
        scroller.addLine(slListing("Gary Fivish"));

        scroller.addHyperlinkListener(new HyperListen());

        UnitTestFrame frame = new UnitTestFrame(380, 450);
        frame.basePanel().add(scroller);

        // Note: need to make frame visible FIRST, before starting the scroller
        //       because we check isDisplayable() and abort if this is false.
        frame.setVisible(true);
        scroller.start();

        napForAWhile(30);
    }
}
