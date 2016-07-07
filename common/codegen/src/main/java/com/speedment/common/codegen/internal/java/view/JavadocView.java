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
import com.speedment.common.codegen.internal.java.view.trait.HasJavadocTagsView;
import com.speedment.common.codegen.internal.util.TextUtil;
import com.speedment.common.codegen.model.Javadoc;

import java.util.Optional;

import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.Formatting.nl;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;

/**
 * Transforms from a {@link Javadoc} to java code.
 * 
 * @author Emil Forslund
 */
public final class JavadocView implements Transform<Javadoc, String>, 
    HasJavadocTagsView<Javadoc> {
    
	private final static String
		JAVADOC_DELIMITER = nl() + " * ",
		JAVADOC_PREFIX    = "/**" + nl() + " * ",
		JAVADOC_SUFFIX    = nl() + " */";
	
    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<String> transform(Generator gen, Javadoc model) {
        requireNonNulls(gen, model);
        
        final String formattedText = TextUtil.formatJavaDocBox(model.getText()) + 
            renderJavadocTags(gen, model)
                .map(TextUtil::formatJavaDocBox)
                .collect(joinIfNotEmpty(nl(), nl(), ""));
        
		return Optional.of(
            JAVADOC_PREFIX + 
            formattedText.replace(nl(), JAVADOC_DELIMITER) + 
            JAVADOC_SUFFIX
        );
	}
}