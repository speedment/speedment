package com.speedment.runtime.join.trait;

import com.speedment.runtime.field.trait.HasComparableOperators;

/**
 *
 * @author Per Minborg
 * @param <V> comparable value type
 * @param <ENTITY> entity type
 * @param <R> return type
 */
public interface HasOnPredicates<V extends Comparable<? super V>, ENTITY, R> {

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>equal</em> to the given {@code joinedField}.
     *
     * @param <FIELD> type for the joined field
     * @param joinedField to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>> R equal(FIELD joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>not equal</em> to the given
     * {@code joinedField}.
     *
     * @param <FIELD> type for the joined field
     * @param joinedField to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>> R notEqual(FIELD joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>less than</em> the given {@code joinedField}.
     *
     * @param <FIELD> type for the joined field
     * @param joinedField to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>> R lessThan(FIELD joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>less or equal</em> to the given
     * {@code joinedField}.
     *
     * @param <FIELD> type for the joined field
     * @param joinedField to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>> R lessOrEqual(FIELD joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>greater than</em> the given
     * {@code joinedField}.
     *
     * @param <FIELD> type for the joined field
     * @param joinedField to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>> R greaterThan(FIELD joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedField} and whereby the operation returns true if and only
     * the previous field is <em>greater or equal</em> to the given
     * {@code joinedField}.
     *
     * @param <FIELD> type for the joined field
     * @param joinedField to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if the provided {@code joinedField} is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>> R greaterOrEqual(FIELD joinedField);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedFieldFrom} and {@code joinedFieldTo} and whereby the
     * operation returns true if and only the previous field is <em>between</em>
     * {@code joinedFieldFrom} (inclusive>) and {@code joinedFieldTo}
     * (exclusive).
     *
     * @param <FIELD> type for the joined field
     * @param <FIELD2> type for the second joined field
     * @param joinedFieldFrom to use by the operation
     * @param joinedFieldTo to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if either the provided
     * {@code joinedFieldFrom} or the provided {@code joinedFieldTo } is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>, FIELD2 extends HasComparableOperators<ENTITY, V>>
        R between(FIELD joinedFieldFrom, FIELD2 joinedFieldTo);

    /**
     * Adds an operation where a previous field is compared to the given
     * {@code joinedFieldFrom} and {@code joinedFieldTo} and whereby the
     * operation returns true if and only the previous field is <em>not
     * between</em> {@code joinedFieldFrom} (inclusive>) and
     * {@code joinedFieldTo} (exclusive).
     *
     * @param <FIELD> type for the joined field
     * @param <FIELD2> type for the second joined field
     * @param joinedFieldFrom to use by the operation
     * @param joinedFieldTo to use by the operation
     * @return a builder
     *
     * @throws NullPointerException if either the provided
     * {@code joinedFieldFrom} or the provided {@code joinedFieldTo } is
     * {@code null}.
     */
    <FIELD extends HasComparableOperators<ENTITY, V>, FIELD2 extends HasComparableOperators<ENTITY, V>>
        R notBetween(FIELD joinedFieldFrom, FIELD2 joinedFieldTo);

}
