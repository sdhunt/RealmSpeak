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
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see
 *
 * http://www.gnu.org/licenses/
 */
package com.robin.general.swing;

import javax.swing.*;

/**
 * Abstract base class for enhanced JButton which includes a "mandatory"
 * designation.
 */
public abstract class SingleButton extends JButton {

    private boolean mandatory;

    /**
     * Subclasses should implement this method to indicate when the
     * button needs to be shown.
     *
     * @return true, if the button should be shown
     */
    public abstract boolean needsShow();

    /**
     * Constructs a single button with the given text, and an indication of
     * whether the button is mandatory.
     *
     * @param in the button text
     * @param mandatory mandatory designation
     */
    public SingleButton(String in, boolean mandatory) {
        super(in);
        this.mandatory = mandatory;
    }

    /**
     * Returns the mandatory designation of this button.
     *
     * @return the mandatory designation
     */
    public boolean isMandatory() {
        return mandatory;
    }
}
