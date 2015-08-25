package com.speedment.core.field;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @param <ENTITY> the entity
 */
public final class CombinedBaseFunction<ENTITY> extends BaseFunction<ENTITY> {

    private final List<Function<? super ENTITY, ? extends ENTITY>> functions;

    /**
     * Initialize this class.
     *
     * @param first   the first function to include
     * @param second  the second function to include
     */
    public CombinedBaseFunction(Function<? super ENTITY, ? extends ENTITY> first, Function<? super ENTITY, ? extends ENTITY> second) {
        functions = new ArrayList<>();
        add(requireNonNull(first));
        add(requireNonNull(second));
    }

    /**
     * Adds the provided Function to this CombinedBaseFunction.
     *
     * @param function  to add
     * @return          a reference to a CombinedFunction after the method has been applied
     */
    protected final CombinedBaseFunction<ENTITY> add(Function<? super ENTITY, ? extends ENTITY> function) {
        if (getClass().equals(function.getClass())) {
            @SuppressWarnings("unchecked")
            final CombinedBaseFunction<ENTITY> cbp = getClass().cast(function);
            cbp.stream().forEachOrdered(functions::add);
        } else {
            functions.add(function);
        }

        return this;
    }

    /**
     * Removes the provided Function from this CombinedBaseFunction.
     *
     * @param   function to remove
     * @return  a reference to a CombinedFunction after the method has been applied
     */
    protected CombinedBaseFunction<ENTITY> remove(Function<? super ENTITY, ? extends ENTITY> function) {
        functions.remove(function);
        return this;
    }

    /**
     * Creates and returns a {link Stream} of all functions that this CombinedBaseFunction holds.
     *
     * @return  a {link Stream} of all predicates that this CombinedBaseFunction holds
     */
    public Stream<Function<? super ENTITY, ? extends ENTITY>> stream() {
        return functions.stream();
    }

    /**
     * Returns the number of functions that this CombinedBaseFunction holds
     *
     * @return  the number of functions that this CombinedBaseFunction holds
     */
    public int size() {
        return functions.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ENTITY apply(ENTITY entity) {
        ENTITY current = entity;

        for (Function<? super ENTITY, ? extends ENTITY> function : functions) {
            current = function.apply(current);
        }

        return current;
    }
}