/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.codegen.lang.models.implementation;

import com.speedment.internal.codegen.lang.models.Javadoc;
import com.speedment.internal.codegen.lang.models.JavadocTag;
import com.speedment.internal.codegen.util.Copier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * This is the default implementation of the {@link Javadoc} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Javadoc#of()} method to get an instance. In that way, you can layer 
 * change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Javadoc
 */
public final class JavadocImpl implements Javadoc {

    private String text;
    private final List<JavadocTag> tags;

    /**
     * Initializes this javadoc block.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Javadoc#of()} method!
     */
    public JavadocImpl() {
        text = "";
        tags = new ArrayList<>();
    }

    /**
     * Initializes this javadoc block using a text. The text may have multiple
     * lines separated by new-line characters.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Javadoc#of(java.lang.String)} method!
     * 
     * @param text  the text
     */
    public JavadocImpl(final String text) {
        this.text = requireNonNull(text);
        this.tags = new ArrayList<>();
    }

    /**
     * Copy constructor.
     * 
     * @param prototype the prototype
     */
    protected JavadocImpl(final Javadoc prototype) {
        text = requireNonNull(prototype).getText();
        tags = Copier.copy(prototype.getTags());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Javadoc setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JavadocTag> getTags() {
        return tags;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JavadocImpl copy() {
        return new JavadocImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.text);
        hash = 47 * hash + Objects.hashCode(this.tags);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Javadoc.class.isAssignableFrom(o.getClass()))
            .map(o -> (Javadoc) o)
            .filter(o -> Objects.equals(getText(), o.getText()))
            .filter(o -> Objects.equals(getTags(), o.getTags()))
            .isPresent();
    }
}
