package com.speedment.common.codegenxml.internal.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegenxml.DocType;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public final class DocTypeView implements Transform<DocType, String> {

    @Override
    public Optional<String> transform(Generator gen, DocType model) {
        return Optional.of(new StringBuilder()
            .append("<!DOCTYPE ")
            .append(model.getRootType())
            .append('>')
            .toString()
        );
    }
    
}
