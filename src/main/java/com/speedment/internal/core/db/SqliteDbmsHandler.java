/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.db;

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Schema;
import com.speedment.db.DatabaseNamingConvention;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import static com.speedment.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import com.speedment.internal.util.sql.SqlTypeInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 * Specific SQLite implementation of a DbmsHandler. Currently, there are no
 * specific implementations for SQLite.
 *
 * @author ikost
 * @since 2.4
 */
public final class SqliteDbmsHandler extends AbstractRelationalDbmsHandler {

    private static final Logger LOGGER = LoggerManager.getLogger(SqliteDbmsHandler.class);

    public SqliteDbmsHandler(Speedment speedment, final Dbms dbms) {
        super(speedment, dbms);
    }

    @Override
    public void readSchemaMetadata(final Connection connection, Predicate<String> filterCriteria) {
        // There is a bug in Xeria SQLite JDBC driver and
        // connection.getMetaData().getSchemas() and 
        // connection.getMetaData().getCatalogs() return empty resultsets
        // see e.g. https://bitbucket.org/xerial/sqlite-jdbc/issues/135/several-minor-bugs-and-remarks
        // When these bugs are fixed this method should be deprecated
        // and the super method should be used
        requireNonNull(connection);
        LOGGER.info("Reading metadata from " + getDbms().toString());

        final Set<String> discardedSchemas = new HashSet<>();
        final DatabaseNamingConvention naming = dbmsTypeOf(speedment, getDbms()).getDatabaseNamingConvention();
        try {
            final Set<SqlTypeInfo> preSet = dbmsTypeOf(speedment, getDbms()).getDataTypes();
            sqlTypeMapping = !preSet.isEmpty() ? readTypeMapFromSet(preSet) : readTypeMapFromDB(connection);
            String catalogName = "DEFAULT", schemaName = "DEFAULT";   // default SQLite schema 
            boolean schemaWasUsed = false;
            if (!naming.getSchemaExcludeSet().contains(schemaName)) {
                final String name = Optional.ofNullable(schemaName).orElse(catalogName);
                final Schema schema = getDbms().mutator().addNewSchema();
                schema.mutator().setName(name);
                schemaWasUsed = true;
            }

            if (!schemaWasUsed) {
                discardedSchemas.add(schemaName);
            }
        } catch (SQLException sqle) {
            throw new SpeedmentException(sqle);
        }

        final AtomicBoolean atleastOneSchema = new AtomicBoolean(false);
        getDbms().schemas().forEach(schema -> {
            tables(connection, schema);
            atleastOneSchema.set(true);
        });

        if (!atleastOneSchema.get()) {
            throw new SpeedmentException(
                    "Could not find any matching schema. The following schemas were considered: " + discardedSchemas + "."
            );
        }
    }

}
