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

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.util.DatabaseUtil;
import static com.speedment.runtime.join.internal.component.stream.sql.JoinSqlUtil.requireSameDbms;
import com.speedment.runtime.join.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class SqlInfo {

    private final Project project;
    private final Dbms dbms;
    private final DbmsType dbmsType;
    private final DatabaseNamingConvention naming;
    private final FieldPredicateView fieldPredicateView;
    private final List<Stage<?>> stages;

    SqlInfo(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final List<Stage<?>> stages
    ) {
        requireNonNull(project);
        requireNonNull(dbmsHandlerComponent);
        this.stages = requireNonNull(stages);
        this.project = project;
        this.dbms = requireSameDbms(project, stages);
        this.dbmsType = DatabaseUtil.dbmsTypeOf(dbmsHandlerComponent, dbms);
        this.naming = dbmsType.getDatabaseNamingConvention();
        this.fieldPredicateView = dbmsType.getFieldPredicateView();
    }

    Project project() {
        return project;
    }

    Dbms dbms() {
        return dbms;
    }

    DbmsType dbmsType() {
        return dbmsType;
    }

    DatabaseNamingConvention namingConvention() {
        return naming;
    }

    FieldPredicateView fieldPredicateView() {
        return fieldPredicateView;
    }

    List<SqlStage> sqlStages() {
        final List<SqlStage> sqlStages = new ArrayList<>();
        for (int i = 0; i < stages.size(); i++) {
            sqlStages.add(new SqlStage(this, stages.get(i), i));
        }
        return sqlStages;
    }

}
