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
package com.speedment.runtime.typemapper.string;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Maps between a large real number stored as a {@code String} into a java
 * {@link BigDecimal}.
 *
 * @author Emil Forslund
 * @since  3.0.23
 */
public final class StringToBigDecimalMapper
implements TypeMapper<String, BigDecimal> {

    @Override
    public String getLabel() {
        return "String to BigDecimal Mapper";
    }

    @Override
    public Type getJavaType(Column column) {
        return BigDecimal.class;
    }

    @Override
    public Category getJavaTypeCategory(Column column) {
        return Category.COMPARABLE;
    }

    @Override
    public BigDecimal toJavaType(Column column, Class<?> entityType, String value) {
        return value == null ? null : new BigDecimal(value);
    }

    @Override
    public String toDatabaseType(BigDecimal value) {
        return value == null ? null : value.toString();
    }

    @Override
    public Ordering getOrdering() {
        // Alphabetic order and numeric order might differ
        return Ordering.UNSPECIFIED;
    }
}