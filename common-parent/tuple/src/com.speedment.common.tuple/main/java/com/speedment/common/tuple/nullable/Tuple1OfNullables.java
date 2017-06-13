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
 * This interface defines a generic Tuple of degree 1 that can hold non-null
 * values. A Tuple is type safe, immutable and thread safe. For Tuples that can
 * hold
 * null elements see {@link TupleOfNullables}
 * 
 * This {@link Tuple } has a degree of 1
 * 
 * @param <T0> type of element 0
 * 
 * @author Per Minborg
 */
public interface Tuple1OfNullables<T0> extends TupleOfNullables {
    
    Optional<T0> get0();
    
    @Override
    default int degree() {
        return 1;
    }
    
    @SuppressWarnings("unchecked")
    default Optional<Object> get(int index) {
        switch (index) {
            case 0 : return (Optional<Object>)get0();
            default : throw new IllegalArgumentException(String.format("Index %d is outside bounds of tuple of degree %s", index, degree()
            ));
        }
    }
}