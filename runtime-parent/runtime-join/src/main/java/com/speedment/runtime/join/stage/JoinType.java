package com.speedment.runtime.join.stage;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public enum JoinType {
    INNER_JOIN("INNER JOIN"),
    LEFT_JOIN("LEFT JOIN"),
    RIGHT_JOIN("RIGHT JOIN"),
    FULL_OUTER_JOIN("FULL OUTER JOIN"),
    CROSS_JOIN("CROSS JOIN");

    private final String sql;

    private JoinType(String sql) {
        this.sql = requireNonNull(sql);
    }

    public String sql() {
        return sql;
    }

}
