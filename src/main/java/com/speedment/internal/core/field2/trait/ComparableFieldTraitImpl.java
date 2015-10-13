/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.field2.trait;

import com.speedment.field2.Inclusion;
import com.speedment.internal.core.field2.predicate.impl.comparable.BetweenPredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.EqualPredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.GreaterOrEqualPredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.GreaterThanPredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.InPredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.LessOrEqualPredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.LessThanPredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.NotEqualPredicate;
import com.speedment.field2.methods.Getter;
import com.speedment.field2.predicate.ComparableSpeedmentPredicate;
import com.speedment.internal.comparator.impl.NullOrder;
import com.speedment.internal.comparator.impl.SpeedmentComparatorImpl;
import java.util.Comparator;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import com.speedment.field2.trait.ComparableFieldTrait;
import com.speedment.field2.trait.FieldTrait;
import com.speedment.internal.core.field2.predicate.impl.comparable.AlwaysFalseComparablePredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.IsNotNullComparablePredicate;
import com.speedment.internal.core.field2.predicate.impl.comparable.IsNullComparablePredicate;

/**
 * @param <ENTITY> the entity type
 * @param <V> the field value type
 * @author pemi
 */
public class ComparableFieldTraitImpl<ENTITY, V extends Comparable<? super V>> implements ComparableFieldTrait<ENTITY, V> {

    private final FieldTrait field;
    private final Getter<ENTITY, V> getter;
    private final ComparableSpeedmentPredicate<ENTITY, V> alwaysFalsePredicate;
    private final ComparableSpeedmentPredicate<ENTITY, V> isNullPredicate;
    private final ComparableSpeedmentPredicate<ENTITY, V> isNotNullPredicate;

    public ComparableFieldTraitImpl(FieldTrait field, Getter<ENTITY, V> getter) {
        this.field = field;
        this.getter = getter;
        this.alwaysFalsePredicate = new AlwaysFalseComparablePredicate<>(field, getter);
        this.isNullPredicate =  new IsNullComparablePredicate<>(field, getter);
        this.isNotNullPredicate = new IsNotNullComparablePredicate<>(field, getter);
    }

    @Override
    public Comparator<ENTITY> comparator() {
        return new SpeedmentComparatorImpl<>(field, getter, NullOrder.NONE);
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsFirst() {
        return new SpeedmentComparatorImpl<>(field, getter, NullOrder.FIRST);
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsLast() {
        return new SpeedmentComparatorImpl<>(field, getter, NullOrder.LAST);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> equal(V value) {
        if (value == null) {
            return isNullPredicate;
        }
        return new EqualPredicate<>(field, getter, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> notEqual(V value) {
        if (value == null) {
            return isNotNullPredicate;
        }
        return new NotEqualPredicate<>(field, getter, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> lessThan(V value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new LessThanPredicate<>(field, getter, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> lessOrEqual(V value) {
        if (value == null) {
            return isNullPredicate;
        }
        return new LessOrEqualPredicate<>(field, getter, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> greaterThan(V value) {
        if (value == null) {
            return alwaysFalsePredicate;
        }
        return new GreaterThanPredicate<>(field, getter, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> greaterOrEqual(V value) {
        if (value == null) {
            return isNullPredicate;
        }
        return new GreaterOrEqualPredicate<>(field, getter, value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> between(V start, V end, Inclusion inclusion) {
        // First, take a look at the case when either or both start or/and end are null
        if (start == null || end == null) {
            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE: {
                    return alwaysFalsePredicate;
                }
                case START_EXCLUSIVE_END_INCLUSIVE: {
                    if (end == null) {
                        return isNullPredicate;
                    }
                    return alwaysFalsePredicate;
                }
                case START_INCLUSIVE_END_EXCLUSIVE: {
                    if (end == null) {
                        return isNullPredicate;
                    }
                    return alwaysFalsePredicate;
                }
                case START_INCLUSIVE_END_INCLUSIVE: {
                    return isNullPredicate;
                }
            }
        }
        // Secondly, take a look at the case when neither start nor end end is null
        // We need to make sure that start < end or posibly start <= end
        final int comparison = end.compareTo(start);
        switch (inclusion) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                if (comparison <= 0) {
                    return alwaysFalsePredicate;
                }
                break;
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {
                if (comparison == 0) {
                    return new EqualPredicate<>(field, getter, end);
                }
                if (comparison < 0) {
                    return alwaysFalsePredicate;
                }
                break;
            }

            case START_INCLUSIVE_END_EXCLUSIVE: {
                if (comparison == 0) {
                    return new EqualPredicate<>(field, getter, start);
                }
                if (comparison < 0) {
                    return alwaysFalsePredicate;
                }
                break;
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                if (comparison == 0) {
                    return new EqualPredicate<>(field, getter, start);
                }
                if (comparison < 0) {
                    return alwaysFalsePredicate;
                }
                break;
            }
        }
        // Normal case:
        return new BetweenPredicate<>(field, getter, start, end, inclusion);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a stream from an array is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, V> in(V... values) {
        if (values.length == 0) {
            return alwaysFalsePredicate;
        }
        if (values.length == 1) {
            return new EqualPredicate<>(field, getter, values[0]);
        }
        return in(Stream.of(values).collect(toSet()));
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> in(Set<V> values) {
        if (values.isEmpty()) {
            return alwaysFalsePredicate;
        }
        if (values.size() == 1) {
            return new EqualPredicate<>(field, getter, values.stream().findAny().get());
        }
        return new InPredicate<>(field, getter, values);
    }

}
