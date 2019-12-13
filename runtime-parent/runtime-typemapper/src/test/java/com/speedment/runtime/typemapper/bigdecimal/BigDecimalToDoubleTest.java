package com.speedment.runtime.typemapper.bigdecimal;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class BigDecimalToDoubleTest extends AbstractTypeMapperTest<BigDecimal, Double, BigDecimalToDouble> {

    BigDecimalToDoubleTest() {
        super(
            BigDecimal.class,
            Double.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            BigDecimalToDouble::new
        );
    }

    @Override
    protected Map<BigDecimal, Double> testVector() {
        Map<BigDecimal, Double> map = new HashMap<>();
        map.put(BigDecimal.valueOf(748147123.0), 748147123.0);
        map.put(BigDecimal.valueOf(Double.MAX_VALUE), Double.MAX_VALUE);
        map.put(BigDecimal.valueOf(Double.MIN_VALUE), Double.MIN_VALUE);
        map.put(BigDecimal.valueOf(0.0), 0.0);
        map.put(null, null);
        return map;
    }
}