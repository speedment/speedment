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
import com.speedment.db.crud.Create;
import com.speedment.db.crud.CreateBuilder;
import static com.speedment.db.crud.CrudOperation.Type.CREATE;
import static java.util.Collections.unmodifiableMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Create} operation.
 *
 * @author Emil Forslund
 */
public final class CreateImpl implements Create {

    private final Table table;
    private final Map<String, Object> values;

    /**
     * CreateImpl should be constructed using the appropriate {@link Builder} class.
     *
     * @param table   the table to create the entity in
     * @param values  the values to use
     */
    private CreateImpl(Table table, Map<String, Object> values) {
        this.table  = table;
        this.values = unmodifiableMap(values);
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
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * Builder class for {@link CreateImpl}.
     */
    public static class Builder implements CreateBuilder {

        private final Table table;
        private final Map<String, Object> values;

        /**
         * Constructs a builder for the specified {@link Table}.
         *
         * @param table  the table
         */
        public Builder(Table table) {
            this.table  = requireNonNull(table);
            this.values = new ConcurrentHashMap<>();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Builder with(String column, Object value) {
            values.put(
                requireNonNull(column),
                value
            );

            return this;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public CreateImpl.Builder with(Map<String, Object> values) {
            values.forEach(this::with);
            return this;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Type getType() {
            return CREATE;
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
        public CreateImpl build() {
            return new CreateImpl(table, values);
        }
    }
}