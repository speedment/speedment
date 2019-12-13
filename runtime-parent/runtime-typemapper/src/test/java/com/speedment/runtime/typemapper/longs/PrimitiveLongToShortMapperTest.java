package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrimitiveLongToShortMapperTest extends AbstractTypeMapperTest<Long, Short, PrimitiveLongToShortMapper> {

    PrimitiveLongToShortMapperTest() {
        super(
            Long.class,
            Short.class,
            Category.SHORT,
            Ordering.RETAIN,
            PrimitiveLongToShortMapper::new);
    }

    @Override
    @Test
    protected void getJavaType() {
        assertEquals(short.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Long, Short> testVector() {
        Map<Long, Short> map = new HashMap<>();
        map.put(3387L, Short.valueOf("3387"));
        map.put(null, null);
        map.put(0L, Short.valueOf("0"));
        map.put(-3334L, Short.valueOf("-3334"));
        return map;
    }
}