package com.speedment.orm.core.manager;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.core.Buildable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractManager<PK, ENTITY, BUILDER extends Buildable<ENTITY>> implements Manager<PK, ENTITY, BUILDER> {

    private final Map<List<Column>, IndexHolder> indexes;
    
    public AbstractManager() {
        indexes = new ConcurrentHashMap<>();
    }
}