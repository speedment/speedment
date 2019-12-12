package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class StringToBigDecimalMapperTest extends AbstractTypeMapperTest<String, BigDecimal, StringToBigDecimalMapper> {

    StringToBigDecimalMapperTest() {
        super(String.class, BigDecimal.class, StringToBigDecimalMapper::new);
    }

    @Test
    void getOrdering() {
        assertEquals(TypeMapper.Ordering.UNSPECIFIED, typeMapper().getOrdering());
    }

    @Test
    void getJavaTypeCategory() {
        assertEquals(TypeMapper.Category.COMPARABLE, typeMapper().getJavaTypeCategory(column()));
    }

    @Override
    protected Map<String, BigDecimal> testVector() {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("278217481", BigDecimal.valueOf(278217481));
        map.put("2147483647", BigDecimal.valueOf(Integer.MAX_VALUE));
        map.put("0", BigDecimal.valueOf(0));
        map.put("12314151.34", BigDecimal.valueOf(12314151.34));
        map.put("-12324135314151.34", BigDecimal.valueOf(-12324135314151.34));
        map.put("-2147483648", BigDecimal.valueOf(Integer.MIN_VALUE));
        map.put(null, null);
        return map;
    }
}