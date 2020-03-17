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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.config.trait.HasColumnProperty;
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
class HasColumnPropertyTest extends AbstractDocumentTest {

    @TestFactory
    @DisplayName("Traverse all HasColumn-implementations")
    Stream<DynamicTest> test() {
        return DocumentUtil.traverseOver(project)
            .filter(HasColumnProperty.class::isInstance)
            .map(HasColumnProperty.class::cast)
            .map(doc -> dynamicTest(doc.getName(), () -> {
                assertTrue(doc.findColumn().isPresent(),
                    doc.getName() + " of type " + doc.getClass().getName()
                    + " is missing the referenced column");
                assertNotNull(doc.columnProperty().get());
                assertEquals(doc.findColumn().map(Column::getName).get(), doc.getName());
            }));
    }

}
