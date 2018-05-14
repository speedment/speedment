package com.speedment.common.restservice.internal;

import com.speedment.common.restservice.Parameter;
import com.speedment.common.restservice.ParameterType;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link Parameter} where {@link Parameter#isOptional()}
 * returns {@code false}.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class RequiredParameter implements Parameter {

    private final String name;
    private final ParameterType type;

    public RequiredParameter(String name, ParameterType type) {
        this.name = requireNonNull(name);
        this.type = requireNonNull(type);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public ParameterType type() {
        return type;
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public Object defaultValue() {
        throw new UnsupportedOperationException(
            "Method 'defaultValue()' should never be called on a Parameter " +
            "where isOptional() returns false."
        );
    }
}
