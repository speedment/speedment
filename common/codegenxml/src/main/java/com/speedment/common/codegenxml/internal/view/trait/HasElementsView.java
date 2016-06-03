package com.speedment.common.codegenxml.internal.view.trait;

import com.speedment.common.codegen.Generator;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.Formatting.indent;
import static com.speedment.common.codegen.internal.util.Formatting.nl;
import com.speedment.common.codegenxml.ContentElement;
import com.speedment.common.codegenxml.Element;
import com.speedment.common.codegenxml.trait.HasElements;
import java.util.List;

/**
 *
 * @author Per Minborg
 * @param <T>  the type of the main view
 */
public interface HasElementsView<T extends HasElements<T>> {

    default boolean useIndent() {
        return true;
    }

    default String transformElements(Generator gen, T model) {
        final List<Element> elements = model.elements();
        if (elements.size() == 1 && (elements.get(0) instanceof ContentElement)) {
            return gen.on(elements.get(0)).orElse("");
        } else {
            return gen.onEach(elements)
                .map(s -> useIndent() ? indent(s) : s)
                .collect(joinIfNotEmpty(nl(), nl(), nl()));
        }
    }

}
