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
package com.speedment.internal.core.config.mapper.string;

import com.speedment.config.db.mapper.string.TrueFalseStringToBooleanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Maria Sparenberg
 * @author Patrick Hobusch
 */
public class TrueFalseStringToBooleanMapperTest {

    private TrueFalseStringToBooleanMapper mapper;

    @Before
    public void setUp() {
        mapper = new TrueFalseStringToBooleanMapper();
    }

    @Test
    public void testStringTrueMapping() {
        String string = "TRUE";

        Boolean javaType = mapper.toJavaType(string);
        Assert.assertEquals("JavaType should have value 'true'",  true, javaType);

        String databaseType = mapper.toDatabaseType(javaType);
        Assert.assertTrue("DatabaseType should have value 'true'", string.equalsIgnoreCase(databaseType));
    }

    @Test
    public void testStringFalseMapping() {
        String string = "FALSE";

        Boolean javaType = mapper.toJavaType(string);
        Assert.assertEquals("JavaType should have value 'false'",  false, javaType);

        String databaseType = mapper.toDatabaseType(javaType);
        Assert.assertTrue("DatabaseType should have value 'true'", string.equalsIgnoreCase(databaseType));
    }
}