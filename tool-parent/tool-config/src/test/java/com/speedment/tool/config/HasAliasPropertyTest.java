package com.speedment.tool.config;

import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.config.trait.HasAliasProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
class HasAliasPropertyTest extends AbstractDocumentTest {

    @TestFactory
    @DisplayName("Traverse all HasAlias-implementations")
    Stream<DynamicTest> test() {
        return DocumentUtil.traverseOver(project)
            .filter(HasAliasProperty.class::isInstance)
            .map(HasAliasProperty.class::cast)
            .map(doc -> dynamicTest(doc.getName(), () -> {
                assertEquals(doc.aliasProperty().get(), doc.getAlias().orElse(null));
                assertEquals(doc.getAlias().orElse(doc.getName()), doc.getName());
                doc.aliasProperty().setValue("OtherName");
                assertNotEquals("OtherName", doc.getName());
            }));
    }

}
