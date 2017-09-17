package com.speedment.common.jvm_version.internal;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class InternalJvmVersionTest {

    private static final InternalJvmVersion instance = new InternalJvmVersion();

    @Test
    public void testGetSpecificationTitle() {
        final String result = instance.getSpecificationTitle();
        assertNotNull(result);
    }

    @Test
    public void testGetSpecificationVersion() {
        final String result = instance.getSpecificationVersion();
        assertNotNull(result);
    }

    @Test
    public void testGetSpecificationVendor() {
        final String result = instance.getSpecificationVendor();
        assertNotNull(result);
    }

    @Test
    public void testGetImplementationTitle() {
        final String result = instance.getImplementationTitle();
        assertNotNull(result);
    }

    @Test
    public void testGetImplementationVersion() {
        final String result = instance.getImplementationVersion();
        assertNotNull(result);
    }

    @Test
    public void testGetImplementationVendor() {
        final String result = instance.getImplementationVendor();
        assertNotNull(result);
    }

    @Test
    public void testMajor() {
        final int result = instance.major();
        assertTrue(result >= 8);
    }

    @Test
    public void testMinor() {
        final int result = instance.minor();
        assertTrue(result >= 0);
    }

    @Test
    public void testSecurity() {
        final int result = instance.security();
        assertTrue(result >= 0);
    }

}
