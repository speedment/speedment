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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.field.internal.comparator;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.ByteField;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 *
 * @author Per Minborg
 */
final class ByteFieldComparatorImplTest {

    ByteFieldComparatorImpl<ByteValue, Byte> instance;

    @BeforeEach
    void setUp() {
        instance = new ByteFieldComparatorImpl<>(ByteValue.BYTE);
    }

    @Test
    void testGetField() {
        final ByteField<ByteValue, Byte> expResult = ByteValue.BYTE;
        final ByteField<ByteValue, Byte> result =
            (ByteField<ByteValue, Byte>) instance.getField();
        assertEquals(expResult, result);
    }

    @Test
    void testGetNullOrder() {
        final NullOrder expResult = NullOrder.NONE;
        final NullOrder result = instance.getNullOrder();
        assertEquals(expResult, result);
    }

    @Test
    void testIsReversed() {
        boolean expResult = false;
        boolean result = instance.isReversed();
        assertEquals(expResult, result);
    }

    @Test
    void testReversed() {
        final FieldComparator<ByteValue> expResult = new ByteFieldComparatorImpl<>(ByteValue.BYTE, true);
        final FieldComparator<ByteValue> result = instance.reversed();
        assertEquals(expResult, result);
        assertTrue(result.isReversed());
    }

    @Test
    void testCompareSimple() {
        final ByteValue first = ByteValue.of((byte) 0);
        final ByteValue second = ByteValue.of((byte) 1);
        int expResult = -1;
        int result = instance.compare(first, second);
        assertEquals(expResult, result);
    }

    @Test
    void testCompareAllTheThings() {
        IntStream.rangeClosed(Byte.MIN_VALUE, Byte.MAX_VALUE).forEach(first -> {
            IntStream.rangeClosed(Byte.MIN_VALUE, Byte.MAX_VALUE).forEach(second -> {
                final ByteValue firstByteValue = ByteValue.of((byte) first);
                final ByteValue secondByteValue = ByteValue.of((byte) second);
                final int expResult = signum(Byte.compare(firstByteValue.getByte(), secondByteValue.getByte()));
                final int result = signum(instance.compare(firstByteValue, secondByteValue));
                assertEquals(expResult, result);
            });
        });
    }

    private int signum(int b) {
        if (b == 0) {
            return 0;
        }
        if (b > 0) {
            return 1;
        }
        return -1;
    }

    @Test
    void testHashCode() {
        final int expResult = new ByteFieldComparatorImpl<>(ByteValue.BYTE, false).reversed().reversed().hashCode();
        final int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    @Test
    void testEquals() {
        final ByteFieldComparatorImpl<ByteValue, Byte> result = new ByteFieldComparatorImpl<>(ByteValue.BYTE, false);
        assertEquals(instance, result);
    }

    @Test
    void testToString() {
        final String result = instance.toString();
        assertNotNull(result);
    }

    private interface ByteValue {

        byte getByte();

        ByteValue setByte(byte value);

        ColumnIdentifier<ByteValue> BYTE_IDENTIFIER = new ColumnIdentifier<ByteValue>() {
            @Override
            public String getDbmsId() {
                return "db0";
            }

            @Override
            public String getSchemaId() {
                return "schema";
            }

            @Override
            public String getTableId() {
                return "byte_value";
            }

            @Override
            public String getColumnId() {
                return "byte";
            }

        };

        ByteField<ByteValue, Byte> BYTE = ByteField.create(
            BYTE_IDENTIFIER,
            ByteValue::getByte,
            ByteValue::setByte,
            TypeMapper.identity(),
            false
        );

        static ByteValue of(byte value) {
            return new ByteValueImpl(value);
        }

    }

    private static class ByteValueImpl implements ByteValue {

        private byte value;

        public ByteValueImpl(byte value) {
            this.value = value;
        }

        @Override
        public byte getByte() {
            return value;
        }

        @Override
        public ByteValue setByte(byte value) {
            this.value = value;
            return this;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof ByteValue)) {
                return false;
            }
            ByteValue that = (ByteValue) obj;
            return this.getByte() == that.getByte();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return Byte.toString(value);
        }

    }

}
