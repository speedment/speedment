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

import com.speedment.common.mutablestream.internal.action.FlatMapActionImpl;
import java.util.function.Function;
import java.util.stream.Stream;
import com.speedment.common.mutablestream.HasNext;

/**
 *
 * @param <T>  the ingoing stream type
 * @param <R>  the outgoing stream type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface FlatMapAction<T, R> extends Action<T, Stream<T>, R, Stream<R>> {

    Function<T, Stream<R>> getMapper();
    
    static <T, R> FlatMapAction<T, R> create(HasNext<T, Stream<T>> previous, Function<T, Stream<R>> mapper) {
        return new FlatMapActionImpl<>(previous, mapper);
    }
}