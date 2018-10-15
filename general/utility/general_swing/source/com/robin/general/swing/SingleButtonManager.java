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

import java.util.ArrayList;

/**
 * Manages a collection of {@link SingleButton}s.
 */
public class SingleButtonManager {

    private boolean oneShowing;
    private boolean mandatoryShowing;
    private ArrayList<SingleButton> buttons;

    /**
     * Constructs an empty button manager.
     */
    public SingleButtonManager() {
        buttons = new ArrayList<SingleButton>();
    }

    /**
     * Adds the specified button to the manager's collection.
     *
     * @param button the button to add
     */
    public void addButton(SingleButton button) {
        buttons.add(button);
    }

    /**
     * Updates the button visibility, based on the state of the buttons
     * in the collection.
     */
    public void updateButtonVisibility() {
        oneShowing = false;
        mandatoryShowing = false;
        for (SingleButton button : buttons) {
            if (!oneShowing && button.needsShow()) {
                button.setVisible(true);
                oneShowing = true;
                mandatoryShowing = button.isMandatory();
            } else {
                button.setVisible(false);
            }
        }
    }

    /**
     * Returns true if a mandatory button is currently showing.
     *
     * @return true if mandatory button showing
     */
    public boolean hasMandatoryShowing() {
        return mandatoryShowing;
    }
}
