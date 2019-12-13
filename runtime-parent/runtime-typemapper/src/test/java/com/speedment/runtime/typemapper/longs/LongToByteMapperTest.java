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

final class LongToByteMapperTest extends AbstractTypeMapperTest<Long, Byte, LongToByteMapper> {

    LongToByteMapperTest() {
        super(
            Long.class,
            Byte.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            LongToByteMapper::new);
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