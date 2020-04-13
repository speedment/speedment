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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.internal.stream;

import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class InternalStreamUtil {

    private InternalStreamUtil() {}

    public static <T> Stream<T> asStream(Iterator<T> iterator) {
        requireNonNull(iterator);
        return asStream(iterator, false);
    }

    private static <T> Stream<T> asStream(Iterator<T> iterator, boolean parallel) {
        requireNonNull(iterator);
        final Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    public static <T> Stream<T> asStream(ResultSet resultSet, SqlFunction<ResultSet, T> mapper, ParallelStrategy parallelStrategy) {
        requireNonNull(resultSet);
        requireNonNull(mapper);
        final Iterator<T> iterator = new ResultSetIterator<>(resultSet, mapper);
        return StreamSupport.stream(parallelStrategy.spliteratorUnknownSize(iterator, Spliterator.IMMUTABLE + Spliterator.NONNULL), false);
    }
    
    /**
     * Specialized read-only {@link Iterator} for consuming
     * {@link ResultSet ResultSets} by mapping them to an entity using an
     * adapter. This implementation is not thread safe.
     *
     * @param <T>  the type of the entity
     */
    static final class ResultSetIterator<T> implements Iterator<T> {

        private static final String NEXT_INVOKED_BUT_HAS_NEXT_WAS_FALSE = "The method next() was invoked even though hasNext() returns false.";

        /**
         * The current state of the {@code ResultSetIterator}.
         */
        private enum State {
            /**
             * There is a row in the ResultSet that has not yet been consumed.
             */
            NEXT,

            /**
             * There are no more rows in the ResultSet.
             */
            NO_NEXT,

            /**
             * The current row has already been consumed and we don't know if
             * there are any more yet. Call {@link #hasNext()} to find out.
             */
            NOT_DETERMINED
        }

        private final ResultSet resultSet;
        private final SqlFunction<ResultSet, T> mapper;

        private State state = State.NOT_DETERMINED;

        ResultSetIterator(final ResultSet resultSet,
                          final SqlFunction<ResultSet, T> mapper) {

            this.resultSet = requireNonNull(resultSet);
            this.mapper    = requireNonNull(mapper);
        }

        /**
         * {@inheritDoc}
         * <p>
         * After the termination of this method, the state will be either
         * {@link State#NEXT} or {@link State#NO_NEXT}. Calling this method
         * multiple times in a row has no effect. The state will remain the
         * same.
         *
         * @return  if there are more rows
         */
        @Override
        public boolean hasNext() {
            switch (state) {
                case NEXT    : return true;
                case NO_NEXT : return false;
                case NOT_DETERMINED :
                    return setState();

                default : throw new IllegalStateException(
                    "Unknown state '" + state + "'."
                );
            }
        }

        private boolean setState() {
            try {
                if (!resultSet.next()) {
                    state = State.NO_NEXT;
                    return false;
                }
            } catch (final SQLException ex) {
                state = State.NO_NEXT;
                return false;
            }

            state = State.NEXT;
            return true;
        }

        @Override
        public T next() {
            if (state == State.NOT_DETERMINED && !hasNext()) {
                throw new NoSuchElementException(NEXT_INVOKED_BUT_HAS_NEXT_WAS_FALSE);
            }

            if (state == State.NO_NEXT) {
                throw new NoSuchElementException(NEXT_INVOKED_BUT_HAS_NEXT_WAS_FALSE);
            }

            state = State.NOT_DETERMINED;

            try {
                return mapper.apply(resultSet);
            } catch (final SQLException ex) {
                throw new SpeedmentException(ex);
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            if (state == State.NOT_DETERMINED && !hasNext()) {
                return;
            }

            if (state == State.NO_NEXT) {
                return;
            }

            try {
                do {
                    try {
                        action.accept(mapper.apply(resultSet));
                    } catch (final SQLException ex) {
                        throw new SpeedmentException(ex);
                    }
                } while (resultSet.next());
            } catch (final SQLException ex) {
                // Do nothing.
            } finally {
                state = State.NO_NEXT;
            }
        }


    }

}
