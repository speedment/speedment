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
package com.speedment.internal.codegen.java.views;

import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.lang.models.JavadocTag;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * Transforms from a {@link JavadocTag} to java code.
 * 
 * @author Emil Forslund
 */
public final class JavadocTagView implements Transform<JavadocTag, String> {

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, JavadocTag model) {
        requireNonNull(gen);
        requireNonNull(model);
        
		return Optional.of(
			AT + model.getName() + 
			ifelse(model.getValue(), s -> SPACE + s, EMPTY) + SPACE +
			model.getText().orElse(EMPTY)
		);
	}
}