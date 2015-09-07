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
package com.speedment.internal.core.manager.memory;

import com.speedment.exception.SpeedmentException;
import com.speedment.field.ReferenceComparableField;
import com.speedment.field.operators.StandardComparableOperator;
import com.speedment.internal.core.stream.MapStream;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

/**
 * A store for a particular type of entity. The store can be used to retreive 
 * subsets of all entities based on a standard operator. For an example, the
 * subset of all entities that have a value 'id' greater than 100 or the
 * subset of all entities with the value 'date' lesser than '2015-01-01'.
 * <p>
 * Retreiving a subset is an O(1) operator and results in a {@code Stream}. The
 * stream can then be traversed in linear time. It is therefore recommended to
 * retreive the smallest subset first and apply less specific filters later on.
 * <p>
 * A store can be modified using the {@link #add(java.lang.Object)} and 
 * {@link #remove(java.lang.Object)} methods. The current implementation of 
 * these methods <b>are not thread safe</b>!
 * 
 * @author Emil
 * @param <ENTITY>  the type of entity to store
 */
public class OnHeapStore<ENTITY> {
    
    private final Map<ReferenceComparableField<ENTITY, ? extends Comparable<?>>, 
        SortedMap<? extends Comparable<?>, Set<ENTITY>>> stores;
    
    /**
     * Constructs the store and populates it with all the static fields of type
     * {@link ReferenceComparableField} found in the specified class.
     * 
     * @param entityClass  the entity class this store is for
     */
    public OnHeapStore(Class<ENTITY> entityClass) {
        requireNonNull(entityClass);
        stores = new ConcurrentHashMap<>();
        populateByReflection(entityClass);
    }
    
    /**
     * Adds the specified entity to the store.
     * <p>
     * Observe that this method is currently <b>not threadsafe!</b>
     * 
     * @param entity  the entity to add 
     */
    public void add(ENTITY entity) {
        requireNonNull(entity);
        MapStream.of(stores)
            .forEach((field, store) -> addEntity(field, store, entity));
    }
    
    /**
     * Removed the specified entity from the store.
     * <p>
     * Observe that this method is currently <b>not threadsafe!</b>
     * 
     * @param entity  the entity to remove.
     */
    public void remove(ENTITY entity) {
        requireNonNull(entity);
        MapStream.of(stores)
            .forEach((field, store) -> removeEntity(field, store, entity));
    }

    /**
     * Returns a stream of subsets of this store based on the specified field,
     * operator and value. If no entities match, an empty stream will be
     * returned. None of the in parameters can be {@code null}.
     * 
     * @param <V>       the type of the value
     * @param field     the field to compare
     * @param operator  the operator to use for comparing
     * @param value     the value to compare with
     * @return          a stream of matching entities
     */
    public <V extends Comparable<V>> Stream<ENTITY> where(
        ReferenceComparableField<ENTITY, V> field, 
        StandardComparableOperator operator, 
        V value) {
        
        requireNonNulls(field, operator, value);

        // This cast should be safe as long as the map has not been manipulated 
        // outside this class. The store should not be null as long as the field
        // is a valid field retreived by reflection.
        final SortedMap<V, Set<ENTITY>> store = 
            (SortedMap<V, Set<ENTITY>>) stores.get(field);

        switch (operator) {
            case EQUAL : 
                return fromStore(store, value).stream();
                
            case NOT_EQUAL :
                return MapStream.of(store)
                    .filter((k, v) -> !Objects.equals(k, value))
                    .flatMapValue((k, set) -> set.stream())
                    .values();
                
            case GREATER_THAN :
                return MapStream.of(store.tailMap(value))
                    .flatMapValue((k, set) -> set.stream())
                    .values();
                
            case GREATER_OR_EQUAL :
                return Stream.concat(
                    fromStore(store, value).stream(), 
                    MapStream.of(store.tailMap(value))
                        .flatMapValue((k, set) -> set.stream())
                        .values()
                );
                
            case LESS_THAN :
                return MapStream.of(store.headMap(value))
                    .flatMapValue((k, set) -> set.stream())
                    .values();
                
            case LESS_OR_EQUAL :
                return Stream.concat(
                    fromStore(store, value).stream(), 
                    MapStream.of(store.headMap(value))
                        .flatMapValue((k, set) -> set.stream())
                        .values()
                );
                
            default : throw new UnsupportedOperationException(
                "Unknown standard comparable operator '" + operator + "'."
            );
        }
    }
    
    private void populateByReflection(Class<ENTITY> entityClass) {
        Stream.of(entityClass.getDeclaredFields())
            .filter(f -> f.isAccessible())
            .filter(f -> Modifier.isStatic(f.getModifiers()))
            .filter(f -> ReferenceComparableField.class.isAssignableFrom(f.getType()))
            .map(f -> {
                try {
                    return f.get(null);
                } catch (IllegalArgumentException ex) {
                    // Should never be thrown as the filter above checks if static
                    throw new SpeedmentException("Specified field is not static.", ex);
                } catch (IllegalAccessException ex) {
                    // Should never be thrown as the filter above checks if accessible
                    throw new SpeedmentException("Could not access field.", ex);
                }
            })
            
            // Should be safe to throw as the following has been assured:
            //     (i)   f is a ReferenceComparableField
            //     (ii)  The declared class is ENTITY
            //     (iii) Only comparable values can be type params of 
            //           ReferenceComparableField
            .map(f -> (ReferenceComparableField<ENTITY, ? extends Comparable<?>>) f)
            
            .forEach(f -> stores.put(f, new ConcurrentSkipListMap<>()));
    }
    
    private static <ENTITY, V extends Comparable<V>> void addEntity(
        ReferenceComparableField<ENTITY, ? extends Comparable<?>> field, 
        SortedMap<? extends Comparable<?>, Set<ENTITY>> store,
        ENTITY entity) {
        
        final V value = (V) field.get(entity);
        final SortedMap<V, Set<ENTITY>> typedStore = (SortedMap<V, Set<ENTITY>>) store;
        
        fromStore(typedStore, value).add(entity);
    }
    
    private static <ENTITY, V extends Comparable<V>> void removeEntity(
        ReferenceComparableField<ENTITY, ? extends Comparable<?>> field,
        SortedMap<? extends Comparable<?>, Set<ENTITY>> store,
        ENTITY entity) {
        
        final V value = (V) field.get(entity);
        final SortedMap<V, Set<ENTITY>> typedStore = (SortedMap<V, Set<ENTITY>>) store;
        
        fromStore(typedStore, value).remove(entity);
    }
    
    private static <ENTITY, V extends Comparable<V>> Set<ENTITY> fromStore(SortedMap<V, Set<ENTITY>> store, V value) {
        return store.computeIfAbsent(value, v -> new HashSet<>());
    }
}
