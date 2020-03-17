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
package com.speedment.runtime.join.stage;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public enum JoinType {
    INNER_JOIN("INNER JOIN", false, false),
    LEFT_JOIN("LEFT JOIN", true, false),
    RIGHT_JOIN("RIGHT JOIN", false, true),
    //FULL_OUTER_JOIN("FULL OUTER JOIN", true, true),
     CROSS_JOIN("CROSS JOIN", false, false);

    private final String sql;
    private final boolean nullableSelf;
    private final boolean nullableOther;

    JoinType(String sql, boolean nullableSelf, boolean nullableOther) {
        this.sql = requireNonNull(sql);
        this.nullableSelf = nullableSelf;
        this.nullableOther = nullableOther;
    }

    /**
     * Returns the SQL representation of the JoinType.
     *
     * @return the SQL representation of the JoinType
     */
    public String sql() {
        return sql;
    }

    /**
     * Returns if this JoinType can produce results that are {@code null} for
     * entities belonging to the same Stage where this JoinType is present.
     *
     * @return if this JoinType can produce results that are {@code null} for
     * entities belonging to the same Stage where this JoinType is present
     */
    public boolean isNullableSelf() {
        return nullableSelf;
    }

    /**
     * Returns if this JoinType can produce results that are {@code null} for
     * entities belonging to another Stage.
     *
     * @return if this JoinType can produce results that are {@code null} for
     * entities belonging to another Stage
     */
    public boolean isNullableOther() {
        return nullableOther;
    }

    /**
     * Returns if this JoinType always produce results that are <em>NEVER</em>
     * {@code null} for entities belonging to another Stage.
     *
     * @return if this JoinType always produce results that are <em>NEVER</em>
     * {@code null} for entities belonging to another Stage.
     */
    public boolean isNeverNullable() {
        return !nullableSelf && !nullableOther;
    }

}
