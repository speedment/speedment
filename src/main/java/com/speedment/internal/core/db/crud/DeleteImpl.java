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
import static com.speedment.db.crud.CrudOperation.Type.DELETE;
import com.speedment.db.crud.Delete;
import com.speedment.db.crud.DeleteBuilder;
import com.speedment.db.crud.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Delete} operation.
 *
 * @author Emil Forslund
 */
public final class DeleteImpl implements Delete {

    private final Table table;
    private final List<Selector> selectors;
    private final long limit;

    /**
     * DeleteImpl should be constructed using the appropriate {@link Builder} class.
     *
     * @param table      the table to delete the entity in
     * @param selectors  the selectors used to determine which entities to delete
     * @param limit      the maximum number of entities to affect
     */
    private DeleteImpl(Table table, List<Selector> selectors, long limit) {
        this.table     = table;
        this.selectors = selectors;
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
    public long getLimit() {
        return limit;
    }

    /**
     * Builder class for {@link DeleteImpl}.
     */
    public static class Builder implements DeleteBuilder {

        private final Table table;
        private final List<Selector> selectors;
        private long limit;

        /**
         * Constructs a builder for the specified {@link Table}.
         *
         * @param table  the table
         */
        public Builder(Table table) {
            this.table     = requireNonNull(table);
            this.selectors = new ArrayList<>();
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
        public Type getType() {
            return DELETE;
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
        public DeleteImpl build() {
            return new DeleteImpl(table, selectors, limit);
        }
    }
}