/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class RuntimeBundleTest {
    
    @Test
    public void testInjectables() {
        assertTrue(new RuntimeBundle().injectables().count() > 0);
    }
    
}
