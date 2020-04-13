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
package com.speedment.runtime.field.internal.predicate.reference;

import com.speedment.common.tuple.Tuple2;
import com.speedment.runtime.field.internal.predicate.AbstractFieldPredicate;
import com.speedment.runtime.field.predicate.trait.HasInclusion;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasReferenceValue;

import java.util.function.Predicate;

import static com.speedment.runtime.field.predicate.PredicateType.BETWEEN;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class ReferenceBetweenPredicate<ENTITY, D, V extends Comparable<? super V>>
    extends AbstractFieldPredicate<ENTITY, HasReferenceValue<ENTITY, D, V>>
    implements HasInclusion, Tuple2<V, V> {

    private final V start;
    private final V end;
    private final Inclusion inclusion;

    public ReferenceBetweenPredicate(
        final HasReferenceValue<ENTITY, D, V> referenceField,
        final V start,
        final V end,
        final Inclusion inclusion
    ) {
        super(BETWEEN, referenceField, entityPredicate(referenceField, start, end, inclusion));
        this.start     = start;
        this.end       = end;
        this.inclusion = requireNonNull(inclusion);
    }

    private static <ENTITY, D, V extends Comparable<? super V>> Predicate<ENTITY> entityPredicate(HasReferenceValue<ENTITY, D, V> referenceField, V start, V end, Inclusion inclusion) {
        return entity -> {
            final V fieldValue = referenceField.get(entity);

            switch (inclusion) {
                case START_EXCLUSIVE_END_EXCLUSIVE :
                    return startExclusiveEndExclusive(start, end, fieldValue);

                case START_EXCLUSIVE_END_INCLUSIVE :
                    return startExclusiveEndIncluseve(start, end, fieldValue);

                case START_INCLUSIVE_END_EXCLUSIVE :
                    return startInclusiveEndExclusive(start, end, fieldValue);

                case START_INCLUSIVE_END_INCLUSIVE :
                    return startInclusiveEndInclusive(start, end, fieldValue);

                default : throw new IllegalStateException("Inclusion unknown: " + inclusion);
            }
        };
    }

    private static <V extends Comparable<? super V>> boolean startInclusiveEndInclusive(V start, V end, V fieldValue) {
        if (fieldValue == null) {
            return start == null || end == null;
        } else if (start == null || end == null) {
            return false;
        }
        return (start.compareTo(fieldValue) <= 0 && end.compareTo(fieldValue) >= 0);
    }

    private static <V extends Comparable<? super V>> boolean startInclusiveEndExclusive(V start, V end, V fieldValue) {
        if (fieldValue == null) {
            return start == null && end != null;
        } else if (start == null || end == null) {
            return false;
        }
        return (start.compareTo(fieldValue) <= 0 && end.compareTo(fieldValue) > 0);
    }

    private static <V extends Comparable<? super V>> boolean startExclusiveEndIncluseve(V start, V end, V fieldValue) {
        if (fieldValue == null) {
            return start != null && end == null;
        } else if (start == null || end == null) {
            return false;
        } else return (start.compareTo(fieldValue) < 0 && end.compareTo(fieldValue) >= 0);
    }

    private static <V extends Comparable<? super V>> boolean startExclusiveEndExclusive(V start, V end, V fieldValue) {
        if (fieldValue == null) {
            return false;
        } else if (start == null || end == null) {
            return false;
        } else return (start.compareTo(fieldValue) < 0 && end.compareTo(fieldValue) > 0);
    }

    @Override
    public V get0() {
        return start;
    }

    @Override
    public V get1() {
        return end;
    }

    @Override
    public Inclusion getInclusion() {
        return inclusion;
    }

    @Override
    public ReferenceNotBetweenPredicate<ENTITY, D, V> negate() {
        return new ReferenceNotBetweenPredicate<>(getField(), start, end, inclusion);
    }
    
}