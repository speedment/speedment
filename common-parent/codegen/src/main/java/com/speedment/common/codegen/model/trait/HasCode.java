/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.util.Formatting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNullElements;

/**
 * A trait for models that contains code.
 *
 * @param <T> the extending type
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface HasCode<T extends HasCode<T>> {
    
    /**
     * Adds the specified row of code to this model. If the row contains
     * new-line characters, the line will be broken apart on those characters
     * and added using the {@link #add(java.lang.String...)}-method.
     * 
     * @param row  the row
     * @return     a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(String row) {
        return add(row.split(Formatting.nl()));
    }
    
    /**
     * Adds all the specified rows to this model. If any of the specified rows
     * contains a new-line character it will be broken apart on that character
     * so that every row is in fact added as a separate string.
     * 
     * @param rows  the rows
     * @return      a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(String... rows) {
        requireNonNullElements(rows);
        for (final String row : rows) {
            Collections.addAll(getCode(), row.split(Formatting.nl()));
        }
        return (T) this;
    }

    /**
     * Adds all the specified rows to this model. If any of the specified rows
     * contains a new-line character it will be broken apart on that character
     * so that every row is in fact added as a separate string.
     *
     * @param rows  the rows
     * @return      a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(Stream<String> rows) {
        rows.flatMap(row -> Stream.of(row.split(Formatting.nl())))
            .forEachOrdered(this::add);
        return (T) this;
    }
    
    /**
     * Returns a list of the code rows of this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the code rows
     */
    List<String> getCode();
}