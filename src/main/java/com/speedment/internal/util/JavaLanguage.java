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
package com.speedment.internal.util;

import static com.speedment.internal.codegen.util.Formatting.ucfirst;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
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
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class JavaLanguage {

    // From http://download.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
    //
    // Literals
    public final static Set<String> JAVA_LITERAL_WORDS = unmodifiableSetOf(
            "true", "false", "null"
    );

    // Java reserved keywords
    public final static Set<String> JAVA_RESERVED_WORDS = unmodifiableSetOf(
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

    public static final Set<Class<?>> JAVA_BUILT_IN_CLASSES = unmodifiableSetOf(
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

    public static final Set<String> JAVA_DEFAULT_IMPORTS = unmodifiableSetOf("java.lang");

    public final static Set<String> JAVA_BUILT_IN_CLASS_WORDS = JAVA_BUILT_IN_CLASSES.stream().map(Class::getSimpleName).collect(toUnmodifiableSet());

    public final static Set<String> JAVA_USED_WORDS = Stream.of(
            JAVA_LITERAL_WORDS,
            JAVA_RESERVED_WORDS,
            JAVA_BUILT_IN_CLASS_WORDS
    )
            .flatMap(Collection::stream)
            .collect(toUnmodifiableSet());

    public final static Set<String> JAVA_USED_WORDS_LOWER_CASE = JAVA_USED_WORDS.stream()
            .map(String::toLowerCase)
            .collect(toUnmodifiableSet());

    private static final Character REPLACEMENT_CHARACTER = '_';
    //private static final Set<String> REPLACEMENT_STRING_SET = unmodifiableSetOf("_", "-", "+", " ");

    public static String javaTypeName(final String externalName) {
        requireNonNull(externalName);
        return javaName(externalName, Character::toUpperCase);
    }

    public static String javaVariableName(final String externalName) {
        requireNonNull(externalName);
        return javaName(externalName, Character::toLowerCase);
    }

    public static String javaStaticFieldName(final String externalName) {
        requireNonNull(externalName);
        return javaName(toUnderscoreSeparated(externalName), Character::toUpperCase).toUpperCase();
    }

    public static String javaPacketName(final String externalName) {
        requireNonNull(externalName);
        return replaceIfIllegalJavaIdentifierCharacter(
                replaceIfJavaUsedWord(externalName)
        ).toLowerCase();
    }

    private static String javaName(final String externalName, Function<Character, Character> mutator) {

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

    public static String javaNameFromExternal(final String externalName) {
        requireNonNull(externalName);
        String result = unQuote(externalName.trim()); // Trim if there are initial spaces or trailing spaces...

        // CamelCase
        result = Stream.of(result.replaceAll("([A-Z]+)", "_$1").split("[^A-Za-z0-9]")).map(String::toLowerCase).map(s -> ucfirst(s)).collect(Collectors.joining());

//        int underscoreIndex;
//        for (String replacement : REPLACEMENT_STRING_SET) {
//            while ((underscoreIndex = result.indexOf(replacement)) != -1) {
//                String c = result.charAt(underscoreIndex + 1) + "";
//                c = c.toUpperCase();
//                result = result.substring(0, underscoreIndex) + c + result.substring(underscoreIndex + 2, result.length());
//            }
//        }
        return replaceIfIllegalJavaIdentifierCharacter(replaceIfJavaUsedWord(result));
    }

//    public static String addFirstIfStartsWithIllegalIdentifierCharacter(final String word) {
//        requireNonNull(word);
//        if (word.isEmpty()) {
//            return word;
//        }
//        final Character first = word.charAt(0);
//        if (!Character.isJavaIdentifierStart(first)) {
//            return "_" + word;
//        }
//        return word;
//    }

    public static String replaceIfJavaUsedWord(final String word) {
        requireNonNull(word);
        // We need to replace regardless of case because we do not know how the retuned string is to be used
        if (JAVA_USED_WORDS_LOWER_CASE.contains(word.toLowerCase())) {
            // If it is a java reseved/literal/class, add a "_" at the end to avoid naming conflics
            return word + "_";
        }
        return word;
    }

    public static String replaceIfIllegalJavaIdentifierCharacter(final String word) {
        requireNonNull(word);
        if (word.isEmpty()) {
            return "_"; // No name is translated to REPLACEMENT_CHARACTER only
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
            } else {
                if (Character.isJavaIdentifierPart(c)) {
                    // Fine! Just add it
                    sb.append(c);
                } else {
                    // Cannot be used as a java identifier. Replace it
                    sb.append(REPLACEMENT_CHARACTER);
                }
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

    public static String javaObjectName(final String javaTypeName) {
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

    public static String toUnderscoreSeparated(final String javaName) {
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

    public static String toHumanReadable(final String javaName) {
        requireNonNull(javaName);
        return Stream.of(unQuote(javaName.trim())
                .replaceAll("([A-Z]+)", "_$1")
                .split("[^A-Za-z0-9]"))
                .map(String::trim).filter(s -> !s.isEmpty())
                .map(String::toLowerCase)
                .map(s -> ucfirst(s)).collect(Collectors.joining(" "));
    }

    /**
     * Utility classes should not be instantiated.
     */
    private JavaLanguage() {
        instanceNotAllowed(getClass());
    }
}
