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

import com.speedment.common.function.Function6;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.builder.JoinBuilder6;
import com.speedment.runtime.join.builder.JoinBuilder7;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder6Impl<T0, T1, T2, T3, T4, T5>
    extends AbstractJoinBuilder<T5, JoinBuilder6<T0, T1, T2, T3, T4, T5>>
    implements JoinBuilder6<T0, T1, T2, T3, T4, T5> {

    JoinBuilder6Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T5> current) {
        super(previousStage, current);
    }

    @Override
    public <T6> AfterJoin<T0, T1, T2, T3, T4, T5, T6> innerJoinOn(HasComparableOperators<T6, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T6> AfterJoin<T0, T1, T2, T3, T4, T5, T6> leftJoinOn(HasComparableOperators<T6, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T6> AfterJoin<T0, T1, T2, T3, T4, T5, T6> rightJoinOn(HasComparableOperators<T6, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T6> JoinBuilder7<T0, T1, T2, T3, T4, T5, T6> crossJoin(TableIdentifier<T6> joinedTable) {
        return new JoinBuilder7Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T6>
        extends BaseAfterJoin<T6, JoinBuilder7<T0, T1, T2, T3, T4, T5, T6>>
        implements AfterJoin<T0, T1, T2, T3, T4, T5, T6> {

        private AfterJoinImpl(StageBean<T6> stageBean) {
            super(JoinBuilder6Impl.this, stageBean, JoinBuilder7Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function6<T0, T1, T2, T3, T4, T5, T> constructor) {
        requireNonNull(constructor);
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T0>) stages.get(0).identifier(),
            (TableIdentifier<T1>) stages.get(1).identifier(),
            (TableIdentifier<T2>) stages.get(2).identifier(),
            (TableIdentifier<T3>) stages.get(3).identifier(),
            (TableIdentifier<T4>) stages.get(4).identifier(),
            (TableIdentifier<T5>) stages.get(5).identifier()
        );
    }

}