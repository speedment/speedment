package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

/**
 * @author juliagustafsson
 * Since 3.2.6
 */
final class LongToShortMapperTest extends AbstractTypeMapperTest<Long, Short, LongToShortMapper> {

    LongToShortMapperTest() {
        super(
            Long.class,
            Short.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            LongToShortMapper::new);
    }

    @Override
    protected Map<Long, Short> testVector() {
        Map<Long, Short> map = new HashMap<>();
        map.put(100L, Short.valueOf("100"));
        map.put(32767L, Short.MAX_VALUE);
        map.put(0L, Short.valueOf("0"));
        map.put(-32768L, Short.MIN_VALUE);
        map.put(null, null);
        return map;
    }
}