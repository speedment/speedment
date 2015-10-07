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
import static com.speedment.db.crud.CrudOperation.Type.READ;
import com.speedment.db.crud.Join;
import com.speedment.db.crud.Read;
import com.speedment.db.crud.ReadBuilder;
import com.speedment.db.crud.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Read} operation.
 *
 * @author Emil Forslund
 */
public final class ReadImpl implements Read {

    private final Table table;
    private final List<Selector> selectors;
    private final List<Join> joins;
    private final long limit;

    /**
     * ReadImpl should be constructed using the appropriate {@link Builder} 
     * class.
     *
     * @param table      the table to read the entity from
     * @param selectors  the selectors used to determine which entities to read
     * @param limit      the maximum number of entities to read
     */
    private ReadImpl(
            Table table, 
            List<Selector> selectors, 
            List<Join> joins, 
            long limit) {
        
        this.table     = table;
        this.selectors = selectors;
        this.joins     = joins;
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
    public Stream<Join> getJoins() {
        return joins.stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getLimit() {
        return limit;
    }

    /**
     * Builder class for {@link ReadImpl}.
     */
    public static class Builder implements ReadBuilder {

        private final Table table;
        private final List<Selector> selectors;
        private final List<Join> joins;
        private long limit;

        /**
         * Constructs a builder for the specified {@link Table}.
         *
         * @param table  the table
         */
        public Builder(Table table) {
            this.table     = requireNonNull(table);
            this.selectors = new ArrayList<>();
            this.joins     = new ArrayList<>();
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
        public Builder join(Join join) {
            joins.add(join);
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
            return READ;
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
        public ReadImpl build() {
            return new ReadImpl(table, selectors, joins, limit);
        }
    }
}