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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for {@link SingleButton}.
 */
public class SingleButtonTest extends AbstractGraphicsTest {

    @Test
    @Ignore(FRAME)
    public void basic() {
        title("Basic");

        Box box = Box.createHorizontalBox();

        // Not quite sure what we can test here, really...

        SingleButton button = new SingleButton("Some Text", true) {
            @Override
            public boolean needsShow() {
                return false;
            }
        };

        assertThat(button.isMandatory(), is(true));
        assertThat(button.needsShow(), is(false));

        box.add(button);

        UnitTestFrame frame = new UnitTestFrame();
        frame.basePanel().add(box);
        frame.setVisible(true);
        napForAWhile();
    }
}
