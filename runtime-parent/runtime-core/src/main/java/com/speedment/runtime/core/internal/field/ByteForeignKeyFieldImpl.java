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
package com.speedment.runtime.core.internal.field;

import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.core.field.ByteField;
import com.speedment.runtime.core.field.ByteForeignKeyField;
import com.speedment.runtime.core.field.method.BackwardFinder;
import com.speedment.runtime.core.field.method.ByteGetter;
import com.speedment.runtime.core.field.method.ByteSetter;
import com.speedment.runtime.core.field.method.FindFrom;
import com.speedment.runtime.core.field.method.Finder;
import com.speedment.runtime.core.field.predicate.FieldPredicate;
import com.speedment.runtime.core.field.predicate.Inclusion;
import com.speedment.runtime.core.internal.field.comparator.ByteFieldComparator;
import com.speedment.runtime.core.internal.field.comparator.ByteFieldComparatorImpl;
import com.speedment.runtime.core.internal.field.finder.FindFromByte;
import com.speedment.runtime.core.internal.field.predicate.bytes.ByteBetweenPredicate;
import com.speedment.runtime.core.internal.field.predicate.bytes.ByteEqualPredicate;
import com.speedment.runtime.core.internal.field.predicate.bytes.ByteGreaterOrEqualPredicate;
import com.speedment.runtime.core.internal.field.predicate.bytes.ByteGreaterThanPredicate;
import com.speedment.runtime.core.internal.field.predicate.bytes.ByteInPredicate;
import com.speedment.runtime.core.internal.field.streamer.BackwardFinderImpl;
import com.speedment.runtime.core.manager.Manager;

import javax.annotation.Generated;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
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
public final class ByteForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements ByteField<ENTITY, D>, ByteForeignKeyField<ENTITY, D, FK_ENTITY> {
    
    private final ColumnIdentifier<ENTITY> identifier;
    private final ByteGetter<ENTITY> getter;
    private final ByteSetter<ENTITY> setter;
    private final ByteField<FK_ENTITY, ?> referenced;
    private final Finder<ENTITY, FK_ENTITY> finder;
    private final TypeMapper<D, Byte> typeMapper;
    private final boolean unique;
    
    public ByteForeignKeyFieldImpl(ColumnIdentifier<ENTITY> identifier, ByteGetter<ENTITY> getter, ByteSetter<ENTITY> setter, ByteField<FK_ENTITY, ?> referenced, Finder<ENTITY, FK_ENTITY> finder, TypeMapper<D, Byte> typeMapper, boolean unique) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.referenced = requireNonNull(referenced);
        this.finder     = requireNonNull(finder);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
    }
    
    @Override
    public ColumnIdentifier<ENTITY> identifier() {
        return identifier;
    }
    
    @Override
    public ByteSetter<ENTITY> setter() {
        return setter;
    }
    
    @Override
    public ByteGetter<ENTITY> getter() {
        return getter;
    }
    
    @Override
    public ByteField<FK_ENTITY, ?> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(Manager<ENTITY> manager) {
        return new BackwardFinderImpl<>(this, manager);
    }
    
    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(Manager<FK_ENTITY> foreignManager) {
        return new FindFromByte<>(this, referenced, foreignManager);
    }
    
    @Override
    public TypeMapper<D, Byte> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparator() {
        return new ByteFieldComparatorImpl<>(this);
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparatorNullFieldsFirst() {
        return comparator();
    }
    
    @Override
    public ByteFieldComparator<ENTITY, D> comparatorNullFieldsLast() {
        return comparator();
    }
    
    @Override
    public FieldPredicate<ENTITY> equal(Byte value) {
        return new ByteEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterThan(Byte value) {
        return new ByteGreaterThanPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> greaterOrEqual(Byte value) {
        return new ByteGreaterOrEqualPredicate<>(this, value);
    }
    
    @Override
    public FieldPredicate<ENTITY> between(Byte start, Byte end, Inclusion inclusion) {
        return new ByteBetweenPredicate<>(this, start, end, inclusion);
    }
    
    @Override
    public FieldPredicate<ENTITY> in(Set<Byte> set) {
        return new ByteInPredicate<>(this, set);
    }
    
    @Override
    public Predicate<ENTITY> notEqual(Byte value) {
        return new ByteEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessOrEqual(Byte value) {
        return new ByteGreaterThanPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> lessThan(Byte value) {
        return new ByteGreaterOrEqualPredicate<>(this, value).negate();
    }
    
    @Override
    public Predicate<ENTITY> notBetween(Byte start, Byte end, Inclusion inclusion) {
        return new ByteBetweenPredicate<>(this, start, end, inclusion).negate();
    }
    
    @Override
    public Predicate<ENTITY> notIn(Set<Byte> set) {
        return new ByteInPredicate<>(this, set).negate();
    }
}