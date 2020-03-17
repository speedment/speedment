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

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.generator.translator.TranslatorKey;
import com.speedment.runtime.config.trait.HasMainInterface;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author      Per Minborg
 * @param <DOC> document type
 * @param <T>   codegen main model
 */
public final class TranslatorKeyImpl<DOC extends HasMainInterface, T extends ClassOrInterface<T>> 
        implements TranslatorKey<DOC, T> {

    private final String key;
    private final Class<T> translatedType;

    public TranslatorKeyImpl(String key, Class<T> translatedType) {
        this.key            = requireNonNull(key);
        this.translatedType = requireNonNull(translatedType);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Class<T> getTranslatedType() {
        return translatedType;
    }

    @Override
    public String toString() {
        return key;
    }
}