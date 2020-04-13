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
package com.speedment.generator.translator.provider;

import com.speedment.common.function.CharUnaryOperator;
import com.speedment.generator.translator.internal.namer.JavaLanguageNamerImpl;
import com.speedment.generator.translator.namer.JavaLanguageNamer;

public final class StandardJavaLanguageNamer implements JavaLanguageNamer {

    private final JavaLanguageNamer inner;

    public StandardJavaLanguageNamer() {
        this.inner = new JavaLanguageNamerImpl();
    }

    @Override
    public String javaTypeName(String externalName) {
        return inner.javaTypeName(externalName);
    }

    @Override
    public String javaVariableName(String externalName) {
        return inner.javaVariableName(externalName);
    }

    @Override
    public String javaStaticFieldName(String externalName) {
        return inner.javaStaticFieldName(externalName);
    }

    @Override
    public String javaPackageName(String externalName) {
        return inner.javaPackageName(externalName);
    }

    @Override
    public String javaName(String externalName, CharUnaryOperator mutator) {
        return inner.javaName(externalName, mutator);
    }

    @Override
    public String javaNameFromExternal(String externalName) {
        return inner.javaNameFromExternal(externalName);
    }

    @Override
    public String nameFromExternal(String externalName) {
        return inner.nameFromExternal(externalName);
    }

    @Override
    public String replaceIfJavaUsedWord(String word) {
        return inner.replaceIfJavaUsedWord(word);
    }

    @Override
    public String replaceIfIllegalJavaIdentifierCharacter(String word) {
        return inner.replaceIfIllegalJavaIdentifierCharacter(word);
    }

    @Override
    public String javaObjectName(String javaTypeName) {
        return inner.javaObjectName(javaTypeName);
    }

    @Override
    public String toUnderscoreSeparated(String javaName) {
        return inner.toUnderscoreSeparated(javaName);
    }

    public static String toHumanReadable(String javaName) {
        return JavaLanguageNamer.toHumanReadable(javaName);
    }

    public static JavaLanguageNamer create() {
        return JavaLanguageNamer.create();
    }
}
