package com.speedment.runtime.internal.platform.component.impl;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.internal.runtime.DefaultApplicationBuilder;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author Emil Forslund
 */
public class CreateSpeedmentInstanceTest {
    
    @Test
    public void createSpeedmentInstance() {
        final Speedment speedment = new DefaultApplicationBuilder()
            .withCheckDatabaseConnectivity(false)
            .withValidateRuntimeConfig(false)
            .build();
        
        assertNotNull(speedment);
    }
    
}
