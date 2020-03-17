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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Specialized Type Mapper that stores dates on the form {@code YYYYMMDD} as
 * a single {@code short} value (the number of days since the beginning of epoch
 * time).
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class DateIntToShortMapper implements TypeMapper<Integer, Short> {
    
    @Override
    public String getLabel() {
        return "Date Integer to Short";
    }

    @Override
    public Type getJavaType(Column column) {
        return Short.class;
    }

    @Override
    public Short toJavaType(Column column, Class<?> entityType, Integer date) {
        if (date == null) return null;

        final int day = date % 100;
        final int month = (date % 10_000 - day) / 100;
        final int year = date / 10_000;

        final LocalDate localDate = LocalDate.of(year, month, day);
        return  (short) ChronoUnit.DAYS.between(localDate, LocalDate.of(1970, 1,1));
    }

    @Override
    public Integer toDatabaseType(Short encoded) {
        if (encoded == null) return null;

        final LocalDate date = LocalDate.ofEpochDay(encoded);
        return date.getYear() * 10_000 + date.getMonthValue() * 100 + date.getDayOfMonth();
    }
}