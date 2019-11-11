/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.speedment.runtime.field.TestEntity.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author pemi
 */
final class RerferenceFieldTest extends BaseFieldTest {

    @Test
    void testGetter() throws Exception {
        final ReferenceGetter<TestEntity, String> result = NAME.getter();
        final ReferenceGetter<TestEntity, String> expected = (TestEntity e) -> e.getName();
        final TestEntity e = new TestEntityImpl(45, "Arne");
        assertEquals(expected.apply(e), result.apply(e));
    }

    @Test
    void testSetter() throws Exception {
        final ReferenceSetter<TestEntity, String> result = NAME.setter();
        final TestEntity e = new TestEntityImpl(45, "Arne");
        result.accept(e, "Tryggve");
        assertEquals("Tryggve", e.getName());
    }

    @Test
    void testIsNull() throws Exception {
        final List<TestEntity> result = collect(NAME.isNull());
        final List<TestEntity> expected = collect(e -> e.getName() == null);
        assertEquals(expected, result);
    }

    @Test
    void testIsNotNull() throws Exception {
        final List<TestEntity> result = collect(NAME.isNotNull());
        final List<TestEntity> expected = collect(e -> e.getName() != null);
        assertEquals(expected, result);
    }
    
    
    
    @Test
    void testIsNullNegated() throws Exception {
        final List<TestEntity> result = collect(NAME.isNull().negate());
        final List<TestEntity> expected = collect(e -> e.getName() != null);
        assertEquals(expected, result);
    }
    
    @Test
    void testIsNotNullNegated() throws Exception {
        final List<TestEntity> result = collect(NAME.isNotNull().negate());
        final List<TestEntity> expected = collect(e -> e.getName() == null);
        assertEquals(expected, result);
    }    

}
