package com.speedment.runtime.core.manager.sql;

import com.speedment.runtime.field.Field;

import java.util.List;

/**
 * Trait for {@link SqlStatement} that describes the generated keys that can be
 * set automatically when an insert is done in the database.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface HasGeneratedKeys extends SqlStatement {

    /**
     * Returns a list with the fields that are generated automatically by this
     * insert statement.
     *
     * @return  the generated fields
     */
    List<Field<?>> getGeneratedColumnFields();

    /**
     * Adds the specified key to the list of keys that has been generated.
     *
     * @param generatedKey to be added
     */
    void addGeneratedKey(Long generatedKey);

    /**
     * Notifies the generated key listener that a key has been generated.
     */
    void notifyGeneratedKeyListener();

}
