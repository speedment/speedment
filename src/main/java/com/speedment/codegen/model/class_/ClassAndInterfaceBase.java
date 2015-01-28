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
package com.speedment.codegen.model.class_;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.AbstractModifiableCodeModel;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.type.Type_;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.method.Method_;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.statement.block.Initializable;
import com.speedment.codegen.model.statement.block.InitializerBlock_;
import com.speedment.codegen.model.dependency_.Dependable;
import com.speedment.codegen.model.dependency_.Dependency_;
import com.speedment.codegen.model.field.FieldAdder;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.field.Fieldable;
import com.speedment.codegen.model.javadoc.JavadocAdder;
import com.speedment.codegen.model.javadoc.Javadoc_;
import com.speedment.codegen.model.javadoc.Javadockable;
import com.speedment.codegen.model.method.MethodAdder;
import com.speedment.codegen.model.method.Methodable;
import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.codegen.model.modifier.Modifier_;
import com.speedment.codegen.model.package_.Packagable;
import com.speedment.codegen.model.package_.Package_;
import com.speedment.util.stream.StreamUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <M>
 */
public abstract class ClassAndInterfaceBase<T extends ClassAndInterfaceBase<T, M>, M extends Enum<M> & Modifier_<M>>
        extends AbstractModifiableCodeModel<T, M>
        implements CodeModel, Modifiable<M>, Annotatable, Fieldable, Methodable,
        Interfaceable, Nameable, Packagable, Initializable, Nestable, Dependable, Javadockable {

    private final List<Interface_> interfaces;
    private final List<Field_> fields;
    private final List<Method_> methods;
    private final Set<M> modifiers;
    private final List<Annotation_> annotations;
    private final List<ClassAndInterfaceBase<?, ?>> nestedClasses;
    private final List<InitializerBlock_> initializers;
    private final Set<Dependency_> dependencies;
    private Javadoc_ javadoc;
    private Package_ pagage;
    private CharSequence name;

    public ClassAndInterfaceBase(final Class<M> mClass) {
        fields = new ArrayList<>();
        methods = new ArrayList<>();
        interfaces = new ArrayList<>();
        modifiers = EnumSet.noneOf(mClass);
        annotations = new ArrayList<>();
        nestedClasses = new ArrayList<>();
        initializers = new ArrayList<>();
        dependencies = new HashSet<>();
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.of(fields, methods, interfaces, modifiers, annotations, nestedClasses, initializers, dependencies);
    }

    @Override
    public T add(final Interface_ interf) {
        return add(interf, interfaces::add);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T add(final Field_ field) {
        return add(field, fields::add);
    }

    @Override
    public T add(final Method_ method_) {
        return add(method_, methods::add);
    }

    @Override
    public T add(final Annotation_ annotation) {
        return add(annotation, annotations::add);
    }

    @Override
    public T add(final Class_ nestedClass) {
        return add(nestedClass, nestedClasses::add);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T add(final InitializerBlock_ initializer) {
        return add(initializer, initializers::add);
    }

    @Override
    public boolean has(Annotation_ annotation_) {
        return annotations.contains(annotation_);
    }

    @Override
    public List<Interface_> getInterfaces() {
        return interfaces;
    }

    @Override
    public List<Field_> getFields() {
        return fields;
    }

    @Override
    public List<Method_> getMethods() {
        return methods;
    }

    @Override
    public Package_ getPackage() {
        return pagage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T setPackage(final Package_ package_) {
        return set(package_, p -> pagage = p);
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public T setName(final CharSequence name) {
        return set(name, n -> this.name = n);
    }

    @Override
    public Set<M> getModifiers() {
        return modifiers;
    }


    @Override
    public List<Annotation_> getAnnotations() {
        return annotations;
    }

    @Override
    public List<ClassAndInterfaceBase<?, ?>> getNestedClasses() {
        return nestedClasses;
    }

    @Override
    public List<InitializerBlock_> getInitializers() {
        return initializers;
    }

    @Override
    public Set<Dependency_> getDependencies() {
        return dependencies;
    }

    @Override
    public boolean has(Dependency_ dep) {
        return dependencies.contains(dep);
    }

    @Override
    public T add(Dependency_ dep) {
        return add(dep, dependencies::add);
    }

    public Type_ toType() {

        //return new Type_(CodeUtil.flattenName(this)).add();
        return null;
    }

    public T package_(CharSequence packagePath) {
        return setPackage(Package_.by(packagePath));
    }

    public MethodAdder<T> methodAdder() {
        return new MethodAdder<>((T) this, this::add);
    }

    public FieldAdder<T> fieldAdder() {
        return new FieldAdder<>((T) this, this::add);
    }

    public JavadocAdder<T> javadocAdder() {
        return new JavadocAdder<>((T) this, this::setJavadoc);
    }

    @Override
    public Javadoc_ getJavadoc() {
        return javadoc;
    }

    @Override
    public T setJavadoc(Javadoc_ javadoc_) {
        return set(javadoc_, j -> javadoc = j);
    }

}
