package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import java.util.Optional;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
@Api(version = "3.0")
public interface HasIdentifier<ENTITY> {
    
    /**
     * Returns the unique identifier of this field.
     * 
     * @return  the identifier
     */
    FieldIdentifier<ENTITY> identifier();
    
    /**
     * Locates the column that this field is referencing by using the specified
     * {@link Speedment} instance.
     * 
     * @param project  the project instance
     * @return         the column
     */
    default Optional<? extends Column> findColumn(Project project) {
        return DocumentDbUtil.referencedColumnIfPresent(project, identifier());
    }
    
}