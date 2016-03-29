package com.speedment.internal.util;

import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Per Minborg
 */
public class CaseInsensitiveMaps {

    public static <T> Map<String, T> newCaseInsensitiveMap() {
        return new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    private CaseInsensitiveMaps() {
        instanceNotAllowed(CaseInsensitiveMaps.class);
    }

}
