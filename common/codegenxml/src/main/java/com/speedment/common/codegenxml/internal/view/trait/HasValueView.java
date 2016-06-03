package com.speedment.common.codegenxml.internal.view.trait;

import com.speedment.common.codegenxml.trait.HasValue;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 * @param <T>  the type of the main view
 */
public interface HasValueView<T extends HasValue<T>> {

    default Optional<String> transformValue(T model) {
        return model.getValue().map(s -> s
            .replaceAll("&", "&amp;")
            .replaceAll(">", "&gt;")
            .replaceAll("<", "&lt;")
            .replaceAll("\"", "&quot;"));
    }

}