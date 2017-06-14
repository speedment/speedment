/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.logger.internal.formatter;

import com.speedment.common.logger.Level;
import com.speedment.common.logger.LoggerFormatter;
import java.time.Instant;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import java.util.stream.IntStream;

/**
 *
 * @author pemi
 */
public final class PlainFormatter implements LoggerFormatter {

    // Different Java versions have different resolution. Get a dynamic reference
    private static final String INSTANT_STAMPLE = Instant.ofEpochSecond(0, 1_000_000).toString();
    private static final int DECIMALS = decimals();
    private static String PADDING_REF = "." + IntStream.range(0, DECIMALS).mapToObj(i -> "0").collect(joining());
    private static final int INSTANT_LENGTH = INSTANT_STAMPLE.length();

    @Override
    public String apply(Level level, String name, String message) {
        requireNonNull(level);
        requireNonNull(name);

        return new StringBuilder()
            .append(formatInstance(Instant.now().toString()))
            .append(" ")
            .append(level.toText())
            .append(" [")
            .append(Thread.currentThread().getName())
            .append("] (")
            .append(name)
            .append(") - ")
            .append(message)
            .toString();
    }

    protected String formatInstance(final String instantString) {
        if (instantString.length() >= INSTANT_LENGTH) {
            return instantString;
        }
        final int missingCharacters = INSTANT_LENGTH - instantString.length();
        if (missingCharacters > PADDING_REF.length()) {
            // Huston, we've had a problem... Just return to Earth...
            return instantString;
        }
        final String padding = PADDING_REF.substring(PADDING_REF.length() - missingCharacters);
        return instantString.substring(0, instantString.length() - 1) + padding + "Z";
    }

    private static int decimals() {
        final int indexOfDot = INSTANT_STAMPLE.lastIndexOf(".");
        final int indexOfZ = INSTANT_STAMPLE.lastIndexOf("Z");
        if (indexOfDot > 0 && indexOfZ > 0) {
            final int decimals = indexOfZ - indexOfDot - 1;
            return decimals;
        }

        return 6; // Default to 6
    }

}
