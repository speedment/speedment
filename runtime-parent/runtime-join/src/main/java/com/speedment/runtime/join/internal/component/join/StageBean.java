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
package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.join.stage.JoinOperator;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 * Bean used to parse table info during join building.
 *
 * @author Per Minborg
 * @param <T> entity type of the table
 */
final class StageBean<T> {

    private final TableIdentifier<T> identifier;
    private final List<Predicate<? super T>> predicates;
    private JoinType joinType;
    private HasComparableOperators<T, ?> field;
    private JoinOperator joinOperator;
    private HasComparableOperators<?, ?> foreignField;
    private int referencedStage;
//    private HasComparableOperators<?, ?> foreignSecondField;
//    private Inclusion foreignInclusion;

    StageBean(TableIdentifier<T> identifier) {
        this.identifier = requireNonNull(identifier);
        this.predicates = new ArrayList<>();
    }

    StageBean(TableIdentifier<T> identifier, JoinType joinType) {
        this.identifier = requireNonNull(identifier);
        this.predicates = new ArrayList<>();
        this.joinType = requireNonNull(joinType);
    }

    StageBean(JoinType joinType, HasComparableOperators<T, ?> field) {
        requireNonNull(field);
        this.identifier = requireNonNull(field.identifier().asTableIdentifier());
        this.predicates = new ArrayList<>();
        this.joinType = requireNonNull(joinType);
        this.field = field;
    }

    JoinType getJoinType() {
        return joinType;
    }

    void setJoinType(JoinType joinType) {
        this.joinType = requireNonNull(joinType);
    }

    HasComparableOperators<? extends T, ?> getField() {
        return field;
    }

    void setField(HasComparableOperators<T, ?> field) {
        this.field = requireNonNull(field);
    }

    JoinOperator getJoinOperator() {
        return joinOperator;
    }

    void setJoinOperator(JoinOperator joinOperator) {
        this.joinOperator = requireNonNull(joinOperator);
    }

    HasComparableOperators<?, ?> getForeignField() {
        return foreignField;
    }

    void setForeignField(HasComparableOperators<?, ?> foreignFirstField) {
        this.foreignField = requireNonNull(foreignFirstField);
    }

    int getReferencedStage() {
        return referencedStage;
    }

    void setReferencedStage(int referencedStage) {
        this.referencedStage = referencedStage;
    }

//    public HasComparableOperators<?, ?> getForeignSecondField() {
//        return foreignSecondField;
//    }
//
//    public void setForeignSecondField(HasComparableOperators<?, ?> foreignSecondField) {
//        this.foreignSecondField = requireNonNull(foreignSecondField);
//    }

     TableIdentifier<T> getIdentifier() {
        return identifier;
    }

    /**
     * This list is intentionally exposed for mutable operation.
     *
     * @return a mutable list
     */
     List<Predicate<? super T>> getPredicates() {
        return predicates;
    }

//    public Inclusion getForeignInclusion() {
//        return foreignInclusion;
//    }
//
//    public void setForeignInclusion(Inclusion foreignInclusion) {
//        this.foreignInclusion = requireNonNull(foreignInclusion);
//    }

    Stage<T> asStage() {
        return Stage.of(
            identifier,
            predicates,
            joinType,
            field,
            joinOperator,
            foreignField,
            referencedStage
//            foreignSecondField,
//            foreignInclusion
        );
    }

}
