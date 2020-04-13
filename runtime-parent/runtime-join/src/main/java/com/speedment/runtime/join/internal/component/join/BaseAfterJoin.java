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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.stage.JoinOperator;
import com.speedment.runtime.join.trait.HasOnPredicates;

import java.util.function.BiFunction;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
class BaseAfterJoin<T, R> implements HasOnPredicates<R> {

    private final AbstractJoinBuilder<?, ?> currentBuilderStage;
    private final StageBean<T> stageBean;
    private final BiFunction<AbstractJoinBuilder<?, ?>, StageBean<T>, R> constructor;

    BaseAfterJoin(
        final AbstractJoinBuilder<?, ?> currentBuilderStage,
        final StageBean<T> stageBean,
        final BiFunction<AbstractJoinBuilder<?, ?>, StageBean<T>, R> constructor
    ) {
        this.currentBuilderStage = requireNonNull(currentBuilderStage);
        this.stageBean = requireNonNull(stageBean);
        this.constructor = requireNonNull(constructor);
    }

    @Override
    public R equal(HasComparableOperators<?, ?> joinedField) {
        return operation(JoinOperator.EQUAL, joinedField);
    }

    @Override
    public R notEqual(HasComparableOperators<?, ?> joinedField) {
        return operation(JoinOperator.NOT_EQUAL, joinedField);
    }

    @Override
    public R lessThan(HasComparableOperators<?, ?> joinedField) {
        return operation(JoinOperator.LESS_THAN, joinedField);
    }

    @Override
    public R lessOrEqual(HasComparableOperators<?, ?> joinedField) {
        return operation(JoinOperator.LESS_OR_EQUAL, joinedField);
    }

    @Override
    public R greaterThan(HasComparableOperators<?, ?> joinedField) {
        return operation(JoinOperator.GREATER_THAN, joinedField);
    }

    @Override
    public R greaterOrEqual(HasComparableOperators<?, ?> joinedField) {
        return operation(JoinOperator.GREATER_OR_EQUAL, joinedField);
    }

//    @Override
//    public <ENTITY> R between(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo) {
//        return between(joinedFieldFrom, joinedFieldTo, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
//    }
//
//    @Override
//    public <ENTITY> R between(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo, Inclusion inclusion) {
//        return operation(JoinOperator.BETWEEN, joinedFieldFrom, joinedFieldTo, inclusion);
//    }
//
//    @Override
//    public <ENTITY> R notBetween(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo) {
//        return notBetween(joinedFieldFrom, joinedFieldTo, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
//    }
//
//    @Override
//    public <ENTITY> R notBetween(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo, Inclusion inclusion) {
//        return operation(JoinOperator.NOT_BETWEEN, joinedFieldFrom, joinedFieldTo, inclusion);
//    }

    private R operation(JoinOperator operatorType, HasComparableOperators<?, ?> joinedField) {
        stageBean.setJoinOperator(operatorType);
        stageBean.setForeignField(joinedField);
        return constructor.apply(currentBuilderStage, stageBean);
    }

//    private <ENTITY> R operation(
//        final JoinOperator operatorType,
//        final HasComparableOperators<ENTITY, ?> joinedFieldFrom,
//        final HasComparableOperators<ENTITY, ?> joinedFieldTo,
//        final Inclusion inclusion
//    ) {
//        stageBean.setJoinOperator(operatorType);
//        stageBean.setForeignFirstField(joinedFieldFrom);
//        stageBean.setForeignSecondField(joinedFieldTo);
//        stageBean.setForeignInclusion(inclusion);
//        return constructor.apply(currentBuilderStage, stageBean);
//    }

}
