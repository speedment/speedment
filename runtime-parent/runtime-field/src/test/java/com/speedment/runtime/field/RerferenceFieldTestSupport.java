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

import com.speedment.runtime.compute.*;
import com.speedment.runtime.field.exception.SpeedmentFieldException;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pemi
 */
final class RerferenceFieldTestSupport extends BaseFieldTest {

    private static final String NAME = "TRYGGVE";
    private static final int ID = 42;
    private static final int NULL_ID = 42;
    private static final TestEntity TEST_ENTITY = new TestEntityImpl(ID, NAME);
    private static final TestEntity NULL_TEST_ENTITY = new TestEntityImpl(NULL_ID, null);

    private final ReferenceField<TestEntity, String, String> field;

    public RerferenceFieldTestSupport(ReferenceField<TestEntity, String, String> field) {
        this.field = requireNonNull(field);
        setUp();
    }

    void testAll() {
        testGetter();
        testSetter();
        testIsNull();
        testIsNotNull();
        testIsNullNegated();
        testIsNotNullNegated();
        tableAlias();
        mapToByteIfPresent();
        asByte();
        mapToShortIfPresent();
        asShort();
        mapToIntIfPresent();
        asInt();
        mapToLongIfPresent();
        asLong();
        mapToFloatIfPresent();
        asFloat();
        mapToDoubleIfPresent();
        asDouble();
        mapToBoolean();
        asBoolean();
    }

    /*@Test*/
    void testGetter() {
        final ReferenceGetter<TestEntity, String> result = field.getter();
        final ReferenceGetter<TestEntity, String> expected = (TestEntity e) -> e.getName();
        final TestEntity e = new TestEntityImpl(45, "Arne");
        assertEquals(expected.apply(e), result.apply(e));
    }

    /*@Test*/
    void testSetter() {
        final ReferenceSetter<TestEntity, String> result = field.setter();
        final TestEntity e = new TestEntityImpl(45, "Arne");
        result.accept(e, "Tryggve");
        assertEquals("Tryggve", e.getName());
    }

    /*@Test*/
    void testIsNull() {
        final List<TestEntity> result = collect(field.isNull());
        final List<TestEntity> expected = collect(e -> e.getName() == null);
        assertEquals(expected, result);
    }

/*    @Test*/
    void testIsNotNull() {
        final List<TestEntity> result = collect(field.isNotNull());
        final List<TestEntity> expected = collect(e -> e.getName() != null);
        assertEquals(expected, result);
    }

  /*  @Test*/
    void testIsNullNegated()  {
        final List<TestEntity> result = collect(field.isNull().negate());
        final List<TestEntity> expected = collect(e -> e.getName() != null);
        assertEquals(expected, result);
    }
    
/*    @Test*/
    void testIsNotNullNegated()  {
        final List<TestEntity> result = collect(field.isNotNull().negate());
        final List<TestEntity> expected = collect(e -> e.getName() == null);
        assertEquals(expected, result);
    }

    void tableAlias()  {
        final String result = field.tableAlias();
        assertNotNull(result);
    }

    void mapToByteIfPresent()  {
        final ToByteNullable<TestEntity> mapper = field.mapToByteIfPresent(s -> (byte) s.length());
        assertEquals(NAME.length(), mapper.applyAsByte(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asByte()  {
        final ToByteNullable<TestEntity> mapper = field.asByte();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsByte(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToShortIfPresent()  {
        final ToShortNullable<TestEntity> mapper = field.mapToShortIfPresent(s -> (byte) s.length());
        assertEquals(NAME.length(), mapper.applyAsShort(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asShort()  {
        final ToShortNullable<TestEntity> mapper = field.asShort();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsShort(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToIntIfPresent()  {
        final ToIntNullable<TestEntity> mapper = field.mapToIntIfPresent(s -> (byte) s.length());
        assertEquals(NAME.length(), mapper.applyAsInt(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asInt()  {
        final ToIntNullable<TestEntity> mapper = field.asInt();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsInt(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToLongIfPresent()  {
        final ToLongNullable<TestEntity> mapper = field.mapToLongIfPresent(s -> (byte) s.length());
        assertEquals(NAME.length(), mapper.applyAsLong(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asLong()  {
        final ToLongNullable<TestEntity> mapper = field.asLong();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsLong(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToFloatIfPresent()  {
        final ToFloatNullable<TestEntity> mapper = field.mapToFloatIfPresent(s -> (byte) s.length());
        assertEquals(NAME.length(), mapper.applyAsFloat(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asFloat()  {
        final ToFloatNullable<TestEntity> mapper = field.asFloat();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsFloat(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToDoubleIfPresent()  {
        final ToDoubleNullable<TestEntity> mapper = field.mapToDoubleIfPresent(s -> (byte) s.length());
        assertEquals(NAME.length(), mapper.applyAsDouble(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asDouble()  {
        final ToDoubleNullable<TestEntity> mapper = field.asDouble();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsDouble(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }


    void mapToBoolean()  {
        final ToBooleanNullable<TestEntity> mapper = field.mapToBooleanIfPresent(String::isEmpty);
        assertFalse(mapper.applyAsBoolean(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asBoolean()  {
        final ToBooleanNullable<TestEntity> mapper = field.asBoolean();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsBoolean(TEST_ENTITY)); // Not a Boolean so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Boolean so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }



}
