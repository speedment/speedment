package com.speedment.common.codegenxml.internal.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegenxml.internal.view.trait.HasAttributesView;
import com.speedment.common.codegenxml.internal.view.trait.HasElementsView;
import com.speedment.common.codegenxml.internal.view.trait.HasNameView;
import java.util.Optional;
import com.speedment.common.codegenxml.TagElement;

/**
 *
 * @author Per Minborg
 */
public class TagElementView implements Transform<TagElement, String>,
        HasNameView<TagElement>, 
        HasElementsView<TagElement>, 
        HasAttributesView<TagElement> {

    @Override
    public Optional<String> transform(Generator gen, TagElement model) {
        return Optional.of(new StringBuilder()
            .append('<')
            .append(transformName(model))
            .append(transformAttributes(gen, model))
            .append(model.elements().isEmpty() ? '/' : "")
            .append('>')
            .append(transformElements(gen, model))
            .append(model.elements().isEmpty() ? "" : new StringBuilder()
                .append("</")
                .append(transformName(model))
                .append('>')
            )
            .toString()
        );
    }
}