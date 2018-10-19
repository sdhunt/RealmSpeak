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
 * You should have received a copy of the GNU General
 * Public License along with this program. If not, see
 *
 * http://www.gnu.org/licenses/
 */
package com.robin.general.swing;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A dialog that can be set up to request multiple values, using text and/or
 * combo box inputs.
 */
public class MultiQueryDialog extends AggressiveDialog {

    private static final Dimension MAX_SIZE = new Dimension(200, 25);

    private final Map<String, JTextComponent> textLookup = new HashMap<>();
    private final Map<String, JComboBox> comboLookup = new HashMap<>();
    private final ArrayList<JTextComponent> requiredText = new ArrayList<>();

    private final Box layoutBox;
    private final JButton okay;
    private final JButton cancel;
    private final UniformLabelGroup group = new UniformLabelGroup();

    private boolean okayPressed = false;

    /**
     * Creates the initial dialog with okay and cancel buttons.
     *
     * @param parent the parent frame
     * @param title  the dialog title
     */
    public MultiQueryDialog(JFrame parent, String title) {
        super(parent, title);
        layoutBox = Box.createVerticalBox();
        layoutBox.add(Box.createVerticalGlue());
        Box controls = Box.createHorizontalBox();
        controls.add(Box.createHorizontalGlue());

        cancel = addControlButton(controls, "Cancel", ev -> {
            okayPressed = false;
            close();
        });
        okay = addControlButton(controls, "Okay", ev -> {
            okayPressed = true;
            close();
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(layoutBox, BorderLayout.CENTER);
        getContentPane().add(controls, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(okay);
        setModal(true);
    }

    private JButton addControlButton(Box controls, String text,
                                     ActionListener callback) {
        JButton btn = new JButton(text);
        btn.addActionListener(callback);
        controls.add(btn);
        controls.add(Box.createHorizontalGlue());
        return btn;
    }

    private void updateSize() {
        int width = group.getMaxPixelWidth() + 200;
        int height = (layoutBox.getComponentCount() + 3) * 25;
        setSize(width, height);
    }

    private void updateButtons() {
        boolean allClear = true;
        for (JTextComponent tc : requiredText) {
            if (tc.getText().trim().length() == 0) {
                allClear = false;
                break;
            }
        }
        okay.setEnabled(allClear);
    }

    private void close() {
        setVisible(false);
    }

    /**
     * Adds a component line to the dialog, using the given label.
     *
     * @param label     the label
     * @param component the component
     */
    private void addComponent(String label, JComponent component) {
        component.setMaximumSize(MAX_SIZE);
        int count = layoutBox.getComponentCount();
        Box line = group.createLabelLine(label);
        line.add(component);
        layoutBox.add(line, count - 1); // adds the line before the glue
        updateSize();
        updateButtons();
    }

    /**
     * Adds an optional text input field to the dialog.
     *
     * @param key           the lookup key
     * @param label         the field label
     * @param textComponent the text component
     */
    public void addQueryLine(String key, String label, JTextComponent textComponent) {
        this.addQueryLine(key, label, textComponent, false);
    }

    /**
     * Adds a text input field to the dialog.
     *
     * @param key           the lookup key
     * @param label         the field label
     * @param textComponent the text component
     * @param requireInput  true if the text input is required (not optional)
     */
    public void addQueryLine(String key, String label,
                             JTextComponent textComponent, boolean requireInput) {
        if (requireInput) {
            textComponent.addCaretListener(ev -> updateButtons());
            requiredText.add(textComponent);
        }
        addComponent(label, textComponent);
        textLookup.put(key, textComponent);
    }

    /**
     * Adds a combo box input field to the dialog.
     *
     * @param key      the lookup key
     * @param label    the field label
     * @param comboBox the combo box component
     */
    public void addQueryLine(String key, String label, JComboBox comboBox) {
        addComponent(label, comboBox);
        comboLookup.put(key, comboBox);
    }

    /**
     * Returns the (trimmed) text of the text input field with the given key.
     * If no such component is registered with the dialog, null is returned.
     *
     * @param key the lookup key
     * @return the text value of the field
     */
    public String getText(String key) {
        JTextComponent tc = textLookup.get(key);
        return tc == null ? null : tc.getText().trim();
    }

    /**
     * Returns the selected object of the combo box field with the given key.
     * If no such component is registered with the dialog, null is returned.
     *
     * @param key the lookup key
     * @return the selected object
     */
    public Object getComboChoice(String key) {
        JComboBox cbox = comboLookup.get(key);
        return cbox == null ? null : cbox.getSelectedItem();
    }

    /**
     * Returns true if the Okay button was pressed.
     *
     * @return true if Okay was pressed
     */
    public boolean saidOkay() {
        return okayPressed;
    }
}
