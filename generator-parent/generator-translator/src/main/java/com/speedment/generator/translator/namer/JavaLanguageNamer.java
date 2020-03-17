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
package com.speedment.generator.translator.namer;

import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.function.CharUnaryOperator;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.generator.translator.internal.namer.JavaLanguageNamerImpl;

import static java.util.Objects.requireNonNull;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since 2.2.0
 */
@InjectKey(JavaLanguageNamer.class)
public interface JavaLanguageNamer {

    /**
     * Returns the Java type name (e.g. "User", "ExpDate" or "Customer").
     *
     * @param externalName to base the name on
     * @return the Java type name (e.g. "User", "ExpDate" or "Customer")
     */
    String javaTypeName(final String externalName);

    /**
     * Returns the Java variable name (e.g. "user", "expDate" or "customer").
     *
     * @param externalName to base the name on
     * @return the Java type name (e.g. "user", "expDate" or "customer")
     */
    String javaVariableName(final String externalName);

    /**
     * Returns the Java static field variable name (e.g. "USER", "EXP_DATE" or
     * "CUSTOMER").
     *
     * @param externalName to base the name on
     * @return the Java static field variable name (e.g. "USER", "EXP_DATE" or
     * "CUSTOMER")
     */
    String javaStaticFieldName(final String externalName);

    /**
     * Returns the package name (e.g. "com.company.some_table").
     *
     * @param externalName to base the name on
     * @return the package name (e.g. "com.company.some_table")
     */
    String javaPackageName(final String externalName);

    /**
     * Returns the java name (e.g. "someName") by first applying the 
     * {@link #nameFromExternal(java.lang.String) } method and then applying the
     * given mutator on the first character (if any).
     *
     * @param externalName to base the name on
     * @param mutator to apply to the initial character
     * @return the java name (e.g. "someName") by first applying the 
     * {@link #javaNameFromExternal(java.lang.String) } method and then applying
     * the given mutator on the first character (if any)
     */
    String javaName(final String externalName, CharUnaryOperator mutator);

    /**
     * Returns the java name (e.g. "someName") by applying the 
     * {@link #nameFromExternal(java.lang.String) } method and then replacing
     * some reserved Java words.
     *
     * @param externalName to base the name on
     * @return the java name (e.g. "someName") by applying the 
     * {@link #nameFromExternal(java.lang.String) } method and then replacing
     * some reserved Java words
     */
    String javaNameFromExternal(final String externalName);

    /**
     * Returns the camel case java name (e.g. "someName") from an external name
     * (e.g. "some_name"). Also tidies up by removing leading and trailing
     * spaces etc.
     *
     * @param externalName to base the name on
     * @return the camel case java name (e.g. "someName") from an external name
     * (e.g. "some_name")
     */
    String nameFromExternal(final String externalName);

    /**
     * Returns a replacement word if the given word is reserved by Java,
     * otherwise the original word is returned.
     *
     * @param word  to replace
     * @return a replacement word if the given word is reserved by Java,
     * otherwise the original word is returned
     */
    String replaceIfJavaUsedWord(final String word);

    /**
     * Returns a replacement word if the given word contains illegal Java
     * identifier characters, otherwise the original word is returned.
     *
     * @param word to replace
     * @return a replacement word if the given word contains illegal Java
     * identifier characters, otherwise the original word is returned
     */
    String replaceIfIllegalJavaIdentifierCharacter(final String word);

    /**
     * Returns the wrapper class name for primitive type names (e.g. Integer for
     * int) or else returns null.
     *
     * @param javaTypeName naming the primitive class
     * @return the wrapper class name for primitive type names (e.g. Integer for
     * int) or else returns null
     */
    String javaObjectName(final String javaTypeName);

    /**
     * Returns a String where the camel cased java name has been replaced with
     * an underscore separated String.
     *
     * @param javaName to convert
     * @return a String where the camel cased java name has been replaced with
     * an underscore separated String
     */
    String toUnderscoreSeparated(final String javaName);

    /**
     * Returns a String that is easy to read for a human.
     *
     * @param javaName to convert
     * @return a String that is easy to read for a human
     */
    static String toHumanReadable(String javaName) {
        requireNonNull(javaName);
        javaName = javaName.trim();
        if (javaName.startsWith("\"") && javaName.endsWith("\"")) {
            // Un-quote the name
            javaName = javaName.substring(1, javaName.length() - 1);
        }
        return Stream.of(
            javaName
                .replaceAll("([A-Z]+)", "_$1")
                .split("[^A-Za-z0-9]")
        )
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(String::toLowerCase)
            .map(Formatting::ucfirst)
            .collect(Collectors.joining(" "));
    }

    /**
     * Creates and returns a new standard implementation of a {@link JavaLanguageNamer}
     *
     * @return new {@link JavaLanguageNamer} with the given parameters
     */
    static JavaLanguageNamer create() {
        return new JavaLanguageNamerImpl();
    }
}
