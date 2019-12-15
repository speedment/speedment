package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class ShortEpochDaysToLocalDateMapperTest extends AbstractTypeMapperTest<Short, LocalDate, ShortEpochDaysToLocalDateMapper> {

    ShortEpochDaysToLocalDateMapperTest() {
        super(
            Short.class,
            LocalDate.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            ShortEpochDaysToLocalDateMapper::new);
    }

    @Override
    protected Map<Short, LocalDate> testVector() {
        Map<Short, LocalDate> map = new HashMap<>();
        map.put(Short.valueOf("9133"), LocalDate.of(1995,01,03));
        map.put(Short.valueOf("0"), LocalDate.EPOCH);
        map.put(null, null);
        return map;
    }
}