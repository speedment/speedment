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
package com.speedment.runtime.core.internal.util.document;

import com.speedment.runtime.config.*;
import com.speedment.runtime.config.trait.HasAliasUtil;
import com.speedment.runtime.config.trait.HasEnableUtil;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.runtime.config.trait.HasTypeMapperUtil;
import org.junit.jupiter.api.BeforeEach;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

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
                entry(DbmsUtil.SCHEMAS, map(
                    entry(HasNameUtil.NAME, "Schema A"),
                    entry(HasAliasUtil.ALIAS, "Custom Schema A"),
                    entry(HasEnableUtil.ENABLED, true),
                    entry(SchemaUtil.TABLES, map(
                        entry(HasNameUtil.NAME, "Table A"),
                        entry(HasAliasUtil.ALIAS, "Custom Table A"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(TableUtil.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column A1"),
                            entry(HasAliasUtil.ALIAS, "Custom Column A1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, Long.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column A2"),
                            entry(HasAliasUtil.ALIAS, "Custom Column A2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, Integer.class.getName())
                        )),
                        entry(TableUtil.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column A1")
                        )),
                        entry(TableUtil.INDEXES, map(
                            entry(HasNameUtil.NAME, "Index A2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(IndexUtil.INDEX_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column A2")
                            ))
                        )),
                        entry(TableUtil.FOREIGN_KEYS, map(
                            entry(HasNameUtil.NAME, "ForeignKey A2 to C1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(ForeignKeyUtil.FOREIGN_KEY_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column A2"),
                                entry(ForeignKeyColumnUtil.FOREIGN_TABLE_NAME, "Table C"),
                                entry(ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME, "Column C1")
                            ))
                        ))
                    ), map(
                        entry(HasNameUtil.NAME, "Table C"),
                        entry(HasAliasUtil.ALIAS, "Custom Table C"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(TableUtil.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column C1"),
                            entry(HasAliasUtil.ALIAS, "Custom Column C1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, Integer.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column C2"),
                            entry(HasAliasUtil.ALIAS, "Custom Column C2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, String.class.getName())
                        )),
                        entry(TableUtil.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column C1")
                        ))
                    ))
                ))
            ), map(
                entry(HasNameUtil.NAME, "Dbms B"),
                entry(HasEnableUtil.ENABLED, true),
                entry(DbmsUtil.SCHEMAS, map(
                    entry(HasNameUtil.NAME, "Schema B"),
                    entry(HasAliasUtil.ALIAS, "Custom Schema B"),
                    entry(HasEnableUtil.ENABLED, true),
                    entry(SchemaUtil.TABLES, map(
                        entry(HasNameUtil.NAME, "Table B"),
                        entry(HasAliasUtil.ALIAS, "Custom Table B"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(TableUtil.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column B1"),
                            entry(HasAliasUtil.ALIAS, "Custom Column B1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, Long.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column B2"),
                            entry(HasAliasUtil.ALIAS, "Custom Column B2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, Integer.class.getName())
                        )),
                        entry(TableUtil.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column B1")
                        )),
                        entry(TableUtil.INDEXES, map(
                            entry(HasNameUtil.NAME, "Index B2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(IndexUtil.UNIQUE, true),
                            entry(IndexUtil.INDEX_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column B2")
                            ))
                        )),
                        entry(TableUtil.FOREIGN_KEYS, map(
                            entry(HasNameUtil.NAME, "ForeignKey B2 to D1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(ForeignKeyUtil.FOREIGN_KEY_COLUMNS, map(
                                entry(HasNameUtil.NAME, "Column B2"),
                                entry(ForeignKeyColumnUtil.FOREIGN_TABLE_NAME, "Table D"),
                                entry(ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME, "Column D1")
                            ))
                        ))
                    ), map(
                        entry(HasNameUtil.NAME, "Table D"),
                        entry(HasAliasUtil.ALIAS, "Custom Table D"),
                        entry(HasEnableUtil.ENABLED, true),
                        entry(TableUtil.COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column D1"),
                            entry(HasAliasUtil.ALIAS, "Custom Column D1"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, Integer.class.getName())
                        ), map(
                            entry(HasNameUtil.NAME, "Column D2"),
                            entry(HasAliasUtil.ALIAS, "Custom Column D2"),
                            entry(HasEnableUtil.ENABLED, true),
                            entry(HasTypeMapperUtil.DATABASE_TYPE, String.class.getName())
                        )),
                        entry(TableUtil.PRIMARY_KEY_COLUMNS, map(
                            entry(HasNameUtil.NAME, "Column D1")
                        ))
                    ))
                ))
            ))
        );

        project = Project.create(data);

        dbmsA = project.children(ProjectUtil.DBMSES, Dbms::create).findFirst().get();
        dbmsB = project.children(ProjectUtil.DBMSES, Dbms::create).skip(1).findFirst().get();
        schemaA = dbmsA.children(DbmsUtil.SCHEMAS, Schema::create).findFirst().get();
        schemaB = dbmsB.children(DbmsUtil.SCHEMAS, Schema::create).findFirst().get();
        tableA = schemaA.children(SchemaUtil.TABLES, Table::create).findFirst().get();
        tableB = schemaB.children(SchemaUtil.TABLES, Table::create).findFirst().get();
        tableC = schemaA.children(SchemaUtil.TABLES, Table::create).skip(1).findFirst().get();
        tableD = schemaB.children(SchemaUtil.TABLES, Table::create).skip(1).findFirst().get();
        columnA1 = tableA.children(TableUtil.COLUMNS, Column::create).findFirst().get();
        columnA2 = tableA.children(TableUtil.COLUMNS, Column::create).skip(1).findFirst().get();
        columnB1 = tableB.children(TableUtil.COLUMNS, Column::create).findFirst().get();
        columnB2 = tableB.children(TableUtil.COLUMNS, Column::create).skip(1).findFirst().get();
        columnC1 = tableC.children(TableUtil.COLUMNS, Column::create).findFirst().get();
        columnC2 = tableC.children(TableUtil.COLUMNS, Column::create).skip(1).findFirst().get();
        columnD1 = tableD.children(TableUtil.COLUMNS, Column::create).findFirst().get();
        columnD2 = tableD.children(TableUtil.COLUMNS, Column::create).skip(1).findFirst().get();
        primaryKeyColumnA1 = tableA.children(TableUtil.PRIMARY_KEY_COLUMNS, PrimaryKeyColumn::create).findFirst().get();
        primaryKeyColumnB1 = tableB.children(TableUtil.PRIMARY_KEY_COLUMNS, PrimaryKeyColumn::create).findFirst().get();
        primaryKeyColumnC1 = tableC.children(TableUtil.PRIMARY_KEY_COLUMNS, PrimaryKeyColumn::create).findFirst().get();
        primaryKeyColumnD1 = tableD.children(TableUtil.PRIMARY_KEY_COLUMNS, PrimaryKeyColumn::create).findFirst().get();
        indexA2 = tableA.children(TableUtil.INDEXES, Index::create).findFirst().get();
        indexB2 = tableB.children(TableUtil.INDEXES, Index::create).findFirst().get();
        indexColumnA2 = indexA2.children(IndexUtil.INDEX_COLUMNS, IndexColumn::create).findFirst().get();
        indexColumnB2 = indexB2.children(IndexUtil.INDEX_COLUMNS, IndexColumn::create).findFirst().get();
        foreignKeyA2_C1 = tableA.children(TableUtil.FOREIGN_KEYS, ForeignKey::create).findFirst().get();
        foreignKeyB2_D1 = tableB.children(TableUtil.FOREIGN_KEYS, ForeignKey::create).findFirst().get();
        foreignKeyColumnA2_C1 = foreignKeyA2_C1.children(ForeignKeyUtil.FOREIGN_KEY_COLUMNS, ForeignKeyColumn::create).findFirst().get();
        foreignKeyColumnB2_D1 = foreignKeyB2_D1.children(ForeignKeyUtil.FOREIGN_KEY_COLUMNS, ForeignKeyColumn::create).findFirst().get();
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
        return Stream.of(entries)
            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
