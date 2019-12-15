package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

final class DateToIntMapperTest extends AbstractTypeMapperTest<Date, Integer, DateToIntMapper>  {

    DateToIntMapperTest() {
        super(
            Date.class,
            Integer.class,
            TypeMapper.Category.COMPARABLE,
            TypeMapper.Ordering.RETAIN,
            DateToIntMapper::new);
    }

    @Override
    protected Map<Date, Integer> testVector() {
        Map<Date, Integer> map = new HashMap<>();
        map.put(new Date(0), 0);
        map.put(new Date(788918400000L), 788918400);
        map.put(null, null);
        return map;
    }
}