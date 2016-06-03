package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.Attribute;
import com.speedment.common.codegenxml.XmlDeclaration;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Per Minborg
 */
public class XmlDeclarationImpl implements XmlDeclaration {

    private final List<Attribute> attributes;

    public XmlDeclarationImpl() {
        this.attributes = new ArrayList<>();
    }

    @Override
    public List<Attribute> attributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "<?xml " + attributes().stream()
            .map(a -> ".")
            .collect(joining("", " ", "")) + 
            "?>";
    }
}