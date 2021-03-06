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
package com.robin.general.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Finds resources, whether in local directory, or in jar file.
 */
public class ResourceFinder {
    private static final ClassLoader sysLoader = ClassLoader.getSystemClassLoader();

    /**
     * Find and return the resource at the given path as an input stream. If
     * the resource cannot be found, null is returned.
     *
     * @param path path of resource
     * @return resource as input stream
     */
    public static InputStream getInputStream(String path) {
        InputStream stream = null;

        File f = new File(path);
        if (f.exists()) {
            try {
                return new FileInputStream(f);
            } catch (IOException ex) {
                // ignore
            }
        }

        URL u = sysLoader.getResource(path);
        if (u != null) {
            try {
                stream = u.openStream();
            } catch (IOException ex) {
            }
        }
        return stream;
    }

    /**
     * Returns true if the path specifies a resource that can be loaded.
     *
     * @param path the path
     * @return true if a loadable resource
     */
    public static boolean exists(String path) {
        File f = new File(path);
        if (f.exists()) {
            return true;
        }
        URL u = sysLoader.getResource(path);
        if (u != null) {
            return true;
        }
        return false;
    }

    /**
     * Loads and returns the specified resource as a string. In other words, it
     * is expected that the resource is some sort of text file. If no (text)
     * resource exists at that path, null is returned.
     *
     * @param path the resource path
     * @return the contents of the resource
     */
    public static String getString(String path) {
        try {
            InputStream stream = getInputStream(path);
            if (stream != null) {
                StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                return sb.toString();
            }
        } catch (IOException ex) {
            // Do nothing
        }
        return null;
    }
}