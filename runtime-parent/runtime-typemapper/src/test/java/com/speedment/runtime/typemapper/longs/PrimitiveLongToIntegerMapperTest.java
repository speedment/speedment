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

final class PrimitiveLongToIntegerMapperTest extends AbstractTypeMapperTest<Long, Integer, PrimitiveLongToIntegerMapper> {

    PrimitiveLongToIntegerMapperTest() {
        super(
            Long.class,
            Integer.class,
            Category.INT,
            Ordering.RETAIN,
            PrimitiveLongToIntegerMapper::new);
    }

    @Override
    @Test
    protected void getJavaType() {
        assertEquals(int.class, typeMapper().getJavaType(column()));
    }

    @Override
    protected Map<Long, Integer> testVector() {
        Map<Long, Integer> map = new HashMap<>();
        map.put(100L, 100);
        map.put(2147483647L, Integer.MAX_VALUE);
        map.put(0L, 0);
        map.put(-2147483648L, Integer.MIN_VALUE);
        map.put(null, null);
        return map;
    }
}