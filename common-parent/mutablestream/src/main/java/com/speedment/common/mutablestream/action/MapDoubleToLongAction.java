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
import com.speedment.common.mutablestream.internal.action.MapDoubleToLongActionImpl;
import java.util.function.DoubleToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

/**
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface MapDoubleToLongAction extends Action<Double, DoubleStream, Long, LongStream> {
    
    DoubleToLongFunction getMapper();
    
    static MapDoubleToLongAction create(HasNext<Double, DoubleStream> previous, DoubleToLongFunction mapper) {
        return new MapDoubleToLongActionImpl(previous, mapper);
    }
}