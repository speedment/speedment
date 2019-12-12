package com.speedment.runtime.typemapper.shorts;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ShortToByteMapperTest extends AbstractTypeMapperTest<Short, Byte, ShortToByteMapper> {

    ShortToByteMapperTest() {
        super(Short.class, Byte.class, ShortToByteMapper::new);
    }

    @Test
    void getOrdering() {
        assertEquals(TypeMapper.Ordering.RETAIN, typeMapper().getOrdering());
    }

    @Override
    protected Map<Short, Byte> testVector() {
        Map<Short, Byte> map = new HashMap<>();
        map.put(Short.valueOf("0"), Byte.valueOf("0"));
        map.put(null, null);
        return map;
    }
}