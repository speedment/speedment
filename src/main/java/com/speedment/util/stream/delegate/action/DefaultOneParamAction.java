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
package com.speedment.util.stream.delegate.action;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <P0>
 */
public class DefaultOneParamAction<T, P0> implements TypeInvariantAction<T> {

    private final BiFunction<Stream<T>, P0, Stream<T>> biMapper;
    protected final P0 firstParameter;

    public DefaultOneParamAction(final BiFunction<Stream<T>, P0, Stream<T>> biMapper, final P0 firstParameter) {
        this.biMapper = Objects.requireNonNull(biMapper);
        this.firstParameter = Objects.requireNonNull(firstParameter);
    }

    @Override
    public Stream<T> apply(Stream<T> s) {
        return biMapper.apply(s, firstParameter);
    }

}
