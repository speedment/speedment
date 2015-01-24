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
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.statement.Statement_;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.field.parameter.Parameter_;
import com.speedment.codegen.model.parameter.Parameterable;
import com.speedment.codegen.model.modifier.MethodModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Method_ implements CodeModel, Modifiable<MethodModifier_>, Annotatable, Nameable, Parameterable {

    private final Set<MethodModifier_> modifiers;
    private final List<Annotation_> annotations;
    private final List<Parameter_> parameters; 
    private final List<Statement_> statements; 
    private Type_ type;
    private CharSequence name;

    public Method_(Type_ type_, CharSequence name_) {
        this.parameters = new ArrayList<>();
        this.statements = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.type = type_;
        this.name = name_;
        this.modifiers = EnumSet.noneOf(MethodModifier_.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Method_ add(final MethodModifier_ firstClassModifier_m, final MethodModifier_... restClassModifiers) {
        getModifiers().add(firstClassModifier_m);
        Stream.of(restClassModifiers).forEach(getModifiers()::add);
        return this;
    }

    @Override
    public Method_ add(Parameter_ field_) {
        getParameters().add(field_);
        return this;
    }

    public Method_ add(Statement_ statement) {
        statements.add(statement);
        return this;
    }

    public Type_ getType() {
        return type;
    }

    public void setType(Type_ type_) {
        this.type = type_;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public Method_ setName(CharSequence name) {
        this.name = name;
        return this;
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
    public boolean is(MethodModifier_ modifier) {
        return modifiers.contains(modifier);
    }

    @Override
    public Set<MethodModifier_> getModifiers() {
        return modifiers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Method_ set(final Set<MethodModifier_> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return this;
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
        annotations.add(annotation);
        return this;
    }

    public Method_ abstract_() {
        add(MethodModifier_.ABSTRACT);
        return this;
    }

    public Method_ final_() {
        add(MethodModifier_.FINAL);
        return this;
    }

    public Method_ native_() {
        add(MethodModifier_.NATIVE);
        return this;
    }

    public Method_ private_() {
        add(MethodModifier_.PRIVATE);
        return this;
    }

    public Method_ protected_() {
        add(MethodModifier_.PROTECTED);
        return this;
    }

    public Method_ public_() {
        add(MethodModifier_.PUBLIC);
        return this;
    }

    public Method_ static_() {
        add(MethodModifier_.STATIC);
        return this;
    }

    public Method_ strictfp_() {
        add(MethodModifier_.STRICTFP);
        return this;
    }

    public Method_ synchronized_() {
        add(MethodModifier_.SYNCHRONIZED);
        return this;
    }

}
