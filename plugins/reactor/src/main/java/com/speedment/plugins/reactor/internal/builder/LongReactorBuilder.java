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

import com.speedment.runtime.field.LongField;
import com.speedment.runtime.manager.Manager;
import java.util.Comparator;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

/**
 *
 * A specialized reactor builder for tables that have a primitive
 * long as primary key.
 * 
 * @param <ENTITY>  the entity type to react on
 * @param <T>       the primary key of the entity table
 * 
 * @author  Emil Forslund
 * @since   1.1.0
 */
public final class LongReactorBuilder<ENTITY, T extends Comparable<T>> 
        extends AbstractReactorBuilder<ENTITY, T, AtomicLong> {

    private final LongField<ENTITY, ?> idField;

    /**
     * Initiates the builder with default values.
     * 
     * @param manager  the manager to use for database polling
     * @param idField  the field that identifier which entity is refered to 
     *                 in the event
     */
    public LongReactorBuilder(
            Manager<ENTITY> manager, 
            LongField<ENTITY, ?> idField) {
        
        super(manager);
        this.idField = requireNonNull(idField);
    }

    @Override
    protected String fieldName() {
        return idField.identifier().columnName();
    }

    @Override
    protected Predicate<ENTITY> idPredicate(AtomicLong lastId) {
        return idField.greaterThan(lastId.get());
    }

    @Override
    protected Comparator<ENTITY> idComparator() {
        return idField.comparator();
    }

    @Override
    protected AtomicLong idHolder() {
        return new AtomicLong(Long.MIN_VALUE);
    }

    @Override
    protected void bumpLatestId(AtomicLong idHolder, ENTITY lastEntity) {
        idHolder.set(idField.getAsLong(lastEntity));
    }

    @Override
    protected String idToString(AtomicLong idHolder) {
        return Long.toString(idHolder.get());
    }
}