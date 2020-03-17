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
package com.speedment.runtime.core.internal.manager.sql;

import com.speedment.runtime.core.manager.sql.HasGeneratedKeys;
import com.speedment.runtime.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class SqlInsertStatement
extends AbstractSqlStatement
implements HasGeneratedKeys {

    private final List<Long> generatedKeys;
    private final List<Field<?>> generatedFields;
    private final Consumer<List<Long>> generatedKeysConsumer;

    public SqlInsertStatement(
            String sql,
            List<?> values,
            List<Field<?>> generatedFields,
            Consumer<List<Long>> generatedKeyListeners) {
        
        super(sql, values);
        this.generatedKeys         = new ArrayList<>();
        this.generatedFields       = requireNonNull(generatedFields);
        this.generatedKeysConsumer = requireNonNull(generatedKeyListeners);
    }

    @Override
    public List<Field<?>> getGeneratedColumnFields() {
        return generatedFields;
    }

    @Override
    public void addGeneratedKey(Long generatedKey) {
        generatedKeys.add(generatedKey);
    }

    @Override
    public void notifyGeneratedKeyListener() {
        generatedKeysConsumer.accept(generatedKeys);
    }

    @Override
    public Type getType() {
        return Type.INSERT;
    }
}