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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.internal.java.view.trait.HasAnnotationUsageView;
import com.speedment.common.codegen.internal.java.view.trait.HasCodeView;
import com.speedment.common.codegen.internal.java.view.trait.HasFieldsView;
import com.speedment.common.codegen.internal.java.view.trait.HasGenericsView;
import com.speedment.common.codegen.internal.java.view.trait.HasJavadocView;
import com.speedment.common.codegen.internal.java.view.trait.HasModifiersView;
import com.speedment.common.codegen.internal.java.view.trait.HasNameView;
import com.speedment.common.codegen.internal.java.view.trait.HasThrowsView;
import com.speedment.common.codegen.internal.java.view.trait.HasTypeView;
import com.speedment.common.codegen.model.InterfaceMethod;
import static com.speedment.common.codegen.model.modifier.Modifier.*;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * Transforms from an {@link InterfaceMethod} to java code.
 * 
 * @author Emil Forslund
 */
public final class InterfaceMethodView implements Transform<InterfaceMethod, String>,
        HasModifiersView<InterfaceMethod>,
        HasNameView<InterfaceMethod>,
        HasTypeView<InterfaceMethod>,
        HasThrowsView<InterfaceMethod>,
        HasGenericsView<InterfaceMethod>,
        HasFieldsView<InterfaceMethod>,
        HasJavadocView<InterfaceMethod>, 
        HasAnnotationUsageView<InterfaceMethod>,
        HasCodeView<InterfaceMethod> {
    
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, InterfaceMethod model) {
        requireNonNull(gen);
        requireNonNull(model);
        
        final Optional<String> body = Optional.of(renderCode(gen, model))
            .filter(s -> model.getModifiers().contains(DEFAULT)
                      || model.getModifiers().contains(STATIC)
            );
        
        return Optional.of(
            renderJavadoc(gen, model) +
            renderAnnotations(gen, model) +
			renderModifiers(gen, model, STATIC, DEFAULT) +
            renderGenerics(gen, model) +
            renderType(gen, model) +
            renderName(gen, model) + "(" +
            renderFields(gen, model) + ")" +
                (body.isPresent() ? " " : "") +
            renderThrows(gen, model) + 
            body.orElse(";")
        );
	}

    @Override
    public String fieldSeparator() {
        return ", ";
    }
}