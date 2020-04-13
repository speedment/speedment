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
package com.speedment.common.codegen.provider;

import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.TransformFactory;
import com.speedment.common.codegen.internal.DefaultDependencyManager;
import com.speedment.common.codegen.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

final class StandardGeneratorTest {

    private StandardGenerator INSTANCE = new StandardGenerator(new DefaultDependencyManager(), new StandardTransformFactory("A"));

    @Test
    void getDependencyMgr() {
        assertNotNull(INSTANCE.getDependencyMgr());
    }

    @Test
    void getRenderStack() {
        assertNotNull(INSTANCE.getRenderStack());
    }

    @Test
    void metaOn() {
        assertNotNull(INSTANCE.metaOn(Integer.class, String.class));
    }

    @Test
    void transform() {
        final TransformFactory factory = new StandardTransformFactory("A");
        final Transform<Integer, String> transform = (g, i) -> Optional.of(Integer.toString(i));
        factory.install(Integer.class, String.class, () -> transform);
        assertNotNull(INSTANCE.transform(transform, 1, factory));
    }
}