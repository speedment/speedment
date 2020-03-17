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
package com.speedment.runtime.typemapper.longs;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class PrimitiveLongToShortMapperTest extends AbstractTypeMapperTest<Long, Short, PrimitiveLongToShortMapper> {

    PrimitiveLongToShortMapperTest() {
        super(
            Long.class,
            Short.class,
            Category.SHORT,
            Ordering.RETAIN,
            PrimitiveLongToShortMapper::new);
    }

    @Override
    @Test
    protected void getJavaType() {
        assertEquals(short.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Long, Short> testVector() {
        Map<Long, Short> map = new HashMap<>();
        map.put(3387L, Short.valueOf("3387"));
        map.put(null, null);
        map.put(0L, Short.valueOf("0"));
        map.put(-3334L, Short.valueOf("-3334"));
        return map;
    }
}