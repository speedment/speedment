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

import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.internal.JoinImpl;
import com.speedment.runtime.join.internal.component.stream.SqlAdapterMapper;
import static com.speedment.runtime.join.internal.component.stream.sql.JoinSqlUtil.resultSetMapper;
import com.speedment.runtime.join.stage.Stage;
import java.sql.ResultSet;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
abstract class AbstractSqlHasCreateJoin {

    private final DbmsHandlerComponent dbmsHandlerComponent;
    private final Project project;
    private final SqlAdapterMapper sqlAdapterMapper;
    private final boolean allowStreamIteratorAndSpliterator;

    AbstractSqlHasCreateJoin(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final SqlAdapterMapper sqlAdapterMapper,
        final boolean allowStreamIteratorAndSpliterator
    ) {
        this.dbmsHandlerComponent = requireNonNull(dbmsHandlerComponent);
        this.project = requireNonNull(project);
        this.sqlAdapterMapper = requireNonNull(sqlAdapterMapper);
        this.allowStreamIteratorAndSpliterator = allowStreamIteratorAndSpliterator;
    }

    <T> SqlFunction<ResultSet, T> rsMapper(
        final List<Stage<?>> stages,
        final int stageIndex,
        final TableIdentifier<T> identifier
    ) {
        return resultSetMapper(project, identifier, stages, stageIndex, sqlAdapterMapper);
    }

    <T> Join<T> newJoin(final List<Stage<?>> stages, final SqlFunction<ResultSet, T> rsMapper) {
        requireNonNull(stages);
        requireNonNull(rsMapper);
        return new JoinImpl<>(
            () -> JoinSqlUtil.stream(dbmsHandlerComponent, project, stages, rsMapper, allowStreamIteratorAndSpliterator)
        );
    }

}
