package com.speedment.runtime.typemapper.doubles;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrimitiveDoubleToFloatMapperTest extends AbstractTypeMapperTest<Double, Float, PrimitiveDoubleToFloatMapper> {

    PrimitiveDoubleToFloatMapperTest() {
        super(Double.class,
            Float.class,
            Category.FLOAT,
            Ordering.RETAIN,
            PrimitiveDoubleToFloatMapper::new
        );
    }

    @Override
    protected void getJavaType() {
        assertEquals(float.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Double, Float> testVector() {
        Map<Double, Float> map = new HashMap<>();
        map.put(0.0, 0.0f);
        map.put(Double.NaN, Float.NaN);
        map.put(null, null);
        return map;
    }
}