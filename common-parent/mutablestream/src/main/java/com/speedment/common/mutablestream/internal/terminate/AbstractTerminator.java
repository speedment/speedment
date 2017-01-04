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
import com.speedment.common.mutablestream.terminate.Terminator;
import static java.util.Objects.requireNonNull;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>  the terminated stream type
 * @param <R>  the final type returned upon execution
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
abstract class AbstractTerminator<T, TS extends BaseStream<T, TS>, R> implements Terminator<T, TS, R> {
    
    private final HasNext<T, TS> previous;
    private final boolean parallel;
    
    protected AbstractTerminator(HasNext<T, TS> previous, boolean parallel) {
        this.previous = requireNonNull(previous);
        this.parallel = parallel;
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }
    
    @Override
    public final HasNext<T, TS> previous() {
        return previous;
    }
    
    protected final TS buildPrevious() {
        return previous.build(parallel);
    }
}