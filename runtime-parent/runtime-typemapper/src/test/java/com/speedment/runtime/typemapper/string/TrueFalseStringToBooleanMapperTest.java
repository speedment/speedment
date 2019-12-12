package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

final class TrueFalseStringToBooleanMapperTest extends AbstractTypeMapperTest<String, Boolean, TrueFalseStringToBooleanMapper> {


    TrueFalseStringToBooleanMapperTest() {
        super(String.class, Boolean.class, TrueFalseStringToBooleanMapper::new);
    }

    protected Map<String, Boolean> testVector() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("true", true);
        map.put("false", false);
        map.put(null, null);
        return map;
    }
}