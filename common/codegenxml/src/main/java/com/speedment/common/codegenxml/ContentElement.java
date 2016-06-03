package com.speedment.common.codegenxml;

import com.speedment.common.codegenxml.internal.ContentElementImpl;
import com.speedment.common.codegenxml.trait.HasIsEscape;
import com.speedment.common.codegenxml.trait.HasValue;

/**
 *
 * @author Per Minborg
 */
public interface ContentElement extends Element, HasValue<ContentElement>, HasIsEscape<ContentElement> {
    
    static ContentElement of(String row) {
        return new ContentElementImpl(row);
    }
    
    static ContentElement ofUnescaped(String row) {
        return new ContentElementImpl(row).setEscape(false);
    }
    
}