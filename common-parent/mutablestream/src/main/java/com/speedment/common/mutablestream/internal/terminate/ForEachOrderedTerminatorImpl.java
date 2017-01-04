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

import java.util.function.Consumer;
import java.util.stream.Stream;
import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.terminate.ForEachOrderedTerminator;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <T>  the terminated stream type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ForEachOrderedTerminatorImpl<T> 
extends AbstractTerminator<T, Stream<T>, Void> 
implements ForEachOrderedTerminator<T> {

    private final Consumer<T> consumer;
    
    public ForEachOrderedTerminatorImpl(HasNext<T, Stream<T>> previous, boolean parallel, Consumer<T> consumer) {
        super(previous, parallel);
        this.consumer = requireNonNull(consumer);
    }

    @Override
    public Consumer<T> getConsumer() {
        return consumer;
    }

    @Override
    public Void execute() {
        try (final Stream<T> stream = buildPrevious()) {
            stream.forEachOrdered(consumer);
        }
        
        return null;
    }
}