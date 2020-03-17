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
package com.speedment.runtime.join.trait;

// import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;

/**
 *
 * @author Per Minborg
 * @param <R> return type
 */
public interface HasOnPredicates<R> {

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>equal</em> to the given {@code joinedField}.
     *
     * @param joinedField to use by the operation
     * @return a builder with the added operation
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    R equal(HasComparableOperators<?, ?> joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>not equal</em> to the given
     * {@code joinedField}.
     *
     * @param joinedField to use by the operation
     * @return a builder with the added operation
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    R notEqual(HasComparableOperators<?, ?> joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>less than</em> the given {@code joinedField}.
     *
     * @param joinedField to use by the operation
     * @return a builder with the added operation
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    R lessThan(HasComparableOperators<?, ?> joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>less or equal</em> to the given
     * {@code joinedField}.
     *
     * @param joinedField to use by the operation
     * @return a builder with the added operation
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    R lessOrEqual(HasComparableOperators<?, ?> joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>greater than</em> the given
     * {@code joinedField}.
     *
     * @param joinedField to use by the operation
     * @return a builder with the added operation
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    R greaterThan(HasComparableOperators<?, ?> joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>greater or equal</em> to the given
     * {@code joinedField}.
     *
     * @param joinedField to use by the operation
     * @return a builder with the added operation
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    R greaterOrEqual(HasComparableOperators<?, ?> joinedField);

//    /**
//     * Adds an operation where a previous field is compared to the given
//     * {@code joinedFieldFrom} and {@code joinedFieldTo} and whereby the
//     * operation returns true if and only the previous field is <em>between</em>
//     * {@code joinedFieldFrom} (inclusive>) and {@code joinedFieldTo}
//     * (exclusive).
//     *
//     * @param <ENTITY> entity type for both fields
//     * @param joinedFieldFrom to use by the operation
//     * @param joinedFieldTo to use by the operation
//     * @return a builder with the added operation
//     *
//     * @throws NullPointerException if either the provided
//     * {@code joinedFieldFrom} or the provided {@code joinedFieldTo } is
//     * {@code null}.
//     */
//    default <ENTITY> R between(
//        HasComparableOperators<ENTITY, ?> joinedFieldFrom,
//        HasComparableOperators<ENTITY, ?> joinedFieldTo
//    ) {
//        return between(joinedFieldFrom, joinedFieldTo, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
//    }
//
//    /**
//     * Adds an operation where a previous field is compared to the given
//     * {@code joinedFieldFrom} and {@code joinedFieldTo} and whereby the
//     * operation returns true if and only the previous field is <em>between</em>
//     * {@code joinedFieldFrom} and {@code joinedFieldTo} while considering the
//     * provided mode of {@code inclusion}.
//     * <p>
//     * The Inclusion parameter may take the following values:
//     * <p>
//     * <
//     * pre>{@code
//     *    START_INCLUSIVE_END_INCLUSIVE,
//     *    START_INCLUSIVE_END_EXCLUSIVE,
//     *    START_EXCLUSIVE_END_INCLUSIVE,
//     *    START_EXCLUSIVE_END_EXCLUSIVE
//     * }
//     * </pre>
//     *
//     * @param <ENTITY> entity type for both fields
//     * @param joinedFieldFrom to use by the operation
//     * @param joinedFieldTo to use by the operation
//     * @param inclusion type of between (open or closed in both ends)
//     * @return a builder with the added operation
//     *
//     * @throws NullPointerException if either the provided
//     * {@code joinedFieldFrom}, the provided {@code joinedFieldTo } or the
//     * provided {@code inclusion} is {@code null}.
//     */
//    <ENTITY> R between(
//        HasComparableOperators<ENTITY, ?> joinedFieldFrom,
//        HasComparableOperators<ENTITY, ?> joinedFieldTo,
//        Inclusion inclusion
//    );
//
//    /**
//     * Adds an operation where a previous field is compared to the given
//     * {@code joinedFieldFrom} and {@code joinedFieldTo} and whereby the
//     * operation returns true if and only the previous field is <em>not
//     * between</em> {@code joinedFieldFrom} (inclusive>) and
//     * {@code joinedFieldTo} (exclusive).
//     *
//     * @param <ENTITY> entity type for both fields
//     * @param joinedFieldFrom to use by the operation
//     * @param joinedFieldTo to use by the operation
//     * @return a builder with the added operation
//     *
//     * @throws NullPointerException if either the provided
//     * {@code joinedFieldFrom} or the provided {@code joinedFieldTo } is
//     * {@code null}.
//     */
//    default <ENTITY> R notBetween(
//        HasComparableOperators<ENTITY, ?> joinedFieldFrom,
//        HasComparableOperators<ENTITY, ?> joinedFieldTo
//    ) {
//        return notBetween(joinedFieldFrom, joinedFieldTo, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
//    }
//
//    /**
//     * Adds an operation where a previous field is compared to the given
//     * {@code joinedFieldFrom} and {@code joinedFieldTo} and whereby the
//     * operation returns true if and only the previous field is <em>not
//     * between</em> {@code joinedFieldFrom} and {@code joinedFieldTo} while
//     * considering the provided mode of {@code inclusion}.
//     * <p>
//     * The Inclusion parameter may take the following values:
//     * <p>
//     * <
//     * pre>{@code
//     *    START_INCLUSIVE_END_INCLUSIVE,
//     *    START_INCLUSIVE_END_EXCLUSIVE,
//     *    START_EXCLUSIVE_END_INCLUSIVE,
//     *    START_EXCLUSIVE_END_EXCLUSIVE
//     * }
//     * </pre>
//     *
//     * @param <ENTITY> entity type for both fields
//     * @param joinedFieldFrom to use by the operation
//     * @param joinedFieldTo to use by the operation
//     * @param inclusion type of between (open or closed in both ends)
//     * @return a builder with the added operation
//     *
//     * @throws NullPointerException if either the provided null
//     * {@code joinedFieldFrom}, the provided {@code joinedFieldTo } or the
//     * provided {@code inclusion} is {@code null}.
//     */
//    <ENTITY> R notBetween(
//        HasComparableOperators<ENTITY, ?> joinedFieldFrom,
//        HasComparableOperators<ENTITY, ?> joinedFieldTo,
//        Inclusion inclusion
//    );

}
