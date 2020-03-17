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
package com.speedment.runtime.core.internal.runtime.typemapping;

import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.core.internal.component.resultset.StandardJavaTypeMapping;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author pemi
 */
final class StandardJavaTypeMappingTest {

    @Test
    void testParse() {
        testMapping(Boolean.class, Boolean.TRUE, m -> m.parse("true"));
        testMapping(Boolean.class, Boolean.FALSE, m -> m.parse("false"));
        testMapping(Byte.class,   (byte)  -47, m -> m.parse("-47"));
        testMapping(Short.class,  (short) -47, m -> m.parse("-47"));
        testMapping(Integer.class,        -47, m -> m.parse("-47"));
    }

    private static void testMapping(Class<?> javaType, Object expected, Function<ResultSetMapping<?>, Object> actual) {
        StandardJavaTypeMapping.stream()
            .filter(m -> javaType.equals(m.getJavaClass()))
            .forEach(m -> assertEquals(expected, actual.apply(m)));
    }
}
