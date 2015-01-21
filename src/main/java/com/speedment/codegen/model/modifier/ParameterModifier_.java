package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum ParameterModifier_ implements Modifier_<ParameterModifier_> {

    FINAL(Modifier.FINAL);

    private final static StaticSupport<ParameterModifier_> staticSupport = new StaticSupport<>(values());

    private final int value;

    private ParameterModifier_(int value) {
        this.value = Modifier_.requireInValues(value, Modifier.parameterModifiers());
    }

    @Override
    public int getValue() {
        return value;
    }

    public static ParameterModifier_ byName(final String text) {
        return staticSupport.byName(text);
    }

    public static Set<ParameterModifier_> byText(final String text) {
        return staticSupport.byText(text);
    }

    public static Set<ParameterModifier_> byCode(final int code) {
        return staticSupport.byCode(code);
    }

    public static Set<ParameterModifier_> of(final ParameterModifier_... classModifiers) {
        return staticSupport.of(classModifiers);
    }

}
