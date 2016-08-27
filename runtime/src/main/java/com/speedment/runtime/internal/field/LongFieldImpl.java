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
import com.speedment.runtime.field.LongField;
import com.speedment.runtime.field.method.LongGetter;
import com.speedment.runtime.field.method.LongSetter;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.internal.field.comparator.LongFieldComparator;
import com.speedment.runtime.internal.field.comparator.LongFieldComparatorImpl;
import com.speedment.runtime.internal.field.predicate.longs.LongBetweenPredicate;
import com.speedment.runtime.internal.field.predicate.longs.LongEqualPredicate;
import com.speedment.runtime.internal.field.predicate.longs.LongGreaterOrEqualPredicate;
import com.speedment.runtime.internal.field.predicate.longs.LongGreaterThanPredicate;
import com.speedment.runtime.internal.field.predicate.longs.LongInPredicate;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated
public final class LongFieldImpl<ENTITY, D> implements LongField<ENTITY, D> {
    
    private final FieldIdentifier<ENTITY> identifier;
    private final LongGetter<ENTITY> getter;
    private final LongSetter<ENTITY> setter;
    private final TypeMapper<D, Long> typeMapper;
    private final boolean unique;
    
    public LongFieldImpl(FieldIdentifier<ENTITY> identifier, LongGetter<ENTITY> getter, LongSetter<ENTITY> setter, TypeMapper<D, Long> typeMapper, boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public FieldIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public LongSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public LongGetter<ENTITY> getter() {
        return getter;
    }
    
    @Override
    public TypeMapper<D, Long> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public LongFieldComparator<ENTITY, D> comparator() {
        return new LongFieldComparatorImpl<>(this);
    }
    
    @Override
    public LongFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public LongFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Long value) {
        return new LongEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Long value) {
        return new LongGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Long value) {
        return new LongGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Long start, Long end, Inclusion inclusion) {
        return new LongBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Set<Long> set) {
        return new LongInPredicate<>(this, set);
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Long value) {
        return new LongEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Long value) {
        return new LongGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Long value) {
        return new LongGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Long start, Long end, Inclusion inclusion) {
        return new LongBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Set<Long> set) {
        return new LongInPredicate<>(this, set).negate();
    }
}