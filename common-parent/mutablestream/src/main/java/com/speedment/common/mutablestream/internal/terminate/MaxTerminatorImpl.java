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
import com.speedment.common.mutablestream.terminate.MaxTerminator;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the streamed type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class MaxTerminatorImpl<T> 
extends AbstractTerminator<T, Stream<T>, Optional<T>> 
implements MaxTerminator<T> {

    private final Comparator<T> comparator;
    
    public MaxTerminatorImpl(HasNext<T, Stream<T>> previous, boolean parallel, Comparator<T> comparator) {
        super(previous, parallel);
        this.comparator = requireNonNull(comparator);
    }

    @Override
    public Comparator<T> getComparator() {
        return comparator;
    }
    
    @Override
    public Optional<T> execute() {
        try (final Stream<T> stream = buildPrevious()) {
            return stream.max(comparator);
        }
    }
}