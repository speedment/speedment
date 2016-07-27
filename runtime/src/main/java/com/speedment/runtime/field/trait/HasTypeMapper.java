package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;

/**
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
@Api(version = "3.0")
public interface HasTypeMapper {
    
    /**
     * Returns the type mapper used by this field.
     * 
     * @return  the type mapper
     */
    TypeMapper<?, ?> typeMapper();
    
}
