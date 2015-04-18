/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.util.stream.builder.action.reference;

import com.speedment.util.stream.builder.action.Action;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class FlatMapAction<T, R> extends Action<Stream<T>, Stream<R>> {

    public FlatMapAction(Function<? super T, ? extends Stream<? extends R>> mapper) {
        super((Stream<T> t) -> t.flatMap(mapper), Stream.class);
    }

}
