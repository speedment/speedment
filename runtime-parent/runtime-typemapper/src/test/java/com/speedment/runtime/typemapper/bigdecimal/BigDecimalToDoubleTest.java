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

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;

final class BigDecimalToDoubleTest extends AbstractTypeMapperTest<BigDecimal, Double, BigDecimalToDouble> {

    BigDecimalToDoubleTest() {
        super(
            BigDecimal.class,
            Double.class,
            Category.COMPARABLE,
            Ordering.RETAIN,
            BigDecimalToDouble::new
        );
    }

    @Override
    protected Map<BigDecimal, Double> testVector() {
        Map<BigDecimal, Double> map = new HashMap<>();
        map.put(BigDecimal.valueOf(748147123.0), 748147123.0);
        map.put(BigDecimal.valueOf(Double.MAX_VALUE), Double.MAX_VALUE);
        map.put(BigDecimal.valueOf(Double.MIN_VALUE), Double.MIN_VALUE);
        map.put(BigDecimal.valueOf(0.0), 0.0);
        map.put(null, null);
        return map;
    }
}