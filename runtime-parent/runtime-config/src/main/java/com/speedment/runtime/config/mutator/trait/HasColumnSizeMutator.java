package com.speedment.runtime.config.mutator.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.HasColumnSize;

/**
 * Trait for {@link DocumentMutator} that have a
 * {@link HasColumnSize#getColumnSize() column size} property.
 *
 * @param <DOC>  the primary interface for this document type
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public interface HasColumnSizeMutator<DOC extends Document>
    extends DocumentMutator<DOC>{

    /**
     * Mutates the {@link HasColumnSize#getColumnSize() column size} of the
     * underlying document.
     *
     * @param columnSize  the new column size (or {@code null}).
     */
    default void setColumnSize(Integer columnSize) {
        put(HasColumnSize.COLUMN_SIZE, columnSize);
    }
}
