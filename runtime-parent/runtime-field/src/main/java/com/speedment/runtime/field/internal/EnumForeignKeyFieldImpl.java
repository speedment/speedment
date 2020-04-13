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
import com.speedment.runtime.field.EnumForeignKeyField;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.field.internal.comparator.ReferenceFieldComparatorImpl;
import com.speedment.runtime.field.internal.method.BackwardFinderImpl;
import com.speedment.runtime.field.internal.method.FindFromNullableReference;
import com.speedment.runtime.field.internal.method.FindFromReference;
import com.speedment.runtime.field.internal.predicate.AlwaysFalsePredicate;
import com.speedment.runtime.field.internal.predicate.enums.EnumIsNotNullPredicate;
import com.speedment.runtime.field.internal.predicate.enums.EnumIsNullPredicate;
import com.speedment.runtime.field.internal.predicate.reference.ReferenceEqualPredicate;
import com.speedment.runtime.field.internal.predicate.reference.ReferenceInPredicate;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.method.FindFromNullable;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link EnumForeignKeyField}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public final class EnumForeignKeyFieldImpl
    <ENTITY, D, E extends Enum<E>, FK>
implements EnumForeignKeyField<ENTITY, D, E, FK>,
           FieldComparator<ENTITY> {

    public static final String UNKNOWN_INCLUSION = "Unknown inclusion";

    private final ColumnIdentifier<ENTITY> identifier;
    private final ReferenceGetter<ENTITY, E> getter;
    private final ReferenceSetter<ENTITY, E> setter;
    private final TypeMapper<D, E> typeMapper;
    private final HasComparableOperators<FK, E> referenced;
    private final Function<E, String> enumToString;
    private final Function<String, E> stringToEnum;
    private final Class<E> enumClass;
    private final EnumSet<E> constants;
    private final String tableAlias;

    public EnumForeignKeyFieldImpl(
        final ColumnIdentifier<ENTITY> identifier,
        final ReferenceGetter<ENTITY, E> getter,
        final ReferenceSetter<ENTITY, E> setter,
        final TypeMapper<D, E> typeMapper,
        final HasComparableOperators<FK, E> referenced,
        final Function<E, String> enumToString,
        final Function<String, E> stringToEnum,
        final Class<E> enumClass
    ) {
        this.identifier   = requireNonNull(identifier);
        this.getter       = requireNonNull(getter);
        this.setter       = requireNonNull(setter);
        this.typeMapper   = requireNonNull(typeMapper);
        this.referenced   = requireNonNull(referenced);
        this.enumToString = requireNonNull(enumToString);
        this.stringToEnum = requireNonNull(stringToEnum);
        this.enumClass    = requireNonNull(enumClass);
        this.constants    = EnumSet.allOf(enumClass);
        this.tableAlias   = identifier.getTableId();
    }

    private EnumForeignKeyFieldImpl(
        final ColumnIdentifier<ENTITY> identifier,
        final ReferenceGetter<ENTITY, E> getter,
        final ReferenceSetter<ENTITY, E> setter,
        final TypeMapper<D, E> typeMapper,
        final HasComparableOperators<FK, E> referenced,
        final Function<E, String> enumToString,
        final Function<String, E> stringToEnum,
        final Class<E> enumClass,
        final String tableAlias
    ) {
        this.identifier   = requireNonNull(identifier);
        this.getter       = requireNonNull(getter);
        this.setter       = requireNonNull(setter);
        this.typeMapper   = requireNonNull(typeMapper);
        this.referenced   = requireNonNull(referenced);
        this.enumToString = requireNonNull(enumToString);
        this.stringToEnum = requireNonNull(stringToEnum);
        this.enumClass    = requireNonNull(enumClass);
        this.constants    = EnumSet.allOf(enumClass);
        this.tableAlias   = requireNonNull(tableAlias);
    }


    ////////////////////////////////////////////////////////////////////////////
    //                                Getters                                 //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public EnumSet<E> constants() {
        return EnumSet.allOf(enumClass);
    }

    @Override
    public ColumnIdentifier<ENTITY> identifier() {
        return identifier;
    }

    @Override
    public ReferenceSetter<ENTITY, E> setter() {
        return setter;
    }

    @Override
    public ReferenceGetter<ENTITY, E> getter() {
        return getter;
    }

    @Override
    public TypeMapper<D, E> typeMapper() {
        return typeMapper;
    }

    @Override
    public Function<String, E> stringToEnum() {
        return stringToEnum;
    }

    @Override
    public Function<E, String> enumToString() {
        return enumToString;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public HasComparableOperators<FK, E> getReferencedField() {
        return referenced;
    }

    @Override
    public BackwardFinder<FK, ENTITY>
    backwardFinder(TableIdentifier<ENTITY> identifier,
                   Supplier<Stream<ENTITY>> streamSupplier) {

        return new BackwardFinderImpl<>(this, identifier, streamSupplier);
    }

    @Override
    public FindFrom<ENTITY, FK> finder(TableIdentifier<FK> identifier,
                                       Supplier<Stream<FK>> streamSupplier) {

        return new FindFromReference<>(
            this, referenced, identifier, streamSupplier
        );
    }

    @Override
    public FindFromNullable<ENTITY, FK> nullableFinder(
            TableIdentifier<FK> identifier,
            Supplier<Stream<FK>> streamSupplier) {

        return new FindFromNullableReference<>(
            this, referenced, identifier, streamSupplier
        );
    }

    @Override
    public String tableAlias() {
        return tableAlias;
    }

    @Override
    public EnumForeignKeyField<ENTITY, D, E, FK> tableAlias(String tableAlias) {
        return new EnumForeignKeyFieldImpl<>(identifier, getter, setter, typeMapper, referenced, enumToString, stringToEnum, enumClass, tableAlias);
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
        final E f = get(first);
        final E s = get(second);
        if (f == null && s == null) return 0;
        else if (f == null) return 1;
        else if (s == null) return -1;
        else return f.compareTo(s);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                              Predicates                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public EnumIsNullPredicate<ENTITY, D, E> isNull() {
        return new EnumIsNullPredicate<>(this);
    }

    @Override
    public EnumIsNotNullPredicate<ENTITY, D, E> isNotNull() {
        return new EnumIsNotNullPredicate<>(this);
    }

    @Override
    public SpeedmentPredicate<ENTITY> equal(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) == 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) != 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessThan(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) < 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessOrEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) <= 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterThan(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) > 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterOrEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) >= 0);
    }

    @Override
    public SpeedmentPredicate<ENTITY> between(E start, E end, Inclusion inclusion) {
        return toEntityPredicate(e -> {
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) >  0 && e.compareTo(end) <  0;
                case START_EXCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) >  0 && e.compareTo(end) <= 0;
                case START_INCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) >= 0 && e.compareTo(end) <  0;
                case START_INCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) >= 0 && e.compareTo(end) <= 0;
                default : throw new UnsupportedOperationException(
                    UNKNOWN_INCLUSION + " '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notBetween(E start, E end, Inclusion inclusion) {
        return toEntityPredicate(e -> {
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) <= 0 || e.compareTo(end) >= 0;
                case START_EXCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) <= 0 || e.compareTo(end) >  0;
                case START_INCLUSIVE_END_EXCLUSIVE:
                    return e.compareTo(start) <  0 || e.compareTo(end) >= 0;
                case START_INCLUSIVE_END_INCLUSIVE:
                    return e.compareTo(start) <  0 || e.compareTo(end) >  0;
                default : throw new UnsupportedOperationException(
                    UNKNOWN_INCLUSION + " '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> in(Collection<E> values) {
        return toEntityPredicate(values::contains);
    }

    @Override
    public SpeedmentPredicate<ENTITY> notIn(Collection<E> values) {
        return toEntityPredicate(e -> !values.contains(e));
    }

    ////////////////////////////////////////////////////////////////////////////
    //                           String Predicates                            //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public SpeedmentPredicate<ENTITY> equal(String value) {
        return toEntityPredicate(e -> value.equals(enumToString.apply(e)));
    }

    @Override
    public SpeedmentPredicate<ENTITY> notEqual(String value) {
        return toEntityPredicate(e -> !value.equals(enumToString.apply(e)));
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessThan(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) < 0;
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> lessOrEqual(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) <= 0;
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterThan(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) > 0;
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> greaterOrEqual(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) >= 0;
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> between(String start, String end, Inclusion inclusion) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            if (str == null) return false;

            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE:
                    return str.compareTo(start) >  0 && str.compareTo(end) <  0;
                case START_EXCLUSIVE_END_INCLUSIVE:
                    return str.compareTo(start) >  0 && str.compareTo(end) <= 0;
                case START_INCLUSIVE_END_EXCLUSIVE:
                    return str.compareTo(start) >= 0 && str.compareTo(end) <  0;
                case START_INCLUSIVE_END_INCLUSIVE:
                    return str.compareTo(start) >= 0 && str.compareTo(end) <= 0;
                default : throw new UnsupportedOperationException(
                    UNKNOWN_INCLUSION + " '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notBetween(String start, String end, Inclusion inclusion) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            if (str == null) return false;

            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE:
                    return str.compareTo(start) <= 0 || str.compareTo(end) >= 0;
                case START_EXCLUSIVE_END_INCLUSIVE:
                    return str.compareTo(start) <= 0 || str.compareTo(end) >  0;
                case START_INCLUSIVE_END_EXCLUSIVE:
                    return str.compareTo(start) <  0 || str.compareTo(end) >= 0;
                case START_INCLUSIVE_END_INCLUSIVE:
                    return str.compareTo(start) <  0 || str.compareTo(end) >  0;
                default : throw new UnsupportedOperationException(
                    UNKNOWN_INCLUSION + " '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> isEmpty() {
        return toEntityPredicate(e -> "".equals(enumToString.apply(e)));
    }

    @Override
    public SpeedmentPredicate<ENTITY> equalIgnoreCase(String value) {
        return toEntityPredicate(e -> value.equalsIgnoreCase(enumToString.apply(e)));
    }

    @Override
    public SpeedmentPredicate<ENTITY> startsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.startsWith(value);
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> startsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.toLowerCase()
                .startsWith(value.toLowerCase());
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> endsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.endsWith(value);
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> endsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.toLowerCase()
                .endsWith(value.toLowerCase());
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> contains(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.contains(value);
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> containsIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.toLowerCase()
                .contains(value.toLowerCase());
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> isNotEmpty() {
        return toEntityPredicate(e -> !"".equals(enumToString.apply(e)));
    }

    @Override
    public SpeedmentPredicate<ENTITY> notEqualIgnoreCase(String value) {
        return toEntityPredicate(e -> !value.equalsIgnoreCase(enumToString.apply(e)));
    }

    @Override
    public SpeedmentPredicate<ENTITY> notStartsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.startsWith(value);
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notEndsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.endsWith(value);
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notContains(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.contains(value);
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notStartsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.toLowerCase()
                .startsWith(value.toLowerCase());
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notEndsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.toLowerCase()
                .endsWith(value.toLowerCase());
        });
    }

    @Override
    public SpeedmentPredicate<ENTITY> notContainsIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.toLowerCase()
                .contains(value.toLowerCase());
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    //                            Internal Methods                            //
    ////////////////////////////////////////////////////////////////////////////

    private SpeedmentPredicate<ENTITY> toEntityPredicate(Predicate<E> predicate) {
        final EnumSet<E> valid = evaluate(predicate);
        switch (valid.size()) {
            case 0  : return new AlwaysFalsePredicate<>(this);
            case 1  : return new ReferenceEqualPredicate<>(this, valid.iterator().next());
            default : return new ReferenceInPredicate<>(this, valid);
        }
    }

    private EnumSet<E> evaluate(Predicate<E> predicate) {
        final EnumSet<E> result = EnumSet.noneOf(enumClass);
        constants.stream().filter(predicate).forEach(result::add);
        return result;
    }

}