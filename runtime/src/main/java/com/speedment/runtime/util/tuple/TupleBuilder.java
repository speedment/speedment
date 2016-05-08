/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.util.tuple;

/**
 *
 * @author Per Minborg
 */
public class TupleBuilder {

    private Tuple current = Tuples.of();

    private TupleBuilder() {
    }

    public static Build0 builder() {
        return new TupleBuilder().new Build0();
    }

    public class Build0 extends BaseBuilder<Tuple0> {

        public <T0> Build1<T0> add(T0 e0) {
            current = Tuples.of(e0);
            return new Build1<>();
        }

    }

    public class Build1<T0> extends BaseBuilder<Tuple1<T0>> {

        public <T1> Build2<T0, T1> add(T1 e1) {
            current = Tuples.of(current.get(0), e1);
            return new Build2<>();
        }

    }

    public class Build2<T0, T1> extends BaseBuilder<Tuple2<T0, T1>> {

        public <T2> Build3<T0, T1, T2> add(T2 e2) {
            current = Tuples.of(current.get(0), current.get(1), e2);
            return new Build3<>();
        }

    }

    public class Build3<T0, T1, T2> extends BaseBuilder<Tuple3<T0, T1, T2>> {

        public <T3> Build4<T0, T1, T2, T3> add(T3 e3) {
            current = Tuples.of(current.get(0), current.get(1), current.get(2), e3);
            return new Build4<>();
        }
    }

    public class Build4<T0, T1, T2, T3> extends BaseBuilder<Tuple4<T0, T1, T2, T3>> {

        public <T4> Build5<T0, T1, T2, T3, T4> add(T4 e4) {
            current = Tuples.of(current.get(0), current.get(1), current.get(2), current.get(3), e4);
            return new Build5<>();
        }

    }

    public class Build5<T0, T1, T2, T3, T4> extends BaseBuilder<Tuple5<T0, T1, T2, T3, T4>> {

        public <T5> Build6<T0, T1, T2, T3, T4, T5> add(T5 e5) {
            current = Tuples.of(
                    current.get(0),
                    current.get(1),
                    current.get(2),
                    current.get(3),
                    current.get(4),
                    e5);
            return new Build6<>();
        }

    }

    public class Build6<T0, T1, T2, T3, T4, T5> extends BaseBuilder<Tuple5<T0, T1, T2, T3, T4>> {

    }

    protected class BaseBuilder<T> {

        @SuppressWarnings("unchecked")
        public T build() {
            return (T) current;
        }

    }

}
