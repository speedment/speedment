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
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.field.internal.comparator.ReferenceFieldComparatorImpl;
import com.speedment.runtime.field.internal.predicate.reference.*;
import com.speedment.runtime.field.internal.predicate.string.*;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.Collection;

import static com.speedment.runtime.field.internal.util.CollectionUtil.collectionToSet;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <D>      the database type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public final class StringFieldImpl<ENTITY, D>
implements StringField<ENTITY, D>,
           FieldComparator<ENTITY> {

    private final ColumnIdentifier<ENTITY> identifier;
    private final ReferenceGetter<ENTITY, String> getter;
    private final ReferenceSetter<ENTITY, String> setter;
    private final TypeMapper<D, String> typeMapper;
    private final boolean unique;
    private final String tableAlias;

    public StringFieldImpl(
        final ColumnIdentifier<ENTITY> identifier,
        final ReferenceGetter<ENTITY, String> getter,
        final ReferenceSetter<ENTITY, String> setter,
        final TypeMapper<D, String> typeMapper,
        final boolean unique
    ) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique     = unique;
        this.tableAlias = identifier.getTableId();
    }

    public StringFieldImpl(
        final ColumnIdentifier<ENTITY> identifier,
        final ReferenceGetter<ENTITY, String> getter,
        final ReferenceSetter<ENTITY, String> setter,
        final TypeMapper<D, String> typeMapper,
        final boolean unique,
        final String tableAlias
    ) {
        this.identifier = requireNonNull(identifier);
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
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
    public ReferenceSetter<ENTITY, String> setter() {
        return setter;
    }

    @Override
    public ReferenceGetter<ENTITY, String> getter() {
        return getter;
    }

    @Override
    public TypeMapper<D, String> typeMapper() {
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
    public StringField<ENTITY, D> tableAlias(String tableAlias) {
        return new StringFieldImpl<>(identifier, getter, setter, typeMapper, unique, tableAlias);
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
        final String f = get(first);
        final String s = get(second);
        if (f == null && s == null) return 0;
        else if (f == null) return 1;
        else if (s == null) return -1;
        else return f.compareTo(s);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Operators                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public StringIsNullPredicate<ENTITY, D> isNull() {
        return new StringIsNullPredicate<>(this);
    }

    @Override
    public StringIsNotNullPredicate<ENTITY, D> isNotNull() {
        return new StringIsNotNullPredicate<>(this);
    }

    @Override
    public FieldPredicate<ENTITY> equal(String value) {
        requireNonNull(value);
        return new ReferenceEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterThan(String value) {
        requireNonNull(value);
        return new ReferenceGreaterThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterOrEqual(String value) {
        requireNonNull(value);
        return new ReferenceGreaterOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> between(String start, String end, Inclusion inclusion) {
        requireNonNull(start);
        requireNonNull(end);
        requireNonNull(inclusion);
        return new ReferenceBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> in(Collection<String> values) {
        requireNonNull(values);
        return new ReferenceInPredicate<>(this, collectionToSet(values));
    }
    
    @Override
    public SpeedmentPredicate<ENTITY> notEqual(String value) {
        requireNonNull(value);
        return new ReferenceNotEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessThan(String value) {
        requireNonNull(value);
        return new ReferenceLessThanPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessOrEqual(String value) {
        requireNonNull(value);
        return new ReferenceLessOrEqualPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notBetween(String start, String end, Inclusion inclusion) {
        requireNonNull(start);
        requireNonNull(end);
        requireNonNull(inclusion);
        return new ReferenceNotBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notIn(Collection<String> values) {
        requireNonNull(values);
        return new ReferenceNotInPredicate<>(this, collectionToSet(values));
    }

    @Override
    public SpeedmentPredicate<ENTITY> equalIgnoreCase(String value) {
        requireNonNull(value);
        return new StringEqualIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    @Override
    public SpeedmentPredicate<ENTITY> startsWith(String value) {
        requireNonNull(value);
        return new StringStartsWithPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> endsWith(String value) {
        requireNonNull(value);
        return new StringEndsWithPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> contains(String value) {
        requireNonNull(value);
        return new StringContainsPredicate<>(this, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY> isEmpty() {
        return new StringIsEmptyPredicate<>(this);
    }

    @Override
    public SpeedmentPredicate<ENTITY> startsWithIgnoreCase(String value) {
        requireNonNull(value);
        return new StringStartsWithIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    @Override
    public SpeedmentPredicate<ENTITY> endsWithIgnoreCase(String value) {
        requireNonNull(value);
        return new StringEndsWithIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    @Override
    public SpeedmentPredicate<ENTITY> containsIgnoreCase(String value) {
        requireNonNull(value);
        return new StringContainsIgnoreCasePredicate<>(this, value.toLowerCase());
    }

    ////////////////////////////////////////////////////////////////////////////
    //                               Operators                                //
    ////////////////////////////////////////////////////////////////////////////


    @Override
    public String toString() {
        return StringFieldImpl.class.getSimpleName() + "{" + identifier.getColumnId() + "}";
    }
}