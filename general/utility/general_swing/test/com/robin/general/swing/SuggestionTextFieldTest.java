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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests for {@link SuggestionTextField}.
 */
public class SuggestionTextFieldTest extends AbstractGraphicsTest {

    private static final String[] SUGGESTIONS = {
            // "DragonsLair",
            // "UndeadTown",
            // "DraconicTemple",
            // "Lost City",
            "Bubbles",
            "Bubbles 2",
            "Magic Flute",
            "Magic Spectacles",
    };

    private static final List<String> SUGGEST_LIST = Arrays.asList(SUGGESTIONS);

    @Test
    @Ignore(FRAME)
    public void originalMain() {
        title("Original Main");
        SuggestionTextField textField = new SuggestionTextField();
        ArrayList<String> myList = new ArrayList<>(SUGGEST_LIST);
        textField.setWords(myList);
        textField.setLineModeOn(true);
        JOptionPane.showMessageDialog(new JFrame(), textField);

        print("The selection chosen was: [%s]", textField.getText());
    }

}
