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
package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.internal.field.comparator.NullOrder;
import com.speedment.runtime.internal.field.comparator.ReferenceFieldComparatorImpl;
import com.speedment.runtime.internal.field.predicate.reference.ReferenceBetweenPredicate;
import com.speedment.runtime.internal.field.predicate.reference.ReferenceEqualPredicate;
import com.speedment.runtime.internal.field.predicate.reference.ReferenceGreaterOrEqualPredicate;
import com.speedment.runtime.internal.field.predicate.reference.ReferenceGreaterThanPredicate;
import com.speedment.runtime.internal.field.predicate.reference.ReferenceInPredicate;
import com.speedment.runtime.internal.field.predicate.reference.ReferenceIsNullPredicate;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import com.speedment.runtime.field.predicate.FieldPredicate;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * @param <V>      the field value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ComparableFieldImpl<ENTITY, D, V extends Comparable<? super V>> 
implements ComparableField<ENTITY, D, V> {

    private final FieldIdentifier<ENTITY> identifier;
    private final ReferenceGetter<ENTITY, V> getter;
    private final ReferenceSetter<ENTITY, V> setter;
    private final TypeMapper<D, V> typeMapper;
    private final boolean unique;

    public ComparableFieldImpl(
            FieldIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, V> getter,
            ReferenceSetter<ENTITY, V> setter,
            TypeMapper<D, V> typeMapper,
            boolean unique) {
        
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    /*****************************************************************/
    /*                           Getters                             */
    /*****************************************************************/

    @Override
    public FieldIdentifier<ENTITY> identifier() {
        return identifier;
    }

    @Override
    public ReferenceSetter<ENTITY, V> setter() {
        return setter;
    }

    @Override
    public ReferenceGetter<ENTITY, V> getter() {
        return getter;
    }

    @Override
    public TypeMapper<D, V> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    /*****************************************************************/
    /*                         Comparators                           */
    /*****************************************************************/
    
    @Override
    public Comparator<ENTITY> comparator() {
        return new ReferenceFieldComparatorImpl<>(this, NullOrder.NONE);
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsFirst() {
        return new ReferenceFieldComparatorImpl<>(this, NullOrder.FIRST);
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsLast() {
        return new ReferenceFieldComparatorImpl<>(this, NullOrder.LAST);
    }
    
    /*****************************************************************/
    /*                           Operators                           */
    /*****************************************************************/

    @Override
    public FieldPredicate<ENTITY> isNull() {
        return new ReferenceIsNullPredicate<>(this);
    }

    @Override
    public FieldPredicate<ENTITY> equal(V value) {
        return new ReferenceEqualPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> greaterThan(V value) {
        return new ReferenceGreaterThanPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> greaterOrEqual(V value) {
        return new ReferenceGreaterOrEqualPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> between(V start, V end, Inclusion inclusion) {
        return new ReferenceBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public Predicate<ENTITY> in(Set<V> values) {
        return new ReferenceInPredicate<>(this, values);
    }
}