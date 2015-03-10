package com.speedment.orm.core.manager;

import static com.speedment.util.stream.StreamUtil.streamOfNullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @param <KEY> Key type used for this IndexHolder
 * @param <PK> Primary Key type for this Manager
 * @param <ENTITY> Entity type for this Manager
 */
public class UniqueKeyIndexHolder<KEY, PK, ENTITY> implements IndexHolder<KEY, PK, ENTITY> {
    
    private final Map<KEY, ENTITY> entities;
    
    public UniqueKeyIndexHolder() {
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