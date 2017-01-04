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
import com.speedment.common.mutablestream.internal.terminate.AverageTerminatorImpl;
import java.util.OptionalDouble;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>   the streamed type
 * @param <TS>  the type of the stream itself
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface AverageTerminator<T, TS extends BaseStream<T, TS>> 
extends Terminator<T, TS, OptionalDouble> {
    
    static <T, TS extends BaseStream<T, TS>> AverageTerminator<T, TS> 
    create(HasNext<T, TS> previous, boolean parallel) {
        
        return new AverageTerminatorImpl<>(previous, parallel);
    }
    
}