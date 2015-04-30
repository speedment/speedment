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
package com.speedment.orm.function;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <T>
 * @param <C>
 */
@Deprecated
public class SpecialPredicate<T, C extends Comparable<C>> implements Predicate<T> {

    private final StandardBinaryPredicate binaryOperation;
    private final C value;
    private final Function<T, C> mapper;
    private final boolean invert;

    public SpecialPredicate(StandardBinaryPredicate binaryOperation, C value, Function<T, C> mapper) {
        this(binaryOperation, value, mapper, false);
    }

    public SpecialPredicate(StandardBinaryPredicate binaryOperation, C value, Function<T, C> mapper, boolean invert) {
        this.binaryOperation = binaryOperation;
        this.value = value;
        this.mapper = mapper;
        this.invert = invert;
    }

    @Override
    public boolean test(T t) {
        return isInvert() ^ getBinaryOperation().test(getValue(), getMapper().apply(t));
    }

    public StandardBinaryPredicate getBinaryOperation() {
        return binaryOperation;
    }

    public C getValue() {
        return value;
    }

    public Function<T, C> getMapper() {
        return mapper;
    }

    public boolean isInvert() {
        return invert;
    }

}
