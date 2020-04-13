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
package com.speedment.generator.translator.provider;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.generator.translator.Translator;
import com.speedment.generator.translator.TranslatorConstructor;
import com.speedment.generator.translator.TranslatorDecorator;
import com.speedment.generator.translator.TranslatorKey;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.generator.translator.component.DecoratorBuilder;
import com.speedment.generator.translator.component.TranslatorAppender;
import com.speedment.generator.translator.component.function.GenerateClass;
import com.speedment.generator.translator.component.function.GenerateEnum;
import com.speedment.generator.translator.component.function.GenerateInterface;
import com.speedment.generator.translator.internal.component.CodeGenerationComponentImpl;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;

import java.util.stream.Stream;

public final class StandardCodeGenerationComponent implements CodeGenerationComponent {

    private final CodeGenerationComponentImpl inner;

    public StandardCodeGenerationComponent() {
        this.inner = new CodeGenerationComponentImpl();
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        inner.setInjector(injector);
    }

    @Override
    public CodeGenerationComponent newClass(GenerateClass<Project> creator) {
        return inner.newClass(creator);
    }

    @Override
    public CodeGenerationComponent newEnum(GenerateEnum<Project> creator) {
        return inner.newEnum(creator);
    }

    @Override
    public CodeGenerationComponent newInterface(GenerateInterface<Project> creator) {
        return inner.newInterface(creator);
    }

    @Override
    public TranslatorAppender<Table> forEveryTable() {
        return inner.forEveryTable();
    }

    @Override
    public TranslatorAppender<Schema> forEverySchema() {
        return inner.forEverySchema();
    }

    @Override
    public TranslatorAppender<Dbms> forEveryDbms() {
        return inner.forEveryDbms();
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> DecoratorBuilder<T> decorate(TranslatorKey<DOC, T> key) {
        return inner.decorate(key);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void put(Class<DOC> docType, Class<T> modelType, String key, TranslatorConstructor<DOC, T> constructor) {
        inner.put(docType, modelType, key, constructor);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void add(Class<DOC> docType, Class<T> modelType, String key, TranslatorDecorator<DOC, T> decorator) {
        inner.add(docType, modelType, key, decorator);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void remove(Class<DOC> docType, String key) {
        inner.remove(docType, key);
    }

    @Override
    public <DOC extends HasName & HasMainInterface> Stream<? extends Translator<DOC, ?>> translators(DOC document) {
        return inner.translators(document);
    }

    @Override
    public Stream<String> translatorKeys() {
        return inner.translatorKeys();
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> Translator<DOC, T> findTranslator(DOC document, Class<T> modelType, String key) {
        return inner.findTranslator(document, modelType, key);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void put(Class<DOC> docType, TranslatorKey<DOC, T> tKey, TranslatorConstructor<DOC, T> constructor) {
        inner.put(docType, tKey, constructor);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void add(Class<DOC> docType, TranslatorKey<DOC, T> tKey, TranslatorDecorator<DOC, T> decorator) {
        inner.add(docType, tKey, decorator);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void remove(Class<DOC> docType, TranslatorKey<DOC, T> tKey) {
        inner.remove(docType, tKey);
    }

    @Override
    public <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> Translator<DOC, T> findTranslator(DOC document, TranslatorKey<DOC, T> key) {
        return inner.findTranslator(document, key);
    }
}
