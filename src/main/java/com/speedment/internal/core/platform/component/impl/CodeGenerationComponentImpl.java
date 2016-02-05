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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import static com.speedment.code.StandardTranslatorKey.*;
import com.speedment.code.Translator;
import com.speedment.code.TranslatorConstructor;
import com.speedment.code.TranslatorDecorator;
import com.speedment.component.CodeGenerationComponent;
import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.java.JavaGenerator;
import com.speedment.internal.codegen.lang.models.ClassOrInterface;
import com.speedment.internal.core.code.JavaClassTranslator;
import com.speedment.internal.core.code.entity.EntityImplTranslator;
import com.speedment.internal.core.code.entity.EntityTranslator;
import com.speedment.internal.core.code.lifecycle.SpeedmentApplicationMetadataTranslator;
import com.speedment.internal.core.code.lifecycle.SpeedmentApplicationTranslator;
import com.speedment.internal.core.code.manager.EntityManagerImplTranslator;
import com.speedment.internal.util.DefaultJavaLanguageNamer;
import com.speedment.stream.MapStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import com.speedment.internal.util.JavaLanguageNamer;
import com.speedment.license.Software;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;

public final class CodeGenerationComponentImpl extends InternalOpenSourceComponent implements CodeGenerationComponent {

    private Generator generator;
    private final Map<Class<? extends HasMainInterface>, Map<String, TranslatorSettings<?, ?>>> map;
    private Supplier<? extends JavaLanguageNamer> javaLanguageSupplier;

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
            final JavaClassTranslator<DOC, T> translator = (JavaClassTranslator<DOC, T>) 
                getConstructor().apply(speedment, generator, document);
            
            decorators.stream().forEachOrdered(dec -> dec.apply(translator));
            return translator;
        }
    }

    public CodeGenerationComponentImpl(Speedment speedment) {
        super(speedment);
        generator = new JavaGenerator();
        map = new ConcurrentHashMap<>();
        put(Table.class, ENTITY, EntityTranslator::new);
        put(Table.class, ENTITY_IMPL, EntityImplTranslator::new);
        put(Table.class, MANAGER_IMPL, EntityManagerImplTranslator::new);
        put(Project.class, SPEEDMENT_APPLICATION, SpeedmentApplicationTranslator::new);
        put(Project.class, SPEEDMENT_APPLICATION_METADATA, SpeedmentApplicationMetadataTranslator::new);
        javaLanguageSupplier = DefaultJavaLanguageNamer::new;
    }

    @Override
    public Generator getGenerator() {
        return generator;
    }

    @Override
    public void setGenerator(Generator generator) {
        this.generator = generator;
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
        return (TranslatorSettings<DOC, T>) 
            map.computeIfAbsent(docType, 
                s -> new ConcurrentHashMap<>()
            ).computeIfAbsent(key, TranslatorSettings::new);
    }

    @Override
    public <DOC extends HasName & HasMainInterface> 
    Stream<? extends Translator<DOC, ?>> translators(DOC document) {
        return translators(document, s -> true);
    }

    @Override
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
        return javaLanguageSupplier.get();
    }

    @Override
    public void setJavaLanguageNamerSupplier(Supplier<? extends JavaLanguageNamer> supplier) {
        this.javaLanguageSupplier = supplier;
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

    private static Supplier<SpeedmentException> noTranslatorFound(HasMainInterface doc, String key) {
        return () -> new SpeedmentException(
            "Found no translator with key '"
            + key + "' for document '"
            + doc.mainInterface().getSimpleName() + "'."
        );
    }
}
