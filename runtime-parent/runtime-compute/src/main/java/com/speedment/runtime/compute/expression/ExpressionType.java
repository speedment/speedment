package com.speedment.runtime.compute.expression;

/**
 * Every expression type has a corresponding interface to get a type-safe way
 * of applying the expression without boxing any values. This enumeration can be
 * used to tell which type an expression has.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public enum ExpressionType {

    BYTE, BYTE_NULLABLE,
    SHORT, SHORT_NULLABLE,
    INT, INT_NULLABLE,
    LONG, LONG_NULLABLE,
    FLOAT, FLOAT_NULLABLE,
    DOUBLE, DOUBLE_NULLABLE,
    CHAR, CHAR_NULLABLE,
    BOOLEAN, BOOLEAN_NULLABLE,
    ENUM, ENUM_NULLABLE,
    STRING, STRING_NULLABLE;

    /**
     * Returns {@code true} if this type is one of the nullable types, and
     * otherwise {@code false}
     *
     * @return  {@code true} if expression result may be {@code null}
     */
    public boolean isNullable() {
        return ordinal() % 2 == 1;
    }
}
