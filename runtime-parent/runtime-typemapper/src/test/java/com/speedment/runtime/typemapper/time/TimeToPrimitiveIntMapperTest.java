package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class TimeToPrimitiveIntMapperTest extends AbstractTypeMapperTest<Time, Integer, TimeToPrimitiveIntMapper> {


    TimeToPrimitiveIntMapperTest() {
        super(
            Time.class,
            Integer.class,
            Category.INT,
            Ordering.RETAIN,
            TimeToPrimitiveIntMapper::new
        );
    }

    @Override
    protected void getJavaType() {
        assertEquals(int.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Time, Integer> testVector() {
        Map<Time, Integer> map = new HashMap<>();
        map.put(new Time(0L), 0);
        map.put(new Time(788918400000L), 788918400);
        map.put(null, null);
        return map;
    }
}