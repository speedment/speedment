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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author pemi
 */
public abstract class SqlStatement {

    private final String sql;
    private final List<?> values;

    public enum Type {
        INSERT, UPDATE, DELETE
    }

    public SqlStatement(final String sql, final List<?> values) {
        this.sql = Objects.requireNonNull(sql);
        this.values = new ArrayList<>(Objects.requireNonNull(values));
    }

    public String getSql() {
        return sql;
    }

    public List<?> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return getSql() + ", " + values.toString();
    }

    public abstract Type getType();

}
