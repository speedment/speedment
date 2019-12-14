package com.speedment.tool.config;

import com.speedment.runtime.config.trait.HasNullable;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.config.trait.HasNullableProperty;
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
class HasNullablePropertyTest extends AbstractDocumentTest {

    @TestFactory
    @DisplayName("Traverse all HasNullable-implementations")
    Stream<DynamicTest> test() {
        return DocumentUtil.traverseOver(project)
            .filter(HasNullableProperty.class::isInstance)
            .map(HasNullableProperty.class::cast)
            .map(doc -> dynamicTest(doc.getName(), () -> {
                assertTrue(doc.isNullable());
                assertEquals(HasNullable.ImplementAs.OPTIONAL, doc.getNullableImplementation());

                doc.nullableImplementationProperty().setValue(HasNullable.ImplementAs.WRAPPER);

                assertEquals(HasNullable.ImplementAs.WRAPPER, doc.getNullableImplementation());
            }));
    }

}
