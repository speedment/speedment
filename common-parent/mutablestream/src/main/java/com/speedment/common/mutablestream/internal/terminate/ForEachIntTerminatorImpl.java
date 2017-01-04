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
import com.speedment.common.mutablestream.terminate.ForEachIntTerminator;
import static java.util.Objects.requireNonNull;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ForEachIntTerminatorImpl
extends AbstractTerminator<Integer, IntStream, Void> 
implements ForEachIntTerminator {

    private final IntConsumer consumer;
    
    public ForEachIntTerminatorImpl(HasNext<Integer, IntStream> previous, boolean parallel, IntConsumer consumer) {
        super(previous, parallel);
        this.consumer = requireNonNull(consumer);
    }

    @Override
    public IntConsumer getConsumer() {
        return consumer;
    }

    @Override
    public Void execute() {
        try (final IntStream stream = buildPrevious()) {
            stream.forEach(consumer);
        }
        
        return null;
    }
}