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
package com.speedment.internal.codegen.java.views.interfaces;

import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.lang.interfaces.HasJavadoc;
import com.speedment.internal.codegen.lang.interfaces.HasJavadocTags;
import static com.speedment.internal.codegen.util.Formatting.EMPTY;
import java.util.stream.Stream;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasJavadoc}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasJavadocTagsView<M extends HasJavadocTags<M>> {
    
    /**
     * Returns a stream of javadoc lines generated from the tags in the
     * specified model. If the stream is not empty it is prepended by an
     * empty line.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default Stream<String> renderJavadocTags(Generator gen, M model) {
        final Stream<String> stream = gen.onEach(model.getTags());
        
        if (model.getTags().isEmpty()) {
            return stream;
        } else {
            return Stream.concat(
                Stream.of(EMPTY), // to get an empty line before the tags...
                stream
            );
        }
    }
}