/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.codegen.model;

import com.speedment.codegen.model.JavadocTag;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * This is the default implementation of the {@link JavadocTag} interface. This
 * class should not be instantiated directly. Instead you should call the
 * {@link JavadocTag#of(java.lang.String)} method to get an instance. In that
 * way, you can layer change the implementing class without modifying the using
 * code.
 *
 * @author Emil Forslund
 * @see JavadocTag
 */
public final class JavadocTagImpl extends JavadocTagBase {

    /**
     * Initializes this javadoc tag using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the {@link JavadocTag#of(java.lang.String)} method!
     *
     * @param name the name
     */
    public JavadocTagImpl(String name) {
        super(name);
    }

    /**
     * Initializes this javadoc tag using a name and a text.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the {@link JavadocTag#of(java.lang.String, java.lang.String)} method!
     *
     * @param name the name
     * @param text the text
     */
    public JavadocTagImpl(String name, String text) {
        super(name, text);
    }

    /**
     * Initializes this javadoc tag using a name, a value and a text.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the
     * {@link JavadocTag#of(java.lang.String, java.lang.String, java.lang.String)}
     * method!
     *
     * @param name the name
     * @param value the value
     * @param text the text
     */
    public JavadocTagImpl(String name, String value, String text) {
        super(name, value, text);
    }

    /**
     * Copy constructor.
     *
     * @param prototype the prototype
     */
    protected JavadocTagImpl(JavadocTag prototype) {
        super(prototype);
    }

    public final static class JavadocTagConst extends JavadocTagBase {

        public JavadocTagConst(String name) {
            super(name);
        }

        public JavadocTagConst(String name, String value) {
            super(name, value);
        }

        public JavadocTagConst(String name, String value, String text) {
            super(name, value, text);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JavadocTag setValue(String value) {
            return copy().setValue(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JavadocTag setText(String text) {
            return copy().setText(text);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JavadocTag setName(String name) {
            return copy().setName(name);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JavadocTagImpl copy() {
            return new JavadocTagImpl(this);
        }
    }
}

abstract class JavadocTagBase implements JavadocTag {

    private String name;
    private String value;
    private String text;

    JavadocTagBase(String name) {
        this.name = requireNonNull(name);
        this.value = null;
        this.text = null;
    }

    JavadocTagBase(String name, String text) {
        this.name = requireNonNull(name);
        this.value = null;
        this.text = text;
    }

    JavadocTagBase(String name, String value, String text) {
        this.name = requireNonNull(name);
        this.value = value;
        this.text = text;
    }

    JavadocTagBase(JavadocTag prototype) {
        requireNonNull(prototype);
        this.name = prototype.getName();
        this.value = prototype.getValue().orElse(null);
        this.text = prototype.getText().orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JavadocTag setValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JavadocTag setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JavadocTag setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JavadocTagImpl copy() {
        return new JavadocTagImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.value);
        hash = 67 * hash + Objects.hashCode(this.text);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JavadocTagBase other = (JavadocTagBase) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        return true;
    }

}
