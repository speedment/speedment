/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.stream.builder.action.reference;

import static com.speedment.runtime.core.internal.stream.builder.action.StandardBasicAction.DROP_WHILE;
import com.speedment.runtime.core.internal.stream.builder.action.trait.HasPredicate;
import com.speedment.runtime.core.stream.java9.Java9StreamUtil;
import com.speedment.runtime.core.stream.action.Action;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> the type of the stream elements
 */
public final class DropWhileAction<T> extends Action<Stream<T>, Stream<T>> implements HasPredicate<T> {

    private final Predicate<? super T> predicate;

    public DropWhileAction(Predicate<? super T> predicate) {
        super(s -> Java9StreamUtil.dropWhile(s, requireNonNull(predicate)), Stream.class, DROP_WHILE);
        this.predicate = predicate;
    }

    @Override
    public Predicate<? super T> getPredicate() {
        return predicate;
    }

}
