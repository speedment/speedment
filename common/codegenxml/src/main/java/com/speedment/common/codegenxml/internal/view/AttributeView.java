package com.speedment.common.codegenxml.internal.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegenxml.internal.view.trait.HasNameView;
import com.speedment.common.codegenxml.internal.view.trait.HasValueView;
import com.speedment.common.codegenxml.Attribute;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public class AttributeView implements Transform<Attribute, String>, 
        HasNameView<Attribute>, HasValueView<Attribute> {

    @Override
    public Optional<String> transform(Generator gen, Attribute model) {
        return Optional.of(
            new StringBuilder(transformName(model))
                .append(transformValue(model)
                    .map(s -> "=\"" + s + "\"")
                    .orElse("")
                )
                .toString()
        );
    }
}