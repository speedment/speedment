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
import java.util.Objects;
import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class ByteFieldComparatorImplTest {

    ByteFieldComparatorImpl<ByteValue, Byte> instance;

    @Before
    public void setUp() {
        instance = new ByteFieldComparatorImpl<>(ByteValue.BYTE);
    }

    @Test
    public void testGetField() {
        final ByteField<ByteValue, Byte> expResult = ByteValue.BYTE;
        final ByteField<ByteValue, Byte> result = instance.getField();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNullOrder() {
        final NullOrder expResult = NullOrder.NONE;
        final NullOrder result = instance.getNullOrder();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsReversed() {
        boolean expResult = false;
        boolean result = instance.isReversed();
        assertEquals(expResult, result);
    }

    @Test
    public void testReversed() {
        final FieldComparator<ByteValue, Byte> expResult = new ByteFieldComparatorImpl<>(ByteValue.BYTE, true);
        final FieldComparator<ByteValue, Byte> result = instance.reversed();
        assertEquals(expResult, result);
        assertTrue(result.isReversed());
    }

    @Test
    public void testCompareSimple() {
        final ByteValue first = ByteValue.of((byte) 0);
        final ByteValue second = ByteValue.of((byte) 1);
        int expResult = -1;
        int result = instance.compare(first, second);
        assertEquals(expResult, result);
    }

    @Test
    public void testCompareABunch() {
        final Random rnd = new Random();

        final byte[] bytes = new byte[2];
        for (int i = 0; i < 100_000; i++) {
            rnd.nextBytes(bytes);
            final ByteValue first = ByteValue.of(bytes[0]);
            final ByteValue second = ByteValue.of(bytes[1]);
            final int expResult = signum(Byte.compare(first.getByte(), second.getByte()));
            final int result = signum(instance.compare(first, second));
            assertEquals(expResult, result);
        }
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
    public void testHashCode() {
        final int expResult = new ByteFieldComparatorImpl<>(ByteValue.BYTE, false).reversed().reversed().hashCode();
        final int result = instance.hashCode();
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals() {
        final ByteFieldComparatorImpl<ByteValue, Byte> result = new ByteFieldComparatorImpl<>(ByteValue.BYTE, false);
        assertEquals(instance, result);
    }

    @Test
    public void testToString() {
        final String result = instance.toString();
        assertNotNull(result);
    }

    private interface ByteValue {

        byte getByte();

        ByteValue setByte(byte value);

        final ColumnIdentifier BYTE_IDENTIFIER = new ColumnIdentifier<ByteValue>() {
            @Override
            public String getDbmsName() {
                return "db0";
            }

            @Override
            public String getSchemaName() {
                return "schema";
            }

            @Override
            public String getTableName() {
                return "byte_value";
            }

            @Override
            public String getColumnName() {
                return "byte";
            }

        };

        final ByteField<ByteValue, Byte> BYTE = ByteField.create(
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
