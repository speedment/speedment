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
package com.speedment.runtime.field.internal.comparator;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.*;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Emil Forslund
 * @since  3.0.11
 */
final class CombinedComparatorImplTest {

    private static final int ENTITIES = 1000;

    private Random random;
    private List<Entity> list;

    @BeforeEach
    void setUp() throws Exception {
        random = new Random(1);
        list   = Stream.generate(this::next).limit(ENTITIES).collect(toList());
    }

    @Test
    void compare() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp -> {
            final Comparator<Entity> mock = mockComparator(
                comp.getField(),
                comp.getNullOrder(),
                comp.isReversed()
            );

            try {
                final List<Entity> expected = list.stream().sorted(mock).collect(toList());
                final List<Entity> actual = list.stream().sorted(comp).collect(toList());

                assertEquals(actual, expected, format(
                    "Compare %s using %s",
                    comp.getField().identifier().getColumnId(),
                    comp
                ));
            } catch (final IllegalArgumentException ex) {
                throw new RuntimeException(format(
                    "Error comparing %s using %s",
                    comp.getField().identifier().getColumnId(),
                    comp
                ), ex);
            }
        });
    }

    @Test
    void reversed() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp -> {
            final boolean reversed = comp.isReversed();
            assertEquals(!reversed, comp.reversed().isReversed());
        });
    }

    @Test
    void thenComparing() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp1 -> {
            final Comparator<Entity> mock1 = mockComparator(
                comp1.getField(),
                comp1.getNullOrder(),
                comp1.isReversed()
            );

            comparatorsAndReverses().forEachOrdered(comp2 -> {
                final Comparator<Entity> mock2 = mockComparator(
                    comp2.getField(),
                    comp2.getNullOrder(),
                    comp2.isReversed()
                );

                final Comparator<Entity> mock = mock1.thenComparing(mock2);
                final Comparator<Entity> comp = comp1.thenComparing(comp2);

                final List<Entity> expected = list.stream().sorted(mock).collect(toList());
                final List<Entity> actual   = list.stream().sorted(comp).collect(toList());
                assertEquals(actual, expected, format("Compare %s then %s (reversed) using %s",
                    comp1.getField().identifier().getColumnId(),
                    comp2.getField().identifier().getColumnId(),
                    comp
                )
                );
            });
        });
    }

    @Test
    void thenComparing1() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp1 -> {
            final Comparator<Entity> mock1 = mockComparator(
                comp1.getField(),
                comp1.getNullOrder(),
                comp1.isReversed()
            );

            final Comparator<Entity> mock = mock1.thenComparing(Entity.STRING.getter());
            final Comparator<Entity> comp = comp1.thenComparing(Entity.STRING.getter());

            final List<Entity> expected = list.stream().sorted(mock).collect(toList());
            final List<Entity> actual   = list.stream().sorted(comp).collect(toList());
            assertEquals(actual, expected, format("Compare %s then %s using %s",
                comp1.getField().identifier().getColumnId(),
                Entity.STRING.identifier().getColumnId(),
                comp
            )
            );
        });
    }

    @Test
    void thenComparing2() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp1 -> {
            final Comparator<Entity> mock1 = mockComparator(
                comp1.getField(),
                comp1.getNullOrder(),
                comp1.isReversed()
            );

            final Comparator<Entity> mock = mock1.thenComparing(Entity.STRING.getter(), Comparator.reverseOrder());
            final Comparator<Entity> comp = comp1.thenComparing(Entity.STRING.getter(), Comparator.reverseOrder());

            final List<Entity> expected = list.stream().sorted(mock).collect(toList());
            final List<Entity> actual   = list.stream().sorted(comp).collect(toList());
            assertEquals(actual, expected, format("Compare %s then %s (reversed) using %s",
                comp1.getField().identifier().getColumnId(),
                Entity.STRING.identifier().getColumnId(),
                comp
                )
            );
        });
    }

    @Test
    void thenComparingInt() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp1 -> {
            final Comparator<Entity> mock1 = mockComparator(
                comp1.getField(),
                comp1.getNullOrder(),
                comp1.isReversed()
            );

            final Comparator<Entity> mock = mock1.thenComparingInt(Entity.INT.getter());
            final Comparator<Entity> comp = comp1.thenComparingInt(Entity.INT.getter());

            final List<Entity> expected = list.stream().sorted(mock).collect(toList());
            final List<Entity> actual   = list.stream().sorted(comp).collect(toList());
            assertEquals(actual, expected, format("Compare %s then %s using %s",
                comp1.getField().identifier().getColumnId(),
                Entity.INT.identifier().getColumnId(),
                comp
                )
            );
        });
    }

    @Test
    void thenComparingLong() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp1 -> {
            final Comparator<Entity> mock1 = mockComparator(
                comp1.getField(),
                comp1.getNullOrder(),
                comp1.isReversed()
            );

            final Comparator<Entity> mock = mock1.thenComparingLong(Entity.LONG.getter());
            final Comparator<Entity> comp = comp1.thenComparingLong(Entity.LONG.getter());

            final List<Entity> expected = list.stream().sorted(mock).collect(toList());
            final List<Entity> actual   = list.stream().sorted(comp).collect(toList());
            assertEquals(actual, expected, format("Compare %s then %s using %s",
                comp1.getField().identifier().getColumnId(),
                Entity.LONG.identifier().getColumnId(),
                comp
                )
            );
        });
    }

    @Test
    void thenComparingDouble() throws Exception {
        comparatorsAndReverses().forEachOrdered(comp1 -> {
            final Comparator<Entity> mock1 = mockComparator(
                comp1.getField(),
                comp1.getNullOrder(),
                comp1.isReversed()
            );

            final Comparator<Entity> mock = mock1.thenComparingDouble(Entity.DOUBLE.getter());
            final Comparator<Entity> comp = comp1.thenComparingDouble(Entity.DOUBLE.getter());

            final List<Entity> expected = list.stream().sorted(mock).collect(toList());
            final List<Entity> actual   = list.stream().sorted(comp).collect(toList());
            assertEquals(actual, expected, format("Compare %s then %s using %s",
                comp1.getField().identifier().getColumnId(),
                Entity.DOUBLE.identifier().getColumnId(),
                comp
                )
            );
        });
    }

    private Comparator<Entity> mockComparator(
            Field<Entity> field, NullOrder nullOrder, boolean reversed) {

        Comparator<Entity> comparator;
        switch ((Entity.Identifier) field.identifier()) {
            case BYTE:   comparator = Comparator.comparingInt(Entity.BYTE::getAsByte); break;
            case SHORT:  comparator = Comparator.comparingInt(Entity.SHORT::getAsShort); break;
            case INT:    comparator = Comparator.comparingInt(Entity.INT::getAsInt); break;
            case LONG:   comparator = Comparator.comparingLong(Entity.LONG::getAsLong); break;
            case DOUBLE: comparator = Comparator.comparingDouble(Entity.DOUBLE::getAsDouble); break;
            case CHAR:   comparator = Comparator.comparingInt(Entity.CHAR::getAsChar); break;
            case BOOLEAN: {
                comparator = (a, b) -> Boolean.compare(
                    Entity.BOOLEAN.getAsBoolean(a),
                    Entity.BOOLEAN.getAsBoolean(b)
                ); break;
            }
            case FLOAT: {
                comparator = (a, b) -> Float.compare(
                    Entity.FLOAT.getAsFloat(a),
                    Entity.FLOAT.getAsFloat(b)
                ); break;
            }
            case STRING: {
                comparator = (a, b) -> Objects.compare(
                    Entity.STRING.get(a),
                    Entity.STRING.get(b),
                    Comparator.naturalOrder()
                ); break;
            }
            default : throw new UnsupportedOperationException();
        }

        if (nullOrder == NullOrder.FIRST) {
            comparator = Comparator.nullsFirst(comparator);
        } else if (nullOrder == NullOrder.LAST) {
            comparator = Comparator.nullsLast(comparator);
        }

        if (reversed) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    private Stream<FieldComparator<Entity>> comparatorsAndReverses() {
        return Stream.concat(
            comparators(),
            comparators().map(FieldComparator::reversed)
        );
    }

    private Stream<FieldComparator<Entity>> comparators() {
        return Stream.of(
            Entity.BYTE.comparator(),
            Entity.SHORT.comparator(),
            Entity.INT.comparator(),
            Entity.LONG.comparator(),
            Entity.FLOAT.comparator(),
            Entity.DOUBLE.comparator(),
            Entity.CHAR.comparator(),
            Entity.STRING.comparator(),
            Entity.BYTE.comparatorNullFieldsFirst(),
            Entity.SHORT.comparatorNullFieldsFirst(),
            Entity.INT.comparatorNullFieldsFirst(),
            Entity.LONG.comparatorNullFieldsFirst(),
            Entity.FLOAT.comparatorNullFieldsFirst(),
            Entity.DOUBLE.comparatorNullFieldsFirst(),
            Entity.CHAR.comparatorNullFieldsFirst(),
            Entity.STRING.comparatorNullFieldsFirst(),
            new BooleanFieldComparator(NullOrder.FIRST, false),
            new BooleanFieldComparator(NullOrder.LAST, false)
        );
    }

    private Stream<Field<Entity>> fields() {
        return Stream.of(
            Entity.BYTE,
            Entity.SHORT,
            Entity.INT,
            Entity.LONG,
            Entity.FLOAT,
            Entity.DOUBLE,
            Entity.BOOLEAN,
            Entity.CHAR,
            Entity.STRING
        );
    }

    private Entity next() {
        return new Entity()
            .setByteValue(nextByte())
            .setShortValue(nextShort())
            .setIntValue(nextInt())
            .setLongValue(nextLong())
            .setFloatValue(nextFloat())
            .setDoubleValue(nextDouble())
            .setCharValue(nextChar())
            .setBooleanValue(nextBoolean())
            .setStringValue(nextString());
    }

    private byte nextByte() {
        switch (random.nextInt(10)) {
            case 0 : return 0;
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return -1;
            case 4 : return -2;
            case 5 : return Byte.MAX_VALUE;
            case 6 : return Byte.MIN_VALUE;
            case 7 : return 99;
            case 8 : return 100;
            default : return (byte) random.nextInt();
        }
    }

    private short nextShort() {
        switch (random.nextInt(10)) {
            case 0 : return 0;
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return -1;
            case 4 : return -2;
            case 5 : return Short.MAX_VALUE;
            case 6 : return Short.MIN_VALUE;
            case 7 : return 1099;
            case 8 : return 1100;
            default : return (short) random.nextInt();
        }
    }

    private int nextInt() {
        switch (random.nextInt(10)) {
            case 0 : return 0;
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return -1;
            case 4 : return -2;
            case 5 : return Integer.MAX_VALUE;
            case 6 : return Integer.MIN_VALUE;
            case 7 : return 100_099;
            case 8 : return 100_100;
            default : return random.nextInt();
        }
    }

    private long nextLong() {
        switch (random.nextInt(10)) {
            case 0 : return 0;
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return -1;
            case 4 : return -2;
            case 5 : return Long.MAX_VALUE;
            case 6 : return Long.MIN_VALUE;
            case 7 : return 100_000_000_099L;
            case 8 : return 100_000_000_100L;
            default : return random.nextLong();
        }
    }

    private float nextFloat() {
        switch (random.nextInt(10)) {
            case 0 : return 0;
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return -1;
            case 4 : return -2;
            case 5 : return Float.MAX_VALUE;
            case 6 : return -Float.MAX_VALUE;
            case 7 : return 1.5f;
            case 8 : return 2.6f;
            case 9 : return (float) random.nextGaussian();
            default : return random.nextFloat();
        }
    }

    private double nextDouble() {
        switch (random.nextInt(10)) {
            case 0 : return 0;
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return -1;
            case 4 : return -2;
            case 5 : return Double.MAX_VALUE;
            case 6 : return -Double.MAX_VALUE;
            case 7 : return 1.5d;
            case 8 : return 2.6d;
            case 9 : return random.nextGaussian();
            default : return random.nextDouble();
        }
    }

    private char nextChar() {
        switch (random.nextInt(10)) {
            case 0  : return ' ';
            case 1  : return '_';
            case 2  : return 'a';
            case 3  : return 'b';
            case 4  : return 'c';
            case 5  : return 'å';
            case 6  : return 'ä';
            case 7  : return (char) 0xffff;
            case 8  : return (char) 0xfffa;
            default : return (char) random.nextInt();
        }
    }

    private boolean nextBoolean() {
        return random.nextBoolean();
    }

    private String nextString() {
        final char[] chars = new char[random.nextInt(10)];

        for (int i = 0; i < chars.length; i++) {
            chars[i] = nextChar();
        }

        return new String(chars);
    }

    private static final class Entity {

        static final ByteField<Entity, Byte> BYTE = ByteField.create(
            Identifier.BYTE,
            Entity::getByteValue,
            Entity::setByteValue,
            TypeMapper.primitive(),
            false
        );

        static final ShortField<Entity, Short> SHORT = ShortField.create(
            Identifier.SHORT,
            Entity::getShortValue,
            Entity::setShortValue,
            TypeMapper.primitive(),
            false
        );

        static final IntField<Entity, Integer> INT = IntField.create(
            Identifier.INT,
            Entity::getIntValue,
            Entity::setIntValue,
            TypeMapper.primitive(),
            false
        );

        static final LongField<Entity, Long> LONG = LongField.create(
            Identifier.LONG,
            Entity::getLongValue,
            Entity::setLongValue,
            TypeMapper.primitive(),
            false
        );

        static final FloatField<Entity, Float> FLOAT = FloatField.create(
            Identifier.FLOAT,
            Entity::getFloatValue,
            Entity::setFloatValue,
            TypeMapper.primitive(),
            false
        );

        static final DoubleField<Entity, Double> DOUBLE = DoubleField.create(
            Identifier.DOUBLE,
            Entity::getDoubleValue,
            Entity::setDoubleValue,
            TypeMapper.primitive(),
            false
        );

        static final BooleanField<Entity, Boolean> BOOLEAN = BooleanField.create(
            Identifier.BOOLEAN,
            Entity::isBooleanValue,
            Entity::setBooleanValue,
            TypeMapper.primitive(),
            false
        );

        static final CharField<Entity, Character> CHAR = CharField.create(
            Identifier.CHAR,
            Entity::getCharValue,
            Entity::setCharValue,
            TypeMapper.primitive(),
            false
        );

        static final StringField<Entity, String> STRING = StringField.create(
            Identifier.STRING,
            Entity::getStringValue,
            Entity::setStringValue,
            TypeMapper.primitive(),
            false
        );

        private byte byteValue;
        private short shortValue;
        private int intValue;
        private long longValue;
        private float floatValue;
        private double doubleValue;
        private boolean booleanValue;
        private char charValue;
        private String stringValue;

        public byte getByteValue() {
            return byteValue;
        }

        public Entity setByteValue(byte byteValue) {
            this.byteValue = byteValue;
            return this;
        }

        public short getShortValue() {
            return shortValue;
        }

        public Entity setShortValue(short shortValue) {
            this.shortValue = shortValue;
            return this;
        }

        public int getIntValue() {
            return intValue;
        }

        public Entity setIntValue(int intValue) {
            this.intValue = intValue;
            return this;
        }

        public long getLongValue() {
            return longValue;
        }

        public Entity setLongValue(long longValue) {
            this.longValue = longValue;
            return this;
        }

        public float getFloatValue() {
            return floatValue;
        }

        public Entity setFloatValue(float floatValue) {
            this.floatValue = floatValue;
            return this;
        }

        public double getDoubleValue() {
            return doubleValue;
        }

        public Entity setDoubleValue(double doubleValue) {
            this.doubleValue = doubleValue;
            return this;
        }

        public boolean isBooleanValue() {
            return booleanValue;
        }

        public Entity setBooleanValue(boolean booleanValue) {
            this.booleanValue = booleanValue;
            return this;
        }

        public char getCharValue() {
            return charValue;
        }

        public Entity setCharValue(char charValue) {
            this.charValue = charValue;
            return this;
        }

        public String getStringValue() {
            return stringValue;
        }

        public Entity setStringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Entity)) return false;

            Entity entity = (Entity) o;

            if (getByteValue() != entity.getByteValue()) return false;
            if (getShortValue() != entity.getShortValue()) return false;
            if (getIntValue() != entity.getIntValue()) return false;
            if (getLongValue() != entity.getLongValue()) return false;
            if (Float.compare(entity.getFloatValue(), getFloatValue()) != 0)
                return false;
            if (Double.compare(entity.getDoubleValue(), getDoubleValue()) != 0)
                return false;
            if (isBooleanValue() != entity.isBooleanValue()) return false;
            if (getCharValue() != entity.getCharValue()) return false;
            return getStringValue() != null ? getStringValue().equals(entity.getStringValue()) : entity.getStringValue() == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = getByteValue();
            result = 31 * result + (int) getShortValue();
            result = 31 * result + getIntValue();
            result = 31 * result + (int) (getLongValue() ^ (getLongValue() >>> 32));
            result = 31 * result + (getFloatValue() != +0.0f ? Float.floatToIntBits(getFloatValue()) : 0);
            temp = Double.doubleToLongBits(getDoubleValue());
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + (isBooleanValue() ? 1 : 0);
            result = 31 * result + (int) getCharValue();
            result = 31 * result + (getStringValue() != null ? getStringValue().hashCode() : 0);
            return result;
        }

        enum Identifier implements ColumnIdentifier<Entity> {
            BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BOOLEAN, CHAR, STRING;

            @Override
            public String getDbmsId() {
                return "test_dbms";
            }

            @Override
            public String getSchemaId() {
                return "test_schema";
            }

            @Override
            public String getTableId() {
                return "test_table";
            }

            @Override
            public String getColumnId() {
                return name().toLowerCase();
            }
        }
    }

    private static final class BooleanFieldComparator
    implements FieldComparator<Entity> {

        private final NullOrder nullOrder;
        private final boolean reversed;

        public BooleanFieldComparator(NullOrder nullOrder, boolean reversed) {
            this.nullOrder = nullOrder;
            this.reversed  = reversed;
        }

        @Override
        public Field<Entity> getField() {
            return Entity.BOOLEAN;
        }

        @Override
        public NullOrder getNullOrder() {
            return nullOrder;
        }

        @Override
        public boolean isReversed() {
            return reversed;
        }

        @Override
        public FieldComparator<Entity> reversed() {
            return new BooleanFieldComparator(nullOrder, !reversed);
        }

        @Override
        public int compare(Entity o1, Entity o2) {
            final boolean b1 = Entity.BOOLEAN.getAsBoolean(o1);
            final boolean b2 = Entity.BOOLEAN.getAsBoolean(o2);
            if (b1 && b2) {
                return 0;
            } else if (b1) {
                return reversed ? -1 : 1;
            } else if (b2) {
                return reversed ? 1 : -1;
            } else {
                return 0;
            }
        }
    }
}