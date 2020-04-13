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
package com.speedment.runtime.field;

import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 *
 * @author pemi
 */
final class RerferenceFieldTest {

    private final ReferenceField<TestEntity, String, String> field;

    public RerferenceFieldTest() {
        field = ReferenceField.create(
            TestEntity.Identifier.NAME,
            TestEntity::getName,
            TestEntity::setName,
            TypeMapper.identity(),
            false
        );
    }

    @Test
    void testAll() {
        final RerferenceFieldTestSupport<String> support = new RerferenceFieldTestSupport<>(field, Function.identity(), TestEntity::getName, TestEntity::setName, RerferenceFieldTestSupport.NAME, "Sven");
        support.testAll();
    }

}
