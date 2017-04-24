/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.internal.util.testing;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class JavaVersionUtilTest {

    @Test
    public void testGetJavaVersion() {
        System.out.println("Running java version " + JavaVersionUtil.getJavaVersion());
    }

    @Test
    public void testGetJavaVersion2() {
        System.out.println("Running java package version " + Runtime.class.getPackage().getImplementationVersion());
    }

}
