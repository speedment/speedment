package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class TimestampToPrimitiveLongMapperTest extends AbstractTypeMapperTest<Timestamp, Long, TimestampToPrimitiveLongMapper> {

    TimestampToPrimitiveLongMapperTest() {
        super(
            Timestamp.class,
            Long.class,
            TypeMapper.Category.LONG,
            TypeMapper.Ordering.RETAIN,
            TimestampToPrimitiveLongMapper::new
        );
    }

    @Override
    protected void getJavaType() {
        assertEquals(long.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Timestamp, Long> testVector() {
        Map<Timestamp, Long> map = new HashMap<>();
        map.put(new Timestamp(0L), 0L);
        map.put(new Timestamp(788918400000L), 788918400000L);
        map.put(null, null);
        return map;
    }
}