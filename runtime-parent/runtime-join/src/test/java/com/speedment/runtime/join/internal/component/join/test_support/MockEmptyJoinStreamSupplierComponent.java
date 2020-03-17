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
package com.speedment.runtime.join.internal.component.join.test_support;

import com.speedment.common.function.*;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.JoinImpl;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E0;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E1;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E2;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E3;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E4;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E5;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E6;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E7;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E8;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E9;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class MockEmptyJoinStreamSupplierComponent implements JoinStreamSupplierComponent {

    private List<Stage<?>> stages;
    private Object constructor;
    private TableIdentifier<E0> t0;
    private TableIdentifier<E1> t1;
    private TableIdentifier<E2> t2;
    private TableIdentifier<E3> t3;
    private TableIdentifier<E4> t4;
    private TableIdentifier<E5> t5;
    private TableIdentifier<E6> t6;
    private TableIdentifier<E7> t7;
    private TableIdentifier<E8> t8;
    private TableIdentifier<E9> t9;

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T> Join<T> createJoin(List<Stage<?>> stages, BiFunction<T0, T1, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1) {
        set(
            stages,
            constructor,
            (TableIdentifier<E0>) t0,
            (TableIdentifier<E1>) t1,
            (TableIdentifier<E2>) null,
            (TableIdentifier<E3>) null,
            (TableIdentifier<E4>) null,
            (TableIdentifier<E5>) null,
            (TableIdentifier<E6>) null,
            (TableIdentifier<E7>) null,
            (TableIdentifier<E8>) null,
            (TableIdentifier<E9>) null
        );
        return empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T> Join<T> createJoin(List<Stage<?>> stages, TriFunction<T0, T1, T2, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2) {
        set(
            stages,
            constructor,
            (TableIdentifier<E0>) t0,
            (TableIdentifier<E1>) t1,
            (TableIdentifier<E2>) t2,
            (TableIdentifier<E3>) null,
            (TableIdentifier<E4>) null,
            (TableIdentifier<E5>) null,
            (TableIdentifier<E6>) null,
            (TableIdentifier<E7>) null,
            (TableIdentifier<E8>) null,
            (TableIdentifier<E9>) null
        );

        return empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T> Join<T> createJoin(List<Stage<?>> stages, QuadFunction<T0, T1, T2, T3, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3) {
        set(
            stages,
            constructor,
            (TableIdentifier<E0>) t0,
            (TableIdentifier<E1>) t1,
            (TableIdentifier<E2>) t2,
            (TableIdentifier<E3>) t3,
            (TableIdentifier<E4>) null,
            (TableIdentifier<E5>) null,
            (TableIdentifier<E6>) null,
            (TableIdentifier<E7>) null,
            (TableIdentifier<E8>) null,
            (TableIdentifier<E9>) null
        );

        return empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T> Join<T> createJoin(List<Stage<?>> stages, Function5<T0, T1, T2, T3, T4, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4) {
        set(
            stages,
            constructor,
            (TableIdentifier<E0>) t0,
            (TableIdentifier<E1>) t1,
            (TableIdentifier<E2>) t2,
            (TableIdentifier<E3>) t3,
            (TableIdentifier<E4>) t4,
            (TableIdentifier<E5>) null,
            (TableIdentifier<E6>) null,
            (TableIdentifier<E7>) null,
            (TableIdentifier<E8>) null,
            (TableIdentifier<E9>) null
        );

        return empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T5, T> Join<T> createJoin(List<Stage<?>> stages, Function6<T0, T1, T2, T3, T4, T5, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5) {
        set(
            stages,
            constructor,
            (TableIdentifier<E0>) t0,
            (TableIdentifier<E1>) t1,
            (TableIdentifier<E2>) t2,
            (TableIdentifier<E3>) t3,
            (TableIdentifier<E4>) t4,
            (TableIdentifier<E5>) t5,
            (TableIdentifier<E6>) null,
            (TableIdentifier<E7>) null,
            (TableIdentifier<E8>) null,
            (TableIdentifier<E9>) null
        );
        return empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T5, T6, T> Join<T> createJoin(List<Stage<?>> stages, Function7<T0, T1, T2, T3, T4, T5, T6, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6) {
        set(
            stages,
            constructor,
            (TableIdentifier<E0>) t0,
            (TableIdentifier<E1>) t1,
            (TableIdentifier<E2>) t2,
            (TableIdentifier<E3>) t3,
            (TableIdentifier<E4>) t4,
            (TableIdentifier<E5>) t5,
            (TableIdentifier<E6>) t6,
            (TableIdentifier<E7>) null,
            (TableIdentifier<E8>) null,
            (TableIdentifier<E9>) null
        );
        return empty();
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T5, T6, T7, T> Join<T> createJoin(List<Stage<?>> stages, Function8<T0, T1, T2, T3, T4, T5, T6, T7, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6, TableIdentifier<T7> t7) {
        set(
                stages,
                constructor,
                (TableIdentifier<E0>) t0,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) t3,
                (TableIdentifier<E4>) t4,
                (TableIdentifier<E5>) t5,
                (TableIdentifier<E6>) t6,
                (TableIdentifier<E7>) t7,
                (TableIdentifier<E8>) null,
                (TableIdentifier<E9>) null
        );
        return empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T5, T6, T7, T8, T> Join<T> createJoin(List<Stage<?>> stages, Function9<T0, T1, T2, T3, T4, T5, T6, T7, T8, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6, TableIdentifier<T7> t7, TableIdentifier<T8> t8) {
        set(
                stages,
                constructor,
                (TableIdentifier<E0>) t0,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) t3,
                (TableIdentifier<E4>) t4,
                (TableIdentifier<E5>) t5,
                (TableIdentifier<E6>) t6,
                (TableIdentifier<E7>) t7,
                (TableIdentifier<E8>) t8,
                (TableIdentifier<E9>) null
        );
        return empty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T> Join<T> createJoin(List<Stage<?>> stages, Function10<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T> constructor, TableIdentifier<T0> t0, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6, TableIdentifier<T7> t7, TableIdentifier<T8> t8, TableIdentifier<T9> t9) {
        set(
                stages,
                constructor,
                (TableIdentifier<E0>) t0,
                (TableIdentifier<E1>) t1,
                (TableIdentifier<E2>) t2,
                (TableIdentifier<E3>) t3,
                (TableIdentifier<E4>) t4,
                (TableIdentifier<E5>) t5,
                (TableIdentifier<E6>) t6,
                (TableIdentifier<E7>) t7,
                (TableIdentifier<E8>) t8,
                (TableIdentifier<E9>) t9
        );
        return empty();
    }

    public List<Stage<?>> stages() {
        return stages;
    }

    public Object constructor() {
        return constructor;
    }

    public TableIdentifier<E0> t0() {
        return t0;
    }

    public TableIdentifier<E1> t1() {
        return t1;
    }

    public TableIdentifier<E2> t2() {
        return t2;
    }

    public TableIdentifier<E3> t3() {
        return t3;
    }

    public TableIdentifier<E4> t4() {
        return t4;
    }

    public TableIdentifier<E5> t5() {
        return t5;
    }

    public TableIdentifier<E6> t6() {
        return t6;
    }

    public TableIdentifier<E7> t7() {
        return t7;
    }

    public TableIdentifier<E8> t8() {
        return t8;
    }

    public TableIdentifier<E9> t9() {
        return t9;
    }

    public TableIdentifier<?> t(int index) {
        switch (index) {
            case 0:
                return t0();
            case 1:
                return t1();
            case 2:
                return t2();
            case 3:
                return t3();
            case 4:
                return t4();
            case 5:
                return t5();
            case 6:
                return t6();
            case 7:
                return t7();
            case 8:
                return t8();
            case 9:
                return t9();
        }
        throw new IndexOutOfBoundsException(Integer.toString(index));
    }

    private void set(
            List<Stage<?>> stages,
            Object constructor,
            TableIdentifier<E0> t0,
            TableIdentifier<E1> t1,
            TableIdentifier<E2> t2,
            TableIdentifier<E3> t3,
            TableIdentifier<E4> t4,
            TableIdentifier<E5> t5,
            TableIdentifier<E6> t6,
            TableIdentifier<E7> t7,
            TableIdentifier<E8> t8,
            TableIdentifier<E9> t9
    ) {
        this.stages = stages;
        this.constructor = constructor;
        this.t0 = t0;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
    }

    private <T> Join<T> empty() {
        return new JoinImpl<>(Stream::empty);
    }
}
