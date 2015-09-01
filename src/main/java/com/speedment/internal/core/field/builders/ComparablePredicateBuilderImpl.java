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
package com.speedment.internal.core.field.builders;

import com.speedment.field.ReferenceComparableField;
import java.util.Comparator;
import java.util.Objects;
import com.speedment.field.builders.ComparablePredicateBuilder;
import com.speedment.field.operators.ComparableOperator;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 * @param <V> Value type
 */
public final class ComparablePredicateBuilderImpl<ENTITY, V extends Comparable<? super V>> 
    extends AbstractBasePredicate<ENTITY> 
    implements ComparablePredicateBuilder<ENTITY, V> {
    
    private final ReferenceComparableField<ENTITY, V> field;
    private final V value;
    private final ComparableOperator operator;
    private final Comparator<V> entityComparator;
    
    public ComparablePredicateBuilderImpl(
        final ReferenceComparableField<ENTITY, V> field,
        final V value,
        final ComparableOperator operator,
        final Comparator<V> entityComparator
    ) {
        this.field            = requireNonNull(field);
        this.value            = value;
        this.entityComparator = requireNonNull(entityComparator);
        this.operator         = requireNonNull(operator);
    }

    @Override
    public boolean test(final ENTITY entity) {
        return test(Objects.compare(field.get(entity), getOperand(), entityComparator));
    }

    public boolean test(final int compare) {
        return operator.getComparator().test(compare);
    }

    @Override
    public ComparableOperator getComparableOperator() {
        return operator;
    }

    @Override
    public V getOperand() {
        return value;
    }

    @Override
    public ReferenceComparableField<ENTITY, V> getField() {
        return field;
    }
}