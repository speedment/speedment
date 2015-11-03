package com.speedment.internal.core.config.immutable;

/**
 *
 * @author pemi
 */
public class ImmutableUtil {

    public  static <T> T immutableClassModification() {
        throw new UnsupportedOperationException("This class is immutable.");
    }

    private ImmutableUtil() {
    }

}
