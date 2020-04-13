/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.ComparableForeignKeyField;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.field.internal.comparator.ReferenceFieldComparatorImpl;
import com.speedment.runtime.field.internal.method.BackwardFinderImpl;
import com.speedment.runtime.field.internal.method.FindFromNullableReference;
import com.speedment.runtime.field.internal.method.FindFromReference;
import com.speedment.runtime.field.internal.predicate.reference.*;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.FindFromNullable;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.speedment.runtime.field.internal.util.CollectionUtil.collectionToSet;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY>     the entity type
 * @param <D>          the database type
 * @param <V>          the field type
 * @param <FK_ENTITY>  the foreign entity type
 * 
 * @author  Emil Forslund
 * @author  Per Minborg
 * 
 * @since  2.2.0
 */
public final class ComparableForeignKeyFieldImpl
    <ENTITY, D, V extends Comparable<? super V>, FK_ENTITY>
implements ComparableForeignKeyField<ENTITY, D, V, FK_ENTITY>,
           FieldComparator<ENTITY> {

    private final ColumnIdentifier<ENTITY> identifier;
    private final ReferenceGetter<ENTITY, V> getter;
    private final ReferenceSetter<ENTITY, V> setter;
    private final HasComparableOperators<FK_ENTITY, V> referenced;
    private final TypeMapper<D, V> typeMapper;
    private final boolean unique;
    private final String tableAlias;

    public ComparableForeignKeyFieldImpl(
        final ColumnIdentifier<ENTITY> identifier,
        final ReferenceGetter<ENTITY, V> getter,
        final ReferenceSetter<ENTITY, V> setter,
        final HasComparableOperators<FK_ENTITY, V> referenced,
        final TypeMapper<D, V> typeMapper,
        final boolean unique
    ) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.referenced = requireNonNull(referenced);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
        this.tableAlias = identifier.getTableId();
    }

    private ComparableForeignKeyFieldImpl(
        final ColumnIdentifier<ENTITY> identifier,
        final ReferenceGetter<ENTITY, V> getter,
        final ReferenceSetter<ENTITY, V> setter,
        final HasComparableOperators<FK_ENTITY, V> referenced,
        final TypeMapper<D, V> typeMapper,
        final boolean unique,
        final String tableAlias
    ) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.referenced = requireNonNull(referenced);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
        this.tableAlias = requireNonNull(tableAlias);
    }


    ////////////////////////////////////////////////////////////////////////////
    //                                Getters                                 //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public ColumnIdentifier<ENTITY> identifier() {
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
    public HasComparableOperators<FK_ENTITY, V> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(
            TableIdentifier<ENTITY> identifier,
            Supplier<Stream<ENTITY>> streamSupplier) {

        return new BackwardFinderImpl<>(this, identifier, streamSupplier);
    }

    @Override
    public FindFromNullable<ENTITY, FK_ENTITY> nullableFinder(
            TableIdentifier<FK_ENTITY> identifier,
            Supplier<Stream<FK_ENTITY>> streamSupplier) {

        return new FindFromNullableReference<>(
            this, referenced, identifier, streamSupplier
        );
    }

    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(
            TableIdentifier<FK_ENTITY> identifier,
            Supplier<Stream<FK_ENTITY>> streamSupplier) {

        return new FindFromReference<>(
            this, referenced, identifier, streamSupplier
        );
    }

    @Override
    public TypeMapper<D, V> typeMapper() {
        return typeMapper;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }


    @Override
    public String tableAlias() {
        return tableAlias;
    }

    @Override
    public ComparableForeignKeyField<ENTITY, D, V, FK_ENTITY> tableAlias(String tableAlias) {
        return new ComparableForeignKeyFieldImpl<>(identifier, getter, setter, referenced, typeMapper, unique, tableAlias);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                              Comparators                               //
    ////////////////////////////////////////////////////////////////////////////
    
    @Override
    public FieldComparator<ENTITY> comparator() {
        return new ReferenceFieldComparatorImpl<>(this, NullOrder.LAST);
    }

    @Override
    public FieldComparator<ENTITY> comparatorNullFieldsFirst() {
        return new ReferenceFieldComparatorImpl<>(this, NullOrder.FIRST);
    }

    @Override
    public Field<ENTITY> getField() {
        return this;
    }

    @Override
    public NullOrder getNullOrder() {
        return NullOrder.LAST;
    }

    @Override
    public boolean isReversed() {
        return false;
    }

    @Override
    public FieldComparator<ENTITY> reversed() {
        return comparator().reversed();
    }

    @Override
    public int compare(ENTITY first, ENTITY second) {
        final V f = get(first);
        final V s = get(second);
        if (f == null && s == null) return 0;
        else if (f == null) return 1;
        else if (s == null) return -1;
        else return f.compareTo(s);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Operators                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public FieldPredicate<ENTITY> isNull() {
        return new ReferenceIsNullPredicate<>(this);
    }

    @Override
    public FieldPredicate<ENTITY> equal(V value) {
        return new ReferenceEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterThan(V value) {
        return new ReferenceGreaterThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterOrEqual(V value) {
        return new ReferenceGreaterOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> between(V start, V end, Inclusion inclusion) {
        return new ReferenceBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> in(Collection<V> values) {
        return new ReferenceInPredicate<>(this, collectionToSet(values));
    }
    
    @Override
    public SpeedmentPredicate<ENTITY> notEqual(V value) {
        return new ReferenceNotEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessThan(V value) {
        return new ReferenceLessThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessOrEqual(V value) {
        return new ReferenceLessOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notBetween(V start, V end, Inclusion inclusion) {
        return new ReferenceNotBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notIn(Collection<V> values) {
        return new ReferenceNotInPredicate<>(this, collectionToSet(values));
    }
}