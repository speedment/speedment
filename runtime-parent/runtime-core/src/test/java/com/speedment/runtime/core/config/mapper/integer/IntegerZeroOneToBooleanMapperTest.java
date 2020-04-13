/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.config.mapper.integer;

import com.speedment.runtime.typemapper.integer.IntegerZeroOneToBooleanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Roberts Vartinss
 */
final class IntegerZeroOneToBooleanMapperTest {

    private IntegerZeroOneToBooleanMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new IntegerZeroOneToBooleanMapper();
    }

    @Test
    void testStringYesMapping() {
        assertEquals(Boolean.TRUE, mapper.toJavaType(null, null, 1), "JavaType should have value 'true'");
        assertEquals(Boolean.TRUE, mapper.toJavaType(null, null, 2), "JavaType should have value 'true'");
        assertEquals(Boolean.TRUE, mapper.toJavaType(null, null, -1), "JavaType should have value 'true'");
        assertTrue( 1 == mapper.toDatabaseType(true), "DatabaseType should have value '1'");
    }

    @Test
    void testStringNoMapping() {
    	assertEquals(Boolean.FALSE, mapper.toJavaType(null, null, 0), "JavaType should have value 'false'");
    	assertTrue( 0 == mapper.toDatabaseType(false), "DatabaseType should have value '0'");
    }
}