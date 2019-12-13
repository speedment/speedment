package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper.Category;
import com.speedment.runtime.typemapper.TypeMapper.Ordering;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrimitiveIntegerZeroOneToBooleanMapperTest extends AbstractTypeMapperTest<Integer, Boolean, PrimitiveIntegerZeroOneToBooleanMapper> {

    PrimitiveIntegerZeroOneToBooleanMapperTest() {
        super(Integer.class,
            Boolean.class,
            Category.BOOLEAN,
            Ordering.RETAIN,
            PrimitiveIntegerZeroOneToBooleanMapper::new
            );
    }

    @Override
    @Test
    protected void getJavaType() {
        assertEquals(boolean.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Integer, Boolean> testVector() {
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(1, true);
        map.put(0, false);
        map.put(null, null);
        return map;
    }

}