package com.speedment.util;

import java.util.Optional;

/**
 *
 * @author pemi
 */
public class Cast {

    private Cast() {
    }

    public static <T> Optional<T> cast(Object o, Class<T> clazz) {
        if (clazz.isAssignableFrom(o.getClass())) {
            final T result = clazz.cast(o);
            return Optional.of(result);
        }
        return Optional.empty();
    }

}
