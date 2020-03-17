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
package com.speedment.runtime.core.internal.config.mapper.string;

import com.speedment.runtime.typemapper.string.YesNoStringToBooleanMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Maria Sparenberg
 * @author Patrick Hobusch
 */
public class YesNoStringToBooleanMapperTest {

    private YesNoStringToBooleanMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new YesNoStringToBooleanMapper();
    }

    @Test
    public void testStringYesMapping() {
        String string = "YES";

        Boolean javaType = mapper.toJavaType(null, null, string);
        assertEquals( true, javaType, "JavaType should have value 'true'");

        String databaseType = mapper.toDatabaseType(javaType);
        assertTrue(string.equalsIgnoreCase(databaseType), "DatabaseType should have value 'yes'");
    }

    @Test
    public void testStringNoMapping() {
        String string = "NO";

        Boolean javaType = mapper.toJavaType(null, null, string);
        assertEquals(  false, javaType, "JavaType should have value 'false'");

        String databaseType = mapper.toDatabaseType(javaType);
        assertTrue(string.equalsIgnoreCase(databaseType), "DatabaseType should have value 'no'");
    }
}