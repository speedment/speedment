/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.util.document;

import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.internal.*;
import com.speedment.runtime.config.trait.HasEnableUtil;
import com.speedment.runtime.config.trait.HasNameUtil;
import org.junit.jupiter.api.BeforeEach;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 */
public abstract class AbstractDocumentTest {

    Project project;
    protected Dbms dbmsA, dbmsB;
    protected Schema schemaA, schemaB;
    protected Table tableA, tableB, tableC, tableD;
    protected Column columnA1, columnA2, columnB1, columnB2, columnC1, columnC2, columnD1, columnD2;
    protected PrimaryKeyColumn primaryKeyColumnA1, primaryKeyColumnB1, primaryKeyColumnC1, primaryKeyColumnD1;
    protected Index indexA2, indexB2;
    protected IndexColumn indexColumnA2, indexColumnB2;
    protected ForeignKey foreignKeyA2_C1, foreignKeyB2_D1;
    protected ForeignKeyColumn foreignKeyColumnA2_C1, foreignKeyColumnB2_D1;

    @BeforeEach
    public void setUp() {
        final Map<String, Object> data = map(
            entry(HasNameUtil.NAME, "Project"),
            entry(HasEnableUtil.ENABLED, true),
            entry(ProjectUtil.DBMSES, map(
                entry(HasNameUtil.NAME, "Dbms A"),
                entry(HasEnableUtil.ENABLED, true),
                entry(Dbms.SCHEMAS, map(
                    entry(HasNameUtil.NAME, "Schema A"),
                    entry(Schema.ALIAS, "Custom Schema A"),
                    entry(HasEnableUtil.ENABLED, true),
                    entry(Schema.TABLES, map(
                        entry(HasNameUtil.NAME, "Table A"),
                        entry(Schema.ALIAS, "Custom Table A"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column A1"),
                            entry(Schema.ALIAS, "Custom Column A1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Long.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column A2"),
                            entry(Schema.ALIAS, "Custom Column A2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column A1")
                        )),
                        entry(Table.INDEXES, map(
                            entry(HasNameUtil.NAME, "Index A2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Index.INDEX_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column A2")
                            ))
                        )),
                        entry(Table.FOREIGN_KEYS, map(
                            entry(HasNameUtil.NAME, "ForeignKey A2 to C1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(ForeignKey.FOREIGN_KEY_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column A2"),
                                entry(ForeignKeyColumn.FOREIGN_TABLE_NAME, "Table C"),
                                entry(ForeignKeyColumn.FOREIGN_COLUMN_NAME, "Column C1")
                            ))
                        ))
                    ), map(
                        entry(HasNameUtil.NAME, "Table C"),
                        entry(Schema.ALIAS, "Custom Table C"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column C1"),
                            entry(Schema.ALIAS, "Custom Column C1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column C2"),
                            entry(Schema.ALIAS, "Custom Column C2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, String.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column C1")
                        ))
                    ))
                ))
            ), map(
                entry(HasNameUtil.NAME, "Dbms B"),
                entry(HasEnableUtil.ENABLED, true),
                entry(Dbms.SCHEMAS, map(
                    entry(HasNameUtil.NAME, "Schema B"),
                    entry(Schema.ALIAS, "Custom Schema B"),
                    entry(HasEnableUtil.ENABLED, true),
                    entry(Schema.TABLES, map(
                        entry(HasNameUtil.NAME, "Table B"),
                        entry(Schema.ALIAS, "Custom Table B"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column B1"),
                            entry(Schema.ALIAS, "Custom Column B1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Long.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column B2"),
                            entry(Schema.ALIAS, "Custom Column B2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column B1")
                        )),
                        entry(Table.INDEXES, map(
                            entry(HasNameUtil.NAME, "Index B2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Index.UNIQUE, true),
                            entry(Index.INDEX_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column B2")
                            ))
                        )),
                        entry(Table.FOREIGN_KEYS, map(
                            entry(HasNameUtil.NAME, "ForeignKey B2 to D1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(ForeignKey.FOREIGN_KEY_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column B2"),
                                entry(ForeignKeyColumn.FOREIGN_TABLE_NAME, "Table D"),
                                entry(ForeignKeyColumn.FOREIGN_COLUMN_NAME, "Column D1")
                            ))
                        ))
                    ), map(
                        entry(HasNameUtil.NAME, "Table D"),
                        entry(Schema.ALIAS, "Custom Table D"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column D1"),
                            entry(Schema.ALIAS, "Custom Column D1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, Integer.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column D2"),
                            entry(Schema.ALIAS, "Custom Column D2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(Column.DATABASE_TYPE, String.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column D1")
                        ))
                    ))
                ))
            ))
        );

        project = new ProjectImpl(data);

        dbmsA = project.children(ProjectUtil.DBMSES, DbmsImpl::new).findFirst().get();
        dbmsB = project.children(ProjectUtil.DBMSES, DbmsImpl::new).skip(1).findFirst().get();
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
