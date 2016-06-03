package com.speedment.common.codegenxml.internal.view.trait;

import com.speedment.common.codegenxml.trait.HasName;

/**
 *
 * @author Per Minborg
 * @param <T>  the type of the main view
 */
public interface HasNameView<T extends HasName<T>> {

    default String transformName(T model) {
        return model.getName()
            .replaceAll("&", "")
            .replaceAll(">", "")
            .replaceAll("<", "")
            .replaceAll("\"", "")
            .replaceAll("'", "");
    }

}
