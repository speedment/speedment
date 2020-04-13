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
package com.speedment.runtime.join.internal.component.stream;

import com.speedment.common.function.*;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.Execute;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.SqlAdapter;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin10;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin2;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin3;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin4;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin5;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin6;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin7;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin8;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin9;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.*;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Per Minborg
 */
public final class SqlJoinStreamSupplierComponent
    implements JoinStreamSupplierComponent {

    private final boolean allowStreamIteratorAndSpliterator;

    private Map<TableIdentifier<?>, SqlAdapter<?>> sqlAdapterMap;
    private HasCreateJoin2 join2Creator;
    private HasCreateJoin3 join3Creator;
    private HasCreateJoin4 join4Creator;
    private HasCreateJoin5 join5Creator;
    private HasCreateJoin6 join6Creator;
    private HasCreateJoin7 join7Creator;
    private HasCreateJoin8 join8Creator;
    private HasCreateJoin9 join9Creator;
    private HasCreateJoin10 join10Creator;

    public SqlJoinStreamSupplierComponent(
        @Config(name = "allowStreamIteratorAndSpliterator", value = "false") final boolean allowStreamIteratorAndSpliterator
    ) {
        this.allowStreamIteratorAndSpliterator = allowStreamIteratorAndSpliterator;
    }

    @Execute
    public void init(
        final Injector injector,
        final ProjectComponent projectComponent,
        final DbmsHandlerComponent dbmsHandlerComponent
    ) {
        final Project project = projectComponent.getProject();
        sqlAdapterMap = injector.stream(SqlAdapter.class)
            .map(sa -> (SqlAdapter<?>) sa)
            .collect(
                toMap(
                    SqlAdapter::identifier,
                    sa -> sa
                )
            );

        join2Creator = new SqlHasCreateJoin2(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join3Creator = new SqlHasCreateJoin3(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join4Creator = new SqlHasCreateJoin4(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join5Creator = new SqlHasCreateJoin5(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join6Creator = new SqlHasCreateJoin6(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join7Creator = new SqlHasCreateJoin7(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join8Creator = new SqlHasCreateJoin8(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join9Creator = new SqlHasCreateJoin9(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
        join10Creator = new SqlHasCreateJoin10(dbmsHandlerComponent, project, this::sqlAdapterMapper, allowStreamIteratorAndSpliterator);
    }

    @Override
    public <T0, T1, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final BiFunction<T0, T1, T> constructor,
        final TableIdentifier<T0> t0,
        final TableIdentifier<T1> t1
    ) {
        return join2Creator.createJoin(stages, constructor, t0, t1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final TriFunction<T0, T1, T2, T> constructor,
        final TableIdentifier<T0> t0,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2
    ) {
        return join3Creator.createJoin(stages, constructor, t0, t1, t2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final QuadFunction<T0, T1, T2, T3, T> constructor,
        final TableIdentifier<T0> t0,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3
    ) {
        return join4Creator.createJoin(stages, constructor, t0, t1, t2, t3);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final Function5<T0, T1, T2, T3, T4, T> constructor,
        final TableIdentifier<T0> t0,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4
    ) {
        return join5Creator.createJoin(stages, constructor, t0, t1, t2, t3, t4);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T5, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final Function6<T0, T1, T2, T3, T4, T5, T> constructor,
        final TableIdentifier<T0> t0,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4,
        final TableIdentifier<T5> t5
    ) {
        return join6Creator.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T> Join<T> createJoin(
            List<Stage<?>> stages,
            Function7<T0, T1, T2, T3, T4, T5, T6, T> constructor,
            TableIdentifier<T0> t0,
            TableIdentifier<T1> t1,
            TableIdentifier<T2> t2,
            TableIdentifier<T3> t3,
            TableIdentifier<T4> t4,
            TableIdentifier<T5> t5,
            TableIdentifier<T6> t6
    ) {
        return join7Creator.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T7, T> Join<T> createJoin(
            List<Stage<?>> stages,
            Function8<T0, T1, T2, T3, T4, T5, T6, T7, T> constructor,
            TableIdentifier<T0> t0,
            TableIdentifier<T1> t1,
            TableIdentifier<T2> t2,
            TableIdentifier<T3> t3,
            TableIdentifier<T4> t4,
            TableIdentifier<T5> t5,
            TableIdentifier<T6> t6,
            TableIdentifier<T7> t7
    ) {
        return join8Creator.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6, t7);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T7, T8, T> Join<T> createJoin(
            List<Stage<?>> stages,
            Function9<T0, T1, T2, T3, T4, T5, T6, T7, T8, T> constructor,
            TableIdentifier<T0> t0,
            TableIdentifier<T1> t1,
            TableIdentifier<T2> t2,
            TableIdentifier<T3> t3,
            TableIdentifier<T4> t4,
            TableIdentifier<T5> t5,
            TableIdentifier<T6> t6,
            TableIdentifier<T7> t7,
            TableIdentifier<T8> t8
    ) {
        return join9Creator.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6, t7, t8);
    }

    @Override
    public <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T> Join<T> createJoin(
            List<Stage<?>> stages,
            Function10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T> constructor,
            TableIdentifier<T0> t0,
            TableIdentifier<T1> t1,
            TableIdentifier<T2> t2,
            TableIdentifier<T3> t3,
            TableIdentifier<T4> t4,
            TableIdentifier<T5> t5,
            TableIdentifier<T6> t6,
            TableIdentifier<T7> t7,
            TableIdentifier<T8> t8,
            TableIdentifier<T9> t9
    ) {
        return join10Creator.createJoin(stages, constructor, t0, t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    private <ENTITY> SqlAdapter<ENTITY> sqlAdapterMapper(TableIdentifier<ENTITY> identifier) {
        @SuppressWarnings("unchecked")
        final SqlAdapter<ENTITY> result = (SqlAdapter<ENTITY>) sqlAdapterMap.get(identifier);
        if (result == null) {
            throw new IllegalArgumentException(
                "There is no mapping for " + identifier + " "
                + "The following " + sqlAdapterMap.size() + " mappings are available: "
                + sqlAdapterMap.keySet().stream().map(Object::toString).collect(joining(", "))
            );
        }
        return result;
    }

}
