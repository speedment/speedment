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

import com.speedment.common.function.Function10;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.builder.JoinBuilder10;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder10Impl<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>
    extends AbstractJoinBuilder<T9, JoinBuilder10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9>>
    implements JoinBuilder10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9> {

    JoinBuilder10Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T9> current) {
        super(previousStage, current);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T> constructor) {
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
            (TableIdentifier<T7>) stages.get(7).identifier(),
            (TableIdentifier<T8>) stages.get(8).identifier(),
            (TableIdentifier<T9>) stages.get(9).identifier()
        );
    }

}
