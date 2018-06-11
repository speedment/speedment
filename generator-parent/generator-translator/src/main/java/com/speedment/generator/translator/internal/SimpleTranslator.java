package com.speedment.generator.translator.internal;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.JavaClassTranslator;
import com.speedment.generator.translator.Translator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * A very basic implementation of {@link Translator} that delegates most of the
 * work to a single {@link Function}.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public final class SimpleTranslator
    <DOC extends Document & HasName & HasMainInterface,
        T extends ClassOrInterface<T>>
implements JavaClassTranslator<DOC, T> {

    private final Injector injector;
    private final Function<DOC, T> creator;
    private final DOC document;
    private final boolean generated;

    public SimpleTranslator(
            Injector injector,
            DOC document,
            Function<DOC, T> creator,
            boolean generated) {

        this.creator   = requireNonNull(creator);
        this.document  = requireNonNull(document);
        this.injector  = requireNonNull(injector);
        this.generated = generated;
    }

    @Override
    public File get() {
        final T generated = creator.apply(document);
        final File file = File.of(getSupport().baseDirectoryName() + "/"
            + (isInGeneratedPackage() ? "generated/" : "")
            + generated.getName() + ".java"
        );

        file.add(generated);
        return file;
    }

    @Override
    public TranslatorSupport<DOC> getSupport() {
        return new TranslatorSupport<>(injector, document);
    }

    @Override
    public DOC getDocument() {
        return document;
    }

    @Override
    public boolean isInGeneratedPackage() {
        return generated;
    }

    @Override
    public Generator getCodeGenerator() {
        return Generator.forJava();
    }

    @Override
    public void onMake(BiConsumer<File, Builder<T>> action) {
        // This implementation doesn't use listeners.
    }

    @Override
    public Stream<BiConsumer<File, Builder<T>>> listeners() {
        return Stream.empty();
    }
}
