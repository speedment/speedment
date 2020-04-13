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
package com.speedment.runtime.typemapper.integer;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class IntegerToByteMapperTest extends AbstractTypeMapperTest<Integer, Byte, IntegerToByteMapper> {

    IntegerToByteMapperTest() {
        super(
            Integer.class,
            Byte.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            IntegerToByteMapper::new);
    }

    @Override
    protected Map<Integer, Byte> testVector() {
        Map<Integer, Byte> map = new HashMap<>();
        map.put(100, Byte.valueOf("100"));
        map.put(-128, Byte.MIN_VALUE);
        map.put(0, Byte.valueOf("0"));
        map.put(127, Byte.MAX_VALUE);
        map.put(null, null);
        return map;
    }

}