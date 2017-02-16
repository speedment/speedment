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
import com.speedment.common.codegen.internal.java.view.trait.HasClassesView;
import com.speedment.common.codegen.internal.java.view.trait.HasFieldsView;
import com.speedment.common.codegen.internal.java.view.trait.HasInitializersView;
import com.speedment.common.codegen.internal.java.view.trait.HasJavadocView;
import com.speedment.common.codegen.internal.java.view.trait.HasMethodsView;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import com.speedment.common.codegen.model.EnumConstant;
import static com.speedment.common.codegen.util.Formatting.block;
import static com.speedment.common.codegen.util.Formatting.nl;
import static com.speedment.common.codegen.util.Formatting.separate;
import java.util.Optional;

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
	public Optional<String> transform(Generator gen, EnumConstant model) {
        requireNonNulls(gen, model);
        
        final String inner;
        if (model.getMethods().isEmpty()
        &&  model.getFields().isEmpty()
        &&  model.getInitializers().isEmpty()) {
            inner = "";
        } else {
            inner = " " + block(separate(
                renderFields(gen, model),
                renderInitalizers(gen, model),
                renderMethods(gen, model),
                renderClasses(gen, model)
            ));
        }
        
		return Optional.of(
            renderJavadoc(gen, model) +
			model.getName() + 
			(model.getValues().isEmpty() ? "" : " ") +
			gen.onEach(model.getValues()).collect(
				joinIfNotEmpty(", ", "\t(", ")")
			) + inner
		);
	}
}