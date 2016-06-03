package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.internal.view.trait.HasAttributesView;
import com.speedment.common.codegenxml.Attribute;
import com.speedment.common.codegenxml.Element;
import com.speedment.common.codegenxml.TagElement;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Per Minborg
 */
public class TagElementImpl implements TagElement {

    private String name;
    private final List<Attribute> attributes;
    private final List<Element> elements;

    public TagElementImpl(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.elements = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public TagElement setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    @Override
    public List<Attribute> attributes() {
        return attributes;
    }

    @Override
    public List<Element> elements() {
        return elements;
    }

    @Override
    public String toString() {
        return "<" + name + attributes().stream()
            .map(a -> ".")
            .collect(joining("", " ", "")) + 
            ">...</" + name + ">";
    }
}