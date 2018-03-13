package com.speedment.runtime.join.stage;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public enum JoinOperator {
    EQUAL("=", true),
    NOT_EQUAL("<>", true),
    LESS_THAN("<", false),
    LESS_OR_EQUAL("<=", false),
    GREATER_THAN(">", false),
    GREATER_OR_EQUAL(">=", false),
    BETWEEN("BETWEEN", false),
    NOT_BETWEEN("NOT BETWEEN", false);

    private final String operator;
    private final boolean symetric;

    private JoinOperator(String operator, boolean symetric) {
        this.operator = requireNonNull(operator);
        this.symetric = symetric;
    }

    public String sqlOperator() {
        return operator;
    }

    public boolean isSymetric() {
        return symetric;
    }

}
