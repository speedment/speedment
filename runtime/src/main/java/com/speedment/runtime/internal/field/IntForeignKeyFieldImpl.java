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
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.IntForeignKeyField;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.Finder;
import com.speedment.runtime.field.method.IntGetter;
import com.speedment.runtime.field.method.IntSetter;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.internal.field.comparator.IntFieldComparator;
import com.speedment.runtime.internal.field.comparator.IntFieldComparatorImpl;
import com.speedment.runtime.internal.field.finder.FindFromInt;
import com.speedment.runtime.internal.field.predicate.ints.IntBetweenPredicate;
import com.speedment.runtime.internal.field.predicate.ints.IntEqualPredicate;
import com.speedment.runtime.internal.field.predicate.ints.IntGreaterOrEqualPredicate;
import com.speedment.runtime.internal.field.predicate.ints.IntGreaterThanPredicate;
import com.speedment.runtime.internal.field.predicate.ints.IntInPredicate;
import com.speedment.runtime.internal.field.streamer.BackwardFinderImpl;
import com.speedment.runtime.manager.Manager;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Generated;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY>    entity type
 * @param <D>         database type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class IntForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements IntField<ENTITY, D>, IntForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final FieldIdentifier<ENTITY> identifier;
    private final IntGetter<ENTITY> getter;
    private final IntSetter<ENTITY> setter;
    private final IntField<FK_ENTITY, ?> referenced;
    private final Finder<ENTITY, FK_ENTITY> finder;
    private final TypeMapper<D, Integer> typeMapper;
    private final boolean unique;
    
    public IntForeignKeyFieldImpl(FieldIdentifier<ENTITY> identifier, IntGetter<ENTITY> getter, IntSetter<ENTITY> setter, IntField<FK_ENTITY, ?> referenced, Finder<ENTITY, FK_ENTITY> finder, TypeMapper<D, Integer> typeMapper, boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.referenced = requireNonNull(referenced);
        this.finder     = requireNonNull(finder);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public FieldIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public IntSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public IntGetter<ENTITY> getter() {
        return getter;
    }
    
    @Override
    public IntField<FK_ENTITY, ?> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(Manager<ENTITY> manager) {
        return new BackwardFinderImpl<>(this, manager);
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(Manager<FK_ENTITY> foreignManager) {
        return new FindFromInt<>(this, referenced, foreignManager);
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
    public FieldPredicate<ENTITY> in(Set<Integer> set) {
        return new IntInPredicate<>(this, set);
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
    public Predicate<ENTITY> notIn(Set<Integer> set) {
        return new IntInPredicate<>(this, set).negate();
    }
}