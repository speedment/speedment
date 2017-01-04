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
package com.speedment.common.mutablestream;

import com.speedment.common.mutablestream.action.Action;
import com.speedment.common.mutablestream.terminate.Terminator;
import java.util.stream.BaseStream;

/**
 * A pipeline action that can be followed up by another action. This can be
 * either an intermediary or a source action.
 * <p>
 * Implementations of this interface should be <em>immutable</em> and contain
 * publically accessible getters for all the metadata nescessary to execute it.
 * 
 * @param <R>   the outgoing type
 * @param <RS>  the type of the outgoing stream
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface HasNext<R, RS extends BaseStream<R, RS>> {
    
    /**
     * Returns an action that fulfills both everything that this action does and
     * everything that the specified action does. The result can either (i) be
     * the action specified or (ii) a reference to this action if this can
     * accomplish both tasks.
     * <p>
     * Actions used as parameter to this method should already have this 
     * instance set as it's 'previous' action upon calling this method.
     * 
     * @param <Q>   the outgoing type after this method has been applied
     * @param <QS>  the stream type after this method has been applied
     * @param next  the action to append to the pipeline
     * @return      an appended pipeline
     */
    <Q, QS extends BaseStream<Q, QS>> HasNext<Q, QS> append(Action<R, RS, Q, QS> next);
    
    /**
     * Executes the specified terminator on this pipeline. If this action can
     * fully execute the specified action without calling it's
     * {@link Terminator#execute()}-method it may do so, as long as the result 
     * returns is equivalent to what the terminator would have returned.
     * 
     * @param <X>         the result type returned by the terminator
     * @param terminator  the terminator for this pipeline
     * @return            a result from terminating the pipeline
     */
    <X> X execute(Terminator<R, RS, X> terminator);
    
    /**
     * Builds a standard java stream from the pipeline. Streams can be built
     * either as sequential or parallel.
     * 
     * @param parallel  if the built pipeline should be a parallel stream
     * @return          the built stream
     */
    RS build(boolean parallel);
    
}