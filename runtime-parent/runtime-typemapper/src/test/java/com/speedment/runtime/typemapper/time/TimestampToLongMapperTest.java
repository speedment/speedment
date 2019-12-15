package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class TimestampToLongMapperTest extends AbstractTypeMapperTest<Timestamp, Long, TimestampToLongMapper> {


    TimestampToLongMapperTest() {
        super(
            Timestamp.class,
            Long.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            TimestampToLongMapper::new);
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