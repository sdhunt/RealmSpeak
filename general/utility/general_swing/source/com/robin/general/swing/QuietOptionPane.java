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
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see
 *
 * http://www.gnu.org/licenses/
 */
package com.robin.general.swing;

import javax.swing.*;
import java.awt.*;

/**
 * A utility class to create JOptionPane dialogs that can be silenced.
 */
public class QuietOptionPane {

    private static final Font FONT = UIManager.getFont("Label.font");
    private static final String CENTER = "Center";
    private static final String SOUTH = "South";
    private static final int BORDER_GAP = 10;
    private static final String CONFIRM_TITLE = "Select an Option";
    private static final String EMPTY = "";

    private final Object message;
    private final String silencingString;

    private JCheckBox silencingOption;

    private QuietOptionPane(Object message, String silencingString) {
        this.message = message;
        this.silencingString = silencingString;
    }

    private boolean isSilenced() {
        return silencingOption.isSelected();
    }

    // Creates a panel to be displayed as the "message"
    private JPanel getPanel(boolean defaultOn) {
        JPanel panel = new JPanel(new BorderLayout(BORDER_GAP, BORDER_GAP));
        if (message instanceof Component) {
            panel.add((Component) message, CENTER);
        } else {
            JTextArea area = new JTextArea(message.toString());
            area.setEditable(false);
            area.setOpaque(false);
            area.setFont(FONT);
            panel.add(area, CENTER);
        }
        silencingOption = new JCheckBox(silencingString, defaultOn);
        panel.add(silencingOption, SOUTH);
        return panel;
    }

    private static boolean lastWasSilenced;

    /**
     * Returns true if the last quiet option pane to be dismissed was
     * marked for silence.
     *
     * @return true if last quiet option pane was silenced
     */
    public static boolean lastWasSilenced() {
        return lastWasSilenced;
    }

    /**
     * Presents a silenceable confirmation dialog to the user, with
     * YES/NO/CANCEL options.
     *
     * @param parentComponent dialog's parent frame
     * @param message         the message (object) to display
     * @param silencingString label for silencing checkbox
     * @param defaultOn       silencing checkbox initial state
     * @return the choice selected by the user
     */
    public static int showConfirmDialog(Component parentComponent, Object message,
                                        String silencingString, boolean defaultOn)
            throws HeadlessException {
        return showConfirmDialog(parentComponent, message, CONFIRM_TITLE,
                                 JOptionPane.YES_NO_CANCEL_OPTION,
                                 JOptionPane.QUESTION_MESSAGE, null,
                                 silencingString, defaultOn);
    }

    /**
     * Presents a silenceable confirmation dialog to the user.
     *
     * @param parentComponent dialog's parent frame
     * @param message         the message (object) to display
     * @param title           dialog title
     * @param optionType      constant designating the option type
     * @param silencingString label for silencing checkbox
     * @param defaultOn       silencing checkbox initial state
     * @return the choice selected by the user
     */
    public static int showConfirmDialog(Component parentComponent, Object message,
                                        String title, int optionType,
                                        String silencingString, boolean defaultOn)
            throws HeadlessException {
        return showConfirmDialog(parentComponent, message, title, optionType,
                                 JOptionPane.QUESTION_MESSAGE, null,
                                 silencingString, defaultOn);
    }

    /**
     * Presents a silenceable confirmation dialog to the user.
     *
     * @param parentComponent dialog's parent frame
     * @param message         the message (object) to display
     * @param title           dialog title
     * @param optionType      constant designating the available options
     * @param messageType     constant designating message type (error, warn, etc.)
     * @param silencingString label for silencing checkbox
     * @param defaultOn       silencing checkbox initial state
     * @return the choice selected by the user
     */
    public static int showConfirmDialog(Component parentComponent, Object message,
                                        String title, int optionType,
                                        int messageType, String silencingString,
                                        boolean defaultOn)
            throws HeadlessException {
        return showConfirmDialog(parentComponent, message, title, optionType,
                                 messageType, null, silencingString, defaultOn);
    }

    /**
     * Presents a silenceable confirmation dialog to the user.
     * Note that if the icon parameter is not null, that icon will be used
     * in place of the standard message type icon.
     *
     * @param parentComponent dialog's parent frame
     * @param message         the message (object) to display
     * @param title           dialog title
     * @param optionType      constant designating the available options
     * @param messageType     constant designating message type (error, warn, etc.)
     * @param icon            the icon to display in the dialog
     * @param silencingString label for silencing checkbox
     * @param defaultOn       silencing checkbox initial state
     * @return the choice selected by the user
     */
    public static int showConfirmDialog(Component parentComponent, Object message,
                                        String title, int optionType,
                                        int messageType, Icon icon,
                                        String silencingString, boolean defaultOn)
            throws HeadlessException {
        QuietOptionPane qop = new QuietOptionPane(message, silencingString);
        int ret = JOptionPane.showConfirmDialog(parentComponent,
                                                qop.getPanel(defaultOn), title,
                                                optionType, messageType, icon);
        lastWasSilenced = qop.isSilenced();
        return ret;
    }

    /**
     * Presents a silenceable message (informational) to the user.
     *
     * @param parentComponent dialog's parent frame
     * @param message         the message (object) to display
     * @param silencingString label for silencing checkbox
     * @param defaultOn       silencing checkbox initial state
     */
    public static void showMessageDialog(Component parentComponent, Object message,
                                         String silencingString, boolean defaultOn)
            throws HeadlessException {
        showMessageDialog(parentComponent, message, EMPTY,
                          JOptionPane.INFORMATION_MESSAGE, null,
                          silencingString, defaultOn);
    }

    /**
     * Presents a silenceable message to the user.
     * Note that if the icon parameter is not null, that icon will be used
     * in place of the standard message type icon.
     *
     * @param parentComponent dialog's parent frame
     * @param message         the message (object) to display
     * @param title           dialog title
     * @param messageType     constant designating message type (error, warn, etc.)
     * @param icon            the icon to display in the dialog
     * @param silencingString label for silencing checkbox
     * @param defaultOn       silencing checkbox initial state
     */
    public static void showMessageDialog(Component parentComponent, Object message,
                                         String title, int messageType, Icon icon,
                                         String silencingString, boolean defaultOn)
            throws HeadlessException {
        QuietOptionPane qop = new QuietOptionPane(message, silencingString);
        JOptionPane.showMessageDialog(parentComponent, qop.getPanel(defaultOn),
                                      title, messageType, icon);
        lastWasSilenced = qop.isSilenced();
    }
}
