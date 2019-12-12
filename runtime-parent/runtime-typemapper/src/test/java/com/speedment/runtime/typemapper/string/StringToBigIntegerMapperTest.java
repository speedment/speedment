package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringToBigIntegerMapperTest extends AbstractTypeMapperTest<String, BigInteger, StringToBigIntegerMapper> {


    public StringToBigIntegerMapperTest() {
        super(String.class, BigInteger.class, StringToBigIntegerMapper::new);
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
    protected Map<String, BigInteger> testVector() {
        Map<String, BigInteger> map = new HashMap<>();
        map.put("278217481", BigInteger.valueOf(278217481));
        map.put("2147483647", BigInteger.valueOf(Integer.MAX_VALUE));
        map.put("0", BigInteger.valueOf(0));
        map.put("-2147483648", BigInteger.valueOf(Integer.MIN_VALUE));
        map.put(null, null);
        return map;
    }
}