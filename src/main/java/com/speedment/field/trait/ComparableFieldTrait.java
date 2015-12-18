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
package com.speedment.field.trait;

import com.speedment.annotation.Api;
import com.speedment.field.Inclusion;
import com.speedment.field.predicate.ComparableSpeedmentPredicate;
import java.util.Comparator;
import java.util.Set;

/**
 *
 * @author pemi, Emil Forslund
 * @param <ENTITY> the entity type
 * @param <V> the field value type
 */
@Api(version = "2.2")
public interface ComparableFieldTrait<ENTITY, V extends Comparable<? super V>> {

    /**
     * Returns a {@link Comparator} that will compare to this field using this
     * fields natural order.
     *
     * @return a {@link Comparator} that will compare to this field using this
     * fields natural order
     * @throws NullPointerException if a field is null
     */
    Comparator<ENTITY> comparator();

    /**
     * Returns a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted first.
     *
     * @return a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted first
     */
    Comparator<ENTITY> comparatorNullFieldsFirst();

    /**
     * Returns a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted last.
     *
     * @return a {@link Comparator} that will compare to this field using this
     * fields natural order, null fields are sorted last
     */
    Comparator<ENTITY> comparatorNullFieldsLast();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value
     */
    ComparableSpeedmentPredicate<ENTITY, V> equal(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value
     */
    ComparableSpeedmentPredicate<ENTITY, V> notEqual(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>less than</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>less than</em> the given value
     */
    ComparableSpeedmentPredicate<ENTITY, V> lessThan(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>less than or equal</em>
     * to the given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>less than or equal</em> to the given value
     */
    ComparableSpeedmentPredicate<ENTITY, V> lessOrEqual(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>greater than</em>
     * the given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>greater than</em> the given value
     */
    ComparableSpeedmentPredicate<ENTITY, V> greaterThan(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>greater than or equal</em>
     * to the given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>greater than or equal</em> to the given value
     */
    ComparableSpeedmentPredicate<ENTITY, V> greaterOrEqual(V value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>between</em>
     * the given values (inclusive the start value but exclusive the end value).
     * <p>
     * N.B. if the start value is greater than the end value, then the returned
     * Predicate will always evaluate to {@code false}
     *
     * @param start to compare as a start value
     * @param end to compare as an end value
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>between</em> the given values (inclusive the start
     * value but exclusive the end value)
     */
    default ComparableSpeedmentPredicate<ENTITY, V> between(V start, V end) {
        return between(start, end, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>between</em>
     * the given values and taking the Inclusion parameter into account when
     * determining if either of the end points shall be included in the Field
     * range or not.
     * <p>
     * N.B. if the start value is greater than the end value, then the returned
     * Predicate will always evaluate to {@code false}
     *
     * @param start to compare as a start value
     * @param end to compare as an end value
     * @param inclusion determines if the end points is included in the Field
     * range.
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>between</em> the given values and taking the Inclusion
     * parameter into account when determining if either of the end points shall
     * be included in the Field range or not
     */
    ComparableSpeedmentPredicate<ENTITY, V> between(V start, V end, Inclusion inclusion);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>in</em> the set of given
     * values.
     * <p>
     * N.B. if no values are given, then the returned Predicate will always
     * evaluate to {@code false}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>in</em> the set of given values
     */
    @SuppressWarnings("unchecked")
    ComparableSpeedmentPredicate<ENTITY, V> in(V... values);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>in</em> the given Set.
     * <p>
     * N.B. if the Set is empty, then the returned Predicate will always
     * evaluate to {@code false}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>in</em> the given Set
     */
    ComparableSpeedmentPredicate<ENTITY, V> in(Set<V> values);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not in</em> the set of
     * given values.
     * <p>
     * N.B. if no values are given, then the returned Predicate will always
     * evaluate to {@code true}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not in</em> the set of given values
     */
    @SuppressWarnings("unchecked")
    ComparableSpeedmentPredicate<ENTITY, V> notIn(V... values);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not in</em> the given Set.
     * <p>
     * N.B. if the Set is empty, then the returned Predicate will always
     * evaluate to {@code true}
     *
     * @param values the set of values to match towards this Field
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not in</em> the given Set
     */
    ComparableSpeedmentPredicate<ENTITY, V> notIn(Set<V> values);

}
