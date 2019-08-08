package com.speedment.common.injector.internal.util;

import java.util.StringJoiner;

/**
 * Utility class for formatting strings.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public final class StringUtil {

    /**
     * Returns the specified words separated with commas and the word 'and' as
     * appropriate.
     *
     * @param words  the words to join
     * @return       the human-readable joined string
     */
    public static String commaAnd(String... words) {
        switch (words.length) {
            case 0: return "";
            case 1: return words[0];
            case 2: return words[0] + " and " + words[1];
            default: {
                final StringJoiner join = new StringJoiner(", ", "", " and ");
                for (int i = 0; i < words.length - 1; i++) {
                    join.add(words[i]);
                }
                return join.toString() + words[words.length - 1];
            }
        }
    }

    private StringUtil() {}
}
