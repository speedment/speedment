package com.speedment.common.injector.internal.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class for creating URLs.
 *
 * @author Julia Gustafsson
 * @since  3.2.2
 */

public final class UrlUtil {

    private UrlUtil() {}

    public static Object tryCreateURL(String serialized) {
        Object object;
        try {
            object = new URL(serialized);
        } catch (final MalformedURLException ex) {
            throw new IllegalArgumentException(String.format(
                "Specified URL '%s' is malformed.", serialized
            ), ex);
        }
        return object;
    }

}
