package com.speedment.orm.core.manager;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.core.Buildable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractManager<PK, ENTITY, BUILDER extends Buildable<ENTITY>> implements Manager<PK, ENTITY, BUILDER> {

    private final Map<List<Column>, IndexHolder> indexes;
    
    public AbstractManager() {
        indexes = new ConcurrentHashMap<>();
    }
    
    @Override
    public void insert(ENTITY entity) {
        indexes.entrySet().stream().forEach(e -> {
            e.getValue().put(makeKey(e.getKey(), entity), entity);
        });
    }
    
    @Override
    public void update(ENTITY entity) {
        //TODO Make atomic.
        delete(entity);
        insert(entity);
    }
    
    @Override
    public void delete(ENTITY entity) {
        indexes.entrySet().stream().forEach(e -> {
            e.getValue().remove(makeKey(e.getKey(), entity));
        });
    }
    
    private Object makeKey(List<Column> columns, ENTITY entity) {
        if (columns.size() == 1) {
            return get(entity, columns.get(0));
        } else {
            return columns.stream()
                .map(c -> get(entity, c))
                .collect(Collectors.toList());
        }
    }
}