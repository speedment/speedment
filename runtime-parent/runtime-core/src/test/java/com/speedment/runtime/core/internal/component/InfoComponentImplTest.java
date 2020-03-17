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
package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.core.component.InfoComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Per Minborg
 */
final class InfoComponentImplTest {

    private InfoComponent instance;

    @BeforeEach
    void setUp() {
        instance = new InfoComponentImpl();
    }

    @Test
    void testVendor() {
        assertEquals("Speedment, Inc.", instance.getVendor());
    }

    @Test
    void testTitle() {
        assertNotNull(instance.getTitle());
    }

    @Test
    void testSubtitle() {
        assertNotNull(instance.getSubtitle());
    }

    @Test
    void testImplementationVersion() {
        assertEquals(versionFromPom(), instance.getImplementationVersion());
    }

    @Test
    void testSpecificationVersion() {
        assertNotNull(instance.getSpecificationVersion());
        assertTrue(instance.getImplementationVersion().contains(instance.getSpecificationVersion()));
    }

    // This truly sucks...
    private String versionFromPom() {
        final Pattern pattern = Pattern.compile("<version>(.+)</version>");
        
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
