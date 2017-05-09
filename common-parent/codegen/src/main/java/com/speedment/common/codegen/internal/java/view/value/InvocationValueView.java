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
