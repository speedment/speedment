package com.speedment.orm.core.manager;

import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @param <KEY> Key type used for this IndexHolder
 * @param <PK> Primary Key type for this Manager
 * @param <ENTITY> Entity type for this Manager
 */
public interface IndexHolder<KEY, PK, ENTITY> {

    Stream<ENTITY> stream();

    Stream<ENTITY> stream(KEY key);

    void put(KEY key, ENTITY entity);

    void remove(KEY key);
}
