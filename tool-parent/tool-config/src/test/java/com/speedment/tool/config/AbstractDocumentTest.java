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
package com.speedment.tool.config;

import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.List;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
public class AbstractDocumentTest extends AbstractTest {

    public ProjectProperty project;
    public DbmsProperty dbms;
    public SchemaProperty schema;
    public TableProperty table1;
    public TableProperty table2;
    public ColumnProperty table1Column1;
    public ColumnProperty table1Column2;
    public ColumnProperty table1Column3;
    ColumnProperty table2Column1;
    ColumnProperty table2Column2;
    ColumnProperty table2Column3;
    ForeignKeyProperty fk1;
    IndexProperty idx1, idx2, idx3, idx4, idx5;
    PrimaryKeyColumnProperty pk1, pk2;
    List<DocumentProperty> all;

    @BeforeEach
    void setUp() {
        all = Arrays.asList(
            project = createMockProject("TestProject"),
            dbms = createMockDbms(project, "TestDbms"),
            schema = createMockSchema(dbms, "TestSchema"),
            table1 = createMockTable(schema, "TestTable1"),
            table2 = createMockTable(schema, "TestTable2"),
            table1Column1 = createMockColumn(table1, "TestTable1Column1", "int"),
            table1Column2 = createMockColumn(table1, "TestTable1Column2", "java.lang.String"),
            table1Column3 = createMockColumn(table1, "TestTable1Column3", "long"),
            table2Column1 = createMockColumn(table2, "TestTable2Column1", "int"),
            table2Column2 = createMockColumn(table2, "TestTable2Column2", "java.lang.String"),
            table2Column3 = createMockColumn(table2, "TestTable2Column3", "long"),
            idx1 = createMockIndex(table1Column1, true),
            idx2 = createMockIndex(table1Column2, true),
            idx3 = createMockIndex(table1Column3, false),
            idx4 = createMockIndex(table2Column1, true),
            idx5 = createMockIndex(table2Column3, true),
            fk1 = createMockForeignKey(table1Column1, table2Column1),
            pk1 = createMockPrimaryKey(table1Column1),
            pk2 = createMockPrimaryKey(table2Column3)
        );
    }

    private ProjectProperty createMockProject(String name) {
        ProjectProperty project = new ProjectProperty();
        project.nameProperty().setValue(name);
        project.enabledProperty().setValue(true);
        return project;
    }

    private DbmsProperty createMockDbms(ProjectProperty project, String name) {
        DbmsProperty dbms = new DbmsProperty(project);
        project.dbmsesProperty().add(dbms);
        dbms.nameProperty().setValue(name);
        dbms.enabledProperty().setValue(true);
        return dbms;
    }

    private SchemaProperty createMockSchema(DbmsProperty dbms, String name) {
        SchemaProperty schema = new SchemaProperty(dbms);
        dbms.schemasProperty().add(schema);
        schema.nameProperty().setValue(name);
        schema.enabledProperty().setValue(true);
        return schema;
    }

    private TableProperty createMockTable(SchemaProperty schema, String name) {
        TableProperty table = new TableProperty(schema);
        schema.tablesProperty().add(table);
        table.nameProperty().setValue(name);
        table.enabledProperty().setValue(true);
        return table;
    }

    private ColumnProperty createMockColumn(TableProperty table, String name, String dbType) {
        ColumnProperty column = new ColumnProperty(table);
        table.columnsProperty().add(column);
        column.nameProperty().setValue(name);
        column.enabledProperty().setValue(true);
        switch (dbType) {
            case "int": case "long":
                column.typeMapperProperty().setValue(null);
                break;
            default:
                column.typeMapperProperty().setValue(TypeMapper.identity().getClass().getName());
                break;
        }
        column.stringPropertyOf("databaseType", () -> "").setValue(dbType);
        return column;
    }

    private PrimaryKeyColumnProperty createMockPrimaryKey(ColumnProperty column) {
        TableProperty table = (TableProperty) column.getParentOrThrow();
        PrimaryKeyColumnProperty pkCol = new PrimaryKeyColumnProperty(table);
        table.primaryKeyColumnsProperty().add(pkCol);

        pkCol.enabledProperty().setValue(true);
        pkCol.nameProperty().setValue(column.getName());

        return pkCol;
    }

    private ForeignKeyProperty createMockForeignKey(ColumnProperty from, ColumnProperty to) {
        TableProperty table = (TableProperty) from.getParentOrThrow();
        ForeignKeyProperty fk = new ForeignKeyProperty(table);
        table.foreignKeysProperty().add(fk);

        fk.enabledProperty().setValue(true);
        fk.nameProperty().setValue(
            from.getParentOrThrow().getName()+ "_" + from.getName() + "_to_" +
            to.getParentOrThrow().getName() + "_" + to.getName());

        ForeignKeyColumnProperty fkCol = new ForeignKeyColumnProperty(fk);
        fk.foreignKeyColumnsProperty().add(fkCol);
        fkCol.nameProperty().setValue(from.getName());
        fkCol.stringPropertyOf("foreignDatabaseName", () -> null).setValue(table.getParentOrThrow().getParentOrThrow().getName());
        fkCol.stringPropertyOf("foreignSchemaName", () -> null).setValue(table.getParentOrThrow().getName());
        fkCol.stringPropertyOf("foreignTableName", () -> null).setValue(table.getName());
        fkCol.stringPropertyOf("foreignColumnName", () -> null).setValue(to.getName());

        return fk;
    }

    private IndexProperty createMockIndex(ColumnProperty column, boolean unique) {
        TableProperty table = (TableProperty) column.getParentOrThrow();
        IndexProperty idx = new IndexProperty(table);
        table.indexesProperty().add(idx);

        idx.nameProperty().setValue(column.getName() + "_Idx" + (unique ? "Unique" : ""));
        idx.uniqueProperty().setValue(unique);
        idx.enabledProperty().setValue(true);

        IndexColumnProperty idxCol = new IndexColumnProperty(idx);
        idx.indexColumnsProperty().add(idxCol);
        idxCol.nameProperty().setValue(column.getName());

        return idx;
    }


}
