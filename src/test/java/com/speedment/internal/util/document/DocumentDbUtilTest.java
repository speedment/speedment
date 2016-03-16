/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.util.document;

import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.db.mapper.identity.IntegerIdentityMapper;
import com.speedment.config.db.mapper.identity.LongIdentityMapper;
import com.speedment.config.db.mapper.identity.StringIdentityMapper;
import com.speedment.internal.core.config.db.ColumnImpl;
import com.speedment.internal.core.config.db.DbmsImpl;
import com.speedment.internal.core.config.db.ForeignKeyColumnImpl;
import com.speedment.internal.core.config.db.ForeignKeyImpl;
import com.speedment.internal.core.config.db.IndexColumnImpl;
import com.speedment.internal.core.config.db.IndexImpl;
import com.speedment.internal.core.config.db.PrimaryKeyColumnImpl;
import com.speedment.internal.core.config.db.ProjectImpl;
import com.speedment.internal.core.config.db.SchemaImpl;
import com.speedment.internal.core.config.db.TableImpl;
import com.speedment.stream.MapStream;
import java.util.AbstractMap;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentDbUtilTest {

    private Project project;
    private Dbms dbmsA, dbmsB;
    private Schema schemaA, schemaB;
    private Table tableA, tableB, tableC, tableD;
    private Column columnA1, columnA2, columnB1, columnB2, columnC1, columnC2, columnD1, columnD2;
    private PrimaryKeyColumn primaryKeyColumnA1, primaryKeyColumnB1, primaryKeyColumnC1, primaryKeyColumnD1;
    private Index indexA2, indexB2;
    private IndexColumn indexColumnA2, indexColumnB2;
    private ForeignKey foreignKeyA2_C1, foreignKeyB2_D1;
    private ForeignKeyColumn foreignKeyColumnA2_C1, foreignKeyColumnB2_D1;
    
    private static Map.Entry<String, Object> entry(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
    
    private static Map.Entry<String, Object> entry(String key, boolean value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
    
    private static Map.Entry<String, Object> entry(String key, Map<String, Object>... children) {
        return new AbstractMap.SimpleEntry<>(key, Stream.of(children).collect(toList()));
    }
    
    private static Map<String, Object> map(Map.Entry<String, Object>... entries) {
        return MapStream.of(Stream.of(entries)).toMap();
    }
    
    @Before
    public void setUp() {
        final Map<String, Object> data = map(
            entry(Project.NAME, "Project"),
            entry(Project.ENABLED, true),
            entry(Project.DBMSES, map(
                entry(Dbms.NAME, "Dbms A"),
                entry(Dbms.ENABLED, true),
                entry(Dbms.SCHEMAS, map(
                    entry(Schema.NAME, "Schema A"),
                    entry(Schema.ENABLED, true),
                    entry(Schema.TABLES, map(
                        entry(Table.NAME, "Table A"),
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column A1"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, LongIdentityMapper.class.getName())
                        ), map(
                            entry(Column.NAME, "Column A2"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, IntegerIdentityMapper.class.getName())
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
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column C1"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, IntegerIdentityMapper.class.getName())
                        ), map(
                            entry(Column.NAME, "Column C2"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, StringIdentityMapper.class.getName())
                        )),
                        entry(Table.PRIMARY_KEY_COLUMNS, map(
                            entry(PrimaryKeyColumn.NAME, "Column C1")
                        ))
                    ))
                ))
            ), map(
                entry(Dbms.NAME, "Dbms B"),
                entry(Dbms.ENABLED, true),
                entry(Dbms.SCHEMAS, map(
                    entry(Schema.NAME, "Schema B"),
                    entry(Schema.ENABLED, true),
                    entry(Schema.TABLES, map(
                        entry(Table.NAME, "Table B"),
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column B1"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, LongIdentityMapper.class.getName())
                        ), map(
                            entry(Column.NAME, "Column B2"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, IntegerIdentityMapper.class.getName())
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
                        entry(Table.ENABLED, true),
                        entry(Table.COLUMNS, map(
                            entry(Column.NAME, "Column D1"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, IntegerIdentityMapper.class.getName())
                        ), map(
                            entry(Column.NAME, "Column D2"),
                            entry(Column.ENABLED, true),
                            entry(Column.TYPE_MAPPER, StringIdentityMapper.class.getName())
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

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_Column_Column() {
        System.out.println("Testing: isSame(Column, Column)");
        final Column[] columns = new Column[] {
            columnA1, columnA2, columnB1, columnB2,
            columnC1, columnC2, columnD1, columnD2
        };
        
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns.length; j++) {
                assertEquals(
                    "  Is " + columns[i].getName() + 
                    " same as " + columns[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(columns[i], columns[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_IndexColumn_IndexColumn() {
        System.out.println("Testing: isSame(IndexColumn, IndexColumn)");
        final IndexColumn[] indexColumns = new IndexColumn[] {
            indexColumnA2, indexColumnB2
        };
        
        for (int i = 0; i < indexColumns.length; i++) {
            for (int j = 0; j < indexColumns.length; j++) {
                assertEquals(
                    "  Is " + indexColumns[i].getName() + 
                    " same as " + indexColumns[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(indexColumns[i], indexColumns[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_Index_Index() {
        System.out.println("Testing: isSame(Index, Index)");
        final Index[] indexes = new Index[] {
            indexA2, indexB2
        };
        
        for (int i = 0; i < indexes.length; i++) {
            for (int j = 0; j < indexes.length; j++) {
                assertEquals(
                    "  Is " + indexes[i].getName() + 
                    " same as " + indexes[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(indexes[i], indexes[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_PrimaryKeyColumn_PrimaryKeyColumn() {
        System.out.println("Testing: isSame(PrimaryKeyColumn, PrimaryKeyColumn)");
        final PrimaryKeyColumn[] primaryKeyColumns = new PrimaryKeyColumn[] {
            primaryKeyColumnA1, primaryKeyColumnB1, 
            primaryKeyColumnC1, primaryKeyColumnD1
        };
        
        for (int i = 0; i < primaryKeyColumns.length; i++) {
            for (int j = 0; j < primaryKeyColumns.length; j++) {
                assertEquals(
                    "  Is " + primaryKeyColumns[i].getName() + 
                    " same as " + primaryKeyColumns[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(primaryKeyColumns[i], primaryKeyColumns[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_ForeignKeyColumn_ForeignKeyColumn() {
        System.out.println("Testing: isSame(ForeignKeyColumn, ForeignKeyColumn)");
        final ForeignKeyColumn[] foreignKeyColumns = new ForeignKeyColumn[] {
            foreignKeyColumnA2_C1, foreignKeyColumnB2_D1
        };
        
        for (int i = 0; i < foreignKeyColumns.length; i++) {
            for (int j = 0; j < foreignKeyColumns.length; j++) {
                assertEquals(
                    "  Is " + foreignKeyColumns[i].getName() + 
                    " same as " + foreignKeyColumns[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(foreignKeyColumns[i], foreignKeyColumns[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_ForeignKey_ForeignKey() {
        System.out.println("Testing: isSame(ForeignKey, ForeignKey)");
        final ForeignKey[] foreignKeys = new ForeignKey[] {
            foreignKeyA2_C1, foreignKeyB2_D1
        };
        
        for (int i = 0; i < foreignKeys.length; i++) {
            for (int j = 0; j < foreignKeys.length; j++) {
                assertEquals(
                    "  Is " + foreignKeys[i].getName() + 
                    " same as " + foreignKeys[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(foreignKeys[i], foreignKeys[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_Table_Table() {
        System.out.println("Testing: isSame(Table, Table)");
        final Table[] tables = new Table[] {
            tableA, tableB, tableC, tableD
        };
        
        for (int i = 0; i < tables.length; i++) {
            for (int j = 0; j < tables.length; j++) {
                assertEquals(
                    "  Is " + tables[i].getName() + 
                    " same as " + tables[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(tables[i], tables[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_Schema_Schema() {
        System.out.println("Testing: isSame(Schema, Schema)");
        final Schema[] schemas = new Schema[] {
            schemaA, schemaB
        };
        
        for (int i = 0; i < schemas.length; i++) {
            for (int j = 0; j < schemas.length; j++) {
                assertEquals(
                    "  Is " + schemas[i].getName() + 
                    " same as " + schemas[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(schemas[i], schemas[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_Dbms_Dbms() {
        System.out.println("Testing: isSame(Dbms, Dbms)");
        final Dbms[] dbmses = new Dbms[] {
            dbmsA, dbmsB
        };
        
        for (int i = 0; i < dbmses.length; i++) {
            for (int j = 0; j < dbmses.length; j++) {
                assertEquals(
                    "  Is " + dbmses[i].getName() + 
                    " same as " + dbmses[j].getName() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(dbmses[i], dbmses[j])
                );
            }
        }
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_Project_Project() {
        System.out.println("Testing: isSame(Project, Project)");
        assertTrue(
            "  Is " + project.getName() + 
            " same as " + project.getName() + ": ",
            DocumentDbUtil.isSame(project, project)
        );
    }
    
    /**
     * Test of isUnique method, of class DocumentDbUtil.
     */
    @Test
    public void testIsUnique() {
        System.out.println("Testing: isUnique(Column)");
        assertTrue("  Is " + columnA1.getName() + " unique: ", DocumentDbUtil.isUnique(columnA1));
        assertFalse("  Is " + columnA2.getName() + " unique: ", DocumentDbUtil.isUnique(columnA2));
        assertTrue("  Is " + columnB1.getName() + " unique: ", DocumentDbUtil.isUnique(columnB1));
        assertTrue("  Is " + columnB2.getName() + " unique: ", DocumentDbUtil.isUnique(columnB2));
        assertTrue("  Is " + columnC1.getName() + " unique: ", DocumentDbUtil.isUnique(columnC1));
        assertFalse("  Is " + columnC2.getName() + " unique: ", DocumentDbUtil.isUnique(columnC2));
        assertTrue("  Is " + columnD1.getName() + " unique: ", DocumentDbUtil.isUnique(columnD1));
        assertFalse("  Is " + columnD2.getName() + " unique: ", DocumentDbUtil.isUnique(columnD2));
    }
}
