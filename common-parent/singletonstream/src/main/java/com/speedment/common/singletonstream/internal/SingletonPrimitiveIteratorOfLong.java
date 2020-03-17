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
package com.speedment.common.singletonstream.internal;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.function.LongConsumer;

import static java.util.Objects.requireNonNull;

public final class SingletonPrimitiveIteratorOfLong implements PrimitiveIterator.OfLong {

    private final long element;
    private boolean hasNext = true;

    public SingletonPrimitiveIteratorOfLong(long element) {
        this.element = element;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public long nextLong() {
        if (hasNext) {
            hasNext = false;
            return element;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Long next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        return nextLong();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(LongConsumer action) {
        requireNonNull(action);
        if (hasNext) {
            action.accept(element);
            hasNext = false;
        }
    }
}
