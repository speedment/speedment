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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.core.stream;

import com.speedment.core.exception.SpeedmentException;
import static com.speedment.util.Util.instanceNotAllowed;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author Emil Forslund
 */
public final class StreamUtil {

    public static <T> Stream<T> streamOfNullable(T element) {
        if (element == null) {
            return Stream.empty();
        } else {
            return Stream.of(element);
        }
    }

    public static <T> Stream<T> asStream(Iterator<T> iterator) {
        return asStream(iterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> iterator, boolean parallel) {
        final Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    public static <T> Stream<T> asStream(ResultSet resultSet, Function<ResultSet, T> mapper) {
        final Iterator<T> iterator = new ResultSetIterator<>(resultSet, mapper);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE + Spliterator.NONNULL), false);
    }

    private static class ResultSetIterator<T> implements Iterator<T> {

        private final ResultSet resultSet;
        private final Function<ResultSet, T> mapper;

        public ResultSetIterator(final ResultSet resultSet, final Function<ResultSet, T> mapper) {
            this.resultSet = Objects.requireNonNull(resultSet);
            this.mapper = Objects.requireNonNull(mapper);
        }

        @Override
        public boolean hasNext() {
            try {
                return resultSet.next();
            } catch (SQLException sqle) {
                throw new SpeedmentException("Error iterating over a ResultSet", sqle);
            }
        }

        @Override
        public T next() {
            return mapper.apply(resultSet);
        }

    }

    /**
     * Utility classes should not be instantiated.
     */
    private StreamUtil() { instanceNotAllowed(getClass()); }
}