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
package com.speedment.common.mutablestream.internal.terminate;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.terminate.ReduceDoubleTerminator;
import static java.util.Objects.requireNonNull;
import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;
import java.util.stream.DoubleStream;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ReduceDoubleTerminatorImpl 
extends AbstractTerminator<Double, DoubleStream, OptionalDouble> 
implements ReduceDoubleTerminator {

    private final double initialValue;
    private final boolean hasInitialValue;
    private final DoubleBinaryOperator combiner;
    
    public ReduceDoubleTerminatorImpl(HasNext<Double, DoubleStream> previous, boolean parallel, DoubleBinaryOperator combiner) {
        super(previous, parallel);
        this.initialValue    = 0;
        this.hasInitialValue = false;
        this.combiner        = requireNonNull(combiner);
    }

    public ReduceDoubleTerminatorImpl(HasNext<Double, DoubleStream> previous, boolean parallel, double initialValue, DoubleBinaryOperator combiner) {
        super(previous, parallel);
        this.initialValue    = initialValue;
        this.hasInitialValue = true;
        this.combiner        = requireNonNull(combiner);
    }
    
    @Override
    public OptionalDouble getInitialValue() {
        return hasInitialValue 
            ? OptionalDouble.of(initialValue) 
            : OptionalDouble.empty();
    }

    @Override
    public DoubleBinaryOperator getCombiner() {
        return combiner;
    }
    
    @Override
    public OptionalDouble execute() {
        try (final DoubleStream stream = buildPrevious()) {
            if (hasInitialValue) {
                return OptionalDouble.of(stream.reduce(initialValue, combiner));
            } else {
                return stream.reduce(combiner);
            }
        }
    }
}
