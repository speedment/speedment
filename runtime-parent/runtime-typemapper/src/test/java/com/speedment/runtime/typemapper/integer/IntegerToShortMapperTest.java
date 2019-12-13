package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class IntegerToShortMapperTest extends AbstractTypeMapperTest<Integer, Short, IntegerToShortMapper> {

    IntegerToShortMapperTest() {
        super(
            Integer.class,
            Short.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            IntegerToShortMapper::new
        );
    }

    @Override
    protected Map<Integer, Short> testVector() {
        Map<Integer, Short> map = new HashMap<>();
        map.put(100, Short.valueOf("100"));
        map.put(32767, Short.MAX_VALUE);
        map.put(0, Short.valueOf("0"));
        map.put(-32768, Short.MIN_VALUE);
        map.put(null, null);
        return map;
    }

}