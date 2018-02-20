/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.stage.OperatorType;
import com.speedment.runtime.join.trait.HasOnPredicates;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;

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
        return operation(OperatorType.EQUAL, joinedField);
    }

    @Override
    public R notEqual(HasComparableOperators<?, ?> joinedField) {
        return operation(OperatorType.NOT_EQUAL, joinedField);
    }

    @Override
    public R lessThan(HasComparableOperators<?, ?> joinedField) {
        return operation(OperatorType.LESS_THAN, joinedField);
    }

    @Override
    public R lessOrEqual(HasComparableOperators<?, ?> joinedField) {
        return operation(OperatorType.LESS_OR_EQUAL, joinedField);
    }

    @Override
    public R greaterThan(HasComparableOperators<?, ?> joinedField) {
        return operation(OperatorType.GREATER_THAN, joinedField);
    }

    @Override
    public R greaterOrEqual(HasComparableOperators<?, ?> joinedField) {
        return operation(OperatorType.GREATER_OR_EQUAL, joinedField);
    }

    @Override
    public <ENTITY> R between(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo) {
        return between(joinedFieldFrom, joinedFieldTo, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    }

    @Override
    public <ENTITY> R between(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo, Inclusion inclusion) {
        return operation(OperatorType.BETWEEN, joinedFieldFrom, joinedFieldTo, inclusion);
    }

    @Override
    public <ENTITY> R notBetween(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo) {
        return notBetween(joinedFieldFrom, joinedFieldTo, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    }

    @Override
    public <ENTITY> R notBetween(HasComparableOperators<ENTITY, ?> joinedFieldFrom, HasComparableOperators<ENTITY, ?> joinedFieldTo, Inclusion inclusion) {
        return operation(OperatorType.NOT_BETWEEN, joinedFieldFrom, joinedFieldTo, inclusion);
    }

    private R operation(OperatorType operatorType, HasComparableOperators<?, ?> joinedField) {
        stageBean.setOperatorType(operatorType);
        stageBean.setForeignFirstField(joinedField);
        return constructor.apply(currentBuilderStage, stageBean);
    }

    private <ENTITY> R operation(
        final OperatorType operatorType,
        final HasComparableOperators<ENTITY, ?> joinedFieldFrom,
        final HasComparableOperators<ENTITY, ?> joinedFieldTo,
        final Inclusion inclusion
    ) {
        stageBean.setOperatorType(operatorType);
        stageBean.setForeignFirstField(joinedFieldFrom);
        stageBean.setForeignSecondField(joinedFieldTo);
        stageBean.setForeignInclusion(inclusion);
        return constructor.apply(currentBuilderStage, stageBean);
    }

}
