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
package com.speedment.codegen.model;

import com.speedment.annotation.Api;
import com.speedment.codegen.model.modifier.AnnotationModifier;
import com.speedment.codegen.model.trait.HasAnnotationUsage;
import com.speedment.codegen.model.trait.HasCopy;
import com.speedment.codegen.model.trait.HasFields;
import com.speedment.codegen.model.trait.HasImports;
import com.speedment.codegen.model.trait.HasJavadoc;
import com.speedment.codegen.model.trait.HasName;
import com.speedment.internal.codegen.model.AnnotationImpl;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 * A model that represents an annotation in code.
 *
 * @author Emil Forslund
 * @see AnnotationUsage
 * @since   2.0
 */
@Api(version = "2.3")
public interface Annotation extends HasCopy<Annotation>, HasName<Annotation>,
    HasJavadoc<Annotation>, HasFields<Annotation>, HasImports<Annotation>,
    AnnotationModifier<Annotation>, HasAnnotationUsage<Annotation> {

    /**
     * Factory holder.
     */
    enum Factory {
        INST;
        private Function<String, Annotation> mapper = AnnotationImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Function) setSupplier} method.
     *
     * @param name the name
     * @return the new instance
     */
    static Annotation of(String name) {
        return Factory.INST.mapper.apply(name);
    }

    /**
     * Sets the instantiation factory method used to create new instances of
     * this interface.
     *
     * @param mapper the new constructor
     */
    static void setSupplier(Function<String, Annotation> mapper) {
        Factory.INST.mapper = requireNonNull(mapper);
    }
}
