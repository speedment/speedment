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
package com.speedment.common.tuple.nullable;

import com.speedment.common.tuple.Tuple11;
import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.TupleOfNullables;
import com.speedment.common.tuple.getter.TupleGetter0;
import com.speedment.common.tuple.getter.TupleGetter10;
import com.speedment.common.tuple.getter.TupleGetter1;
import com.speedment.common.tuple.getter.TupleGetter2;
import com.speedment.common.tuple.getter.TupleGetter3;
import com.speedment.common.tuple.getter.TupleGetter4;
import com.speedment.common.tuple.getter.TupleGetter5;
import com.speedment.common.tuple.getter.TupleGetter6;
import com.speedment.common.tuple.getter.TupleGetter7;
import com.speedment.common.tuple.getter.TupleGetter8;
import com.speedment.common.tuple.getter.TupleGetter9;
import com.speedment.common.tuple.getter.TupleGetter;
import java.util.Optional;

/**
 * This interface defines a generic Tuple of degree 11 that can hold non-null
 * values. A Tuple is type safe, immutable and thread safe. For Tuples that can
 * hold
 * null elements see {@link TupleOfNullables}
 * 
 * This {@link Tuple } has a degree of 11
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
 * 
 * @author Per Minborg
 */
public interface Tuple11OfNullables<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends TupleOfNullables {
    
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
    
    @Override
    default int degree() {
        return 11;
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
            default : throw new IllegalArgumentException(String.format("Index %d is outside bounds of tuple of degree %s", index, degree()
            ));
        }
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 0th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter0<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T0> getter0() {
        return Tuple11::get0;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 1st element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter1<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T1> getter1() {
        return Tuple11::get1;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 2nd element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter2<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T2> getter2() {
        return Tuple11::get2;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 3rd element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter3<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T3> getter3() {
        return Tuple11::get3;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 4th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter4<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T4> getter4() {
        return Tuple11::get4;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 5th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter5<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T5> getter5() {
        return Tuple11::get5;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 6th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter6<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T6> getter6() {
        return Tuple11::get6;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 7th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter7<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T7> getter7() {
        return Tuple11::get7;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 8th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter8<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T8> getter8() {
        return Tuple11::get8;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 9th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter9<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T9> getter9() {
        return Tuple11::get9;
    }
    
    /**
     * Returns a {@link TupleGetter getter} for the 10th element in the {@code
     * Tuple}.
     */
    static <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> TupleGetter10<Tuple11<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, T10> getter10() {
        return Tuple11::get10;
    }
}