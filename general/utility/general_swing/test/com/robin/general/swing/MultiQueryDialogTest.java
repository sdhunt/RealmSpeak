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

/**
 * Unit tests for {@link MultiQueryDialog}.
 */
public class MultiQueryDialogTest extends AbstractGraphicsTest {

    @Test
    @Ignore(FRAME)
    public void originalMain() {
        title("Original Main");
        MultiQueryDialog dialog = new MultiQueryDialog(new JFrame(), "test");
        dialog.addQueryLine("name", "Name", new JTextField());
        dialog.addQueryLine("address", "Address", new JTextField());
        JComboBox<String> cb = new JComboBox<>();
        cb.addItem("Northern");
        cb.addItem("Southern");
        cb.addItem("Norweestum");
        dialog.addQueryLine("county", "County", cb);
        dialog.addOptionalQueryLine("nick", "Nickname", new JTextField());

        dialog.setVisible(true);

        if (dialog.saidOkay()) {
            print("   Name: [" + dialog.getText("name") + "]");
            print("Address: [" + dialog.getText("address") + "]");
            print(" County: [" + dialog.getComboChoice("county") + "]");
            print("   Nick: [" + dialog.getText("nick") + "]");
        } else {
            print("Dialog interaction was cancelled");
        }
    }
}
