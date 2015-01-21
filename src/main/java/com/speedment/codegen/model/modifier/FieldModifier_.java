package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum FieldModifier_ implements Modifier_<FieldModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE),
    STATIC(Modifier.STATIC),
    FINAL(Modifier.FINAL),
    TRANSIENT(Modifier.TRANSIENT),
    VOLATILE(Modifier.VOLATILE);

    private final static StaticSupport<FieldModifier_> staticSupport = new StaticSupport<>(values());

    private final int value;

    private FieldModifier_(int value) {
        this.value = Modifier_.requireInValues(value, Modifier.fieldModifiers());
    }

    @Override
    public int getValue() {
        return value;
    }

    public static FieldModifier_ by(final String text) {
        return staticSupport.by(text);
    }

    public static Set<FieldModifier_> of(final String text) {
        return staticSupport.of(text);
    }

    public static Set<FieldModifier_> of(final int code) {
        return staticSupport.of(code);
    }

    public static Set<FieldModifier_> of(final FieldModifier_... classModifiers) {
        return staticSupport.of(classModifiers);
    }

}
