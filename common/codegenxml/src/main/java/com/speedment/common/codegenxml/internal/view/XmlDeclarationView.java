package com.speedment.common.codegenxml.internal.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegenxml.internal.view.trait.HasAttributesView;
import java.util.Optional;
import com.speedment.common.codegenxml.XmlDeclaration;

/**
 *
 * @author Per Minborg
 */
public class XmlDeclarationView implements Transform<XmlDeclaration, String>,
        HasAttributesView<XmlDeclaration> {

    @Override
    public Optional<String> transform(Generator gen, XmlDeclaration model) {
        return Optional.of(new StringBuilder()
            .append("<?xml")
            .append(transformAttributes(gen, model))
            .append("?>")
            .toString()
        );
    }
}