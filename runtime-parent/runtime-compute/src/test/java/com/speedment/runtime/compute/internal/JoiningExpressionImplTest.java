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
package com.speedment.runtime.compute.internal;

import static org.junit.jupiter.api.Assertions.*;

import com.speedment.runtime.compute.ToString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class JoiningExpressionImplTest {

    private static final CharSequence SEPARATOR = " ";
    private static final CharSequence PREFIX = "prefix";
    private static final CharSequence SUFFIX = "suffix";
    private static final ToString<String> FIRST = String::toUpperCase;
    private static final ToString<String> SECOND = String::toLowerCase;

    private final JoiningExpressionImpl<String> instance = new JoiningExpressionImpl<>(
            SEPARATOR,
            PREFIX,
            SUFFIX,
            new ArrayList<ToString<String>>() {
                {add(FIRST);}
                {add(SECOND);}
            }
    );

    @Test
    void expressions() {
        assertEquals(2, instance.expressions().size());
    }

    @Test
    void prefix() {
        assertEquals(PREFIX, instance.prefix());
    }

    @Test
    void suffix() {
        assertEquals(SUFFIX, instance.suffix());
    }

    @Test
    void separator() {
        assertEquals(SEPARATOR, instance.separator());
    }

    @ParameterizedTest
    @ValueSource(strings = {"foo", "bar"})
    void apply(String input) {
        assertEquals(PREFIX + FIRST.apply(input) + SEPARATOR + SECOND.apply(input) + SUFFIX,
                instance.apply(input));
    }
}
