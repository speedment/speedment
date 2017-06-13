/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
        Assert.assertEquals("JavaType should have value 'true'",  Boolean.TRUE, mapper.toJavaType(null, null, 1));
        Assert.assertEquals("JavaType should have value 'true'",  Boolean.TRUE, mapper.toJavaType(null, null, 2));
        Assert.assertEquals("JavaType should have value 'true'",  Boolean.TRUE, mapper.toJavaType(null, null, -1));
        Assert.assertTrue("DatabaseType should have value '1'", 1 == mapper.toDatabaseType(true));
    }

    @Test
    public void testStringNoMapping() {
    	Assert.assertEquals("JavaType should have value 'false'",  Boolean.FALSE, mapper.toJavaType(null, null, 0));
    	Assert.assertTrue("DatabaseType should have value '0'", 0 == mapper.toDatabaseType(false));
    }
}