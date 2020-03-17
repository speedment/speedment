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

import com.speedment.common.codegen.internal.java.view.InterfaceView;
import com.speedment.common.codegen.model.Class;
import com.speedment.common.codegen.model.Import;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class ImportImplTest extends AbstractTest<Import> {

    private final static String NAME = "A";

    public ImportImplTest() {
        super(() -> new ImportImpl(Long.class),
                a -> a.setParent(Class.of("A")),
                a -> a.setStaticMember("a"),
                a -> a.set(Integer.class),
                Import::static_
        );
    }

    @Test
    void setParent() {
        instance().setParent(Class.of(NAME));
        assertTrue(instance().getParent().isPresent());
    }

    @Test
    void getParent() {
        assertFalse(instance().getParent().isPresent());
    }

    @Test
    void set() {
        instance().set(String.class);
        assertEquals(String.class, instance().getType());
    }

    @Test
    void getType() {
        assertEquals(Long.class, instance().getType());
    }

    @Test
    void getModifiers() {
        assertTrue(instance().getModifiers().isEmpty());
    }

    @Test
    void getStaticMember() {
        assertEquals(Optional.empty(), instance().getStaticMember());
    }

    @Test
    void setStaticMember() {
        final String memeber = "A";
        instance().setStaticMember(memeber);
        assertEquals(memeber, instance().getStaticMember().orElseThrow(NoSuchElementException::new));
    }
}