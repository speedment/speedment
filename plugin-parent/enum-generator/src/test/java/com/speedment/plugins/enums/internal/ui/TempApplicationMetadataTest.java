package com.speedment.plugins.enums.internal.ui;

import com.speedment.plugins.enums.TestUtil;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.tool.core.exception.SpeedmentToolException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TempApplicationMetadataTest {

    @Test
    void makeProject() throws IOException {
        final String json = Files.lines(Paths.get("src", "test", "resources", "project.json"))
                .collect(Collectors.joining(String.format("%n")));

        final TempApplicationMetadata instance = new TempApplicationMetadata(json);

        final Project expected = TestUtil.project();
        final Project actual = instance.makeProject();

        assertTrue(DocumentDbUtil.isSame(expected, actual));

    }

    @Test
    void makeProjectIllegalJson() throws IOException {
        final String json = "a";
        final TempApplicationMetadata instance = new TempApplicationMetadata(json);
        assertThrows(SpeedmentToolException.class, instance::makeProject);
    }
}