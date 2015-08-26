/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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