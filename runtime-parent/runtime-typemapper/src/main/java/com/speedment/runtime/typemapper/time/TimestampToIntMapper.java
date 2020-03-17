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


import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 *
 * @author  Emil Forslund
 */

public final class TimestampToIntMapper implements TypeMapper<Timestamp, Integer> {

    @Override
    public String getLabel() {
        return "Timestamp to Integer";
    }
    
    @Override
    public Type getJavaType(Column column) {
        return Integer.class;
    }

    @Override
    public Integer toJavaType(Column column, Class<?> entityType, Timestamp value) {
        return value == null ? null : (int) (value.getTime() / 1000);
    }

    @Override
    public Timestamp toDatabaseType(Integer value) {
        return value == null ? null : new Timestamp(value * 1000L);
    }
    
    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }
}