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
package com.speedment.common.mutablestream.action;

import com.speedment.common.mutablestream.HasNext;
import com.speedment.common.mutablestream.internal.action.SortedActionImpl;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.BaseStream;

/**
 *
 * @param <T>   the streamed type
 * @param <TS>  the type of the stream itself
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface SortedAction<T, TS extends BaseStream<T, TS>> extends Action<T, TS, T, TS> {
    
    Optional<Comparator<T>> getComparator();
    
    static <T, TS extends BaseStream<T, TS>> SortedAction<T, TS> create(HasNext<T, TS> previous) {
        return create(previous, null);
    }
    
    static <T, TS extends BaseStream<T, TS>> SortedAction<T, TS> create(HasNext<T, TS> previous, Comparator<T> comparator) {
        return new SortedActionImpl<>(previous, comparator);
    }
}