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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

final class IntEpochDaysToLocalDateMapperTest extends AbstractTypeMapperTest<Integer, LocalDate, IntEpochDaysToLocalDateMapper> {

    IntEpochDaysToLocalDateMapperTest() {
        super(
            Integer.class,
            LocalDate.class,
            TypeMapper.Category.COMPARABLE,
            TypeMapper.Ordering.RETAIN,
            IntEpochDaysToLocalDateMapper::new
        );
    }

    @Override
    protected Map<Integer, LocalDate> testVector() {
        Map<Integer, LocalDate> map = new HashMap<>();
        map.put(9133, LocalDate.of(1995,01,03));
        map.put(0, LocalDate.of(1970, 1, 1));
        map.put(null, null);
        return map;
    }
}