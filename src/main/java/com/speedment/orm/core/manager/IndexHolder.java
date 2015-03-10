package com.speedment.orm.core.manager;

import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface IndexHolder<KEY, PK, ENTITY> {
    
    Stream<ENTITY> stream();

    Stream<ENTITY> stream(KEY key);
    
    void put(KEY key, ENTITY entity);
    
    void remove(KEY key);
}