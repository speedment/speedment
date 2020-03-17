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
package com.speedment.common.codegen.internal;

import com.speedment.common.codegen.*;
import com.speedment.common.codegen.provider.StandardTransformFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class MetaImplTest {

    private Meta<Integer, String> instance;
    private Transform<Integer, String> transform;
    private TransformFactory factory;
    private RenderStack renderStack;
    private RenderTree renderTree;

    @BeforeEach
    void setup() {
        transform = (g, i) -> Optional.of(Integer.toString(i));
        factory = new StandardTransformFactory("T");
        renderStack = new DefaultRenderStack();
        renderTree = RenderTree.builder().build();
        instance = Meta.builder(1, "1")
                .withTransform(transform)
                .withFactory(factory)
                .withRenderStack(renderStack)
                .withRenderTree(renderTree)
                .build();
    }

    @Test
    void getResult() {
        assertEquals("1", instance.getResult());
    }

    @Test
    void getTransform() {
        assertEquals(transform, instance.getTransform());
    }

    @Test
    void getFactory() {
        assertEquals(factory, instance.getFactory());
    }

    @Test
    void getModel() {
        assertEquals(1, instance.getModel());
    }

    @Test
    void getRenderStack() {
        assertEquals(renderStack, instance.getRenderStack());
    }

    @Test
    void getRenderTree() {
        assertEquals(renderTree, instance.getRenderTree());
    }

    @Test
    void testToString() {
        final String s = instance.toString();
        assertTrue(s.contains("model"));
        assertTrue(s.contains("result"));
        assertTrue(s.contains("1"));
    }
}