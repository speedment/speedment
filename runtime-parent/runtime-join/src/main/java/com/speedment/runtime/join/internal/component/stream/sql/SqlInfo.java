package com.speedment.runtime.join.internal.component.stream.sql;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
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
public final class SqlInfo {

    private final Project project;
    private final Dbms dbms;
    private final DbmsType dbmsType;
    private final DatabaseNamingConvention naming;
    private final FieldPredicateView fieldPredicateView;
    private final List<Stage<?>> stages;

    public SqlInfo(
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

    public Project project() {
        return project;
    }

    public Dbms dbms() {
        return dbms;
    }

    public DbmsType dbmsType() {
        return dbmsType;
    }

    public DatabaseNamingConvention namingConvention() {
        return naming;
    }

    public FieldPredicateView fieldPredicateView() {
        return fieldPredicateView;
    }

    public List<SqlStage> sqlStages() {
        final List<SqlStage> sqlStages = new ArrayList<>();
        for (int i = 0; i < stages.size(); i++) {
            sqlStages.add(new SqlStage(this, stages.get(i), i));
        }
        return sqlStages;
    }

}
