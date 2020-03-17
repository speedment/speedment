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
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.builder.JoinBuilder1;
import com.speedment.runtime.join.builder.JoinBuilder2;
import com.speedment.runtime.join.stage.JoinType;

/**
 *
 * @author Per Minborg
 * @param <T0> entity type of the first table
 */
final class JoinBuilder1Impl<T0>
    extends AbstractJoinBuilder<T0, JoinBuilder1<T0>>
    implements JoinBuilder1<T0> {

    JoinBuilder1Impl(JoinStreamSupplierComponent streamSupplier, TableIdentifier<T0> initialTable) {
        super(streamSupplier, initialTable);
    }

    @Override
    public <T1> AfterJoin<T0, T1> innerJoinOn(HasComparableOperators<T1, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T1> AfterJoin<T0, T1> leftJoinOn(HasComparableOperators<T1, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T1> AfterJoin<T0, T1> rightJoinOn(HasComparableOperators<T1, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T1> JoinBuilder2<T0, T1> crossJoin(TableIdentifier<T1> joinedTable) {
        return new JoinBuilder2Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T1>
        extends BaseAfterJoin<T1, JoinBuilder2<T0, T1>>
        implements AfterJoin<T0, T1> {

        private AfterJoinImpl(StageBean<T1> stageBen) {
            super(JoinBuilder1Impl.this, stageBen, JoinBuilder2Impl::new);
        }
    }

}