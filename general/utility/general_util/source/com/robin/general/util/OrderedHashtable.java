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
package com.robin.general.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Hashtable class that guarantees the order of the keys and values added
 * to it. In other words, the order of insertion is maintained.
 */
public class OrderedHashtable<T, U> extends Hashtable<T, U> {
    protected ArrayList<T> orderedKeys = new ArrayList<T>();

    @Override
    public void clear() {
        super.clear();
        orderedKeys.clear();
    }

    @Override
    public U put(T key, U value) {
        U ret = super.put(key, value);
        if (orderedKeys == null) {
            orderedKeys = new ArrayList();
        }
        if (!orderedKeys.contains(key)) {
            orderedKeys.add(key);
        }
        //validate();
        return ret;
    }

    @Override
    public void putAll(Map map) {
        for (int i = 0; i < orderedKeys.size(); i++) {
            T key = orderedKeys.get(i);
            U val = (U) map.get(key);
            put(key, val);
        }
    }

    @Override
    public Set keySet() {
        return new LinkedHashSet(orderedKeys);
    }

    @Override
    public Collection<U> values() {
        ArrayList vals = new ArrayList();
        if (orderedKeys == null) {
            orderedKeys = new ArrayList();
        }
        for (T key : orderedKeys) {
            vals.add(get(key));
        }
        return vals;
    }

    @Override
    public U remove(Object key) {
        U ret = super.remove(key);
        orderedKeys.remove(key);
        //validate();
        return ret;
    }

    // == Custom methods

    public Object remove(int index) {
        String key = (String) orderedKeys.get(index);
        return remove(key);
    }

    public Object getKey(int index) {
        return orderedKeys.get(index);
    }

    public Object getValue(int index) {
        return get(getKey(index));
    }

    public int indexOf(Object key) {
        return orderedKeys.indexOf(key);
    }

    public ArrayList<T> orderedKeys() {
        return orderedKeys;
    }

    public Object insert(int index, T key, U val) {
        ArrayList newOrderedKeys = new ArrayList();
        for (int i = 0; i < orderedKeys.size(); i++) {
            if (i == index) {
                newOrderedKeys.add(key);
            }
            newOrderedKeys.add(orderedKeys.get(i));
        }
        orderedKeys = newOrderedKeys;
        return this.put(key, val);
    }

    public Object replace(int index, T key, U val) {
        ArrayList newOrderedKeys = new ArrayList();
        for (int i = 0; i < orderedKeys.size(); i++) {
            String currentKey = (String) orderedKeys.get(i);
            if (i == index) {
                newOrderedKeys.add(key);
                remove(currentKey);
            } else {
                newOrderedKeys.add(orderedKeys.get(i));
            }
        }
        orderedKeys = newOrderedKeys;
        return this.put(key, val);
    }

    public void sortKeys(Comparator<T> comparator) {
        Collections.sort(orderedKeys, comparator);
    }

    @Override
    public synchronized String toString() {
        if (size() == 0) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (T key : orderedKeys) {
            U value = get(key);
            sb.append(key == this ? "(this Map)" : key.toString());
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value.toString());
            sb.append(", ");
        }
        final int len = sb.length();
        sb.replace(len - 2, len, "}");
        return sb.toString();
    }
}