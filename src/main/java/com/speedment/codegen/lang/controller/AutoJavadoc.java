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
package com.speedment.codegen.lang.controller;

import static com.speedment.codegen.Formatting.*;
import com.speedment.codegen.lang.interfaces.HasClasses;
import com.speedment.codegen.lang.interfaces.HasConstructors;
import com.speedment.codegen.lang.interfaces.HasJavadoc;
import com.speedment.codegen.lang.interfaces.HasFields;
import com.speedment.codegen.lang.interfaces.HasGenerics;
import com.speedment.codegen.lang.interfaces.HasMethods;
import com.speedment.codegen.lang.models.ClassOrInterface;
import com.speedment.codegen.lang.models.Javadoc;
import com.speedment.codegen.lang.models.JavadocTag;
import com.speedment.codegen.lang.models.Method;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.AUTHOR;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.PARAM;
import static com.speedment.codegen.lang.models.constants.DefaultJavadocTag.RETURN;
import java.util.function.Consumer;

/**
 *
 * @author Emil Forslund
 * @param <T>
 */
public class AutoJavadoc<T extends HasJavadoc<?>> implements Consumer<T> {
	private final static String 
			DEFAULT_TEXT = "Write some documentation here.",
			DEFAULT_NAME = "Your Name";
 
    @Override
    public void accept(T model) {
		createJavadoc(model);
    }
	
	private static <T extends HasJavadoc<?>> T createJavadoc(T model) {
		final Javadoc doc = model.getJavadoc().orElse(Javadoc.of(DEFAULT_TEXT));
		model.set(doc);

		if (model instanceof HasGenerics) {
            // Add @param for each type variable.
			((HasGenerics<?>) model).getGenerics().forEach(g -> 
				g.getLowerBound().ifPresent(t -> addTag(doc, 
					PARAM.setValue(SS + t + SE)
				))
			);
		}

		if (model instanceof ClassOrInterface) {
			// Add @author
			doc.add(AUTHOR.setValue(DEFAULT_NAME));
		} else {
			// Add @param for each parameter.
			if (model instanceof HasFields) {
				((HasFields<?>) model).getFields().forEach(f -> 
					addTag(doc, PARAM.setValue(f.getName()))
				);
			}
		}

		if (model instanceof Method) {
            if (!"void".equals(((Method) model).getType().getName())) {
                // Add @return to methods.
                addTag(doc, RETURN);
            }
		}
		
		if (model instanceof HasConstructors) {
            // Generate javadoc for each constructor.
			((HasConstructors<?>) model).getConstructors()
				.forEach(m -> createJavadoc(m));
		}

		if (model instanceof HasMethods) {
            // Generate javadoc for each method.
			((HasMethods<?>) model).getMethods()
				.forEach(m -> createJavadoc(m));
		}
        
        if (model instanceof HasClasses) {
            // Generate javadoc for each subclass.
            ((HasClasses<?>) model).getClasses()
                .forEach(m -> createJavadoc(m));
        }
		
		return model;
	}
	
	private static void addTag(Javadoc doc, JavadocTag tag) {
		if (!hasTagAlready(doc, tag)) {
			doc.add(tag);
		}
	}
	
	private static boolean hasTagAlready(Javadoc doc, JavadocTag tag) {
		return doc.getTags().stream().anyMatch(t -> 
			tag.getName().equals(t.getName())
			&& tag.getValue().equals(t.getValue())
		);
	}
}
