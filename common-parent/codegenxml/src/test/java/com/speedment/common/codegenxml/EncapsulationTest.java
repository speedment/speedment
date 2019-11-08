package com.speedment.common.codegenxml;

import com.speedment.common.codegen.internal.model.FieldImpl;
import com.speedment.common.codegen.model.Field;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Per Minborg
 */
@Disabled("This test does not work for Jenkins, See #815")
final class EncapsulationTest {


    @Test
    void internalNotAccessible() {
        assertThrows(IllegalAccessError.class, () -> new FieldImpl("s", String.class), "The module com.speedment.common.codegen is not encapsulated");
    }

    @Test
    void deepReflection() {
        final Field f = Field.of("s", String.class);

        try {
            f.getClass().getDeclaredField("name").setAccessible(true);
        } catch (Exception e) {
            if ("InaccessibleObjectException".equals(e.getClass().getSimpleName())) {
                return;
            }
        }
        fail("The module com.speedment.common.codegen is not encapsulated for deep reflection");
    }
}


