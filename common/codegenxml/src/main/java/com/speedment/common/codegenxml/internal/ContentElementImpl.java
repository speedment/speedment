package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.ContentElement;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public final class ContentElementImpl implements ContentElement {

    private String value;
    private boolean escape;

    public ContentElementImpl(String value) {
        this.value = value;
        this.escape = true;
    }

    @Override
    public ContentElement setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public boolean isEscape() {
        return escape;
    }

    @Override
    public ContentElement setEscape(boolean escape) {
        this.escape = escape;
        return this;
    }

}
