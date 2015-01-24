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

import com.speedment.codegen.model.Block_;
import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.Expression_;
import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.Type_;
import com.speedment.codegen.model.annotation.Annotatable;
import com.speedment.codegen.model.annotation.Annotation_;
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
public class Method_ implements CodeModel, Modifiable<MethodModifier_>, Annotatable {

    private final Set<MethodModifier_> modifiers;
	private final List<Annotation_> annotations;
    private Type_ type_;
    private CharSequence name_;
    private Expression_ expression_;
    private List<Field_> parameters; // Todo: Introduce parameter
    private Block_ block;

    public Method_(Type_ type_, CharSequence name_) {
        this.parameters = new ArrayList<>();
		this.annotations = new ArrayList<>();
        this.type_ = type_;
        this.name_ = name_;
        this.modifiers = EnumSet.noneOf(MethodModifier_.class);
		
    }

    @SuppressWarnings("unchecked")
    @Override
    public Method_ add(final MethodModifier_ firstClassModifier_m, final MethodModifier_... restClassModifiers) {
        getModifiers().add(firstClassModifier_m);
        Stream.of(restClassModifiers).forEach(getModifiers()::add);
        return (Method_) this;
    }

    public Method_ add(Field_ field_) {
        getParameters().add(field_);
        return this;
    }

    public Type_ getType_() {
        return type_;
    }

    public Method_ setType_(Type_ type_) {
        this.type_ = type_;
		return this;
    }

    public CharSequence getName_() {
        return name_;
    }

    public Method_ setName_(CharSequence name_) {
        this.name_ = name_;
		return this;
    }

    public Expression_ getExpression_() {
        return expression_;
    }

    public Method_ setExpression_(Expression_ expression_) {
        this.expression_ = expression_;
		return this;
    }

    public List<Field_> getParameters() {
        return parameters;
    }

    public Method_ setParameters(List<Field_> parameters) {
        this.parameters = parameters;
		return this;
    }

    public Method_ setBlock_(Block_ block) {
        this.block = block;
		return this;
    }

    public Block_ getBlock_() {
		return block;
    }

    @Override
    public Type getType() {
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
}
