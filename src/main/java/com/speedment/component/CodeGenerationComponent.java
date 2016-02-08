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
import com.speedment.code.TranslatorDecorator;
import com.speedment.code.TranslatorKey;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.lang.models.ClassOrInterface;
import java.util.stream.Stream;
import com.speedment.internal.util.JavaLanguageNamer;
import com.speedment.stream.MapStream;
import java.util.Set;
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
     * @param <DOC>        type of Document
     * @param <T>          type of codegen model
     * @param docType        class of the Document
     * @param tKey         translatorKey to use
     * @param constructor  to use when producing Translators of the specified
     *                     type
     */
    default <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void 
    put(Class<DOC> docType, TranslatorKey<DOC, T> tKey, TranslatorConstructor<DOC, T> constructor) {
        put(docType, tKey.getTranslatedType(), tKey.getKey(), constructor);
    }

    /**
     * Puts a new {@code TranslatorConstructor} for the given class/key pair. If
     * an old TranslatorConstructor exists for the same class/key pair, it is
     * replaced.
     *
     * @param <DOC>        type of Document
     * @param <T>          type of codegen model
     * @param docType      class of the Document
     * @param modelType    class of the codegen model
     * @param key          key to use
     * @param constructor  to use when producing Translators of the specified
     *                     type
     */
    <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void 
    put(Class<DOC> docType, Class<T> modelType, String key, TranslatorConstructor<DOC, T> constructor);

    /**
     * Adds a new {@code TranslatorDecorator} for the given class/key pair.
     *
     * @param <DOC>      type of Document
     * @param <T>        type of codegen model
     * @param docType      class of the Document
     * @param tKey       translatorKey to use
     * @param decorator  the new decorator
     */
    default <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void 
    add(Class<DOC> docType, TranslatorKey<DOC, T> tKey, TranslatorDecorator<DOC, T> decorator) {
        add(docType, tKey.getTranslatedType(), tKey.getKey(), decorator);
    }

    /**
     * Adds a new {@code TranslatorDecorator} for the given class/key pair.
     *
     * @param <DOC>      type of Document
     * @param <T>        type of codegen model
     * @param docType    class of the Document
     * @param modelType  class of the codegen mdoel
     * @param key        key to use
     * @param decorator  the new decorator
     */
    <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void 
    add(Class<DOC> docType, Class<T> modelType, String key, TranslatorDecorator<DOC, T> decorator);

    /**
     * Removes the {@code TranslatorConstructor} for the given class/key pair.
     *
     * @param <DOC>  type of Document
     * @param <T>    type of codegen model
     * @param docType  class of the Document
     * @param tKey   translatorKey to use
     */
    default <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void 
    remove(Class<DOC> docType, TranslatorKey<DOC, T> tKey) {
        remove(docType, tKey.getKey());
    }

    /**
     * Removes the {@code TranslatorConstructor} for the given class/key pair.
     *
     * @param <DOC>    type of Document
     * @param <T>      type of codegen model
     * @param docType  class of the Document
     * @param key      key to use
     */
    <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> void 
    remove(Class<DOC> docType, String key);

    /**
     * Returns a Stream of newly created {@code Translator Translators} for the
     * given Document. The translators are all created regardless of its
     * registered key.
     *
     * @param <DOC>     Document type
     * @param document  to use when making translators
     * @return          a Stream of newly created {@code Translator Translators} 
     *                  for the given Document
     */
    <DOC extends HasName & HasMainInterface> Stream<? extends Translator<DOC, ?>> 
    translators(DOC document);

    /**
     * Returns a Stream of newly created {@code Translator Translators} for the
     * given Document and key. Only Translators that match the provided key are
     * included in the Stream.
     *
     * @param <DOC>     document type
     * @param <T>       codegen model type
     * @param document  to use when making translators
     * @param key       the key
     * @return          a Stream of newly created {@code Translator Translators} 
     *                  for the given Document
     * 
     * @throws SpeedmentException  if the specified translator did not exist
     */
    default <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> Translator<DOC, T>
    findTranslator(DOC document, TranslatorKey<DOC, T> key) throws SpeedmentException {
        return CodeGenerationComponent.this.findTranslator(document, key.getTranslatedType(), key.getKey());
    }

    /**
     * Returns a Stream of newly created {@code Translator Translators} for the
     * given Document and key. Only Translators that match the provided key are
     * included in the Stream.
     *
     * @param <DOC>      document type
     * @param <T>        codegen model type
     * @param document   to use when making translators
     * @param modelType  type to indicate return type variables
     * @param key        the key
     * @return           the newly created {@code Translator Translators} for the 
     *                   given Document
     * 
     * @throws SpeedmentException  if the specified translator did not exist
     */
    <DOC extends HasName & HasMainInterface, T extends ClassOrInterface<T>> Translator<DOC, T> 
    findTranslator(DOC document, Class<T> modelType, String key) throws SpeedmentException;

    /**
     * Returns the current {@link JavaLanguageNamer} used by Speedment.
     *
     * @return  the current {@link JavaLanguageNamer}
     */
    JavaLanguageNamer javaLanguageNamer();

    void setJavaLanguageNamerSupplier(Supplier<? extends JavaLanguageNamer> supplier);

    MapStream<Class<? extends HasMainInterface>, Set<String>> stream();
}