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

import com.speedment.orm.config.model.Column;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 */
@Deprecated
public class PredicateBuilder<ENTITY, V extends Comparable<V>> implements Predicate<ENTITY> {

    private Column column;
    private Function<ENTITY, V> getter;
    private BinaryPredicate binaryOperation;
    private V value;

    public PredicateBuilder(Column column, Function<ENTITY, V> getter) {
        this.column = column;
        this.getter = getter;
    }

    public Predicate<ENTITY> equal(V value) {
        this.binaryOperation = StandardBinaryPredicate.EQ;
        this.value = value;
        return this;
    }

    public Predicate<ENTITY> greaterThan(V value) {
        this.binaryOperation = StandardBinaryPredicate.GT;
        this.value = value;
        return this;
    }

    public Predicate<ENTITY> lessThan(V value) {
        this.binaryOperation = StandardBinaryPredicate.LT;
        this.value = value;
        return this;
    }

    @Override
    public boolean test(ENTITY t) {
        return binaryOperation.test(getter.apply(t), value);
    }

    public V from(ENTITY t) {
        return getter.apply(t);
    }

}
