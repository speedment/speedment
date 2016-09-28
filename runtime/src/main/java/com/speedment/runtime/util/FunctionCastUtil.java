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
package com.speedment.runtime.util;



import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 *
 * @author Per Minborg
 */

public class FunctionCastUtil {

    public static <T, U> BiConsumer<T, U> asBiConsumer(BiConsumer<T, U> biConsumer) {
        return biConsumer;
    }

    public static <T, U, R> BiFunction<T, U, R> asBiFunction(BiFunction<T, U, R> biFunction) {
        return biFunction;
    }

    public static <T> BinaryOperator<T> asBinaryOperator(BinaryOperator<T> binaryOperator) {
        return binaryOperator;
    }

    public static <T, U> BiPredicate<T, U> asBiPredicate(BiPredicate<T, U> biPredicate) {
        return biPredicate;
    }

    public static BooleanSupplier asBooleanSupplier(BooleanSupplier booleanSupplier) {
        return booleanSupplier;
    }

    public static <T> Consumer<T> asConsumer(Consumer<T> consumer) {
        return consumer;
    }

    public static DoubleBinaryOperator asDoubleBinaryOperator(DoubleBinaryOperator doubleBinaryOperator) {
        return doubleBinaryOperator;
    }

    public static DoubleConsumer asDoubleConsumer(DoubleConsumer doubleConsumer) {
        return doubleConsumer;
    }

    public static <R> DoubleFunction<R> asDoubleFunction(DoubleFunction<R> doubleFunction) {
        return doubleFunction;
    }

    public static DoublePredicate asDoublePredicate(DoublePredicate doublePredicate) {
        return doublePredicate;
    }

    public static DoubleToIntFunction asDoubleToIntFunction(DoubleToIntFunction doubleToIntFunctiontem) {
        return doubleToIntFunctiontem;
    }

    public static DoubleToLongFunction asDoubleToLongFunction(DoubleToLongFunction doubleToLongFunction) {
        return doubleToLongFunction;
    }

    public static DoubleUnaryOperator asDoubleUnaryOperator(DoubleUnaryOperator doubleUnaryOperator) {
        return doubleUnaryOperator;
    }

    public static <T, R> Function<T, R> asFunction(Function<T, R> function) {
        return function;
    }

    public static IntBinaryOperator asIntBinaryOperator(IntBinaryOperator intBinaryOperator) {
        return intBinaryOperator;
    }

    public static IntConsumer asIntConsumer(IntConsumer intConsumer) {
        return intConsumer;
    }

    public static <R> IntFunction<R> asIntFunction(IntFunction<R> intFunction) {
        return intFunction;
    }

    public static IntPredicate asIntPredicate(IntPredicate intPredicate) {
        return intPredicate;
    }

    public static IntSupplier asIntSupplier(IntSupplier intSupplier) {
        return intSupplier;
    }

    public static IntToDoubleFunction asIntToDoubleFunction(IntToDoubleFunction intToDoubleFunction) {
        return intToDoubleFunction;
    }

    public static IntToLongFunction asIntToLongFunction(IntToLongFunction intToLongFunction) {
        return intToLongFunction;
    }

    public static IntUnaryOperator asIntUnaryOperator(IntUnaryOperator intUnaryOperator) {
        return intUnaryOperator;
    }

    public static LongBinaryOperator asLongBinaryOperator(LongBinaryOperator longBinaryOperator) {
        return longBinaryOperator;
    }

    public static LongConsumer asLongConsumer(LongConsumer longConsumer) {
        return longConsumer;
    }

    public static <R> LongFunction<R> asLongFunction(LongFunction<R> longFunction) {
        return longFunction;
    }

    public static LongPredicate asLongPredicate(LongPredicate longPredicate) {
        return longPredicate;
    }

    public static <T> LongSupplier asLongSupplier(LongSupplier longSupplier) {
        return longSupplier;
    }

    public static LongToDoubleFunction asLongToDoubleFunction(LongToDoubleFunction longToDoubleFunction) {
        return longToDoubleFunction;
    }

    public static LongToIntFunction asLongToIntFunction(LongToIntFunction longToIntFunction) {
        return longToIntFunction;
    }

    public static LongUnaryOperator asLongUnaryOperator(LongUnaryOperator longUnaryOperator) {
        return longUnaryOperator;
    }

    public static <T> ObjDoubleConsumer<T> asObjDoubleConsumer(ObjDoubleConsumer<T> objDoubleConsumer) {
        return objDoubleConsumer;
    }

    public static <T> ObjIntConsumer<T> asObjIntConsumer(ObjIntConsumer<T> objIntConsumer) {
        return objIntConsumer;
    }

    public static <T> ObjLongConsumer<T> asObjLongConsumer(ObjLongConsumer<T> objLongConsumer) {
        return objLongConsumer;
    }

    public static <T> Predicate<T> asPredicate(Predicate<T> predicate) {
        return predicate;
    }

    public static <T> Supplier<T> asSupplier(Supplier<T> supplier) {
        return supplier;
    }

    public static <T, U> ToDoubleBiFunction<T, U> asToDoubleBiFunction(ToDoubleBiFunction<T, U> toDoubleBiFunction) {
        return toDoubleBiFunction;
    }

    public static <T> ToDoubleFunction<T> asToDoubleFunction(ToDoubleFunction<T> toDoubleFunction) {
        return toDoubleFunction;
    }

    public static <T, U> ToIntBiFunction<T, U> asToIntBiFunction(ToIntBiFunction<T, U> toIntBiFunction) {
        return toIntBiFunction;
    }

    public static <T> ToIntFunction<T> asToIntFunction(ToIntFunction<T> ioIntFunction) {
        return ioIntFunction;
    }

    public static <T, U> ToLongBiFunction<T, U> asToLongBiFunction(ToLongBiFunction<T, U> toLongBiFunction) {
        return toLongBiFunction;
    }

    public static <T> ToLongFunction<T> asToLongFunction(ToLongFunction<T> toLongFunction) {
        return toLongFunction;
    }

    public static <T> UnaryOperator<T> asUnaryOperator(UnaryOperator<T> unaryOperator) {
        return unaryOperator;
    }

    private FunctionCastUtil() {
    }

}
