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
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * A dialog that requests multiple values.
 */
public class MultiQueryDialog extends AggressiveDialog {

    private Hashtable textComponents = new Hashtable();
    private Hashtable comboBoxes = new Hashtable();
    private ArrayList requiredInputComponents = new ArrayList();

    private Box layoutBox;
    private boolean okayPressed = false;

    protected JButton okay;
    protected JButton cancel;
    protected UniformLabelGroup group = new UniformLabelGroup();

    public MultiQueryDialog(JFrame parent, String title) {
        super(parent, title);
        layoutBox = Box.createVerticalBox();
        layoutBox.add(Box.createVerticalGlue());
        Box controls = Box.createHorizontalBox();
        controls.add(Box.createHorizontalGlue());
        cancel = new JButton("Cancel");
        cancel.addActionListener(ev -> {
            okayPressed = false;
            close();
        });
        controls.add(cancel);
        controls.add(Box.createHorizontalGlue());
        okay = new JButton("Okay");
        okay.addActionListener(ev -> {
            okayPressed = true;
            close();
        });
        controls.add(okay);
        controls.add(Box.createHorizontalGlue());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(layoutBox, "Center");
        getContentPane().add(controls, "South");
        getRootPane().setDefaultButton(okay);
        setModal(true);
    }

    public void updateSize() {
        int width = group.getMaxPixelWidth() + 200;
        int height = (layoutBox.getComponentCount() + 3) * 25;
        setSize(width, height);
    }

    public void close() {
        setVisible(false);
    }

    private void updateButtons() {
        boolean allClear = true;
        for (Object requiredComp : requiredInputComponents) {
            JTextComponent tc = (JTextComponent) requiredComp;
            if (tc.getText().trim().length() == 0) {
                allClear = false;
                break;
            }
        }
        okay.setEnabled(allClear);
    }

    /**
     * Adds the component
     */
    private void addComponent(String label, JComponent component) {
        component.setMaximumSize(new Dimension(200, 25));
        int count = layoutBox.getComponentCount();
        Box line = group.createLabelLine(label);
        line.add(component);
        layoutBox.add(line, count - 1); // adds the line before the glue
        updateSize();
        updateButtons();
    }

    public void addQueryLine(String key, String label, JTextComponent textComponent) {
        this.addQueryLine(key, label, textComponent, false);
    }

    public void addQueryLine(String key, String label, JTextComponent textComponent, boolean requireInput) {
        if (requireInput) {
            textComponent.addCaretListener(ev -> updateButtons());
            requiredInputComponents.add(textComponent);
        }
        addComponent(label, textComponent);
        textComponents.put(key, textComponent);
    }

    public void addQueryLine(String key, String label, JComboBox comboBox) {
        addComponent(label, comboBox);
        comboBoxes.put(key, comboBox);
    }

    public String getText(String key) {
        JTextComponent textComponent = (JTextComponent) textComponents.get(key);
        if (textComponent != null) {
            return textComponent.getText().trim();
        }
        return null;
    }

    public Object getComboChoice(String key) {
        JComboBox comboBox = (JComboBox) comboBoxes.get(key);
        if (comboBox != null) {
            return comboBox.getSelectedItem();
        }
        return null;
    }

    public boolean saidOkay() {
        return okayPressed;
    }
}
