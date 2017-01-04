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
package com.speedment.common.mutablestream.action;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.internal.action.MapLongActionImpl;
import java.util.function.LongFunction;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @param <R>  the outgoing type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface MapLongAction<R> extends Action<Long, LongStream, R, Stream<R>> {
    
    LongFunction<R> getMapper();
    
    static <R> MapLongAction<R> create(HasNext<Long, LongStream> previous, LongFunction<R> mapper) {
        return new MapLongActionImpl<>(previous, mapper);
    }
}