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
package com.speedment.internal.core.db.crud;

import com.speedment.db.crud.Selector;
import com.speedment.field.predicate.ComparableSpeedmentPredicate;
import com.speedment.field.predicate.PredicateType;
import com.speedment.field.trait.FieldTrait;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Selector} interface.
 *
 * @author Emil
 */
public final class SelectorImpl implements Selector {

    private final String columnName;
    private final PredicateType predicateType;
    private final Object operand;

    /**
     * SelectorImpl should be constructed using the appropriate static class.
     *
     * @param columnName the column name to compare
     * @param predicateType the operator to use when comparing
     * @param operand the operand to compare to, or {@code null} if the operator
     * is unary
     */
    private SelectorImpl(String columnName, PredicateType predicateType, Object operand) {
        this.columnName = requireNonNull(columnName);
        this.predicateType = requireNonNull(predicateType);
        this.operand = operand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName() {
        return columnName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PredicateType getPredicateType() {
        return predicateType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Object> getOperand() {
        return Optional.ofNullable(operand);
    }

    /**
     * Constructs a new {@link Selector} based on a comparable predicate
     * builder.
     *
     * @param predicate the predicate
     * @param <ENTITY> the type of the entity
     * @param <V> the type of the value
     * @return the constructed {@link Selector}
     * @see BinaryPredicateBuilder
     */
    public static <ENTITY, V extends Comparable<V>> Selector fromComparablePredicate(ComparableSpeedmentPredicate<ENTITY, V> predicate) {

        final PredicateType predicateType = predicate.getPredicateType();
        final FieldTrait field = predicate.getField();

        throw new UnsupportedOperationException("TBW");

//        return new SelectorImpl(
//            predicate.getField().getColumnName(),
//            predicate.getComparableOperator(),
//            predicate.getOperand()
//        );
    }

    /**
     * Constructs a standard key-value selector.
     *
     * @param columnName the column name
     * @param value the expected value
     * @return a selector for that match
     */
    public static Selector standard(String columnName, Object value) {
        return new SelectorImpl(columnName, PredicateType.EQUAL, value);
    }
}
