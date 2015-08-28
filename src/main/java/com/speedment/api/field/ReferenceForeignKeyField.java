package com.speedment.api.field;

import com.speedment.api.annotation.Api;
import com.speedment.api.field.methods.Getter;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 * @param <FK>
 */
@Api(version = "2.1")
public interface ReferenceForeignKeyField<ENTITY, V, FK> extends ReferenceField<ENTITY, V> {
    /**
     * Returns a function that can find a foreign entity pointed out by this
     * field.
     * 
     * @return  the finder
     */
    Getter<ENTITY, FK> finder();
    
    /**
     * Finds and returns the foreign key Entity using the provided Entity.
     *
     * @param entity  to use when finding the foreign key Entity
     * @return        the foreign key Entity
     */
    FK findFrom(ENTITY entity);
}