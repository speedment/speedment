package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum ConstructorModifier_ implements Modifier_<ConstructorModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE);

    private final static StaticSupport<ConstructorModifier_> staticSupport = new StaticSupport<>(values());

    private final int value;

    private ConstructorModifier_(int value) {
        this.value = Modifier_.requireInValues(value, Modifier.constructorModifiers());
    }

    @Override
    public int getValue() {
        return value;
    }

    public static ConstructorModifier_ by(final String text) {
        return staticSupport.by(text);
    }

    public static Set<ConstructorModifier_> of(final String text) {
        return staticSupport.of(text);
    }

    public static Set<ConstructorModifier_> of(final int code) {
        return staticSupport.of(code);
    }

    public static Set<ConstructorModifier_> of(final ConstructorModifier_... classModifiers) {
        return staticSupport.of(classModifiers);
    }

}
