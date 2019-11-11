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
