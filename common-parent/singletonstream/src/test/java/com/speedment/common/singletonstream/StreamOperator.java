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
package com.speedment.common.singletonstream;

import java.util.function.UnaryOperator;
import java.util.stream.BaseStream;

import static java.util.Objects.requireNonNull;

final class StreamOperator<T, S extends BaseStream<T, S>> implements UnaryOperator<S>  {

    private final String name;
    private final UnaryOperator<S> inner;

    StreamOperator(String name, UnaryOperator<S> inner) {
        this.name  = requireNonNull(name);
        this.inner = requireNonNull(inner);
    }

    @Override
    public S apply(S s) {
        return inner.apply(s);
    }

    String name() {
        return name;
    }

    static <T, S extends BaseStream<T, S>> StreamOperator<T, S> create(String name, UnaryOperator<S> operator) {
        return new StreamOperator<>(name, operator);
    }

}