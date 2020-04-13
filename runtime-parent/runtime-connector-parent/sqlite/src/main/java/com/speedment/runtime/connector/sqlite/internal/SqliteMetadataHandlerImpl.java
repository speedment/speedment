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
package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.mutator.ForeignKeyColumnMutator;
import com.speedment.runtime.config.mutator.IndexMutator;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.connector.sqlite.internal.types.SqlTypeMappingHelper;
import com.speedment.runtime.connector.sqlite.internal.util.MetaDataUtil;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.runtime.core.util.ProgressMeasureUtil;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.connector.sqlite.internal.util.LoggingUtil.describe;
import static com.speedment.runtime.connector.sqlite.internal.util.MetaDataUtil.*;
import static com.speedment.runtime.core.abstracts.AbstractDbmsMetadataHandler.SHOW_METADATA;
import static java.lang.String.format;
import static java.util.stream.Collectors.*;

/**
 * Implementation of {@link DbmsMetadataHandler} for SQLite databases.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SqliteMetadataHandlerImpl implements DbmsMetadataHandler {

    private static final Logger LOGGER = LoggerManager.getLogger(SqliteMetadataHandlerImpl.class);
    private static final String[] TABLES_AND_VIEWS = {"TABLE", "VIEW"};
    private static final String ORIGINAL_TYPE = "originalDatabaseType";
    private static final String ROW_ID = "rowid";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String KEY_SEQ = "KEY_SEQ";
    private static final String INDEX_NAME = "INDEX_NAME";
    private static final String SCHEMA = "schema";

    private static final Pattern BINARY_TYPES = Pattern.compile(
        "^(?:(?:TINY|MEDIUM|LONG)?\\s?BLOB|(?:VAR)?BINARY)(?:\\(\\d+\\))?$");

    private static final Pattern INTEGER_BOOLEAN_TYPE = Pattern.compile(
        "^(?:BIT|INT|INTEGER|TINYINT)\\(1\\)$");

    private static final Pattern BIT_TYPES = Pattern.compile(
        "^BIT\\((\\d+)\\)$");

    private static final Pattern SHORT_TYPES = Pattern.compile(
        "^(?:UNSIGNED\\s+TINY\\s?INT|SHORT(?:\\s?INT)?)(?:\\((\\d+)\\))?$");

    private static final Pattern INT_TYPES = Pattern.compile(
        "^(?:UNSIGNED (?:SHORT|MEDIUM\\s?INT)|INT(?:EGER)?|MEDIUM\\s?INT)(?:\\((\\d+)\\))?$");

    private static final Pattern LONG_TYPES = Pattern.compile(
        "^(?:UNSIGNED(?: INT(?:EGER)?)|(?:UNSIGNED\\s*)?(?:BIG\\s?INT|LONG))(?:\\((\\d+)\\))?$");

    /**
     * Fix #566: Some connectors throw an exception if getIndexInfo() is invoked
     * for a database VIEW.
     */
    private static final boolean IGNORE_VIEW_INDEXES = false;

    /**
     * 'true' below might speed up metadata retrieval since approximations can
     * be used. See https://github.com/speedment/speedment-enterprise/issues/168
     */
    private static final boolean APPROXIMATE_INDEX = true;

    private final ConnectionPoolComponent connectionPool;
    private final ProjectComponent projects;
    private final JavaTypeMap javaTypeMap;

    private SqlTypeMappingHelper typeMappingHelper;

    public SqliteMetadataHandlerImpl(
        final ConnectionPoolComponent connectionPool,
        final ProjectComponent projects
    ) {
        this.connectionPool = connectionPool;
        this.projects = projects;

        this.javaTypeMap = JavaTypeMap.create();
        javaTypeMap.addRule((mappings, md) ->
            md.getTypeName().toUpperCase().startsWith("NUMERIC(")
                ? Optional.of(Double.class) : Optional.empty()
        );

        javaTypeMap.addRule((mappings, md) ->
            md.getTypeName().toUpperCase().startsWith("DECIMAL(")
                ? Optional.of(Double.class) : Optional.empty()
        );

        javaTypeMap.addRule((mappings, md) ->
            Optional.of(md.getTypeName())
                .map(String::toUpperCase)
                .filter(str -> INTEGER_BOOLEAN_TYPE.matcher(str).find())
                .map(str -> Boolean.class)
        );

        javaTypeMap.addRule((mappings, md) -> patternMapper(SHORT_TYPES, md, Short.class));
        javaTypeMap.addRule((mappings, md) -> patternMapper(LONG_TYPES, md, Long.class));
        javaTypeMap.addRule((mappings, md) -> patternMapper(INT_TYPES, md, Integer.class));

        javaTypeMap.addRule((mappings, md) ->
            Optional.of(md.getTypeName())
                .map(String::toUpperCase)
                .map(BIT_TYPES::matcher)
                .filter(Matcher::find)
                .map(match -> match.group(1))
                .map(Integer::parseInt)
                .map(bits -> {
                    if      (bits > Integer.SIZE) return Long.class;
                    else if (bits > Short.SIZE)   return Integer.class;
                    else if (bits > Byte.SIZE)    return Short.class;
                    else return Byte.class;
                })
        );

        javaTypeMap.addRule((mappings, md) ->
            Optional.of(md.getTypeName())
                .map(String::toUpperCase)
                .filter(str -> BINARY_TYPES.matcher(str).find())
                .map(str -> byte[].class)
        );
    }

    @ExecuteBefore(State.RESOLVED)
    public void initSqlTypeMappingHelper(Injector injector) {
        typeMappingHelper = SqlTypeMappingHelper.create(injector, javaTypeMap);
    }

    @Override
    public String getDbmsInfoString(Dbms dbms) throws SQLException {
        try (final Connection conn = connectionPool.getConnection(dbms)) {
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
            projectCopy, dbmsCopy, progress
        ).whenCompleteAsync((project, ex) -> {
            progress.setProgress(ProgressMeasureUtil.DONE);
            if (ex != null) {
                progress.setCurrentAction("Error!");
                throw new SpeedmentException("Unable to read schema metadata.", ex);
            } else {
                progress.setCurrentAction("Done!");
            }
        });
    }

    private CompletableFuture<Project> readSchemaMetadata(
            Project project, Dbms dbms, ProgressMeasure progress) {

        progress.setCurrentAction(describe(dbms));
        LOGGER.info(describe(dbms));

        final CompletableFuture<Map<String, Class<?>>> typeMappingTask
            = typeMappingHelper.loadFor(dbms);

        final Schema schema = dbms.mutator().addNewSchema();
        schema.mutator().setId(SCHEMA);
        schema.mutator().setName(SCHEMA);

        return readTableMetadata(schema, typeMappingTask, progress)
            .thenApplyAsync(unused -> project);
    }

    private CompletableFuture<Schema> readTableMetadata(
            Schema schema,
            CompletableFuture<Map<String, Class<?>>> typeMappingTask,
            ProgressMeasure progress) {

        final CompletableFuture<Void> tablesTask = CompletableFuture.runAsync(() ->
            tablesTask(schema)
        );

        return tablesTask.thenComposeAsync(unused -> typeMappingTask)
            .thenComposeAsync(sqlTypeMappings -> {
                final Dbms dbms = schema.getParentOrThrow();
                final AtomicInteger cnt = new AtomicInteger();
                final double noTables = schema.tables().count();

                return CompletableFuture.allOf(
                    schema.tables().map(table -> CompletableFuture.runAsync(() -> {
                        try (final Connection conn = connectionPool.getConnection(dbms)) {
                            progress.setCurrentAction(describe(table));
                            columns(conn, sqlTypeMappings, table, progress);
                            indexes(conn, table, progress);
                            foreignKeys(conn, table);
                            primaryKeyColumns(conn, table, progress);

                            if (!table.isView()) {

                                // If no INTEGER PRIMARY KEY exists, a rowId should be created.
                                if (table.columns()
                                    .filter(col -> col.getAsString(ORIGINAL_TYPE).filter("INTEGER"::equalsIgnoreCase).isPresent())
                                    .noneMatch(col -> table.primaryKeyColumns().anyMatch(pkc -> DocumentDbUtil.isSame(pkc.findColumnOrThrow(), col)))
                                &&  table.columns().map(Column::getId).noneMatch(ROW_ID::equalsIgnoreCase)) {
                                    createRowId(table);
                                } else {
                                    table.columns()
                                        .filter(col -> col.getAsString(ORIGINAL_TYPE).filter("INTEGER"::equalsIgnoreCase).isPresent())
                                        .filter(col -> table.primaryKeyColumns().anyMatch(pkc -> DocumentDbUtil.isSame(pkc.findColumnOrThrow(), col)))
                                        .forEach(col -> col.mutator().setAutoIncrement(true));
                                }
                            }

                            table.columns().forEach(col ->
                                col.getData().remove(ORIGINAL_TYPE)
                            );

                            progress.setProgress(cnt.incrementAndGet() / noTables);
                        } catch (final SQLException ex) {
                            throw new SpeedmentException(ex);
                        }
                    })).toArray(CompletableFuture[]::new)
                ).thenApplyAsync(v -> schema);
            });
    }

    private static Optional<Class<?>> patternMapper(Pattern pattern, ColumnMetaData md, Class<?> expected) {
        return Optional.of(md.getTypeName())
            .map(String::toUpperCase)
            .map(pattern::matcher)
            .filter(Matcher::find)
            .map(match -> {
                final String group = match.group(1);
                if (group == null) {
                    return expected;
                } else {
                    final int digits = Integer.parseInt(group);
                    if      (digits > 11) return Long.class;
                    else if (digits > 4)  return Integer.class;
                    else if (digits > 2)  return Short.class;
                    else                  return Byte.class;
                }
            });
    }

    private void tablesTask(Schema schema) {
        final Dbms dbms = schema.getParentOrThrow();

        try (final Connection connection = connectionPool.getConnection(dbms)) {
            try (final ResultSet rsTable = connection.getMetaData().getTables(null, null, null, TABLES_AND_VIEWS)) {
                if (SHOW_METADATA) {
                    final ResultSetMetaData rsmd = rsTable.getMetaData();
                    final int numberOfColumns = rsmd.getColumnCount();
                    for (int x = 1; x <= numberOfColumns; x++) {
                        LOGGER.debug(new StringJoiner(", ")
                            .add(rsmd.getColumnName(x))
                            .add(rsmd.getColumnClassName(x))
                            .add(Integer.toString(rsmd.getColumnType(x)))
                            .toString()
                        );
                    }
                }

                while (rsTable.next()) {
                    if (SHOW_METADATA) {
                        final ResultSetMetaData rsmd = rsTable.getMetaData();
                        final int numberOfColumns = rsmd.getColumnCount();
                        for (int x = 1; x <= numberOfColumns; x++) {
                            LOGGER.debug(rsmd.getColumnName(x) +
                                ":'" + rsTable.getObject(x) + "'");
                        }
                    }

                    final Table table      = schema.mutator().addNewTable();
                    final String tableName = rsTable.getString("TABLE_NAME");
                    final String tableType = rsTable.getString("TABLE_TYPE");
                    table.mutator().setId(tableName);
                    table.mutator().setName(tableName);
                    table.mutator().setView("VIEW".equals(tableType));
                }
            } catch (final SQLException ex) {
                throw new SpeedmentException(format(
                    "Error reading results from SQLite-database '%s'.",
                    DocumentUtil.toStringHelper(dbms)
                ), ex);
            }
        } catch (final SQLException ex) {
            throw new SpeedmentException(format(
                "Error getting connection to SQLite-database '%s'.",
                DocumentUtil.toStringHelper(dbms)
            ), ex);
        }
    }

    private void createRowId(Table table) {
        final Column column = table.mutator().addNewColumn();

        column.mutator().setId(ROW_ID);
        column.mutator().setName(ROW_ID);
        column.mutator().setOrdinalPosition(0);
        column.mutator().setDatabaseType(Long.class);
        column.mutator().setAutoIncrement(true);
        column.mutator().setNullable(false);
        column.mutator().setTypeMapper(PrimitiveTypeMapper.class);

        // When we introduce a new primary key, we need to
        // add a UNIQUE index that represents the same set
        // of columns as the old primary key.
        if (table.primaryKeyColumns().anyMatch(pkc -> true)) {
            final Set<String> oldPks = table.primaryKeyColumns()
                .map(PrimaryKeyColumn::getId)
                .collect(toCollection(LinkedHashSet::new));

            // Make sure such an index doesn't already exist
            if (table.indexes()
                .filter(Index::isUnique)
                .noneMatch(idx -> oldPks.equals(idx.indexColumns()
                    .map(IndexColumn::getId)
                    .collect(toCollection(LinkedHashSet::new))
                ))) {
                addUniqueIndex(table, oldPks);
            }

            // Remove the existing primary key since the rowid is
            // the only value that should be considered part of
            // the primary key
            table.getData().remove(TableUtil.PRIMARY_KEY_COLUMNS);
        }

        final PrimaryKeyColumn pkc = table.mutator().addNewPrimaryKeyColumn();
        pkc.mutator().setId(ROW_ID);
        pkc.mutator().setName(ROW_ID);
        pkc.mutator().setOrdinalPosition(1);
    }

    private void addUniqueIndex(Table table, Set<String> oldPks) {
        final Index pkReplacement = table.mutator().addNewIndex();
        final String idxName = md5(oldPks.toString());

        final IndexMutator<? extends Index> mutator = pkReplacement.mutator();
        mutator.setId(idxName);
        mutator.setName(idxName);
        mutator.setUnique(true);

        table.primaryKeyColumns().forEachOrdered(pkc -> {
            final int ordNo = 1 + (int) pkReplacement.indexColumns().count();
            final IndexColumn idxCol = mutator.addNewIndexColumn();
            idxCol.mutator().setId(pkc.getId());
            idxCol.mutator().setName(pkc.getName());
            idxCol.mutator().setOrdinalPosition(ordNo);
        });
    }

    private void columns(Connection conn, Map<String, Class<?>> sqlTypeMapping, Table table, ProgressMeasure progress) {
        requireNonNulls(conn, sqlTypeMapping, table, progress);

        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getColumns(null, null, table.getId(), null);

        final TableChildMutator<Column> mutator = (column, rs) -> {

            final ColumnMetaData md = ColumnMetaData.of(rs);
            final String columnName = md.getColumnName();

            column.getData().put(ORIGINAL_TYPE, md.getTypeName());

            column.mutator().setId(columnName);
            column.mutator().setName(columnName);
            column.mutator().setOrdinalPosition(md.getOrdinalPosition());
            column.mutator().setNullable(MetaDataUtil.isNullable(md));
            column.mutator().setDatabaseType(
                typeMappingHelper.findFor(sqlTypeMapping, md)
                    .orElseGet(() -> {
                        LOGGER.warn(format(
                            "Unable to determine mapping for table %s, " +
                            "column %s. Type name %s, data type %d, decimal " +
                            "digits %d. Fallback to JDBC-type %s",
                            table.getId(),
                            column.getId(),
                            md.getTypeName(),
                            md.getDataType(),
                            md.getDecimalDigits(),
                            Object.class.getName()
                        ));
                        return Object.class;
                    })
            );

            if (!md.isDecimalDigitsNull() && md.getDecimalDigits() != 10) {
                column.mutator().setDecimalDigits(md.getDecimalDigits());
            }

            if (!md.isColumnSizeNull() && md.getColumnSize() != 2_000_000_000) {
                column.mutator().setColumnSize(md.getColumnSize());
            }

            if (isAutoIncrement(md)) {
                column.mutator().setAutoIncrement(true);
            }

            if (!column.isNullable() && isWrapper(column.findDatabaseType())) {
                column.mutator().setTypeMapper(TypeMapper.primitive().getClass());
            }

            if ("ENUM".equalsIgnoreCase(md.getTypeName())) {
                final Dbms dbms = DocumentUtil.ancestor(table, Dbms.class).get();
                final List<String> constants = enumConstantsOf(dbms, table, columnName);
                column.mutator().setEnumConstants(constants.stream().collect(joining(",")));
            }

            progress.setCurrentAction(describe(column));
        };

        tableChilds(
            table,
            Column.class,
            table.mutator()::addNewColumn,
            supplier,
            rsChild -> ColumnMetaData.of(rsChild).getColumnName(),
            mutator
        );
    }

    private void primaryKeyColumns(Connection conn, Table table, ProgressMeasure progress) {
        requireNonNulls(conn, table, progress);

        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getPrimaryKeys(null, null, table.getId());

        final TableChildMutator<PrimaryKeyColumn> mutator = (pkc, rs) -> {
            final String columnName = rs.getString(COLUMN_NAME);
            pkc.mutator().setId(columnName);
            pkc.mutator().setName(columnName);
            pkc.mutator().setOrdinalPosition(rs.getInt(KEY_SEQ));
        };

        tableChilds(
            table,
            PrimaryKeyColumn.class,
            table.mutator()::addNewPrimaryKeyColumn,
            supplier,
            rsChild -> rsChild.getString(COLUMN_NAME),
            mutator
        );

        if (!table.isView() && table.primaryKeyColumns().noneMatch(pk -> true)) {
            LOGGER.warn(format("Table '%s' does not have any primary key.",
                table.getId()));
        }
    }

    private void indexes(Connection conn, Table table, ProgressMeasure progress) {
        requireNonNulls(conn, table, progress);

        if (table.isView() && IGNORE_VIEW_INDEXES) return;

        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getIndexInfo(null, null, table.getId(), false,
                APPROXIMATE_INDEX);

        final TableChildMutator<Index> mutator = (index, rs) -> {
            final String indexName = rs.getString(INDEX_NAME);

            index.mutator().setId(indexName);
            index.mutator().setName(indexName);
            index.mutator().setUnique(!rs.getBoolean("NON_UNIQUE"));

            final IndexColumn indexColumn = index.mutator().addNewIndexColumn();
            final String columnName = rs.getString(COLUMN_NAME);
            indexColumn.mutator().setId(columnName);
            indexColumn.mutator().setName(columnName);
            indexColumn.mutator().setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
            indexColumn.mutator().setOrderType(getOrderType(rs));
        };

        final SqlPredicate<ResultSet> filter = rs -> rs.getString(INDEX_NAME) != null;

        tableChilds(
            table,
            Index.class,
            table.mutator()::addNewIndex,
            supplier,
            rsChild -> rsChild.getString(INDEX_NAME),
            mutator,
            filter
        );
    }

    private void foreignKeys(Connection conn, Table table) {
        requireNonNulls(conn, table);

        final Schema schema = table.getParentOrThrow();
        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getImportedKeys(null, null, table.getId());

        final Set<String> fksThatNeedNewNames = new LinkedHashSet<>();

        final TableChildMutator<ForeignKey> mutator = (foreignKey, rs) -> {

            final String foreignKeyName = rs.getString("FK_NAME");
            if (foreignKeyName == null || foreignKeyName.trim().isEmpty()) {
                if (rs.getInt(KEY_SEQ) == 1) {
                    final String uniqueName = UUID.randomUUID().toString();
                    foreignKey.mutator().setId(uniqueName);
                    foreignKey.mutator().setName(uniqueName);
                    fksThatNeedNewNames.add(uniqueName);
                }
            } else {
                foreignKey.mutator().setId(foreignKeyName);
                foreignKey.mutator().setName(foreignKeyName);
            }

            final ForeignKeyColumn foreignKeyColumn = foreignKey.mutator().addNewForeignKeyColumn();
            final ForeignKeyColumnMutator<?> fkcMutator = foreignKeyColumn.mutator();
            final String fkColumnName = rs.getString("FK" + COLUMN_NAME);
            fkcMutator.setId(fkColumnName);
            fkcMutator.setName(fkColumnName);
            fkcMutator.setOrdinalPosition(rs.getInt(KEY_SEQ));
            fkcMutator.setForeignTableName(rs.getString("PKTABLE_NAME"));
            fkcMutator.setForeignColumnName(rs.getString("PK" + COLUMN_NAME));

            // FKs always point to the same DBMS but can
            // be changed to another one using the config
            fkcMutator.setForeignDatabaseName(schema.getParentOrThrow().getId());
            fkcMutator.setForeignSchemaName(SCHEMA);
        };

        tableChilds(
            table,
            ForeignKey.class,
            table.mutator()::addNewForeignKey,
            supplier,
            rsChild -> rsChild.getInt(KEY_SEQ) == 1 ? ""
                : fksThatNeedNewNames.stream()
                    .skip(fksThatNeedNewNames.size() - 1L)
                    .findFirst().orElseThrow(IllegalStateException::new),
            mutator
        );

        // Fix foreign keys without any id by finding an index with the same
        // set of columns.
        table.foreignKeys()
            .filter(fk -> fksThatNeedNewNames.contains(fk.getId()))
            .forEach(fk -> {
                final Set<String> thisSet = fk.foreignKeyColumns()
                    .map(ForeignKeyColumn::getId)
                    .collect(toSet());

                final Optional<? extends Index> found = table.indexes()
                    .filter(idx -> idx.indexColumns()
                        .map(IndexColumn::getId)
                        .collect(toSet())
                        .equals(thisSet)
                    ).findFirst();

                if (found.isPresent()) {
                    fk.mutator().setId(found.get().getId());
                    fk.mutator().setName(found.get().getName());
                } else {
                    final String randName = md5(thisSet.toString());
                    fk.mutator().setId(randName);
                    fk.mutator().setName(randName);
                    LOGGER.error(format(
                        "Found a foreign key in table '%s' with no name. " +
                        "Assigning it a random name '%s'",
                        table.getId(), randName));
                }
            });

    }

    private static String md5(String str) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] mdbytes = md.digest(str.getBytes(StandardCharsets.UTF_8));

            final StringBuilder sb = new StringBuilder();
            for (byte mdbyte : mdbytes) {
                sb.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("MD5 algorithm not supported.", ex);
        }
    }

    private <T extends Document & HasId> void tableChilds(
            final Table table,
            final Class<T> childType,
            final Supplier<T> childSupplier,
            final SqlSupplier<ResultSet> resultSetSupplier,
            final SqlFunction<ResultSet, String> childIdGetter,
            final TableChildMutator<T> resultSetMutator) {

        tableChilds(table, childType, childSupplier, resultSetSupplier,
            childIdGetter, resultSetMutator, rs -> true);
    }

    private <T extends Document & HasId> void tableChilds(
            final Table table,
            final Class<T> childType,
            final Supplier<T> childSupplier,
            final SqlSupplier<ResultSet> resultSetSupplier,
            final SqlFunction<ResultSet, String> childIdGetter,
            final TableChildMutator<T> resultSetMutator,
            final SqlPredicate<ResultSet> filter) {

        requireNonNulls(childSupplier, resultSetSupplier, resultSetMutator);

        try (final ResultSet rsChild = resultSetSupplier.get()) {

            if (SHOW_METADATA) {
                final ResultSetMetaData rsmd = rsChild.getMetaData();
                final int numberOfColumns = rsmd.getColumnCount();
                for (int x = 1; x <= numberOfColumns; x++) {
                    final int columnType = rsmd.getColumnType(x);
                    LOGGER.info(x + ":" + rsmd.getColumnName(x) + ", " +
                        rsmd.getColumnClassName(x) + ", " + columnType);
                }
            }

            while (rsChild.next()) {
                if (SHOW_METADATA) {
                    final ResultSetMetaData rsmd = rsChild.getMetaData();
                    final int numberOfColumns = rsmd.getColumnCount();
                    for (int x = 1; x <= numberOfColumns; x++) {
                        final Object val = rsChild.getObject(x);
                        LOGGER.info(x + ":" + rsmd.getColumnName(x) + ":'" +
                            val + "'");
                    }
                }

                if (filter.test(rsChild)) {
                    final String id = childIdGetter.apply(rsChild);
                    final Optional<T> existing = DocumentDbUtil.typedChildrenOf(table)
                        .filter(childType::isInstance)
                        .map(childType::cast)
                        .filter(idx -> id.equals(idx.getId()))
                        .findAny();

                    final T child = existing.orElseGet(childSupplier);
                    resultSetMutator.mutate(child, rsChild);
                } else {
                    LOGGER.info("Skipped due to RS filtering. This is normal " +
                        "for some DBMS types.");
                }
            }
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Unable to read table child.");
            throw new SpeedmentException(sqle);
        }
    }

    /**
     * Queries the database for a list of ENUM constants belonging to the specified table and
     * column.
     *
     * @param dbms           the dbms
     * @param table          the table
     * @param columnName     the column name
     * @return               list of enum constants.
     * @throws SQLException  if an error occured
     */
    private List<String> enumConstantsOf(Dbms dbms, Table table, String columnName) throws SQLException {
        final DatabaseNamingConvention naming = new SqliteNamingConvention(); // This should be handled via the injector

        final String sql = String.format(
            "show columns from %s where field=%s;",
            naming.fullNameOf(table),
            naming.quoteField(columnName)
        );

        try (final Connection conn = connectionPool.getConnection(dbms);
             final PreparedStatement ps = conn.prepareStatement(sql);
             final ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                final String fullResult = rs.getString(2);

                if (fullResult.startsWith("enum('")
                    &&  fullResult.endsWith("')")) {

                    final String middle = fullResult.substring(5, fullResult.length() - 1);
                    return Stream.of(middle.split(","))
                        .map(s -> s.substring(1, s.length() - 1))
                        .filter(s -> !s.isEmpty())
                        .collect(toList());

                } else {
                    throw new SpeedmentException("Unexpected response (" + fullResult + ").");
                }
            } else {
                throw new SpeedmentException("Expected an result.");
            }
        }
    }

    @FunctionalInterface
    protected interface TableChildMutator<T> {
        void mutate(T t, ResultSet u) throws SQLException;
    }
}
