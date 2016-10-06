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
import com.speedment.common.codegen.internal.java.view.trait.HasAnnotationUsageView;
import com.speedment.common.codegen.internal.java.view.trait.HasJavadocView;
import com.speedment.common.codegen.internal.java.view.trait.HasNameView;
import com.speedment.common.codegen.model.Annotation;

import java.util.Optional;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.stream.Collectors.joining;

/**
 * Transforms from an {@link Annotation} to java code.
 *
 * @author Emil Forslund
 */
public final class AnnotationView implements Transform<Annotation, String>,
        HasJavadocView<Annotation>,
        HasAnnotationUsageView<Annotation>,
        HasNameView<Annotation> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> transform(Generator gen, Annotation model) {
        requireNonNulls(gen, model);

        return Optional.of(
            renderAnnotations(gen, model)
            + renderAnnotations(gen, model)
            + "@interface "
            + renderName(gen, model)
            + block(
                model.getFields().stream().map(f
                    -> // Field javadoc (optional)
                    renderJavadoc(gen, model)
                    + // Field declaration
                    gen.on(f.getType()) + " " + f.getName() + "()"
                    + // Default value (optional)
                    ifelse(gen.on(f.getValue()), v -> " default " + v, "")
                    + ";"
                ).collect(joining(dnl()))
            )
        );
    }
}
