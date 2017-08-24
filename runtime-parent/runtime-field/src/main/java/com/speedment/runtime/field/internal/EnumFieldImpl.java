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

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.EnumField;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.field.internal.comparator.ReferenceFieldComparatorImpl;
import com.speedment.runtime.field.internal.predicate.AlwaysFalsePredicate;
import com.speedment.runtime.field.internal.predicate.reference.ReferenceEqualPredicate;
import com.speedment.runtime.field.internal.predicate.reference.ReferenceInPredicate;
import com.speedment.runtime.field.internal.predicate.reference.ReferenceIsNotNullPredicate;
import com.speedment.runtime.field.internal.predicate.reference.ReferenceIsNullPredicate;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link EnumField}-interface.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public final class EnumFieldImpl<ENTITY, D, E extends Enum<E>>
implements EnumField<ENTITY, D, E> {

    private final ColumnIdentifier<ENTITY> identifier;
    private final ReferenceGetter<ENTITY, E> getter;
    private final ReferenceSetter<ENTITY, E> setter;
    private final TypeMapper<D, E> typeMapper;
    private final Function<E, String> enumToString;
    private final Function<String, E> stringToEnum;
    private final Class<E> enumClass;
    private final EnumSet<E> constants;

    public EnumFieldImpl(ColumnIdentifier<ENTITY> identifier,
                         ReferenceGetter<ENTITY, E> getter,
                         ReferenceSetter<ENTITY, E> setter,
                         TypeMapper<D, E> typeMapper,
                         Function<E, String> enumToString,
                         Function<String, E> stringToEnum,
                         Class<E> enumClass) {

        this.identifier   = requireNonNull(identifier);
        this.getter       = requireNonNull(getter);
        this.setter       = requireNonNull(setter);
        this.typeMapper   = requireNonNull(typeMapper);
        this.enumToString = requireNonNull(enumToString);
        this.stringToEnum = requireNonNull(stringToEnum);
        this.enumClass    = requireNonNull(enumClass);
        this.constants    = EnumSet.allOf(enumClass);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                Getters                                 //
    ////////////////////////////////////////////////////////////////////////////

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
    public FieldComparator<ENTITY> comparatorNullFieldsLast() {
        return comparator();
    }

    ////////////////////////////////////////////////////////////////////////////
    //                              Predicates                                //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public Predicate<ENTITY> isNull() {
        return new ReferenceIsNullPredicate<>(this);
    }

    @Override
    public Predicate<ENTITY> isNotNull() {
        return new ReferenceIsNotNullPredicate<>(this);
    }

    @Override
    public Predicate<ENTITY> equal(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) == 0);
    }

    @Override
    public Predicate<ENTITY> notEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) != 0);
    }

    @Override
    public Predicate<ENTITY> lessThan(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) < 0);
    }

    @Override
    public Predicate<ENTITY> lessOrEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) <= 0);
    }

    @Override
    public Predicate<ENTITY> greaterThan(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) > 0);
    }

    @Override
    public Predicate<ENTITY> greaterOrEqual(E value) {
        return toEntityPredicate(e -> e != null && e.compareTo(value) >= 0);
    }

    @Override
    public Predicate<ENTITY> between(E start, E end, Inclusion inclusion) {
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
                    "Unknown inclusion '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public Predicate<ENTITY> notBetween(E start, E end, Inclusion inclusion) {
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
                    "Unknown inclusion '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public Predicate<ENTITY> in(Collection<E> values) {
        return toEntityPredicate(values::contains);
    }

    @Override
    public Predicate<ENTITY> notIn(Collection<E> values) {
        return toEntityPredicate(e -> !values.contains(e));
    }

    ////////////////////////////////////////////////////////////////////////////
    //                           String Predicates                            //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public Predicate<ENTITY> equal(String value) {
        return toEntityPredicate(e -> value.equals(enumToString.apply(e)));
    }

    @Override
    public Predicate<ENTITY> notEqual(String value) {
        return toEntityPredicate(e -> !value.equals(enumToString.apply(e)));
    }

    @Override
    public Predicate<ENTITY> lessThan(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) < 0;
        });
    }

    @Override
    public Predicate<ENTITY> lessOrEqual(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) <= 0;
        });
    }

    @Override
    public Predicate<ENTITY> greaterThan(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) > 0;
        });
    }

    @Override
    public Predicate<ENTITY> greaterOrEqual(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.compareTo(value) >= 0;
        });
    }

    @Override
    public Predicate<ENTITY> between(String start, String end, Inclusion inclusion) {
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
                    "Unknown inclusion '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public Predicate<ENTITY> notBetween(String start, String end, Inclusion inclusion) {
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
                    "Unknown inclusion '" + inclusion + "'."
                );
            }
        });
    }

    @Override
    public Predicate<ENTITY> isEmpty() {
        return toEntityPredicate(e -> "".equals(enumToString.apply(e)));
    }

    @Override
    public Predicate<ENTITY> equalIgnoreCase(String value) {
        return toEntityPredicate(e -> value.equalsIgnoreCase(enumToString.apply(e)));
    }

    @Override
    public Predicate<ENTITY> startsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.startsWith(value);
        });
    }

    @Override
    public Predicate<ENTITY> startsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.toLowerCase()
                .startsWith(value.toLowerCase());
        });
    }

    @Override
    public Predicate<ENTITY> endsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.endsWith(value);
        });
    }

    @Override
    public Predicate<ENTITY> endsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.toLowerCase()
                .endsWith(value.toLowerCase());
        });
    }

    @Override
    public Predicate<ENTITY> contains(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.contains(value);
        });
    }

    @Override
    public Predicate<ENTITY> containsIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && str.toLowerCase()
                .contains(value.toLowerCase());
        });
    }

    @Override
    public Predicate<ENTITY> isNotEmpty() {
        return toEntityPredicate(e -> !"".equals(enumToString.apply(e)));
    }

    @Override
    public Predicate<ENTITY> notEqualIgnoreCase(String value) {
        return toEntityPredicate(e -> !value.equalsIgnoreCase(enumToString.apply(e)));
    }

    @Override
    public Predicate<ENTITY> notStartsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.startsWith(value);
        });
    }

    @Override
    public Predicate<ENTITY> notEndsWith(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.endsWith(value);
        });
    }

    @Override
    public Predicate<ENTITY> notContains(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.contains(value);
        });
    }

    @Override
    public Predicate<ENTITY> notStartsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.toLowerCase()
                .startsWith(value.toLowerCase());
        });
    }

    @Override
    public Predicate<ENTITY> notEndsWithIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.toLowerCase()
                .endsWith(value.toLowerCase());
        });
    }

    @Override
    public Predicate<ENTITY> notContainsIgnoreCase(String value) {
        return toEntityPredicate(e -> {
            final String str = enumToString.apply(e);
            return str != null && !str.toLowerCase()
                .contains(value.toLowerCase());
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    //                            Internal Methods                            //
    ////////////////////////////////////////////////////////////////////////////

    private Predicate<ENTITY> toEntityPredicate(Predicate<E> predicate) {
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
