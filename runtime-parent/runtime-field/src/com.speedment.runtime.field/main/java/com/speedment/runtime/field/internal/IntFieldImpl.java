/**
 * 
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.field.internal;

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.internal.comparator.IntFieldComparator;
import com.speedment.runtime.field.internal.comparator.IntFieldComparatorImpl;
import com.speedment.runtime.field.internal.method.GetIntImpl;
import com.speedment.runtime.field.internal.predicate.ints.IntBetweenPredicate;
import com.speedment.runtime.field.internal.predicate.ints.IntEqualPredicate;
import com.speedment.runtime.field.internal.predicate.ints.IntGreaterOrEqualPredicate;
import com.speedment.runtime.field.internal.predicate.ints.IntGreaterThanPredicate;
import com.speedment.runtime.field.internal.predicate.ints.IntInPredicate;
import com.speedment.runtime.field.method.GetInt;
import com.speedment.runtime.field.method.IntGetter;
import com.speedment.runtime.field.method.IntSetter;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.typemapper.TypeMapper;
import java.util.Collection;
import java.util.function.Predicate;
import static com.speedment.runtime.field.internal.util.CollectionUtil.collectionToSet;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class IntFieldImpl<ENTITY, D> implements IntField<ENTITY, D> {
    
    private final ColumnIdentifier<ENTITY> identifier;
    private final GetInt<ENTITY, D> getter;
    private final IntSetter<ENTITY> setter;
    private final TypeMapper<D, Integer> typeMapper;
    private final boolean unique;
    
    public IntFieldImpl(
            ColumnIdentifier<ENTITY> identifier,
            IntGetter<ENTITY> getter,
            IntSetter<ENTITY> setter,
            TypeMapper<D, Integer> typeMapper,
            boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = new GetIntImpl<>(this, getter);
        this.setter     = requireNonNull(setter);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public ColumnIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public IntSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public GetInt<ENTITY, D> getter() {
        return getter;
    }
    
    @Override
    public TypeMapper<D, Integer> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public IntFieldComparator<ENTITY, D> comparator() {
        return new IntFieldComparatorImpl<>(this);
    }
    
    @Override
    public IntFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public IntFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Integer value) {
        return new IntEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Integer value) {
        return new IntGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Integer value) {
        return new IntGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Integer start, Integer end, Inclusion inclusion) {
        return new IntBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Collection<Integer> values) {
        return new IntInPredicate<>(this, collectionToSet(values));
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Integer value) {
        return new IntEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Integer value) {
        return new IntGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Integer value) {
        return new IntGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Integer start, Integer end, Inclusion inclusion) {
        return new IntBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Collection<Integer> values) {
        return new IntInPredicate<>(this, collectionToSet(values)).negate();
    }
}