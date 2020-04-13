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
package com.speedment.runtime.core.component.sql;

import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.sql.SqlStreamOptimizerInfoImpl;
import com.speedment.runtime.field.Field;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToLongBiFunction;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the entity type
 */
public interface SqlStreamOptimizerInfo<ENTITY> {

    /**
     * Returns the DbmsType.
     *
     * @return the DbmsType
     */
    DbmsType getDbmsType();

    /**
     * Returns the SQL select statement.
     * <p>
     * The initial value is "SELECT " + sqlColumnList + " FROM " +
     * sqlTableReference;
     *
     * @return the SQL select statement
     */
    String getSqlSelect();

    /**
     * Returns the SQL select count statement.
     * <p>
     * "SELECT COUNT(*) FROM " + sqlTableReference;
     *
     * @return the SQL select count statement
     */
    String getSqlSelectCount();

    /**
     * Returns a BiFunction that will read in the count long value from the
     * database.
     * <p>
     * E.g. getCounter.apply("select count(*) from user", emptyList()))
     *
     * @return a BiFunction that will read in the count long value from the
     * database
     */
    ToLongBiFunction<String, List<Object>> getCounter();

    /**
     * Returns a Function that will map a Field to a column name.
     *
     * @return a Function that will map a Field to a column name
     */
    Function<Field<ENTITY>, String> getSqlColumnNamer();

    /**
     * Returns a Function that will map a Field to a column class type.
     *
     * @return a Function that will map a Field to a column class type
     */
    Function<Field<ENTITY>, Class<?>> getSqlDatabaseTypeFunction();

    static <ENTITY> SqlStreamOptimizerInfo<ENTITY> of(
        final DbmsType dbmsType,
        final String sqlSelect,
        final String sqlSelectCount,
        final ToLongBiFunction<String, List<Object>> counter,
        final Function<Field<ENTITY>, String> sqlColumnNamer,
        final Function<Field<ENTITY>, Class<?>> sqlDatabaseTypeFunction
    ) {
        return new SqlStreamOptimizerInfoImpl<>(
            dbmsType,
            sqlSelect,
            sqlSelectCount,
            counter,
            sqlColumnNamer,
            sqlDatabaseTypeFunction
        );
    }

}
