/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.generator.internal.component;

import com.speedment.runtime.Speedment;
import com.speedment.generator.JavaClassTranslator;
import static com.speedment.generator.StandardTranslatorKey.*;
import com.speedment.generator.Translator;
import com.speedment.generator.TranslatorConstructor;
import com.speedment.generator.TranslatorDecorator;
import com.speedment.generator.TranslatorManager;
import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.fika.codegen.Generator;
import com.speedment.fika.codegen.internal.java.JavaGenerator;
import com.speedment.fika.codegen.model.ClassOrInterface;
import com.speedment.generator.internal.TranslatorManagerImpl;
import com.speedment.generator.internal.entity.EntityImplTranslator;
import com.speedment.generator.internal.entity.EntityTranslator;
import com.speedment.generator.internal.entity.GeneratedEntityImplTranslator;
import com.speedment.generator.internal.entity.GeneratedEntityTranslator;
import com.speedment.generator.internal.lifecycle.GeneratedSpeedmentApplicationMetadataTranslator;
import com.speedment.generator.internal.lifecycle.GeneratedSpeedmentApplicationTranslator;
import com.speedment.generator.internal.lifecycle.SpeedmentApplicationTranslator;
import com.speedment.generator.internal.manager.EntityManagerImplTranslator;
import com.speedment.generator.internal.manager.EntityManagerTranslator;
import com.speedment.generator.internal.manager.GeneratedEntityManagerImplTranslator;
import com.speedment.generator.internal.manager.GeneratedEntityManagerTranslator;
import com.speedment.generator.internal.util.DefaultJavaLanguageNamer;
import com.speedment.generator.util.JavaLanguageNamer;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.runtime.license.Software;
import com.speedment.fika.mapstream.MapStream;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class CodeGenerationComponentImpl extends InternalOpenSourceComponent implements CodeGenerationComponent {

    private Generator generator;
    private TranslatorManager translatorManager;
    private final Map<Class<? extends HasMainInterface>, Map<String, TranslatorSettings<?, ?>>> map;
    private JavaLanguageNamer javaLanguageNamer;

    public CodeGenerationComponentImpl(Speedment speedment) {
        super(speedment);

        generator = new JavaGenerator();
        translatorManager = new TranslatorManagerImpl(speedment);
        map = new ConcurrentHashMap<>();

        put(Table.class, ENTITY, EntityTranslator::new);
        put(Table.class, ENTITY_IMPL, EntityImplTranslator::new);
        put(Table.class, MANAGER, EntityManagerTranslator::new);
        put(Table.class, MANAGER_IMPL, EntityManagerImplTranslator::new);
        put(Table.class, GENERATED_ENTITY, GeneratedEntityTranslator::new);
        put(Table.class, GENERATED_ENTITY_IMPL, GeneratedEntityImplTranslator::new);
        put(Table.class, GENERATED_MANAGER, GeneratedEntityManagerTranslator::new);
        put(Table.class, GENERATED_MANAGER_IMPL, GeneratedEntityManagerImplTranslator::new);
        put(Project.class, APPLICATION, SpeedmentApplicationTranslator::new);
        put(Project.class, GENERATED_APPLICATION, GeneratedSpeedmentApplicationTranslator::new);
        put(Project.class, GENERATED_APPLICATION_METADATA, GeneratedSpeedmentApplicationMetadataTranslator::new);

        javaLanguageNamer = new DefaultJavaLanguageNamer();
    }

    private CodeGenerationComponentImpl(Speedment speedment, CodeGenerationComponentImpl template) {
        this(speedment);
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public void setGenerator(Generator generator) {
        this.generator = requireNonNull(generator);
    }

    @Override
    public TranslatorManager getTranslatorManager() {
        return translatorManager;
    }

    @Override
    public void setTranslatorManager(TranslatorManager manager) {
        this.translatorManager = requireNonNull(manager);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        void put(Class<DOC> docType, Class<T> modelType, String key, TranslatorConstructor<DOC, T> constructor) {
        aquireTranslatorSettings(docType, modelType, key).setConstructor(constructor);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        void add(Class<DOC> docType, Class<T> modelType, String key, TranslatorDecorator<DOC, T> decorator) {
        aquireTranslatorSettings(docType, modelType, key).decorators().add(decorator);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        void remove(Class<DOC> docType, String key) {
        aquireTranslatorSettings(docType, null, key).setConstructor(null);
    }

    private <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        TranslatorSettings<DOC, T> aquireTranslatorSettings(Class<DOC> docType, Class<T> modelType, String key) {
        @SuppressWarnings("unchecked")
        final TranslatorSettings<DOC, T> result = (TranslatorSettings<DOC, T>) map
            .computeIfAbsent(docType,
                s -> new ConcurrentHashMap<>()
            ).computeIfAbsent(key, k -> new TranslatorSettings<>(k));

        return result;
    }

    @Override
    public <DOC extends HasName & HasMainInterface>
        Stream<? extends Translator<DOC, ?>> translators(DOC document) {
        return translators(document, s -> true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        Translator<DOC, T> findTranslator(DOC document, Class<T> modelType, String key) {
        return translators(document, key::equals)
            .findAny()
            .map(translator -> (Translator<DOC, T>) translator)
            .orElseThrow(noTranslatorFound(document, key));
    }

    @SuppressWarnings("unchecked")
    private <DOC extends HasName & HasMainInterface>
        Stream<? extends Translator<DOC, ?>> translators(DOC document, Predicate<String> nameFilter) {

        return MapStream.of(map)
            .filterKey(c -> c.isInstance(document))
            .values()
            .flatMap(m -> MapStream.of(m).filterKey(nameFilter).values())
            .map(s -> (TranslatorSettings<DOC, ?>) s)
            .map(settings -> settings.createDecorated(getSpeedment(), generator, document));
    }

    @Override
    public JavaLanguageNamer javaLanguageNamer() {
        return javaLanguageNamer;
    }

    @Override
    public void setJavaLanguageNamerSupplier(Supplier<? extends JavaLanguageNamer> supplier) {
        javaLanguageNamer = supplier.get();
    }

    @Override
    public MapStream<Class<? extends HasMainInterface>, Set<String>> stream() {
        return MapStream.of(map)
            .mapValue(Map::keySet);
    }

    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }

    @Override
    public CodeGenerationComponent defaultCopy(Speedment speedment) {
        return new CodeGenerationComponentImpl(speedment, this);
    }

    private static Supplier<SpeedmentException> noTranslatorFound(HasMainInterface doc, String key) {
        return () -> new SpeedmentException(
            "Found no translator with key '"
            + key + "' for document '"
            + doc.mainInterface().getSimpleName() + "'."
        );
    }

    private final static class TranslatorSettings<DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> {

        private final String key;
        private final List<TranslatorDecorator<DOC, T>> decorators;
        private TranslatorConstructor<DOC, T> constructor;

        public TranslatorSettings(String key) {
            this.key = requireNonNull(key);
            this.decorators = new CopyOnWriteArrayList<>();
        }

        public String key() {
            return key;
        }

        public TranslatorConstructor<DOC, T> getConstructor() {
            return constructor;
        }

        public void setConstructor(TranslatorConstructor<DOC, T> constructor) {
            this.constructor = constructor;
        }

        public List<TranslatorDecorator<DOC, T>> decorators() {
            return decorators;
        }

        public JavaClassTranslator<DOC, T> createDecorated(Speedment speedment, Generator generator, DOC document) {
            @SuppressWarnings("unchecked")
            final JavaClassTranslator<DOC, T> translator = (JavaClassTranslator<DOC, T>) getConstructor().apply(speedment, generator, document);

            decorators.stream().forEachOrdered(dec -> dec.apply(translator));
            return translator;
        }
    }
}
