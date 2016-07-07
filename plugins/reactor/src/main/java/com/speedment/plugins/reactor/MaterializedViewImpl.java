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
package com.speedment.plugins.reactor;

import com.speedment.runtime.field.ComparableField;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link MaterializedView} interface.
 * 
 * @param <ENTITY>  the entity type
 * @param <T>       the unique key type
 * 
 * @author  Emil Forslund
 * @since   1.1.0
 */
public class MaterializedViewImpl<ENTITY, T extends Comparable<T>> 
    implements MaterializedView<ENTITY, T> {
    
    private final ComparableField<ENTITY, ?, T> field;
    private final Map<T, ENTITY> view;
    
    /**
     * Creates a {@code MaterializedView} of the entities of a particular table. 
     * For the view to be able to distinguish which events are referring to the 
     * same object, a field identifier must be specified. Database rows that has 
     * the same value for this field will be considered the same entity and a
     * merge attempt will be done using the
     * {@link #merge(Object, Object)}-method.
     * 
     * @param field  the field to use as identifier
     */
    public MaterializedViewImpl(ComparableField<ENTITY, ?, T> field) {
        this.field = requireNonNull(field);
        this.view = new ConcurrentHashMap<>();
    }

    /**
     * Accepts a series of modifications into this view. This should only be 
     * called by the owner of the view, normally a {@link Reactor}. The events
     * given to this method is expected to be ordered chronologically.
     * 
     * @param events  the events to load
     */
    @Override
    public final void accept(List<ENTITY> events) {
        events.forEach(entity -> {
            view.compute(field.get(entity), 
                (key, existing) -> merge(existing, entity)
            );
        });
    }
    
    @Override
    public final Optional<ENTITY> get(T key) {
        return Optional.ofNullable(view.get(key));
    }
    
    @Override
    public final Stream<ENTITY> stream() {
        return view.values().stream();
    }
    
    /**
     * Merge the two specified entities into the view. The returned value could
     * be either one of the two or a completely new one. This method is 
     * guaranteed to be called in order, merging each entity with the 
     * immediately preceeding one.
     * <p>
     * If the returned value is {@code null}, the entity will be removed from
     * the view.
     * 
     * @param existing  the existing entity (can be null)
     * @param loaded    the loaded entity
     * @return          the one entity to remember in the view
     */
    protected ENTITY merge(ENTITY existing, ENTITY loaded) {
        return loaded;
    }
}