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
import com.speedment.common.codegen.model.EnumConstant;

import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNullElements;
import static com.speedment.common.codegen.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.stream.Collectors.joining;

/**
 * Transforms from an {@link EnumConstant} to java code.
 * 
 * @author Emil Forslund
 */
public final class EnumConstantView 
implements Transform<EnumConstant, String>,
        HasJavadocView<EnumConstant>,
        HasClassesView<EnumConstant>,
        HasInitializersView<EnumConstant>,
        HasAnnotationUsageView<EnumConstant>,
        HasMethodsView<EnumConstant>,
        HasFieldsView<EnumConstant> {

    @Override
    public String fieldSeparator(EnumConstant model) {
        return nl();
    }

    @Override
    public String fieldSuffix() {
        return ";";
    }

    @Override
    public String annotationSeparator() {
        return " ";
    }

	@Override
	public Optional<String> transform(Generator gen, EnumConstant model) {
        requireNonNulls(gen, model);
        
        final String inner;

        if (model.getFields().isEmpty()
                && model.getInitializers().isEmpty()
                && model.getClasses().isEmpty()
                && model.getMethods().isEmpty()
        ) {
            inner = "";
        } else {
            inner = " " + block(separate(
                    renderFields(gen, model),
                    renderInitalizers(gen, model),
                    renderClasses(gen, model),
                    renderMethods(gen, model)
                    ));
        }

		return Optional.of(
            renderJavadoc(gen, model) +
            renderAnnotations(gen, model) +
			model.getName() + 
			(model.getValues().isEmpty() ? "" : " ") +
			gen.onEach(model.getValues()).collect(
				joinIfNotEmpty(", ", "\t(", ")")
			) + inner
		);
	}


    private String separate(Object... strings) {
        requireNonNullElements(strings);

        return Stream.of(strings)
                .map(Object::toString)
                .filter(s -> s.length() > 0)
                .collect(joining(dnl()));
    }

}
