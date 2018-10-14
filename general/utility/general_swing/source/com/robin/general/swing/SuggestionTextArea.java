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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

import static javax.swing.SwingUtilities.invokeLater;

/*
 * This class was derived from Sun's Tutorial class called TextAreaDemo, which
 * demonstrated auto-completion. As the method they used was mostly retained in
 * this class, the copyright notice above is necessary.
 */

/**
 * A text area that can make suggestions to the user.
 */
public class SuggestionTextArea extends JTextArea {

    private static final String ENTER = "ENTER";

    private static final String COMMIT_ACTION = "commit";
    private static final String ROLLBACK_ACTION = "rollback";

    private enum Mode {
        INSERT, COMPLETION
    }

    private Mode mode = Mode.INSERT;
    private boolean lineModeOn = false;
    private boolean autoSpace = true;

    // TODO:
    private ArrayList<String> words;

    /**
     * Creates an empty suggestion text area.
     */
    public SuggestionTextArea() {
        init();
    }

    /**
     * Creates a suggestion text area initialized with the given text.
     *
     * @param text the initial text
     */
    public SuggestionTextArea(String text) {
        super(text);
        init();
    }

    /**
     * Creates an empty suggestion text area of the given dimensions
     *
     * @param rows the number of rows of text
     * @param cols the number of character columns
     */
    public SuggestionTextArea(int rows, int cols) {
        super(rows, cols);
        init();
    }

    private void init() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent ev) {
            }

            @Override
            public void removeUpdate(DocumentEvent ev) {
            }

            @Override
            public void insertUpdate(DocumentEvent ev) {
                process(ev);
            }
        });

        InputMap im = getInputMap();
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke(ENTER), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), ROLLBACK_ACTION);
        am.put(ROLLBACK_ACTION, new RollbackAction());
    }

    /**
     * Sets the list of words for suggestions.
     *
     * @param words the list of suggestions
     */
    // TODO: change sig to List<String> (also defensive programming)
    public void setWords(ArrayList<String> words) {
        this.words = words;
        Collections.sort(this.words);
    }

    /**
     * Returns true if line mode is enabled.
     *
     * @return true if line mode enabled
     */
    public boolean isLineModeOn() {
        return lineModeOn;
    }

    /**
     * Enables or disables line mode
     *
     * @param lineModeOn true to enable; false to disable
     */
    public void setLineModeOn(boolean lineModeOn) {
        this.lineModeOn = lineModeOn;
    }

    /**
     * Returns true if auto space is enabled.
     *
     * @return true if auto space enabled
     */
    public boolean isAutoSpace() {
        return autoSpace;
    }

    /**
     * Enables or disables auto spacing.
     *
     * @param autoSpace true to enable; false to disable
     */
    public void setAutoSpace(boolean autoSpace) {
        this.autoSpace = autoSpace;
    }


    private void process(DocumentEvent ev) {
        if (ev.getLength() != 1 || words == null || words.isEmpty()) {
            return;
        }

        int cursorPos = ev.getOffset();
        String content = null;
        try {
            content = getText(0, cursorPos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        // Find where the word starts
        int wordStart;
        for (wordStart = cursorPos; wordStart >= 0; wordStart--) {
            char c = content.charAt(wordStart);
            if (lineModeOn && c == '\n') {
                break;
            }
            if (!lineModeOn && Character.isWhitespace(c)) {
                break;
            }
        }
        if (cursorPos - wordStart < 2) {
            // Too few chars
            return;
        }

        String prefix = content.substring(wordStart + 1);
        int n = Collections.binarySearch(words, prefix);

        if (n < 0 && -n <= words.size()) {
            String match = words.get(-n - 1);
            if (match.startsWith(prefix)) {
                // A completion is found
                String completion = match.substring(cursorPos - wordStart);
                // We cannot modify Document from within notification,
                // so we submit a task that does the change later
                invokeLater(new CompletionTask(completion, cursorPos + 1));
            }
        } else {
            // Nothing found
            mode = Mode.INSERT;
        }
    }

    private class CompletionTask implements Runnable {
        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        @Override
        public void run() {
            insert(completion, position);
            setCaretPosition(position + completion.length());
            moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = getSelectionEnd();
                insert(" ", pos);
                setCaretPosition(pos + (autoSpace ? 1 : 0));
                mode = Mode.INSERT;
            } else {
                replaceSelection("\n");
            }
        }
    }

    private class RollbackAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                replaceSelection("");
                mode = Mode.INSERT;
            } else {
                int pos = getCaretPosition();
                if (pos > 0) {
                    setCaretPosition(pos - 1);
                    moveCaretPosition(pos);
                    replaceSelection("");
                }
            }
        }
    }
}
