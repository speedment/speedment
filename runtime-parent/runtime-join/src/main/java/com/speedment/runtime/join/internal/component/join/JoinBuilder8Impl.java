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

import com.speedment.common.function.Function8;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.builder.JoinBuilder8;
import com.speedment.runtime.join.builder.JoinBuilder9;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder8Impl<T0, T1, T2, T3, T4, T5, T6, T7>
    extends AbstractJoinBuilder<T7, JoinBuilder8<T0, T1, T2, T3, T4, T5, T6, T7>>
    implements JoinBuilder8<T0, T1, T2, T3, T4, T5, T6, T7> {

    JoinBuilder8Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T7> current) {
        super(previousStage, current);
    }

    @Override
    public <T8> AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7, T8> innerJoinOn(HasComparableOperators<T8, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T8> AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7, T8> leftJoinOn(HasComparableOperators<T8, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T8> AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7, T8> rightJoinOn(HasComparableOperators<T8, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T8> JoinBuilder9<T0, T1, T2, T3, T4, T5, T6, T7, T8> crossJoin(TableIdentifier<T8> joinedTable) {
        return new JoinBuilder9Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T8>
        extends BaseAfterJoin<T8, JoinBuilder9<T0, T1, T2, T3, T4, T5, T6, T7, T8>>
        implements AfterJoin<T0, T1, T2, T3, T4, T5, T6, T7, T8> {

        private AfterJoinImpl(StageBean<T8> stageBean) {
            super(JoinBuilder8Impl.this, stageBean, JoinBuilder9Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function8<T0, T1, T2, T3, T4, T5, T6, T7, T> constructor) {
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
            (TableIdentifier<T5>) stages.get(5).identifier(),
            (TableIdentifier<T6>) stages.get(6).identifier(),
            (TableIdentifier<T7>) stages.get(7).identifier()
        );
    }

}