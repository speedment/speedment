package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class TimestampToLocalDateTimeMapperTest extends AbstractTypeMapperTest<Timestamp, LocalDateTime, TimestampToLocalDateTimeMapper> {


    TimestampToLocalDateTimeMapperTest() {
        super(
            Timestamp.class,
            LocalDateTime.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            TimestampToLocalDateTimeMapper::new);
    }

    @Override
    protected Map<Timestamp, LocalDateTime> testVector() {
        Map<Timestamp, LocalDateTime> map = new HashMap<>();
        return map;
    }
}