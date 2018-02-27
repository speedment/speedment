package com.speedment.runtime.join.internal.component.stream.sql;

import static com.speedment.common.invariant.IntRangeUtil.requireNonNegative;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.join.stage.Stage;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Per Minborg
 */
public final class SqlStage {

    private final Stage<?> stage;
    private final int stageIndex;
    private final Table table;
    private final String sqlColumnList;
    private final String sqlTableReference;

    public SqlStage(
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

        this.sqlTableReference = info.namingConvention().fullNameOf(table) + " AS " + tableAlias;
    }

    public Stage<?> stage() {
        return stage;
    }

    public int stageIndex() {
        return stageIndex;
    }

    public Table table() {
        return table;
    }

    public String sqlColumnList() {
        return sqlColumnList;
    }

    public String sqlTableReference() {
        return sqlTableReference;
    }

}
