package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class DateToLongMapperTest extends AbstractTypeMapperTest<Date, Long, DateToLongMapper> {

    public DateToLongMapperTest() {
        super(
            Date.class,
            Long.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            DateToLongMapper::new
        );
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