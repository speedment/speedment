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
package com.speedment.common.tuple;

import com.speedment.common.tuple.getter.TupleGetter;
import com.speedment.common.tuple.nullable.Tuple10OfNullables;
import com.speedment.common.tuple.nullable.Tuple11OfNullables;
import com.speedment.common.tuple.nullable.Tuple12OfNullables;
import com.speedment.common.tuple.nullable.Tuple13OfNullables;
import com.speedment.common.tuple.nullable.Tuple14OfNullables;
import com.speedment.common.tuple.nullable.Tuple15OfNullables;
import com.speedment.common.tuple.nullable.Tuple16OfNullables;
import com.speedment.common.tuple.nullable.Tuple17OfNullables;
import com.speedment.common.tuple.nullable.Tuple18OfNullables;
import com.speedment.common.tuple.nullable.Tuple19OfNullables;
import com.speedment.common.tuple.nullable.Tuple1OfNullables;
import com.speedment.common.tuple.nullable.Tuple20OfNullables;
import com.speedment.common.tuple.nullable.Tuple21OfNullables;
import com.speedment.common.tuple.nullable.Tuple22OfNullables;
import com.speedment.common.tuple.nullable.Tuple23OfNullables;
import com.speedment.common.tuple.nullable.Tuple2OfNullables;
import com.speedment.common.tuple.nullable.Tuple3OfNullables;
import com.speedment.common.tuple.nullable.Tuple4OfNullables;
import com.speedment.common.tuple.nullable.Tuple5OfNullables;
import com.speedment.common.tuple.nullable.Tuple6OfNullables;
import com.speedment.common.tuple.nullable.Tuple7OfNullables;
import com.speedment.common.tuple.nullable.Tuple8OfNullables;
import com.speedment.common.tuple.nullable.Tuple9OfNullables;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

final class TupleOfNullablesTest {
    
