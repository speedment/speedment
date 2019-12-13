package com.speedment.runtime.typemapper.doubles;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class DoubleToFloatMapperTest extends AbstractTypeMapperTest<Double, Float, DoubleToFloatMapper> {

    DoubleToFloatMapperTest() {
        super(Double.class,
            Float.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            DoubleToFloatMapper::new
        );
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