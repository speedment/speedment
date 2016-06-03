package com.speedment.common.codegenxml.internal.view;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import static com.speedment.common.codegen.internal.util.Formatting.nl;
import com.speedment.common.codegenxml.internal.view.trait.HasElementsView;
import com.speedment.common.codegenxml.Document;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class DocumentView implements Transform<Document, String>,
    HasElementsView<Document> {

    @Override
    public boolean useIndent() {
        return false;
    }

    @Override
    public Optional<String> transform(Generator gen, Document model) {
        return Optional.of(
            Stream.of(
                gen.onEach(model.preamble()).map(s -> useIndent() ? indent(s) : s).collect(joinIfNotEmpty(nl(), "", "")),
                model.getXmlDeclaration().flatMap(gen::on).orElse(""),
                model.getDocType().flatMap(gen::on).orElse(""),
                transformElements(gen, model)
            ).filter(s -> !s.isEmpty())
            .collect(joinIfNotEmpty(nl(), "", ""))
        );
    }
}
