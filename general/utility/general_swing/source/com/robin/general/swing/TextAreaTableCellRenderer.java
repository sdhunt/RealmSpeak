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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * A table cell renderer for text areas.
 */
public class TextAreaTableCellRenderer extends JTextArea
        implements TableCellRenderer {

    private final DefaultTableCellRenderer modelRenderer =
            new DefaultTableCellRenderer();

    /**
     * Default constructor.
     */
    public TextAreaTableCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object obj, boolean isSelected, boolean hasFocus,
            int row, int column) {
        modelRenderer.getTableCellRendererComponent(table, obj, isSelected,
                                                    hasFocus, row, column);
        setForeground(modelRenderer.getForeground());
        setBackground(modelRenderer.getBackground());
        setBorder(modelRenderer.getBorder());
        setFont(modelRenderer.getFont());
        setText(modelRenderer.getText());
        return this;
    }
}