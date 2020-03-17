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

import com.speedment.common.function.Function5;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.builder.JoinBuilder5;
import com.speedment.runtime.join.builder.JoinBuilder6;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder5Impl<T0, T1, T2, T3, T4>
    extends AbstractJoinBuilder<T4, JoinBuilder5<T0, T1, T2, T3, T4>>
    implements JoinBuilder5<T0, T1, T2, T3, T4> {

    JoinBuilder5Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T4> current) {
        super(previousStage, current);
    }

    @Override
    public <T5> AfterJoin<T0, T1, T2, T3, T4, T5> innerJoinOn(HasComparableOperators<T5, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T5> AfterJoin<T0, T1, T2, T3, T4, T5> leftJoinOn(HasComparableOperators<T5, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T5> AfterJoin<T0, T1, T2, T3, T4, T5> rightJoinOn(HasComparableOperators<T5, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T5> JoinBuilder6<T0, T1, T2, T3, T4, T5> crossJoin(TableIdentifier<T5> joinedTable) {
        return new JoinBuilder6Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T5>
        extends BaseAfterJoin<T5, JoinBuilder6<T0, T1, T2, T3, T4, T5>>
        implements AfterJoin<T0, T1, T2, T3, T4, T5> {

        private AfterJoinImpl(StageBean<T5> stageBean) {
            super(JoinBuilder5Impl.this, stageBean, JoinBuilder6Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function5<T0, T1, T2, T3, T4, T> constructor) {
        requireNonNull(constructor);
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T0>) stages.get(0).identifier(),
            (TableIdentifier<T1>) stages.get(1).identifier(),
            (TableIdentifier<T2>) stages.get(2).identifier(),
            (TableIdentifier<T3>) stages.get(3).identifier(),
            (TableIdentifier<T4>) stages.get(4).identifier()
        );
    }

}