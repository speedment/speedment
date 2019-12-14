package com.speedment.tool.config;

import org.junit.jupiter.api.BeforeAll;

import static org.testfx.api.FxToolkit.registerPrimaryStage;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
abstract class AbstractTest {
    @BeforeAll
    static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }
}
