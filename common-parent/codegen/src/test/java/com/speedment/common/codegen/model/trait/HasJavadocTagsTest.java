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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.JavadocTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class HasJavadocTagsTest {

    private static final String TEXT = "A";

    private HasJavadocTags<Javadoc> javadoc;
    private JavadocTag javadocTag;

    @BeforeEach
    void setup() {
        javadoc = Javadoc.of("a");
        javadocTag = JavadocTag.of("a", "b");
    }

    @Test
    void add() {
        javadoc.add(javadocTag);
        assertEquals(singletonList(javadocTag), javadoc.getTags());
    }

    @Test
    void author() {
        javadoc.author(TEXT);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("author", tag.getName());
        assertEquals(TEXT, tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void returns() {
        javadoc.returns(TEXT);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("return", tag.getName());
        assertEquals(TEXT, tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void since() {
        javadoc.since(TEXT);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("since", tag.getName());
        assertEquals(TEXT, tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void see() {
        javadoc.see(int.class);
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("see", tag.getName());
        assertEquals("{@link int}", tag.getValue().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void param() {
        javadoc.param("x", "x-coordinate");
        final JavadocTag tag = javadoc.getTags().iterator().next();
        assertEquals("param", tag.getName());
        assertEquals("x", tag.getValue().orElseThrow(NoSuchElementException::new));
        assertEquals("x-coordinate", tag.getText().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void getTags() {
        assertTrue(javadoc.getTags().isEmpty());
    }
}