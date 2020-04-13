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
package com.speedment.runtime.join.internal.stage;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.JoinOperator;
import com.speedment.runtime.join.stage.Stage;
import java.util.Collections;
import java.util.List;

import static com.speedment.common.invariant.IntRangeUtil.requireNonNegative;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <T> entity types for this Stage
 */
public class StageImpl<T> implements Stage<T> {

    private final TableIdentifier<T> identifier;
    private final List<Predicate<? super T>> predicates;
    private final JoinType joinType;
    private final HasComparableOperators<T, ?> field;
    private final JoinOperator joinOperator;
    private final HasComparableOperators<?, ?> foreignFirstField;
    private final int referencedStage;
//    private final HasComparableOperators<?, ?> foreignSecondField;
//    private final Inclusion foreignInclusion;

    public StageImpl(
        final TableIdentifier<T> identifier,
        final List<Predicate<? super T>> predicates,
        final JoinType joinType,
        final HasComparableOperators<T, ?> field,
        final JoinOperator joinOperator,
        final HasComparableOperators<?, ?> foreignField,
        final int referencedStage
//        final HasComparableOperators<?, ?> foreignSecondField,
//        final Inclusion foreignInclusion
    ) {
        this.identifier = requireNonNull(identifier);
        this.predicates = predicates; // Nullable
        this.joinType = joinType; // Nullable
        this.field = field; // Nullable
        this.joinOperator = joinOperator; // Nullable
        this.foreignFirstField = foreignField; // Nullable
        this.referencedStage = referencedStage;
//        this.foreignSecondField = foreignSecondField; // Nullable
//        this.foreignInclusion = foreignInclusion; // Nullable
    }

    @Override
    public TableIdentifier<T> identifier() {
        return identifier;
    }

    @Override
    public List<Predicate<? super T>> predicates() {
        return Collections.unmodifiableList(predicates);
    }

    @Override
    public Optional<JoinType> joinType() {
        return Optional.ofNullable(joinType);
    }

    @Override
    public Optional<HasComparableOperators<T, ?>> field() {
        return Optional.ofNullable(field);
    }

    @Override
    public Optional<JoinOperator> joinOperator() {
        return Optional.ofNullable(joinOperator);
    }

    @Override
    public Optional<HasComparableOperators<?, ?>> foreignField() {
        return Optional.ofNullable(foreignFirstField);
    }

    @Override
    public int referencedStage() {
        return referencedStage;
    }

    //    @Override
//    public Optional<HasComparableOperators<?, ?>> foreignSecondField() {
//        return Optional.ofNullable(foreignSecondField);
//    }
//
//    @Override
//    public Optional<Inclusion> foreignInclusion() {
//        return Optional.ofNullable(foreignInclusion);
//    }

    @Override
    public String toString() {
        return "StageImpl{" +
            "identifier=" + identifier +
            ", predicates=" + predicates +
            ", joinType=" + joinType +
            ", field=" + field +
            ", joinOperator=" + joinOperator +
            ", foreignFirstField=" + foreignFirstField +
            ", referencedStage=" + referencedStage +
            '}';
    }
}
