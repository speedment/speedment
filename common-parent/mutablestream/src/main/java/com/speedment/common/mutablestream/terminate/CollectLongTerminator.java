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
package com.speedment.common.mutablestream.terminate;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.internal.terminate.CollectLongTerminatorImpl;
import java.util.function.BiConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;

/**
 *
 * @param <R>  the resulting type once the collector has been applied
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface CollectLongTerminator<R> extends Terminator<Long, LongStream, R> {

    Supplier<R> getSupplier();
    
    ObjLongConsumer<R> getAccumulator();
    
    BiConsumer<R, R> getCombiner();
    
    static <R> CollectLongTerminator<R> create(
            HasNext<Long, LongStream> previous, 
            boolean parallel, 
            Supplier<R> supplier, 
            ObjLongConsumer<R> accumulator, 
            BiConsumer<R, R> merger) {
        
        return new CollectLongTerminatorImpl<>(
            previous, parallel, supplier, accumulator, merger
        );
    }
}
