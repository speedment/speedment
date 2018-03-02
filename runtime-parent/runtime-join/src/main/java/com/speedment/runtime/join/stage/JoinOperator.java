package com.speedment.runtime.join.stage;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public enum JoinOperator {
    EQUAL("="),
    NOT_EQUAL("<>"),
    LESS_THAN("<"),
    LESS_OR_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_OR_EQUAL(">="),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN");

    private final String operator;

    private JoinOperator(String operator) {
        this.operator = requireNonNull(operator);
    }

    public String sqlOperator() {
        return operator;
    }
}
