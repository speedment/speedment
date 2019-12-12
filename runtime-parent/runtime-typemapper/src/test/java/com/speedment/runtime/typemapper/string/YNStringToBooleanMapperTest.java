package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

final class YNStringToBooleanMapperTest extends AbstractTypeMapperTest<String, Boolean, YNStringToBooleanMapper> {

    YNStringToBooleanMapperTest() {
        super(String.class, Boolean.class, YNStringToBooleanMapper::new);
    }

    protected Map<String, Boolean> testVector() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Y", true);
        map.put("N", false);
        map.put(null, null);
        return map;
    }

}