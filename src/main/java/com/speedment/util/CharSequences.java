package com.speedment.util;

/**
 *
 * @author pemi
 */
public class CharSequences {

    private CharSequences() {
    }

    public static final int INDEX_NOT_FOUND = -1;

    /**
     * Returns the index within this char sequence of the first occurrence of
     * the specified character.
     *
     * @param cs the CharSequence to search.
     * @param ch the character to search for.
     * @return the index of the first occurrence of the character in the
     * character sequence, or {@code -1} if the character does not occur.
     */
    public static int indexOf(CharSequence cs, char ch) {
        return indexOf(cs, ch, 0);
    }

    /**
     * Returns the index within this char sequence of the first occurrence of
     * the specified character, starting the search at the specified index.
     *
     * @param cs the CharSequence to search.
     * @param ch the character to search for.
     * @param fromIndex the index to start the search from.
     * @return the index of the first occurrence of the character in the
     * character sequence that is greater than or equal to {@code fromIndex}, or
     * {@code -1} if the character does not occur.
     */
    public static int indexOf(CharSequence cs, char ch, int fromIndex) {
        final int max = cs.length();
        if (fromIndex < 0) {
            fromIndex = 0;
        } else if (fromIndex >= max) {
            return INDEX_NOT_FOUND;
        }
        for (int i = fromIndex; i < max; i++) {
            if (cs.charAt(i) == ch) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

}
