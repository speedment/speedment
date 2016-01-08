package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;

/**
 *
 * @author Emil Forslund
 * @param <T> Mutator type
 */
@Api(version = "2.3")
public interface HasMutator<T extends DocumentMutator> {

    /**
     * Returns a {@link DocumentMutator} for this Document. A DocumentMutator allows a
     * Document to be updated in a type safe way.
     *
     * @return a DocumentMutator for this Document
     * @throws UnsupportedOperationException if this Document does not support
     * mutation. For example, an immutable Document might elect to throw an
     * exception upon a call to this method.
     */
    T mutator();

}
