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
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerComponent;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.SqlStreamTerminatorComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.ReferenceStreamBuilder;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.core.util.DatabaseUtil;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.trait.HasComparableOperators;

import java.sql.ResultSet;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static com.speedment.runtime.config.util.DocumentDbUtil.isSame;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
 * Default implementation of the {@link SqlStreamSupplier}-interface.
 *
 * @author Emil Forslund
 * @since 3.0.1
 */
final class SqlStreamSupplierImpl<ENTITY> implements SqlStreamSupplier<ENTITY> {

    private static final Logger LOGGER_SELECT = LoggerManager.getLogger(ApplicationBuilder.LogType.STREAM.getLoggerName()); // Hold an extra reference to this logger

    private final SqlFunction<ResultSet, ENTITY> entityMapper;
    private final Dbms dbms;
    private final DbmsType dbmsType;
    private final Map<ColumnIdentifier<ENTITY>, String> columnNameMap;
    private final Map<ColumnIdentifier<ENTITY>, Class<?>> columnDatabaseTypeMap;
    private final String sqlSelect;
    private final String sqlSelectCount;
    private final String sqlTableReference;
    private final SqlStreamOptimizerComponent sqlStreamOptimizerComponent;
    private final SqlStreamTerminatorComponent sqlStreamTerminatorComponent;
    private final boolean allowIteratorAndSpliterator;

    SqlStreamSupplierImpl(
        final TableIdentifier<ENTITY> tableId,
        final SqlFunction<ResultSet, ENTITY> entityMapper,
        final ProjectComponent projectComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ManagerComponent managerComponent,
        final SqlStreamOptimizerComponent sqlStreamOptimizerComponent,
        final SqlStreamTerminatorComponent sqlStreamTerminatorComponent,
        final boolean allowIteratorAndSpliterator
    ) {
        requireNonNull(tableId);
        requireNonNull(projectComponent);
        requireNonNull(dbmsHandlerComponent);
        requireNonNull(managerComponent);

        this.entityMapper = requireNonNull(entityMapper);
        this.sqlStreamOptimizerComponent = requireNonNull(sqlStreamOptimizerComponent);
        this.sqlStreamTerminatorComponent = requireNonNull(sqlStreamTerminatorComponent);
        this.allowIteratorAndSpliterator = allowIteratorAndSpliterator;

        final Project project = projectComponent.getProject();
        final Table table = DocumentDbUtil.referencedTable(project, tableId);

        this.dbms = DocumentDbUtil.referencedDbms(project, tableId);
        this.dbmsType = DatabaseUtil.dbmsTypeOf(dbmsHandlerComponent, dbms);

        @SuppressWarnings("unchecked")
        final Manager<ENTITY> manager = (Manager<ENTITY>) managerComponent.stream()
            .filter(m -> tableId.equals(m.getTableIdentifier()))
            .findAny().orElseThrow(() -> new SpeedmentException(
            "Could not find any manager for table '" + tableId + "'."
        ));

        final DatabaseNamingConvention naming = dbmsType.getDatabaseNamingConvention();
        final String sqlColumnList = table.columns()
            .filter(Column::isEnabled)
            .map(Column::getName)
            .map(naming::encloseField)
            .collect(joining(","));

        this.sqlTableReference = naming.fullNameOf(table);
        this.sqlSelect = "SELECT " + sqlColumnList + " FROM " + sqlTableReference;
        this.sqlSelectCount = "SELECT COUNT(*) FROM " + sqlTableReference;

        this.columnNameMap = manager.fields()
            .filter(f -> f.findColumn(project)
                .map(HasParent<Table>::getParentOrThrow)
                .map(t -> isSame(table, t))
                .orElse(false)
            )
            .map(Field::identifier)
            .collect(toMap(identity(), c -> naming.encloseField(c.getColumnId())));

        this.columnDatabaseTypeMap = new HashMap<>();

        manager.fields()
            .forEach(f -> {
                final Optional<? extends Column> c = f.findColumn(project);
                final Class<?> javaClass = c.orElseThrow(() ->
                    new SpeedmentException(format("Field '%s' in manager '%s'" +
                        " referred to a column that couldn't be found " +
                        "in config model.",
                        f.identifier().toString(),
                        manager
                    ))
                ).findDatabaseType();
                columnDatabaseTypeMap.put(f.identifier(), javaClass);
            });
    }

    @Override
    public Stream<ENTITY> stream(ParallelStrategy parallelStrategy) {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult
            = dbmsType.getOperationHandler().executeQueryAsync(
                dbms,
                sqlSelect,
                Collections.emptyList(),
                entityMapper,
                parallelStrategy
            );

        final SqlStreamOptimizerInfo<ENTITY> info = SqlStreamOptimizerInfo.of(
            dbmsType,
            sqlSelect,
            sqlSelectCount,
            this::executeAndGetLong,
            this::sqlColumnNamer,
            this::sqlDatabaseTypeFunction
        );

        final SqlStreamTerminator<ENTITY> terminator = new SqlStreamTerminator<>(
            info,
            asynchronousQueryResult,
            sqlStreamOptimizerComponent,
            sqlStreamTerminatorComponent,
            allowIteratorAndSpliterator
        );

        final Supplier<BaseStream<?, ?>> initialSupplier
            = asynchronousQueryResult::stream;

        final Stream<ENTITY> result = new ReferenceStreamBuilder<>(
            new PipelineImpl<>(initialSupplier),
            terminator
        );

        // Make sure we are closing the ResultSet, Statement and Connection later
        result.onClose(asynchronousQueryResult::close);

        return result;
    }

    @Override
    public <V extends Comparable<? super V>> Optional<ENTITY> findAny(HasComparableOperators<ENTITY, V> field, V value) {
        return stream(ParallelStrategy.computeIntensityDefault())
            .filter(field.equal(value))
            .findAny();
    }

    public String getSqlTableReference() {
        return sqlTableReference;
    }

    public long executeAndGetLong(String sql, List<Object> values) {
        LOGGER_SELECT.debug("%s, values:%s", sql, values);
        return dbmsType.getOperationHandler().executeQuery(dbms,
            sql,
            values,
            rs -> rs.getLong(1)
        ).findAny().orElseThrow(() -> new NoSuchElementException("No long value for " + sql + ", values " + values));
    }

    private String sqlColumnNamer(Field<ENTITY> field) {
        return columnNameMap.get(field.identifier());
    }

    private Class<?> sqlDatabaseTypeFunction(Field<ENTITY> field) {
        return columnDatabaseTypeMap.get(field.identifier());
    }
}
