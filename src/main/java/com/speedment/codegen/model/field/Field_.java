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
package com.speedment.codegen.model.field;

import com.speedment.codegen.Nameable;
import com.speedment.codegen.model.AbstractModifiableCodeModel;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.type.Type_;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.javadoc.JavadocAdder;
import com.speedment.codegen.model.javadoc.Javadoc_;
import com.speedment.codegen.model.javadoc.Javadockable;
import com.speedment.codegen.model.modifier.FieldModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.codegen.model.statement.expression.Expression;
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
public class Field_ extends AbstractModifiableCodeModel<Field_, FieldModifier_> implements CodeModel, Nameable, Modifiable<FieldModifier_>, Annotatable, Javadockable {

    private final Set<FieldModifier_> modifiers;
    private final List<Annotation_> annotations;
    private Javadoc_ javadoc;
    private Type_ type;
    private CharSequence name;
    private Expression expression;

    public Field_() {
        this.modifiers = EnumSet.noneOf(FieldModifier_.class);
        this.annotations = new ArrayList<>();
    }

    public Field_(Type_ type, CharSequence name) {
        this();
        setType(type);
        setName(name);
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public Field_ setName(CharSequence name_) {
        return set(name_, n -> name = n);
    }

    public Field_ setType(Type_ type) {
        return set(type, t -> this.type = t);
    }

    public Expression getExpression() {
        return expression;
    }

    public Field_ setExpression(Expression expression) {
        return set(expression, e -> this.expression = e);
    }

    @Override
    public Type getModelType() {
        return Type.FIELD;
    }

    public Type_ getType() {
        return type;
    }

    @Override
    public Set<FieldModifier_> getModifiers() {
        return modifiers;
    }

    @Override
    public boolean is(FieldModifier_ modifier) {
        return modifiers.contains(modifier);
    }

    @Override
    public Field_ add(final Annotation_ annotation) {
        return add(annotation, getAnnotations()::add);
    }

    @Override
    public List<Annotation_> getAnnotations() {
        return annotations;
    }

    @Override
    public boolean has(Annotation_ annotation_) {
        return annotations.contains(annotation_);
    }

    public Field_ public_() {
        return add(FieldModifier_.PUBLIC);
    }

    public Field_ protected_() {
        return add(FieldModifier_.PROTECTED);
    }

    public Field_ private_() {
        return add(FieldModifier_.PRIVATE);
    }

    public Field_ static_() {
        return add(FieldModifier_.STATIC);
    }

    public Field_ final_() {
        return add(FieldModifier_.FINAL);
    }

    public Field_ transient_() {
        return add(FieldModifier_.TRANSIENT);
    }

    public Field_ volatile_() {
        return add(FieldModifier_.VOLATILE);
    }

    @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.<CodeModel>of(annotations, modifiers);
    }

    @Override
    public Javadoc_ getJavadoc() {
        return javadoc;
    }

    @Override
    public Field_ setJavadoc(Javadoc_ javadoc) {
        return set(javadoc, j -> this.javadoc = j);
    }

    public JavadocAdder<Field_> javadocAdder() {
        return new JavadocAdder<>(this, this::setJavadoc);
    }

}
