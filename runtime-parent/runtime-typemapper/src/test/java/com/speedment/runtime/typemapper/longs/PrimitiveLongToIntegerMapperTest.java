package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrimitiveLongToIntegerMapperTest extends AbstractTypeMapperTest<Long, Integer, PrimitiveLongToIntegerMapper> {

    PrimitiveLongToIntegerMapperTest() {
        super(
            Long.class,
            Integer.class,
            Category.INT,
            Ordering.RETAIN,
            PrimitiveLongToIntegerMapper::new);
    }

    @Override
    @Test
    protected void getJavaType() {
        assertEquals(int.class, typeMapper().getJavaType(column()));
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