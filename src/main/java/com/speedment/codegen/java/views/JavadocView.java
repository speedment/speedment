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

import com.speedment.codegen.util.CodeCombiner;
import com.speedment.codegen.lang.models.Javadoc;
import java.util.Optional;
import static com.speedment.codegen.util.Formatting.*;
import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.codegen.java.views.interfaces.HasJavadocTagsView;
import java.util.stream.Stream;

/**
 * Transforms from a {@link Javadoc} to java code.
 * 
 * @author Emil Forslund
 */
public class JavadocView implements Transform<Javadoc, String>, 
    HasJavadocTagsView<Javadoc> {
    
	private final static String
		JAVADOC_DELIMITER = nl() + SPACE + STAR + SPACE,
		JAVADOC_PREFIX = SLASH + STAR + STAR + nl() + SPACE + STAR + SPACE,
		JAVADOC_SUFFIX = nl() + SPACE + STAR + SLASH;
	
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Javadoc model) {
		return CodeCombiner.ifEmpty(
            Stream.concat(
                model.getRows().stream(),
                renderJavadocTags(gen, model)
			).collect(
				CodeCombiner.joinIfNotEmpty(
					JAVADOC_DELIMITER, 
					JAVADOC_PREFIX, 
					JAVADOC_SUFFIX
				)
			)
		);
	}
}