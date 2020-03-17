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
package com.speedment.common.tuple.internal.nonnullable.mapper;

import com.speedment.common.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class Tuple0MapperImplTest {
    
    private final @SuppressWarnings(value = "unchecked") Tuple0MapperImpl<Integer> instance = (Tuple0MapperImpl) Tuple0MapperImpl.EMPTY_MAPPER;;
    
    @Test
    void degree() {
        assertEquals(0, instance.degree());
    }
    
    @Test
    void apply() {
        assertEquals(Tuples.of(), instance.apply(0));
    }
}