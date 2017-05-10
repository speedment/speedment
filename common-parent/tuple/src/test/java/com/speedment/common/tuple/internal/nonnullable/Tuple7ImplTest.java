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
package com.speedment.common.tuple.internal.nonnullable;

import org.junit.Test;
import static org.junit.Assert.*;

public final class Tuple7ImplTest<T0, T1, T2, T3, T4, T5, T6> extends AbstractTupleImplTest<Tuple7Impl<Integer, Integer, Integer, Integer, Integer, Integer, Integer>> {
    
    public Tuple7ImplTest() {
        super(() -> new Tuple7Impl<>(0, 1, 2, 3, 4, 5, 6), 7);
    }
    
    @Test
    public void get0Test() {
        assertEquals(0, (int) instance.get0());
    }
    
    @Test
    public void get1Test() {
        assertEquals(1, (int) instance.get1());
    }
    
    @Test
    public void get2Test() {
        assertEquals(2, (int) instance.get2());
    }
    
    @Test
    public void get3Test() {
        assertEquals(3, (int) instance.get3());
    }
    
    @Test
    public void get4Test() {
        assertEquals(4, (int) instance.get4());
    }
    
    @Test
    public void get5Test() {
        assertEquals(5, (int) instance.get5());
    }
    
    @Test
    public void get6Test() {
        assertEquals(6, (int) instance.get6());
    }
}