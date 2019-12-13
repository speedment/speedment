package com.speedment.runtime.typemapper.shorts;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class ShortToByteMapperTest extends AbstractTypeMapperTest<Short, Byte, ShortToByteMapper> {

    ShortToByteMapperTest() {
        super(
            Short.class,
            Byte.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            ShortToByteMapper::new);
    }

    @Override
    protected Map<Short, Byte> testVector() {
        Map<Short, Byte> map = new HashMap<>();
        map.put(Short.valueOf("0"), Byte.valueOf("0"));
        map.put(null, null);
        return map;
    }

}