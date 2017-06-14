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

import java.util.stream.Stream;
import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.terminate.ToArrayTerminator;
import java.util.function.IntFunction;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the terminated stream type
 * @param <A>  the resulting array type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ToArrayTerminatorImpl<T, A> 
extends AbstractTerminator<T, Stream<T>, A[]> 
implements ToArrayTerminator<T, A> {

    private final IntFunction<A[]> instantiator;
    
    public ToArrayTerminatorImpl(HasNext<T, Stream<T>> previous, boolean parallel, IntFunction<A[]> consumer) {
        super(previous, parallel);
        this.instantiator = requireNonNull(consumer);
    }

    @Override
    public IntFunction<A[]> getInstantiator() {
        return instantiator;
    }

    @Override
    public A[] execute() {
        try (final Stream<T> stream = buildPrevious()) {
            return stream.toArray(instantiator);
        }
    }
}