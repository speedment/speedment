package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

final class StringToLocaleMapperTest extends AbstractTypeMapperTest<String, Locale, StringToLocaleMapper> {

    StringToLocaleMapperTest() {
        super(String.class, Locale.class, StringToLocaleMapper::new);
    }

    @Override
    protected Map<String, Locale> testVector() {
        Map<String, Locale> map = new HashMap<>();
        map.put("se", new Locale("se"));
        map.put("en", new Locale("en"));
        map.put("de", new Locale("de"));
        map.put(null, null);
        return map;
    }
}