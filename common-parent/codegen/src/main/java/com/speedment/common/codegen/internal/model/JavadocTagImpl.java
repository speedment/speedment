/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.JavadocTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link JavadocTag} interface. This
 * class should not be instantiated directly. Instead you should call the
 * {@link JavadocTag#of(java.lang.String)} method to get an instance. In that
 * way, you can later change the implementing class without modifying the using
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
}

abstract class JavadocTagBase implements JavadocTag {

    private String name;
    private String value;
    private String text;
    private final List<Import> imports;

    JavadocTagBase(String name) {
        this.name    = requireNonNull(name);
        this.value   = null;
        this.text    = null;
        this.imports = new ArrayList<>();
    }

    JavadocTagBase(String name, String text) {
        this.name    = requireNonNull(name);
        this.value   = null;
        this.text    = text;
        this.imports = new ArrayList<>();
    }

    JavadocTagBase(String name, String value, String text) {
        this.name    = requireNonNull(name);
        this.value   = value;
        this.text    = text;
        this.imports = new ArrayList<>();
    }

    JavadocTagBase(JavadocTag prototype) {
        requireNonNull(prototype);
        this.name    = prototype.getName();
        this.value   = prototype.getValue().orElse(null);
        this.text    = prototype.getText().orElse(null);
        this.imports = Copier.copy(prototype.getImports());
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public JavadocTag setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public List<Import> getImports() {
        return imports;
    }

    @Override
    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    @Override
    public JavadocTag setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public JavadocTag setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

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
        hash = 67 * hash + Objects.hashCode(this.imports);
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
        if (!Objects.equals(this.imports, other.imports)) {
            return false;
        }
        return Objects.equals(this.text, other.text);
    }

}
