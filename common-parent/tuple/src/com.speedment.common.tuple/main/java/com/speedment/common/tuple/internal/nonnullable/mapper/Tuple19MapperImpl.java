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
package com.speedment.common.tuple.internal.nonnullable.mapper;

import com.speedment.common.tuple.Tuple19;
import com.speedment.common.tuple.TupleMapper;
import com.speedment.common.tuple.Tuples;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;

/**
 * An implementation class of a {@link TupleMapper } of degree 19
 * 
 * @param <T>   Type of the original object for the mapper to use when creating
 *              a {@code Tuple }
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
 * @param <T16> type of element 16
 * @param <T17> type of element 17
 * @param <T18> type of element 18
 * 
 * @author Per Minborg
 */
public final class Tuple19MapperImpl<T, T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> implements TupleMapper<T, Tuple19<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> {
    
    private final Function<T, T0> m0;
    private final Function<T, T1> m1;
    private final Function<T, T2> m2;
    private final Function<T, T3> m3;
    private final Function<T, T4> m4;
    private final Function<T, T5> m5;
    private final Function<T, T6> m6;
    private final Function<T, T7> m7;
    private final Function<T, T8> m8;
    private final Function<T, T9> m9;
    private final Function<T, T10> m10;
    private final Function<T, T11> m11;
    private final Function<T, T12> m12;
    private final Function<T, T13> m13;
    private final Function<T, T14> m14;
    private final Function<T, T15> m15;
    private final Function<T, T16> m16;
    private final Function<T, T17> m17;
    private final Function<T, T18> m18;
    
    /**
     * Constructs a {@link TupleMapper } that can create {@link Tuple19 }.
     * 
     * @param m0  mapper to apply for element 0
     * @param m1  mapper to apply for element 1
     * @param m2  mapper to apply for element 2
     * @param m3  mapper to apply for element 3
     * @param m4  mapper to apply for element 4
     * @param m5  mapper to apply for element 5
     * @param m6  mapper to apply for element 6
     * @param m7  mapper to apply for element 7
     * @param m8  mapper to apply for element 8
     * @param m9  mapper to apply for element 9
     * @param m10 mapper to apply for element 10
     * @param m11 mapper to apply for element 11
     * @param m12 mapper to apply for element 12
     * @param m13 mapper to apply for element 13
     * @param m14 mapper to apply for element 14
     * @param m15 mapper to apply for element 15
     * @param m16 mapper to apply for element 16
     * @param m17 mapper to apply for element 17
     * @param m18 mapper to apply for element 18
     */
    public Tuple19MapperImpl(
            Function<T, T0> m0,
            Function<T, T1> m1,
            Function<T, T2> m2,
            Function<T, T3> m3,
            Function<T, T4> m4,
            Function<T, T5> m5,
            Function<T, T6> m6,
            Function<T, T7> m7,
            Function<T, T8> m8,
            Function<T, T9> m9,
            Function<T, T10> m10,
            Function<T, T11> m11,
            Function<T, T12> m12,
            Function<T, T13> m13,
            Function<T, T14> m14,
            Function<T, T15> m15,
            Function<T, T16> m16,
            Function<T, T17> m17,
            Function<T, T18> m18) {
        this.m0	=	requireNonNull(m0);
        this.m1	=	requireNonNull(m1);
        this.m2	=	requireNonNull(m2);
        this.m3	=	requireNonNull(m3);
        this.m4	=	requireNonNull(m4);
        this.m5	=	requireNonNull(m5);
        this.m6	=	requireNonNull(m6);
        this.m7	=	requireNonNull(m7);
        this.m8	=	requireNonNull(m8);
        this.m9	=	requireNonNull(m9);
        this.m10	=	requireNonNull(m10);
        this.m11	=	requireNonNull(m11);
        this.m12	=	requireNonNull(m12);
        this.m13	=	requireNonNull(m13);
        this.m14	=	requireNonNull(m14);
        this.m15	=	requireNonNull(m15);
        this.m16	=	requireNonNull(m16);
        this.m17	=	requireNonNull(m17);
        this.m18	=	requireNonNull(m18);
    }
    
    @Override
    public Tuple19<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> apply(T t) {
        return Tuples.of(
            m0.apply(t),
            m1.apply(t),
            m2.apply(t),
            m3.apply(t),
            m4.apply(t),
            m5.apply(t),
            m6.apply(t),
            m7.apply(t),
            m8.apply(t),
            m9.apply(t),
            m10.apply(t),
            m11.apply(t),
            m12.apply(t),
            m13.apply(t),
            m14.apply(t),
            m15.apply(t),
            m16.apply(t),
            m17.apply(t),
            m18.apply(t)
        );
    }
    
    @Override
    public int degree() {
        return 19;
    }
    
    @Override
    public Function<T, ?> get(int index) {
        switch(index){
            case 0	: return	get0();
            case 1	: return	get1();
            case 2	: return	get2();
            case 3	: return	get3();
            case 4	: return	get4();
            case 5	: return	get5();
            case 6	: return	get6();
            case 7	: return	get7();
            case 8	: return	get8();
            case 9	: return	get9();
            case 10	: return	get10();
            case 11	: return	get11();
            case 12	: return	get12();
            case 13	: return	get13();
            case 14	: return	get14();
            case 15	: return	get15();
            case 16	: return	get16();
            case 17	: return	get17();
            case 18	: return	get18();
            default : throw new IndexOutOfBoundsException();
        }
    }
    
    public Function<T, T0> get0() {
        return m0;
    }
    
    public Function<T, T1> get1() {
        return m1;
    }
    
    public Function<T, T2> get2() {
        return m2;
    }
    
    public Function<T, T3> get3() {
        return m3;
    }
    
    public Function<T, T4> get4() {
        return m4;
    }
    
    public Function<T, T5> get5() {
        return m5;
    }
    
    public Function<T, T6> get6() {
        return m6;
    }
    
    public Function<T, T7> get7() {
        return m7;
    }
    
    public Function<T, T8> get8() {
        return m8;
    }
    
    public Function<T, T9> get9() {
        return m9;
    }
    
    public Function<T, T10> get10() {
        return m10;
    }
    
    public Function<T, T11> get11() {
        return m11;
    }
    
    public Function<T, T12> get12() {
        return m12;
    }
    
    public Function<T, T13> get13() {
        return m13;
    }
    
    public Function<T, T14> get14() {
        return m14;
    }
    
    public Function<T, T15> get15() {
        return m15;
    }
    
    public Function<T, T16> get16() {
        return m16;
    }
    
    public Function<T, T17> get17() {
        return m17;
    }
    
    public Function<T, T18> get18() {
        return m18;
    }
}