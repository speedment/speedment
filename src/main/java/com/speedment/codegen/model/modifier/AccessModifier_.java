package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum AccessModifier_ implements Modifier_<AccessModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE);

    private final static StaticSupport<AccessModifier_> staticSupport = new StaticSupport<>(values());

    private final int value;

    private AccessModifier_(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static AccessModifier_ by(final String text) {
        return staticSupport.by(text);
    }

    public static Set<AccessModifier_> of(final String text) {
        return staticSupport.of(text);
    }

    public static Set<AccessModifier_> of(final int code) {
        return staticSupport.of(code);
    }

    public static Set<AccessModifier_> of(final AccessModifier_... classModifiers) {
        return staticSupport.of(classModifiers);
    }

}
