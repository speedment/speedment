/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import com.speedment.common.codegen.model.Field;

import java.util.Optional;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;

/**
 * Transforms from a {@link Field} to java code.
 * 
 * @author Emil Forslund
 */
public final class FieldView implements Transform<Field, String>, HasNameView<Field>, 
    HasJavadocView<Field>, HasModifiersView<Field>, HasTypeView<Field>,
    HasValueView<Field>, HasAnnotationUsageView<Field> {

	@Override
	public Optional<String> transform(Generator gen, Field model) {
        requireNonNulls(gen, model);
        
		return Optional.of(
			renderJavadoc(gen, model) +
            renderModifiers(gen, model) +
            renderAnnotations(gen, model) +
			renderType(gen, model) +
			renderName(gen, model) +
			renderValue(gen, model)
		);
	}

    @Override
    public String annotationSeparator() {
        return " ";
    }
}