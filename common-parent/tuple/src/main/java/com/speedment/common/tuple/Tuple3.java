/**
 * 
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.tuple;

import com.speedment.common.tuple.getter.TupleGetter0;
import com.speedment.common.tuple.getter.TupleGetter1;
import com.speedment.common.tuple.getter.TupleGetter2;

/**
 * This interface defines a generic Tuple of any order that can hold null
 * values. A Tuple is type safe, immutable and thread safe. For pure non-null
 * value elements see {@link Tuple}
 * This {@link Tuple } has a degree of 3
 * 
 * @param <T0> type of element 0
 * @param <T1> type of element 1
 * @param <T2> type of element 2
 * 
 * @author Per Minborg
 */
public interface Tuple3<T0, T1, T2> extends Tuple {
    
    T0 get0();
    
    T1 get1();
    
    T2 get2();
    
    @Override
    default int degree() {
        return 3;
    }
    
    default Object get(int index) {
        switch (index) {
            case 0 : return get0();
            case 1 : return get1();
            case 2 : return get2();
            default : throw new IllegalArgumentException(String.format("Index %d is outside bounds of tuple of degree %s", index, degree()
            ));
        }
    }

    static <T0, T1, T2> TupleGetter0<Tuple3<T0, T1, T2>, T0> getter0() {
        return Tuple3::get0;
    }

    static <T0, T1, T2> TupleGetter1<Tuple3<T0, T1, T2>, T1> getter1() {
        return Tuple3::get1;
    }

    static <T0, T1, T2> TupleGetter2<Tuple3<T0, T1, T2>, T2> getter2() {
        return Tuple3::get2;
    }
}