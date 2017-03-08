/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.util;

import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.IndexColumn;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.internal.ColumnImpl;
import com.speedment.runtime.config.internal.DbmsImpl;
import com.speedment.runtime.config.internal.ForeignKeyColumnImpl;
import com.speedment.runtime.config.internal.ForeignKeyImpl;
import com.speedment.runtime.config.internal.IndexColumnImpl;
import com.speedment.runtime.config.internal.IndexImpl;
import com.speedment.runtime.config.internal.PrimaryKeyColumnImpl;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.internal.SchemaImpl;
import com.speedment.runtime.config.internal.TableImpl;
import java.util.AbstractMap;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.junit.Before;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractDocumentTest {

    protected static final String DBMS_A_ID = "Dbms A Id";
    
    protected Project project;
    protected Dbms dbmsA, dbmsB;
    protected Schema schemaA, schemaB;
    protected Table tableA, tableB, tableC, tableD;
    protected Column columnA1, columnA2, columnB1, columnB2, columnC1, columnC2, columnD1, columnD2;
    protected PrimaryKeyColumn primaryKeyColumnA1, primaryKeyColumnB1, primaryKeyColumnC1, primaryKeyColumnD1;
    protected Index indexA2, indexB2;
    protected IndexColumn indexColumnA2, indexColumnB2;
    protected ForeignKey foreignKeyA2_C1, foreignKeyB2_D1;
    protected ForeignKeyColumn foreignKeyColumnA2_C1, foreignKeyColumnB2_D1;

    @Before
    public void setUp() {
        final Map<String, Object> data = map(
            entry(Project.NAME, "Project"),
            entry(Project.ENABLED, true),
            entry(Project.DBMSES, map(
                entry(Dbms.ID, DBMS_A_ID),
                entry(Dbms.NAME, "Dbms A"),
                entry(Dbms.ENABLED, true),
                entry(Dbms.TYPE_NAME, "MySQL"),
                entry(Dbms.SCHEMAS, map(
                    entry(Schema.NAME, "Schema A"),
                    entry(Schema.ALIAS, "Custom Schema A"),
                    entry(Schema.ENABLED, true),
                    entry(Schema.TABLES, map(
                        entry(Table.NAME, "Table A"),
                        entry(Schema.ALIAS, "Custom Table A"),
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column A1"),
                            entry(Schema.ALIAS, "Custom Column A1"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Long.class.getName())
                        ), map(
                            entry(Column.NAME, "Column A2"),
                            entry(Schema.ALIAS, "Custom Column A2"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(PrimaryKeyColumn.NAME, "Column A1")
                        )),
                        entry(Table.INDEXES, map(
                            entry(Index.NAME, "Index A2"),
                            entry(Index.ENABLED, true),
                            entry(Index.INDEX_COLUMNS, map(
                                entry(IndexColumn.NAME, "Column A2")
                            ))
                        )),
                        entry(Table.FOREIGN_KEYS, map(
                            entry(ForeignKey.NAME, "ForeignKey A2 to C1"),
                            entry(ForeignKey.ENABLED, true),
                            entry(ForeignKey.FOREIGN_KEY_COLUMNS, map(
                                entry(ForeignKeyColumn.NAME, "Column A2"),
                                entry(ForeignKeyColumn.FOREIGN_TABLE_NAME, "Table C"),
                                entry(ForeignKeyColumn.FOREIGN_COLUMN_NAME, "Column C1")
                            ))
                        ))
                    ), map(
                        entry(Table.NAME, "Table C"),
                        entry(Schema.ALIAS, "Custom Table C"),
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column C1"),
                            entry(Schema.ALIAS, "Custom Column C1"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        ), map(
                            entry(Column.NAME, "Column C2"),
                            entry(Schema.ALIAS, "Custom Column C2"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, String.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(PrimaryKeyColumn.NAME, "Column C1")
                        ))
                    ))
                ))
            ), map(
                entry(Dbms.NAME, "Dbms B"),
                entry(Dbms.ENABLED, true),
                entry(Dbms.TYPE_NAME, "MySQL"),
                entry(Dbms.SCHEMAS, map(
                    entry(Schema.NAME, "Schema B"),
                    entry(Schema.ALIAS, "Custom Schema B"),
                    entry(Schema.ENABLED, true),
                    entry(Schema.TABLES, map(
                        entry(Table.NAME, "Table B"),
                        entry(Schema.ALIAS, "Custom Table B"),
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column B1"),
                            entry(Schema.ALIAS, "Custom Column B1"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Long.class.getName())
                        ), map(
                            entry(Column.NAME, "Column B2"),
                            entry(Schema.ALIAS, "Custom Column B2"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(PrimaryKeyColumn.NAME, "Column B1")
                        )),
                        entry(Table.INDEXES, map(
                            entry(Index.NAME, "Index B2"),
                            entry(Index.ENABLED, true),
                            entry(Index.UNIQUE, true),
                            entry(Index.INDEX_COLUMNS, map(
                                entry(IndexColumn.NAME, "Column B2")
                            ))
                        )),
                        entry(Table.FOREIGN_KEYS, map(
                            entry(ForeignKey.NAME, "ForeignKey B2 to D1"),
                            entry(ForeignKey.ENABLED, true),
                            entry(ForeignKey.FOREIGN_KEY_COLUMNS, map(
                                entry(ForeignKeyColumn.NAME, "Column B2"),
                                entry(ForeignKeyColumn.FOREIGN_TABLE_NAME, "Table D"),
                                entry(ForeignKeyColumn.FOREIGN_COLUMN_NAME, "Column D1")
                            ))
                        ))
                    ), map(
                        entry(Table.NAME, "Table D"),
                        entry(Schema.ALIAS, "Custom Table D"),
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column D1"),
                            entry(Schema.ALIAS, "Custom Column D1"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        ), map(
                            entry(Column.NAME, "Column D2"),
                            entry(Schema.ALIAS, "Custom Column D2"),
                            entry(Column.ENABLED, true),
                            entry(Column.DATABASE_TYPE, String.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(PrimaryKeyColumn.NAME, "Column D1")
                        ))
                    ))
                ))
            ))
        );

        project = new ProjectImpl(data);

        dbmsA = project.children(Project.DBMSES, DbmsImpl::new).findFirst().get();
        dbmsB = project.children(Project.DBMSES, DbmsImpl::new).skip(1).findFirst().get();
        schemaA = dbmsA.children(Dbms.SCHEMAS, SchemaImpl::new).findFirst().get();
        schemaB = dbmsB.children(Dbms.SCHEMAS, SchemaImpl::new).findFirst().get();
        tableA = schemaA.children(Schema.TABLES, TableImpl::new).findFirst().get();
        tableB = schemaB.children(Schema.TABLES, TableImpl::new).findFirst().get();
        tableC = schemaA.children(Schema.TABLES, TableImpl::new).skip(1).findFirst().get();
        tableD = schemaB.children(Schema.TABLES, TableImpl::new).skip(1).findFirst().get();
        columnA1 = tableA.children(Table.COLUMNS, ColumnImpl::new).findFirst().get();
        columnA2 = tableA.children(Table.COLUMNS, ColumnImpl::new).skip(1).findFirst().get();
        columnB1 = tableB.children(Table.COLUMNS, ColumnImpl::new).findFirst().get();
        columnB2 = tableB.children(Table.COLUMNS, ColumnImpl::new).skip(1).findFirst().get();
        columnC1 = tableC.children(Table.COLUMNS, ColumnImpl::new).findFirst().get();
        columnC2 = tableC.children(Table.COLUMNS, ColumnImpl::new).skip(1).findFirst().get();
        columnD1 = tableD.children(Table.COLUMNS, ColumnImpl::new).findFirst().get();
        columnD2 = tableD.children(Table.COLUMNS, ColumnImpl::new).skip(1).findFirst().get();
        primaryKeyColumnA1 = tableA.children(Table.PRIMARY_KEY_COLUMNS, PrimaryKeyColumnImpl::new).findFirst().get();
        primaryKeyColumnB1 = tableB.children(Table.PRIMARY_KEY_COLUMNS, PrimaryKeyColumnImpl::new).findFirst().get();
        primaryKeyColumnC1 = tableC.children(Table.PRIMARY_KEY_COLUMNS, PrimaryKeyColumnImpl::new).findFirst().get();
        primaryKeyColumnD1 = tableD.children(Table.PRIMARY_KEY_COLUMNS, PrimaryKeyColumnImpl::new).findFirst().get();
        indexA2 = tableA.children(Table.INDEXES, IndexImpl::new).findFirst().get();
        indexB2 = tableB.children(Table.INDEXES, IndexImpl::new).findFirst().get();
        indexColumnA2 = indexA2.children(Index.INDEX_COLUMNS, IndexColumnImpl::new).findFirst().get();
        indexColumnB2 = indexB2.children(Index.INDEX_COLUMNS, IndexColumnImpl::new).findFirst().get();
        foreignKeyA2_C1 = tableA.children(Table.FOREIGN_KEYS, ForeignKeyImpl::new).findFirst().get();
        foreignKeyB2_D1 = tableB.children(Table.FOREIGN_KEYS, ForeignKeyImpl::new).findFirst().get();
        foreignKeyColumnA2_C1 = foreignKeyA2_C1.children(ForeignKey.FOREIGN_KEY_COLUMNS, ForeignKeyColumnImpl::new).findFirst().get();
        foreignKeyColumnB2_D1 = foreignKeyB2_D1.children(ForeignKey.FOREIGN_KEY_COLUMNS, ForeignKeyColumnImpl::new).findFirst().get();
    }

    public Stream<Document> stream() {
        return Stream.of(
            project,
            dbmsA, dbmsB,
            schemaA, schemaB,
            tableA, tableB, tableC, tableD,
            columnA1, columnA2, columnB1, columnB2, columnC1, columnC2, columnD1, columnD2,
            primaryKeyColumnA1, primaryKeyColumnB1, primaryKeyColumnC1, primaryKeyColumnD1,
            indexA2, indexB2,
            indexColumnA2, indexColumnB2,
            foreignKeyA2_C1, foreignKeyB2_D1,
            foreignKeyColumnA2_C1, foreignKeyColumnB2_D1
        );
    }

    public Stream<Project> projects() {
        return Stream.of(project);
    }

    public <T extends Document> Stream<T> streamOf(Class<T> clazz) {
        return stream().filter(clazz::isInstance).map(clazz::cast);
    }

    private static Map.Entry<String, Object> entry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    private static Map.Entry<String, Object> entry(String key, boolean value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    private static Map.Entry<String, Object> entry(String key, Map<String, Object>... children) {
        return new AbstractMap.SimpleEntry<>(key, Stream.of(children).collect(toList()));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    private static Map<String, Object> map(Map.Entry<String, Object>... entries) {
        return MapStream.of(Stream.of(entries)).toMap();
    }
}
