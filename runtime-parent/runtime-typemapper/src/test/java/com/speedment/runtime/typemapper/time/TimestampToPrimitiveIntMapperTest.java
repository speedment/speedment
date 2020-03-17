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
package com.speedment.runtime.typemapper.time;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class TimestampToPrimitiveIntMapperTest extends AbstractTypeMapperTest<Timestamp, Integer, TimestampToPrimitiveIntMapper> {

    TimestampToPrimitiveIntMapperTest() {
        super(
            Timestamp.class,
            Integer.class,
            TypeMapper.Category.INT,
            TypeMapper.Ordering.RETAIN,
            TimestampToPrimitiveIntMapper::new);
    }

    @Override
    protected void getJavaType() {
        assertEquals(int.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Timestamp, Integer> testVector() {
        Map<Timestamp, Integer> map = new HashMap<>();
        map.put(new Timestamp(0L), 0);
        map.put(new Timestamp(788918400000L), 788918400);
        map.put(null, null);
        return map;
    }
}