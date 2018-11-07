package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.util.ProgressMeasure;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static com.speedment.runtime.connector.sqlite.internal.util.LoggingUtil.describe;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;

/**
 * Implementation of {@link DbmsMetadataHandler} for SQLite databases.
 *
 * @author Emil Forslund
 * @since  3.1.9
 */
public final class SqliteMetadataHandler implements DbmsMetadataHandler {

    private final static Logger LOGGER = LoggerManager.getLogger(SqliteMetadataHandler.class);

    private @Inject ConnectionPoolComponent connectionPoolComponent;
    private @Inject DbmsHandlerComponent dbmsHandlerComponent;
    private @Inject ProjectComponent projects;

    @Override
    public String getDbmsInfoString(Dbms dbms) throws SQLException {
        try (final Connection conn = getConnection(dbms)) {
            final DatabaseMetaData md = conn.getMetaData();
            return md.getDatabaseProductName() + ", " +
                md.getDatabaseProductVersion() + ", " +
                md.getDriverName() + " " +
                md.getDriverVersion() + ", JDBC version " +
                md.getJDBCMajorVersion() + "." +
                md.getJDBCMinorVersion();
        }
    }

    @Override
    public CompletableFuture<Project> readSchemaMetadata(
            Dbms dbms, ProgressMeasure progress,
            Predicate<String> filterCriteria) {

        // Create a deep copy of the project document.
        final Project projectCopy = projects.getProject().deepCopy();

        // Make sure there are not multiple dbmses with the same id
        final Set<String> ids = new HashSet<>();
        if (!projectCopy.dbmses().map(Dbms::getId).allMatch(ids::add)) {
            final Set<String> duplicates = new HashSet<>();
            ids.clear();

            projectCopy.dbmses()
                .map(Dbms::getId)
                .forEach(s -> {
                    if (!ids.add(s)) {
                        duplicates.add(s);
                    }
                });

            throw new SpeedmentException(
                "The following dbmses have duplicates in the config document: "
                    + duplicates
            );
        }

        // Locate the dbms in the copy.
        final Dbms dbmsCopy = projectCopy.dbmses()
            .filter(d -> d.getId().equals(dbms.getId()))
            .findAny().orElseThrow(() -> new SpeedmentException(
                "Could not find Dbms document in copy."
            ));

        return readSchemaMetadata(
            projectCopy, dbmsCopy, filterCriteria, progress
        ).whenCompleteAsync((project, ex) -> {
            progress.setProgress(ProgressMeasure.DONE);
            if (ex != null) {
                progress.setCurrentAction("Error!");
                throw new SpeedmentException("Unable to read schema metadata.", ex);
            } else {
                progress.setCurrentAction("Done!");
            }
        });
    }

    private CompletableFuture<Project> readSchemaMetadata(
            Project project, Dbms dbms,
            Predicate<String> filterCriteria, ProgressMeasure progress) {

        final DbmsType dbmsType = dbmsTypeOf(dbmsHandlerComponent, dbms);

        progress.setCurrentAction(describe(dbms));
        LOGGER.info(describe(dbms));

        // TODO: Continue working here.

        return CompletableFuture.completedFuture(null);
    }

    private Connection getConnection(Dbms dbms) {
        return connectionPoolComponent.getConnection(dbms);
    }
}
