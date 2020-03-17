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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.AnnotationUsage;

import java.lang.reflect.Type;
import java.util.List;

import static com.speedment.common.codegen.model.Value.ofText;

/**
 * A trait for models that contains {@link AnnotationUsage} components.
 * 
 * @author Emil Forslund
 * @param <T> The extending type
 */
public interface HasAnnotationUsage<T extends HasAnnotationUsage<T>> {
    
    /**
     * Adds the specified {@link AnnotationUsage} to this model.
     * 
     * @param annotation  the new child
     * @return            a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final AnnotationUsage annotation) {
        getAnnotations().add(annotation);
        return (T) this;
    }

    /**
     * Adds the specified {@link AnnotationUsage} to this model. This is a
     * synonym for {@link #add(AnnotationUsage)}.
     *
     * @param annotation  the new annotation usage
     * @return            a reference to this
     *
     * @since 2.5
     */
    default T annotate(final AnnotationUsage annotation) {
        return add(annotation);
    }

    /**
     * Creates an {@link AnnotationUsage} with the specified {@code Type} and
     * adds it to this model.
     *
     * @param annotation  the new annotation usage
     * @return            a reference to this
     *
     * @since 2.5
     */
    default T annotate(final Type annotation) {
        return annotate(AnnotationUsage.of(annotation));
    }

    /**
     * Creates an {@link AnnotationUsage} with the specified {@code Type} and
     * adds it to this model. This method will also set the {@code value()}
     * of the annotation to {@code textValue}.
     *
     * @param annotation  the new annotation usage
     * @param textValue   the value of the text for this annotation
     * @return            a reference to this
     *
     * @since 2.5
     */
    default T annotate(final Type annotation, String textValue) {
        return annotate(AnnotationUsage.of(annotation).set(ofText(textValue)));
    }
    
    /**
     * Returns a list of all the annotation usages in this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the annotations. 
     */
	List<AnnotationUsage> getAnnotations();
}