package com.speedment.orm.core.manager;

import static com.speedment.util.stream.StreamUtil.streamOfNullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class KeyIndexHolder<KEY, ENTITY> implements IndexHolder<KEY, ENTITY> {
    
    private final Map<KEY, ENTITY> entities;
    
    public KeyIndexHolder() {
        entities = new ConcurrentHashMap<>();
    }

    @Override
    public Stream<ENTITY> stream() {
        return entities.values().stream();
    }

    @Override
    public Stream<ENTITY> stream(KEY key) {
        return streamOfNullable(entities.get(key));
    }

    @Override
    public void put(KEY key, ENTITY entity) {
        entities.put(key, entity);
    }

    @Override
    public void remove(KEY key) {
        entities.remove(key);
    }
    
}