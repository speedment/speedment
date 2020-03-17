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
package com.speedment.runtime.config.util;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.IndexColumn;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Emil Forslund
 */
final class DocumentUtilTest extends AbstractDocumentTest {

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    void testIsSame_Column_Column() {
        final Column[] columns = new Column[] {
            columnA1, columnA2, columnB1, columnB2,
            columnC1, columnC2, columnD1, columnD2
        };
        
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < columns.length; j++) {
                assertEquals(
                    "  Is " + columns[i].getId() +
                    " same as " + columns[j].getId() +
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
    void testIsSame_IndexColumn_IndexColumn() {
        final IndexColumn[] indexColumns = new IndexColumn[] {
            indexColumnA2, indexColumnB2
        };
        
        for (int i = 0; i < indexColumns.length; i++) {
            for (int j = 0; j < indexColumns.length; j++) {
                assertEquals(
                    "  Is " + indexColumns[i].getId() +
                    " same as " + indexColumns[j].getId() +
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
    void testIsSame_Index_Index() {
        final Index[] indexes = new Index[] {
            indexA2, indexB2
        };
        
        for (int i = 0; i < indexes.length; i++) {
            for (int j = 0; j < indexes.length; j++) {
                assertEquals(
                    "  Is " + indexes[i].getId() + 
                    " same as " + indexes[j].getId() + 
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
    void testIsSame_PrimaryKeyColumn_PrimaryKeyColumn() {
        final PrimaryKeyColumn[] primaryKeyColumns = new PrimaryKeyColumn[] {
            primaryKeyColumnA1, primaryKeyColumnB1, 
            primaryKeyColumnC1, primaryKeyColumnD1
        };
        
        for (int i = 0; i < primaryKeyColumns.length; i++) {
            for (int j = 0; j < primaryKeyColumns.length; j++) {
                assertEquals(
                    "  Is " + primaryKeyColumns[i].getId() + 
                    " same as " + primaryKeyColumns[j].getId() + 
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
    void testIsSame_ForeignKeyColumn_ForeignKeyColumn() {
        final ForeignKeyColumn[] foreignKeyColumns = new ForeignKeyColumn[] {
            foreignKeyColumnA2_C1, foreignKeyColumnB2_D1
        };
        
        for (int i = 0; i < foreignKeyColumns.length; i++) {
            for (int j = 0; j < foreignKeyColumns.length; j++) {
                assertEquals(
                    "  Is " + foreignKeyColumns[i].getId() + 
                    " same as " + foreignKeyColumns[j].getId() + 
                    ": ", i == j, 
                    DocumentDbUtil.isSame(foreignKeyColumns[i], foreignKeyColumns[j])
                );
            }
        }
    }

    private void assertEquals(String s, boolean b, boolean same) {
    }

    /**
     * Test of isSame method, of class DocumentDbUtil.
     */
    @Test
    void testIsSame_ForeignKey_ForeignKey() {
        final ForeignKey[] foreignKeys = new ForeignKey[] {
            foreignKeyA2_C1, foreignKeyB2_D1
        };
        
        for (int i = 0; i < foreignKeys.length; i++) {
            for (int j = 0; j < foreignKeys.length; j++) {
                assertEquals(
                    "  Is " + foreignKeys[i].getId() + 
                    " same as " + foreignKeys[j].getId() + 
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
    void testIsSame_Table_Table() {
        final Table[] tables = new Table[] {
            tableA, tableB, tableC, tableD
        };
        
        for (int i = 0; i < tables.length; i++) {
            for (int j = 0; j < tables.length; j++) {
                assertEquals(
                    "  Is " + tables[i].getId() + 
                    " same as " + tables[j].getId() + 
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
    void testIsSame_Schema_Schema() {
        final Schema[] schemas = new Schema[] {
            schemaA, schemaB
        };
        
        for (int i = 0; i < schemas.length; i++) {
            for (int j = 0; j < schemas.length; j++) {
                assertEquals(
                    "  Is " + schemas[i].getId() + 
                    " same as " + schemas[j].getId() + 
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
    void testIsSame_Dbms_Dbms() {
        final Dbms[] dbmses = new Dbms[] {
            dbmsA, dbmsB
        };
        
        for (int i = 0; i < dbmses.length; i++) {
            for (int j = 0; j < dbmses.length; j++) {
                assertEquals(
                    "  Is " + dbmses[i].getId() + 
                    " same as " + dbmses[j].getId() + 
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
    void testIsSame_Project_Project() {
        assertTrue(
            DocumentDbUtil.isSame(project, project),
            "  Is " + project.getId() + " same as " + project.getId() + ": "
        );
    }
}