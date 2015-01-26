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
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.CodeModel.Type;
import com.speedment.codegen.model.Expression_;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.annotation.Annotation_;
import com.speedment.codegen.model.modifier.FieldModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.util.StreamUtil;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Field_ implements CodeModel, Nameable, Modifiable<FieldModifier_>, Annotatable {

    private final Set<FieldModifier_> modifiers;
    private final List<Annotation_> annotations;
    private Type_ type;
    private CharSequence name;
    private Expression_ expression;

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
        this.name = name_;
        return this;
    }

    public Field_ setType(Type_ type) {
        this.type = type;
        return this;
    }

    public Expression_ getExpression() {
        return expression;
    }

    public void setExpression(Expression_ expression_) {
        this.expression = expression_;
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
    public Field_ add(final FieldModifier_ firstClassModifier_m, final FieldModifier_... restClassModifiers) {
        getModifiers().add(firstClassModifier_m);
        Stream.of(restClassModifiers).forEach(getModifiers()::add);
        return this;
    }

    @Override
    public Field_ set(final Set<FieldModifier_> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return this;
    }

    @Override
    public boolean is(FieldModifier_ modifier) {
        return modifiers.contains(modifier);
    }

    @Override
    public Field_ add(final Annotation_ annotation) {
        getAnnotations().add(annotation);
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

    public Field_ public_() {
        add(FieldModifier_.PUBLIC);
        return this;
    }

    public Field_ protected_() {
        add(FieldModifier_.PROTECTED);
        return this;
    }

    public Field_ private_() {
        add(FieldModifier_.PRIVATE);
        return this;
    }

    public Field_ static_() {
        add(FieldModifier_.STATIC);
        return this;
    }

    public Field_ final_() {
        add(FieldModifier_.FINAL);
        return this;
    }

    public Field_ transient_() {
        add(FieldModifier_.TRANSIENT);
        return this;
    }

    public Field_ volatile_() {
        add(FieldModifier_.VOLATILE);
        return this;
    }

        @Override
    public Stream<CodeModel> stream() {
        return StreamUtil.<CodeModel>streamBuilder(annotations, modifiers).build();
    }
}
