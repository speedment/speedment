package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import static com.speedment.runtime.config.trait.HasTypeMapper.DATABASE_TYPE;
import static com.speedment.runtime.config.util.DocumentUtil.newNoSuchElementExceptionFor;
import java.util.Optional;

/**
 * Trait for documents that have a {@code TypeMapper} specified.
 * 
 * @author Emil Forslund
 * @since  3.0.2
 */
public interface HasTypeMapper extends Document {
    
    /**
     * The attribute under which the name of the {@code TypeMapper} is stored in
     * the configuration file.
     */
    String TYPE_MAPPER = "typeMapper";
    
    /**
     * The attribute under which the database type is stored in the 
     * configuration file. This is used initially to determine the default 
     * {@code TypeMapper} to use.
     */
    String DATABASE_TYPE  = "databaseType";
    
    /**
     * Returns the name of the mapper class that will be used to generate a java
     * representation of the database types.
     *
     * @return the mapper class
     */
    default Optional<String> getTypeMapper() {
        return getAsString(TYPE_MAPPER);
    }
    
    /**
     * Returns the name of the class that represents the database type.
     *
     * @return the database type class
     */
    default String getDatabaseType() {
        return getAsString(DATABASE_TYPE)
            .orElseThrow(newNoSuchElementExceptionFor(this, DATABASE_TYPE));
    }
    
    /**
     * Returns the class that represents the database type.
     *
     * @return the database type
     */
    default Class<?> findDatabaseType() {
        final String name = getDatabaseType();

        try {
            return Class.forName(name);
        } catch (final ClassNotFoundException ex) {
            throw new SpeedmentConfigException(
                "Could not find database type: '" + name + "'.", 
                ex
            );
        }
    }
}