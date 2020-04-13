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
package com.speedment.generator.translator.internal.component;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.Enum;
import com.speedment.common.codegen.model.Interface;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.mapstream.MapStream;
import com.speedment.generator.translator.*;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.generator.translator.component.DecoratorBuilder;
import com.speedment.generator.translator.component.TranslatorAppender;
import com.speedment.generator.translator.component.function.GenerateClass;
import com.speedment.generator.translator.component.function.GenerateEnum;
import com.speedment.generator.translator.component.function.GenerateInterface;
import com.speedment.generator.translator.exception.SpeedmentTranslatorException;
import com.speedment.generator.translator.internal.SimpleTranslator;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public final class CodeGenerationComponentImpl implements CodeGenerationComponent {

    private Injector injector;

    private final Map<Class<? extends HasMainInterface>, Map<String, TranslatorSettings<?, ?>>> map;

    public CodeGenerationComponentImpl() {
        map = new ConcurrentHashMap<>();
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        this.injector = requireNonNull(injector);
    }

    @Override
    public CodeGenerationComponent newClass(GenerateClass<Project> creator) {
        injector.inject(creator);
        put(Project.class,
            com.speedment.common.codegen.model.Class.class,
            creator.getClass().getName(),
            (inj, project) -> new SimpleTranslator<>(inj, project, creator, false));
        return this;
    }

    @Override
    public CodeGenerationComponent newEnum(GenerateEnum<Project> creator) {
        injector.inject(creator);
        put(Project.class,
            Enum.class,
            creator.getClass().getName(),
            (inj, project) -> new SimpleTranslator<>(inj, project, creator, false));
        return this;
    }

    @Override
    public CodeGenerationComponent newInterface(GenerateInterface<Project> creator) {
        injector.inject(creator);
        put(Project.class,
            Interface.class,
            creator.getClass().getName(),
            (inj, project) -> new SimpleTranslator<>(inj, project, creator, false));
        return this;
    }

    @Override
    public TranslatorAppender<Table> forEveryTable() {
        return new TranslatorAppenderImpl<>(Table.class);
    }

    @Override
    public TranslatorAppender<Schema> forEverySchema() {
        return new TranslatorAppenderImpl<>(Schema.class);
    }

    @Override
    public TranslatorAppender<Dbms> forEveryDbms() {
        return new TranslatorAppenderImpl<>(Dbms.class);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
    DecoratorBuilder<T> decorate(TranslatorKey<DOC, T> key) {
        return new DecoratorBuilderImpl<>(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        void put(Class<DOC> docType, Class<T> modelType, String key, TranslatorConstructor<DOC, T> constructor) {
        acquireTranslatorSettings(docType, modelType, key).setConstructor(constructor);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        void add(Class<DOC> docType, Class<T> modelType, String key, TranslatorDecorator<DOC, T> decorator) {
        acquireTranslatorSettings(docType, modelType, key).decorators().add(decorator);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        void remove(Class<DOC> docType, String key) {
        acquireTranslatorSettings(docType, null, key).setConstructor(null);
    }

    private <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
        TranslatorSettings<DOC, T> acquireTranslatorSettings(Class<DOC> docType, Class<T> modelType, String key) {
        @SuppressWarnings("unchecked")
        final TranslatorSettings<DOC, T> result = (TranslatorSettings<DOC, T>) map
            .computeIfAbsent(docType,
                s -> new ConcurrentHashMap<>()
            ).computeIfAbsent(key, TranslatorSettings::new);

        return result;
    }

    @Override
    public <DOC extends HasName & HasMainInterface>
        Stream<? extends Translator<DOC, ?>> translators(DOC document) {
        return translators(document, s -> true);
    }

    @Override
    public Stream<String> translatorKeys() {
        return map.values().stream()
            .flatMap(m -> MapStream.of(m).filterValue(s -> s.constructor != null).keys())
            .distinct();
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
            .filter(Objects::nonNull)
            .filter(s -> s.getConstructor() != null)
            .map(settings -> settings.createDecorated(injector, document));
    }

    private static Supplier<SpeedmentTranslatorException> noTranslatorFound(HasMainInterface doc, String key) {
        return () -> new SpeedmentTranslatorException(
            "Found no translator with key '"
            + key + "' for document '"
            + doc.mainInterface().getSimpleName() + "'."
        );
    }

    private static final class TranslatorSettings<DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> {

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

        public JavaClassTranslator<DOC, T> createDecorated(Injector injector, DOC document) {
            @SuppressWarnings("unchecked")
            final JavaClassTranslator<DOC, T> translator
                = (JavaClassTranslator<DOC, T>) getConstructor().apply(injector, document);

            injector.inject(translator);
            
            decorators.stream()
                .map(injector::inject)
                .forEachOrdered(dec -> dec.apply(translator));

            return translator;
        }
    }

    private final class DecoratorBuilderImpl
        <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>>
    implements DecoratorBuilder<T> {

        private final TranslatorKey<DOC, T> key;

        DecoratorBuilderImpl(TranslatorKey<DOC, T> key) {
            this.key = requireNonNull(key);
        }

        @Override
        public CodeGenerationComponent preGenerate(Consumer<T> action) {
            final Class<?> documentType = figureOutDocumentType();
            if (Project.class.equals(documentType)) {
                add(figureOutDocumentType(), key, translator ->
                    translator.onMake((file, builder) ->
                        builder.forEveryProject(Translator.Phase.PRE_MAKE,
                            (model, table) -> action.accept(model))
                    )
                );
            } else if (Table.class.equals(documentType)) {
                add(figureOutDocumentType(), key, translator ->
                    translator.onMake((file, builder) ->
                        builder.forEveryTable(Translator.Phase.PRE_MAKE,
                            (model, table) -> action.accept(model))
                    )
                );
            } else {
                throw new IllegalArgumentException(
                    "Can not decorate translator with document type " +
                        documentType);
            }

            return CodeGenerationComponentImpl.this;
        }

        @Override
        public CodeGenerationComponent postGenerate(Consumer<T> action) {
            final Class<?> documentType = figureOutDocumentType();
            if (Project.class.equals(documentType)) {
                add(figureOutDocumentType(), key, translator ->
                    translator.onMake((file, builder) ->
                        builder.forEveryProject(Translator.Phase.POST_MAKE,
                            (model, table) -> action.accept(model))
                    )
                );
            } else if (Table.class.equals(documentType)) {
                add(figureOutDocumentType(), key, translator ->
                    translator.onMake((file, builder) ->
                        builder.forEveryTable(com.speedment.generator.translator.Translator.Phase.POST_MAKE,
                            (model, table) -> action.accept(model))
                    )
                );
            } else {
                throw new IllegalArgumentException(
                    "Can not decorate translator with document type " +
                        documentType);
            }

            return CodeGenerationComponentImpl.this;
        }

        private MapStream<Class<? extends HasMainInterface>, Map<String, TranslatorSettings<?, ?>>> translatorKeyClasses() {
            return MapStream.of(map);
        }

        @SuppressWarnings("unchecked")
        private Class<DOC> figureOutDocumentType() {
            return (Class<DOC>) translatorKeyClasses()
                .filterValue(settings -> null != settings.get(key.getKey()))
                .keys().findFirst().orElseThrow(() -> new IllegalArgumentException(
                    "Could not find any translators with key " + key
                ));
        }
    }

    private final class TranslatorAppenderImpl<DOC extends HasName & HasMainInterface>
    implements TranslatorAppender<DOC> {

        private final Class<DOC> docClass;

        public TranslatorAppenderImpl(Class<DOC> docClass) {
            this.docClass = requireNonNull(docClass);
        }

        @Override
        public CodeGenerationComponent newClass(GenerateClass<DOC> creator) {
            injector.inject(creator);
            put(docClass,
                com.speedment.common.codegen.model.Class.class,
                creator.getClass().getName(),
                (inj, project) -> new SimpleTranslator<>(inj, project, creator, false));
            return CodeGenerationComponentImpl.this;
        }

        @Override
        public CodeGenerationComponent newEnum(GenerateEnum<DOC> creator) {
            injector.inject(creator);
            put(docClass,
                Enum.class,
                creator.getClass().getName(),
                (inj, project) -> new SimpleTranslator<>(inj, project, creator, false));
            return CodeGenerationComponentImpl.this;
        }

        @Override
        public CodeGenerationComponent newInterface(GenerateInterface<DOC> creator) {
            injector.inject(creator);
            put(docClass,
                Interface.class,
                creator.getClass().getName(),
                (inj, project) -> new SimpleTranslator<>(inj, project, creator, false));
            return CodeGenerationComponentImpl.this;
        }
    }
}
