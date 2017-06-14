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
package com.speedment.common.tuple.nullable;

import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TupleOfNullables;
import java.util.Optional;

/**
 * This interface defines a generic Tuple of degree 16 that can hold non-null
 * values. A Tuple is type safe, immutable and thread safe. For Tuples that can
 * hold
 * null elements see {@link TupleOfNullables}
 * 
 * This {@link Tuple } has a degree of 16
 * 
 * @param <T0>  type of element 0
 * @param <T1>  type of element 1
 * @param <T2>  type of element 2
 * @param <T3>  type of element 3
 * @param <T4>  type of element 4
 * @param <T5>  type of element 5
 * @param <T6>  type of element 6
 * @param <T7>  type of element 7
 * @param <T8>  type of element 8
 * @param <T9>  type of element 9
 * @param <T10> type of element 10
 * @param <T11> type of element 11
 * @param <T12> type of element 12
 * @param <T13> type of element 13
 * @param <T14> type of element 14
 * @param <T15> type of element 15
 * 
 * @author Per Minborg
 */
public interface Tuple16OfNullables<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> extends TupleOfNullables {
    
    Optional<T0> get0();
    
    Optional<T1> get1();
    
    Optional<T2> get2();
    
    Optional<T3> get3();
    
    Optional<T4> get4();
    
    Optional<T5> get5();
    
    Optional<T6> get6();
    
    Optional<T7> get7();
    
    Optional<T8> get8();
    
    Optional<T9> get9();
    
    Optional<T10> get10();
    
    Optional<T11> get11();
    
    Optional<T12> get12();
    
    Optional<T13> get13();
    
    Optional<T14> get14();
    
    Optional<T15> get15();
    
    @Override
    default int degree() {
        return 16;
    }
    
    @SuppressWarnings("unchecked")
    default Optional<Object> get(int index) {
        switch (index) {
            case 0 : return (Optional<Object>)get0();
            case 1 : return (Optional<Object>)get1();
            case 2 : return (Optional<Object>)get2();
            case 3 : return (Optional<Object>)get3();
            case 4 : return (Optional<Object>)get4();
            case 5 : return (Optional<Object>)get5();
            case 6 : return (Optional<Object>)get6();
            case 7 : return (Optional<Object>)get7();
            case 8 : return (Optional<Object>)get8();
            case 9 : return (Optional<Object>)get9();
            case 10 : return (Optional<Object>)get10();
            case 11 : return (Optional<Object>)get11();
            case 12 : return (Optional<Object>)get12();
            case 13 : return (Optional<Object>)get13();
            case 14 : return (Optional<Object>)get14();
            case 15 : return (Optional<Object>)get15();
            default : throw new IllegalArgumentException(String.format("Index %d is outside bounds of tuple of degree %s", index, degree()
            ));
        }
    }
}