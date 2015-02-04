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
package com.speedment.codegen.model.method;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.AbstractModifiableCodeModel;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.statement.Statement_;
import com.speedment.codegen.model.type.Type_;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.javadoc.JavadocAdder;
import com.speedment.codegen.model.javadoc.Javadoc_;
import com.speedment.codegen.model.javadoc.Javadockable;
import com.speedment.codegen.model.parameter.Parameterable;
import com.speedment.codegen.model.modifier.MethodModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.codegen.model.parameter.Parameter_;
import com.speedment.util.stream.StreamUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Method_ extends AbstractModifiableCodeModel<Method_, MethodModifier_> implements CodeModel, Modifiable<MethodModifier_>, Annotatable, Nameable, Parameterable, Javadockable {

    private final Set<MethodModifier_> modifiers;
    private final List<Annotation_> annotations;
    private final List<Parameter_> parameters;
    private final List<Statement_> statements;
    private Javadoc_ javadoc;
    private Type_ type;
    private CharSequence name;

    public Method_() {
        this.parameters = new ArrayList<>();
        this.statements = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.modifiers = EnumSet.noneOf(MethodModifier_.class);
    }

    public Method_(Type_ type, CharSequence name) {
        this();
        this.type = type;
        this.name = name;
    }

    @Override
    public Method_ add(Parameter_ field_) {
        return with(field_, getParameters()::add);
    }

    public Method_ add(Statement_ statement) {
        return with(statement, getStatements()::add);
    }

    public Type_ getType() {
        return type;
    }

    public Method_ setType(Type_ type_) {
        return with(type_, t -> this.type = t);
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public Method_ setName(CharSequence name) {
        return with(name, n -> this.name = n);
    }

    @Override
    public List<Parameter_> getParameters() {
        return parameters;
    }

    @Override
    public boolean hasParameter(Parameter_ parameter) {
        return parameters.contains(parameter);
    }

    public List<Statement_> getStatements() {
        return statements;
    }

    @Override
    public Type getModelType() {
        return Type.METHOD;
    }

    @Override
    public Set<MethodModifier_> getModifiers() {
        return modifiers;
    }

    @Override
    public List<Annotation_> getAnnotations() {
        return annotations;
    }

    @Override
    public boolean has(Annotation_ annotation_) {
        return annotations.contains(annotation_);
    }

    @Override
    public Method_ add(Annotation_ annotation) {
        return with(annotation, getAnnotations()::add);
    }

    public Method_ abstract_() {
        return add(MethodModifier_.ABSTRACT);
    }

    public Method_ final_() {
        return add(MethodModifier_.FINAL);
    }

    public Method_ native_() {
        return add(MethodModifier_.NATIVE);
    }

    public Method_ private_() {
        return add(MethodModifier_.PRIVATE);
    }

    public Method_ protected_() {
        return add(MethodModifier_.PROTECTED);
    }

    public Method_ public_() {
        return add(MethodModifier_.PUBLIC);
    }

    public Method_ static_() {
        return add(MethodModifier_.STATIC);
    }

    public Method_ strictfp_() {
        return add(MethodModifier_.STRICTFP);
    }

    public Method_ synchronized_() {
        return add(MethodModifier_.SYNCHRONIZED);
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.<CodeModel>of(annotations, modifiers, parameters, statements);
    }

    @Override
    public Javadoc_ getJavadoc() {
        return javadoc;
    }

    @Override
    public Method_ setJavadoc(Javadoc_ javadoc) {
        return with(javadoc, j -> this.javadoc = j);
    }

    public JavadocAdder<Method_> javadocAdder() {
        return new JavadocAdder<>(this, this::setJavadoc);
    }

}
