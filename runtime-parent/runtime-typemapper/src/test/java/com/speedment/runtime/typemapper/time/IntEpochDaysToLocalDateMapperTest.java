package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

final class IntEpochDaysToLocalDateMapperTest extends AbstractTypeMapperTest<Integer, LocalDate, IntEpochDaysToLocalDateMapper> {

    IntEpochDaysToLocalDateMapperTest() {
        super(
            Integer.class,
            LocalDate.class,
            TypeMapper.Category.COMPARABLE,
            TypeMapper.Ordering.RETAIN,
            IntEpochDaysToLocalDateMapper::new
        );
    }

    @Override
    protected Map<Integer, LocalDate> testVector() {
        Map<Integer, LocalDate> map = new HashMap<>();
        map.put(9133, LocalDate.of(1995,01,03));
        map.put(0, LocalDate.EPOCH);
        map.put(null, null);
        return map;
    }
}