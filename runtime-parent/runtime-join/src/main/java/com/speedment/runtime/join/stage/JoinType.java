package com.speedment.runtime.join.stage;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public enum JoinType {
    INNER_JOIN("INNER JOIN", false),
    LEFT_JOIN("LEFT JOIN", true),
    RIGHT_JOIN("RIGHT JOIN", true),
    FULL_OUTER_JOIN("FULL OUTER JOIN", true),
    CROSS_JOIN("CROSS JOIN", false);

    private final String sql;
    private final boolean nullable;

    private JoinType(String sql, boolean nullable) {
        this.sql = requireNonNull(sql);
        this.nullable = nullable;
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
     * Returns if this JoinType can produce results that are null.
     *
     * @return if this JoinType can produce results that are null
     */
    public boolean isNullable() {
        return nullable;
    }

}
