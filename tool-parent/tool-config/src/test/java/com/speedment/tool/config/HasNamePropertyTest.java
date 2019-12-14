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
