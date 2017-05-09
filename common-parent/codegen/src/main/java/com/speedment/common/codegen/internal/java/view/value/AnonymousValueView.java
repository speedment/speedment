package com.speedment.common.codegen.internal.java.view.value;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.internal.java.view.trait.HasClassesView;
import com.speedment.common.codegen.internal.java.view.trait.HasFieldsView;
import com.speedment.common.codegen.internal.java.view.trait.HasInitializersView;
import com.speedment.common.codegen.internal.java.view.trait.HasMethodsView;
import com.speedment.common.codegen.model.value.AnonymousValue;

import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNullElements;
import static com.speedment.common.codegen.util.Formatting.*;
import static java.util.stream.Collectors.joining;

/**
 * Transforms from an {@link AnonymousValue} to java code.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public final class AnonymousValueView
implements Transform<AnonymousValue, String>,
           HasFieldsView<AnonymousValue>,
           HasMethodsView<AnonymousValue>,
           HasInitializersView<AnonymousValue>,
           HasClassesView<AnonymousValue> {

    @Override
    public String fieldSeparator(AnonymousValue model) {
        return nl();
    }

    @Override
    public String fieldSuffix() {
        return ";";
    }

    @Override
    public Optional<String> transform(Generator gen, AnonymousValue model) {
        return Optional.of("new " + gen.on(model.getValue()).get() +
            gen.onEach(model.getTypeParameters()).collect(joinIfNotEmpty(", ", "<", ">")) +
            gen.onEach(model.getValues()).collect(joining(", ", "(", ")")) + " " + block(
                separate(
                    renderFields(gen, model),
                    renderInitalizers(gen, model),
                    renderMethods(gen, model),
                    renderClasses(gen, model)
                )
            )
        );
    }

    /**
     * Converts the specified elements into strings using their
     * {@code toString}-method and combines them with two new-line-characters.
     * Empty strings will be discarded.
     *
     * @param strings  the strings to combine
     * @return         the combined string
     */
    private String separate(Object... strings) {
        requireNonNullElements(strings);

        return Stream.of(strings)
            .map(Object::toString)
            .filter(s -> s.length() > 0)
            .collect(joining(dnl()));
    }
}
