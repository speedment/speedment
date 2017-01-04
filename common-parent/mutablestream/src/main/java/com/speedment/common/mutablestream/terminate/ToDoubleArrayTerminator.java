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
import com.speedment.common.mutablestream.internal.terminate.ToDoubleArrayTerminatorImpl;
import java.util.stream.DoubleStream;

/**
 * 
 * @author Emil Forslund
 * @since   1.0.0
 */
public interface ToDoubleArrayTerminator extends Terminator<Double, DoubleStream, double[]> {
  
    static <T, A> ToDoubleArrayTerminator create(HasNext<Double, DoubleStream> previous, boolean parallel) {
        return new ToDoubleArrayTerminatorImpl(previous, parallel);
    }
    
}