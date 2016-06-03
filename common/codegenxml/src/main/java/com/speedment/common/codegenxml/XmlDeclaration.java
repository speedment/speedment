package com.speedment.common.codegenxml;

import com.speedment.common.codegenxml.internal.XmlDeclarationImpl;
import com.speedment.common.codegenxml.trait.HasAttributes;

/**
 *
 * @author Per Minborg
 */
public interface XmlDeclaration extends Element, HasAttributes<XmlDeclaration> {
    
    static XmlDeclaration of() {
        return new XmlDeclarationImpl();
    }
    
    static XmlDeclaration of(String version, String encoding) {
        return of()
            .add(Attribute.of("version").setValue(version))
            .add(Attribute.of("encoding").setValue(encoding));
    }
    
}
