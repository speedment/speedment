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
