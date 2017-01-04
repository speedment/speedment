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
package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.InfoComponent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class InfoComponentImplTest {

    private Speedment speedment;
    private InfoComponent instance;

    @Before
    public void setUp() {
        speedment = ApplicationBuilder.empty().withComponent(InfoComponentImpl.class).build();
        instance = speedment.getOrThrow(InfoComponent.class);
    }

    @After
    public void tearDown() {
        speedment.stop();
    }

    @Test
    public void testVendor() {
        assertEquals("Speedment, Inc.", instance.getVendor());
    }

    @Test
    public void testTitle() {
        assertNotNull(instance.getTitle());
    }

    @Test
    public void testSubtitle() {
        assertNotNull(instance.getSubtitle());
    }

    @Test
    public void testImplementationVersion() {
        assertEquals(versionFromPom(), instance.getImplementationVersion());
    }

    @Test
    public void testSpecificationVersion() {
        assertNotNull(instance.getSpecificationVersion());
        assertTrue(instance.getImplementationVersion().contains(instance.getSpecificationVersion()));
    }

    // This truly sucks...
    private String versionFromPom() {
        String result = "*unknown*";
        final Path path = Paths.get("..", "pom.xml");
        try {
            final List<String> lines = Files.readAllLines(path);
            boolean seenGroupId = false;
            for (final String line : lines) {
                if (line.contains("<groupId>")) {
                    seenGroupId = true;
                }
                if (seenGroupId) {
                    final Pattern pattern = Pattern.compile("<version>(.+)</version>");
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        result = matcher.group(1);
                    }
                }
                if (line.contains("<packaging>")) {
                    return result;
                }

            }
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return result;
    }

}
