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

import com.speedment.common.function.TriFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.builder.JoinBuilder3;
import com.speedment.runtime.join.builder.JoinBuilder4;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder3Impl<T0, T1, T2>
    extends AbstractJoinBuilder<T2, JoinBuilder3<T0, T1, T2>>
    implements JoinBuilder3<T0, T1, T2> {

    JoinBuilder3Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T2> current) {
        super(previousStage, current);
    }

    @Override
    public <T3> AfterJoin<T0, T1, T2, T3> innerJoinOn(HasComparableOperators<T3, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T3> AfterJoin<T0, T1, T2, T3> leftJoinOn(HasComparableOperators<T3, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T3> AfterJoin<T0, T1, T2, T3> rightJoinOn(HasComparableOperators<T3, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T3> JoinBuilder4<T0, T1, T2, T3> crossJoin(TableIdentifier<T3> joinedTable) {
        return new JoinBuilder4Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T3>
        extends BaseAfterJoin<T3, JoinBuilder4<T0, T1, T2, T3>>
        implements AfterJoin<T0, T1, T2, T3> {

        private AfterJoinImpl(StageBean<T3> stageBean) {
            super(JoinBuilder3Impl.this, stageBean, JoinBuilder4Impl::new);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(TriFunction<T0, T1, T2, T> constructor) {
        requireNonNull(constructor);
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T0>) stages.get(0).identifier(),
            (TableIdentifier<T1>) stages.get(1).identifier(),
            (TableIdentifier<T2>) stages.get(2).identifier()
        );
    }

}
