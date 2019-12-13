package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

final class IntegerZeroOneToBooleanMapperTest extends AbstractTypeMapperTest<Integer, Boolean, IntegerZeroOneToBooleanMapper> {

    IntegerZeroOneToBooleanMapperTest() {
        super(
            Integer.class,
            Boolean.class,
            TypeMapper.Category.BOOLEAN,
            IntegerZeroOneToBooleanMapper::new
        );
    }

    @Override
    protected Map<Integer, Boolean> testVector() {
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(1, true);
        map.put(0, false);
        map.put(null, null);
        return map;
    }

    @Override
    @Test
    protected void getJavaType() {
        when(column().isNullable()).thenReturn(true);
        assertEquals(Boolean.class, typeMapper().getJavaType(column()));
    }
}