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
package com.speedment.tool.config;

import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.config.trait.HasNameProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
class HasNamePropertyTest extends AbstractDocumentTest {

    @TestFactory
    @DisplayName("Traverse all HasName-implementations")
    Stream<DynamicTest> test() {
        return DocumentUtil.traverseOver(project)
            .filter(HasNameProperty.class::isInstance)
            .map(HasNameProperty.class::cast)
            .map(doc -> dynamicTest(doc.getName(), () -> {
                assertNotNull(doc.getName());
                assertEquals(doc.nameProperty().get(), doc.getName());
                assertNotEquals("", doc.getName());
            }));
    }

}