    @Test
    void tuple1() {
        final Tuple1OfNullables<Integer> tuple = TuplesOfNullables.ofNullables(0);
        tupleTest(tuple);
        final Tuple1OfNullables<Integer> defaultTuple = new Tuple1OfNullables<Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple2() {
        final Tuple2OfNullables<Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1);
        tupleTest(tuple);
        final Tuple2OfNullables<Integer, Integer> defaultTuple = new Tuple2OfNullables<Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple3() {
        final Tuple3OfNullables<Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2);
        tupleTest(tuple);
        final Tuple3OfNullables<Integer, Integer, Integer> defaultTuple = new Tuple3OfNullables<Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple4() {
        final Tuple4OfNullables<Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3);
        tupleTest(tuple);
        final Tuple4OfNullables<Integer, Integer, Integer, Integer> defaultTuple = new Tuple4OfNullables<Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple5() {
        final Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4);
        tupleTest(tuple);
        final Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple6() {
        final Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5);
        tupleTest(tuple);
        final Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple7() {
        final Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6);
        tupleTest(tuple);
        final Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple8() {
        final Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7);
        tupleTest(tuple);
        final Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple9() {
        final Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8);
        tupleTest(tuple);
        final Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple10() {
        final Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        tupleTest(tuple);
        final Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple11() {
        final Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        tupleTest(tuple);
        final Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple12() {
        final Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        tupleTest(tuple);
        final Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple13() {
        final Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        tupleTest(tuple);
        final Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple14() {
        final Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        tupleTest(tuple);
        final Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple15() {
        final Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        tupleTest(tuple);
        final Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple16() {
        final Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        tupleTest(tuple);
        final Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple17() {
        final Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        tupleTest(tuple);
        final Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple18() {
        final Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        tupleTest(tuple);
        final Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple19() {
        final Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
        tupleTest(tuple);
        final Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple20() {
        final Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        tupleTest(tuple);
        final Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple21() {
        final Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        tupleTest(tuple);
        final Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
            @Override
            public Optional<Integer> get20() {
                return Optional.of(20);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple22() {
        final Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        tupleTest(tuple);
        final Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
            @Override
            public Optional<Integer> get20() {
                return Optional.of(20);
            }
            @Override
            public Optional<Integer> get21() {
                return Optional.of(21);
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple23() {
        final Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = TuplesOfNullables.ofNullables(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        tupleTest(tuple);
        final Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Optional<Integer> get0() {
                return Optional.of(0);
            }
            @Override
            public Optional<Integer> get1() {
                return Optional.of(1);
            }
            @Override
            public Optional<Integer> get2() {
                return Optional.of(2);
            }
            @Override
            public Optional<Integer> get3() {
                return Optional.of(3);
            }
            @Override
            public Optional<Integer> get4() {
                return Optional.of(4);
            }
            @Override
            public Optional<Integer> get5() {
                return Optional.of(5);
            }
            @Override
            public Optional<Integer> get6() {
                return Optional.of(6);
            }
            @Override
            public Optional<Integer> get7() {
                return Optional.of(7);
            }
            @Override
            public Optional<Integer> get8() {
                return Optional.of(8);
            }
            @Override
            public Optional<Integer> get9() {
                return Optional.of(9);
            }
            @Override
            public Optional<Integer> get10() {
                return Optional.of(10);
            }
            @Override
            public Optional<Integer> get11() {
                return Optional.of(11);
            }
            @Override
            public Optional<Integer> get12() {
                return Optional.of(12);
            }
            @Override
            public Optional<Integer> get13() {
                return Optional.of(13);
            }
            @Override
            public Optional<Integer> get14() {
                return Optional.of(14);
            }
            @Override
            public Optional<Integer> get15() {
                return Optional.of(15);
            }
            @Override
            public Optional<Integer> get16() {
                return Optional.of(16);
            }
            @Override
            public Optional<Integer> get17() {
                return Optional.of(17);
            }
            @Override
            public Optional<Integer> get18() {
                return Optional.of(18);
            }
            @Override
            public Optional<Integer> get19() {
                return Optional.of(19);
            }
            @Override
            public Optional<Integer> get20() {
                return Optional.of(20);
            }
            @Override
            public Optional<Integer> get21() {
                return Optional.of(21);
            }
            @Override
            public Optional<Integer> get22() {
                return Optional.of(22);
            }
        };
        tupleTest(defaultTuple);
    }
    
    private void tupleTest(final Tuple1OfNullables<Integer> tuple) {
        final TupleGetter<Tuple1OfNullables<Integer>, Optional<Integer>> getter0 = Tuple1OfNullables.getter0();
        final TupleGetter<Tuple1OfNullables<Integer>, Integer> getterOrNull0 = Tuple1OfNullables.getterOrNull0();
        assertEquals(0, getter0.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(1));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(0, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(0, expectedSum2);
    }
    
    private void tupleTest(final Tuple2OfNullables<Integer, Integer> tuple) {
        final TupleGetter<Tuple2OfNullables<Integer, Integer>, Optional<Integer>> getter0 = Tuple2OfNullables.getter0();
        final TupleGetter<Tuple2OfNullables<Integer, Integer>, Optional<Integer>> getter1 = Tuple2OfNullables.getter1();
        final TupleGetter<Tuple2OfNullables<Integer, Integer>, Integer> getterOrNull0 = Tuple2OfNullables.getterOrNull0();
        final TupleGetter<Tuple2OfNullables<Integer, Integer>, Integer> getterOrNull1 = Tuple2OfNullables.getterOrNull1();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(2));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(1, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(1, expectedSum2);
    }
    
    private void tupleTest(final Tuple3OfNullables<Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple3OfNullables<Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple3OfNullables.getter0();
        final TupleGetter<Tuple3OfNullables<Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple3OfNullables.getter1();
        final TupleGetter<Tuple3OfNullables<Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple3OfNullables.getter2();
        final TupleGetter<Tuple3OfNullables<Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple3OfNullables.getterOrNull0();
        final TupleGetter<Tuple3OfNullables<Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple3OfNullables.getterOrNull1();
        final TupleGetter<Tuple3OfNullables<Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple3OfNullables.getterOrNull2();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(3));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(3, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(3, expectedSum2);
    }
    
    private void tupleTest(final Tuple4OfNullables<Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple4OfNullables.getter0();
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple4OfNullables.getter1();
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple4OfNullables.getter2();
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple4OfNullables.getter3();
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple4OfNullables.getterOrNull0();
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple4OfNullables.getterOrNull1();
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple4OfNullables.getterOrNull2();
        final TupleGetter<Tuple4OfNullables<Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple4OfNullables.getterOrNull3();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(4));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(6, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(6, expectedSum2);
    }
    
    private void tupleTest(final Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple5OfNullables.getter0();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple5OfNullables.getter1();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple5OfNullables.getter2();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple5OfNullables.getter3();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple5OfNullables.getter4();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple5OfNullables.getterOrNull0();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple5OfNullables.getterOrNull1();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple5OfNullables.getterOrNull2();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple5OfNullables.getterOrNull3();
        final TupleGetter<Tuple5OfNullables<Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple5OfNullables.getterOrNull4();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(5));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(10, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(10, expectedSum2);
    }
    
    private void tupleTest(final Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple6OfNullables.getter0();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple6OfNullables.getter1();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple6OfNullables.getter2();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple6OfNullables.getter3();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple6OfNullables.getter4();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple6OfNullables.getter5();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple6OfNullables.getterOrNull0();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple6OfNullables.getterOrNull1();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple6OfNullables.getterOrNull2();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple6OfNullables.getterOrNull3();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple6OfNullables.getterOrNull4();
        final TupleGetter<Tuple6OfNullables<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple6OfNullables.getterOrNull5();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(6));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(15, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(15, expectedSum2);
    }
    
    private void tupleTest(final Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple7OfNullables.getter0();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple7OfNullables.getter1();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple7OfNullables.getter2();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple7OfNullables.getter3();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple7OfNullables.getter4();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple7OfNullables.getter5();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple7OfNullables.getter6();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple7OfNullables.getterOrNull0();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple7OfNullables.getterOrNull1();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple7OfNullables.getterOrNull2();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple7OfNullables.getterOrNull3();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple7OfNullables.getterOrNull4();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple7OfNullables.getterOrNull5();
        final TupleGetter<Tuple7OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple7OfNullables.getterOrNull6();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(7));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(21, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(21, expectedSum2);
    }
    
    private void tupleTest(final Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple8OfNullables.getter0();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple8OfNullables.getter1();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple8OfNullables.getter2();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple8OfNullables.getter3();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple8OfNullables.getter4();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple8OfNullables.getter5();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple8OfNullables.getter6();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple8OfNullables.getter7();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple8OfNullables.getterOrNull0();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple8OfNullables.getterOrNull1();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple8OfNullables.getterOrNull2();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple8OfNullables.getterOrNull3();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple8OfNullables.getterOrNull4();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple8OfNullables.getterOrNull5();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple8OfNullables.getterOrNull6();
        final TupleGetter<Tuple8OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple8OfNullables.getterOrNull7();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(8));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(28, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(28, expectedSum2);
    }
    
    private void tupleTest(final Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple9OfNullables.getter0();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple9OfNullables.getter1();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple9OfNullables.getter2();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple9OfNullables.getter3();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple9OfNullables.getter4();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple9OfNullables.getter5();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple9OfNullables.getter6();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple9OfNullables.getter7();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple9OfNullables.getter8();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple9OfNullables.getterOrNull0();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple9OfNullables.getterOrNull1();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple9OfNullables.getterOrNull2();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple9OfNullables.getterOrNull3();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple9OfNullables.getterOrNull4();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple9OfNullables.getterOrNull5();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple9OfNullables.getterOrNull6();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple9OfNullables.getterOrNull7();
        final TupleGetter<Tuple9OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple9OfNullables.getterOrNull8();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(9));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(36, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(36, expectedSum2);
    }
    
    private void tupleTest(final Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple10OfNullables.getter0();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple10OfNullables.getter1();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple10OfNullables.getter2();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple10OfNullables.getter3();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple10OfNullables.getter4();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple10OfNullables.getter5();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple10OfNullables.getter6();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple10OfNullables.getter7();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple10OfNullables.getter8();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple10OfNullables.getter9();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple10OfNullables.getterOrNull0();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple10OfNullables.getterOrNull1();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple10OfNullables.getterOrNull2();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple10OfNullables.getterOrNull3();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple10OfNullables.getterOrNull4();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple10OfNullables.getterOrNull5();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple10OfNullables.getterOrNull6();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple10OfNullables.getterOrNull7();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple10OfNullables.getterOrNull8();
        final TupleGetter<Tuple10OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple10OfNullables.getterOrNull9();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(10));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(45, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(45, expectedSum2);
    }
    
    private void tupleTest(final Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple11OfNullables.getter0();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple11OfNullables.getter1();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple11OfNullables.getter2();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple11OfNullables.getter3();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple11OfNullables.getter4();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple11OfNullables.getter5();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple11OfNullables.getter6();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple11OfNullables.getter7();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple11OfNullables.getter8();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple11OfNullables.getter9();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple11OfNullables.getter10();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple11OfNullables.getterOrNull0();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple11OfNullables.getterOrNull1();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple11OfNullables.getterOrNull2();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple11OfNullables.getterOrNull3();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple11OfNullables.getterOrNull4();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple11OfNullables.getterOrNull5();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple11OfNullables.getterOrNull6();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple11OfNullables.getterOrNull7();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple11OfNullables.getterOrNull8();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple11OfNullables.getterOrNull9();
        final TupleGetter<Tuple11OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple11OfNullables.getterOrNull10();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(11));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(55, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(55, expectedSum2);
    }
    
    private void tupleTest(final Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple12OfNullables.getter0();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple12OfNullables.getter1();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple12OfNullables.getter2();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple12OfNullables.getter3();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple12OfNullables.getter4();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple12OfNullables.getter5();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple12OfNullables.getter6();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple12OfNullables.getter7();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple12OfNullables.getter8();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple12OfNullables.getter9();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple12OfNullables.getter10();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple12OfNullables.getter11();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple12OfNullables.getterOrNull0();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple12OfNullables.getterOrNull1();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple12OfNullables.getterOrNull2();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple12OfNullables.getterOrNull3();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple12OfNullables.getterOrNull4();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple12OfNullables.getterOrNull5();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple12OfNullables.getterOrNull6();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple12OfNullables.getterOrNull7();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple12OfNullables.getterOrNull8();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple12OfNullables.getterOrNull9();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple12OfNullables.getterOrNull10();
        final TupleGetter<Tuple12OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple12OfNullables.getterOrNull11();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(12));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(66, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(66, expectedSum2);
    }
    
    private void tupleTest(final Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple13OfNullables.getter0();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple13OfNullables.getter1();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple13OfNullables.getter2();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple13OfNullables.getter3();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple13OfNullables.getter4();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple13OfNullables.getter5();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple13OfNullables.getter6();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple13OfNullables.getter7();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple13OfNullables.getter8();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple13OfNullables.getter9();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple13OfNullables.getter10();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple13OfNullables.getter11();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple13OfNullables.getter12();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple13OfNullables.getterOrNull0();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple13OfNullables.getterOrNull1();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple13OfNullables.getterOrNull2();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple13OfNullables.getterOrNull3();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple13OfNullables.getterOrNull4();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple13OfNullables.getterOrNull5();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple13OfNullables.getterOrNull6();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple13OfNullables.getterOrNull7();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple13OfNullables.getterOrNull8();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple13OfNullables.getterOrNull9();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple13OfNullables.getterOrNull10();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple13OfNullables.getterOrNull11();
        final TupleGetter<Tuple13OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple13OfNullables.getterOrNull12();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(13));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(78, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(78, expectedSum2);
    }
    
    private void tupleTest(final Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple14OfNullables.getter0();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple14OfNullables.getter1();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple14OfNullables.getter2();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple14OfNullables.getter3();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple14OfNullables.getter4();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple14OfNullables.getter5();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple14OfNullables.getter6();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple14OfNullables.getter7();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple14OfNullables.getter8();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple14OfNullables.getter9();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple14OfNullables.getter10();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple14OfNullables.getter11();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple14OfNullables.getter12();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple14OfNullables.getter13();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple14OfNullables.getterOrNull0();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple14OfNullables.getterOrNull1();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple14OfNullables.getterOrNull2();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple14OfNullables.getterOrNull3();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple14OfNullables.getterOrNull4();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple14OfNullables.getterOrNull5();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple14OfNullables.getterOrNull6();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple14OfNullables.getterOrNull7();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple14OfNullables.getterOrNull8();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple14OfNullables.getterOrNull9();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple14OfNullables.getterOrNull10();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple14OfNullables.getterOrNull11();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple14OfNullables.getterOrNull12();
        final TupleGetter<Tuple14OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple14OfNullables.getterOrNull13();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(14));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(91, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(91, expectedSum2);
    }
    
    private void tupleTest(final Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple15OfNullables.getter0();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple15OfNullables.getter1();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple15OfNullables.getter2();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple15OfNullables.getter3();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple15OfNullables.getter4();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple15OfNullables.getter5();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple15OfNullables.getter6();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple15OfNullables.getter7();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple15OfNullables.getter8();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple15OfNullables.getter9();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple15OfNullables.getter10();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple15OfNullables.getter11();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple15OfNullables.getter12();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple15OfNullables.getter13();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple15OfNullables.getter14();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple15OfNullables.getterOrNull0();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple15OfNullables.getterOrNull1();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple15OfNullables.getterOrNull2();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple15OfNullables.getterOrNull3();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple15OfNullables.getterOrNull4();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple15OfNullables.getterOrNull5();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple15OfNullables.getterOrNull6();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple15OfNullables.getterOrNull7();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple15OfNullables.getterOrNull8();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple15OfNullables.getterOrNull9();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple15OfNullables.getterOrNull10();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple15OfNullables.getterOrNull11();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple15OfNullables.getterOrNull12();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple15OfNullables.getterOrNull13();
        final TupleGetter<Tuple15OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple15OfNullables.getterOrNull14();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(15));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(105, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(105, expectedSum2);
    }
    
    private void tupleTest(final Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple16OfNullables.getter0();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple16OfNullables.getter1();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple16OfNullables.getter2();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple16OfNullables.getter3();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple16OfNullables.getter4();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple16OfNullables.getter5();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple16OfNullables.getter6();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple16OfNullables.getter7();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple16OfNullables.getter8();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple16OfNullables.getter9();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple16OfNullables.getter10();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple16OfNullables.getter11();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple16OfNullables.getter12();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple16OfNullables.getter13();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple16OfNullables.getter14();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple16OfNullables.getter15();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple16OfNullables.getterOrNull0();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple16OfNullables.getterOrNull1();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple16OfNullables.getterOrNull2();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple16OfNullables.getterOrNull3();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple16OfNullables.getterOrNull4();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple16OfNullables.getterOrNull5();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple16OfNullables.getterOrNull6();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple16OfNullables.getterOrNull7();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple16OfNullables.getterOrNull8();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple16OfNullables.getterOrNull9();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple16OfNullables.getterOrNull10();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple16OfNullables.getterOrNull11();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple16OfNullables.getterOrNull12();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple16OfNullables.getterOrNull13();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple16OfNullables.getterOrNull14();
        final TupleGetter<Tuple16OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple16OfNullables.getterOrNull15();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(16));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(120, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(120, expectedSum2);
    }
    
    private void tupleTest(final Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple17OfNullables.getter0();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple17OfNullables.getter1();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple17OfNullables.getter2();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple17OfNullables.getter3();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple17OfNullables.getter4();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple17OfNullables.getter5();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple17OfNullables.getter6();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple17OfNullables.getter7();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple17OfNullables.getter8();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple17OfNullables.getter9();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple17OfNullables.getter10();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple17OfNullables.getter11();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple17OfNullables.getter12();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple17OfNullables.getter13();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple17OfNullables.getter14();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple17OfNullables.getter15();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = Tuple17OfNullables.getter16();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple17OfNullables.getterOrNull0();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple17OfNullables.getterOrNull1();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple17OfNullables.getterOrNull2();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple17OfNullables.getterOrNull3();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple17OfNullables.getterOrNull4();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple17OfNullables.getterOrNull5();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple17OfNullables.getterOrNull6();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple17OfNullables.getterOrNull7();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple17OfNullables.getterOrNull8();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple17OfNullables.getterOrNull9();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple17OfNullables.getterOrNull10();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple17OfNullables.getterOrNull11();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple17OfNullables.getterOrNull12();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple17OfNullables.getterOrNull13();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple17OfNullables.getterOrNull14();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple17OfNullables.getterOrNull15();
        final TupleGetter<Tuple17OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = Tuple17OfNullables.getterOrNull16();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(17));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(136, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(136, expectedSum2);
    }
    
    private void tupleTest(final Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple18OfNullables.getter0();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple18OfNullables.getter1();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple18OfNullables.getter2();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple18OfNullables.getter3();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple18OfNullables.getter4();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple18OfNullables.getter5();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple18OfNullables.getter6();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple18OfNullables.getter7();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple18OfNullables.getter8();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple18OfNullables.getter9();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple18OfNullables.getter10();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple18OfNullables.getter11();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple18OfNullables.getter12();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple18OfNullables.getter13();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple18OfNullables.getter14();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple18OfNullables.getter15();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = Tuple18OfNullables.getter16();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = Tuple18OfNullables.getter17();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple18OfNullables.getterOrNull0();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple18OfNullables.getterOrNull1();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple18OfNullables.getterOrNull2();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple18OfNullables.getterOrNull3();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple18OfNullables.getterOrNull4();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple18OfNullables.getterOrNull5();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple18OfNullables.getterOrNull6();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple18OfNullables.getterOrNull7();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple18OfNullables.getterOrNull8();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple18OfNullables.getterOrNull9();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple18OfNullables.getterOrNull10();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple18OfNullables.getterOrNull11();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple18OfNullables.getterOrNull12();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple18OfNullables.getterOrNull13();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple18OfNullables.getterOrNull14();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple18OfNullables.getterOrNull15();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = Tuple18OfNullables.getterOrNull16();
        final TupleGetter<Tuple18OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = Tuple18OfNullables.getterOrNull17();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(18));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(153, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(153, expectedSum2);
    }
    
    private void tupleTest(final Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple19OfNullables.getter0();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple19OfNullables.getter1();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple19OfNullables.getter2();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple19OfNullables.getter3();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple19OfNullables.getter4();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple19OfNullables.getter5();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple19OfNullables.getter6();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple19OfNullables.getter7();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple19OfNullables.getter8();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple19OfNullables.getter9();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple19OfNullables.getter10();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple19OfNullables.getter11();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple19OfNullables.getter12();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple19OfNullables.getter13();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple19OfNullables.getter14();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple19OfNullables.getter15();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = Tuple19OfNullables.getter16();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = Tuple19OfNullables.getter17();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = Tuple19OfNullables.getter18();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple19OfNullables.getterOrNull0();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple19OfNullables.getterOrNull1();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple19OfNullables.getterOrNull2();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple19OfNullables.getterOrNull3();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple19OfNullables.getterOrNull4();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple19OfNullables.getterOrNull5();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple19OfNullables.getterOrNull6();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple19OfNullables.getterOrNull7();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple19OfNullables.getterOrNull8();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple19OfNullables.getterOrNull9();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple19OfNullables.getterOrNull10();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple19OfNullables.getterOrNull11();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple19OfNullables.getterOrNull12();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple19OfNullables.getterOrNull13();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple19OfNullables.getterOrNull14();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple19OfNullables.getterOrNull15();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = Tuple19OfNullables.getterOrNull16();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = Tuple19OfNullables.getterOrNull17();
        final TupleGetter<Tuple19OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = Tuple19OfNullables.getterOrNull18();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(19));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(171, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(171, expectedSum2);
    }
    
    private void tupleTest(final Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple20OfNullables.getter0();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple20OfNullables.getter1();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple20OfNullables.getter2();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple20OfNullables.getter3();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple20OfNullables.getter4();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple20OfNullables.getter5();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple20OfNullables.getter6();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple20OfNullables.getter7();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple20OfNullables.getter8();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple20OfNullables.getter9();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple20OfNullables.getter10();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple20OfNullables.getter11();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple20OfNullables.getter12();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple20OfNullables.getter13();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple20OfNullables.getter14();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple20OfNullables.getter15();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = Tuple20OfNullables.getter16();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = Tuple20OfNullables.getter17();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = Tuple20OfNullables.getter18();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = Tuple20OfNullables.getter19();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple20OfNullables.getterOrNull0();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple20OfNullables.getterOrNull1();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple20OfNullables.getterOrNull2();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple20OfNullables.getterOrNull3();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple20OfNullables.getterOrNull4();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple20OfNullables.getterOrNull5();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple20OfNullables.getterOrNull6();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple20OfNullables.getterOrNull7();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple20OfNullables.getterOrNull8();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple20OfNullables.getterOrNull9();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple20OfNullables.getterOrNull10();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple20OfNullables.getterOrNull11();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple20OfNullables.getterOrNull12();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple20OfNullables.getterOrNull13();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple20OfNullables.getterOrNull14();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple20OfNullables.getterOrNull15();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = Tuple20OfNullables.getterOrNull16();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = Tuple20OfNullables.getterOrNull17();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = Tuple20OfNullables.getterOrNull18();
        final TupleGetter<Tuple20OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = Tuple20OfNullables.getterOrNull19();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(20));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(190, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(190, expectedSum2);
    }
    
    private void tupleTest(final Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple21OfNullables.getter0();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple21OfNullables.getter1();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple21OfNullables.getter2();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple21OfNullables.getter3();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple21OfNullables.getter4();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple21OfNullables.getter5();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple21OfNullables.getter6();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple21OfNullables.getter7();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple21OfNullables.getter8();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple21OfNullables.getter9();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple21OfNullables.getter10();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple21OfNullables.getter11();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple21OfNullables.getter12();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple21OfNullables.getter13();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple21OfNullables.getter14();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple21OfNullables.getter15();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = Tuple21OfNullables.getter16();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = Tuple21OfNullables.getter17();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = Tuple21OfNullables.getter18();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = Tuple21OfNullables.getter19();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter20 = Tuple21OfNullables.getter20();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple21OfNullables.getterOrNull0();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple21OfNullables.getterOrNull1();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple21OfNullables.getterOrNull2();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple21OfNullables.getterOrNull3();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple21OfNullables.getterOrNull4();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple21OfNullables.getterOrNull5();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple21OfNullables.getterOrNull6();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple21OfNullables.getterOrNull7();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple21OfNullables.getterOrNull8();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple21OfNullables.getterOrNull9();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple21OfNullables.getterOrNull10();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple21OfNullables.getterOrNull11();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple21OfNullables.getterOrNull12();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple21OfNullables.getterOrNull13();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple21OfNullables.getterOrNull14();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple21OfNullables.getterOrNull15();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = Tuple21OfNullables.getterOrNull16();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = Tuple21OfNullables.getterOrNull17();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = Tuple21OfNullables.getterOrNull18();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = Tuple21OfNullables.getterOrNull19();
        final TupleGetter<Tuple21OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull20 = Tuple21OfNullables.getterOrNull20();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(20, getter20.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(20, getter20.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
        assertEquals(20, getterOrNull20.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get(20).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(21));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(210, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(210, expectedSum2);
    }
    
    private void tupleTest(final Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple22OfNullables.getter0();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple22OfNullables.getter1();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple22OfNullables.getter2();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple22OfNullables.getter3();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple22OfNullables.getter4();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple22OfNullables.getter5();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple22OfNullables.getter6();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple22OfNullables.getter7();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple22OfNullables.getter8();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple22OfNullables.getter9();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple22OfNullables.getter10();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple22OfNullables.getter11();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple22OfNullables.getter12();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple22OfNullables.getter13();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple22OfNullables.getter14();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple22OfNullables.getter15();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = Tuple22OfNullables.getter16();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = Tuple22OfNullables.getter17();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = Tuple22OfNullables.getter18();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = Tuple22OfNullables.getter19();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter20 = Tuple22OfNullables.getter20();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter21 = Tuple22OfNullables.getter21();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple22OfNullables.getterOrNull0();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple22OfNullables.getterOrNull1();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple22OfNullables.getterOrNull2();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple22OfNullables.getterOrNull3();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple22OfNullables.getterOrNull4();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple22OfNullables.getterOrNull5();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple22OfNullables.getterOrNull6();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple22OfNullables.getterOrNull7();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple22OfNullables.getterOrNull8();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple22OfNullables.getterOrNull9();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple22OfNullables.getterOrNull10();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple22OfNullables.getterOrNull11();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple22OfNullables.getterOrNull12();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple22OfNullables.getterOrNull13();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple22OfNullables.getterOrNull14();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple22OfNullables.getterOrNull15();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = Tuple22OfNullables.getterOrNull16();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = Tuple22OfNullables.getterOrNull17();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = Tuple22OfNullables.getterOrNull18();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = Tuple22OfNullables.getterOrNull19();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull20 = Tuple22OfNullables.getterOrNull20();
        final TupleGetter<Tuple22OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull21 = Tuple22OfNullables.getterOrNull21();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(20, getter20.index());
        assertEquals(21, getter21.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(20, getter20.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(21, getter21.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
        assertEquals(20, getterOrNull20.apply(tuple));
        assertEquals(21, getterOrNull21.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get(20).orElseThrow(NoSuchElementException::new));
        assertEquals(21, tuple.get(21).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(22));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(231, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(231, expectedSum2);
    }
    
    private void tupleTest(final Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter0 = Tuple23OfNullables.getter0();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter1 = Tuple23OfNullables.getter1();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter2 = Tuple23OfNullables.getter2();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter3 = Tuple23OfNullables.getter3();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter4 = Tuple23OfNullables.getter4();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter5 = Tuple23OfNullables.getter5();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter6 = Tuple23OfNullables.getter6();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter7 = Tuple23OfNullables.getter7();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter8 = Tuple23OfNullables.getter8();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter9 = Tuple23OfNullables.getter9();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter10 = Tuple23OfNullables.getter10();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter11 = Tuple23OfNullables.getter11();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter12 = Tuple23OfNullables.getter12();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter13 = Tuple23OfNullables.getter13();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter14 = Tuple23OfNullables.getter14();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter15 = Tuple23OfNullables.getter15();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter16 = Tuple23OfNullables.getter16();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter17 = Tuple23OfNullables.getter17();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter18 = Tuple23OfNullables.getter18();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter19 = Tuple23OfNullables.getter19();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter20 = Tuple23OfNullables.getter20();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter21 = Tuple23OfNullables.getter21();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Optional<Integer>> getter22 = Tuple23OfNullables.getter22();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull0 = Tuple23OfNullables.getterOrNull0();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull1 = Tuple23OfNullables.getterOrNull1();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull2 = Tuple23OfNullables.getterOrNull2();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull3 = Tuple23OfNullables.getterOrNull3();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull4 = Tuple23OfNullables.getterOrNull4();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull5 = Tuple23OfNullables.getterOrNull5();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull6 = Tuple23OfNullables.getterOrNull6();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull7 = Tuple23OfNullables.getterOrNull7();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull8 = Tuple23OfNullables.getterOrNull8();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull9 = Tuple23OfNullables.getterOrNull9();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull10 = Tuple23OfNullables.getterOrNull10();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull11 = Tuple23OfNullables.getterOrNull11();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull12 = Tuple23OfNullables.getterOrNull12();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull13 = Tuple23OfNullables.getterOrNull13();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull14 = Tuple23OfNullables.getterOrNull14();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull15 = Tuple23OfNullables.getterOrNull15();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull16 = Tuple23OfNullables.getterOrNull16();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull17 = Tuple23OfNullables.getterOrNull17();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull18 = Tuple23OfNullables.getterOrNull18();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull19 = Tuple23OfNullables.getterOrNull19();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull20 = Tuple23OfNullables.getterOrNull20();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull21 = Tuple23OfNullables.getterOrNull21();
        final TupleGetter<Tuple23OfNullables<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getterOrNull22 = Tuple23OfNullables.getterOrNull22();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(9, getter9.index());
        assertEquals(10, getter10.index());
        assertEquals(11, getter11.index());
        assertEquals(12, getter12.index());
        assertEquals(13, getter13.index());
        assertEquals(14, getter14.index());
        assertEquals(15, getter15.index());
        assertEquals(16, getter16.index());
        assertEquals(17, getter17.index());
        assertEquals(18, getter18.index());
        assertEquals(19, getter19.index());
        assertEquals(20, getter20.index());
        assertEquals(21, getter21.index());
        assertEquals(22, getter22.index());
        assertEquals(0, getter0.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(1, getter1.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(2, getter2.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(3, getter3.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(4, getter4.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(5, getter5.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(6, getter6.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(7, getter7.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(8, getter8.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(9, getter9.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(10, getter10.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(11, getter11.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(12, getter12.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(13, getter13.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(14, getter14.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(15, getter15.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(16, getter16.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(17, getter17.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(18, getter18.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(19, getter19.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(20, getter20.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(21, getter21.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(22, getter22.apply(tuple).orElseThrow(NoSuchElementException::new));
        assertEquals(0, getterOrNull0.apply(tuple));
        assertEquals(1, getterOrNull1.apply(tuple));
        assertEquals(2, getterOrNull2.apply(tuple));
        assertEquals(3, getterOrNull3.apply(tuple));
        assertEquals(4, getterOrNull4.apply(tuple));
        assertEquals(5, getterOrNull5.apply(tuple));
        assertEquals(6, getterOrNull6.apply(tuple));
        assertEquals(7, getterOrNull7.apply(tuple));
        assertEquals(8, getterOrNull8.apply(tuple));
        assertEquals(9, getterOrNull9.apply(tuple));
        assertEquals(10, getterOrNull10.apply(tuple));
        assertEquals(11, getterOrNull11.apply(tuple));
        assertEquals(12, getterOrNull12.apply(tuple));
        assertEquals(13, getterOrNull13.apply(tuple));
        assertEquals(14, getterOrNull14.apply(tuple));
        assertEquals(15, getterOrNull15.apply(tuple));
        assertEquals(16, getterOrNull16.apply(tuple));
        assertEquals(17, getterOrNull17.apply(tuple));
        assertEquals(18, getterOrNull18.apply(tuple));
        assertEquals(19, getterOrNull19.apply(tuple));
        assertEquals(20, getterOrNull20.apply(tuple));
        assertEquals(21, getterOrNull21.apply(tuple));
        assertEquals(22, getterOrNull22.apply(tuple));
        assertEquals(0, tuple.get(0).orElseThrow(NoSuchElementException::new));
        assertEquals(1, tuple.get(1).orElseThrow(NoSuchElementException::new));
        assertEquals(2, tuple.get(2).orElseThrow(NoSuchElementException::new));
        assertEquals(3, tuple.get(3).orElseThrow(NoSuchElementException::new));
        assertEquals(4, tuple.get(4).orElseThrow(NoSuchElementException::new));
        assertEquals(5, tuple.get(5).orElseThrow(NoSuchElementException::new));
        assertEquals(6, tuple.get(6).orElseThrow(NoSuchElementException::new));
        assertEquals(7, tuple.get(7).orElseThrow(NoSuchElementException::new));
        assertEquals(8, tuple.get(8).orElseThrow(NoSuchElementException::new));
        assertEquals(9, tuple.get(9).orElseThrow(NoSuchElementException::new));
        assertEquals(10, tuple.get(10).orElseThrow(NoSuchElementException::new));
        assertEquals(11, tuple.get(11).orElseThrow(NoSuchElementException::new));
        assertEquals(12, tuple.get(12).orElseThrow(NoSuchElementException::new));
        assertEquals(13, tuple.get(13).orElseThrow(NoSuchElementException::new));
        assertEquals(14, tuple.get(14).orElseThrow(NoSuchElementException::new));
        assertEquals(15, tuple.get(15).orElseThrow(NoSuchElementException::new));
        assertEquals(16, tuple.get(16).orElseThrow(NoSuchElementException::new));
        assertEquals(17, tuple.get(17).orElseThrow(NoSuchElementException::new));
        assertEquals(18, tuple.get(18).orElseThrow(NoSuchElementException::new));
        assertEquals(19, tuple.get(19).orElseThrow(NoSuchElementException::new));
        assertEquals(20, tuple.get(20).orElseThrow(NoSuchElementException::new));
        assertEquals(21, tuple.get(21).orElseThrow(NoSuchElementException::new));
        assertEquals(22, tuple.get(22).orElseThrow(NoSuchElementException::new));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(23));
        final long expectedSum = tuple.stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(Integer.class::cast)
            .mapToInt(i -> i)
            .sum();
        assertEquals(253, expectedSum);
        final long expectedSum2 = tuple.streamOf(Integer.class)
            .mapToInt(i -> i)
            .sum();
        assertEquals(253, expectedSum2);
    }
}