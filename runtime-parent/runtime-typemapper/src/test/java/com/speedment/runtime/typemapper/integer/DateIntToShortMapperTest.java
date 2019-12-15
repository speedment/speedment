package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;

final class DateIntToShortMapperTest extends AbstractTypeMapperTest<Integer, Short, DateIntToShortMapper> {

    DateIntToShortMapperTest() {
        super(
            Integer.class,
            Short.class,
            Category.COMPARABLE,
            DateIntToShortMapper::new
        );
    }

    @Override
    protected Map<Integer, Short> testVector() {
        Map<Integer, Short> map = new HashMap<>();
        map.put(19700101, Short.valueOf("9133"));
        map.put(19700101, Short.valueOf("0"));
        map.put(null, null);
        return map;
    }

}