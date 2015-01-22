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
package com.speedment.codegen.model;

import com.speedment.codegen.model.modifier.Modifier_;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <M>
 */
public abstract class ClassAndInterfaceBase<T extends ClassAndInterfaceBase<T, M>, M extends Enum<M> & Modifier_<M>> implements CodeModel {

    private final List<Interface_> interfaces;
    private final List<Field_> fields;
    private final List<Method_> methods;
    private final Set<M> modifiers;
    private final List<Annotation_> annotations;
    private Package_ pagage;
    private CharSequence name;

    public ClassAndInterfaceBase(final Class<M> mClass) {
        fields = new ArrayList<>();
        methods = new ArrayList<>();
        interfaces = new ArrayList<>();
        modifiers = EnumSet.noneOf(mClass);
        annotations = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public T add(final Interface_ interf) {
        getInterfaces().add(interf);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T add(final Field_ field) {
        getFields().add(field);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T add(final Method_ method_) {
        getMethods().add(method_);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T add(final Annotation_ annotation) {
        getAnnotations().add(annotation);
        return (T) this;
    }

//    public T add(M classModifier_) {
//        getModifiers().add(classModifier_);
//        return (T) this;
//    }
    @SuppressWarnings("unchecked")
    public T add(final M firstClassModifier_m, final M... restClassModifiers) {
        getModifiers().add(firstClassModifier_m);
        Stream.of(restClassModifiers).forEach(getModifiers()::add);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T set(final Set<M> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return (T) this;
    }

    public boolean is(M modifier) {
        return modifiers.contains(modifier);
    }

    public List<Interface_> getInterfaces() {
        return interfaces;
    }

    public List<Field_> getFields() {
        return fields;
    }

    public List<Method_> getMethods() {
        return methods;
    }

    public Package_ getPackage() {
        return pagage;
    }

    public void setPackage(final Package_ pagage) {
        this.pagage = pagage;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(final CharSequence name) {
        this.name = name;
    }

    public Set<M> getModifiers() {
        return modifiers;
    }

    public List<Annotation_> getAnnotations() {
        return annotations;
    }
}
