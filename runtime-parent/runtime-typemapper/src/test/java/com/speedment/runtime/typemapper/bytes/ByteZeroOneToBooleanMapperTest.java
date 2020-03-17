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
package com.speedment.runtime.typemapper.bytes;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

final class ByteZeroOneToBooleanMapperTest extends AbstractTypeMapperTest<Byte, Boolean, ByteZeroOneToBooleanMapper> {

    ByteZeroOneToBooleanMapperTest() {
        super(Byte.class,
            Boolean.class,
            Category.BOOLEAN,
            ByteZeroOneToBooleanMapper::new);
    }

    @Override
    protected Map<Byte, Boolean> testVector() {
        Map<Byte, Boolean> map = new HashMap<>();
        map.put((byte) 1, true);
        map.put((byte) 0, false);
        map.put(null, null);
        return map;
    }

    @Override
    protected void getJavaType() {
        when(column().isNullable()).thenReturn(true);
        assertEquals(Boolean.class, typeMapper().getJavaType(column()));
    }
}