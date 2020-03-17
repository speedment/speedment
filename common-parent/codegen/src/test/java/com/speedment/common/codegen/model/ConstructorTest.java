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
package com.speedment.common.codegen.model;

import com.speedment.common.codegen.model.modifier.Modifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

final class ConstructorTest {

    @Test
    void of() {
        assertCorrect(Constructor.of());
    }

    @Test
    void newPublic() {
        assertCorrect(Constructor.newPublic(), Modifier.PUBLIC);
    }

    @Test
    void newPrivate() {
        assertCorrect(Constructor.newPrivate(), Modifier.PRIVATE);
    }

    @Test
    void newProtected() {
        assertCorrect(Constructor.newProtected(), Modifier.PROTECTED);
    }

    void assertCorrect(Constructor constructor, Modifier... modifiers) {
        assertTrue(constructor.getFields().isEmpty());
        assertTrue(constructor.getCode().isEmpty());
        assertEquals(Stream.of(modifiers).collect(toSet()), constructor.getModifiers());
    }

}