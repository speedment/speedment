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
package com.speedment.internal.core.db.crud;

import com.speedment.config.Table;
import static com.speedment.db.crud.CrudOperation.Type.UPDATE;
import com.speedment.db.crud.Selector;
import com.speedment.db.crud.Update;
import com.speedment.db.crud.UpdateBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Update} operation.
 *
 * @author Emil Forslund
 */
public final class UpdateImpl implements Update {

    private final Table table;
    private final List<Selector> selectors;
    private final Map<String, Object> values;
    private final long limit;

    /**
     * UpdateImpl should be constructed using the appropriate {@link Builder} class.
     *
     * @param table      the table of the entity to update
     * @param selectors  the selectors used to determine which entities to update
     * @param values     the new values to use
     * @param limit      the maximum number of entities to update
     */
    private UpdateImpl(Table table, List<Selector> selectors, Map<String, Object> values, long limit) {
        this.table     = table;
        this.selectors = selectors;
        this.values    = values;
        this.limit     = limit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Table getTable() {
        return table;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Selector> getSelectors() {
        return selectors.stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLimit() {
        return limit;
    }

    /**
     * Builder class for {@link UpdateImpl}.
     */
    public static class Builder implements UpdateBuilder {

        private final Table table;
        private final List<Selector> selectors;
        private final Map<String, Object> values;
        private long limit;

        /**
         * Constructs a builder for the specified {@link Table}.
         *
         * @param table  the table
         */
        public Builder(Table table) {
            this.table     = requireNonNull(table);
            this.selectors = new ArrayList<>();
            this.values    = new ConcurrentHashMap<>();
            this.limit     = Long.MAX_VALUE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder where(Selector selector) {
            selectors.add(requireNonNull(selector));
            return this;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Builder limit(long limit) {
            this.limit = limit;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder with(String columnName, Object value) {
            values.put(
                requireNonNull(columnName),
                value
            );

            return this;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Builder with(Map<String, Object> values) {
            values.forEach(this::with);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Type getType() {
            return UPDATE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Table getTable() {
            return table;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public UpdateImpl build() {
            return new UpdateImpl(table, selectors, values, limit);
        }
    }
}