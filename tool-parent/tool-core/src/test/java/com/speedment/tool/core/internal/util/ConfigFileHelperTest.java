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
package com.speedment.tool.core.internal.util;

import com.speedment.common.json.Json;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

final class ConfigFileHelperTest {

    private ConfigFileHelper helper;
    @Mock
    private File mockedCurrentlyOpenFile;

    @Mock
    private DocumentPropertyComponent documentPropertyComponent;
    @Mock
    private DbmsHandlerComponent dbmsHandlerComponent;
    @Mock
    private PasswordComponent passwordComponent;
    @Mock
    private TranslatorManager translatorManager;
    @Mock
    private ProjectComponent projectComponent;
    @Mock
    private InfoComponent infoComponent;


    @BeforeEach
    void setup() {
        helper = new ConfigFileHelper(documentPropertyComponent, dbmsHandlerComponent, passwordComponent, translatorManager, projectComponent, infoComponent, mockedCurrentlyOpenFile);
        helper.setCurrentlyOpenFile(mockedCurrentlyOpenFile);
    }

    @Test
    @Disabled
    void clearTablesAndSaveToFile() throws Exception {

        try {

            // Given
            when(mockedCurrentlyOpenFile.toPath()).thenReturn(new File("src/test/resources/testIn.json").toPath(), new File("target/testOut.json").toPath());

            // When
            helper.clearTablesAndSaveToFile();

            // Then
            verifyOutputFile("target/testOut.json");

        } catch (SpeedmentConfigException e) {
            System.out.println("WARNING: Test skipped in gradle");
        }
    }

    @Test
    @Disabled
    void clearTablesAndSaveToFileNoTables() throws Exception {

        try {
            // Given
            when(mockedCurrentlyOpenFile.toPath()).thenReturn(new File("src/test/resources/testInNoTables.json").toPath(), new File("target/testOutNoTables.json").toPath());

            // When
            helper.clearTablesAndSaveToFile();

            // Then
            verifyOutputFile("target/testOutNoTables.json");
        } catch (SpeedmentConfigException e) {
            System.out.println("WARNING: Test skipped in gradle");
        }
    }

    private void verifyOutputFile(String fileName) {
        Project project = DocumentTranscoder.load(new File(fileName).toPath(), this::fromJson);
        project.dbmses().forEach(dbms -> {
            dbms.schemas().forEach(schema -> {
                assertNull(schema.getData().get("tables"));
            });
        });
        // when the file is correct, remove it.
        new File(fileName).delete();
    }

    private Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed = (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }

}
