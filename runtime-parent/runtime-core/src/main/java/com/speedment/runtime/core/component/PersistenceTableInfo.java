package com.speedment.runtime.core.component;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;

import java.util.stream.Stream;

public interface PersistenceTableInfo<ENTITY> {
    /**
     * Returns an identifier for the table.
     *
     * @return the table identifier
     */
    TableIdentifier<ENTITY> getTableIdentifier();

    /**
     * Returns the entity class for this Manager.
     *
     * @return the entity class
     */
    Class<ENTITY> getEntityClass();

    /**
     * Returns a stream of the fields of the table.
     *
     * @return a stream of all fields
     */
    Stream<Field<ENTITY>> fields();

    /**
     * Returns a stream of the fields that are included in the primary key of
     * the table.
     *
     * @return the primary key fields
     */
    Stream<Field<ENTITY>> primaryKeyFields();

}
