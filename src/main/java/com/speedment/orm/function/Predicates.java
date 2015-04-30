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
 */
@Deprecated
public class Predicates {

    public static <T, C extends Comparable> Predicate<T> eq(C value, Function<T, C> mapper) {
        return new SpecialPredicate<>(StandardBinaryPredicate.EQ, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> eq(Function<T, C> mapper, C value) {
        return new SpecialPredicate<>(StandardBinaryPredicate.EQ, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> lessThan(C value, Function<T, C> mapper) {
        return new SpecialPredicate<>(StandardBinaryPredicate.LT, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> lessThan(Function<T, C> mapper, C value) {
        return new SpecialPredicate<>(StandardBinaryPredicate.LT, value, mapper, true);
    }

    public static <T, C extends Comparable> Predicate<T> greaterThan(C value, Function<T, C> mapper) {
        return new SpecialPredicate<>(StandardBinaryPredicate.GT, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> greaterThan(Function<T, C> mapper, C value) {
        return new SpecialPredicate<>(StandardBinaryPredicate.GT, value, mapper, true);
    }

//        Function<Hare, String> getHareName = h -> h.getName();
//
//    Predicate<Hare> namePredicate = h -> "harry".equals(h.getName());
//    public static <T, C extends Comparable> Predicate<T> equals(C value, Function<T, C> maper) {
//        return t -> value.equals(maper.apply(t));
//    }
}
