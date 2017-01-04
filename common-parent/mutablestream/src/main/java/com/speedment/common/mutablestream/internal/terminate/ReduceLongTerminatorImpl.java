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
import com.speedment.common.mutablestream.terminate.ReduceLongTerminator;
import static java.util.Objects.requireNonNull;
import java.util.OptionalLong;
import java.util.function.LongBinaryOperator;
import java.util.stream.LongStream;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ReduceLongTerminatorImpl 
extends AbstractTerminator<Long, LongStream, OptionalLong> 
implements ReduceLongTerminator {

    private final long initialValue;
    private final boolean hasInitialValue;
    private final LongBinaryOperator combiner;
    
    public ReduceLongTerminatorImpl(HasNext<Long, LongStream> previous, boolean parallel, LongBinaryOperator combiner) {
        super(previous, parallel);
        this.initialValue    = 0;
        this.hasInitialValue = false;
        this.combiner        = requireNonNull(combiner);
    }

    public ReduceLongTerminatorImpl(HasNext<Long, LongStream> previous, boolean parallel, long initialValue, LongBinaryOperator combiner) {
        super(previous, parallel);
        this.initialValue    = initialValue;
        this.hasInitialValue = true;
        this.combiner        = requireNonNull(combiner);
    }
    
    @Override
    public OptionalLong getInitialValue() {
        return hasInitialValue 
            ? OptionalLong.of(initialValue) 
            : OptionalLong.empty();
    }

    @Override
    public LongBinaryOperator getCombiner() {
        return combiner;
    }
    
    @Override
    public OptionalLong execute() {
        try (final LongStream stream = buildPrevious()) {
            if (hasInitialValue) {
                return OptionalLong.of(stream.reduce(initialValue, combiner));
            } else {
                return stream.reduce(combiner);
            }
        }
    }
}
