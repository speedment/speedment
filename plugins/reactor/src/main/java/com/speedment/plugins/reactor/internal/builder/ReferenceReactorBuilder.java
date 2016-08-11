/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.plugins.reactor.internal.builder;

import com.speedment.plugins.reactor.Reactor;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.internal.field.predicate.AlwaysTruePredicate;
import com.speedment.runtime.manager.Manager;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 * Builder class for creating new {@link Reactor} instances with reference
 * type comparison keys.
 * 
 * @param <ENTITY>  the entity type to react on
 * @param <T>       the primary key of the entity table
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ReferenceReactorBuilder<ENTITY, T extends Comparable<T>> 
        extends AbstractReactorBuilder<ENTITY, T, AtomicReference<T>> {

    private final ComparableField<ENTITY, ?, T> idField;

    /**
     * Initiates the builder with default values.
     * 
     * @param manager  the manager to use for database polling
     * @param idField  the field that identifier which entity is refered to 
     *                 in the event
     */
    public ReferenceReactorBuilder(
            Manager<ENTITY> manager, 
            ComparableField<ENTITY, ?, T> idField) {
        
        super(manager);
        this.idField = requireNonNull(idField);
    }

    @Override
    protected String fieldName() {
        return idField.identifier().columnName();
    }

    @Override
    protected Predicate<ENTITY> idPredicate(AtomicReference<T> lastId) {
        final T current = lastId.get();
        if (current == null) {
            return new AlwaysTruePredicate<>(idField);
        } else {
            return idField.greaterThan(current);
        }
    }

    @Override
    protected Comparator<ENTITY> idComparator() {
        return idField.comparator();
    }

    @Override
    protected AtomicReference<T> idHolder() {
        return new AtomicReference<>(null);
    }

    @Override
    protected void bumpLatestId(AtomicReference<T> idHolder, ENTITY lastEntity) {
        idHolder.set(idField.get(lastEntity));
    }

    @Override
    protected String idToString(AtomicReference<T> idHolder) {
        return Objects.toString(idHolder.get());
    }
}