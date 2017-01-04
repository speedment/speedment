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
package com.speedment.common.mutablestream.internal.action;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.action.Action;
import com.speedment.common.mutablestream.terminate.Terminator;
import static java.util.Objects.requireNonNull;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>  the ingoing type
 * @param <R>  the outgoing type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
abstract class AbstractAction<
    T, TS extends BaseStream<T, TS>, 
    R, RS extends BaseStream<R, RS>
> implements Action<T, TS, R, RS> {
    
    private final HasNext<T, TS> previous;
    
    protected AbstractAction(HasNext<T, TS> previous) {
        this.previous = requireNonNull(previous);
    }
    
    @Override
    public final HasNext<T, TS> previous() {
        return previous;
    }

    @Override
    public <X> X execute(Terminator<R, RS, X> terminator) {
        return terminator.execute();
    }
}