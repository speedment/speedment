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

import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.generator.JavaClassTranslator;
import com.speedment.generator.Translator;
import com.speedment.generator.TranslatorConstructor;
import com.speedment.generator.TranslatorDecorator;
import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.generator.internal.TranslatorManagerImpl;
import com.speedment.generator.internal.entity.EntityImplTranslator;
import com.speedment.generator.internal.entity.EntityTranslator;
import com.speedment.generator.internal.entity.GeneratedEntityImplTranslator;
import com.speedment.generator.internal.entity.GeneratedEntityTranslator;
import com.speedment.generator.internal.lifecycle.ApplicationBuilderTranslator;
import com.speedment.generator.internal.lifecycle.ApplicationImplTranslator;
import com.speedment.generator.internal.lifecycle.ApplicationTranslator;
import com.speedment.generator.internal.lifecycle.GeneratedApplicationBuilderTranslator;
import com.speedment.generator.internal.lifecycle.GeneratedApplicationImplTranslator;
import com.speedment.generator.internal.lifecycle.GeneratedApplicationTranslator;
import com.speedment.generator.internal.lifecycle.GeneratedMetadataTranslator;
import com.speedment.generator.internal.manager.GeneratedManagerImplTranslator;
import com.speedment.generator.internal.manager.GeneratedManagerTranslator;
import com.speedment.generator.internal.manager.ManagerImplTranslator;
import com.speedment.generator.internal.manager.ManagerTranslator;
import com.speedment.generator.internal.util.JavaLanguageNamerImpl;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.runtime.license.Software;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.speedment.generator.StandardTranslatorKey.*;
import com.speedment.internal.common.injector.InjectBundle;
import com.speedment.internal.common.injector.Injector;
import static com.speedment.internal.common.injector.State.RESOLVED;
import com.speedment.internal.common.injector.annotation.ExecuteBefore;
import com.speedment.internal.common.injector.annotation.Inject;
import com.speedment.internal.common.mapstream.MapStream;
import static java.util.Objects.requireNonNull;

<<<<<<< HEAD
=======
@IncludeInjectable({
    EventComponentImpl.class,
    TypeMapperComponentImpl.class,
    TranslatorManagerImpl.class,
    JavaLanguageNamerImpl.class,
    PathComponentImpl.class,
    JavaGenerator.class
})
>>>>>>> origin/develop-modules
public final class CodeGenerationComponentImpl extends InternalOpenSourceComponent implements CodeGenerationComponent {

    public static InjectBundle include() {
        return InjectBundle.of(
            EventComponentImpl.class,
            TypeMapperComponentImpl.class,
            TranslatorManagerImpl.class,
            JavaLanguageNamerImpl.class,
            JavaGenerator.class 
        );
    }

    private @Inject Injector injector;

    private final Map<Class<? extends HasMainInterface>, Map<String, TranslatorSettings<?, ?>>> map;

    public CodeGenerationComponentImpl() {
        map = new ConcurrentHashMap<>();
    }

    @ExecuteBefore(RESOLVED)
    void installTranslators() {
        put(Table.class, ENTITY, EntityTranslator::new);
        put(Table.class, ENTITY_IMPL, EntityImplTranslator::new);
        put(Table.class, MANAGER, ManagerTranslator::new);
        put(Table.class, MANAGER_IMPL, ManagerImplTranslator::new);
        put(Table.class, GENERATED_ENTITY, GeneratedEntityTranslator::new);
        put(Table.class, GENERATED_ENTITY_IMPL, GeneratedEntityImplTranslator::new);
        put(Table.class, GENERATED_MANAGER, GeneratedManagerTranslator::new);
        put(Table.class, GENERATED_MANAGER_IMPL, GeneratedManagerImplTranslator::new);
        put(Project.class, APPLICATION, ApplicationTranslator::new);
        put(Project.class, APPLICATION_IMPL, ApplicationImplTranslator::new);
        put(Project.class, APPLICATION_BUILDER, ApplicationBuilderTranslator::new);
        put(Project.class, GENERATED_APPLICATION, GeneratedApplicationTranslator::new);
        put(Project.class, GENERATED_APPLICATION_IMPL, GeneratedApplicationImplTranslator::new);
        put(Project.class, GENERATED_APPLICATION_BUILDER, GeneratedApplicationBuilderTranslator::new);
        put(Project.class, GENERATED_METADATA, GeneratedMetadataTranslator::new);
    }

    @Override
    protected String getDescription() {
        return "Generates java code for a project based on a model tree.";
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
            .filter(s -> s != null)
            .filter(s -> s.getConstructor() != null)
            .map(settings -> settings.createDecorated(document))
            .map(injector::inject);
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

        public JavaClassTranslator<DOC, T> createDecorated(DOC document) {
            @SuppressWarnings("unchecked")
            final JavaClassTranslator<DOC, T> translator
                = (JavaClassTranslator<DOC, T>) getConstructor().apply(document);

            decorators.stream().forEachOrdered(dec -> dec.apply(translator));

            return translator;
        }
    }
}
