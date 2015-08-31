/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.core.runtime.typemapping;

import com.speedment.config.Dbms;

/**
 *
 * @author pemi
 * @param <T> Java type for this mapping
 */
public interface JavaTypeMapping<T> {

    /**
     * Returns the Java Class to use for this type mapping.
     *
     * @return the Java Class to use for this type mapping
     */
    Class<T> getJavaClass();

    /**
     * Returns the name of the ResultSet method to be used when getting data
     * from a database. If, for example, the Java class {@code Integer} is used,
     * then this method will return "Int" because an {@code INteger} can be read
     * from a {@link java.sql.ResultSet} with the method
     * {@link java.sql.ResultSet#getInt(int)}. In some (rare) cases, the
     * response might be different for different Dbms:es, so this is why the
     * dmbs parameter needs to be provided.
     *
     * @param dbms to use
     * @return the name of the ResultSet method to be used when getting data
     * from a database.
     */
    String getResultSetMethodName(Dbms dbms);

    /**
     * Parses the given input {@code String} and returns an object of type T
     * that corresponds to the given {@code long}. This function is needed to be
     * able to reconstruct Entity fields from JSON strings or change data logs
     * for example.
     *
     * @param s the input {@code long}
     * @return an {@code Object} of type T that corresponds to the given
     * {@code String}
     */
    T parse(String s);

    /**
     * Parses the given input {@code long} and returns an object of type T that
     * corresponds to the given long. This function is needed to be able to
     * reconstruct Entity fields that are auto increments in SQL tables.
     * Typically, this method is only implemented for classes implementing the
     * {@link Number} interface.
     *
     * @param l the input {@code long}
     * @return an {@code Object} of type T that corresponds to the given
     * {@code String}
     */
    T parse(long l);

    static <T> T unableToMapString(Class<T> clazz) {
        return unableToMap(String.class, clazz);
    }

    static <T> T unableToMapLong(Class<T> clazz) {
        return unableToMap(Long.class, clazz);
    }

    static <T> T unableToMap(Class<?> from, Class<T> to) {
        throw new IllegalArgumentException("Unable to parse a " + from.toString() + " and make it " + to.toString());
    }

}
