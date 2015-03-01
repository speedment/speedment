/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.core.dao;

import com.speedment.orm.config.model.Table;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface Dao<PK, ENTITY, BEAN, BUILDER> {

    // Metadata
    Table getTable();

    // Entity Mapping
    PK primaryKey(ENTITY entity);

    BEAN bean(ENTITY entity);

    BUILDER builder(ENTITY entity);

    ENTITY entity(ENTITY entity);

    ENTITY newEntity();

    // Retrieval
    Stream<ENTITY> stream();

    default long size() {
        return stream().count();
    }

    // Persistence
    ENTITY insert(ENTITY entity);

    ENTITY update(ENTITY entity);

    void delete(PK pk);

    void load();

}
