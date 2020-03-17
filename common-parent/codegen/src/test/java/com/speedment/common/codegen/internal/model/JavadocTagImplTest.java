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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.JavadocTag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class JavadocTagImplTest extends AbstractTest<JavadocTag> {

    private final static String NAME = "A";

    public JavadocTagImplTest() {
        super(() -> new JavadocTagImpl(NAME),
                a -> a.imports(List.class),
                a -> a.setText("V"),
                a -> a.setValue("V"),
                a -> a.imports(Integer.class),
                a -> a.setText("V"),
                a -> a.setName("B")
        );
    }

    @Test
    void constructor() {
        final String text = "T";
        final JavadocTag tag = new JavadocTagImpl(NAME, text);
        assertEquals(text, tag.getText().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void constructor2() {
        final String text = "T";
        final String value = "V";
        final JavadocTag tag = new JavadocTagImpl(NAME, value, text);
        assertEquals(value, tag.getValue().orElseThrow(NoSuchElementException::new));
        assertEquals(text, tag.getText().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void imports() {
        assertTrue(instance().getImports().isEmpty());
    }

    @Test
    void getValue() {
        assertFalse(instance().getValue().isPresent());
    }

}