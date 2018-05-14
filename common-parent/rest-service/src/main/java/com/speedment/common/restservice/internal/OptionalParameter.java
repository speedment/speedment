package com.speedment.common.restservice.internal;

import com.speedment.common.restservice.Parameter;
import com.speedment.common.restservice.ParameterType;

import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link Parameter} where {@link Parameter#isOptional()}
 * returns {@code true}.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class OptionalParameter implements Parameter {

    private final String name;
    private final ParameterType type;
    private final Object defaultValue; // Nullable

    public OptionalParameter(String name, ParameterType type, Object defaultValue) {
        this.name         = requireNonNull(name);
        this.type         = requireNonNull(type);
        this.defaultValue = defaultValue; // Nullable
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
        return true;
    }

    @Override
    public Object defaultValue() {
        return defaultValue;
    }
}
