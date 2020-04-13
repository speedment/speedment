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

/**
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class PrintUtil {

    private PrintUtil() {}

    public static final String HORIZONTAL_LINE = "+---------------------------------------------------------------------------------+";
    private static final String DOTS = "...";


    /**
     * Returns a String that is shortened to at most {@code length} by omitting characters in the given
     * {@code in } String middle and replacing these with "...".
     * <p>
     * If the given {@code length } is smaller than 5, then just limits to the first 5 characters.
     *
     * @param in String to limit
     * @param length the maximum resulting length
     * @return a String that is shortened by omitting characters in the middle and replacing these
     *         with "..."
     */
    public static String limit(String in, int length) {

        if (in.length() <= length) {
            return in;
        } else {
            if (length < 5) {
                return in.substring(0, length);
            }
            final int breakpoint = (length - DOTS.length()) / 2;
            final StringBuilder sb = new StringBuilder();
            sb.append(in.substring(0, breakpoint));
            sb.append(DOTS);
            final int suffixLength = length - sb.length();
            sb.append(in.substring(in.length() - suffixLength));
            return sb.toString();
        }
    }
    
}