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

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.internal.core.runtime.DefaultSpeedmentApplicationLifecycle;
import static com.speedment.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.internal.util.document.DocumentUtil.Name.JAVA_NAME;
import com.speedment.util.JavaLanguageNamer;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentUtilTest extends AbstractDocumentTest {
    
    /**
     * Test of relativeName method, of class DocumentUtil.
     */
    @Test
    public void testRelativeName_5args() {
        System.out.println("Testing: relativeName(Document, Class, Name, String, Function<String, String>)");
        
        final JavaLanguageNamer namer = new DefaultSpeedmentApplicationLifecycle()
            .build().getCodeGenerationComponent().javaLanguageNamer();
        
        assertEquals("Check project java name: ", "project", DocumentUtil.relativeName(project, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project database name: ", "project", DocumentUtil.relativeName(project, Project.class, DATABASE_NAME, ".", namer::javaPackageName));
        
        assertEquals("Check dbms java name: ", "dbms_a", DocumentUtil.relativeName(dbmsA, Dbms.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check dbms database name: ", "dbms_a", DocumentUtil.relativeName(dbmsA, Dbms.class, DATABASE_NAME, ".", namer::javaPackageName));
        
        assertEquals("Check project.dbms java name: ", "project.dbms_a", DocumentUtil.relativeName(dbmsA, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project.dbms database name: ", "project.dbms_a", DocumentUtil.relativeName(dbmsA, Project.class, DATABASE_NAME, ".", namer::javaPackageName));
        
        assertEquals("Check schema java name: ", "custom_schema_a", DocumentUtil.relativeName(schemaA, Schema.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check schema database name: ", "schema_a", DocumentUtil.relativeName(schemaA, Schema.class, DATABASE_NAME, ".", namer::javaPackageName));
        
        assertEquals("Check dbms.schema java name: ", "dbms_a.custom_schema_a", DocumentUtil.relativeName(schemaA, Dbms.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check dbms.schema database name: ", "dbms_a.schema_a", DocumentUtil.relativeName(schemaA, Dbms.class, DATABASE_NAME, ".", namer::javaPackageName));
        
        assertEquals("Check project.dbms.schema java name: ", "project.dbms_a.custom_schema_a", DocumentUtil.relativeName(schemaA, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project.dbms.schema database name: ", "project.dbms_a.schema_a", DocumentUtil.relativeName(schemaA, Project.class, DATABASE_NAME, ".", namer::javaPackageName));
        
        assertEquals("Check project.dbms.schema.table java name: ", "project.dbms_a.custom_schema_a.custom_table_a", DocumentUtil.relativeName(tableA, Project.class, JAVA_NAME, ".", namer::javaPackageName));
        assertEquals("Check project.dbms.schema.table database name: ", "project.dbms_a.schema_a.table_a", DocumentUtil.relativeName(tableA, Project.class, DATABASE_NAME, ".", namer::javaPackageName));
    }
}
