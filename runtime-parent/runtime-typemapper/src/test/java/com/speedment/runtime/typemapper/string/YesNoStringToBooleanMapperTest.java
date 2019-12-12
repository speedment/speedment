package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

final class YesNoStringToBooleanMapperTest extends AbstractTypeMapperTest<String, Boolean, YesNoStringToBooleanMapper> {

    YesNoStringToBooleanMapperTest() {
        super(String.class, Boolean.class, YesNoStringToBooleanMapper::new);
    }

    protected Map<String, Boolean> testVector() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("yes", true);
        map.put("no", false);
        map.put(null, null);
        return map;
    }

}