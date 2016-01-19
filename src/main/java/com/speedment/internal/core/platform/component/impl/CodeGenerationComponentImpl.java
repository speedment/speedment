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
import com.speedment.component.CodeGenerationComponent;
import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.java.JavaGenerator;
import com.speedment.internal.codegen.lang.models.File;
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
import java.util.function.Supplier;

public final class CodeGenerationComponentImpl extends Apache2AbstractComponent implements CodeGenerationComponent {

    private Generator generator;
    private final Map<Class<? extends HasMainInterface>, Map<String, TranslatorConstructor<HasMainInterface>>> map;
    private Supplier<? extends JavaLanguageNamer> javaLanguageSupplier;

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
    public <T extends HasMainInterface> void put(Class<T> clazz, String key, TranslatorConstructor<T> constructor) {
        aquireMap(clazz).put(key, (TranslatorConstructor<HasMainInterface>) constructor);
    }

    @Override
    public <T extends HasMainInterface> void remove(Class<T> clazz, String key) {
        aquireMap(clazz).remove(key);
    }

    private <T extends HasMainInterface> Map<String, TranslatorConstructor<HasMainInterface>> aquireMap(Class<T> clazz) {
        return map.computeIfAbsent(clazz, s -> new ConcurrentHashMap<>());
    }

    @Override
    public <T extends HasMainInterface> Stream<? extends Translator<T, File>> translators(T document) {
        return translators(document, s -> true);
    }

    @Override
    public <T extends HasMainInterface> Translator<T, File> findTranslator(T document, String key) {
        return translators(document, key::equals)
                .findAny()
                .orElseThrow(noTranslatorFound(document, key));
    }

    @SuppressWarnings("unchecked")
    private <T extends HasMainInterface> Stream<? extends Translator<T, File>>
            translators(T document, Predicate<String> nameFilter) {
        return MapStream.of(map)
                .filterKey(c -> c.isInstance(document))
                .values()
                .flatMap(m -> MapStream.of(m).filterKey(nameFilter).values())
                .map(constructor -> ((TranslatorConstructor<T>) constructor).apply(getSpeedment(), generator, document));
    }

    @Override
    public JavaLanguageNamer javaLanguageNamer() {
        return javaLanguageSupplier.get();
    }

    @Override
    public void setJavaLanguageNamerSupplier(Supplier<? extends JavaLanguageNamer> supplier) {
        this.javaLanguageSupplier = supplier;
    }

    private static Supplier<SpeedmentException> noTranslatorFound(HasMainInterface doc, String key) {
        return () -> new SpeedmentException(
                "Found no translator with key '"
                + key + "' for document '"
                + doc.mainInterface().getSimpleName() + "'."
        );
    }
}
