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
package com.speedment.generator.translator.internal.namer;

import static java.util.Objects.requireNonNull;

import com.speedment.common.codegen.util.Formatting;
import com.speedment.common.function.CharUnaryOperator;
import com.speedment.generator.translator.namer.JavaLanguageNamer;

/**
 *
 * @author Per Minborg
 */
public final class JavaLanguageNamerImpl implements JavaLanguageNamer {

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
        return Formatting.staticField(externalName);
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
    public String javaName(final String externalName, CharUnaryOperator mutator) {

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
            sb.replace(startIndex, startIndex + 1, String.valueOf(mutator.applyAsChar(sb.charAt(startIndex))));
        }
        return sb.toString();
    }

    @Override
    public String nameFromExternal(final String externalName) {
        requireNonNull(externalName);
        return Formatting.nameFromExternal(externalName);
    }

    @Override
    public String javaNameFromExternal(final String externalName) {
        requireNonNull(externalName);
        return Formatting.javaNameFromExternal(externalName);
    }

    @Override
    public String replaceIfJavaUsedWord(final String word) {
        requireNonNull(word);
        return Formatting.replaceIfJavaUsedWord(word);
    }

    @Override
    public String replaceIfIllegalJavaIdentifierCharacter(final String word) {
        requireNonNull(word);
        return Formatting.replaceIfIllegalJavaIdentifierCharacter(word);
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
        return Formatting.toUnderscoreSeparated(javaName);
    }
}
