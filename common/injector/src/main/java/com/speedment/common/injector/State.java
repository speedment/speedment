package com.speedment.common.injector;

/**
 * The state of an injectable instance.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public enum State {

    /**
     * The {@link Injectable} has been created but it has not been exposed anywhere yet.
     */
    CREATED,
    /**
     * The {@link Injectable} has been initialized.
     */
    INIITIALIZED,
    /**
     * The {@link Injectable} has been initialized and resolved.
     */
    RESOLVED,
    /**
     * The {@link Injectable} has been initialized, resolved and started.
     */
    STARTED,
    /**
     * The {@link Injectable} has been initialized, resolved, started and stopped.
     */
    STOPPED;
}
