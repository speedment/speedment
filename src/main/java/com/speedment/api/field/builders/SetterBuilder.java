package com.speedment.api.field.builders;

import com.speedment.api.annotation.Api;
import com.speedment.api.field.Field;
import java.util.function.Function;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 */
@Api(version = "2.1")
public interface SetterBuilder<ENTITY, V> extends Function<ENTITY, ENTITY> {
    
    Field<ENTITY> getField();
    V getValue();
}