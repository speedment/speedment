package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum ClassModifier_ implements Modifier_<ClassModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE),
    ABSTRACT(Modifier.ABSTRACT),
    STATIC(Modifier.STATIC),
    FINAL(Modifier.FINAL),
    STRICTFP(Modifier.STRICT);

    private final static StaticSupport<ClassModifier_> staticSupport = new StaticSupport<>(values());

    private final int value;

    private ClassModifier_(int value) {
        this.value = Modifier_.requireInValues(value, Modifier.classModifiers());
    }

    @Override
    public int getValue() {
        return value;
    }

    public static ClassModifier_ byName(final String text) {
        return staticSupport.byName(text);
    }

    public static Set<ClassModifier_> byText(final String text) {
        return staticSupport.byText(text);
    }

    public static Set<ClassModifier_> byCode(final int code) {
        return staticSupport.byCode(code);
    }

    public static Set<ClassModifier_> of(final ClassModifier_... classModifiers) {
        return staticSupport.of(classModifiers);
    }

}
