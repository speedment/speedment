package com.speedment.common.codegenxml.internal.view.trait;

import com.speedment.common.codegen.Generator;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import com.speedment.common.codegenxml.trait.HasAttributes;

/**
 *
 * @author Per Minborg
 * @param <T>  the type of the main view
 */
public interface HasAttributesView<T extends HasAttributes<T>> {
    
    default String transformAttributes(Generator gen, T model) {
        return gen.onEach(model.attributes()).collect(joinIfNotEmpty(" ", " ", ""));
    }
    
}
