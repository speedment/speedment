package com.speedment.codegen.model;

import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.codegen.model.modifier.Modifier_;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <M>
 */
public abstract class AbstractModifiableCodeModel<T extends AbstractModifiableCodeModel<T, M>, M extends Modifier_<M>> extends AbstractCodeModel<T> implements Modifiable<M> {

    @Override
    public T add(final M firstClassModifier_m, final M... restClassModifiers) {
        return add(firstClassModifier_m, restClassModifiers, (f, s) -> {
            getModifiers().add(f);
            Stream.of(s).forEach(getModifiers()::add);
        });
    }

    @Override
    public T set(final Set<M> newSet) {
        return set(newSet, s -> {
            getModifiers().clear();
            getModifiers().addAll(s);
        });
    }

    @Override
    public boolean is(M modifier) {
        return getModifiers().contains(modifier);
    }

}
