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
package com.speedment.runtime.join.internal.component.stream.sql;

import com.speedment.common.function.QuadFunction;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.internal.component.stream.SqlAdapterMapper;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin4;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Per Minborg
 */
public final class SqlHasCreateJoin4
    extends AbstractSqlHasCreateJoin
    implements HasCreateJoin4 {

    public SqlHasCreateJoin4(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final SqlAdapterMapper sqlAdapterMapper,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        super(dbmsHandlerComponent, project, sqlAdapterMapper, allowStreamIteratorAndSpliterator);
    }

    @Override
    public <T0, T1, T2, T3, T> Join<T> createJoin(
        final List<Stage<?>> stages, 
        final QuadFunction<T0, T1, T2, T3, T> constructor, 
        final TableIdentifier<T0> t0, 
        final TableIdentifier<T1> t1, 
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3
    ) {
        final SqlFunction<ResultSet, T0> rsMapper0 = rsMapper(stages, 0, t0);
        final SqlFunction<ResultSet, T1> rsMapper1 = rsMapper(stages, 1, t1);
        final SqlFunction<ResultSet, T2> rsMapper2 = rsMapper(stages, 2, t2);
        final SqlFunction<ResultSet, T3> rsMapper3 = rsMapper(stages, 3, t3);
        final SqlFunction<ResultSet, T> rsMapper = rs -> constructor.apply(
            rsMapper0.apply(rs),
            rsMapper1.apply(rs),
            rsMapper2.apply(rs),
            rsMapper3.apply(rs)
        );
        return newJoin(stages, rsMapper);
    }

}
