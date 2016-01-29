/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.speedment.code;

import com.speedment.annotation.Api;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.internal.codegen.lang.models.File;

/**
 *
 * @author     Emil Forslund
 * @param <T>  the document type
 * @since      2.3
 */
@Api(version = "2.3")
@FunctionalInterface
public interface TranslatorDecorator<T extends HasMainInterface> {
    void apply(Translator<T, File> translator);
}