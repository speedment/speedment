/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

/**
 * Specialized Type Mapper that stores dates on the form {@code YYYYMMDD} as
 * a single {@code short} value (the number of days since the beginning of epoch
 * time).
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class DateIntToShortMapper
implements TypeMapper<Integer, Short> {

    private final static int DAY_MASK   = 0b0000_0000_0001_1111;
    private final static int MONTH_MASK = 0b0000_0001_1110_0000;
    private final static int YEAR_MASK  = 0b1111_1110_0000_0000;
    
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
        return (short) (
            ((date - (date / 100) * 100) & DAY_MASK) |
            ((((date / 100) - ((date / 10_000) * 100)) << 5) & MONTH_MASK) |
            (((date / 10_000 - 1970) << 9) & YEAR_MASK)
        );
    }

    @Override
    public Integer toDatabaseType(Short encoded) {
        if (encoded == null) return null;

        final int day   =   encoded & DAY_MASK;
        final int month =  (encoded & MONTH_MASK) >>> 5;
        final int year  = ((encoded & YEAR_MASK ) >>> 9) + 1970;
        
        return 10_000 * year + 100 * month + day;
    }
}