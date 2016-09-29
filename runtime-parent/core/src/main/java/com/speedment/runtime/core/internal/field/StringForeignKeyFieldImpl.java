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
import com.speedment.runtime.core.field.StringField;
import com.speedment.runtime.core.field.StringForeignKeyField;
import com.speedment.runtime.core.field.method.BackwardFinder;
import com.speedment.runtime.core.field.method.FindFrom;
import com.speedment.runtime.core.field.method.Finder;
import com.speedment.runtime.core.field.method.ReferenceGetter;
import com.speedment.runtime.core.field.method.ReferenceSetter;
import com.speedment.runtime.core.field.predicate.FieldPredicate;
import com.speedment.runtime.core.field.predicate.Inclusion;
import com.speedment.runtime.core.internal.field.comparator.NullOrder;
import com.speedment.runtime.core.internal.field.comparator.ReferenceFieldComparatorImpl;
import com.speedment.runtime.core.internal.field.finder.FindFromReference;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceBetweenPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceEqualPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceGreaterOrEqualPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceGreaterThanPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceInPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceIsNullPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceLessOrEqualPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceLessThanPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceNotBetweenPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceNotEqualPredicate;
import com.speedment.runtime.core.internal.field.predicate.reference.ReferenceNotInPredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringContainsIgnoreCasePredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringContainsPredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringEndsWithIgnoreCasePredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringEndsWithPredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringEqualIgnoreCasePredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringIsEmptyPredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringStartsWithIgnoreCasePredicate;
import com.speedment.runtime.core.internal.field.predicate.string.StringStartsWithPredicate;
import com.speedment.runtime.core.internal.field.streamer.BackwardFinderImpl;
import com.speedment.runtime.core.manager.Manager;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <D> the database type
 *
 * @author Per Minborg
 * @since 2.2.0
 */
public final class StringForeignKeyFieldImpl<ENTITY, D, FK_ENTITY> implements
    StringForeignKeyField<ENTITY, D, FK_ENTITY> {

    private final ColumnIdentifier<ENTITY> identifier;
    private final ReferenceGetter<ENTITY, String> getter;
    private final ReferenceSetter<ENTITY, String> setter;
    private final StringField<FK_ENTITY, ?> referenced;
    private final TypeMapper<D, String> typeMapper;
    private final boolean unique;

    public StringForeignKeyFieldImpl(
        ColumnIdentifier<ENTITY> identifier,
        ReferenceGetter<ENTITY, String> getter,
        ReferenceSetter<ENTITY, String> setter,
        StringField<FK_ENTITY, ?> referenced,
        Finder<ENTITY, FK_ENTITY> finder,
        TypeMapper<D, String> typeMapper,
        boolean unique) {

        this.identifier = requireNonNull(identifier);
        this.getter = requireNonNull(getter);
        this.setter = requireNonNull(setter);
        this.referenced = requireNonNull(referenced);
        this.typeMapper = requireNonNull(typeMapper);
        this.unique = unique;
    }

    /**
     * **************************************************************
     */
    /*                           Getters                             */
    /**
     * **************************************************************
     */
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
    public StringField<FK_ENTITY, ?> getReferencedField() {
        return referenced;
    }
    
    @Override
    public BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(Manager<ENTITY> manager) {
        return new BackwardFinderImpl<>(this, manager);
    }

    @Override
    public FindFrom<ENTITY, FK_ENTITY> finder(Manager<FK_ENTITY> foreignManager) {
        return new FindFromReference<>(this, referenced, foreignManager);
    }

    @Override
    public TypeMapper<D, String> typeMapper() {
        return typeMapper;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    /**
     * **************************************************************
     */
    /*                         Comparators                           */
    /**
     * **************************************************************
     */
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

    /**
     * **************************************************************
     */
    /*                           Operators                           */
    /**
     * **************************************************************
     */
    @Override
    public FieldPredicate<ENTITY> isNull() {
        return new ReferenceIsNullPredicate<>(this);
    }

    @Override
    public FieldPredicate<ENTITY> equal(String value) {
        return new ReferenceEqualPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> greaterThan(String value) {
        return new ReferenceGreaterThanPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> greaterOrEqual(String value) {
        return new ReferenceGreaterOrEqualPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> between(String start, String end, Inclusion inclusion) {
        return new ReferenceBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public Predicate<ENTITY> in(Set<String> values) {
        return new ReferenceInPredicate<>(this, values);
    }

    @Override
    public Predicate<ENTITY> notEqual(String value) {
        return new ReferenceNotEqualPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> lessThan(String value) {
        return new ReferenceLessThanPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> lessOrEqual(String value) {
        return new ReferenceLessOrEqualPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> notBetween(String start, String end, Inclusion inclusion) {
        return new ReferenceNotBetweenPredicate<>(this, start, end, inclusion);
    }

    @Override
    public Predicate<ENTITY> notIn(Set<String> values) {
        return new ReferenceNotInPredicate<>(this, values);
    }

    @Override
    public Predicate<ENTITY> equalIgnoreCase(String value) {
        return new StringEqualIgnoreCasePredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> startsWith(String value) {
        return new StringStartsWithPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> endsWith(String value) {
        return new StringEndsWithPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> contains(String value) {
        return new StringContainsPredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> isEmpty() {
        return new StringIsEmptyPredicate<>(this);
    }

    @Override
    public Predicate<ENTITY> startsWithIgnoreCase(String value) {
        return new StringStartsWithIgnoreCasePredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> endsWithIgnoreCase(String value) {
        return new StringEndsWithIgnoreCasePredicate<>(this, value);
    }

    @Override
    public Predicate<ENTITY> containsIgnoreCase(String value) {
        return new StringContainsIgnoreCasePredicate<>(this, value);
    }

}
