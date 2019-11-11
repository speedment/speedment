package com.speedment.runtime.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

final class AtJpmsTest {

    @Test
    void atJpms() {
        try {
            String.class.getDeclaredField("value").setAccessible(true);
        } catch (Exception e) {
            if ("InaccessibleObjectException".equals(e.getClass().getSimpleName())) {
                return;
            }
        }
        fail("Not running under the module system");
    }

}
