package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.DocType;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class DocTypeImpl implements DocType {
    
    private String rootType;
    
    public DocTypeImpl(String rootType) {
        this.rootType = requireNonNull(rootType);
    }

    @Override
    public String getRootType() {
        return rootType;
    }

    @Override
    public DocType setRootType(String rootType) {
        this.rootType = requireNonNull(rootType);
        return this;
    }
}