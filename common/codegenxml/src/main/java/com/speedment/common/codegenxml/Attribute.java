package com.speedment.common.codegenxml;

import com.speedment.common.codegenxml.internal.AttributeImpl;
import com.speedment.common.codegenxml.trait.HasName;
import com.speedment.common.codegenxml.trait.HasValue;

/**
 *
 * @author Per Minborg
 */
public interface Attribute extends HasName<Attribute>, HasValue<Attribute> {

    static Attribute of(String name) {
        return new AttributeImpl(name);
    }

}
