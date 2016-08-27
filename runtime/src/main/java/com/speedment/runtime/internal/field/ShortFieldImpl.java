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
import com.speedment.runtime.field.ShortField;
import com.speedment.runtime.field.method.ShortGetter;
import com.speedment.runtime.field.method.ShortSetter;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.internal.field.comparator.ShortFieldComparator;
import com.speedment.runtime.internal.field.comparator.ShortFieldComparatorImpl;
import com.speedment.runtime.internal.field.predicate.shorts.ShortBetweenPredicate;
import com.speedment.runtime.internal.field.predicate.shorts.ShortEqualPredicate;
import com.speedment.runtime.internal.field.predicate.shorts.ShortGreaterOrEqualPredicate;
import com.speedment.runtime.internal.field.predicate.shorts.ShortGreaterThanPredicate;
import com.speedment.runtime.internal.field.predicate.shorts.ShortInPredicate;
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
public final class ShortFieldImpl<ENTITY, D> implements ShortField<ENTITY, D> {
    
    private final FieldIdentifier<ENTITY> identifier;
    private final ShortGetter<ENTITY> getter;
    private final ShortSetter<ENTITY> setter;
    private final TypeMapper<D, Short> typeMapper;
    private final boolean unique;
    
    public ShortFieldImpl(FieldIdentifier<ENTITY> identifier, ShortGetter<ENTITY> getter, ShortSetter<ENTITY> setter, TypeMapper<D, Short> typeMapper, boolean unique) {
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
    public ShortSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public ShortGetter<ENTITY> getter() {
        return getter;
    }
    
    @Override
    public TypeMapper<D, Short> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public ShortFieldComparator<ENTITY, D> comparator() {
        return new ShortFieldComparatorImpl<>(this);
    }
    
    @Override
    public ShortFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public ShortFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Short value) {
        return new ShortEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Short value) {
        return new ShortGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Short value) {
        return new ShortGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Short start, Short end, Inclusion inclusion) {
        return new ShortBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Set<Short> set) {
        return new ShortInPredicate<>(this, set);
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Short value) {
        return new ShortEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Short value) {
        return new ShortGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Short value) {
        return new ShortGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Short start, Short end, Inclusion inclusion) {
        return new ShortBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Set<Short> set) {
        return new ShortInPredicate<>(this, set).negate();
    }
}