package com.speedment.code;

import com.speedment.Speedment;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.File;

/**
 *
 * @author Per Minborg
 * @param <T> document type
 */
@FunctionalInterface
public interface TranslatorConstructor<T extends HasMainInterface> {

    Translator<T, File> apply(Speedment speedment, Generator cg, T document);

}
