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

import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public abstract interface TypeInvariantAction<T> extends Action<T, T> {

    /**
     * Applies this Stream Action into the input Stream and returns a resulting
     * Stream of the same type.
     *
     * @param s input Stream
     * @return the resulting stream.
     */
    @Override
    public abstract Stream<T> apply(Stream<T> s);

}
