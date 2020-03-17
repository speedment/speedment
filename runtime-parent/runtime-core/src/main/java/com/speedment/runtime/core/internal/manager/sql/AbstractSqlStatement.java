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

import com.speedment.runtime.core.manager.sql.SqlStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base implementation of {@link SqlStatement}.
 *
 * @author Per Minborg
 */
abstract class AbstractSqlStatement implements SqlStatement {

    private final String sql;
    private final List<Object> values;

    AbstractSqlStatement(final String sql, final List<?> values) {
        this.sql    = Objects.requireNonNull(sql);
        this.values = new ArrayList<>(Objects.requireNonNull(values));
    }

    @Override
    public final String getSql() {
        return sql;
    }

    @Override
    public final List<Object> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return getSql() + ", " + values.toString();
    }
}
