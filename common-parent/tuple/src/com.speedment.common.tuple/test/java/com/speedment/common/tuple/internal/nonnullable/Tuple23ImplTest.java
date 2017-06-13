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

public final class Tuple23ImplTest<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> extends AbstractTupleImplTest<Tuple23Impl<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> {
    
    public Tuple23ImplTest() {
        super(() -> new Tuple23Impl<>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22), 23);
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
    
    @Test
    public void get7Test() {
        assertEquals(7, (int) instance.get7());
    }
    
    @Test
    public void get8Test() {
        assertEquals(8, (int) instance.get8());
    }
    
    @Test
    public void get9Test() {
        assertEquals(9, (int) instance.get9());
    }
    
    @Test
    public void get10Test() {
        assertEquals(10, (int) instance.get10());
    }
    
    @Test
    public void get11Test() {
        assertEquals(11, (int) instance.get11());
    }
    
    @Test
    public void get12Test() {
        assertEquals(12, (int) instance.get12());
    }
    
    @Test
    public void get13Test() {
        assertEquals(13, (int) instance.get13());
    }
    
    @Test
    public void get14Test() {
        assertEquals(14, (int) instance.get14());
    }
    
    @Test
    public void get15Test() {
        assertEquals(15, (int) instance.get15());
    }
    
    @Test
    public void get16Test() {
        assertEquals(16, (int) instance.get16());
    }
    
    @Test
    public void get17Test() {
        assertEquals(17, (int) instance.get17());
    }
    
    @Test
    public void get18Test() {
        assertEquals(18, (int) instance.get18());
    }
    
    @Test
    public void get19Test() {
        assertEquals(19, (int) instance.get19());
    }
    
    @Test
    public void get20Test() {
        assertEquals(20, (int) instance.get20());
    }
    
    @Test
    public void get21Test() {
        assertEquals(21, (int) instance.get21());
    }
    
    @Test
    public void get22Test() {
        assertEquals(22, (int) instance.get22());
    }
}