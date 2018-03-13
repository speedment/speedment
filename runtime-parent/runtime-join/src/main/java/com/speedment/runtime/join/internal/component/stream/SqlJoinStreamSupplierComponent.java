package com.speedment.runtime.join.internal.component.stream;

import com.speedment.common.function.Function5;
import com.speedment.common.function.Function6;
import com.speedment.common.function.QuadFunction;
import com.speedment.common.function.TriFunction;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Execute;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.SqlAdapter;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin2;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin3;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin4;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin5;
import com.speedment.runtime.join.internal.component.stream.sql.SqlHasCreateJoin6;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin2;
import com.speedment.runtime.join.trait.HasCreateJoin3;
import com.speedment.runtime.join.trait.HasCreateJoin4;
import com.speedment.runtime.join.trait.HasCreateJoin5;
import com.speedment.runtime.join.trait.HasCreateJoin6;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Per Minborg
 */
public class SqlJoinStreamSupplierComponent implements JoinStreamSupplierComponent {

    private Map<TableIdentifier<?>, SqlAdapter<?>> sqlAdapterMap;
    private HasCreateJoin2 join2Creator;
    private HasCreateJoin3 join3Creator;
    private HasCreateJoin4 join4Creator;
    private HasCreateJoin5 join5Creator;
    private HasCreateJoin6 join6Creator;

    @Execute
    void init(
        final Injector injector,
        final ProjectComponent projectComponent,
        final DbmsHandlerComponent dbmsHandlerComponent
    ) {
        final Project project = projectComponent.getProject();
        sqlAdapterMap = injector.stream(SqlAdapter.class)
            .map(sa -> (SqlAdapter<?>) sa)
            .collect(
                toMap(
                    sa -> sa.identifier(),
                    sa -> sa
                )
            );

        join2Creator = new SqlHasCreateJoin2(dbmsHandlerComponent, project, this::sqlAdapterMapper);
        join3Creator = new SqlHasCreateJoin3(dbmsHandlerComponent, project, this::sqlAdapterMapper);
        join4Creator = new SqlHasCreateJoin4(dbmsHandlerComponent, project, this::sqlAdapterMapper);
        join5Creator = new SqlHasCreateJoin5(dbmsHandlerComponent, project, this::sqlAdapterMapper);
        join6Creator = new SqlHasCreateJoin6(dbmsHandlerComponent, project, this::sqlAdapterMapper);
    }

    @Override
    public <T1, T2, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final BiFunction<T1, T2, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2
    ) {
        return join2Creator.createJoin(stages, constructor, t1, t2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1, T2, T3, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final TriFunction<T1, T2, T3, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3
    ) {
        return join3Creator.createJoin(stages, constructor, t1, t2, t3);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1, T2, T3, T4, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final QuadFunction<T1, T2, T3, T4, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4
    ) {
        return join4Creator.createJoin(stages, constructor, t1, t2, t3, t4);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1, T2, T3, T4, T5, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final Function5<T1, T2, T3, T4, T5, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4,
        final TableIdentifier<T5> t5
    ) {
        return join5Creator.createJoin(stages, constructor, t1, t2, t3, t4, t5);
    }

    @Override
    @SuppressWarnings("unchecked")
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
        return join6Creator.createJoin(stages, constructor, t1, t2, t3, t4, t5, t6);
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
