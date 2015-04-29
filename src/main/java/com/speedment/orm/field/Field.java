package com.speedment.orm.field;

import com.speedment.orm.config.model.Column;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public interface Field<ENTITY> {

    public boolean isNullIn(ENTITY entity);

    public Column getColumn();
    
}
