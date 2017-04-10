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

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the entity type
 */
public interface SqlStreamOptimizerInfo<ENTITY> {

    DbmsType getDbmsType();

    String getSqlSelect();

    String getSqlSelectCount();

    BiFunction<String, List<Object>, Long> getCounter();

    Function<Field<ENTITY>, String> getSqlColumnNamer();

    Function<Field<ENTITY>, Class<?>> getSqlDatabaseTypeFunction();

    static <ENTITY> SqlStreamOptimizerInfo<ENTITY> of(
        final DbmsType dbmsType,
        final String sqlSelect,
        final String sqlSelectCount,
        final BiFunction<String, List<Object>, Long> counter,
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
