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
package com.speedment.generator.namer;

import com.speedment.common.injector.annotation.InjectKey;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.Formatting.ucfirst;
import static com.speedment.runtime.internal.util.sql.SqlUtil.unQuote;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Per Minborg
 * @since   2.2.0
 */
@InjectKey(JavaLanguageNamer.class)
public interface JavaLanguageNamer {

    String javaTypeName(final String externalName);

    String javaVariableName(final String externalName);

    String javaStaticFieldName(final String externalName);

    String javaPackageName(final String externalName);

    String javaName(final String externalName, Function<Character, Character> mutator);

    String nameFromExternal(final String externalName);

    String javaNameFromExternal(final String externalName);

    String replaceIfJavaUsedWord(final String word);

    String replaceIfIllegalJavaIdentifierCharacter(final String word);

    String javaObjectName(final String javaTypeName);

    String toUnderscoreSeparated(final String javaName);

    static String toHumanReadable(final String javaName)  {
        requireNonNull(javaName);
        return Stream.of(unQuote(javaName.trim())
                .replaceAll("([A-Z]+)", "_$1")
                .split("[^A-Za-z0-9]"))
                .map(String::trim).filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .map(s -> ucfirst(s)).collect(Collectors.joining(" "));
    }
}