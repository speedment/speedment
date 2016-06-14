/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.mapper.integer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Roberts Vartinss
 */
public class IntegerZeroOneToBooleanMapperTest {

    private IntegerZeroOneToBooleanMapper mapper;

    @Before
    public void setUp() {
        mapper = new IntegerZeroOneToBooleanMapper();
    }

    @Test
    public void testStringYesMapping() {
        Assert.assertEquals("JavaType should have value 'true'",  Boolean.TRUE, mapper.toJavaType(1));
        Assert.assertEquals("JavaType should have value 'true'",  Boolean.TRUE, mapper.toJavaType(2));
        Assert.assertEquals("JavaType should have value 'true'",  Boolean.TRUE, mapper.toJavaType(-1));
        Assert.assertTrue("DatabaseType should have value '1'", 1 == mapper.toDatabaseType(true));
    }

    @Test
    public void testStringNoMapping() {
    	Assert.assertEquals("JavaType should have value 'false'",  Boolean.FALSE, mapper.toJavaType(0));
    	Assert.assertTrue("DatabaseType should have value '0'", 0 == mapper.toDatabaseType(false));
    }
}