package com.speedment.orm.core.manager;

import com.speedment.orm.core.Buildable;
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
public class KeyIndexHolder<KEY, PK, ENTITY> implements IndexHolder<KEY, PK, ENTITY> {

    private final Manager<PK, ENTITY, Buildable<ENTITY>> manager;
    private final Map<KEY, Map<PK, ENTITY>> entities;

    public KeyIndexHolder(Manager<PK, ENTITY, Buildable<ENTITY>> manager) {
        this.manager = manager;
        this.entities = new ConcurrentHashMap<>();
    }

    @Override
    public Stream<ENTITY> stream() {
        return entities.values().stream().flatMap(e -> e.values().stream());
    }

    @Override
    public Stream<ENTITY> stream(KEY key) {
        return streamOfNullable(entities.get(key)).flatMap(e -> e.values().stream());
    }

    @Override
    public void put(KEY key, ENTITY entity) {
        entities.computeIfAbsent(key, k -> new ConcurrentHashMap<>())
                .put(manager.primaryKeyFor(entity), entity);
    }

    @Override
    public void remove(KEY key) {
        entities.remove(key);
    }

}
