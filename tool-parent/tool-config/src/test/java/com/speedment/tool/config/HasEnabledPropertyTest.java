package com.speedment.tool.config;

import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.config.trait.HasEnabledProperty;
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
class HasEnabledPropertyTest extends AbstractDocumentTest {

    @TestFactory
    @DisplayName("Traverse all HasEnabled-implementations")
    Stream<DynamicTest> test() {
        return DocumentUtil.traverseOver(project)
            .filter(HasEnabledProperty.class::isInstance)
            .map(HasEnabledProperty.class::cast)
            .map(doc -> dynamicTest(doc.getName(), () -> {
                assertTrue(doc.isEnabled(),
                    doc.getName() + " of type " + doc.getClass().getName()
                    + " is not enabled by default");
            }));
    }

}
