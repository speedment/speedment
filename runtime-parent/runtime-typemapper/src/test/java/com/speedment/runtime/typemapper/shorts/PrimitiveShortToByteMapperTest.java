package com.speedment.runtime.typemapper.shorts;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrimitiveShortToByteMapperTest extends AbstractTypeMapperTest<Short, Byte, PrimitiveShortToByteMapper> {


    PrimitiveShortToByteMapperTest() {
        super(
            Short.class,
            Byte.class,
            Category.BYTE,
            Ordering.RETAIN,
            PrimitiveShortToByteMapper::new);
    }

    @Override
    protected Map<Short, Byte> testVector() {
        Map<Short, Byte> map = new HashMap<>();
        map.put(Short.valueOf("0"), Byte.valueOf("0"));
        map.put(null, null);
        return map;
    }

    @Override
    protected void getJavaType() {
        assertEquals(byte.class, typeMapper().getJavaType(column()));
    }
}