package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class TimeToIntMapperTest extends AbstractTypeMapperTest<Time, Integer, TimeToIntMapper> {

    TimeToIntMapperTest() {
        super(
            Time.class,
            Integer.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            TimeToIntMapper::new);
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