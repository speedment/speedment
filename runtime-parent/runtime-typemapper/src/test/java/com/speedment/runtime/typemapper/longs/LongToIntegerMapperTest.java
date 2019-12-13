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
final class LongToIntegerMapperTest extends AbstractTypeMapperTest<Long, Integer, LongToIntegerMapper> {

    LongToIntegerMapperTest() {
        super(
            Long.class,
            Integer.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            LongToIntegerMapper::new);
    }

    @Override
    protected Map<Long, Integer> testVector() {
        Map<Long, Integer> map = new HashMap<>();
        map.put(100L, 100);
        map.put(2147483647L, Integer.MAX_VALUE);
        map.put(0L, 0);
        map.put(-2147483648L, Integer.MIN_VALUE);
        map.put(null, null);
        return map;
    }

}