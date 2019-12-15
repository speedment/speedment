package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

final class TimeToLongMapperTest extends AbstractTypeMapperTest<Time, Long, TimeToLongMapper> {

    TimeToLongMapperTest() {
        super(
            Time.class,
            Long.class,
            TypeMapper.Category.COMPARABLE,
            TypeMapper.Ordering.RETAIN,
            TimeToLongMapper::new
        );
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