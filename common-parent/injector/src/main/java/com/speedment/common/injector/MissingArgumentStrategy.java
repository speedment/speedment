package com.speedment.common.injector;

import com.speedment.common.injector.execution.Execution;

/**
 * Enumeration of the possible ways to deal with an argument being missing in
 * the injector when an {@link Execution} should be invoked.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public enum MissingArgumentStrategy {

    /**
     * If the instance is not available for injection, throw an exception during
     * the injector build phase.
     */
    THROW_EXCEPTION,

    /**
     * If the instance is not available for injection, show a message in the
     * injector builder DEBUG log and then skip invoking this method.
     */
    SKIP_INVOCATION

}
