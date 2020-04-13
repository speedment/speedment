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

import static com.speedment.common.invariant.IntRangeUtil.requireNonNegative;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.join.stage.Stage;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Per Minborg
 */
final class SqlStage {

    private final Stage<?> stage;
    private final int stageIndex;
    private final Table table;
    private final String sqlColumnList;
    private final String sqlTableReference;

    SqlStage(
        final SqlInfo info,
        final Stage<?> stage,
        final int stageIndex
    ) {
        requireNonNull(info);
        this.stage = requireNonNull(stage);
        this.stageIndex = requireNonNegative(stageIndex);
        this.table = DocumentDbUtil.referencedTable(info.project(), stage.identifier());
        final String tableAlias = JoinSqlUtil.tableAlias(stageIndex);

        this.sqlColumnList = table.columns()
            .filter(Column::isEnabled)
            .map(Column::getName)
            .map(n -> tableAlias + "." + info.namingConvention().encloseField(n))
            .collect(joining(","));

        this.sqlTableReference = info.namingConvention().fullNameOf(table)
            + ((DbmsType.SubSelectAlias.PROHIBITED == info.dbmsType().getSubSelectAlias()) ? " " : " AS ")
            + tableAlias;
    }

    Stage<?> stage() {
        return stage;
    }

    int stageIndex() {
        return stageIndex;
    }

    Table table() {
        return table;
    }

    String sqlColumnList() {
        return sqlColumnList;
    }

    String sqlTableReference() {
        return sqlTableReference;
    }

}
