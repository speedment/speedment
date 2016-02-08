/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.stream.builder.action.reference;

import com.speedment.internal.core.stream.builder.action.Action;
import static com.speedment.internal.core.stream.builder.action.StandardBasicAction.FLAT_MAP_TO;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> the input type of the stream elements
 */
public final class FlatMapToIntAction<T> extends Action<Stream<T>, IntStream> {

    public FlatMapToIntAction(Function<? super T, ? extends IntStream> mapper) {
        super(s -> s.flatMapToInt(requireNonNull(mapper)), IntStream.class, FLAT_MAP_TO);
    }

}
