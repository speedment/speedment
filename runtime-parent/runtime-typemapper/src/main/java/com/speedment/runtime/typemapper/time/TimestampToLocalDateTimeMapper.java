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
import java.time.LocalDateTime;

/**
 * A mapping from SQL's Timestamp to Java's LocalDateTime.
 * <p>
 * The mapping is naive, and will not include or consider timezone or the similar.
 * Instead, the mapping will be direct: what is written in the database will
 * be directly mapped into Java.
 * <p>
 * Example: <br>
 * In database: '2016-08-01 09:39:45'<br>
 * In Java: 2016-08-01T09:39:45
 *
 * @author Simon Jonasson
 */
public class TimestampToLocalDateTimeMapper implements TypeMapper<Timestamp, LocalDateTime>{

    @Override
    public String getLabel() {
        return "Timestamp to LocalDateTime";
    }

    @Override
    public Type getJavaType(Column column) {
        return LocalDateTime.class;
    }

    @Override
    public LocalDateTime toJavaType(Column column, Class<?> entityType, Timestamp value) {
        return value == null ? null : value.toLocalDateTime();
    }

    @Override
    public Timestamp toDatabaseType(LocalDateTime value) {
        return value == null ? null : Timestamp.valueOf(value);
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }
    
}
