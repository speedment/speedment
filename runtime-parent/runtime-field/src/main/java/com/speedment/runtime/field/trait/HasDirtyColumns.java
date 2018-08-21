package com.speedment.runtime.field.trait;

import com.speedment.runtime.config.identifier.ColumnIdentifier;

import java.util.stream.Stream;

/**
 * Used for entity field modification tracking of a n entity, this trait allows retrieval of a stream of column identifiers
 * representing the fields of the entity that have been modified since the entity was last synchronized with the underlying
 * database.
 *
 * @param <ENTITY>
 */
public interface HasDirtyColumns<ENTITY> {

    /**
     * Returns identifiers for all columns that contain updated values. Used to allow for selective INSERT and UPDATE
     * SQL statements that only contain the values for the fields of the entity that are actually updated.
     *
     * @see #clearUpdatedColumns()
     * @return identifiers for all columns that contain updated values
     */
    Stream<ColumnIdentifier<ENTITY>> dirtyColumns();

    /**
     * Resets the set of updated columns. Immediately after a call to this method,
     * the {@link #dirtyColumns} method will
     * return an empty stream.
     */
    void clearUpdatedColumns();
}
