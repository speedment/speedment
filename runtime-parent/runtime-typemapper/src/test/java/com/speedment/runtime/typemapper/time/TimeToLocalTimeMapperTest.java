package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class TimeToLocalTimeMapperTest extends AbstractTypeMapperTest<Time, LocalTime, TimeToLocalTimeMapper> {


    TimeToLocalTimeMapperTest() {
        super(
            Time.class,
            LocalTime.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            TimeToLocalTimeMapper::new
        );
    }

    @Override
    protected Map<Time, LocalTime> testVector() {
        Map<Time, LocalTime> map = new HashMap<>();
        return map;
    }
}