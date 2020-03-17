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

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class TimeToLocalTimeMapperTest extends AbstractTypeMapperTest<Time, LocalTime, TimeToLocalTimeMapper> {


    TimeToLocalTimeMapperTest() {
        super(
            Time.class,
            LocalTime.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            TimeToLocalTimeMapper::new
        );
    }

    @Override
    protected Map<Time, LocalTime> testVector() {
        Map<Time, LocalTime> map = new HashMap<>();
        return map;
    }
}