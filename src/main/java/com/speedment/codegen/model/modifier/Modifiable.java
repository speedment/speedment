package com.speedment.codegen.model.modifier;

import java.util.Set;

/**
 *
 * @author pemi
 * @param <M>
 */
public interface Modifiable<M extends Modifier_<M>> {

    public Set<M> getModifiers();

    public boolean is(M modifier);

    public Modifiable<M> add(final M firstModifier, final M... restModifiers);

    public Modifiable<M> set(final Set<M> newSet);

}
