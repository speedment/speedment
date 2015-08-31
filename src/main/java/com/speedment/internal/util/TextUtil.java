/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util;

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class TextUtil {

    //private static final Pattern WORDS = Pattern.compile("[\\.,\\s!;?:\"]+");
    private static final Pattern WORDS = Pattern.compile("[\\s]+");
    private static final Pattern JAVADOC_WORDS = Pattern.compile("[\\s]+"); // Text within html tags "pre" are treated as one "word"
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
        requireNonNulls(singleRowers);

        final StringBuilder sb = new StringBuilder();
        final AtomicInteger col = new AtomicInteger();
        //final AtomicInteger wordCount = new AtomicInteger();
        final List<String> words = splitter.splitAsStream(text).collect(toList());
        for (final String w : words) {
            final int wordLen = w.length();
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
            //wordCount.incrementAndGet();
        }
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
     * Utility classes should not be instantiated.
     */
    private TextUtil() { instanceNotAllowed(getClass()); }
}
