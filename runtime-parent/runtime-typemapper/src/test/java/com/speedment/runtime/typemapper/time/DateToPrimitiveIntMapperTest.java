package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class DateToPrimitiveIntMapperTest extends AbstractTypeMapperTest<Date, Integer, DateToPrimitiveIntMapper> {

    DateToPrimitiveIntMapperTest() {
        super(
            Date.class,
            Integer.class,
            Category.INT,
            Ordering.RETAIN,
            DateToPrimitiveIntMapper::new);
    }

    @Override
    protected void getJavaType() {
        assertEquals(int.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Date, Integer> testVector() {
        Map<Date, Integer> map = new HashMap<>();
        map.put(new java.sql.Date(0), 0);
        map.put(new java.sql.Date(788918400000L), 788918400);
        map.put(null, null);
        return map;
    }
}