package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.Attribute;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public final class AttributeImpl implements Attribute {

    private String name;
    private String value;

    public AttributeImpl(String name) {
        this.name = requireNonNull(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public Attribute setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    @Override
    public Attribute setValue(String value) {
        this.value = value;
        return this;
    }
}