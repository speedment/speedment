package com.speedment.runtime.nanohttpd.internal.util;

/**
 * Utility class used to trim strings.
 *
 * @author Emil Forslund
 * @since  3.1.1
 */
public final class TrimUtil {

    /**
     * Trims the specified substring from the beginning and end of the specified
     * input, if it is present.
     *
     * @param input  the input string to trim
     * @param trim   the substring to remove from beginning and end if present
     * @return       the new string
     */
    public static String trimLeadingTrailing(String input, String trim) {
        String result = input;
        if (result.startsWith(trim))
            result = result.substring(trim.length());
        if (result.endsWith(trim))
            result = result.substring(0, result.length() - trim.length());
        return result;
    }

    /**
     * Utility classes should not be instantiated.
     */
    private TrimUtil() {}
}
