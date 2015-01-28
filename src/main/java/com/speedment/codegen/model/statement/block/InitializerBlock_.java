package com.speedment.codegen.model.statement.block;

import com.speedment.codegen.model.modifier.InitializerModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class InitializerBlock_ extends Block_<InitializerBlock_> implements Modifiable<InitializerModifier_> {

    private final Set<InitializerModifier_> modifiers;

    public InitializerBlock_() {
        super();
        this.modifiers = EnumSet.noneOf(InitializerModifier_.class);
    }

    @Override
    public Set<InitializerModifier_> getModifiers() {
        return modifiers;
    }

    @Override
    public InitializerBlock_ add(final InitializerModifier_ firstClassModifier_m, final InitializerModifier_... restClassModifiers) {
        return add(firstClassModifier_m, restClassModifiers, (f, r) -> {
            getModifiers().add(firstClassModifier_m);
            Stream.of(restClassModifiers).forEach(getModifiers()::add);
        });
    }

    @Override
    public InitializerBlock_ set(final Set<InitializerModifier_> newSet) {
        return set(newSet, s -> {
            getModifiers().clear();
            getModifiers().addAll(newSet);
        });
    }

    @Override
    public boolean is(InitializerModifier_ modifier) {
        return getModifiers().contains(modifier);
    }

    public InitializerBlock_ static_() {
        return add(InitializerModifier_.STATIC, getModifiers()::add);
    }

}
