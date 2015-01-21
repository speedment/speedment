package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum InterfaceModifier_ implements Modifier_<InterfaceModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE),
    ABSTRACT(Modifier.ABSTRACT),
    STATIC(Modifier.STATIC),
    STRICTFP(Modifier.STRICT);

    private final static StaticSupport<InterfaceModifier_> support = new StaticSupport<>(values());

    private final int value;

    private InterfaceModifier_(int value) {
        this.value = Modifier_.requireInValues(value, Modifier.interfaceModifiers());
    }

    @Override
    public int getValue() {
        return value;
    }

    public static InterfaceModifier_ byName(final String text) {
        return support.byName(text);
    }

    public static Set<InterfaceModifier_> byText(final String text) {
        return support.byText(text);
    }

    public static Set<InterfaceModifier_> byCode(final int code) {
        return support.byCode(code);
    }

    public static Set<InterfaceModifier_> of(final InterfaceModifier_... classModifiers) {
        return support.of(classModifiers);
    }

}
