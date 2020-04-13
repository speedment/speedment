/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.injector.internal.util;

import java.util.StringJoiner;

/**
 * Utility class for formatting strings.
 *
 * @author Emil Forslund
 * @since  3.1.17
 */
public final class StringUtil {

    private StringUtil() {}

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
            default:
                return joinWordsWithAnd(words);
        }
    }

    private static String joinWordsWithAnd(String[] words) {
        final StringJoiner join = new StringJoiner(", ", "", " and ");
        for (int i = 0; i < words.length - 1; i++) {
            join.add(words[i]);
        }
        return join.toString() + words[words.length - 1];
    }

}
