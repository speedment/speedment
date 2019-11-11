package com.speedment.runtime.connector.sqlite.provider;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.connector.sqlite.SqliteMetadataHandler;
import com.speedment.runtime.connector.sqlite.internal.SqliteMetadataHandlerImpl;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.util.ProgressMeasure;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public final class DelegateSqliteMetadataHandler implements SqliteMetadataHandler {

    private final SqliteMetadataHandlerImpl inner;

    public DelegateSqliteMetadataHandler(
        final ConnectionPoolComponent connectionPool,
        final ProjectComponent projects
    ) {
        this.inner = new SqliteMetadataHandlerImpl(connectionPool, projects);
    }

    @ExecuteBefore(State.RESOLVED)
    public void initSqlTypeMappingHelper(Injector injector) {
        inner.initSqlTypeMappingHelper(injector);
    }

    @Override
    public String getDbmsInfoString(Dbms dbms) throws SQLException {
        return inner.getDbmsInfoString(dbms);
    }

    @Override
    public CompletableFuture<Project> readSchemaMetadata(Dbms dbms, ProgressMeasure progress, Predicate<String> filterCriteria) {
        return inner.readSchemaMetadata(dbms, progress, filterCriteria);
    }
}
