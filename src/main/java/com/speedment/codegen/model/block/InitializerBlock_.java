package com.speedment.codegen.model.block;

import com.speedment.codegen.model.field.Field_;
import com.speedment.codegen.model.modifier.FieldModifier_;
import com.speedment.codegen.model.modifier.InitializerModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class InitializerBlock_ extends Block_ implements Modifiable<InitializerModifier_> {

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
        getModifiers().add(firstClassModifier_m);
        Stream.of(restClassModifiers).forEach(getModifiers()::add);
        return this;
    }

    @Override
    public InitializerBlock_ set(final Set<InitializerModifier_> newSet) {
        getModifiers().clear();
        getModifiers().addAll(newSet);
        return this;
    }

    @Override
    public boolean is(InitializerModifier_ modifier) {
        return modifiers.contains(modifier);
    }

    public InitializerBlock_ static_() {
        modifiers.add(InitializerModifier_.STATIC);
        return this;
    }

}
