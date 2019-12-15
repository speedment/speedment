package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;

final class BinaryToByteArrayMapperTest extends AbstractTypeMapperTest<Object, byte[], BinaryToByteArrayMapper> {

    BinaryToByteArrayMapperTest() {
        super(
            Object.class,
            byte[].class,
            Category.REFERENCE,
            BinaryToByteArrayMapper::new);
    }

    @Override
    protected Map<Object, byte[]> testVector() {
        Map<Object, byte[]> map = new HashMap<>();
        map.put(null, null);
        return map;
    }
}