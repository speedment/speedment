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
package com.speedment.util;

import static com.speedment.internal.codegen.util.Formatting.ucfirst;
import static com.speedment.internal.util.sql.SqlUtil.unQuote;
import static com.speedment.util.CollectorUtil.toUnmodifiableSet;
import static com.speedment.util.CollectorUtil.unmodifiableSetOf;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface JavaLanguageNamer {

    // From http://download.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
    //
    // Literals
    final static Set<String> JAVA_LITERAL_WORDS = unmodifiableSetOf(
            "true", "false", "null"
    );

    // Java reserved keywords
    final static Set<String> JAVA_RESERVED_WORDS = unmodifiableSetOf(
            // Unused
            "const", "goto",
            // The real ones...
            "abstract",
            "continue",
            "for",
            "new",
            "switch",
            "assert",
            "default",
            "goto",
            "package",
            "synchronized",
            "boolean",
            "do",
            "if",
            "private",
            "this",
            "break",
            "double",
            "implements",
            "protected",
            "throw",
            "byte",
            "else",
            "import",
            "public",
            "throws",
            "case",
            "enum",
            "instanceof",
            "return",
            "transient",
            "catch",
            "extends",
            "int",
            "short",
            "try",
            "char",
            "final",
            "interface",
            "static",
            "void",
            "class",
            "finally",
            "long",
            "strictfp",
            "volatile",
            "const",
            "float",
            "native",
            "super",
            "while"
    );

    final static Set<Class<?>> JAVA_BUILT_IN_CLASSES = unmodifiableSetOf(
            Boolean.class,
            Byte.class,
            Character.class,
            Double.class,
            Float.class,
            Integer.class,
            Long.class,
            Object.class,
            Short.class,
            String.class,
            BigDecimal.class,
            BigInteger.class,
            boolean.class,
            byte.class,
            char.class,
            double.class,
            float.class,
            int.class,
            long.class,
            short.class
    );

    final static Set<String> JAVA_DEFAULT_IMPORTS = unmodifiableSetOf("java.lang");

    final static Set<String> JAVA_BUILT_IN_CLASS_WORDS = JAVA_BUILT_IN_CLASSES.stream().map(Class::getSimpleName).collect(toUnmodifiableSet());

    final static Set<String> JAVA_USED_WORDS = Stream.of(
            JAVA_LITERAL_WORDS,
            JAVA_RESERVED_WORDS,
            JAVA_BUILT_IN_CLASS_WORDS
    )
            .flatMap(Collection::stream)
            .collect(toUnmodifiableSet());

    static final Set<String> JAVA_USED_WORDS_LOWER_CASE = JAVA_USED_WORDS.stream()
            .map(String::toLowerCase)
            .collect(toUnmodifiableSet());

    static final Character REPLACEMENT_CHARACTER = '_';

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
