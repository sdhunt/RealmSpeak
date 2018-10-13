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

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Utility class for adding copy functionality to a JTable.
 * Giving the table as an argument to {@link #addCopyActionToTable} binds
 * Ctrl-c (and Command-c on Mac and Ctrl-Insert on PC) to the table
 * (when selected) to copy the selected row(s)/column(s)/cell(s), as specified,
 * to the system Clipboard.
 * Ability to toggle copying of column headers in addition to the data in the
 * selected rows/columns/cells.
 */
// Does not appear to be used anywhere in the code base!
@Deprecated
public class TableCopy {

    private static final String E_NO_COPY_ACTION =
            "Copy Action has not been added to the JTable specified. " +
                    "Invoke TableCopy.addCopyActionToTable on table.";


    private static final String COPY = "tablecopy_copy";

    private static final TableCopy soleInstance = new TableCopy();

    // no instantiation
    private TableCopy() {
    }

    /**
     * Adds copy action to the table, so Ctrl-c (and Command-c on Mac and
     * Ctrl-Insert on PC) will copy selected row(s)/column(s)/cell(s).
     *
     * @param table         The table
     * @param copyEntireRow if true, the contents of entire rows containing
     *                      selected cells are copied
     * @param copyEntireCol if true, the contents of entire cols containing
     *                      selected cells are copied
     * @param copyHeaders   if true, the column headers will be copied as well
     *                      as the data selected in the table. This can be
     *                      changed after the fact using the setCopyHeaders method.
     */
    public static void addCopyActionToTable(JTable table, boolean copyEntireRow,
                                            boolean copyEntireCol, boolean copyHeaders) {
        if (table == null) {
            throw new NullPointerException("Can't add Action to a null table");
        }
        KeyStroke copyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK);
        table.getInputMap().put(copyKeyStroke, COPY);
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
            KeyStroke macCopyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, menuKeyMask);
            table.getInputMap().put(macCopyKeyStroke, COPY);
        }
        if (osName.contains("windows")) {
            KeyStroke windozCopyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,
                                                                   InputEvent.CTRL_MASK);
            table.getInputMap().put(windozCopyKeyStroke, COPY);
        }
        table.getActionMap().put(COPY, soleInstance.new TableCopyAction(
                COPY, table, copyEntireRow, copyEntireCol, copyHeaders) {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                soleInstance.copy(actionTable, actionCopyEntireRow,
                                  actionCopyEntireCol, actionCopyHeaders);
            }
        });
    }

    /**
     * Sets whether or not column headers will be copied along with the data
     * (headers will be first line).
     * Table must have had {@link TableCopy#addCopyActionToTable} invoked.
     *
     * @param table       The table
     * @param copyHeaders if true, the column headers will be copied as well
     *                    as the data selected in the table
     * @throws NullPointerException     If table is null
     * @throws IllegalArgumentException If table doesn't have the appropriate
     *                                  copy Action
     */
    public static void setCopyHeaders(JTable table, boolean copyHeaders) {
        Action action = table.getActionMap().get(COPY);
        if (action == null) {
            throw new IllegalArgumentException(E_NO_COPY_ACTION);
        }

        ((TableCopyAction) action).setCopyHeaders(copyHeaders);
    }

    /**
     * Causes the selected rows in a table to be copied to the System Clipboard.
     * Allows invoking via some other means than the keyboard
     * keys bound via the {@link #addCopyActionToTable} method.
     * Table must have had {@link TableCopy#addCopyActionToTable} invoked.
     *
     * @param table The table.
     * @throws NullPointerException     If table is null
     * @throws IllegalArgumentException If table doesn't have the appropriate
     *                                  copy Action
     */
    public static void invokeCopy(JTable table) {
        Action action = table.getActionMap().get(COPY);
        if (action == null) {
            throw new IllegalArgumentException(E_NO_COPY_ACTION);
        }

        action.actionPerformed(
                new ActionEvent(table, ActionEvent.ACTION_PERFORMED, COPY)
        );
    }

    /**
     * Copies the contents of the selected range of table cells to the
     * system clipboard.
     *
     * @param table         The JTable
     * @param copyEntireRow if true, the contents of entire rows containing
     *                      selected cells are copied
     * @param copyEntireCol if true, the contents of entire cols containing
     *                      selected cells are copied
     * @param copyHeaders   if true, the column headers will be copied as well
     *                      as the data selected in the table
     */
    private void copy(JTable table, boolean copyEntireRow, boolean copyEntireCol,
                      boolean copyHeaders) {
        if (table == null || table.getModel() == null) {
            return;
        }

        TableModel model = table.getModel();
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        int[] selectedRows;
        int[] selectedCols;

        if (copyEntireCol) {
            int rows = model.getRowCount();
            selectedRows = new int[rows];
            for (int i = 0; i < rows; i++) {
                selectedRows[i] = i;
            }
        } else {
            selectedRows = table.getSelectedRows();
        }

        if (copyEntireRow) {
            int cols = model.getColumnCount();
            selectedCols = new int[cols];
            for (int i = 0; i < cols; i++) {
                selectedCols[i] = table.convertColumnIndexToModel(i);
            }
        } else {
            selectedCols = convertColumnIndicesToModel(table, table.getSelectedColumns());
        }

        Object[][] values = getValuesAt(model, selectedRows, selectedCols);

        // Send data to clipboard as a string with tabs delimiting the columns and
        // newlines delimiting the rows.
        StringBuilder data = new StringBuilder(10 * selectedRows.length * selectedCols.length);
        if (copyHeaders) {
            for (int i = 0; i < selectedCols.length; i++) {
                data.append(model.getColumnName(selectedCols[i]));
                if (i < selectedCols.length - 1) {
                    data.append("\t");
                }
            }
            data.append("\n");
        }
        for (Object[] value : values) {
            int lastCol = value.length - 1;
            for (int j = 0; j < lastCol; j++) {
                data.append(value[j] != null ? value[j] : "");
                data.append("\t");
            }
            data.append(value[lastCol] != null ? value[lastCol] : "");
            data.append("\n");
        }

        StringSelection contents = new StringSelection(data.toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(contents, contents);
    }

    /**
     * Converts an array of column indices in view coordinates to the
     * corresponding array of column indices in model coordinates.
     *
     * @param viewColumns The column indices in view coordinates
     * @param table       The JTable
     * @return The column indices in model coordinates
     * @throws NullPointerException If viewColumns is null
     */
    private int[] convertColumnIndicesToModel(JTable table, int[] viewColumns) {
        int[] modelColumns = new int[viewColumns.length];

        for (int i = 0; i < viewColumns.length; i++) {
            modelColumns[i] = table.convertColumnIndexToModel(viewColumns[i]);
        }

        return modelColumns;
    }

    /**
     * Returns the matrix of cell values at the coordinates specified by arrays of
     * row and column indices. Note that while the returned matrix is contiguous,
     * the rows and columns from which the values are taken do not have to be.<p>
     * <p>
     * In general, if v is the returned matrix, <code>v[i][j]</code> = cell value at
     * <code>(rows[i], cols[j])</code>.<p>
     *
     * @param rows Array of row indices
     * @param cols Array of column indices
     * @return Matrix of values from the specified cells
     * @throws ArrayIndexOutOfBoundsException If any of the array indices
     *                                        are out of bounds
     * @throws NullPointerException           If either <code>rows</code> or
     *                                        <code>cols</code> is null
     */
    private Object[][] getValuesAt(TableModel tableModel, int[] rows, int[] cols) {
        Object[][] values = new Object[rows.length][cols.length];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < cols.length; j++) {
                values[i][j] = tableModel.getValueAt(rows[i], cols[j]);
            }
        }
        return values;
    }

    /**
     * Version of AbstractAction that has local variables to allow a table
     * to "remember" how its copy functionality should behave.
     * Also has method for changing whether or not column headers are copied,
     * on the fly.
     */
    private abstract class TableCopyAction extends AbstractAction {
        JTable actionTable;
        boolean actionCopyEntireRow;
        boolean actionCopyEntireCol;
        boolean actionCopyHeaders;

        TableCopyAction(String name, JTable actionTable,
                        boolean actionCopyEntireRow,
                        boolean actionCopyEntireCol, boolean actionCopyHeaders) {
            super(name);
            this.actionTable = actionTable;
            this.actionCopyEntireRow = actionCopyEntireRow;
            this.actionCopyEntireCol = actionCopyEntireCol;
            this.actionCopyHeaders = actionCopyHeaders;
        }

        /**
         * Sets whether the column headers will be copied.
         */
        void setCopyHeaders(boolean actionCopyHeaders) {
            this.actionCopyHeaders = actionCopyHeaders;
        }
    }
}
