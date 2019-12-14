package com.speedment.tool.config;

import com.speedment.runtime.config.parameter.OrderType;
import com.speedment.runtime.config.trait.HasNullable;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.tool.config.trait.HasNullableProperty;
import com.speedment.tool.config.trait.HasOrderTypeProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
class HasOrderTypePropertyTest extends AbstractDocumentTest {

    @TestFactory
    @DisplayName("Traverse all HasOrderType-implementations")
    Stream<DynamicTest> test() {
        return DocumentUtil.traverseOver(project)
            .filter(HasOrderTypeProperty.class::isInstance)
            .map(HasOrderTypeProperty.class::cast)
            .map(doc -> dynamicTest(doc.getName(), () -> {
                doc.orderTypeProperty().setValue(OrderType.ASC);

                assertEquals(OrderType.ASC, doc.getOrderType());

                doc.orderTypeProperty().setValue(OrderType.DESC);

                assertEquals(OrderType.DESC, doc.getOrderType());

                doc.orderTypeProperty().setValue(OrderType.NONE);

                assertEquals(OrderType.NONE, doc.getOrderType());
            }));
    }

}
