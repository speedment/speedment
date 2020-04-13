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
package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.value.AnonymousValue;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of the {@link AnonymousValue} interface.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public final class AnonymousValueImpl implements AnonymousValue {

    private final List<Value<?>> args;
    private final List<Import> imports;
    private final List<Type> typeParams;
    private final List<Field> fields;
    private final List<Method> methods;
    private final List<Initializer> initializers;
    private final List<ClassOrInterface<?>> innerClasses;
    private Type value;

    public AnonymousValueImpl() {
        this.args         = new ArrayList<>();
        this.imports      = new ArrayList<>();
        this.typeParams   = new ArrayList<>();
        this.fields       = new ArrayList<>();
        this.methods      = new ArrayList<>();
        this.initializers = new ArrayList<>();
        this.innerClasses = new ArrayList<>();
    }

    private AnonymousValueImpl(AnonymousValue prototype) {
        this.args             = Copier.copy(prototype.getValues(), HasCopy::copy);
        this.imports          = Copier.copy(prototype.getImports());
        this.typeParams       = new ArrayList<>(prototype.getTypeParameters());
        this.fields           = Copier.copy(prototype.getFields());
        this.methods          = Copier.copy(prototype.getMethods());
        this.initializers     = Copier.copy(prototype.getInitializers());
        this.innerClasses     = Copier.copy(prototype.getClasses(), ClassOrInterface::copy);
        this.value            = prototype.getValue();
    }

    @Override
    public Type getValue() {
        return value;
    }

    @Override
    public List<Value<?>> getValues() {
        return args;
    }

    @Override
    public List<Import> getImports() {
        return imports;
    }

    @Override
    public List<Type> getTypeParameters() {
        return typeParams;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public List<Method> getMethods() {
        return methods;
    }

    @Override
    public List<Initializer> getInitializers() {
        return initializers;
    }

    @Override
    public List<ClassOrInterface<?>> getClasses() {
        return innerClasses;
    }

    @Override
    public AnonymousValue setValue(Type value) {
        this.value = value;
        return this;
    }

    @Override
    public AnonymousValue copy() {
        return new AnonymousValueImpl(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AnonymousValueImpl that = (AnonymousValueImpl) o;
        return Objects.equals(args, that.args) &&
                Objects.equals(imports, that.imports) &&
                Objects.equals(typeParams, that.typeParams) &&
                Objects.equals(fields, that.fields) &&
                Objects.equals(methods, that.methods) &&
                Objects.equals(initializers, that.initializers) &&
                Objects.equals(innerClasses, that.innerClasses) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(args, imports, typeParams, fields, methods, initializers, innerClasses, value);
    }
}