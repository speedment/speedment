package com.speedment.runtime.compute.expression;

/**
 * Every expression type has a corresponding interface to get a type-safe way of
 * applying the expression without boxing any values. This enumeration can be
 * used to tell which type an expression has.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public enum ExpressionType {

    BYTE, BYTE_NULLABLE(true),
    SHORT, SHORT_NULLABLE(true),
    INT, INT_NULLABLE(true),
    LONG, LONG_NULLABLE(true),
    FLOAT, FLOAT_NULLABLE(true),
    DOUBLE, DOUBLE_NULLABLE(true),
    CHAR, CHAR_NULLABLE(true),
    BOOLEAN, BOOLEAN_NULLABLE(true),
    ENUM, ENUM_NULLABLE(true),
    STRING, STRING_NULLABLE(true),
    BIG_DECIMAL, BIG_DECIMAL_NULLABLE(true);

    private final boolean isNullable;

    ExpressionType() {
        this(false);
    }

    ExpressionType(boolean isNullable) {
        this.isNullable = isNullable;
    }

    /**
     * Returns {@code true} if this type is one of the nullable types, and
     * otherwise {@code false}
     *
     * @return {@code true} if expression result may be {@code null}
     */
    public boolean isNullable() {
        return isNullable;
    }
}
