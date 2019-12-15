package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class TimeToPrimitiveLongMapperTest extends AbstractTypeMapperTest<Time, Long, TimeToPrimitiveLongMapper> {

    TimeToPrimitiveLongMapperTest() {
        super(
            Time.class,
            Long.class,
            Category.LONG,
            Ordering.RETAIN,
            TimeToPrimitiveLongMapper::new
        );
    }

    @Override
    protected void getJavaType() {
        assertEquals(long.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Time, Long> testVector() {
        Map<Time, Long> map = new HashMap<>();
        map.put(new Time(0L), 0L);
        map.put(new Time(788918400000L), 788918400000L);
        map.put(null, null);
        return map;
    }
}