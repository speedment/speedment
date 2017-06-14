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
package com.speedment.common.codegen.internal.java.view.value;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.value.InvocationValue;

import java.util.Optional;

import static com.speedment.common.codegen.util.Formatting.indent;
import static com.speedment.common.codegen.util.Formatting.nl;
import static java.util.stream.Collectors.joining;

/**
 * Transforms from an {@link InvocationValue} to java code.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public final class InvocationValueView
implements Transform<InvocationValue, String> {

    @Override
    public Optional<String> transform(Generator gen, InvocationValue model) {
        return Optional.of(
            (model.getType() == null ? "" :
                gen.on(model.getType()).get() + ".") +
            model.getValue() +
            (model.getValues().size() <= 3
                ? gen.onEach(model.getValues()).collect(joining(", ", "(", ")"))
                : ("(" + nl() + indent(gen.onEach(model.getValues())
                    .collect(joining("," + nl()))) + nl() + ")"
                )
            )
        );
    }
}
