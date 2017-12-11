/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.util.DocumentTranscoder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ConfigFileHelperTest {

    // TODO: Enable these tests after Mockito works in Java9
    
    private ConfigFileHelper helper;
    @Mock
    private File mockedCurrentlyOpenFile;

    @Before
    public void setup() {
        helper = new ConfigFileHelper();
        helper.setCurrentlyOpenFile(mockedCurrentlyOpenFile);
    }

    @Test
    public void clearTablesAndSaveToFile() throws Exception {

        try {

            // Given
            Mockito.when(mockedCurrentlyOpenFile.toPath()).thenReturn(new File("src/test/resources/testIn.json").toPath(), new File("target/testOut.json").toPath());
            Mockito.when(mockedCurrentlyOpenFile.isFile()).thenReturn(true);
            Mockito.when(mockedCurrentlyOpenFile.exists()).thenReturn(true);

            // When
            helper.clearTablesAndSaveToFile();

            // Then
            Mockito.verify(mockedCurrentlyOpenFile).delete();
            verifyOutputFile("target/testOut.json");

        } catch (SpeedmentConfigException e) {
            System.out.println("WARNING: Test skipped in gradle");
        }
    }

    @Test
    public void clearTablesAndSaveToFileNoTables() throws Exception {

        try {
            // Given
            Mockito.when(mockedCurrentlyOpenFile.toPath()).thenReturn(new File("src/test/resources/testInNoTables.json").toPath(), new File("target/testOutNoTables.json").toPath());
            Mockito.when(mockedCurrentlyOpenFile.isFile()).thenReturn(true);
            Mockito.when(mockedCurrentlyOpenFile.exists()).thenReturn(true);

            // When
            helper.clearTablesAndSaveToFile();

            // Then
            Mockito.verify(mockedCurrentlyOpenFile).delete();
            verifyOutputFile("target/testOutNoTables.json");
        } catch (SpeedmentConfigException e) {
            System.out.println("WARNING: Test skipped in gradle");
        }
    }

    private void verifyOutputFile(String fileName) {
        Project project = DocumentTranscoder.load(new File(fileName).toPath(), this::fromJson);
        project.dbmses().forEach(dbms -> {
            dbms.schemas().forEach(schema -> {
                Assert.assertNull(schema.getData().get("tables"));
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
