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

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.trait.*;
import com.speedment.runtime.field.exception.SpeedmentFieldException;
import com.speedment.runtime.field.expression.FieldMapper;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.typemapper.bigdecimal.BigDecimalToDouble;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author pemi
 */
final class RerferenceFieldTestSupport<V> extends BaseFieldTest {

    public static final String NAME = "TRYGGVE";
    public static final TestEntity.TestEnum ENUM = TestEntity.TestEnum.TRYGGVE;

    private static final long VALUE = 1L;
    private static final int ID = 42;
    private static final int NULL_ID = 42;

    private static final TestEntity TEST_ENTITY = new TestEntityImpl(ID, NAME, ENUM);
    private static final TestEntity NULL_TEST_ENTITY = new TestEntityImpl(NULL_ID, null, null);

/*    private static final BasicNullableEntity NULL_BASIC_ENTITY = new BasicNullableEntity();
    private static final BasicNullableEntity BASIC_ENTITY = new BasicNullableEntity()
        .setVarByte((byte) VALUE)
        .setVarShort((short) VALUE)
        .setVarInt((int) VALUE)
        .setVarLong(VALUE)
        .setVarFloat((float) VALUE)
        .setVarDouble((double) VALUE)
        .setVarBigDecimal(BigDecimal.valueOf(VALUE))
        .setVarChar((char) VALUE);*/

    private final ReferenceField<TestEntity, String, V> field;
    private final Function<V, String> toStringMapper;
    private final Function<TestEntity, V> getter;
    private final BiConsumer<TestEntity, V> setter;

    private final V original;
    private final String originalAsString;
    private final V sample;

    public RerferenceFieldTestSupport(
        final ReferenceField<TestEntity, String, V> field,
        final Function<V, String> toStringMapper,
        final Function<TestEntity, V> getter,
        final BiConsumer<TestEntity, V> setter,
        final V original,
        final V sample
    ) {
        if (original.equals(sample)) {
            throw new IllegalArgumentException("original and sample cannot be equal");
        }
        this.field = requireNonNull(field);
        this.toStringMapper = requireNonNull(toStringMapper);
        this.getter = requireNonNull(getter);
        this.setter = requireNonNull(setter);
        this.original = requireNonNull(original);
        this.sample = requireNonNull(sample);
        this.originalAsString = requireNonNull(toStringMapper.apply(original));
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
        mapToChar();
        asChar();
        mapToBigDecimal();
        asBigDecimal();
        mapToEnum();
        asEnum();
        mapToString();
        if (original instanceof String) {
            // only run if the field is a String field
            asString();
        }
    }

    /*@Test*/
    void testGetter() {
        final V expected = getter.apply(TEST_ENTITY);
        final V actual = field.getter().apply(TEST_ENTITY);
        assertEquals(expected, actual);
    }

    /*@Test*/
    void testSetter() {
        final ReferenceSetter<TestEntity, V> result = field.setter();
        final TestEntity copy = TEST_ENTITY.copy(); // Make sure we do not alter the original
        result.accept(copy, sample);
        assertEquals(sample, getter.apply(copy));
    }

    /*@Test*/
    void testIsNull() {
        final List<TestEntity> result = collect(field.isNull());
        final List<TestEntity> expected = collect(e -> getter.apply(e) == null);
        assertEquals(expected, result);
    }

