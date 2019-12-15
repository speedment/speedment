package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateToPrimitiveLongMapperTest extends AbstractTypeMapperTest<Date, Long, DateToPrimitiveLongMapper> {

    public DateToPrimitiveLongMapperTest() {
        super(
            Date.class,
            Long.class,
            TypeMapper.Category.LONG,
            TypeMapper.Ordering.RETAIN,
            DateToPrimitiveLongMapper::new
        );
    }

    @Override
    protected void getJavaType() {
        assertEquals(long.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Date, Long> testVector() {
        Map<Date, Long> map = new HashMap<>();
        map.put(new Date(0), 0L);
        map.put(new Date(788918400000L), 788918400000L);
        map.put(null, null);
        return map;
    }
}