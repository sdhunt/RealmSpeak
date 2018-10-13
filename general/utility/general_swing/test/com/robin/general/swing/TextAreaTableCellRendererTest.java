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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for {@link TextAreaTableCellRenderer}.
 */
public class TextAreaTableCellRendererTest extends AbstractGraphicsTest {

    private static final int LABEL = 0;
    private static final int TEXT = 1;
    private static final int N_COLS = 2;

    private class TestTableRow {
        private final String[] data = new String[N_COLS];

        private TestTableRow(String label, String text) {
            data[LABEL] = label;
            data[TEXT] = text;
        }

        Object getValueFromCol(int columnIndex) {
            return data[columnIndex];
        }
    }

    private class TestTableModel extends AbstractTableModel {
        private final String[] HEADERS = {"Label", "Text Description"};

        List<TestTableRow> rows = new ArrayList<>();

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return N_COLS;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return rows.get(rowIndex).getValueFromCol(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            return HEADERS[column];
        }

        // allow us to add rows to the table
        void addRow(String label, String text) {
            TestTableRow row = new TestTableRow(label, text);
            int newRowIndex = rows.size();
            rows.add(row);
            fireTableRowsInserted(newRowIndex, newRowIndex);
        }
    }

    private class MyLabelRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row, int column) {
            int align = JLabel.LEFT;
            Color fg = Color.RED.brighter();

            if (isSelected) {
                align = JLabel.RIGHT;
                fg = Color.YELLOW;
                setBackground(table.getSelectionBackground().brighter());
            }
            setOpaque(isSelected);

            setText((String) value);
            setVerticalAlignment(JLabel.TOP);
            setHorizontalAlignment(align);
            setForeground(fg);

            return this;
        }
    }

    @Test
    @Ignore(FRAME)
    public void basic() {
        /*
         * Some fun building a table, testing the TextAreaTableCellRenderer,
         * but also writing a little label renderer for ourselves.
         */

        title("Basic");
        TestTableModel tm = new TestTableModel();
        tm.addRow("One", "This is the first line, and it is very lengthy" +
                " with the intention of forcing some word wrap.");
        tm.addRow("Two", "This is the second description provided for the test.");
        tm.addRow("Three", "This is the third description - much shorter.");

        JTable table = new JTable(tm);
        table.setRowHeight(32);
        table.setIntercellSpacing(new Dimension(2, 2));

        MyLabelRenderer labelRenderer = new MyLabelRenderer();
        table.getColumnModel().getColumn(LABEL).setCellRenderer(labelRenderer);
        table.getColumnModel().getColumn(LABEL).setMaxWidth(50);

        TextAreaTableCellRenderer renderer = new TextAreaTableCellRenderer();
        table.getColumnModel().getColumn(TEXT).setCellRenderer(renderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(320, 120));

        UnitTestFrame frame = new UnitTestFrame(400, 200);
        frame.basePanel().add(scroll);
        frame.setVisible(true);
        napForAWhile(20);
    }
}
