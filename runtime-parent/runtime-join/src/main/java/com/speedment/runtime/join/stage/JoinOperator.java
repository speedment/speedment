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
    GREATER_OR_EQUAL(">=", false);
//    BETWEEN("BETWEEN", false),
//    NOT_BETWEEN("NOT BETWEEN", false);

    private final String operator;
    private final boolean symetric;

    private JoinOperator(String operator, boolean symetric) {
        this.operator = requireNonNull(operator);
        this.symetric = symetric;
    }

    public String sqlOperator() {
        return operator;
    }

    /**
     * Returns if this operation is symetric with respect to its parameters.
     * More formally returns true if: A oper B is true if and only if B oper A is
     * true.
     *
     * @return if this operation is symetric with respect to its parameters
     */
    public boolean isSymetric() {
        return symetric;
    }

    /**
     * Returns a JoinOperator that would produce an equivalent predicate when
     * the arguments to the operator are applied swapped.
     *
     * @return a JoinOperator that would produce an equivalent predicate when
     * the arguments to the operator are applied swapped.
     * @throws IllegalStateException if no such equivalent JoinOperator exists
     *
     */
    public JoinOperator swapEquivalent() {
        switch (this) {
            case EQUAL:
                return EQUAL;
            case NOT_EQUAL:
                return NOT_EQUAL;
            case LESS_THAN:
                return GREATER_THAN;
            case LESS_OR_EQUAL:
                return GREATER_OR_EQUAL;
            case GREATER_THAN:
                return LESS_THAN;
            case GREATER_OR_EQUAL:
                return LESS_OR_EQUAL;
        }
        throw new IllegalStateException(
            "There is no swap-equivalent " + JoinOperator.class.getSimpleName() + " to " + this
        );
    }

}
