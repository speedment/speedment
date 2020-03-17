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

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.builder.JoinBuilder2;
import com.speedment.runtime.join.builder.JoinBuilder3;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder2Impl<T0, T1>
    extends AbstractJoinBuilder<T1, JoinBuilder2<T0, T1>>
    implements JoinBuilder2<T0, T1> {

    JoinBuilder2Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T1> current) {
        super(previousStage, current);
    }

    @Override
    public <T2> AfterJoin<T0, T1, T2> innerJoinOn(HasComparableOperators<T2, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T2> AfterJoin<T0, T1, T2> leftJoinOn(HasComparableOperators<T2, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T2> AfterJoin<T0, T1, T2> rightJoinOn(HasComparableOperators<T2, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T2> JoinBuilder3<T0, T1, T2> crossJoin(TableIdentifier<T2> joinedTable) {
        return new JoinBuilder3Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T2>
        extends BaseAfterJoin<T2, JoinBuilder3<T0, T1, T2>>
        implements AfterJoin<T0, T1, T2> {

        private AfterJoinImpl(StageBean<T2> stageBean) {
            super(JoinBuilder2Impl.this, stageBean, JoinBuilder3Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(BiFunction<T0, T1, T> constructor) {
        requireNonNull(constructor);
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T0>) stages.get(0).identifier(),
            (TableIdentifier<T1>) stages.get(1).identifier()
        );
    }

}