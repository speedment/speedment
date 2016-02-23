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
package com.speedment.internal.core.field.trait;

import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.field.Inclusion;
import com.speedment.internal.core.field.predicate.impl.comparable.BetweenPredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.EqualPredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.GreaterOrEqualPredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.GreaterThanPredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.InPredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.LessOrEqualPredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.LessThanPredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.NotEqualPredicate;
import com.speedment.field.predicate.ComparableSpeedmentPredicate;
import com.speedment.internal.comparator.impl.NullOrder;
import com.speedment.internal.comparator.impl.SpeedmentComparatorImpl;
import java.util.Comparator;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import com.speedment.field.trait.ComparableFieldTrait;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.predicate.impl.comparable.AlwaysFalseComparablePredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.AlwaysTrueComparablePredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.IsNotNullComparablePredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.IsNullComparablePredicate;
import com.speedment.internal.core.field.predicate.impl.comparable.NotInPredicate;
import static com.speedment.internal.util.CollectionsUtil.getAnyFrom;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <V> the field value type
 * @author pemi
 */
public class ComparableFieldTraitImpl<ENTITY, D, V extends Comparable<? super V>> implements ComparableFieldTrait<ENTITY, D, V> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, V> referenceFieldTrait;

    public ComparableFieldTraitImpl(FieldTrait field, ReferenceFieldTrait<ENTITY, D, V> referenceFieldTrait) {
        this.field = requireNonNull(field);
        this.referenceFieldTrait = requireNonNull(referenceFieldTrait);
    }

    @Override
    public Comparator<ENTITY> comparator() {
        return new SpeedmentComparatorImpl<>(field, referenceFieldTrait, NullOrder.NONE);
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsFirst() {
        return new SpeedmentComparatorImpl<>(field, referenceFieldTrait, NullOrder.FIRST);
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsLast() {
        return new SpeedmentComparatorImpl<>(field, referenceFieldTrait, NullOrder.LAST);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> equal(V value) {
        if (value == null) {
            return newIsNullPredicate();
        }
        return new EqualPredicate<>(field, referenceFieldTrait, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> notEqual(V value) {
        if (value == null) {
            return newIsNotNullPredicate();
        }
        return new NotEqualPredicate<>(field, referenceFieldTrait, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> lessThan(V value) {
        if (value == null) {
            return newAlwaysFalsePredicate();
        }
        return new LessThanPredicate<>(field, referenceFieldTrait, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> lessOrEqual(V value) {
        if (value == null) {
            return newIsNullPredicate();
        }
        return new LessOrEqualPredicate<>(field, referenceFieldTrait, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> greaterThan(V value) {
        if (value == null) {
            return newAlwaysFalsePredicate();
        }
        return new GreaterThanPredicate<>(field, referenceFieldTrait, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> greaterOrEqual(V value) {
        if (value == null) {
            return newIsNullPredicate();
        }
        return new GreaterOrEqualPredicate<>(field, referenceFieldTrait, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> between(V start, V end, Inclusion inclusion) {
        // First, take a look at the case when either or both start or/and end are null
        if (start == null || end == null) {
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE: {
                    return newAlwaysFalsePredicate();
                }
                case START_EXCLUSIVE_END_INCLUSIVE: {
                    if (end == null) {
                        return newIsNullPredicate();
                    }
                    return newAlwaysFalsePredicate();
                }
                case START_INCLUSIVE_END_EXCLUSIVE: {
                    if (end == null) {
                        return newIsNullPredicate();
                    }
                    return newAlwaysFalsePredicate();
                }
                case START_INCLUSIVE_END_INCLUSIVE: {
                    return newIsNullPredicate();
                }
            }
        }
        // Secondly, take a look at the case when neither start nor end end is null
        // We need to make sure that start < end or posibly start <= end
        final int comparison = end.compareTo(start);
        switch (inclusion) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                if (comparison <= 0) {
                    return newAlwaysFalsePredicate();
                }
                break;
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {
                if (comparison == 0) {
                    return new EqualPredicate<>(field, referenceFieldTrait, end);
                }
                if (comparison < 0) {
                    return newAlwaysFalsePredicate();
                }
                break;
            }

            case START_INCLUSIVE_END_EXCLUSIVE: {
                if (comparison == 0) {
                    return new EqualPredicate<>(field, referenceFieldTrait, start);
                }
                if (comparison < 0) {
                    return newAlwaysFalsePredicate();
                }
                break;
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                if (comparison == 0) {
                    return new EqualPredicate<>(field, referenceFieldTrait, start);
                }
                if (comparison < 0) {
                    return newAlwaysFalsePredicate();
                }
                break;
            }
        }
        // Normal case:
        return new BetweenPredicate<>(field, referenceFieldTrait, start, end, inclusion);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a stream from an array is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, D, V> in(V... values) {
        if (values.length == 0) {
            return newAlwaysFalsePredicate();
        }
        if (values.length == 1) {
            return new EqualPredicate<>(field, referenceFieldTrait, values[0]);
        }
        return in(Stream.of(values).collect(toSet()));
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> in(Set<V> values) {
        if (values.isEmpty()) {
            return newAlwaysFalsePredicate();
        }
        if (values.size() == 1) {
            return new EqualPredicate<>(field, referenceFieldTrait, getAnyFrom(values)); 
        }
        return new InPredicate<>(field, referenceFieldTrait, values);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a stream from an array is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, D, V> notIn(V... values) {
        if (values.length == 0) {
            return newAlwaysTruePredicate();
        }
        if (values.length == 1) {
            return new NotEqualPredicate<>(field, referenceFieldTrait, values[0]);
        }
        return notIn(Stream.of(values).collect(toSet()));
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> notIn(Set<V> values) {
        if (values.isEmpty()) {
            return newAlwaysTruePredicate();
        }
        if (values.size() == 1) {
            return new NotEqualPredicate<>(field, referenceFieldTrait, getAnyFrom(values)); 
        }
        return new NotInPredicate<>(field, referenceFieldTrait, values);
    }

    private ComparableSpeedmentPredicate<ENTITY, D, V> newAlwaysFalsePredicate() {
        return new AlwaysFalseComparablePredicate<>(field, referenceFieldTrait);
    }

    private ComparableSpeedmentPredicate<ENTITY, D, V> newAlwaysTruePredicate() {
        return new AlwaysTrueComparablePredicate<>(field, referenceFieldTrait);
    }

    private ComparableSpeedmentPredicate<ENTITY, D, V> newIsNullPredicate() {
        return new IsNullComparablePredicate<>(field, referenceFieldTrait);
    }

    private ComparableSpeedmentPredicate<ENTITY, D, V> newIsNotNullPredicate() {
        return new IsNotNullComparablePredicate<>(field, referenceFieldTrait);
    }

}
