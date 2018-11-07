package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.internal.BaseDocument;
import com.speedment.runtime.config.util.DocumentUtil;

/**
 * Trait for {@link Document documents} that has a {@link #deepCopy()} method
 * that returns a mutable copy of the document.
 *
 * @author Emil Forslund
 * @since  3.1.9
 */
public interface HasDeepCopy extends Document {

    /**
     * Returns a <em>mutable</em> copy of this document. The deepCopy should be
     * a deep copy, but does not need to use the same implementations as the
     * original.
     *
     * @return  a mutable deep copy of the document
     */
    default Document deepCopy() {
        return DocumentUtil.deepCopy(this,
            data -> new BaseDocument(getParent().orElse(null), data));
    }
}
