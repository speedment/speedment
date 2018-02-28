package com.speedment.runtime.join.internal.component.stream.sql;

import com.speedment.common.function.Function6;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.internal.component.stream.SqlAdapterMapper;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin6;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Per Minborg
 */
public final class SqlHasCreateJoin6
    extends AbstractSqlHasCreateJoin
    implements HasCreateJoin6 {

    public SqlHasCreateJoin6(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final Project project,
        final SqlAdapterMapper sqlAdapterMapper
    ) {
        super(dbmsHandlerComponent, project, sqlAdapterMapper);
    }

    @Override
    public <T1, T2, T3, T4, T5, T6, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final Function6<T1, T2, T3, T4, T5, T6, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4,
        final TableIdentifier<T5> t5,
        final TableIdentifier<T6> t6
    ) {
        final SqlFunction<ResultSet, T1> rsMapper1 = rsMapper(stages, 0, t1);
        final SqlFunction<ResultSet, T2> rsMapper2 = rsMapper(stages, 1, t2);
        final SqlFunction<ResultSet, T3> rsMapper3 = rsMapper(stages, 2, t3);
        final SqlFunction<ResultSet, T4> rsMapper4 = rsMapper(stages, 3, t4);
        final SqlFunction<ResultSet, T5> rsMapper5 = rsMapper(stages, 4, t5);
        final SqlFunction<ResultSet, T6> rsMapper6 = rsMapper(stages, 5, t6);
        final SqlFunction<ResultSet, T> rsMapper = rs -> constructor.apply(
            rsMapper1.apply(rs),
            rsMapper2.apply(rs),
            rsMapper3.apply(rs),
            rsMapper4.apply(rs),
            rsMapper5.apply(rs),
            rsMapper6.apply(rs)
        );
        return newJoin(stages, rsMapper);
    }

}
