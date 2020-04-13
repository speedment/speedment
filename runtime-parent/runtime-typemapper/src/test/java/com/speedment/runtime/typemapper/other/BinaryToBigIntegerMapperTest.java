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
package com.speedment.runtime.typemapper.other;

import com.speedment.runtime.typemapper.AbstractTypeMapperTest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static com.speedment.runtime.typemapper.TypeMapper.Category;

final class BinaryToBigIntegerMapperTest extends AbstractTypeMapperTest<Object, BigInteger, BinaryToBigIntegerMapper> {

    BinaryToBigIntegerMapperTest() {
        super(
            Object.class,
            BigInteger.class,
            Category.COMPARABLE,
            BinaryToBigIntegerMapper::new);
    }

    @Override
    protected Map<Object, BigInteger> testVector() {
        Map<Object, BigInteger> map = new HashMap<>();
        map.put(null, null);
        return map;
    }
}