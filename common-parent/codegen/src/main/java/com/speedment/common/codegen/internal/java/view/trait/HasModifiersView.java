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
package com.speedment.common.codegen.internal.java.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.modifier.Modifier;
import com.speedment.common.codegen.model.trait.HasModifiers;

import java.util.Set;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static java.util.stream.Collectors.toSet;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasModifiers}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasModifiersView<M extends HasModifiers<M>> extends 
    Transform<M, String> {
    
    /**
     * Render the modifiers-part of the model with an extra space appended
     * afterwards. If no modifiers exists, an empty string is returned. If an
     * non-empty array of allowed modifiers is provided, only those can
     * be visible in the result.
     * 
     * @param gen      the generator
     * @param model    the model
     * @param allowed  set of modifiers that can be visible
     * @return         the generated code
     */
    default String renderModifiers(Generator gen, M model, Modifier... allowed) {
        final Set<Modifier> modifiers;
        
        if (allowed.length == 0) {
            modifiers = model.getModifiers();
        } else {
            modifiers = Stream.of(allowed).collect(toSet());
            modifiers.retainAll(model.getModifiers());
        }
        
        return gen.onEach(modifiers).collect(joinIfNotEmpty(" ", "", " "));
    }
}