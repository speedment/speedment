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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.internal.java.view.trait.HasCodeView;
import com.speedment.common.codegen.internal.java.view.trait.HasModifiersView;
import com.speedment.common.codegen.model.Initializer;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Transforms from an {@link Initializer} to java code.
 * 
 * @author Emil Forslund
 */
public final class InitalizerView implements Transform<Initializer, String>,
        HasModifiersView<Initializer>,
        HasCodeView<Initializer> {

    @Override
    public Optional<String> transform(Generator gen, Initializer model) {
        requireNonNull(gen);
        requireNonNull(model);
        
        return Optional.of(
            renderModifiers(gen, model) +
            renderCode(gen, model)
        );
    }
}