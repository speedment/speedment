package com.speedment.common.injector.exception;

/**
 * Exception thrown if an Injector annotation like {@code WithState} is used in
 * a way that indicates an error in the setup of the injected class. For an
 * example, if a parameter to a method is set to have a state that occurs far
 * before or after the state of the method itself.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public final class MisusedAnnotationException extends RuntimeException {

    private static final long serialVersionUID = 2302868474717414052L;

    public MisusedAnnotationException(String message) {
        super(message);
    }
}
