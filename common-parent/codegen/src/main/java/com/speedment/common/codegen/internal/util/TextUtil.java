/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.codegen.internal.util;

import com.speedment.common.codegen.util.Formatting;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNullElements;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.internal.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class TextUtil {

    private static final Pattern WORDS = Pattern.compile("[\\s]+");
    private static final Pattern JAVADOC_WORDS = Pattern.compile("[ ]+"); // Text within html tags "pre" are treated as one "word"
    private static final String NL = "\n";
    private static final int JAVA_DOC_WIDTH = 74;
    private static final Set<String> JAVA_DOC_SINGLE_LINE_WORDS = Collections.unmodifiableSet(Stream.of("<p>").collect(Collectors.toSet()));

    /**
     * Creates and returns a Stream of the words in the given text. Words are a
     * group of characters separated by one or more white spaces.
     *
     * @param text containing zero or more words
     * @return a Stream of the words in the given text
     */
    public static Stream<String> wordsOf(String text) {
        requireNonNull(text);
        return WORDS.splitAsStream(text);
    }

    /**
     * Creates and returns a String formatted so it will fit in a box with the
     * given width.
     *
     * @param text the input text
     * @param width of the text box
     * @return a String formatted so it will fit in a box with the given width
     */
    public static String formatTextBox(String text, int width) {
        requireNonNull(text);
        return formatTextBox(text, width, WORDS, Collections.emptySet());
    }

    /**
     * Creates and returns a String formatted so it will fit in a box with the
     * given width. Words in the singleRowers set will appear on a separate
     * line.
     *
     * @param text the input text
     * @param width of the text box
     * @param splitter pattern to use for splitting up words
     * @param singleRowers a Set of Strings that shall be on a separate row
     * @return a String formatted so it will fit in a box with the given width
     */
    public static String formatTextBox(String text, int width, Pattern splitter, Set<String> singleRowers) {
        requireNonNulls(text, splitter);
        requireNonNullElements(singleRowers);

        final StringBuilder sb = new StringBuilder();
        final AtomicInteger col = new AtomicInteger();
        
        splitter.splitAsStream(text)
            .map(w -> w.replace("\t", Formatting.tab()))
            .forEachOrdered(w -> {
                final int wordLen = w.length() - Math.max(w.lastIndexOf("\n"), 0);
                
                if (singleRowers.contains(w)) {
                    sb.append(NL);
                    sb.append(w);
                    sb.append(NL);
                    col.set(0);
                } else {
                    if (col.get() + wordLen >= width) {
                        sb.append(NL);
                        col.set(0);
                    } else if (col.get() > 0) {
                        sb.append(" ");
                        col.incrementAndGet();
                    }
                    sb.append(w);
                    col.getAndAdd(wordLen);
                }
            });
        
        return sb.toString();
    }

    /**
     * Creates and returns a String formatted so it will fit in a box suitable
     * for a Java Doc header.
     *
     * @param text the input text
     * @return a String formatted so it will fit in a box suitable for a Java
     * Doc header
     */
    public static String formatJavaDocBox(String text) {
        requireNonNull(text);
        return formatTextBox(text, JAVA_DOC_WIDTH, JAVADOC_WORDS, JAVA_DOC_SINGLE_LINE_WORDS);
    }
    
    /**
     * Returns a string of the specified length where the substring is located
     * in the middle, padded with spaces on both sides. If uneven, an extra
     * space will be added to the right side. If the specified 
     * {@code totalWidth} is less than the length of the substring, the
     * substring is returned but with the overflowing characters removed.
     * 
     * @param substring   the substring to align
     * @param totalWidth  the width of the returned string
     * @return            the padded string
     */
    public static String alignCenter(String substring, int totalWidth) {
        return alignCenter(substring, totalWidth, ' ');
    }
 
    /**
     * Returns a string of the specified length where the substring is located
     * in the middle, padded with a character on both sides. If uneven, an extra
     * space will be added to the right side. If the specified 
     * {@code totalWidth} is less than the length of the substring, the
     * substring is returned but with the overflowing characters removed.
     * 
     * @param substring   the substring to align
     * @param totalWidth  the width of the returned string
     * @param fill        the character to use for padding
     * @return            the padded string
     */
    public static String alignCenter(String substring, int totalWidth, char fill) {
        if (substring.length() > totalWidth) {
            return substring.substring(0, totalWidth);
        } else {
            final double padding = (totalWidth - substring.length()) / 2d;
            final int left  = (int) Math.floor(padding);
            final int right = (int) Math.ceil(padding);
            return repeat("" + fill, left) + substring + repeat("" + fill, right);
        }
    }

    /**
     * Returns a string of the specified length where the substring is located
     * to the left, padded spaces on the right. If the specified 
     * {@code totalWidth} is less than the length of the substring, the
     * substring is returned but with the overflowing characters removed.
     * 
     * @param substring   the substring to align
     * @param totalWidth  the width of the returned string
     * @return            the padded string
     */
    public static String alignLeft(String substring, int totalWidth) {
        return alignLeft(substring, totalWidth, ' ');
    }
    
    /**
     * Returns a string of the specified length where the substring is located
     * to the left, padded with a character on the right. If the specified 
     * {@code totalWidth} is less than the length of the substring, the
     * substring is returned but with the overflowing characters removed.
     * 
     * @param substring   the substring to align
     * @param totalWidth  the width of the returned string
     * @param fill        the character to use for padding
     * @return            the padded string
     */
    public static String alignLeft(String substring, int totalWidth, char fill) {
        if (substring.length() > totalWidth) {
            return substring.substring(0, totalWidth);
        } else {
            return substring + repeat("" + fill, totalWidth - substring.length());
        }
    }
    
    /**
     * Returns a string of the specified length where the substring is located
     * to the right, padded with spaces on the left. If the specified 
     * {@code totalWidth} is less than the length of the substring, the
     * substring is returned but with the overflowing characters removed.
     * 
     * @param substring   the substring to align
     * @param totalWidth  the width of the returned string
     * @return            the padded string
     */
    public static String alignRight(String substring, int totalWidth) {
        return alignRight(substring, totalWidth, ' ');
    }

    /**
     * Returns a string of the specified length where the substring is located
     * to the right, padded with a character on the left. If the specified 
     * {@code totalWidth} is less than the length of the substring, the
     * substring is returned but with the overflowing characters removed.
     * 
     * @param substring   the substring to align
     * @param totalWidth  the width of the returned string
     * @param fill        the character to use for padding
     * @return            the padded string
     */
    public static String alignRight(String substring, int totalWidth, char fill) {
        if (substring.length() > totalWidth) {
            return substring.substring(0, totalWidth);
        } else {
            return repeat("" + fill, totalWidth - substring.length()) + substring;
        }
    }

    /**
     * Repeats the specified substring a number of times.
     *
     * @param str    the string to repeat
     * @param count  the number of times to repeat it
     * @return       the new string
     */
    public static String repeat(String str, int count) {
        final StringBuilder result = new StringBuilder(str.length() * count);

        for (int i = 0; i < count; i++) {
            result.append(str);
        }

        return result.toString();
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private TextUtil() { instanceNotAllowed(getClass()); }
}
