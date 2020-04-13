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