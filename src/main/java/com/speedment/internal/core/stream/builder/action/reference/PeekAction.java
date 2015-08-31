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
package com.speedment.internal.core.stream.builder.action.reference;

import com.speedment.internal.core.stream.builder.action.Action;
import static com.speedment.internal.core.stream.builder.action.StandardBasicAction.PEEK;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> the type of the stream elements
 */
public final class PeekAction<T> extends Action<Stream<T>, Stream<T>> {

    public PeekAction(Consumer<? super T> action) {
        super(s -> s.peek(requireNonNull(action)), Stream.class, PEEK);
    }

}
