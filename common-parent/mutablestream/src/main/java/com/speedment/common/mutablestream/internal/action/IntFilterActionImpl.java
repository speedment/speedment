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
import com.speedment.common.mutablestream.action.IntFilterAction;
import java.util.function.IntPredicate;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class IntFilterActionImpl extends AbstractAction<Integer, IntStream, Integer, IntStream> implements IntFilterAction {

    private final IntPredicate predicate;
    
    public IntFilterActionImpl(HasNext<Integer, IntStream> previous, IntPredicate predicate) {
        super(previous);
        this.predicate = requireNonNull(predicate);
    }
    
    @Override
    public IntPredicate getPredicate() {
        return predicate;
    }
    
    @Override
    public <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<Integer, IntStream, Q, QS> next) {
        return next;
    }
    
    @Override
    public IntStream build(boolean parallel) {
        return previous().build(parallel).filter(predicate);
    }
}