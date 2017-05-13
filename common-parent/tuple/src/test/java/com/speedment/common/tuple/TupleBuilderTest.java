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
package com.speedment.common.tuple;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class TupleBuilderTest {

    @Test
    public void testBuilder() {
        final Tuple3<Integer, String, Long> expected = Tuples.of(1, "Olle", Long.MAX_VALUE);
        final Tuple3<Integer, String, Long> actual = TupleBuilder.builder().add(1).add("Olle").add(Long.MAX_VALUE).build();
        assertEquals(expected, actual);
        
        Tuples.of(1,3,4,4,"Arne");
        
        TupleBuilder.builder().add(1).add("Arne").build();

    }

}
