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
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class TupleTest {
    
    @Test
    void tuple1() {
        final Tuple1<Integer> tuple = Tuples.of(0);
        tupleTest(tuple);
        final Tuple1<Integer> defaultTuple = new Tuple1<Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple2() {
        final Tuple2<Integer, Integer> tuple = Tuples.of(0, 1);
        tupleTest(tuple);
        final Tuple2<Integer, Integer> defaultTuple = new Tuple2<Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple3() {
        final Tuple3<Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2);
        tupleTest(tuple);
        final Tuple3<Integer, Integer, Integer> defaultTuple = new Tuple3<Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple4() {
        final Tuple4<Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3);
        tupleTest(tuple);
        final Tuple4<Integer, Integer, Integer, Integer> defaultTuple = new Tuple4<Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple5() {
        final Tuple5<Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4);
        tupleTest(tuple);
        final Tuple5<Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple5<Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple6() {
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5);
        tupleTest(tuple);
        final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple7() {
        final Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6);
        tupleTest(tuple);
        final Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple8() {
        final Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7);
        tupleTest(tuple);
        final Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple9() {
        final Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        tupleTest(tuple);
        final Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple10() {
        final Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        tupleTest(tuple);
        final Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple11() {
        final Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        tupleTest(tuple);
        final Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple12() {
        final Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        tupleTest(tuple);
        final Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple13() {
        final Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        tupleTest(tuple);
        final Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple14() {
        final Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        tupleTest(tuple);
        final Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple15() {
        final Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        tupleTest(tuple);
        final Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple16() {
        final Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        tupleTest(tuple);
        final Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple17() {
        final Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        tupleTest(tuple);
        final Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
            @Override
            public Integer get16() {
                return 16;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple18() {
        final Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        tupleTest(tuple);
        final Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
            @Override
            public Integer get16() {
                return 16;
            }
            @Override
            public Integer get17() {
                return 17;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple19() {
        final Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
        tupleTest(tuple);
        final Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
            @Override
            public Integer get16() {
                return 16;
            }
            @Override
            public Integer get17() {
                return 17;
            }
            @Override
            public Integer get18() {
                return 18;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple20() {
        final Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        tupleTest(tuple);
        final Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
            @Override
            public Integer get16() {
                return 16;
            }
            @Override
            public Integer get17() {
                return 17;
            }
            @Override
            public Integer get18() {
                return 18;
            }
            @Override
            public Integer get19() {
                return 19;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple21() {
        final Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        tupleTest(tuple);
        final Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
            @Override
            public Integer get16() {
                return 16;
            }
            @Override
            public Integer get17() {
                return 17;
            }
            @Override
            public Integer get18() {
                return 18;
            }
            @Override
            public Integer get19() {
                return 19;
            }
            @Override
            public Integer get20() {
                return 20;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple22() {
        final Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        tupleTest(tuple);
        final Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
            @Override
            public Integer get16() {
                return 16;
            }
            @Override
            public Integer get17() {
                return 17;
            }
            @Override
            public Integer get18() {
                return 18;
            }
            @Override
            public Integer get19() {
                return 19;
            }
            @Override
            public Integer get20() {
                return 20;
            }
            @Override
            public Integer get21() {
                return 21;
            }
        };
        tupleTest(defaultTuple);
    }
    
    @Test
    void tuple23() {
        final Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple = Tuples.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        tupleTest(tuple);
        final Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> defaultTuple = new Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>() 
        {
            @Override
            public Integer get0() {
                return 0;
            }
            @Override
            public Integer get1() {
                return 1;
            }
            @Override
            public Integer get2() {
                return 2;
            }
            @Override
            public Integer get3() {
                return 3;
            }
            @Override
            public Integer get4() {
                return 4;
            }
            @Override
            public Integer get5() {
                return 5;
            }
            @Override
            public Integer get6() {
                return 6;
            }
            @Override
            public Integer get7() {
                return 7;
            }
            @Override
            public Integer get8() {
                return 8;
            }
            @Override
            public Integer get9() {
                return 9;
            }
            @Override
            public Integer get10() {
                return 10;
            }
            @Override
            public Integer get11() {
                return 11;
            }
            @Override
            public Integer get12() {
                return 12;
            }
            @Override
            public Integer get13() {
                return 13;
            }
            @Override
            public Integer get14() {
                return 14;
            }
            @Override
            public Integer get15() {
                return 15;
            }
            @Override
            public Integer get16() {
                return 16;
            }
            @Override
            public Integer get17() {
                return 17;
            }
            @Override
            public Integer get18() {
                return 18;
            }
            @Override
            public Integer get19() {
                return 19;
            }
            @Override
            public Integer get20() {
                return 20;
            }
            @Override
            public Integer get21() {
                return 21;
            }
            @Override
            public Integer get22() {
                return 22;
            }
        };
        tupleTest(defaultTuple);
    }
    
    private void tupleTest(final Tuple1<Integer> tuple) {
        final TupleGetter<Tuple1<Integer>, Integer> getter0 = Tuple1.getter0();
        assertEquals(0, getter0.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(1));
    }
    
    private void tupleTest(final Tuple2<Integer, Integer> tuple) {
        final TupleGetter<Tuple2<Integer, Integer>, Integer> getter0 = Tuple2.getter0();
        final TupleGetter<Tuple2<Integer, Integer>, Integer> getter1 = Tuple2.getter1();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(2));
    }
    
    private void tupleTest(final Tuple3<Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple3<Integer, Integer, Integer>, Integer> getter0 = Tuple3.getter0();
        final TupleGetter<Tuple3<Integer, Integer, Integer>, Integer> getter1 = Tuple3.getter1();
        final TupleGetter<Tuple3<Integer, Integer, Integer>, Integer> getter2 = Tuple3.getter2();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(3));
    }
    
    private void tupleTest(final Tuple4<Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple4.getter0();
        final TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple4.getter1();
        final TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple4.getter2();
        final TupleGetter<Tuple4<Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple4.getter3();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(4));
    }
    
    private void tupleTest(final Tuple5<Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple5.getter0();
        final TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple5.getter1();
        final TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple5.getter2();
        final TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple5.getter3();
        final TupleGetter<Tuple5<Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple5.getter4();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(5));
    }
    
    private void tupleTest(final Tuple6<Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple6.getter0();
        final TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple6.getter1();
        final TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple6.getter2();
        final TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple6.getter3();
        final TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple6.getter4();
        final TupleGetter<Tuple6<Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple6.getter5();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(6));
    }
    
    private void tupleTest(final Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple7.getter0();
        final TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple7.getter1();
        final TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple7.getter2();
        final TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple7.getter3();
        final TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple7.getter4();
        final TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple7.getter5();
        final TupleGetter<Tuple7<Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple7.getter6();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(7));
    }
    
    private void tupleTest(final Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple8.getter0();
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple8.getter1();
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple8.getter2();
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple8.getter3();
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple8.getter4();
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple8.getter5();
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple8.getter6();
        final TupleGetter<Tuple8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple8.getter7();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(8));
    }
    
    private void tupleTest(final Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple9.getter0();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple9.getter1();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple9.getter2();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple9.getter3();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple9.getter4();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple9.getter5();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple9.getter6();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple9.getter7();
        final TupleGetter<Tuple9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple9.getter8();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(2, getter2.index());
        assertEquals(3, getter3.index());
        assertEquals(4, getter4.index());
        assertEquals(5, getter5.index());
        assertEquals(6, getter6.index());
        assertEquals(7, getter7.index());
        assertEquals(8, getter8.index());
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(9));
    }
    
    private void tupleTest(final Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple10.getter0();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple10.getter1();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple10.getter2();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple10.getter3();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple10.getter4();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple10.getter5();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple10.getter6();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple10.getter7();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple10.getter8();
        final TupleGetter<Tuple10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple10.getter9();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(10));
    }
    
    private void tupleTest(final Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple11.getter0();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple11.getter1();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple11.getter2();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple11.getter3();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple11.getter4();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple11.getter5();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple11.getter6();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple11.getter7();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple11.getter8();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple11.getter9();
        final TupleGetter<Tuple11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple11.getter10();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(11));
    }
    
    private void tupleTest(final Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple12.getter0();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple12.getter1();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple12.getter2();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple12.getter3();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple12.getter4();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple12.getter5();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple12.getter6();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple12.getter7();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple12.getter8();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple12.getter9();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple12.getter10();
        final TupleGetter<Tuple12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple12.getter11();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(12));
    }
    
    private void tupleTest(final Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple13.getter0();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple13.getter1();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple13.getter2();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple13.getter3();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple13.getter4();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple13.getter5();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple13.getter6();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple13.getter7();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple13.getter8();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple13.getter9();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple13.getter10();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple13.getter11();
        final TupleGetter<Tuple13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple13.getter12();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(13));
    }
    
    private void tupleTest(final Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple14.getter0();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple14.getter1();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple14.getter2();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple14.getter3();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple14.getter4();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple14.getter5();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple14.getter6();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple14.getter7();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple14.getter8();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple14.getter9();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple14.getter10();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple14.getter11();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple14.getter12();
        final TupleGetter<Tuple14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple14.getter13();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(14));
    }
    
    private void tupleTest(final Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple15.getter0();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple15.getter1();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple15.getter2();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple15.getter3();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple15.getter4();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple15.getter5();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple15.getter6();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple15.getter7();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple15.getter8();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple15.getter9();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple15.getter10();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple15.getter11();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple15.getter12();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple15.getter13();
        final TupleGetter<Tuple15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple15.getter14();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(15));
    }
    
    private void tupleTest(final Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple16.getter0();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple16.getter1();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple16.getter2();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple16.getter3();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple16.getter4();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple16.getter5();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple16.getter6();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple16.getter7();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple16.getter8();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple16.getter9();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple16.getter10();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple16.getter11();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple16.getter12();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple16.getter13();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple16.getter14();
        final TupleGetter<Tuple16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple16.getter15();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(16));
    }
    
    private void tupleTest(final Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple17.getter0();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple17.getter1();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple17.getter2();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple17.getter3();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple17.getter4();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple17.getter5();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple17.getter6();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple17.getter7();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple17.getter8();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple17.getter9();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple17.getter10();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple17.getter11();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple17.getter12();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple17.getter13();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple17.getter14();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple17.getter15();
        final TupleGetter<Tuple17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple17.getter16();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertEquals(16, tuple.get(16));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(17));
    }
    
    private void tupleTest(final Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple18.getter0();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple18.getter1();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple18.getter2();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple18.getter3();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple18.getter4();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple18.getter5();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple18.getter6();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple18.getter7();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple18.getter8();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple18.getter9();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple18.getter10();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple18.getter11();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple18.getter12();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple18.getter13();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple18.getter14();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple18.getter15();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple18.getter16();
        final TupleGetter<Tuple18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple18.getter17();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertEquals(16, tuple.get(16));
        assertEquals(17, tuple.get(17));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(18));
    }
    
    private void tupleTest(final Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple19.getter0();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple19.getter1();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple19.getter2();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple19.getter3();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple19.getter4();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple19.getter5();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple19.getter6();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple19.getter7();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple19.getter8();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple19.getter9();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple19.getter10();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple19.getter11();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple19.getter12();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple19.getter13();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple19.getter14();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple19.getter15();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple19.getter16();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple19.getter17();
        final TupleGetter<Tuple19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple19.getter18();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertEquals(16, tuple.get(16));
        assertEquals(17, tuple.get(17));
        assertEquals(18, tuple.get(18));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(19));
    }
    
    private void tupleTest(final Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple20.getter0();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple20.getter1();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple20.getter2();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple20.getter3();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple20.getter4();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple20.getter5();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple20.getter6();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple20.getter7();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple20.getter8();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple20.getter9();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple20.getter10();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple20.getter11();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple20.getter12();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple20.getter13();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple20.getter14();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple20.getter15();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple20.getter16();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple20.getter17();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple20.getter18();
        final TupleGetter<Tuple20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple20.getter19();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertEquals(16, tuple.get(16));
        assertEquals(17, tuple.get(17));
        assertEquals(18, tuple.get(18));
        assertEquals(19, tuple.get(19));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(20));
    }
    
    private void tupleTest(final Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple21.getter0();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple21.getter1();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple21.getter2();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple21.getter3();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple21.getter4();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple21.getter5();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple21.getter6();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple21.getter7();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple21.getter8();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple21.getter9();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple21.getter10();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple21.getter11();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple21.getter12();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple21.getter13();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple21.getter14();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple21.getter15();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple21.getter16();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple21.getter17();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple21.getter18();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple21.getter19();
        final TupleGetter<Tuple21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter20 = Tuple21.getter20();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
        assertEquals(20, getter20.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertEquals(16, tuple.get(16));
        assertEquals(17, tuple.get(17));
        assertEquals(18, tuple.get(18));
        assertEquals(19, tuple.get(19));
        assertEquals(20, tuple.get(20));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(21));
    }
    
    private void tupleTest(final Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple22.getter0();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple22.getter1();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple22.getter2();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple22.getter3();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple22.getter4();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple22.getter5();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple22.getter6();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple22.getter7();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple22.getter8();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple22.getter9();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple22.getter10();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple22.getter11();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple22.getter12();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple22.getter13();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple22.getter14();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple22.getter15();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple22.getter16();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple22.getter17();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple22.getter18();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple22.getter19();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter20 = Tuple22.getter20();
        final TupleGetter<Tuple22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter21 = Tuple22.getter21();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
        assertEquals(20, getter20.apply(tuple));
        assertEquals(21, getter21.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertEquals(16, tuple.get(16));
        assertEquals(17, tuple.get(17));
        assertEquals(18, tuple.get(18));
        assertEquals(19, tuple.get(19));
        assertEquals(20, tuple.get(20));
        assertEquals(21, tuple.get(21));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(22));
    }
    
    private void tupleTest(final Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> tuple) {
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter0 = Tuple23.getter0();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter1 = Tuple23.getter1();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter2 = Tuple23.getter2();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter3 = Tuple23.getter3();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter4 = Tuple23.getter4();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter5 = Tuple23.getter5();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter6 = Tuple23.getter6();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter7 = Tuple23.getter7();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter8 = Tuple23.getter8();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter9 = Tuple23.getter9();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter10 = Tuple23.getter10();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter11 = Tuple23.getter11();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter12 = Tuple23.getter12();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter13 = Tuple23.getter13();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter14 = Tuple23.getter14();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter15 = Tuple23.getter15();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter16 = Tuple23.getter16();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter17 = Tuple23.getter17();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter18 = Tuple23.getter18();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter19 = Tuple23.getter19();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter20 = Tuple23.getter20();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter21 = Tuple23.getter21();
        final TupleGetter<Tuple23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>, Integer> getter22 = Tuple23.getter22();
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
        assertEquals(0, getter0.apply(tuple));
        assertEquals(1, getter1.apply(tuple));
        assertEquals(2, getter2.apply(tuple));
        assertEquals(3, getter3.apply(tuple));
        assertEquals(4, getter4.apply(tuple));
        assertEquals(5, getter5.apply(tuple));
        assertEquals(6, getter6.apply(tuple));
        assertEquals(7, getter7.apply(tuple));
        assertEquals(8, getter8.apply(tuple));
        assertEquals(9, getter9.apply(tuple));
        assertEquals(10, getter10.apply(tuple));
        assertEquals(11, getter11.apply(tuple));
        assertEquals(12, getter12.apply(tuple));
        assertEquals(13, getter13.apply(tuple));
        assertEquals(14, getter14.apply(tuple));
        assertEquals(15, getter15.apply(tuple));
        assertEquals(16, getter16.apply(tuple));
        assertEquals(17, getter17.apply(tuple));
        assertEquals(18, getter18.apply(tuple));
        assertEquals(19, getter19.apply(tuple));
        assertEquals(20, getter20.apply(tuple));
        assertEquals(21, getter21.apply(tuple));
        assertEquals(22, getter22.apply(tuple));
        assertEquals(0, tuple.get(0));
        assertEquals(1, tuple.get(1));
        assertEquals(2, tuple.get(2));
        assertEquals(3, tuple.get(3));
        assertEquals(4, tuple.get(4));
        assertEquals(5, tuple.get(5));
        assertEquals(6, tuple.get(6));
        assertEquals(7, tuple.get(7));
        assertEquals(8, tuple.get(8));
        assertEquals(9, tuple.get(9));
        assertEquals(10, tuple.get(10));
        assertEquals(11, tuple.get(11));
        assertEquals(12, tuple.get(12));
        assertEquals(13, tuple.get(13));
        assertEquals(14, tuple.get(14));
        assertEquals(15, tuple.get(15));
        assertEquals(16, tuple.get(16));
        assertEquals(17, tuple.get(17));
        assertEquals(18, tuple.get(18));
        assertEquals(19, tuple.get(19));
        assertEquals(20, tuple.get(20));
        assertEquals(21, tuple.get(21));
        assertEquals(22, tuple.get(22));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> tuple.get(23));
    }
}