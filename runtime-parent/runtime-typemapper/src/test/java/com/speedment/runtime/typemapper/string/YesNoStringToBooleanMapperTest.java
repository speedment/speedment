package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

final class YesNoStringToBooleanMapperTest extends AbstractTypeMapperTest<String, Boolean, YesNoStringToBooleanMapper> {

    YesNoStringToBooleanMapperTest() {
        super(
            String.class,
            Boolean.class,
            Category.BOOLEAN,
            YesNoStringToBooleanMapper::new);
    }

    protected Map<String, Boolean> testVector() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("yes", true);
        map.put("no", false);
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