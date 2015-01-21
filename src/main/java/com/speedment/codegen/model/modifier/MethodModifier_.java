package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum MethodModifier_ implements Modifier_<MethodModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE),
    ABSTRACT(Modifier.ABSTRACT),
    STATIC(Modifier.STATIC),
    FINAL(Modifier.FINAL),
    SYNCHRONIZED(Modifier.SYNCHRONIZED),
    NATIVE(Modifier.NATIVE),
    STRICTFP(Modifier.STRICT);

    private final static StaticSupport<MethodModifier_> staticSupport = new StaticSupport<>(values());

    private final int value;

    private MethodModifier_(int value) {
        this.value = Modifier_.requireInValues(value, Modifier.methodModifiers());
    }

    @Override
    public int getValue() {
        return value;
    }

    public static MethodModifier_ by(final String text) {
        return staticSupport.by(text);
    }

    public static Set<MethodModifier_> of(final String text) {
        return staticSupport.of(text);
    }

    public static Set<MethodModifier_> of(final int code) {
        return staticSupport.of(code);
    }

    public static Set<MethodModifier_> of(final MethodModifier_... classModifiers) {
        return staticSupport.of(classModifiers);
    }

}
