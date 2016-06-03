package com.speedment.common.codegenxml;

import com.speedment.common.codegenxml.internal.TagElementImpl;
import com.speedment.common.codegenxml.trait.HasAttributes;
import com.speedment.common.codegenxml.trait.HasElements;
import com.speedment.common.codegenxml.trait.HasName;

/**
 *
 * @author Per Minborg
 */
public interface TagElement extends
    Element,
    HasName<TagElement>,
    HasElements<TagElement>,
    HasAttributes<TagElement> {

    static TagElement of(String name) {
        return new TagElementImpl(name);
    }
}
