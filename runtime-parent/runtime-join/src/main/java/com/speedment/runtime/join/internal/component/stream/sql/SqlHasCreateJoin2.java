package com.speedment.runtime.join.internal.component.stream.sql;

import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.internal.JoinImpl;
import com.speedment.runtime.join.internal.component.stream.SqlAdapterMapper;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin2;
import java.sql.ResultSet;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;

/**
 *
 * @author Per Minborg
 */
public final class SqlHasCreateJoin2 implements HasCreateJoin2 {

    final DbmsHandlerComponent dbmsHandlerComponent;
    final Project project;
    final SqlAdapterMapper sqlAdapterMapper;

    public SqlHasCreateJoin2(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final SqlAdapterMapper sqlAdapterMapper
    ) {
        this.dbmsHandlerComponent = requireNonNull(dbmsHandlerComponent);
        this.project = requireNonNull(project);
        this.sqlAdapterMapper = requireNonNull(sqlAdapterMapper);
    }

    @Override
    public <T1, T2, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final BiFunction<T1, T2, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2
    ) {
        final SqlFunction<ResultSet, T> rsMapper = rs -> constructor.apply(
            JoinSqlUtil.resultSetMapper(project, t1, stages, 0, sqlAdapterMapper).apply(rs),
            JoinSqlUtil.resultSetMapper(project, t2, stages, 1, sqlAdapterMapper).apply(rs)
        );
        return new JoinImpl<>(
            () -> JoinSqlUtil.stream(dbmsHandlerComponent, project, stages, rsMapper)
        );
    }

}
