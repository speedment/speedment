package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;

final class BinaryToBigIntegerMapperTest extends AbstractTypeMapperTest<Object, BigInteger, BinaryToBigIntegerMapper> {

    BinaryToBigIntegerMapperTest() {
        super(
            Object.class,
            BigInteger.class,
            Category.COMPARABLE,
            BinaryToBigIntegerMapper::new);
    }

    @Override
    protected Map<Object, BigInteger> testVector() {
        Map<Object, BigInteger> map = new HashMap<>();
        map.put(null, null);
        return map;
    }
}