package com.speedment.common.codegenxml.internal.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegenxml.internal.view.trait.HasValueView;
import com.speedment.common.codegenxml.ContentElement;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public class ContentElementView implements Transform<ContentElement, String>,
    HasValueView<ContentElement> {

    @Override
    public Optional<String> transform(Generator gen, ContentElement model) {
        if (model.isEscape()) {
            return transformValue(model);
        } else {
            return model.getValue();
        }
    }
}
