package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrimitiveLongToByteMapperTest extends AbstractTypeMapperTest<Long, Byte, PrimitiveLongToByteMapper> {

    PrimitiveLongToByteMapperTest() {
        super(
            Long.class,
            Byte.class,
            Category.BYTE,
            Ordering.RETAIN,
            PrimitiveLongToByteMapper::new);
    }

    @Override
    @Test
    protected void getJavaType() {
        assertEquals(byte.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Long, Byte> testVector() {
        Map<Long, Byte> map = new HashMap<>();
        map.put(23L, Byte.valueOf("23"));
        map.put(0L, Byte.valueOf("0"));
        map.put(127L, Byte.MAX_VALUE);
        map.put(-128L, Byte.MIN_VALUE);
        map.put(null, null);
        return map;
    }
}