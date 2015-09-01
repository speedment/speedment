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

import com.speedment.internal.codegen.lang.models.Javadoc;
import java.util.Optional;
import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.java.views.interfaces.HasJavadocTagsView;
import static com.speedment.internal.core.stream.CollectorUtil.joinIfNotEmpty;
import com.speedment.internal.util.TextUtil;
import static java.util.Objects.requireNonNull;

/**
 * Transforms from a {@link Javadoc} to java code.
 * 
 * @author Emil Forslund
 */
public final class JavadocView implements Transform<Javadoc, String>, 
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
        requireNonNull(gen);
        requireNonNull(model);
        
        final String formattedText = TextUtil.formatJavaDocBox(model.getText()) + 
            renderJavadocTags(gen, model)
                .map(TextUtil::formatJavaDocBox)
                .collect(joinIfNotEmpty(nl(), nl(), EMPTY));
        
		return Optional.of(
            JAVADOC_PREFIX + 
            formattedText.replace(nl(), JAVADOC_DELIMITER) + 
            JAVADOC_SUFFIX
        );
	}
}