/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.example.typemapper;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper.Ordering;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Per Minborg
 */
final class IntegerZeroOneToYesNoTypeMapperTest {

    private IntegerZeroOneToYesNoTypeMapper mapper;
    private Column column;
    private Class<?> entityType;

    @BeforeEach
    void setUp() {
        mapper = new IntegerZeroOneToYesNoTypeMapper();
        column = null;
        entityType = null;
    }

    @AfterEach
    void tearDown() {
        mapper = null;
    }

    @Test
    void testGetLabel() {
        assertNotNull(mapper.getLabel());
    }

    @Test
    void testGetJavaType() {
        assertEquals(String.class, mapper.getJavaType(column));
    }

    @Test
    void testToJavaType() {
        assertEquals("Yes", mapper.toJavaType(column, entityType, 1));
        assertEquals("No", mapper.toJavaType(column, entityType, 0));
    }

    @Test
    void testToDatabaseType() {
        assertEquals((Integer) 1, mapper.toDatabaseType("Yes"));
        assertEquals((Integer) 0, mapper.toDatabaseType("No"));
    }

    @Test
    void testGetOrdering() {
        assertEquals(Ordering.RETAIN, mapper.getOrdering());
    }

}
