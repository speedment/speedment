package com.speedment.runtime.config.mutator.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.HasDecimalDigits;

/**
 * Trait for {@link DocumentMutator} that have a
 * {@link HasDecimalDigits#getDecimalDigits() decimal digits} property.
 *
 * @param <DOC>  the primary interface for this document type
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public interface HasDecimalDigitsMutator<DOC extends Document>
        extends DocumentMutator<DOC>{

    /**
     * Mutates the {@link HasDecimalDigits#getDecimalDigits() decimal digits} of
     * the underlying document.
     *
     * @param digits  the new decimal digits count (or {@code null}).
     */
    default void setDecimalDigits(Integer digits) {
        put(HasDecimalDigits.DECIMAL_DIGITS, digits);
    }
}
