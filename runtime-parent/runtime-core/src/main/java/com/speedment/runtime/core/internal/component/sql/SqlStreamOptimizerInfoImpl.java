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
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.field.Field;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.ToLongBiFunction;

/**
 *
 * @author Per Minborg
 */
public final class SqlStreamOptimizerInfoImpl<ENTITY> implements SqlStreamOptimizerInfo<ENTITY> {

    private final DbmsType dbmsType;
    private final String sqlSelect;
    private final String sqlSelectCount;
    private final ToLongBiFunction<String, List<Object>> counter;
    private final Function<Field<ENTITY>, String> sqlColumnNamer;
    private final Function<Field<ENTITY>, Class<?>> sqlDatabaseTypeFunction;

    public SqlStreamOptimizerInfoImpl(
        final DbmsType dbmsType,
        final String sqlSelect,
        final String sqlSelectCount,
        final ToLongBiFunction<String, List<Object>> counter,
        final Function<Field<ENTITY>, String> sqlColumnNamer,
        final Function<Field<ENTITY>, Class<?>> sqlDatabaseTypeFunction
    ) {
        this.dbmsType = requireNonNull(dbmsType);
        this.sqlSelect = requireNonNull(sqlSelect);
        this.sqlSelectCount = requireNonNull(sqlSelectCount);
        this.counter = requireNonNull(counter);
        this.sqlColumnNamer = requireNonNull(sqlColumnNamer);
        this.sqlDatabaseTypeFunction = requireNonNull(sqlDatabaseTypeFunction);
    }

    @Override
    public DbmsType getDbmsType() {
        return dbmsType;
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect;
    }

    @Override
    public String getSqlSelectCount() {
        return sqlSelectCount;
    }

    @Override
    public ToLongBiFunction<String, List<Object>> getCounter() {
        return counter;
    }

    @Override
    public Function<Field<ENTITY>, String> getSqlColumnNamer() {
        return sqlColumnNamer;
    }

    @Override
    public Function<Field<ENTITY>, Class<?>> getSqlDatabaseTypeFunction() {
        return sqlDatabaseTypeFunction;
    }

}
