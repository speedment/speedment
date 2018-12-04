/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.maven.abstractmojo;

import com.speedment.runtime.core.Speedment;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Paths;
import org.apache.maven.project.MavenProject;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AbstractReloadMojoTest {

    private AbstractReloadMojoTestImpl mojo;

    private String mockedConfigLocation = "testFile.txt";
    @Mock
    private Speedment mockedSpeedment;
    @Mock
    private ConfigFileHelper mockedConfigFileHelper;

    @BeforeEach
    public void setup() {
        MavenProject mavenProject = mock(MavenProject.class);
        when(mavenProject.getBasedir()).thenReturn(new File("baseDir"));

        mojo = new AbstractReloadMojoTestImpl() {
            @Override
            protected MavenProject project() {
                return mavenProject;
            }

        };
    }

    @Test
    public void execute() throws Exception {
        // Given
        when(mockedSpeedment.getOrThrow(ConfigFileHelper.class)).thenReturn(mockedConfigFileHelper);
        mojo.setConfigFile(mockedConfigLocation);

        // When
        mojo.execute(mockedSpeedment);

        // Then
        verify(mockedConfigFileHelper).setCurrentlyOpenFile(Paths.get("baseDir", mockedConfigLocation).toFile());
        verify(mockedConfigFileHelper).loadFromDatabaseAndSaveToFile();
    }

}
