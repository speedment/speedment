package com.speedment.common.restservice;

import com.speedment.common.restservice.internal.OptionalParameter;
import com.speedment.common.restservice.internal.RequiredParameter;

/**
 * A request parameter expected to be specified when invoking a particular
 * {@link Endpoint}.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface Parameter {

    /**
     * Returns an instance of this interface where {@link #isOptional()} returns
     * {@code false}.
     *
     * @param name  the parameter name
     * @param type  the type expected by {@link Endpoint}
     * @return      the new parameter
     */
    static Parameter required(String name, ParameterType type) {
        return new RequiredParameter(name, type);
    }

    /**
     * Returns an instance of this interface where {@link #isOptional()} returns
     * {@code true}.
     *
     * @param name          the parameter name
     * @param type          the type expected by {@link Endpoint}
     * @param defaultValue  the default value (or {@code null})
     * @return              the new parameter
     */
    static Parameter optional(String name, ParameterType type, Object defaultValue) {
        return new OptionalParameter(name, type, defaultValue);
    }

    /**
     * The name of the parameter (as used in the request).
     *
     * @return  the name
     */
    String name();

    /**
     * The Java-type that the {@link Endpoint} expected values for this
     * parameter to be given as.
     *
     * @return  the expected type of values for this parameter
     */
    ParameterType type();

    /**
     * Returns {@code true} if this parameter is not necessary to complete the
     * operation, and may therefore be omitted by the client when invoking the
     * {@link Endpoint}.
     *
     * @return  {@code true} if parameter is optional, otherwise {@code false}
     */
    boolean isOptional();

    /**
     * If {@link #isOptional()} returns {@code true}, then this value should be
     * the default value expected if the parameter was omitted by the client. If
     * {@link #isOptional()} returns {@code false}, then the behaviour of this
     * method is unspecified. If the parameter is optional and this method
     * returns {@code null}, then the parameter value may be omitted in the
     * arguments passed to {@link Endpoint}.
     *
     * @return  the default value, or {@code null}
     */
    Object defaultValue();

}
