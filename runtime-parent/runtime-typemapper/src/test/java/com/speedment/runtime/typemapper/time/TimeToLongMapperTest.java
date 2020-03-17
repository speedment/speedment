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
import java.util.HashMap;
import java.util.Map;

final class TimeToLongMapperTest extends AbstractTypeMapperTest<Time, Long, TimeToLongMapper> {

    TimeToLongMapperTest() {
        super(
            Time.class,
            Long.class,
            TypeMapper.Category.COMPARABLE,
            TypeMapper.Ordering.RETAIN,
            TimeToLongMapper::new
        );
    }

    @Override
    protected Map<Time, Long> testVector() {
        Map<Time, Long> map = new HashMap<>();
        map.put(new Time(0L), 0L);
        map.put(new Time(788918400000L), 788918400000L);
        map.put(null, null);
        return map;
    }
}