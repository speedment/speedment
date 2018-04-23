/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.example.typemapper;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper.Ordering;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class IntegerZeroOneToYesNoTypeMapperTest {

    private IntegerZeroOneToYesNoTypeMapper mapper;
    private Column column;
    private Class<?> entityType;

    @Before
    public void setUp() {
        mapper = new IntegerZeroOneToYesNoTypeMapper();
        column = null;
        entityType = null;
    }

    @After
    public void tearDown() {
        mapper = null;
    }

    @Test
    public void testGetLabel() {
        assertNotNull(mapper.getLabel());
    }

    @Test
    public void testGetJavaType() {
        assertEquals(String.class, mapper.getJavaType(column));
    }

    @Test
    public void testToJavaType() {
        assertEquals("Yes", mapper.toJavaType(column, entityType, 1));
        assertEquals("No", mapper.toJavaType(column, entityType, 0));
    }

    @Test
    public void testToDatabaseType() {
        assertEquals((Integer) 1, mapper.toDatabaseType("Yes"));
        assertEquals((Integer) 0, mapper.toDatabaseType("No"));
    }

    @Test
    public void testGetOrdering() {
        assertEquals(Ordering.RETAIN, mapper.getOrdering());
    }

}
