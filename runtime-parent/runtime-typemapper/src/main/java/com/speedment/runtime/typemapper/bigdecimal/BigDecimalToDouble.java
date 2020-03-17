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
package com.speedment.runtime.typemapper.bigdecimal;


import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 *
 * @author Per Minborg
 */

public final class BigDecimalToDouble implements TypeMapper<BigDecimal, Double> {

    @Override
    public String getLabel() {
        return "BigDecimal to Double";
    }
    
    @Override
    public Type getJavaType(Column column) {
        return Double.class;
    }

    @Override
    public Double toJavaType(Column column, Class<?> entityType, BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }

    @Override
    public BigDecimal toDatabaseType(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }
    
}
