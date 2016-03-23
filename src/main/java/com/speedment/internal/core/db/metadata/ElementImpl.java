package com.speedment.internal.core.db.metadata;

import static com.sun.javafx.image.impl.IntArgbPre.getter;
import java.util.Map;

/**
 *
 * @author Per Minborg
 */
public class ElementImpl<T> implements Element<T> {

    private final String key;
    private final Map<String, Object> map;

    public ElementImpl(String key, Map<String, Object> map) {
        this.key = key;
        this.map = map;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public T get() {
        return (T) map.get(key);
    }

}
