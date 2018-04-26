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
package com.speedment.common.tuple.getter;

import com.speedment.common.tuple.Tuple;

/**
 * Specialization of {@link TupleGetter} that always returns the 9th element.
 * 
 * @param <TUPLE> the type of the {@code Tuple} to get a value from
 * @param <T9>    the type of the element to return
 * 
 * @author Emil Forslund
 * @since  1.0.8
 */
@FunctionalInterface
public interface TupleGetter9<TUPLE extends Tuple, T9> extends TupleGetter<TUPLE, T9> {
    
    @Override
    default int getOrdinal() {
        return 9;
    }
}