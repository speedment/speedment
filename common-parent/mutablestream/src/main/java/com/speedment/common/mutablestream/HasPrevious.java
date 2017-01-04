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

import java.util.stream.BaseStream;

/**
 * A pipeline action that is supposed to follow a previous action. This can be
 * either an intermediary or a terminating action.
 * <p>
 * Implementations of this interface should be <em>immutable</em> and contain
 * publically accessible getters for all the metadata nescessary to execute it.
 * 
 * @param <T>   the ingoing type
 * @param <TS>  the ingoing stream type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface HasPrevious<T, TS extends BaseStream<T, TS>> {
    
    /**
     * Returns a reference to the previous action.
     * 
     * @return  the previous action
     */
    HasNext<T, TS> previous();
    
}