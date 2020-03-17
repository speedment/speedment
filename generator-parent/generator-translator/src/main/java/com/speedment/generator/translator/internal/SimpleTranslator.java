/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.generator.translator.internal;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.controller.AlignTabs;
import com.speedment.common.codegen.controller.AutoImports;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.File;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.JavaClassTranslator;
import com.speedment.generator.translator.Translator;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.generator.translator.component.function.GeneratorFunction;
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
    private final GeneratorFunction<DOC, T> creator;
    private final DOC document;
    private final boolean generated;

    public SimpleTranslator(
            Injector injector,
            DOC document,
            GeneratorFunction<DOC, T> creator,
            boolean generated) {

        this.creator   = requireNonNull(creator);
        this.document  = requireNonNull(document);
        this.injector  = requireNonNull(injector);
        this.generated = generated;
    }

    @Override
    public File get() {
        final T generatedClass = creator.generate(document);
        final File file = File.of(getSupport().baseDirectoryName() + "/"
            + (isInGeneratedPackage() ? "generated/" : "")
            + generatedClass.getName() + ".java"
        );

        file.add(generatedClass);
        file.call(new AutoImports(getCodeGenerator().getDependencyMgr()));
        file.call(new AlignTabs<>());
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
