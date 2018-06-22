package com.speedment.generator.translator.component;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.generator.translator.TranslatorDecorator;

import java.util.function.Consumer;

/**
 * Modifier that decorates a {@link CodeGenerationComponent}.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface DecoratorBuilder<T extends ClassOrInterface<T>> {

    /**
     * Creates and appends a {@link TranslatorDecorator} to the
     * {@link CodeGenerationComponent} that calls the specified action before
     * the regular generation is complete.
     *
     * @param action  the action to perform
     * @return        the {@code CodeGenerationComponent}
     */
    CodeGenerationComponent preGenerate(Consumer<T> action);

    /**
     * Creates and appends a {@link TranslatorDecorator} to the
     * {@link CodeGenerationComponent} that calls the specified action after the
     * regular generation is complete.
     *
     * @param action  the action to perform
     * @return        the {@code CodeGenerationComponent}
     */
    CodeGenerationComponent postGenerate(Consumer<T> action);

}
