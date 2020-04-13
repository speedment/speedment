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
import java.sql.Date;
import java.time.LocalDate;

/**
 * A mapping from SQL's Date to Java's LocalDate.
 * <p>
 * The mapping is naive, and will not include or consider timezone or the similar.
 * Instead, the mapping will be direct: what is written in the database will
 * be directly mapped into Java.
 * <p>
 * Example: <br>
 * In database: 2016-08-01<br>
 * In Java: 2016-08-01
 *
 * @author Simon Jonasson
 */
public class DateToLocalDateMapper implements TypeMapper<Date, LocalDate>{

    @Override
    public String getLabel() {
        return "Date to LocalDate";
    }

    @Override
    public Type getJavaType(Column column) {
        return LocalDate.class;
    }

    @Override
    public LocalDate toJavaType(Column column, Class<?> entityType, Date value) {
        return value.toLocalDate();
    }

    @Override
    public Date toDatabaseType(LocalDate value) {
        return Date.valueOf(value);
    }
    
    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }
    
}