    /*    @Test*/
    void testIsNotNull() {
        final List<TestEntity> result = collect(field.isNotNull());
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null);
        assertEquals(expected, result);
    }

    /*  @Test*/
    void testIsNullNegated() {
        final List<TestEntity> result = collect(field.isNull().negate());
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null);
        assertEquals(expected, result);
    }

    /*    @Test*/
    void testIsNotNullNegated() {
        final List<TestEntity> result = collect(field.isNotNull().negate());
        final List<TestEntity> expected = collect(e -> getter.apply(e) == null);
        assertEquals(expected, result);
    }

    void tableAlias() {
        final String result = field.tableAlias();
        assertNotNull(result);
    }

    void mapToByteIfPresent() {
        final ToByteNullable<TestEntity> mapper = field.mapToByteIfPresent(v -> (byte) toStringMapper.apply(v).length());
        assertEquals(originalAsString.length(), mapper.applyAsByte(TEST_ENTITY));
        assertEquals((byte) originalAsString.length(), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.BYTE_NULLABLE, (byte) 10);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.BYTE_NULLABLE, (byte) 10);
        testNullEntity(mapper, NULL_TEST_ENTITY, (byte) 10);
    }

    void asByte() {
        final ToByteNullable<TestEntity> mapper = field.asByte();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsByte(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToShortIfPresent() {
        final ToShortNullable<TestEntity> mapper = field.mapToShortIfPresent(v -> (byte) toStringMapper.apply(v).length());
        assertEquals(originalAsString.length(), mapper.applyAsShort(TEST_ENTITY));
        assertEquals((short) originalAsString.length(), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.SHORT_NULLABLE, (short) 10);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.SHORT_NULLABLE, (short) 10);
        testNullEntity(mapper, NULL_TEST_ENTITY, (short) 10);
    }

    void asShort() {
        final ToShortNullable<TestEntity> mapper = field.asShort();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsShort(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToIntIfPresent() {
        final ToIntNullable<TestEntity> mapper = field.mapToIntIfPresent(v -> (byte) toStringMapper.apply(v).length());
        assertEquals(originalAsString.length(), mapper.applyAsInt(TEST_ENTITY));
        assertEquals(originalAsString.length(), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.INT_NULLABLE, 10);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.INT_NULLABLE, 10);
        testNullEntity(mapper, NULL_TEST_ENTITY, 10);
    }

    void asInt() {
        final ToIntNullable<TestEntity> mapper = field.asInt();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsInt(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToLongIfPresent() {
        final ToLongNullable<TestEntity> mapper = field.mapToLongIfPresent(v -> (byte) toStringMapper.apply(v).length());
        assertEquals(originalAsString.length(), mapper.applyAsLong(TEST_ENTITY));
        assertEquals(originalAsString.length(), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.LONG_NULLABLE, 10L);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.LONG_NULLABLE, 10L);
        testNullEntity(mapper, NULL_TEST_ENTITY, 10L);
    }

    void asLong() {
        final ToLongNullable<TestEntity> mapper = field.asLong();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsLong(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToFloatIfPresent() {
        final ToFloatNullable<TestEntity> mapper = field.mapToFloatIfPresent(v -> (byte) toStringMapper.apply(v).length());
        assertEquals(originalAsString.length(), mapper.applyAsFloat(TEST_ENTITY));
        assertEquals(originalAsString.length(), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.FLOAT_NULLABLE, 10f);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.FLOAT_NULLABLE, 10f);
        testNullEntity(mapper, NULL_TEST_ENTITY, 10f);
    }

    void asFloat() {
        final ToFloatNullable<TestEntity> mapper = field.asFloat();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsFloat(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToDoubleIfPresent() {
        final ToDoubleNullable<TestEntity> mapper = field.mapToDoubleIfPresent(v -> (byte) toStringMapper.apply(v).length());
        assertEquals(originalAsString.length(), mapper.applyAsDouble(TEST_ENTITY));
        assertEquals(originalAsString.length(), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.DOUBLE_NULLABLE, 10d);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.DOUBLE_NULLABLE, 10d);
        testNullEntity(mapper, NULL_TEST_ENTITY, 10d);
    }

    void asDouble() {
        final ToDoubleNullable<TestEntity> mapper = field.asDouble();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsDouble(TEST_ENTITY)); // Not a Number so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Number so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }


    void mapToBoolean() {
        final ToBooleanNullable<TestEntity> mapper = field.mapToBooleanIfPresent(v -> toStringMapper.apply(v).isEmpty());
        assertFalse(mapper.applyAsBoolean(TEST_ENTITY));
        assertFalse(mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.BOOLEAN_NULLABLE, Boolean.TRUE);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.BOOLEAN_NULLABLE, Boolean.TRUE);
    }

    void asBoolean() {
        final ToBooleanNullable<TestEntity> mapper = field.asBoolean();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsBoolean(TEST_ENTITY)); // Not a Boolean so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not a Boolean so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToChar() {
        final ToCharNullable<TestEntity> mapper = field.mapToCharIfPresent(v -> toStringMapper.apply(v).charAt(0));
        assertEquals(originalAsString.charAt(0), mapper.applyAsChar(TEST_ENTITY));
        assertEquals(originalAsString.charAt(0), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.CHAR_NULLABLE, 'b');
        test(mapper, NULL_TEST_ENTITY, ExpressionType.CHAR_NULLABLE, 'b');
    }

    void asChar() {
        final ToCharNullable<TestEntity> mapper = field.asChar();
        assertThrows(SpeedmentFieldException.class, () -> mapper.applyAsChar(TEST_ENTITY)); // Not assignable so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not assignable so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToBigDecimal() {
        final ToBigDecimalNullable<TestEntity> mapper = field.mapToBigDecimalIfPresent(s -> new BigDecimal(toStringMapper.apply(s).chars().sum()));
        assertEquals(new BigDecimal(originalAsString.chars().sum()).intValue(), mapper.apply(TEST_ENTITY).intValue());
        assertNull(mapper.apply(NULL_TEST_ENTITY));

        test(mapper, TEST_ENTITY, ExpressionType.BIG_DECIMAL_NULLABLE, BigDecimal.TEN);
        test(mapper, NULL_TEST_ENTITY, ExpressionType.BIG_DECIMAL_NULLABLE, BigDecimal.TEN);
        testNullEntity(mapper, NULL_TEST_ENTITY, BigDecimal.TEN);
    }

    void asBigDecimal() {
        final ToBigDecimalNullable<TestEntity> mapper = field.asBigDecimal();
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not assignable so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not assignable so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    enum Names {OLLE, SVEN, GLENN, TRYGGVE, AJJWHHAUGHGHHG}

    void mapToEnum() {
        final ToEnumNullable<TestEntity, Names> mapper = field.mapToEnumIfPresent(v -> Names.valueOf(toStringMapper.apply(v).toUpperCase()), Names.class);
        assertEquals(Names.TRYGGVE, mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asEnum() {
        final ToEnumNullable<TestEntity, Names> mapper = field.asEnum(Names.class);
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not assignable so it will fail
        assertThrows(SpeedmentFieldException.class, () -> mapper.apply(TEST_ENTITY)); // Not assignable so it will fail
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    void mapToString() {
        final ToStringNullable<TestEntity> mapper = field.mapToStringIfPresent(v -> toStringMapper.apply(v).toUpperCase());
        assertEquals(originalAsString.toUpperCase(), mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY));
    }

    void asString() {
        final ToStringNullable<TestEntity> mapper = field.asString();
        assertEquals(originalAsString, mapper.apply(TEST_ENTITY));
        assertNull(mapper.apply(NULL_TEST_ENTITY)); // Null so it will return null
    }

    private <R, T extends
        Expression<TestEntity> &
        ToNullable<TestEntity, R, ?> &
        HasHash<TestEntity> &
        HasCompare<TestEntity>
        >
    void test(T mapper, TestEntity entity, ExpressionType expressionType, R alternateValue) {
        assertEquals(expressionType, mapper.expressionType());
        assertEquals(getter.apply(entity) == null, mapper.isNull(entity));
        assertEquals(getter.apply(entity) == null, mapper.isNull().test(entity));
        assertEquals(getter.apply(entity) != null, mapper.isNotNull(entity));
        assertEquals(getter.apply(entity) != null, mapper.isNotNull().test(entity));
        if (getter.apply(entity) != null) {
            assertNotEquals(0, mapper.hash(entity), "mapper " + mapper + " produced hash = 0");
        }
    }

    private <R extends Number, NN extends HasAsInt<TestEntity> & Expression<TestEntity>, T extends
        Expression<TestEntity> &
        ToNullable<TestEntity, R, ? extends NN> &
        HasHash<TestEntity> &
        HasCompare<TestEntity>
        >
    void testNullEntity(T mapper, TestEntity entity, R alternateValue) {
        assertNull(entity.getName()); // Must be in order for the following test to run
        assertNumberAsIntEquals(alternateValue, mapper.orElse(alternateValue).asInt().applyAsInt(entity));
/*        assertEquals(alternateValue, mapper
            .orElseGet(mapper
                .orElse(alternateValue)
            )
            .asDouble()
            .applyAsDouble(entity)
        ); // A bit tricky */
        assertThrows(NullPointerException.class, () -> mapper.orThrow().asInt().applyAsInt(entity));
    }

    // Todo: add expressionType, orThrow, etc. on eah


    void assertNumberAsIntEquals(Number expected, Number actual) {
        assertEquals(expected.intValue(), actual.intValue());
    }


}
