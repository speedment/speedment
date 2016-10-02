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
package com.speedment.runtime.config.util;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.runtime.config.util.DocumentUtil.Name.JAVA_NAME;
import com.speedment.generator.core.GeneratorBundle;
import com.speedment.generator.translator.namer.JavaLanguageNamer;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.tool.core.ToolBundle;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class TestConfig extends AbstractDocumentTest1 {
    /**
     * Test of relativeName method, of class DocumentUtil.
     */
    @Test
    public void testRelativeName_5args() {
        System.out.println("Testing: relativeName(Document, Class, Name, String, Function<String, String>)");

        final JavaLanguageNamer namer = ApplicationBuilder.empty()
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