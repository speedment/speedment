package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class DateIntToPrimitiveShortMapperTest extends AbstractTypeMapperTest<Integer, Short, DateIntToPrimitiveShortMapper> {


    DateIntToPrimitiveShortMapperTest() {
        super(Integer.class,
            Short.class,
            Category.SHORT,
            DateIntToPrimitiveShortMapper::new
            );
    }

    @Override
    @Test
    protected void getJavaType() {
        assertEquals(short.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Integer, Short> testVector() {
        // IntStream
        Map<Integer, Short> map = new HashMap<>();
        map.put(19700101, Short.valueOf("9133"));
        map.put(19700101, Short.valueOf("0"));
        map.put(null, null);
        return map;
    }

}