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
package com.speedment.codegen.java.views;

import com.speedment.codegen.lang.models.Field;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.java.views.interfaces.HasAnnotationUsageView;
import com.speedment.codegen.java.views.interfaces.HasJavadocView;
import com.speedment.codegen.java.views.interfaces.HasModifiersView;
import com.speedment.codegen.java.views.interfaces.HasNameView;
import com.speedment.codegen.java.views.interfaces.HasTypeView;
import com.speedment.codegen.java.views.interfaces.HasValueView;
import java.util.Optional;

/**
 * Transforms from a {@link Field} to java code.
 * 
 * @author Emil Forslund
 */
public class FieldView implements Transform<Field, String>, HasNameView<Field>, 
    HasJavadocView<Field>, HasModifiersView<Field>, HasTypeView<Field>,
    HasValueView<Field>, HasAnnotationUsageView<Field> {

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Field model) {
		return Optional.of(
			renderJavadoc(gen, model) +
            renderAnnotations(gen, model) +
			renderModifiers(gen, model) +
			renderType(gen, model) +
			renderName(gen, model) +
			renderValue(gen, model)
		);
	}
	
}