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
package com.speedment.component;

import com.speedment.annotation.Api;
import com.speedment.code.TranslatorConstructor;
import com.speedment.code.Translator;
import com.speedment.code.TranslatorKey;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.File;
import java.util.stream.Stream;
import com.speedment.internal.util.JavaLanguageNamer;
import java.util.function.Supplier;

/**
 * This Component interface is used for Speedmens's code generation.
 *
 * @author Per Minborg
 * @since 2.3
 */
@Api(version = "2.3")
public interface CodeGenerationComponent extends Component {


    @Override
    default Class<CodeGenerationComponent> getComponentClass() {
        return CodeGenerationComponent.class;
    }

    /**
     * Returns the current {@link Generator}.
     *
     * @return the current {@link Generator}
     */
    Generator getGenerator();

    /**
     * Sets the current {@link Generator}.
     *
     * @param generator to set
     */
    void setGenerator(Generator generator);

    /**
     * Puts a new {@code TranslatorConstructor} for the given class/key pair. If
     * an old TranslatorConstructor exists for the same class/key pair, it is
     * replaced.
     *
     * @param <T> Type of Document
     * @param clazz Class of the Document
     * @param tKey translatorKey to use
     * @param constructor to use when producing Translators of the specified
     * type
     */
    default <T extends HasMainInterface> void put(Class<T> clazz, TranslatorKey<T> tKey, TranslatorConstructor<T> constructor) {
        put(clazz, tKey.getKey(), constructor);
    }

    /**
     * Puts a new {@code TranslatorConstructor} for the given class/key pair. If
     * an old TranslatorConstructor exists for the same class/key pair, it is
     * replaced.
     *
     * @param <T> Type of Document
     * @param clazz CLass of the Document
     * @param key key to use
     * @param constructor to use when producing Translators of the specified
     * type
     */
    <T extends HasMainInterface> void put(Class<T> clazz, String key, TranslatorConstructor<T> constructor);

    /**
     * Removes the {@code TranslatorConstructor} for the given class/key pair.
     *
     * @param <T> Type of Document
     * @param clazz CLass of the Document
     * @param tKey translatorKey to use
     */
    default <T extends HasMainInterface> void remove(Class<T> clazz, TranslatorKey<T> tKey) {
        remove(clazz, tKey.getKey());
    }

    /**
     * Removes the {@code TranslatorConstructor} for the given class/key pair.
     *
     * @param <T> Type of Document
     * @param clazz CLass of the Document
     * @param key key to use
     */
    <T extends HasMainInterface> void remove(Class<T> clazz, String key);

    /**
     * Returns a Stream of newly created {@code Translator Translators} for the
     * given Document. The translators are all created regardless of its
     * registered key.
     *
     * @param <T> Document type
     * @param document to use when making translators
     * @return a Stream of newly created {@code Translator Translators} for the
     * given Document
     */
    <T extends HasMainInterface> Stream<? extends Translator<T, File>> translators(T document);

    /**
     * Returns a Stream of newly created {@code Translator Translators} for the
     * given Document and key. Only Translators that match the provided key are
     * included in the Stream.
     *
     * @param <T> Document type
     * @param document to use when making translators
     * @param hasKey key
     * @return a Stream of newly created {@code Translator Translators} for the
     * given Document
     * @throws SpeedmentException if the specified translator did not exist
     */
    default <T extends HasMainInterface> Translator<T, File> 
        findTranslator(T document, TranslatorKey<T> hasKey) throws SpeedmentException {
        return CodeGenerationComponent.this.findTranslator(document, hasKey.getKey());
    }

    /**
     * Returns a Stream of newly created {@code Translator Translators} for the
     * given Document and key. Only Translators that match the provided key are
     * included in the Stream.
     *
     * @param <T> Document type
     * @param document to use when making translators
     * @param key key
     * @return the newly created {@code Translator Translators} for the
     * given Document
     * @throws SpeedmentException if the specified translator did not exist
     */
    <T extends HasMainInterface> Translator<T, File> findTranslator(T document, String key) throws SpeedmentException;
    
    /**
     * Returns the current {@link JavaLanguageNamer} used by Speedment.
     * 
     * @return the current {@link JavaLanguageNamer} used by Speedment
     */
    
    JavaLanguageNamer javaLanguageNamer();
    
    void setJavaLanguageNamerSupplier(Supplier<? extends JavaLanguageNamer> supplier);

}
