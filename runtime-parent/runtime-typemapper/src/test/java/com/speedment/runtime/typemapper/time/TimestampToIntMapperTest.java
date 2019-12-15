package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

final class TimestampToIntMapperTest extends AbstractTypeMapperTest<Timestamp, Integer, TimestampToIntMapper> {


    TimestampToIntMapperTest() {
        super(Timestamp.class,
            Integer.class,
            TypeMapper.Category.COMPARABLE,
            TypeMapper.Ordering.RETAIN,
            TimestampToIntMapper::new);
    }

    @Override
    protected Map<Timestamp, Integer> testVector() {
        Map<Timestamp, Integer> map = new HashMap<>();
        map.put(new Timestamp(0L), 0);
        map.put(new Timestamp(788918400000L), 788918400);
        map.put(null, null);
        return map;
    }
}