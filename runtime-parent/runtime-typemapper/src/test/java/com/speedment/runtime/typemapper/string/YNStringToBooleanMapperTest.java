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
package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

final class YNStringToBooleanMapperTest extends AbstractTypeMapperTest<String, Boolean, YNStringToBooleanMapper> {

    YNStringToBooleanMapperTest() {
        super(
            String.class,
            Boolean.class,
            Category.BOOLEAN,
            YNStringToBooleanMapper::new);
    }

    @Override
    protected Map<String, Boolean> testVector() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("Y", true);
        map.put("N", false);
        map.put(null, null);
        return map;
    }

    @Override
    @Test
    protected void getJavaType() {
        when(column().isNullable()).thenReturn(true);
        assertEquals(Boolean.class, typeMapper().getJavaType(column()));
    }

}