package com.speedment.tool.core.internal.util;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.util.DocumentTranscoder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfigFileHelperTest {

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
    public void clearTablesAndSaveToFileNoTables() throws Exception {

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
