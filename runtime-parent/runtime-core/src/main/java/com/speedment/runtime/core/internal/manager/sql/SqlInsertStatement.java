/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.manager.sql;

import com.speedment.runtime.field.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class SqlInsertStatement<ENTITY> extends SqlStatement {

    private final Collection<Field<ENTITY>> generatedColumnFields;
    private final List<Long> generatedKeys;
    private final Consumer<List<Long>> generatedKeysConsumer;

    public SqlInsertStatement(
            String sql,
            List<?> values,
            Collection<Field<ENTITY>> generatedColumnFields,
            Consumer<List<Long>> generatedKeysConsumer) {
        
        super(sql, values);
        this.generatedKeys = new ArrayList<>();
        this.generatedKeysConsumer = requireNonNull(generatedKeysConsumer);
        this.generatedColumnFields = requireNonNull(generatedColumnFields);
    }

    public Collection<Field<ENTITY>> getGeneratedColumnFields() {
        return generatedColumnFields;
    }

    public List<Long> getGeneratedKeys() {
        return Collections.unmodifiableList(generatedKeys);
    }

    public void addGeneratedKey(Long generatedKey) {
        generatedKeys.add(generatedKey);
    }

    public void acceptGeneratedKeys() {
        generatedKeysConsumer.accept(generatedKeys);
    }

    @Override
    public Type getType() {
        return Type.INSERT;
    }
}