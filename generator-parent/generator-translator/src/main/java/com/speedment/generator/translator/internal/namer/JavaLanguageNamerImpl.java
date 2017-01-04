/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.generator.translator.internal.namer;

import com.speedment.generator.translator.namer.JavaLanguageNamer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.util.Formatting.ucfirst;
import static com.speedment.runtime.core.internal.util.sql.SqlUtil.unQuote;
import static com.speedment.runtime.core.util.CollectorUtil.toUnmodifiableSet;
import static com.speedment.runtime.core.util.CollectorUtil.unmodifiableSetOf;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public class JavaLanguageNamerImpl implements JavaLanguageNamer {

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

    @Override
    public String javaTypeName(final String externalName) {
        requireNonNull(externalName);
        return javaName(externalName, Character::toUpperCase);
    }

    @Override
    public String javaVariableName(final String externalName) {
        requireNonNull(externalName);
        return javaName(externalName, Character::toLowerCase);
    }

    @Override
    public String javaStaticFieldName(final String externalName) {
        requireNonNull(externalName);
        return toUnderscoreSeparated(javaNameFromExternal(externalName)).toUpperCase();
    }

    @Override
    public String javaPackageName(final String externalName) {
        requireNonNull(externalName);
        return replaceIfIllegalJavaIdentifierCharacter(
            replaceIfJavaUsedWord(
                externalName.replace(" ", "") // For package name, just remove all spaces (see #167)
            )
        ).toLowerCase();
    }

    @Override
    public String javaName(final String externalName, Function<Character, Character> mutator) {

        final StringBuilder sb = new StringBuilder(javaNameFromExternal(externalName));

        int startIndex = 0;
        for (int i = 0; i < externalName.length(); i++) {
            if (Character.isAlphabetic(sb.charAt(i))) {
                // Skip over any non alphabetic characers like "_"
                startIndex = i;
                break;
            }
        }

        if (sb.length() > startIndex) {
            sb.replace(startIndex, startIndex + 1, String.valueOf(mutator.apply(sb.charAt(startIndex))));
        }
        return sb.toString();
    }

    @Override
    public String nameFromExternal(final String externalName) {
        requireNonNull(externalName);
        String result = unQuote(externalName.trim()); // Trim if there are initial spaces or trailing spaces...
        // CamelCase
        // http://stackoverflow.com/questions/4050381/regular-expression-for-checking-if-capital-letters-are-found-consecutively-in-a
        // [A-Z] -> \p{Lu}
        // [^A-Za-z0-9] -> [^\pL0-90-9]
        result = Stream.of(result.replaceAll("([\\p{Lu}]+)", "_$1").split("[^\\pL0-9]")).map(String::toLowerCase).map(s -> ucfirst(s)).collect(Collectors.joining());
        return result;
    }

    @Override
    public String javaNameFromExternal(final String externalName) {
        requireNonNull(externalName);
        return replaceIfIllegalJavaIdentifierCharacter(replaceIfJavaUsedWord(nameFromExternal(externalName)));
    }

    @Override
    public String replaceIfJavaUsedWord(final String word) {
        requireNonNull(word);
        // We need to replace regardless of case because we do not know how the retuned string is to be used
        if (JAVA_USED_WORDS_LOWER_CASE.contains(word.toLowerCase())) {
            // If it is a java reseved/literal/class, add a "_" at the end to avoid naming conflics
            return word + "_";
        }
        return word;
    }

    @Override
    public String replaceIfIllegalJavaIdentifierCharacter(final String word) {
        requireNonNull(word);
        if (word.isEmpty()) {
            return REPLACEMENT_CHARACTER.toString(); // No name is translated to REPLACEMENT_CHARACTER only
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            Character c = word.charAt(i);
            if (i == 0) {
                if (Character.isJavaIdentifierStart(c)) {
                    // Fine! Just add the first character
                    sb.append(c);
                } else if (Character.isJavaIdentifierPart(c)) {
                    // Not ok as the first, but ok otherwise. Add the replacement before it
                    sb.append(REPLACEMENT_CHARACTER).append(c);
                } else {
                    // Cannot be used as a java identifier. Replace it
                    sb.append(REPLACEMENT_CHARACTER);
                }
            } else if (Character.isJavaIdentifierPart(c)) {
                // Fine! Just add it
                sb.append(c);
            } else {
                // Cannot be used as a java identifier. Replace it
                sb.append(REPLACEMENT_CHARACTER);
            }

        }
        return sb.toString();

        // We need to replace regardless of case because we do not know how the retuned string is to be used
        //if (JAVA_USED_WORDS_LOWER_CASE.contains(word.toLowerCase())) {
        // If it is a java reseved/literal/class, add a "_" at the end to avoid naming conflics
        //    return word + "_";
        //}
        //return word;
    }

    @Override
    public String javaObjectName(final String javaTypeName) {
        requireNonNull(javaTypeName);
        String result = null;
        if (javaTypeName.startsWith("int")) {
            result = Integer.class.getSimpleName();
        } else if (javaTypeName.startsWith("long")) {
            result = Long.class.getSimpleName();
        } else if (javaTypeName.startsWith("double")) {
            result = Double.class.getSimpleName();
        } else if (javaTypeName.startsWith("float")) {
            result = Float.class.getSimpleName();
        } else if (javaTypeName.startsWith("boolean")) {
            result = Boolean.class.getSimpleName();
        } else if (javaTypeName.startsWith("byte")) {
            result = Byte.class.getSimpleName();
        }

        if (result != null) {
            if (javaTypeName.endsWith("[]")) {
                result += "[]";
            }
        } else {
            result = javaTypeName;
        }

        return result;
    }

    @Override
    public String toUnderscoreSeparated(final String javaName) {
        requireNonNull(javaName);
        final StringBuilder result = new StringBuilder();
        final String input = unQuote(javaName.trim());
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (result.length() == 0) {
                result.append(Character.toLowerCase(c));
            } else if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

}
