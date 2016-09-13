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
package com.speedment.tool.util;

import com.speedment.generator.GeneratorBundle;
import com.speedment.generator.namer.JavaLanguageNamer;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.IndexColumn;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.DefaultApplicationBuilder;
import com.speedment.runtime.internal.EmptyApplicationMetadata;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.runtime.internal.util.document.DocumentUtil;
import org.junit.Test;

import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.JAVA_NAME;
import com.speedment.tool.ToolBundle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentUtilTest extends AbstractDocumentTest {

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    public void testIsSame_Column_Column() {
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
        assertTrue(
            "  Is " + project.getName() + 
            " same as " + project.getName() + ": ",
            DocumentDbUtil.isSame(project, project)
        );
    }
    
    /**
     * Test of relativeName method, of class DocumentUtil.
     */
    @Test
    public void testRelativeName_5args() {
        System.out.println("Testing: relativeName(Document, Class, Name, String, Function<String, String>)");

        final JavaLanguageNamer namer = new DefaultApplicationBuilder(EmptyApplicationMetadata.class)
            .withBundle(GeneratorBundle.class)
            .withBundle(ToolBundle.class)
            .build()
            .getOrThrow(JavaLanguageNamer.class);

        assertEquals("Check project java name: ", "project", DocumentUtil.relativeName(project, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project database name: ", "project", DocumentUtil.relativeName(project, Project.class, DATABASE_NAME, ".", namer::javaPackageName));

        assertEquals("Check dbms java name: ", "dbmsa", DocumentUtil.relativeName(dbmsA, Dbms.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check dbms database name: ", "dbmsa", DocumentUtil.relativeName(dbmsA, Dbms.class, DATABASE_NAME, ".", namer::javaPackageName));

        assertEquals("Check project.dbms java name: ", "project.dbmsa", DocumentUtil.relativeName(dbmsA, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project.dbms database name: ", "project.dbmsa", DocumentUtil.relativeName(dbmsA, Project.class, DATABASE_NAME, ".", namer::javaPackageName));

        assertEquals("Check schema java name: ", "customschemaa", DocumentUtil.relativeName(schemaA, Schema.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check schema database name: ", "schemaa", DocumentUtil.relativeName(schemaA, Schema.class, DATABASE_NAME, ".", namer::javaPackageName));

        assertEquals("Check dbms.schema java name: ", "dbmsa.customschemaa", DocumentUtil.relativeName(schemaA, Dbms.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check dbms.schema database name: ", "dbmsa.schemaa", DocumentUtil.relativeName(schemaA, Dbms.class, DATABASE_NAME, ".", namer::javaPackageName));

        assertEquals("Check project.dbms.schema java name: ", "project.dbmsa.customschemaa", DocumentUtil.relativeName(schemaA, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project.dbms.schema database name: ", "project.dbmsa.schemaa", DocumentUtil.relativeName(schemaA, Project.class, DATABASE_NAME, ".", namer::javaPackageName));

        assertEquals("Check project.dbms.schema.table java name: ", "project.dbmsa.customschemaa.customtablea", DocumentUtil.relativeName(tableA, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project.dbms.schema.table database name: ", "project.dbmsa.schemaa.tablea", DocumentUtil.relativeName(tableA, Project.class, DATABASE_NAME, ".", namer::javaPackageName));
    }
}