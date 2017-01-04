/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.field;

import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import org.junit.Test;

import java.util.List;

import static com.speedment.runtime.core.internal.field.Entity.NAME;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author pemi
 */
public class RerferenceFieldTest extends BaseFieldTest {

    @Test
    public void testGetter() throws Exception {
        final ReferenceGetter<Entity, String> result = NAME.getter();
        final ReferenceGetter<Entity, String> expected = (Entity e) -> e.getName();
        final Entity e = new EntityImpl(45, "Arne");
        assertEquals(expected.apply(e), result.apply(e));
    }

    @Test
    public void testSetter() throws Exception {
        final ReferenceSetter<Entity, String> result = NAME.setter();
        final Entity e = new EntityImpl(45, "Arne");
        result.apply(e, "Tryggve");
        assertEquals("Tryggve", e.getName());
    }

    @Test
    public void testIsNull() throws Exception {
        final List<Entity> result = collect(NAME.isNull());
        final List<Entity> expected = collect(e -> e.getName() == null);
        assertEquals(expected, result);
    }

    @Test
    public void testIsNotNull() throws Exception {
        final List<Entity> result = collect(NAME.isNotNull());
        final List<Entity> expected = collect(e -> e.getName() != null);
        assertEquals(expected, result);
    }
    
    
    
    @Test
    public void testIsNullNegated() throws Exception {
        final List<Entity> result = collect(NAME.isNull().negate());
        final List<Entity> expected = collect(e -> e.getName() != null);
        assertEquals(expected, result);
    }
    
    @Test
    public void testIsNotNullNegated() throws Exception {
        final List<Entity> result = collect(NAME.isNotNull().negate());
        final List<Entity> expected = collect(e -> e.getName() == null);
        assertEquals(expected, result);
    }    

}
