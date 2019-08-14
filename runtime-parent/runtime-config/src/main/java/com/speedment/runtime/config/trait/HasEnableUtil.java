package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;

public final class HasEnableUtil {

    private HasEnableUtil() {}

    /**
     * The key of the {@code enabled} property.
     */
    public static final String ENABLED = "enabled";

    /**
     * If a {@link Document} should be considered {@code true} if it is not
     * specified in the map.
     */
    static final boolean ENABLED_DEFAULT = true;

}
