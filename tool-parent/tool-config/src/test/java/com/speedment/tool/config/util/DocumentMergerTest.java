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
package com.speedment.tool.config.util;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.tool.config.AbstractDocumentTest;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.internal.component.DocumentPropertyComponentImpl;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
class DocumentMergerTest extends AbstractDocumentTest {

    @Test
    void testMergeSimple() {

        Project newProject = createFromJson(
            "{\"config\":{" +
                    "\"name\":\"TestProject\"," +
                    "\"dbmses\":[{" +
                        "\"name\":\"TestDbms\"," +
                        "\"schemas\":[{" +
                            "\"name\":\"TestSchema\"," +
                            "\"alias\":\"NewTestSchema\"," +
                            "\"tables\":[{" +
                                "\"name\":\"TestTable1\"," +
                                "\"columns\":[{" +
                                    "\"name\":\"TestTable1Column3\"," +
                                    "\"databaseType\":\"double\"" +
                                "}]" +
                            "}]" +
                        "}]" +
                    "}]" +
                "}}"
        );

        DocumentPropertyComponent docProps = new DocumentPropertyComponentImpl();
        project.merge(docProps, newProject);

        assertEquals("TestProject", project.getName());
        assertEquals("TestDbms", dbms.getName());
        assertEquals("TestSchema", schema.getName());
        assertEquals("NewTestSchema", schema.getAlias().orElse(schema.getName()));
        assertEquals("TestTable1", table1.getName());
        assertEquals("TestTable1Column1", table1Column1.getName());
        assertEquals("TestTable1Column2", table1Column2.getName());
        assertEquals("TestTable1Column3", table1Column3.getName());
        assertEquals("int", table1Column1.getDatabaseType());
        assertEquals("java.lang.String", table1Column2.getDatabaseType());
        assertEquals("double", table1Column3.getDatabaseType());
    }

    @Test
    void testMergeNewColumn() {

        Project newProject = createFromJson(
            "{\"config\":{" +
                "\"name\":\"TestProject\"," +
                "\"dbmses\":[{" +
                "\"name\":\"TestDbms\"," +
                "\"schemas\":[{" +
                "\"name\":\"TestSchema\"," +
                "\"alias\":\"NewTestSchema\"," +
                "\"tables\":[{" +
                "\"name\":\"TestTable1\"," +
                "\"columns\":[{" +
                "\"name\":\"TestTable1Column4\"," +
                "\"databaseType\":\"double\"" +
                "}]" +
                "}]" +
                "}]" +
                "}]" +
                "}}"
        );

        assertEquals(3, table1.columns().count());

        DocumentPropertyComponent docProps = new DocumentPropertyComponentImpl();
        project.merge(docProps, newProject);

        assertEquals(4, table1.columns().count());

        assertEquals("TestTable1Column1,TestTable1Column2,TestTable1Column3,TestTable1Column4",
            table1.columns().map(Column::getName).collect(Collectors.joining(",")));
    }

    private Project createFromJson(String projectJson) {
        return DocumentTranscoder.load(
            projectJson,
            json -> {
                @SuppressWarnings("unchecked")
                final Map<String, Object> result =
                    (Map<String, Object>) Json.fromJson(json);
                return result;
            }
        );
    }

}