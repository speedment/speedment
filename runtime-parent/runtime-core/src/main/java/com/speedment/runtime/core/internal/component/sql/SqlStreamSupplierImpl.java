/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.field.Field;
import com.speedment.runtime.core.field.trait.HasComparableOperators;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.ReferenceStreamBuilder;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.DatabaseUtil;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import static java.util.function.Function.identity;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.config.util.DocumentDbUtil.isSame;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.config.util.DocumentDbUtil.isSame;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.config.util.DocumentDbUtil.isSame;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.config.util.DocumentDbUtil.isSame;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
final class SqlStreamSupplierImpl<ENTITY> implements SqlStreamSupplier<ENTITY> {
    
    private final SqlFunction<ResultSet, ENTITY> entityMapper;
    private final Dbms dbms;
    private final DbmsType dbmsType;
    private final Map<ColumnIdentifier<ENTITY>, String> columnNameMap;
    private final String sqlSelect;
    private final String sqlTableReference;
    
    SqlStreamSupplierImpl(
            TableIdentifier<ENTITY> tableId,
            SqlFunction<ResultSet, ENTITY> entityMapper,
            ProjectComponent projectComponent,
            DbmsHandlerComponent dbmsHandlerComponent,
            ManagerComponent managerComponent) {
        
        requireNonNulls(tableId, projectComponent, dbmsHandlerComponent);
        
        this.entityMapper = requireNonNull(entityMapper);
        
        final Project project = projectComponent.getProject();
        final Table table     = DocumentDbUtil.referencedTable(project, tableId);
        
        this.dbms  = DocumentDbUtil.referencedDbms(project, tableId);
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

        this.columnNameMap = manager.fields()
            .filter(f -> f.findColumn(project)
                .map(c -> c.getParent())
                .map(t -> isSame(table, t.get()))
                .orElse(false)
            )
            .map(Field<ENTITY>::identifier)
            .collect(toMap(identity(), naming::fullNameOf));
    }

    @Override
    public Stream<ENTITY> stream(ParallelStrategy parallelStrategy) {
        final AsynchronousQueryResult<ENTITY> asynchronousQueryResult = 
            dbmsType.getOperationHandler().executeQueryAsync(
                dbms, 
                sqlSelect, 
                Collections.emptyList(), 
                entityMapper, 
                parallelStrategy
            );
        
        final SqlStreamTerminator<ENTITY> terminator = new SqlStreamTerminator<>(
            dbmsType, 
            sqlSelect, 
            this::sqlCount, 
            this::sqlColumnNamer, 
            asynchronousQueryResult
        );
        
        final Supplier<BaseStream<?, ?>> initialSupplier =
            () -> asynchronousQueryResult.stream();
        
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

    private long sqlCount() {
        return dbmsType.getOperationHandler().executeQuery(dbms,
            "SELECT COUNT(*) FROM " + sqlTableReference,
            Collections.emptyList(),
            rs -> rs.getLong(1)
        ).findAny().get();
    }
    
    private String sqlColumnNamer(Field<ENTITY> field) {
        return columnNameMap.get(field.identifier());
    }
}