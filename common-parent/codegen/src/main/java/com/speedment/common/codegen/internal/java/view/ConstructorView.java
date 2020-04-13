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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.internal.java.view.trait.*;
import com.speedment.common.codegen.model.Constructor;
import com.speedment.common.codegen.model.trait.HasName;
import com.speedment.common.codegen.util.Formatting;

import java.util.Optional;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.model.modifier.Modifier.*;

/**
 * Transforms from a {@link Constructor} to java code.
 * 
 * @author Emil Forslund
 */
public final class ConstructorView
implements Transform<Constructor, String>,
           HasThrowsView<Constructor>,
           HasGenericsView<Constructor>,
           HasFieldsView<Constructor>,
           HasJavadocView<Constructor>,
           HasAnnotationUsageView<Constructor>,
           HasCodeView<Constructor>,
           HasModifiersView<Constructor> {

    @Override
    public String fieldSeparator(Constructor model) {
        if (model.getFields().size() >= 3
        ||  model.getFields().stream()
            .anyMatch(f -> !f.getAnnotations().isEmpty())) {
            return "," + Formatting.nl() + Formatting.tab() + Formatting.tab();
        } else return ", ";
    }

    @Override
    public String throwsSuffix(Constructor model) {
        return " ";
    }

    @Override
	public Optional<String> transform(Generator gen, Constructor model) {
        requireNonNulls(gen, model);

        return Optional.of(
            renderJavadoc(gen, model) +
            renderAnnotations(gen, model) +
            renderModifiers(gen, model, PUBLIC, PRIVATE, PROTECTED) +
            renderGenerics(gen, model) +
            renderName(gen, model)
                .orElseThrow(() -> new UnsupportedOperationException(
                    "Could not find a nameable parent of constructor."
                )) +
            ((splitFields(model)) ? "(" + Formatting.nl() + Formatting.tab() + Formatting.tab() : "(") +
            renderFields(gen, model) + ") " +
            renderThrows(gen, model) +
            renderCode(gen, model)
        );
	}
	
    /**
     * Renders the name of this constructor. In java, this is the name of the
     * class or enum that the constructor is in.
     * 
     * @param gen    the generator
     * @param model  the constructor
     * @return       the rendered name
     */
	private static <T extends HasName<T>> Optional<String> renderName(Generator gen, Constructor model) {
        requireNonNulls(gen, model);

        return gen.getRenderStack()
            .fromTop(HasName.class)
            .map(HasName<T>::getName)
            .map(Formatting::shortName)
            .findFirst();
	}

    private boolean splitFields(Constructor model) {
        return model.getFields().size() >= 3 || model.getFields().stream()
            .anyMatch(f -> !f.getAnnotations().isEmpty());
    }
}