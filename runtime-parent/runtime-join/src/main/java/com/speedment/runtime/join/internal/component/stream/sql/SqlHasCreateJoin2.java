package com.speedment.runtime.join.internal.component.stream.sql;

import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.internal.component.stream.SqlAdapterMapper;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin2;
import java.sql.ResultSet;
import java.util.List;
import java.util.function.BiFunction;

/**
 *
 * @author Per Minborg
 */
public final class SqlHasCreateJoin2
    extends AbstractSqlHasCreateJoin
    implements HasCreateJoin2 {

    public SqlHasCreateJoin2(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final SqlAdapterMapper sqlAdapterMapper
    ) {
        super(dbmsHandlerComponent, project, sqlAdapterMapper);
    }

    @Override
    public <T1, T2, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final BiFunction<T1, T2, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2
    ) {
        final SqlFunction<ResultSet, T1> rsMapper1 = rsMapper(stages, 0, t1);
        final SqlFunction<ResultSet, T2> rsMapper2 = rsMapper(stages, 1, t2);
        final SqlFunction<ResultSet, T> rsMapper = rs -> constructor.apply(
            rsMapper1.apply(rs),
            rsMapper2.apply(rs)
        );
        return newJoin(stages, rsMapper);
    }

}
