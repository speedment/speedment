/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
 */
package com.speedment.common.dbmodel.util;

import com.speedment.common.dbmodel.Dbms;
import com.speedment.common.dbmodel.Project;
import com.speedment.common.dbmodel.Schema;
import static com.speedment.common.dbmodel.util.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.common.dbmodel.util.DocumentUtil.Name.JAVA_NAME;
import com.speedment.generator.GeneratorBundle;
import com.speedment.generator.namer.JavaLanguageNamer;
import com.speedment.runtime.ApplicationBuilder;
import com.speedment.tool.ToolBundle;
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