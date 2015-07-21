package com.speedment.util;

/**
 * Support interface for classes that only contains static methods. This
 * interface can for example be used for various "Util" classes.
 *
 * @author pemi
 */
public interface PureStaticMethods {

    /**
     * Support method that can be used in constructors to throw an
     * {@Code UnsupportedOperationException} if someone is trying to create an
     * instance of the class.
     */
    default void instanceNotAllowed() {
        throw new UnsupportedOperationException("It is not allowed to create instances of the " + getClass().getName() + " class");
    }

}
