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
package com.speedment.runtime.join.provider;

import com.speedment.common.function.*;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.Execute;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.component.stream.SqlJoinStreamSupplierComponent;
import com.speedment.runtime.join.stage.Stage;

import java.util.List;
import java.util.function.BiFunction;

public class DelegateSqlJoinStreamSupplierComponent implements JoinStreamSupplierComponent {

    private final SqlJoinStreamSupplierComponent inner;

    public DelegateSqlJoinStreamSupplierComponent(
        @Config(name = "allowStreamIteratorAndSpliterator", value = "false") final boolean allowStreamIteratorAndSpliterator
    ) {
        this.inner = new SqlJoinStreamSupplierComponent(allowStreamIteratorAndSpliterator);
    }

    @Execute
    public void init(Injector injector, ProjectComponent projectComponent, DbmsHandlerComponent dbmsHandlerComponent) {
        inner.init(injector, projectComponent, dbmsHandlerComponent);
    }

    @Override
    public <T0, T1, T> Join<T> createJoin(List<Stage<?>> stages, BiFunction<T0, T1, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1) {
        return inner.createJoin(stages, constructor, t0, t1);
    }

    @Override
    public <T0, T1, T2, T> Join<T> createJoin(List<Stage<?>> stages, TriFunction<T0, T1, T2, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2) {
        return inner.createJoin(stages, constructor, t0, t1, t2);
    }

    @Override
    public <T0, T1, T2, T3, T> Join<T> createJoin(List<Stage<?>> stages, QuadFunction<T0, T1, T2, T3, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3) {
        return inner.createJoin(stages, constructor, t0, t1, t2, t3);
    }

    @Override
    public <T0, T1, T2, T3, T4, T> Join<T> createJoin(List<Stage<?>> stages, Function5<T0, T1, T2, T3, T4, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4) {
        return inner.createJoin(stages, constructor, t0, t1, t2, t3, t4);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T> Join<T> createJoin(List<Stage<?>> stages, Function6<T0, T1, T2, T3, T4, T5, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5) {
        return inner.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T> Join<T> createJoin(List<Stage<?>> stages, Function7<T0, T1, T2, T3, T4, T5, T6, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6) {
        return inner.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T7, T> Join<T> createJoin(List<Stage<?>> stages, Function8<T0, T1, T2, T3, T4, T5, T6, T7, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6, TableIdentifier<T7> t7) {
        return inner.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6, t7);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T7, T8, T> Join<T> createJoin(List<Stage<?>> stages, Function9<T0, T1, T2, T3, T4, T5, T6, T7, T8, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6, TableIdentifier<T7> t7, TableIdentifier<T8> t8) {
        return inner.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6, t7, t8);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T> Join<T> createJoin(List<Stage<?>> stages, Function10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6, TableIdentifier<T7> t7, TableIdentifier<T8> t8, TableIdentifier<T9> t9) {
        return inner.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }
}
