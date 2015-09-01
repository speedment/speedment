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

import com.speedment.internal.codegen.lang.models.AnnotationUsage;
import java.util.Optional;
import static com.speedment.internal.codegen.util.Formatting.*;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import static com.speedment.internal.core.stream.CollectorUtil.joinIfNotEmpty;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 * Transforms from an {@link AnnotationUsage} to java code.
 *
 * @author Emil Forslund
 */
public final class AnnotationUsageView implements Transform<AnnotationUsage, String> {

    private final static String PSTART = "(",
            EQUALS = " = ";

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> transform(Generator gen, AnnotationUsage model) {
        requireNonNull(gen);
        requireNonNull(model);

        final Optional<String> value = gen.on(model.getValue());
        final Stream<String> valueStream = value.isPresent() ? Stream.of(value.get()) : Stream.empty();

        return Optional.of(
                AT + gen.on(model.getType()).get()
                + Stream.of(
                        model.getValues().stream().map(e -> e.getKey() + gen.on(e.getValue()).map(s -> EQUALS + s).orElse(EMPTY)),
                        valueStream
                ).flatMap(s -> s).collect(
                joinIfNotEmpty(
                        cnl() + tab() + tab(),
                        PSTART,
                        PE
                )
        )
        );
    }
}
