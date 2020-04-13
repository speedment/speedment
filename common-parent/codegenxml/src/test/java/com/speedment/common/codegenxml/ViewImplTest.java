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
package com.speedment.common.codegenxml;

import com.speedment.common.codegen.util.Formatting;
import org.junit.jupiter.api.Test;

import static com.speedment.common.codegenxml.Elements.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Per Minborg
 */
final class ViewImplTest {

    @Test
    void testSimpleHTML() {
        final Document doc = Document.html(
            html()
                .add(head()
                    .add(title().add("Welcome"))
                ).add(body()
                    .add("The content of the document")
                )
        );

        Formatting.tab("    ");
        final String html = new XmlGenerator().on(doc).get();
        assertTrue(html.contains("Welcome"));
    }
    
    @Test
    void testSimpleXML() {
        final Document doc = Document.xmlWithDocType(
            TagElement.of("project")
                .add(TagElement.of("dependencies")
                    .add(TagElement.of("dependency").add("Speedment"))
                )
        );

        Formatting.tab("    ");
        final String html = new XmlGenerator().on(doc).get();

        assertTrue(html.contains("Speedment"));
    }
}