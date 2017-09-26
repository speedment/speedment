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
package com.speedment.maven.abstractmojo;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import org.mockito.runners.MockitoJUnitRunner;


//@RunWith(MockitoJUnitRunner.class)
public class AbstractClearTablesMojoTest {
//
//	private AbstractClearTablesMojoTestImpl mojo;
//
//	private String mockedConfigLocation = "testFile.txt";
//	@Mock private Speedment mockedSpeedment;
//	@Mock private ConfigFileHelper mockedConfigFileHelper;
//	@Mock private MavenProject mockedMavenProject;
//
//
//	@Before
//	public void setup() {
//		when(mockedMavenProject.getBasedir()).thenReturn(new File("baseDir"));
//
//		mojo = new AbstractClearTablesMojoTestImpl() {
//			@Override
//			protected MavenProject project() {
//				return mockedMavenProject;
//			}
//		};
//	}
//
//	@Test
//	public void execute() throws Exception {
//		// Given
//		when(mockedSpeedment.getOrThrow(ConfigFileHelper.class)).thenReturn(mockedConfigFileHelper);
//		mojo.setConfigFile(mockedConfigLocation);
//
//		// When
//		mojo.execute(mockedSpeedment);
//
//		// Then
//		verify(mockedConfigFileHelper).setCurrentlyOpenFile(Paths.get("baseDir", mockedConfigLocation).toFile());
//		verify(mockedConfigFileHelper).clearTablesAndSaveToFile();
//	}
//
//	@Test(expected = MojoExecutionException.class)
//	public void executeException() throws Exception {
//		// Given
//		when(mockedSpeedment.getOrThrow(ConfigFileHelper.class)).thenReturn(mockedConfigFileHelper);
//		doThrow(new RuntimeException("test Exception")).when(mockedConfigFileHelper).clearTablesAndSaveToFile();
//		mojo.setConfigFile(mockedConfigLocation);
//
//		// When
//		mojo.execute(mockedSpeedment);
//
//		// Then
//	}
}