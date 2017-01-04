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
import com.speedment.common.mutablestream.terminate.ReduceIntTerminator;
import static java.util.Objects.requireNonNull;
import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ReduceIntTerminatorImpl 
extends AbstractTerminator<Integer, IntStream, OptionalInt> 
implements ReduceIntTerminator {

    private final int initialValue;
    private final boolean hasInitialValue;
    private final IntBinaryOperator combiner;
    
    public ReduceIntTerminatorImpl(HasNext<Integer, IntStream> previous, boolean parallel, IntBinaryOperator combiner) {
        super(previous, parallel);
        this.initialValue    = 0;
        this.hasInitialValue = false;
        this.combiner        = requireNonNull(combiner);
    }

    public ReduceIntTerminatorImpl(HasNext<Integer, IntStream> previous, boolean parallel, int initialValue, IntBinaryOperator combiner) {
        super(previous, parallel);
        this.initialValue    = initialValue;
        this.hasInitialValue = true;
        this.combiner        = requireNonNull(combiner);
    }
    
    @Override
    public OptionalInt getInitialValue() {
        return hasInitialValue 
            ? OptionalInt.of(initialValue) 
            : OptionalInt.empty();
    }

    @Override
    public IntBinaryOperator getCombiner() {
        return combiner;
    }
    
    @Override
    public OptionalInt execute() {
        try (final IntStream stream = buildPrevious()) {
            if (hasInitialValue) {
                return OptionalInt.of(stream.reduce(initialValue, combiner));
            } else {
                return stream.reduce(combiner);
            }
        }
    }
}
