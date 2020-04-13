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
package com.speedment.common.codegen.util;

import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Utility methods for collecting streams in various ways.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.1
 */
public final class CollectorUtil {

    private CollectorUtil() { }

    /**
     * Similar to the 
     * {@link Collectors#joining(CharSequence, CharSequence, CharSequence)}
     * method except that this method surrounds the result with the specified
     * {@code prefix} and {@code suffix} even if the stream is empty.
     *
     * @param delimiter  the delimiter to separate the strings
     * @param prefix     the prefix to put before the result
     * @param suffix     the suffix to put after the result
     * @return           a {@link Collector} for joining string elements
     */
    public static Collector<String, ?, String> joinIfNotEmpty(String delimiter, String prefix, String suffix) {
        return Collector.of(
                () -> new StringJoiner(delimiter),
                StringJoiner::add,
                StringJoiner::merge,
                s -> s.length() > 0
                        ? prefix + s + suffix
                        : s.toString()
        );
    }

}
