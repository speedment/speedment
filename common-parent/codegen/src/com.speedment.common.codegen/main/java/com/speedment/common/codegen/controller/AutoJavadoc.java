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
package com.speedment.common.codegen.controller;

import static com.speedment.common.codegen.constant.DefaultJavadocTag.*;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.JavadocTag;
import com.speedment.common.codegen.model.Method;
import com.speedment.common.codegen.model.trait.*;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;

/**
 * This control generates javadoc stubs for all models descending from the
 * one applied to.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public final class AutoJavadoc<T extends HasJavadoc<?>> implements Consumer<T> {
    
	private static final String 
			DEFAULT_TEXT = "Write some documentation here.",
			DEFAULT_NAME = "Your Name";
 
    /**
     * Parses the specified model recursively to find all models that implements
     * the {@link HasJavadoc} trait but that does not have proper documentation
     * and generates javadoc stubs for those models.
     * 
     * @param model  the model to parse
     */
    @Override
    public void accept(T model) {
		createJavadoc(requireNonNull(model));
    }
	
    /**
     * Checks if the specified model already has documentation and if not,
     * generates it. This method will recurse through the model tree to make
     * sure all children also have documentation.
     * <p>
     * If documentation exists but is incomplete, it will be completed without
     * changing the existing content.
     * 
     * @param <T>    the type of the model to operate on
     * @param model  the model to add documentation to 
     */
    @SuppressWarnings("unchecked")
	private static <T extends HasJavadoc<?>> void createJavadoc(T model) {
        
		final Javadoc doc = requireNonNull(model).getJavadoc().orElse(Javadoc.of(DEFAULT_TEXT));
		model.set(doc);

		if (model instanceof HasGenerics) {
            // Add @param for each type variable.
			((HasGenerics<?>) model).getGenerics().forEach(g -> 
				g.getLowerBound().ifPresent(t -> addTag(doc, 
					PARAM.setValue("<" + t + ">")
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

        if ((model instanceof Method) && (((Method) model).getType() != void.class)) {
            // Add @return to methods.
            addTag(doc, RETURN);
        }
		
		
		if (model instanceof HasConstructors) {
            // Generate javadoc for each constructor.
			((HasConstructors<?>) model).getConstructors()
				.forEach(AutoJavadoc::createJavadoc);
		}

		if (model instanceof HasMethods) {
            // Generate javadoc for each method.
			((HasMethods<?>) model).getMethods()
				.forEach(AutoJavadoc::createJavadoc);
		}
        
        if (model instanceof HasClasses) {
            // Generate javadoc for each subclass.
            ((HasClasses<?>) model).getClasses()
                .forEach(AutoJavadoc::createJavadoc);
        }
	}
	
    /**
     * Add a javadoc tag to the specified documentation block. If the tag is
     * already defined, this will have no effect.
     * 
     * @param doc  the documentation block
     * @param tag  the tag to add
     */
	private static void addTag(Javadoc doc, JavadocTag tag) {
		if (!hasTagAlready(
            requireNonNull(doc), 
            requireNonNull(tag)
        )) {
			doc.add(tag);
		}
	}
	
    /**
     * Checks if the specified tag is already defined in the supplied
     * documentation block. 
     * 
     * @param doc  the documentation block
     * @param tag  the tag to check
     * @return     {@code true} if it exists, else {@code false}
     */
	private static boolean hasTagAlready(Javadoc doc, JavadocTag tag) {
        requireNonNull(doc);
        requireNonNull(tag);
        
		return doc.getTags().stream().anyMatch(t -> 
			tag.getName().equals(t.getName())
			&& tag.getValue().equals(t.getValue())
		);
	}
}
