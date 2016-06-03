package com.speedment.common.codegenxml;

import com.speedment.common.codegenxml.internal.DocTypeImpl;

/**
 *
 * @author Per Minborg
 */
public interface DocType extends Element {
    
    String getRootType();
    
    DocType setRootType(String rootType);
    
    static DocType of(String rootType) {
        return new DocTypeImpl(rootType);
    }
    
}
