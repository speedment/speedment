package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

final class YNStringToBooleanMapperTest extends AbstractTypeMapperTest<String, Boolean, YNStringToBooleanMapper> {

    YNStringToBooleanMapperTest() {
        super(
            String.class,
            Boolean.class,
            Category.BOOLEAN,
            YNStringToBooleanMapper::new);
    }

    @Override
    protected Map<String, Boolean> testVector() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Y", true);
        map.put("N", false);
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